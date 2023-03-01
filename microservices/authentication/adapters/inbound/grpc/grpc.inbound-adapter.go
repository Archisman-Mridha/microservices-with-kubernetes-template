package grpcInboundAdapter

import (
	"log"
	"net"

	"google.golang.org/grpc"
	"google.golang.org/grpc/reflection"

	grpcServices "authentication/adapters/inbound/grpc/services"
	protocGenerated "authentication/generated/proto/grpc"
	"authentication/ports"
)

type GrpcAdapter struct {
	server *grpc.Server
}

func(adapter *GrpcAdapter) StartServer(applicationLayer ports.ApplicationPort) {

	portListener, error := net.Listen("tcp", "0.0.0.0:4000")
	if error != nil {
		log.Fatal("💀 error listening at port : ", error.Error( ))}

	adapter.server= grpc.NewServer( )
	reflection.Register(adapter.server) // adding reflection service

	protocGenerated.RegisterAuthenticationServer(
		adapter.server, &grpcServices.ImplementedAuthenticationGrpcService{ ApplicationLayer: applicationLayer })

	log.Println("🔥 starting gRPC server")
	adapter.server.Serve(portListener)
}

func(adapter *GrpcAdapter) StopServer( ) {
	if adapter.server != nil {
		adapter.server.Stop( )}
}