package otp.application;

import otp.domain.BusinessLogic.BusinessLogicLayer;
import otp.ports.inbound.APIPort;
import otp.types.SendOTPOutput;
import otp.types.SendOTPParameters;
import otp.types.VerifyOTPOutput;
import otp.types.VerifyOTPParameters;

public class ApplicationLayer implements APIPort {
    BusinessLogicLayer businessLogicLayer;

    public ApplicationLayer(BusinessLogicLayer businessLogicLayer) {
        this.businessLogicLayer= businessLogicLayer;
    }

    @Override
    public SendOTPOutput sendOTP(SendOTPParameters request) {
        return this.businessLogicLayer.sendOTP(request);}

    @Override
    public VerifyOTPOutput verifyOTP(VerifyOTPParameters request) {
        var result= this.businessLogicLayer.verifyOTP(request);

        // TODO: request authentication service to set the temporary user verified in its cache

        return result;
    }

}