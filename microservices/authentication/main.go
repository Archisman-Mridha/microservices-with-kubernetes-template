package main

import (
	_ "github.com/lib/pq"

	inboundAdapters "authentication/adapters/inbound"
	outboundAdapters "authentication/adapters/outbound"
	"authentication/application"
	businessLogic "authentication/domain/business-logic"
	"authentication/domain/repository"
	"authentication/ports"
	"authentication/utils"
)

var rabbitMQMainConnection= utils.CreateRabbitMQMainConnection( )

var (
	//* inbound adapters
	grpcAdapter= &inboundAdapters.GrpcAdapter{ }
	rabbitMQInboundAdapter= inboundAdapters.CreateRabbitMQInboundAdapter(applicationLayer)

	//* outbound adapters
	cockroachDBAdapter= &outboundAdapters.CockroachDBAdapter{ }
	redisAdapter= &outboundAdapters.RedisAdapter{ }

	rabbitMQOutboundAdapter= &outboundAdapters.RabbitMQOutboundAdapter{ }

	//* layers
	applicationLayer= &application.ApplicationLayer{
		MessagingLayer: ports.MessagingPort(rabbitMQOutboundAdapter),

		BusinessLogicLayer: &businessLogic.BusinessLogicLayer{
			RepositoryLayer: &repository.RepositoryLayer{
				UsersRepository: cockroachDBAdapter,

				CacheRepository: redisAdapter,
			},
		},
	}
)

func main( ) {

	// create channels and declare queues for rabbitMQ based inbound / outbound adapters
	// initiate connections for other outbound adapters

	rabbitMQInboundAdapter.Connect(rabbitMQMainConnection)
	defer rabbitMQInboundAdapter.Disconnect( )

	for _, outboundAdapter := range []outboundAdapters.OutboundAdapter{ cockroachDBAdapter, redisAdapter } {
		outboundAdapter.Connect( )
		defer outboundAdapter.Disconnect( )
	}
	rabbitMQOutboundAdapter.Connect(rabbitMQMainConnection)
	defer rabbitMQOutboundAdapter.Disconnect( )

	// the presentation layer

	go rabbitMQInboundAdapter.StartMessageConsumption( )

	grpcAdapter.StartServer(applicationLayer)
	defer grpcAdapter.StopServer( )
}