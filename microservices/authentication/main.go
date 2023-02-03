package main

import (
	"context"
	"flag"
	"log"
	"net"

	"github.com/go-redis/redis"
	"github.com/streadway/amqp"
	"google.golang.org/grpc"
	"google.golang.org/grpc/reflection"

	protocGenerated "authentication/generated/proto"
)

const (
	OTPQueueName= "otp"
	ProfileQueueName= "profile"
)

func connectToRabbitMQ( ) func( ) {
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

	return func( ) {

		defer channel.Close( )
		defer connection.Close( )
	}
}

type ImplementedAuthenticationService struct {
	*protocGenerated.UnimplementedAuthenticationServer
}

func(*ImplementedAuthenticationService) StartRegistration(
	ctx context.Context, request *protocGenerated.StartRegistrationRequest) (*protocGenerated.StartRegistrationResponse, error) {

	return &protocGenerated.StartRegistrationResponse{ }, nil
}

func main( ) {

	//* connecting to rabbitMQ
	cleanupRabbitMQResources := connectToRabbitMQ( )
	defer cleanupRabbitMQResources( )

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

	log.Println("🔥 connected to redis")

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