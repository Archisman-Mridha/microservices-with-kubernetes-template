package main

import (
	_ "github.com/lib/pq"

	inboundAdapters "authentication/adapters/inbound"
	outboundAdapters "authentication/adapters/outbound"
	"authentication/application"
	businessLogic "authentication/domain/business-logic"
	"authentication/domain/repository"
	"authentication/ports"
)

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

	// initiate connections for outbound adapters

	outboundAdapters := []outboundAdapters.OutboundAdapter{

		cockroachDBAdapter,
		redisAdapter,
		rabbitMQOutboundAdapter,
	}

	for _, outboundAdapter := range outboundAdapters {
		outboundAdapter.Connect( )

		defer outboundAdapter.Disconnect( )
	}

	// the presentation layer

	go rabbitMQInboundAdapter.StartMessageConsumption(rabbitMQOutboundAdapter.Channel)

	grpcAdapter.StartServer(applicationLayer)
	defer grpcAdapter.StopServer( )
}