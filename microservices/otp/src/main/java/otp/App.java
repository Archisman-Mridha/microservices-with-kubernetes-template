package otp;

import java.io.IOException;

import io.grpc.Server;
import io.grpc.netty.NettyServerBuilder;
import io.grpc.protobuf.services.ProtoReflectionService;

public class App {

    public static void main(String[ ] args) throws IOException, InterruptedException {

        Server server= NettyServerBuilder.forPort(4000)
            .addService(ProtoReflectionService.newInstance( )) // for gRPC reflection
            .build( );

        System.out.println("🔥 starting gRPC server");

        server.start( ).
            awaitTermination( );
    }

}