package com.lgb.controller;


import com.alibaba.fastjson.JSONObject;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.common.message.Message;

import com.lgb.domain.Person;
import com.lgb.service.PersonService;
import com.lgb.util.AjaxMessageEntity;
import com.lgb.util.PageResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/person")
public class PersonController {
    private static Logger LOGGER = LoggerFactory.getLogger(PersonController.class);



    @Autowired
    private PersonService personService;

    @RequestMapping("/query")
    public ModelAndView intoPersonList() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("person/views/personList");
        return modelAndView;
    }

    //查询
    @RequestMapping("/queryPerson/{queryId}")
    @ResponseBody
    public Person queryPersonById(@PathVariable("queryId") Long id) {
        Person person = personService.queryPersonById(id);
        String messagePerson = JSONObject.toJSONString(person);

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
    public PageResult<Person> queryPersonByPage(Person person) {

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
