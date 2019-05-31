package com.lgb.controller;


import com.alibaba.fastjson.JSONObject;
import com.lgb.domain.Person;
import com.lgb.lock.CodisDistributedLock;
import com.lgb.lock.DistributedLock;
import com.lgb.service.PersonService;
import com.lgb.util.AjaxMessageEntity;
import com.lgb.util.PageResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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


    //测试zookeeper的分布式锁
    @RequestMapping("/testZKLock/{queryId}")
    @ResponseBody
    public AjaxMessageEntity<Person> testZKLockPerson(@PathVariable("queryId") Long id) {
        boolean successFlag = false;
        Person person = null;
        DistributedLock distributedLock = new CodisDistributedLock();
         String key = id.toString();
        // 获得分布式锁，成功为true, 失败为false
        try {
            successFlag = distributedLock.getLock(key, 60);
            if (successFlag) {
                 person = personService.queryPersonById(id);
            } else {
                //获得锁失败
                LOGGER.info("获取分布式锁：[{}]失败", key);
                LOGGER.info("业务单据id=" + id + "正在被处理");
            }
        } catch (Exception e) {
            LOGGER.debug("调用失败", e.getMessage());
        } finally {
            if (successFlag) {
                try {
                    LOGGER.debug("这里要释放锁");
                    // 释放分布式锁
                    //distributedLock.releaseLock();
                } catch (Exception e) {
                    LOGGER.debug("释放分布式锁：{} 失败", key, e);
                }
            }
        }

        AjaxMessageEntity<Person> ajaxMessageEntity = new AjaxMessageEntity<Person>();
        ajaxMessageEntity.setData(person);
        return new AjaxMessageEntity<>();

    }

}
