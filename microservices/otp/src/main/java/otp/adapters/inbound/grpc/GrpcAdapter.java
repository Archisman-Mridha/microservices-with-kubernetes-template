package otp.adapters.inbound.grpc;

import java.io.IOException;

import io.grpc.Server;
import io.grpc.netty.NettyServerBuilder;
import io.grpc.protobuf.services.ProtoReflectionService;
import otp.adapters.inbound.grpc.services.OTPGrpcService;
import otp.application.ApplicationLayer;
import otp.exceptions.AppStartupException;

public class GrpcAdapter {
    Server server;

    public GrpcAdapter(ApplicationLayer applicationLayer) {

        this.server= NettyServerBuilder.forPort(4000)
            .addService(new OTPGrpcService(applicationLayer))
            .addService(ProtoReflectionService.newInstance( )) // for gRPC reflection
            .build( );
    }

    public void startServer( ) throws InterruptedException, AppStartupException {
        try {
            System.out.println("🔥 starting gRPC server");
            this.server.start( ).awaitTermination( );
        }
            catch(IOException error) {
                throw new AppStartupException("💀 error starting gRPC server : " + error.getMessage( ));}
    }
}