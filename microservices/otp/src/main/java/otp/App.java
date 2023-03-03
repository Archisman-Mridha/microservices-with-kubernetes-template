package otp;

import otp.adapters.inbound.grpc.GrpcAdapter;
import otp.adapters.inbound.rabbitmq.RabbitMQInboundAdapter;
import otp.adapters.outbound.rabbitmq.RabbitMQOutboundAdapter;
import otp.adapters.outbound.redis.RedisAdapter;
import otp.application.ApplicationLayer;
import otp.domain.BusinessLogic.BusinessLogicLayer;
import otp.exceptions.AppStartupException;
import otp.utils.RabbitMQConnection;

public class App {
    public static void main(String[ ] args) throws InterruptedException {

        //* initializing the application and business-logic layers
        var businessLogicLayer= new BusinessLogicLayer( );
        var applicationLayer= new ApplicationLayer(businessLogicLayer);

        try(
            var rabbitMQConnection= new RabbitMQConnection( );

            var rabbitMQInboundAdapter= new RabbitMQInboundAdapter(rabbitMQConnection.getConnection( ), applicationLayer);

            var rabbitMQOutboundAdapter= new RabbitMQOutboundAdapter(rabbitMQConnection.getConnection( ));
            var redisAdapter= new RedisAdapter( );
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
}