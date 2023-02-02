package main

import (
	"flag"
	"log"
	"net"

	"google.golang.org/grpc"
	"google.golang.org/grpc/reflection"
)

func main( ) {

	var port= flag.String("port", "0.0.0.0:4000", "Port where gRPC server will listen")
	portListener, error := net.Listen("tcp", *port)
	if error != nil {
		log.Panicf("❌ error listening at port : %s", error.Error( )) }

	server := grpc.NewServer( )
	reflection.Register(server)

	log.Println("🚀 starting gRPC server")
	server.Serve(portListener)

	defer server.Stop( )
}