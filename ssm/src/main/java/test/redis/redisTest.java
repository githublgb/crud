package test.redis;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class redisTest {
    public static void main(String[] args) {
        ApplicationContext cxt = new ClassPathXmlApplicationContext("spring-redis.xml");
        RedisTemplate<String, String> redisTemplate = (RedisTemplate) cxt.getBean("redisTemplate");
        redisTemplate.opsForValue().set("name", "什么情况");


        String name=  redisTemplate.opsForValue().get("name");
        System.out.println(name);

    }

    //测试单列连接
    public static void testJedisSingle() {
        Jedis jedis = new Jedis("118.24.229.84", 6699);
        jedis.set("name", "你好帅");
        String name = jedis.get("name");

        System.out.println(name);
        jedis.close();
    }

    //测试连接池
    public static void testJedisPool() {
        JedisPoolConfig config = new JedisPoolConfig();
        //最大连接数
        config.setMaxTotal(30);
        //最大空闲连接数
        config.setMaxIdle(10);

        //连接池
        JedisPool jedisPool = new JedisPool(config, "118.24.229.84", 6699);
        Jedis jedis = null;

        try {
            jedis = jedisPool.getResource();
            jedis.set("name", "你好");
            String name = jedis.get("name");
            System.out.println(name);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
            jedisPool.close();
        }

    }
}
