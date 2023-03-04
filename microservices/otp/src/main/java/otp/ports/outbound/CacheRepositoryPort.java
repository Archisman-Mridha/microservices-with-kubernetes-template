package otp.ports.outbound;

public interface CacheRepositoryPort {

    Boolean saveOTP(String email, String otp);
    String fetchOTP(String email);
    void expireOTP(String email);
}