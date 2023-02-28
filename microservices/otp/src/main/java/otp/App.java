package otp;

import java.io.IOException;
import java.util.Collections;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeoutException;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

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

class MailingLayer {

    private Session session;

    public MailingLayer( ) {

        Properties properties= new Properties( );

        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.starttls.enable","true");    
        properties.put("mail.smtp.ssl.protocols", "TLSv1.2");
        properties.put("mail.smtp.port", "587");   

        this.session= Session.getInstance(properties,

            new javax.mail.Authenticator( ) {
                @Override
                protected PasswordAuthentication getPasswordAuthentication( ) {
                    return new javax.mail.PasswordAuthentication("archismanmridha12345@gmail.com", "zuhgbzhlrqnlavsb");
                }
            });
        this.session.setDebug(true);
    }

    public void mail(String address, String content) throws AddressException, MessagingException {
        MimeMessage message= new MimeMessage(session);
        message.setFrom(new InternetAddress("archismanmridha12345@gmail.com"));

        message.setRecipient(Message.RecipientType.TO, new InternetAddress(address));
        message.setSubject("Microservices with Kubernetes Template: OTP Verification");

        message.setText(content);

        Transport.send(message);
    }

    public void sendOTP(String address, int otp) throws AddressException, MessagingException {

        String content=
            String.format(
                """
                    Your verification OTP for `Microservices with Kubernetes template` app is %d.

                    This OTP will be valid for the next 5 minutes.
                """, otp
            );

        this.mail(address, content);
    }
}

class RedisLayer {

    private Jedis jedis;

    public RedisLayer( ) {
        this.jedis= new Jedis("0.0.0.0");
    }

    public Jedis getJedisInstance( ) {
        return this.jedis;
    }

    public void disconnect( ) {
        this.jedis.close( );
    }
}

enum Queues {
    otp
}

class RabbitMQLayer {

    private Connection connection;
    private Channel channel;

    private Thread consumerThread;

    public RabbitMQLayer( ) throws IOException, TimeoutException {

        ConnectionFactory connectionFactory= new ConnectionFactory( );
            connectionFactory.setHost("0.0.0.0");
        this.connection= connectionFactory.newConnection( );

        this.channel= this.connection.createChannel( );
        this.channel.queueDeclare(Queues.otp.toString( ), false, false, false, Collections.emptyMap( ));

        System.out.println("🔥 connected to rabbitMQ");
    }

    public void consume(Jedis jedis, MailingLayer mailingLayer) throws IOException {

        DeliverCallback deliverCallback= (consumerTag, message) -> {

            Boolean acknowledge= true;

            try {
                SendOtpRequest request= SendOtpRequest.parser( ).parseFrom(message.getBody( ));

                Random random= new Random( );
                int otp= random.nextInt(100000, 900000);

                jedis.setex(request.getEmail( ), 5 * 60, String.valueOf(otp));

                mailingLayer.sendOTP(request.getEmail( ), otp);

            } catch(InvalidProtocolBufferException exception) {
                System.out.println("unknown type of message received from rabbitMQ");

            } catch (AddressException e) {
                System.out.println("error parsing recipient email address");

            } catch (MessagingException e) {
                System.out.println("error sending mail to the recipient");

                acknowledge= false;
            }

            this.channel.basicAck(message.getEnvelope( ).getDeliveryTag( ), acknowledge);
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

    public static void main(String[ ] args) throws IOException, InterruptedException, TimeoutException, AddressException, MessagingException {

        //* starting SMTP server
        MailingLayer mailingLayer= new MailingLayer( );

        //* connect to redis
        RedisLayer redisLayer= new RedisLayer( );

        //* connecting to rabbitMQ and consuming messages
        RabbitMQLayer rabbitMQLayer= new RabbitMQLayer( );
        rabbitMQLayer.consume(redisLayer.getJedisInstance( ), mailingLayer);

        //* starting the gRPC server
        Server server= NettyServerBuilder.forPort(4000)
            .addService(new OTPService( ))
            .addService(ProtoReflectionService.newInstance( )) // for gRPC reflection
            .build( );

        System.out.println("🔥 starting gRPC server");

        server.start( ).
            awaitTermination( );

        rabbitMQLayer.disconnect( );
        redisLayer.disconnect( );
    }

}