package com.lgb.consumerbootweb.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class MyTemplateConfig {

    @Bean
    @LoadBalanced //负载均衡，也解析了微服务的名称
    public RestTemplate getRestTemplate(){
        return  new RestTemplate();
    }

}
