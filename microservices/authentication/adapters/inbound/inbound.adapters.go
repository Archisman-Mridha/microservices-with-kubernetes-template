package inboundAdapters

import (
	grpcInboundAdapter "authentication/adapters/inbound/grpc"
	rabbitMQInboundAdapter "authentication/adapters/inbound/rabbitmq"
	"authentication/ports"
)

type GrpcAdapter struct {
	grpcInboundAdapter.GrpcAdapter
}

func CreateRabbitMQInboundAdapter(applicationLayer ports.ApplicationPort) *rabbitMQInboundAdapter.RabbitMQInboundAdapter {
	return rabbitMQInboundAdapter.CreateRabbitMQInboundAdapter(applicationLayer)}