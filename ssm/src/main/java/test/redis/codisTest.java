package test.redis;


import io.codis.jodis.JedisResourcePool;
import io.codis.jodis.RoundRobinJedisPool;
import org.apache.log4j.BasicConfigurator;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;

public class codisTest {
    private static final String zkAddr = "118.24.229.84:2181,47.112.124.205:2181,120.79.74.127:2181";
    private static final int zkSessionTimeoutMs = 30000;
    private static final String zkProxyDir = "/jodis/codis-demo";

    public static void main(String[] args) {
        BasicConfigurator.configure();
        JedisResourcePool jedisPool = RoundRobinJedisPool.create()
                .curatorClient(zkAddr, zkSessionTimeoutMs).zkProxyDir(zkProxyDir).build();

        List<String> list = new ArrayList<>();
        list.add("1a");
        list.add("2a");
        list.add("3a");
        list.add("1b");
        list.add("2b");
        list.add("3b");
        list.add("1c");
        list.add("2c");
        list.add("3c");
        Jedis jedis = jedisPool.getResource();

        for (String s : list) {
            jedis.set(s, s);
            System.out.println(jedis.get(s));
        }

    }


}
