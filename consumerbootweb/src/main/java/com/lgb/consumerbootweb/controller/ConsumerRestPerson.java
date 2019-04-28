package com.lgb.consumerbootweb.controller;

import com.lgb.consumerbootweb.domain.Person;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

@Controller
@RequestMapping("/consumerRestPerson")
public class ConsumerRestPerson {
    private static  final  Logger LOGGER= LoggerFactory.getLogger(ConsumerRestPerson.class);
    private static final String REST_URL_PREFIX="http://bootweb";

    @Autowired
    private LoadBalancerClient loadBalancerClient;

   @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "getPersonFallback",ignoreExceptions = {NullPointerException.class})
    @RequestMapping("/restFind/{id}")
    @ResponseBody
   public Person getPersonByRestId(@PathVariable Long id) {
        //restTemplate加有ribbon 可以通过注册的微服务名称来调用
         Person person= restTemplate.getForObject(REST_URL_PREFIX+"/person/queryPerson/"+id, Person.class);

          //restTemplate没有叫ribbon 不可以通过注册的微服务名称来调用
         //Person person= restTemplate.getForObject("http://localhost:8080/person/queryPerson/"+id, Person.class);
           ServiceInstance serviceInstance = loadBalancerClient.choose("bootweb");
        LOGGER.info("{}:{}:{}", serviceInstance.getServiceId(), serviceInstance.getHost(), serviceInstance.getPort());
        return  person;
   }
   //hystrix的回退方法
   public Person getPersonFallback(Long id,Throwable throwable){
       LOGGER.info("进入回退方法中错误原因是======"+throwable.getMessage());
        Person person = new Person();
        person.setName("错误给的特定的人");
        return person;
   }
}
