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

var rabbitMQConnection= utils.CreateRabbitMQConnection( )

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
	rabbitMQInboundAdapter.CreateChannel(rabbitMQConnection)
	defer rabbitMQInboundAdapter.DestroyChannel( )
	rabbitMQOutboundAdapter.CreateChannel(rabbitMQConnection)
	defer rabbitMQOutboundAdapter.DestroyChannel( )

	// initiate connections for other outbound adapters
	for _, outboundAdapter := range []outboundAdapters.OutboundAdapter{ cockroachDBAdapter, redisAdapter } {
		outboundAdapter.Connect( )
		defer outboundAdapter.Disconnect( )
	}

	// the presentation layer

	go rabbitMQInboundAdapter.StartMessageConsumption( )

	grpcAdapter.StartServer(applicationLayer)
	defer grpcAdapter.StopServer( )
}