package otp.exceptions;

public class AppStartupException extends Exception {
    public AppStartupException(String errorMessage) {
        super(errorMessage);
    }
}