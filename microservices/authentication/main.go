package main

import (
	"context"
	"database/sql"
	"encoding/json"
	"flag"
	"log"
	"net"
	"net/mail"
	"time"

	"github.com/go-redis/redis"
	_ "github.com/lib/pq"
	"github.com/streadway/amqp"
	"google.golang.org/grpc"
	"google.golang.org/grpc/reflection"
	"google.golang.org/protobuf/proto"

	protocGenerated "authentication/generated/proto"
	sqlcGenerated "authentication/generated/sqlc"
)

var (
	OTPQueueName= "otp"
	ProfileQueueName= "profile"

	ServerError= "server error occured"

	EmailValidationError= "error validating email"
	NameValidationError= "name should be between 3 to 50 characters"

	SendOTP_RabbitMQMessageType= "SendOTP"
)

type GlobalVariables struct {

	Repository sqlcGenerated.Querier
	RedisClient *redis.Client
	RabbitMQChannel *amqp.Channel
}

var globalVariables= &GlobalVariables{ }

func connectToRabbitMQ( ) (*amqp.Channel, func( )) {
	connection, error := amqp.Dial("amqp://user:password@localhost:5672")
	if error != nil {
		log.Fatal("💀 error connecting to rabbitMQ : ", error.Error( ))}

	channel, error := connection.Channel( )
	if error != nil {
		log.Fatal("💀 error creating rabbitMQ channel : ", error.Error( ))}

	for _, queueName := range []string{OTPQueueName, ProfileQueueName} {
		_, error := channel.QueueDeclare(
			queueName, false, false, false, false, nil)

		if error != nil {
			log.Fatalf("error declaring queue %s : %s", queueName, error.Error( ))}
	}

	log.Println("🔥 connected to rabbitMQ")

	return channel, func( ) {

		defer channel.Close( )
		defer connection.Close( )
	}
}

func connectToCockroachDB( ) {
	connection, error := sql.Open("postgres", "postgresql://root@localhost:26257/defaultdb?sslmode=disable")
	if error != nil {
		log.Panic("💀 error connecting to cockroachDB : ", error.Error( )) }

	error= connection.Ping( )
	if error != nil {
		log.Panic("💀 error pinging cockroachDB : ", error.Error( )) }

	globalVariables.Repository= sqlcGenerated.New(connection)

	log.Println("🔥 connected to cockroachDB")
}

type ImplementedAuthenticationService struct {
	*protocGenerated.UnimplementedAuthenticationServer
}

type TemporaryUserDetails struct {
	IsVerified bool

	Name string
	Email string
}

func(*ImplementedAuthenticationService) StartRegistration(
	ctx context.Context, request *protocGenerated.StartRegistrationRequest) (*protocGenerated.StartRegistrationResponse, error) {

	//! input validation

	_, error := mail.ParseAddress(request.Email)
	if error!= nil {
		return &protocGenerated.StartRegistrationResponse{Error: &EmailValidationError}, nil }

	if len(request.Name) < 3 || len(request.Name) > 50 {
		return &protocGenerated.StartRegistrationResponse{Error: &NameValidationError}, nil }

	//! saving the details temporarily for 5 minutes in redis

	temporaryUserDetails, error := json.Marshal(
		&TemporaryUserDetails{
			IsVerified: false,

			Name: request.Name,
			Email: request.Email,
		},
	)

	if error != nil {
		log.Println("error : ", error.Error( ))
		return &protocGenerated.StartRegistrationResponse{Error: &ServerError}, nil
	}

	error= globalVariables.RedisClient.Set(request.Email, temporaryUserDetails, 600 * time.Second).Err( )
	if error != nil {
		log.Println("error : ", error.Error( ))
		return &protocGenerated.StartRegistrationResponse{Error: &ServerError}, nil
	}

	//! sending request to the otp service for sending otp to the email address

	message, error := proto.Marshal(
		&protocGenerated.SendOTPRequest{
			MessageType: SendOTP_RabbitMQMessageType,

			Email: request.Email,
		},
	)
	if error != nil {
		log.Println("error : ", error.Error( ))
		return &protocGenerated.StartRegistrationResponse{Error: &ServerError}, nil
	}

	error= globalVariables.RabbitMQChannel.Publish(
		"", OTPQueueName,
		false, false,
		amqp.Publishing{ Body: message },
	)
	if error != nil {
		log.Println("error : ", error.Error( ))
		return &protocGenerated.StartRegistrationResponse{Error: &ServerError}, nil
	}

	return &protocGenerated.StartRegistrationResponse{ }, nil
}

func main( ) {

	//* connecting to rabbitMQ
	rabbitMQChannel, cleanupRabbitMQResources := connectToRabbitMQ( )
	defer cleanupRabbitMQResources( )

	globalVariables.RabbitMQChannel= rabbitMQChannel

	//* connecting to redis

	redisClient := redis.NewClient(
		&redis.Options{
			Addr: "localhost:6379",

			Password: "password",
			DB: 0,
		},
	)

	_, error := redisClient.Ping( ).Result( )
	if error != nil {
		log.Fatal("💀 error connecting to redis : ", error.Error( )) }
	defer redisClient.Close( )

	globalVariables.RedisClient= redisClient
	log.Println("🔥 connected to redis")

	//* connecting to cockroachDB
	connectToCockroachDB( )

	//* starting the gRPC server

	var port= flag.String("port", "0.0.0.0:4000", "Port where gRPC server will listen")
	portListener, error := net.Listen("tcp", *port)
	if error != nil {
		log.Fatal("💀 error listening at port : ", error.Error( )) }

	server := grpc.NewServer( )
	reflection.Register(server) // adding reflection service

	protocGenerated.RegisterAuthenticationServer(server, &ImplementedAuthenticationService{ })

	log.Println("🔥 starting gRPC server")
	server.Serve(portListener)

	defer server.Stop( )
}