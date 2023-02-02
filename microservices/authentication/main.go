package main

import (
	"flag"
	"log"
	"net"

	"github.com/streadway/amqp"
	"google.golang.org/grpc"
	"google.golang.org/grpc/reflection"

	protocGenerated "authentication/generated/proto"
)

const (
	OTPQueueName= "otp"
	ProfileQueueName= "profile"
)

type ImplementedAuthenticationService struct {
	*protocGenerated.UnimplementedAuthenticationServer
}

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

func main( ) {

	//* connecting to rabbitMQ
	cleanupRabbitMQResources := connectToRabbitMQ( )
	defer cleanupRabbitMQResources( )

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