package utils

import (
	"log"

	"github.com/streadway/amqp"
)

func CreateRabbitMQConnection( ) *amqp.Connection {

	connection, error := amqp.Dial("amqp://user:password@localhost:5672")
	if error != nil {
		log.Fatal("💀 error connecting to rabbitMQ : ", error.Error( ))}

	log.Println("🔥 connected to rabbitMQ")

	return connection
}

func DeclareRabbitMQQueue(channel *amqp.Channel, queueName string) {
	_, error := channel.QueueDeclare(
		queueName, false, false, false, false, nil)

	if error != nil {
		log.Fatalf("💀 error declaring queue %s : %s", queueName, error.Error( ))}
}