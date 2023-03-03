# Conventions

- In RabbitMQ, if the name of a queue is **profile**, that means, the messages in the queue will be consumed by the **profile microservice**.

- Docker build commands are executed from the root of the project.

- From the perpective of a microservice, if it sends a message to the message-queue, then we call that an `event`. Otherwise it is just called `message`. The microservice consumes `messages` and processes them.

- If we have a command **SendOTP** in a microservice, then at the `presentation` layer, we will have **SendOTPRequest** and **SendOTPResponse**. And at the `appplication` and `business-logic` layers, we will have **SendOTPParameters** and **SendOTPOutput**.