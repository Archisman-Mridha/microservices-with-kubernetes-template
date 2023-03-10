package rabbitMQInboundAdapter

import (
	"log"

	"github.com/streadway/amqp"
	"google.golang.org/protobuf/proto"

	"authentication/generated/proto/messages"
	"authentication/ports"
	"authentication/types"
	"authentication/utils"
)

type RabbitMQInboundAdapter struct {
	ApplicationLayer ports.ApplicationPort

	Channel *amqp.Channel
}

func(instance *RabbitMQInboundAdapter) CreateChannel(connection *amqp.Connection) {
	var error error

	instance.Channel, error= connection.Channel( )
	if error != nil {
		log.Fatal("💀 error creating rabbitMQ channel : ", error.Error( ))}

	utils.DeclareRabbitMQQueue(instance.Channel, utils.AuthenticationQueueName)

	log.Println("🔥 created rabbitMQ channel for message consumption")
}

func(instance *RabbitMQInboundAdapter) DestroyChannel( ) {
	if instance.Channel != nil {
		instance.Channel.Close( )}
}

func(instance *RabbitMQInboundAdapter) StartMessageConsumption( ) {

	newMessages, error := instance.Channel.Consume(utils.AuthenticationQueueName, "", false, false, false, false, nil)
	if error != nil {
		log.Fatalf("💀 error consuming from queue %s : %s", utils.AuthenticationQueueName, error.Error( ))}

	for message := range newMessages {
		var unmarshalledMessage messages.Message

		error := proto.Unmarshal(message.Body, &unmarshalledMessage)
		if error != nil {
			log.Fatalf("💀 error unmarshalling new message received from queue : %s", error.Error( ))}

		switch unmarshalledMessage.MessageType {

			case utils.RegisterUser_MessageType:
				var request messages.RegisterUserIncomingMessage

				error := proto.Unmarshal(message.Body, &request)
				if error != nil {
					log.Fatalf("💀 error unmarshalling `set temporary user verified` request : %s", error.Error( ))

					message.Ack(false)
					continue
				}

				output := instance.ApplicationLayer.Register(
					&types.RegisterParameters{
						Email: request.Email,
					})

				if output.Error != nil {
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