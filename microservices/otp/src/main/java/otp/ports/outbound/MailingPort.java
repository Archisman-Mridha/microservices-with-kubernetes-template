package otp.ports.outbound;

public interface MailingPort {
    Boolean sendMail(String email, String content);
}