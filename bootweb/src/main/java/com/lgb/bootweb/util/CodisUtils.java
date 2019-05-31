package com.lgb.bootweb.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.lgb.bootweb.service.redisServer.Redis;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

public class CodisUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(CodisUtils.class);
    private Random random = new Random();

    private static long defaultExpireTimeSecond = 86400L;

    private static long default36HourExpireTimeSecond = 129600L;

    private static long defaultOneWeekTimeSecond = 604800L;

    private static long defaultOneMonthTimeSecond = 2592000L;

    private static long timeSecond = 15L;
    private Redis redis;
    private String prefix;

    public CodisUtils() {
    }

    public CodisUtils(Redis redis, String prefix) {
        this.redis = redis;
        this.prefix = prefix;
    }

    public void setObjectByDay(String key, Object obj) {
        try {
            this.redis.setex(this.prefix + key, defaultExpireTimeSecond + this.random.nextInt(100), obj);
        } catch (Exception ex) {
            LOGGER.info("setObjectByDay异常:", ex);
        }
    }

    public void setObjectBy36Hour(String key, Object obj) {
        try {
            this.redis.setex(this.prefix + key, default36HourExpireTimeSecond + this.random.nextInt(100), obj);
        } catch (Exception ex) {
            LOGGER.info("setObjectBy36Hour异常:", ex);
        }
    }

    public void setObjectByOneWeek(String key, Object obj) {
        try {
            this.redis.setex(this.prefix + key, defaultOneWeekTimeSecond + this.random.nextInt(100), obj);
        } catch (Exception ex) {
            LOGGER.info("setObjectByOneWeek异常:", ex);
        }
    }

    public void setObjectByOneMonth(String key, Object obj) {
        try {
            this.redis.setex(this.prefix + key, defaultOneMonthTimeSecond + this.random.nextInt(100), obj);
        } catch (Exception ex) {
            LOGGER.info("setObjectByOneMonth异常:", ex);
        }
    }

    //我自己加的一个自定义的过期时间
    public void setObjectByTime(String key, Long time, Object obj) {
        try {
            this.redis.setex(this.prefix + key, time, obj);
        } catch (Exception ex) {
            LOGGER.info("setObjectByOneMonth异常:", ex);
        }
    }


    public void setex(String key, String value, long seconds) {
        try {
            this.redis.setex(this.prefix + key, seconds, value);
        } catch (Exception ex) {
            LOGGER.info("获取redis信息异常:", ex);
        }
    }

    public void set(String key, String value) {
        try {
            this.redis.set(this.prefix + key, value);
        } catch (Exception ex) {
            LOGGER.info("redis.set异常:", ex);
        }
    }

    public void delete(String key) {
        try {
            this.redis.delete(this.prefix + key);
        } catch (Exception ex) {
            LOGGER.info("删除redis信息异常:", ex);
        }
    }

    //获取redis中存储的字符串类型
    public String getString(String key) {
        return (String) this.redis.get(
                this.prefix + key, String.class);
    }

    public <T> T getObject(String key, Class<T> clazz) {
        Object object = null;
        try {
            object = this.redis.get(this.prefix + key, clazz);
        } catch (Exception ex) {
            LOGGER.info("获取redis信息异常:", ex);
        }
        return (T) object;
    }

    public <T> T getObject(String key, TypeReference<T> clazz) {
        Object object = null;
        try {
            object = this.redis.get(this.prefix + key, clazz);
        } catch (Exception ex) {
            LOGGER.info("获取redis信息异常:", ex);
        }
        return (T) object;
    }


}
