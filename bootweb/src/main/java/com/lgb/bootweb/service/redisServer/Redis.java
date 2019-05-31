package com.lgb.bootweb.service.redisServer;

import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.data.redis.connection.MessageListener;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface Redis {
    public void set(String paramString, Object paramObject);

    public Boolean setnx(String paramString, Object paramObject);

    public void setex(String paramString, long paramLong, Object paramObject);

    public void hset(String paramString1, String paramString2, Object paramObject);

    public <T> T getSet(String paramString, TypeReference<T> paramTypeReference);

    public <T> T getSet(String paramString, Class<T> paramClass);

    public <T> T get(String paramString, TypeReference<T> paramTypeReference);

    public <T> T get(String paramString, Class<T> paramClass);

    public <T> T hgetObject(String paramString1, String paramString2, TypeReference<T> paramTypeReference);

    public <T> T hgetObject(String paramString1, String paramString2, Class<T> paramClass);

    public Long expire(String paramString, long paramLong);

    public void delete(String paramString);

    public void hdel(String paramString1, String paramString2);

    public Long lpush(String paramString, Object paramObject);

    public Long rpush(String paramString, Object paramObject);

    public String lpop(String paramString);

    public <T> T lpop(String paramString, Class<T> paramClass);

    public String rpop(String paramString);

    public <T> T rpop(String paramString, Class<T> paramClass);

    public void publish(String paramString1, String paramString2);

    public void subscribe(MessageListener paramMessageListener, byte[][] paramArrayOfByte);

    public <T> T getSet(String paramString, Object paramObject, Class<T> paramClass);

    public Long incr(String paramString);

    public Long llen(String paramString);

    public Set<String> hKeys(String paramString);

    public Double zincrby(String paramString1, Double paramDouble, String paramString2);

    public Double zscore(String paramString1, String paramString2);

    public Long zrevrank(String paramString1, String paramString2);

    public List<String> zrevrange(String paramString, long paramLong1, long paramLong2);

    public Map<String, Double> zrangeWithScores(String paramString, long paramLong1, long paramLong2);

    public Long zunionstore(String paramString, String[] paramArrayOfString);

    public Long zcard(String paramString);

    public Long zadd(String paramString1, Double paramDouble, String paramString2);

    public Long zrank(String paramString1, String paramString2);

    public Map<String, Double> zrevrangeWithScores(String paramString, long paramLong1, long paramLong2);

    public Long zrem(String paramString, byte[][] paramArrayOfByte);

    public Long sadd(String paramString, byte[][] paramArrayOfByte);
}
