package rabbitMQInboundAdapter

import (
	"log"

	"github.com/streadway/amqp"
	"google.golang.org/protobuf/proto"

	protocGenerated "authentication/generated/proto/messages"
	"authentication/ports"
	"authentication/types"
	"authentication/utils"
)

type RabbitMQInboundAdapter struct {
	ApplicationLayer ports.ApplicationPort
}

func(instance *RabbitMQInboundAdapter) StartMessageConsumption(channel *amqp.Channel) {

	newMessages, error := channel.Consume(utils.AuthenticationQueueName, "", false, false, false, false, nil)
	if error != nil {
		log.Fatalf("💀 error consuming from queue %s : %s", utils.AuthenticationQueueName, error.Error( ))}

	for message := range newMessages {
		var unmarshalledMessage protocGenerated.Message

		error := proto.Unmarshal(message.Body, &unmarshalledMessage)
		if error != nil {
			log.Fatalf("💀 error unmarshalling new message received from queue : %s", error.Error( ))}

		switch unmarshalledMessage.MessageType {

			case utils.SetTemporaryUserVerified_MessageType:
				var request protocGenerated.SetTemporaryUserVerifiedRequest

				error := proto.Unmarshal(message.Body, &request)
				if error != nil {
					log.Fatalf("💀 error unmarshalling `set temporary user verified` request : %s", error.Error( ))

					message.Ack(false)
					continue
				}

				result := instance.ApplicationLayer.SetTemporaryUserVerified(
					&types.SetTemporaryUserVerifiedRequest{
						Email: request.Email,
					})

				if result.Error != nil {
					message.Ack(false); continue}

				message.Ack(true)

			default:
				log.Printf("unknown message of type %s received", unmarshalledMessage.MessageType)
		}
	}
}

func CreateRabbitMQInboundAdapter(applicationLayer ports.ApplicationPort) *RabbitMQInboundAdapter {
	return &RabbitMQInboundAdapter{
		ApplicationLayer: applicationLayer,
	}
}