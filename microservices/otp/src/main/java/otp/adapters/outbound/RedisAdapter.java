package otp.adapters.outbound;

import java.io.Closeable;

import otp.ports.outbound.CacheRepositoryPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.SetParams;

public class RedisAdapter implements Closeable, CacheRepositoryPort {
    private Jedis connection;

    public RedisAdapter( ) {
        this.connection= new Jedis("0.0.0.0");}

    public Boolean saveOTP(String email, String otp) {
        String result= this.connection.set(email, otp,
            new SetParams( ).ex(60 * 10));

        if(result == "OK")
            return true;

        System.out.println("error saving email-otp mapping in redis");
        return false;
    }

    public String fetchOTP(String email) {
        String otp= this.connection.get(email);

        if(otp != null)
            return null;

        var isOTPExpired= this.connection.exists(email);
        if(isOTPExpired)
            return "otp expired";

        else return "registration process not started for this email";
    }

    public void expireOTP(String email) {
        this.connection.del(email);}

    @Override
    public void close( ) {
        if(this.connection != null)
            this.connection.close( );
    }

}