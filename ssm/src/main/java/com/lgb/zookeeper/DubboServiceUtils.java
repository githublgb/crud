package com.lgb.zookeeper;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 获取注册在zookeeper中的服务
 */
public class DubboServiceUtils {
    private final static Logger LOGGER = LoggerFactory.getLogger(DubboServiceUtils.class);

    private static final String DUBBO_APPLICATION_NAME = "dubbo.application.name";
    private static final String ZOOKEEPER = "zookeeper";
    private static final String ZOOKEEPER_ADDRESS = "dubbo.registry.zookeeper.address";

    // 注册中心
    private static RegistryConfig registryConfig = new RegistryConfig();
    // 应用
    private static ApplicationConfig application = new ApplicationConfig();

    //做工具类，并发的map
    private static final ConcurrentMap<String, ReferenceConfig<?>> cacheHolder = new ConcurrentHashMap<>();

   static {
       //设置为配置文件获取
        registryConfig.setAddress("118.24.229.84:2181");
        registryConfig.setProtocol(ZOOKEEPER);
        application.setName("lgb");
    }


    @SuppressWarnings("unchecked")
    public static <T> T getDubboProxy(Class<T> clazz, DubboConfigBean config) {

        ReferenceConfig<?> referenceConfig = cacheHolder.get(clazz.getName() + config.getServiceId());
        if (referenceConfig == null) {
            LOGGER.info("缓存中没有DUBBO代理对象,创建DUBBO代理对象");
            referenceConfig = new ReferenceConfig<>();
            referenceConfig.setApplication(application);
            referenceConfig.setRegistry(registryConfig);
            referenceConfig.setInterface(clazz);
            referenceConfig.setGroup(config.getGroup());
            referenceConfig.setVersion(config.getVersion());
            referenceConfig.setTimeout(60000);
            cacheHolder.put(clazz.getName() + config.getServiceId(), referenceConfig);
        }
        T t = null;
        try {
            t = (T) referenceConfig.get();
        } catch (Exception e) {
            LOGGER.error("动态获得Dubbo服务对象失败:" + e.getMessage());
        }
        if (t == null) {
            LOGGER.info("缓存Config无法获得Dubbo服务实例,删除该缓存!");
            cacheHolder.remove(clazz.getName() + config.getServiceId());
        }
        LOGGER.info("DUBBO代理对象创建成功:" + t);
        return t;
    }

}
