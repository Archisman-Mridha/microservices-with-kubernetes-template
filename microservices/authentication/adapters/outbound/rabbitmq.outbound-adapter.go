package outboundAdapters

import (
	"log"

	"github.com/streadway/amqp"
	"google.golang.org/protobuf/proto"

	"authentication/domain/events"
	"authentication/utils"
)

type RabbitMQOutboundAdapter struct {
	OutboundAdapter

	connection *amqp.Connection
	Channel *amqp.Channel
}

func(instance *RabbitMQOutboundAdapter) Connect( ) {
	var error error

	instance.connection, error= amqp.Dial("amqp://user:password@localhost:5672")
	if error != nil {
		log.Fatal("💀 error connecting to rabbitMQ : ", error.Error( ))}

	instance.Channel, error= instance.connection.Channel( )
	if error != nil {
		log.Fatal("💀 error creating rabbitMQ channel : ", error.Error( ))}

	for _, queueName := range []string{utils.OtpQueueName, utils.ProfileQueueName, utils.AuthenticationQueueName} {
		_, error := instance.Channel.QueueDeclare(
			queueName, false, false, false, false, nil)

		if error != nil {
			log.Fatalf("💀 error declaring queue %s : %s", queueName, error.Error( ))}
	}

	log.Println("🔥 connected to rabbitMQ")
}

func(instance *RabbitMQOutboundAdapter) Disconnect( ) {

	if instance.Channel != nil {
		instance.Channel.Close( )}

	if instance.connection != nil {
		instance.connection.Close( )}
}

func(instance *RabbitMQOutboundAdapter) SendOTP(email string) {

	message, error := proto.Marshal(
		&events.SendOTPRequest{
			MessageType: utils.SendOTP_MessageType,

			Email: email,
		},
	)
	if error != nil {
		log.Println("💀 error marshalling `send otp` request : ", error.Error( ))}

	error= instance.Channel.Publish(
		"", utils.OtpQueueName,
		false, false,
		amqp.Publishing{ Body: message },
	)
	if error != nil {
		log.Println("💀 error publishing `send otp` request to rabbitMQ : ", error.Error( ))}
}

func(instance *RabbitMQOutboundAdapter) CreateProfile(name string, email string) {

	message, error := proto.Marshal(
		&events.CreateProfileRequest{
			MessageType: utils.SendOTP_MessageType,

			Name: name,
			Email: email,
		},
	)
	if error != nil {
		log.Println("💀 error marshalling `create profile` request : ", error.Error( ))}

	error= instance.Channel.Publish(
		"", utils.ProfileQueueName,
		false, false,
		amqp.Publishing{ Body: message },
	)
	if error != nil {
		log.Println("💀 error publishing `create profile` request to rabbitMQ : ", error.Error( ))}
}