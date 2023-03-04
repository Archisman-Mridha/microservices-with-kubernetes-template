package otp.application;

import otp.domain.BusinessLogic.BusinessLogicLayer;
import otp.ports.inbound.APIPort;
import otp.ports.outbound.CacheRepositoryPort;
import otp.ports.outbound.MessagingPort;
import otp.types.SendOTPOutput;
import otp.types.SendOTPParameters;
import otp.types.VerifyOTPOutput;
import otp.types.VerifyOTPParameters;

public record ApplicationLayer(

    BusinessLogicLayer businessLogicLayer,
    MessagingPort messagingLayer,
    CacheRepositoryPort cacheRepository

) implements APIPort {

    @Override
    public SendOTPOutput sendOTP(SendOTPParameters request) {
        return this.businessLogicLayer.sendOTP(request);}

    @Override
    public VerifyOTPOutput verifyOTP(VerifyOTPParameters request) {
        var result= this.businessLogicLayer.verifyOTP(request);

        if(result.error( ) == null) {
            // TODO: communicate to authentication microservice in synchronous manner
            var isMessageSendingSuccessfull= this.messagingLayer.registerUser(request.email( ));

            if(!isMessageSendingSuccessfull)
                return new VerifyOTPOutput("server error occured");

            this.cacheRepository.expireOTP(request.email( ));
        }

        return result;
    }

}