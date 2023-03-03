package otp.utils;

import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import otp.exceptions.AppStartupException;

public class RabbitMQConnection implements Closeable {
    Connection connection;

    public RabbitMQConnection( ) throws AppStartupException {
        try {
            ConnectionFactory connectionFactory= new ConnectionFactory( );
                connectionFactory.setHost("0.0.0.0");

            connectionFactory.newConnection( );
        }
            catch(IOException | TimeoutException error) {
                throw new AppStartupException("💀 error connecting to rabbitMQ : " + error.getMessage( ));}
    }

    public Connection getConnection( ) {
        return this.connection;
    }

    @Override
    public void close( ) {
        try {
            if(this.connection != null)
                this.connection.close( );

        } catch(IOException error) { }
    }

}