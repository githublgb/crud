package com.lgb.bootweb.controller;


import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.alibaba.rocketmq.client.exception.MQBrokerException;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.remoting.exception.RemotingException;
import com.lgb.bootweb.Enum.OptStatusEnum;
import com.lgb.bootweb.domain.Person;
import com.lgb.bootweb.service.AsyncTestServer;
import com.lgb.bootweb.service.PersonService;
import com.lgb.bootweb.util.AjaxMessageEntity;
import com.lgb.bootweb.util.PageResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Iterator;
import java.util.List;


@Controller
@RequestMapping("/person")
public class PersonController {
    private static Logger LOGGER = LoggerFactory.getLogger(PersonController.class);

    @Value("${rocketmq.topicName}")
    private String topicName;

    @Autowired
    private PersonService personService;

    @Autowired
    private DefaultMQProducer defaultMQProducer;//rockermq的生产者

    @Autowired
    private AsyncTestServer asyncTestServer;//测试异步调用


    @RequestMapping("/query")
    public ModelAndView intoPersonList() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("person/views/personList");
        return modelAndView;
    }

    //查询
    @RequestMapping("/queryPerson/{queryId}")
    @ResponseBody
    public Person queryPersonById(HttpServletResponse response, HttpServletRequest request, @PathVariable("queryId") Long id) {
        Person person = personService.queryPersonById(id);
        String messagePerson = JSONObject.toJSONString(person);
        Message message = new Message(topicName, messagePerson.getBytes());


        try {
            for (int i = 0; i < 200; i++) {
               // Thread.sleep(1000);
                // asyncTestServer.testAsync(); //测试异步调用
               // defaultMQProducer.send(message); //测试发送消息
            }

        } catch (Exception e) {
            LOGGER.info(e.getMessage());
        }
        return person;
    }

    ;

    //分页查询
    @RequestMapping("/queryPersonByPage")
    @ResponseBody
    public PageResult<Person> queryPersonByPage(HttpServletResponse response, HttpServletRequest request, Person person) {

        return personService.queryPersonByPage(person);
    }

    //新增
    @RequestMapping("/insert")
    @ResponseBody
    public AjaxMessageEntity<Person> addPerson(Person person) {
        personService.addPerson(person);
        return new AjaxMessageEntity<>();
    }

    //删除
    @RequestMapping("/delete")
    @ResponseBody
    public AjaxMessageEntity<Person> deletePerson(@RequestParam(value = "id[]") Long[] id) {
        for (Long longId : id) {
            personService.deletePersonById(longId);
        }
        return new AjaxMessageEntity<>();
    }

    //修改
    @RequestMapping("/update")
    @ResponseBody
    public AjaxMessageEntity<Person> updatePerson(Person person) {
        personService.updatePersonById(person);
        return new AjaxMessageEntity<>();

    }

}
