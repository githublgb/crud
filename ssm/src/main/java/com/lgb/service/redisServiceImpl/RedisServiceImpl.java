package com.lgb.service.redisServiceImpl;


import com.lgb.service.redisService.Redis;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

public class RedisServiceImpl implements Redis {

     private StringRedisTemplate stringRedisTemplate;
     private StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();

    protected <T> T execute(RedisCallback<T> action)
    {
        return this.stringRedisTemplate.execute(action);
    }

    @Override
    public void set(String paramString, Object paramObject) {


    }

    @Override
    public Boolean setnx(String paramString, Object paramObject) {

        return false;
    }
}
