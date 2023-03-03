package otp.adapters.outbound.redis;

import java.io.Closeable;

import redis.clients.jedis.Jedis;

public class RedisAdapter implements Closeable {
    private Jedis connection;

    public RedisAdapter( ) {
        this.connection= new Jedis("0.0.0.0");}

    @Override
    public void close( ) {
        if(this.connection != null)
            this.connection.close( );
    }

}