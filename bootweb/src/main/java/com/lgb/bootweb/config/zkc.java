package com.lgb.bootweb.config;

import org.I0Itec.zkclient.ZkClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class zkc {
    @Bean
    public ZkClient getZkc(){
        ZkClient zkClient = new ZkClient("120.79.74.127:2181",3000);
        return  zkClient;
    }
}
