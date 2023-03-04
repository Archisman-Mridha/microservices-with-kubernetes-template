package otp.domain.BusinessLogic;

import otp.ports.inbound.APIPort;
import otp.ports.outbound.CacheRepositoryPort;
import otp.ports.outbound.MailingPort;
import otp.types.SendOTPOutput;
import otp.types.SendOTPParameters;
import otp.types.VerifyOTPOutput;
import otp.types.VerifyOTPParameters;
import otp.utils.OTPGenerator;

public record BusinessLogicLayer(

    MailingPort mailingLayer,
    CacheRepositoryPort cacheRepository

) implements APIPort {

    public SendOTPOutput sendOTP(SendOTPParameters request) {
        var otp= OTPGenerator.generateOTP( );

        var isCached= this.cacheRepository.saveOTP(request.email( ), otp);
        if(!isCached)
            return new SendOTPOutput(false);

        var content=
            String.format(
                """
                    Your verification OTP for `Microservices with Kubernetes template` app is %d.

                    This OTP will be valid for the next 5 minutes.
                """, otp
            );
        var isEmailSent= mailingLayer.sendMail(request.email( ), content);

        return new SendOTPOutput(isEmailSent);
    }

    public VerifyOTPOutput verifyOTP(VerifyOTPParameters request) {
        String error= this.cacheRepository.fetchOTP(request.email( ));

        return new VerifyOTPOutput(error);
    }

}