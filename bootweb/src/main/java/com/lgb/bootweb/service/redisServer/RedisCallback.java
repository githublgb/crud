package com.lgb.bootweb.service.redisServer;

import redis.clients.jedis.Jedis;

public abstract interface RedisCallback<T>
{
    public abstract T execute(Jedis paramJedis);
}