package com.lgb.bootweb.service.redisCodisImpl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.lgb.bootweb.serializer.JacksonRedisSerializer;
import com.lgb.bootweb.service.redisServer.Redis;
import com.lgb.bootweb.service.redisServer.RedisCallback;
import com.lgb.bootweb.util.RedisFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.MessageListener;
import redis.clients.jedis.Jedis;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CodisImpl implements Redis {

    private static final Logger logger = LoggerFactory.getLogger(CodisImpl.class);
    private RedisFactory redisFactory;
    private JacksonRedisSerializer jacksonRedisSerializer = new JacksonRedisSerializer();

    private String salt;

    protected <T> T execute(RedisCallback<T> callback) {
        // AssertUtil.assertNotNull(this.redisFactory, "redis factory not set");
        try {
            Object localObject1 = null;
            Object localObject4 = null;
            Object localObject3;
            try {
                Jedis jedis = this.redisFactory.getResource();
                try {
                    return callback.execute(jedis);
                } finally {
                    if (jedis != null)
                        jedis.close();
                }
            } finally {
                // if (localObject2 == null) localObject3 = localThrowable;
                // else if (localObject3 != localThrowable) localObject3.addSuppressed(localThrowable);
            }
        } catch (Exception ex) {
            logger.error("codis execute error", ex);
        }

        return null;
    }

    @Override
    public void set(final String key, final Object value) {
        execute(new RedisCallback() {
            public String execute(Jedis jedis) {
                jedis.set(CodisImpl.this.jacksonRedisSerializer.serialize(key), CodisImpl.this.jacksonRedisSerializer.serialize(value));
                return null;
            }
        });
    }


    @Override
    public Boolean setnx(String paramString, Object paramObject) {
        return null;
    }

    @Override
    public void setex(String key, final long seconds, final Object value) {
        execute(new RedisCallback() {
            public Void execute(Jedis jedis) {
                Number secondsNum = Long.valueOf(seconds);
                jedis.setex(CodisImpl.this.jacksonRedisSerializer.serialize(key), secondsNum.intValue(), CodisImpl.this.jacksonRedisSerializer.serialize(value));
                return null;
            }
        });
    }

    @Override
    public void hset(String paramString1, String paramString2, Object paramObject) {

    }

    @Override
    public <T> T getSet(String paramString, TypeReference<T> paramTypeReference) {
        return null;
    }

    @Override
    public <T> T getSet(String paramString, Class<T> paramClass) {
        return null;
    }

    @Override
    public <T> T get(String paramString, TypeReference<T> paramTypeReference) {
        return null;
    }

    @Override
    public <T> T get(String key, Class<T> clazz) {
        return (T) execute(new RedisCallback() {
            public T execute(Jedis jedis) {
                byte[] bytes = jedis.get(CodisImpl.this.jacksonRedisSerializer.serialize( key));
                return CodisImpl.this.jacksonRedisSerializer.deserialize(bytes, clazz);
            }
        });
    }

    @Override
    public <T> T hgetObject(String paramString1, String paramString2, TypeReference<T> paramTypeReference) {
        return null;
    }

    @Override
    public <T> T hgetObject(String paramString1, String paramString2, Class<T> paramClass) {
        return null;
    }

    @Override
    public Long expire(String paramString, long paramLong) {
        return null;
    }

    @Override
    public void delete(String paramString) {

    }

    @Override
    public void hdel(String paramString1, String paramString2) {

    }

    @Override
    public Long lpush(String paramString, Object paramObject) {
        return null;
    }

    @Override
    public Long rpush(String paramString, Object paramObject) {
        return null;
    }

    @Override
    public String lpop(String paramString) {
        return null;
    }

    @Override
    public <T> T lpop(String paramString, Class<T> paramClass) {
        return null;
    }

    @Override
    public String rpop(String paramString) {
        return null;
    }

    @Override
    public <T> T rpop(String paramString, Class<T> paramClass) {
        return null;
    }

    @Override
    public void publish(String paramString1, String paramString2) {

    }

    @Override
    public void subscribe(MessageListener paramMessageListener, byte[][] paramArrayOfByte) {

    }

    @Override
    public <T> T getSet(String paramString, Object paramObject, Class<T> paramClass) {
        return null;
    }

    @Override
    public Long incr(String paramString) {
        return null;
    }

    @Override
    public Long llen(String paramString) {
        return null;
    }

    @Override
    public Set<String> hKeys(String paramString) {
        return null;
    }

    @Override
    public Double zincrby(String paramString1, Double paramDouble, String paramString2) {
        return null;
    }

    @Override
    public Double zscore(String paramString1, String paramString2) {
        return null;
    }

    @Override
    public Long zrevrank(String paramString1, String paramString2) {
        return null;
    }

    @Override
    public List<String> zrevrange(String paramString, long paramLong1, long paramLong2) {
        return null;
    }

    @Override
    public Map<String, Double> zrangeWithScores(String paramString, long paramLong1, long paramLong2) {
        return null;
    }

    @Override
    public Long zunionstore(String paramString, String[] paramArrayOfString) {
        return null;
    }

    @Override
    public Long zcard(String paramString) {
        return null;
    }

    @Override
    public Long zadd(String paramString1, Double paramDouble, String paramString2) {
        return null;
    }

    @Override
    public Long zrank(String paramString1, String paramString2) {
        return null;
    }

    @Override
    public Map<String, Double> zrevrangeWithScores(String paramString, long paramLong1, long paramLong2) {
        return null;
    }

    @Override
    public Long zrem(String paramString, byte[][] paramArrayOfByte) {
        return null;
    }

    @Override
    public Long sadd(String paramString, byte[][] paramArrayOfByte) {
        return null;
    }

    public RedisFactory getRedisFactory() {
        return redisFactory;
    }

    public void setRedisFactory(RedisFactory redisFactory) {
        this.redisFactory = redisFactory;
    }
}
