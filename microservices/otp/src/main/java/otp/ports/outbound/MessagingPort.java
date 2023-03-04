package otp.ports.outbound;

public interface MessagingPort {

    boolean registerUser(String email);
}