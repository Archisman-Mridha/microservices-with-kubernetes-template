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

	Channel *amqp.Channel
}

func(instance *RabbitMQOutboundAdapter) CreateChannel(connection *amqp.Connection) {
	var error error

	instance.Channel, error= connection.Channel( )
	if error != nil {
		log.Fatal("💀 error creating rabbitMQ channel : ", error.Error( ))}

	for _, queueName := range []string{utils.OtpQueueName, utils.ProfileQueueName} {
		utils.DeclareRabbitMQQueue(instance.Channel, queueName)}

	log.Println("🔥 created rabbitMQ channel for sending messages")
}

func(instance *RabbitMQOutboundAdapter) DestroyChannel( ) {
	if instance.Channel != nil {
		instance.Channel.Close( )}
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