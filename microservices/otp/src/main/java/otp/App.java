package otp;

import java.io.IOException;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.TimeoutException;

import com.google.protobuf.InvalidProtocolBufferException;
import com.otp.generated.proto.SendOtpRequest;
import com.otp.generated.proto.VerifyOtpRequest;
import com.otp.generated.proto.VerifyOtpResponse;
import com.otp.generated.proto.OtpGrpc.OtpImplBase;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import io.grpc.Server;
import io.grpc.netty.NettyServerBuilder;
import io.grpc.protobuf.services.ProtoReflectionService;
import io.grpc.stub.StreamObserver;
import redis.clients.jedis.Jedis;

class MailingUtils {

    public static void mail(String address, String content) {

        // TODO: implement mailing logic

    }

    public static void sendOTP(String address, int otp) {

        String content=
            String.format(
                """
                    Your verification OTP for `microservices with kubernetes template` app is %d.

                    This OTP will be valid for the next 5 minutes.
                """, otp
            );

        MailingUtils.mail(address, content);
    }
}

class RedisUtils {

    private Jedis jedis;

    public void connect( ) {
        this.jedis= new Jedis("0.0.0.0");
    }

    public void disconnect( ) {
        this.jedis.close( );
    }
}

enum Queues {
    otp
}

class RabbitMQUtils {

    private Connection connection;
    private Channel channel;

    private Thread consumerThread;

    public void connect( ) throws IOException, TimeoutException {

        ConnectionFactory connectionFactory= new ConnectionFactory( );
            connectionFactory.setHost("0.0.0.0");
        this.connection= connectionFactory.newConnection( );

        this.channel= this.connection.createChannel( );
        this.channel.queueDeclare(Queues.otp.toString( ), false, false, false, Collections.emptyMap( ));

        System.out.println("🔥 connected to rabbitMQ");
    }

    public void consume(Jedis jedis) throws IOException {

        DeliverCallback deliverCallback= (consumerTag, message) -> {

            try {
                SendOtpRequest request= SendOtpRequest.parser( ).parseFrom(message.getBody( ));

                Random random= new Random( );
                int otp= random.nextInt(100000, 900000);

                jedis.setex(request.getEmail( ), 5 * 60, String.valueOf(otp));

                MailingUtils.sendOTP(request.getEmail( ), otp);

                this.channel.basicAck(message.getEnvelope( ).getDeliveryTag( ), true);

            } catch(InvalidProtocolBufferException exception) {
                System.out.println("unknown type of message received from rabbitMQ");

                this.channel.basicAck(message.getEnvelope( ).getDeliveryTag( ), false);
            }
        };

        this.consumerThread= new Thread(
            ( ) -> {
                while(!this.consumerThread.isInterrupted( )) {
                    int retryCount= 0;

                    do {
                        try {
                            this.channel.basicConsume(Queues.otp.toString( ), false, deliverCallback, consumerTag -> { });
        
                        } catch (IOException exception) {
                            System.out.println("💀 error occured during message consumption from rabbitMQ");

                            retryCount++;
                        }
                    } while(retryCount < 5);

                    System.exit(1);
                }
            }
        );

        consumerThread.start( );
    }

    public void disconnect( ) throws IOException, TimeoutException {
        this.consumerThread.interrupt( );

        this.channel.close( );
        this.connection.close( );
    }
}

class OTPService extends OtpImplBase {

    @Override
    public void verifyOtp(VerifyOtpRequest request, StreamObserver<VerifyOtpResponse> responseObserver) {

        // TODO: Auto-generated method stub
        super.verifyOtp(request, responseObserver);
    }
}

public class App {

    public static void main(String[ ] args) throws IOException, InterruptedException, TimeoutException {

        RedisUtils redisUtils= new RedisUtils( );
        RabbitMQUtils rabbitMQUtils= new RabbitMQUtils( );

        try {

            //* connect to redis
            redisUtils.connect( );

            //* connecting to rabbitMQ and consuming messages
            rabbitMQUtils.connect( );

            //* starting the gRPC server
            Server server= NettyServerBuilder.forPort(4000)
                .addService(new OTPService( ))
                .addService(ProtoReflectionService.newInstance( )) // for gRPC reflection
                .build( );

            System.out.println("🔥 starting gRPC server");

            server.start( ).
                awaitTermination( );

        } finally {
            rabbitMQUtils.disconnect( );
            redisUtils.disconnect( );
        }
    }

}