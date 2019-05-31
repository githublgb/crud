package com.lgb.bootweb.util;

import io.codis.jodis.JedisResourcePool;
import io.codis.jodis.RoundRobinJedisPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import redis.clients.jedis.Jedis;

public class RedisFactory implements InitializingBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisFactory.class);
    private JedisResourcePool pool;//连接池
    private String zks;
    private int zkSessionTimeout;
    private static final int DEFAULT_ZK_SESSIONTIMEOUT = 30000;
    private String authpassword;
    private String zkProxyDir;
    private int timeout;
    private static final int DEFAULT_TIMEOUT = 1000;

    public RedisFactory() {
    }

    public RedisFactory(String zks, String zkProxyDir) {
        this.zks = zks;
        this.zkProxyDir = zkProxyDir;
    }

    /**
     * 连接池
     *
     * @return
     */
    public JedisResourcePool getPool() {
        if (this.pool == null) {
            synchronized (RedisFactory.class) {
                if (this.pool == null) {
                    initPool();
                }
            }
        }
        return this.pool;
    }

    /**
     * 获取连接redis的连接
     *
     * @return
     */
    public Jedis getResource() {
        return getPool().getResource();
    }

    /**
     * 初始化连接池
     */
    private void initPool() {
        if (this.pool == null)
            if (this.authpassword != null) {
                this.pool = RoundRobinJedisPool.create().curatorClient(this.zks, this.zkSessionTimeout != 0 ? this.zkSessionTimeout : 30000).timeoutMs(this.timeout != 0 ? this.timeout : 1000).zkProxyDir(this.zkProxyDir).password(this.authpassword).build();
                LOGGER.debug("init codis connection pool by password success.");
            } else {
                this.pool = RoundRobinJedisPool.create().curatorClient(this.zks, this.zkSessionTimeout != 0 ? this.zkSessionTimeout : 30000).timeoutMs(this.timeout != 0 ? this.timeout : 1000).zkProxyDir(this.zkProxyDir).build();
                LOGGER.debug("init codis connection pool success.");
            }
    }

    /**
     * spring bean的初始化方法
     *
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet()
            throws Exception {
        if ((this.zks == null) || (this.zkProxyDir == null)) {
            throw new Exception("this.zks == null || this.subSysName == null");
        }
        try {
            initPool();
        } catch (Exception e) {
           LOGGER.info(e.getMessage());
        }
        LOGGER.debug("init codis connection pool success after properties set.");
    }

    public void setZks(String zks) {
        this.zks = zks;
    }

    public void setAuthpassword(String authpassword) {
        this.authpassword = authpassword;
    }

    public void setZkProxyDir(String zkProxyDir) {
        this.zkProxyDir = zkProxyDir;
    }

    public void setZkSessionTimeout(int zkSessionTimeout) {
        this.zkSessionTimeout = zkSessionTimeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }
}
