package com.lgb.consumerbootweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients  //Feign
@EnableHystrix  //开启断路器支持
public class ConsumerbootwebApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConsumerbootwebApplication.class, args);
    }

}
