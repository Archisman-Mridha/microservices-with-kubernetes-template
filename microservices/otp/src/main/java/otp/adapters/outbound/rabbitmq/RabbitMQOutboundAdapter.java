package otp.adapters.outbound.rabbitmq;

import java.io.Closeable;
import java.io.IOException;
import java.util.Collections;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import otp.exceptions.AppStartupException;
import otp.utils.MessageQueues;

public class RabbitMQOutboundAdapter implements Closeable {
    Channel channel;

    public RabbitMQOutboundAdapter(Connection connection) throws AppStartupException {
        try {
            this.channel= connection.createChannel( );
            this.channel.queueDeclare(MessageQueues.authentication.toString( ), false, false, false, Collections.emptyMap( ));
        }
            catch(IOException error) {
                throw new AppStartupException("error creating rabbitMQ messaging channel : " + error.getMessage( ));}
    }

    public void setEmailVerified(String email) { }

    @Override
    public void close( ) {
        try {
            if(this.channel != null)
                this.channel.close( );

        } catch(IOException | TimeoutException exception) { }
    }

}