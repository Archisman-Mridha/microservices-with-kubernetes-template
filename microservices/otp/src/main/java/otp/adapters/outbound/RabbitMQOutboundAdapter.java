package otp.adapters.outbound;

import java.io.Closeable;
import java.io.IOException;
import java.util.Collections;
import java.util.concurrent.TimeoutException;

import com.otp.generated.proto.RegisterUserOutgoingMessage;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import otp.exceptions.AppStartupException;
import otp.ports.outbound.MessagingPort;
import otp.utils.MessageQueues;
import otp.utils.MessageTypes;

public class RabbitMQOutboundAdapter implements Closeable, MessagingPort {
    Channel channel;

    public RabbitMQOutboundAdapter(Connection connection) throws AppStartupException {
        try {
            this.channel= connection.createChannel( );
            this.channel.queueDeclare(MessageQueues.authentication.toString( ), false, false, false, Collections.emptyMap( ));
        }
            catch(IOException error) {
                throw new AppStartupException("error creating rabbitMQ messaging channel : " + error.getMessage( ));}
    }

    public boolean sendMessage(byte[ ] message) {
        try {
            this.channel.basicPublish("", MessageQueues.authentication.toString( ), null, message);
            return true;
        }
            catch(IOException error) {
                System.out.println("error publishing message to rabbitMQ : " + error.getMessage( ));

                return false;
            }
    }

    public boolean registerUser(String email) {
        var message= RegisterUserOutgoingMessage.newBuilder( )
            .setMessageType(MessageTypes.RegisterUser.toString( ))
            .setEmail(email)
            .build( );

        var isSuccessfull= this.sendMessage(message.toByteArray( ));
        return isSuccessfull;
    }

    @Override
    public void close( ) {
        try {
            if(this.channel != null)
                this.channel.close( );

        } catch(IOException | TimeoutException exception) { }
    }

}