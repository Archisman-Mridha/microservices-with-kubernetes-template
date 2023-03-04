package otp;

import otp.adapters.inbound.grpc.GrpcAdapter;
import otp.adapters.inbound.rabbitmq.RabbitMQInboundAdapter;
import otp.adapters.outbound.MailingAdapter;
import otp.adapters.outbound.RabbitMQOutboundAdapter;
import otp.adapters.outbound.RedisAdapter;
import otp.application.ApplicationLayer;
import otp.domain.BusinessLogic.BusinessLogicLayer;
import otp.exceptions.AppStartupException;
import otp.utils.RabbitMQConnection;

public class App {
    public static void main(String[ ] args) throws InterruptedException {
        try (
            var rabbitMQConnection= new RabbitMQConnection( );

            //* outbound adapters
            var rabbitMQOutboundAdapter= new RabbitMQOutboundAdapter(rabbitMQConnection.getConnection( ));
            var redisAdapter= new RedisAdapter( );
        ) {

            //* initializing the application and business-logic layers
            var businessLogicLayer= new BusinessLogicLayer(new MailingAdapter( ), redisAdapter);
            var applicationLayer= new ApplicationLayer(businessLogicLayer, rabbitMQOutboundAdapter, redisAdapter);

            try(
                //* inbound adapters
                var rabbitMQInboundAdapter= new RabbitMQInboundAdapter(rabbitMQConnection.getConnection( ), applicationLayer);
            )
            {
                //* the presentation layer

                rabbitMQInboundAdapter.consumeMessages( );

                try {
                    new GrpcAdapter(applicationLayer)
                        .startServer( );
                } catch(InterruptedException error) { }
            }
            catch(AppStartupException exception) {
                System.out.println(exception.getMessage( ));

                System.exit(1);
            }
        }
        catch(AppStartupException exception) {
            System.out.println(exception.getMessage( ));

            System.exit(1);
        }
    }
}