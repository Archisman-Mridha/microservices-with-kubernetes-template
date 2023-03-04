package otp.utils;

import java.util.Random;

public class OTPGenerator {

    public static String generateOTP( ) {
        var random= new Random( );
        int otp= random.nextInt(100000, 900000);

        return String.valueOf(otp);
    }
}