package otp.adapters.inbound.grpc.services;

import com.otp.generated.proto.VerifyOTPRequest;
import com.otp.generated.proto.VerifyOTPResponse;
import com.otp.generated.proto.OTPGrpc.OTPImplBase;

import io.grpc.stub.StreamObserver;
import otp.ports.inbound.APIPort;
import otp.types.VerifyOTPParameters;

public class OTPGrpcService extends OTPImplBase {
    APIPort applicationLayer;

    public OTPGrpcService(APIPort applicationLayer) {
        super( );

        this.applicationLayer= applicationLayer;
    }

    @Override
    public void verifyOTP(VerifyOTPRequest request, StreamObserver<VerifyOTPResponse> responseObserver) {
        var result= this.applicationLayer.verifyOTP(
            new VerifyOTPParameters(request.getEmail( ), request.getOtp( )));

        responseObserver.onNext(
            VerifyOTPResponse.newBuilder( )
                .setError(result.error( ))
                .build( ));
        responseObserver.onCompleted( );
    }

}