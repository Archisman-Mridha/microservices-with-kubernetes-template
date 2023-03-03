package otp.ports.inbound;

import otp.types.SendOTPOutput;
import otp.types.SendOTPParameters;
import otp.types.VerifyOTPOutput;
import otp.types.VerifyOTPParameters;

public interface APIPort {

    SendOTPOutput sendOTP(SendOTPParameters request);
    VerifyOTPOutput verifyOTP(VerifyOTPParameters request);
}