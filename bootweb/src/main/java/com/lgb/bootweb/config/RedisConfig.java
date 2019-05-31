package com.lgb.bootweb.config;

import com.lgb.bootweb.service.redisCodisImpl.CodisImpl;
import com.lgb.bootweb.util.CodisUtils;
import com.lgb.bootweb.util.RedisFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedisConfig {

    public static final Logger LOGGER = LoggerFactory.getLogger(RedisConfig.class);

    @Value("${codis.zookeeper}")
    private String zks;

    @Value("${codis.zkProxyDir}")
    private String zkProxyDir;

   /* @Value("${codis.authpassword}")
    private String authpassword;*/

    @Value("${codis.salt}")
    private String salt;

    @Bean({"redisFactory"})
    public RedisFactory getRedisFactory() {
        RedisFactory redisFactory = new RedisFactory();
        redisFactory.setZks(this.zks);
        redisFactory.setZkProxyDir(this.zkProxyDir);
        //redisFactory.setAuthpassword(this.authpassword);
        return redisFactory;
    }

    @Bean({"codis"})
    public CodisImpl getCodis() {
        CodisImpl codis = new CodisImpl();
        // codis.setSalt(this.salt);
        codis.setRedisFactory(getRedisFactory());
        return codis;
    }

   /* @Bean({"redisSerializer"})
    public Jackson2JsonRedisSerializer redisSerializer() {
        Jackson2JsonRedisSerializer redisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        redisSerializer.setObjectMapper(om);
        return redisSerializer;
    }
*/
    @Bean({"codisUtils"})
    public CodisUtils codisUtils() {
        String prefix = "lgb_";
        CodisUtils utils = new CodisUtils(getCodis(), prefix);
        return utils;
    }
}
