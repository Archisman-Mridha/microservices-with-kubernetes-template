package otp.adapters.inbound.rabbitmq;

import java.io.Closeable;
import java.io.IOException;
import java.util.Collections;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReference;

import com.google.protobuf.InvalidProtocolBufferException;
import com.otp.generated.proto.Message;
import com.otp.generated.proto.SendOTPIncomingMessage;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;

import otp.exceptions.AppStartupException;
import otp.ports.inbound.APIPort;
import otp.types.SendOTPOutput;
import otp.types.SendOTPParameters;
import otp.utils.MessageQueues;

public class RabbitMQInboundAdapter implements Closeable {
    Channel channel;
    Thread workerThread;

    APIPort applicationLayer;

    public RabbitMQInboundAdapter(Connection connection, APIPort applicationLayer) throws AppStartupException {
        try {
            this.channel= connection.createChannel( );
            this.channel.queueDeclare(MessageQueues.otp.toString( ), false, false, false, Collections.emptyMap( ));
        }
            catch(IOException error) {
                throw new AppStartupException("error creating rabbitMQ consumption channel : " + error.getMessage( ));}

        this.applicationLayer= applicationLayer;
    }

    DeliverCallback processMessage= (consumerTag, message) -> {
        Boolean acknowledge= true;

        try {
            Message genericRequest= Message.parser( ).parseFrom(message.getBody( ));

            // TODO: use values from `MessageTypes` enum instead for matching
            switch(genericRequest.getMessageType( )) {

                case "SendOTP":
                    var request= SendOTPIncomingMessage.parser( ).parseFrom(message.getBody( ));

                    SendOTPOutput result=
                        this.applicationLayer.sendOTP(new SendOTPParameters(
                            request.getEmail( )
                        ));

                    acknowledge= result.isEmailSent( );

                    break;

                default:
                    System.out.println("unknown type of message received from rabbitMQ");
                    acknowledge= true;
            }
        }
            catch(InvalidProtocolBufferException exception) {
                System.out.println("error parsing rabbitMQ message");}

        this.channel.basicAck(message.getEnvelope( ).getDeliveryTag( ), acknowledge);
    };

    public void consumeMessages( ) throws AppStartupException {
        AtomicReference<IOException> errorInWorkerThread= new AtomicReference<IOException>(null);

        this.workerThread= new Thread(
            ( ) -> {
                while(this.workerThread.isInterrupted( )) {
                    try {
                        this.channel.basicConsume(MessageQueues.otp.toString( ), false, processMessage, consumerTag -> { });
                    }
                        catch(IOException error) { errorInWorkerThread.set(error); }
                }
            }
        );

        if(errorInWorkerThread != null)
            throw new AppStartupException("error trying to consume messages from `otp` queue : " + errorInWorkerThread.get( ).getMessage( ));
    }

    @Override
    public void close( ) {
        try {
            if(this.channel != null)
                this.channel.close( );

        } catch(IOException | TimeoutException exception) { }
    }

}