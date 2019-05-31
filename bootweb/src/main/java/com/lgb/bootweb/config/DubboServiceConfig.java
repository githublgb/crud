package com.lgb.bootweb.config;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

//@Configuration
public class DubboServiceConfig {
    private final static Logger LOGGER = LoggerFactory.getLogger(com.lgb.zookeeper.DubboServiceUtils.class);

    //协议
    private static final String ZOOKEEPER  = "zookeeper";

   /* dubbo.default.timeout=5000
    dubbo.registry.zookeeper.address=118.24.229.84:2181
    dubbo.registry.group=dev
    dubbo.application.name=lgb
    dubbo.protocol.port=20883*/

   @Value("${dubbo.registry.zookeeper.address}")
    private String address;

    @Value("${dubbo.registry.group}")
    private String group;

    @Value("${dubbo.application.name}")
    private String name;

    @Value("${dubbo.default.timeout}")
    private String time;

    @Value("${dubbo.version}")
    private String version;


    // 注册中心
    private  RegistryConfig registryConfig = new RegistryConfig(this.address);

    // 应用
    private  ApplicationConfig application = new ApplicationConfig(this.name);


    //@Bean
    public  ReferenceConfig getReferenceConfig() {
            ReferenceConfig referenceConfig = new ReferenceConfig<>();
            referenceConfig.setApplication(application);
            referenceConfig.setRegistry(registryConfig);
            referenceConfig.setGroup(this.group);
            referenceConfig.setVersion(this.version);
            referenceConfig.setTimeout( Integer.valueOf(this.time));
            return referenceConfig;
    }
}
