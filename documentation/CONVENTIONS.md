# Conventions

- In RabbitMQ, if the name of a queue is **profile**, that means, the messages in the queue will be consumed by the **profile microservice**.

- Docker build commands are executed from the root of the project.

- If we have a command **SendOTP** in a microservice, then at the `presentation` layer, we will have **SendOTPRequest** and **SendOTPResponse**. And at the `appplication` and `business-logic` layers, we will have **SendOTPParameters** and **SendOTPOutput**.

- Messages are exchanged between microservices via message brokers. Every message should have a field `messageType` representing the type of the message. `messageType` should follow camel casing and start with capital letter.

- The registration process should be finished within 10 minutes.