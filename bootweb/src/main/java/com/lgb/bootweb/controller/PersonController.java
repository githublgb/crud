package com.lgb.bootweb.controller;


import com.alibaba.fastjson.JSONObject;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.common.message.Message;
import com.lgb.bootweb.Enum.OptStatusEnum;
import com.lgb.bootweb.annotation.SysOptLog;
import com.lgb.bootweb.domain.Person;
import com.lgb.bootweb.lock.CodisDistributedLock;
import com.lgb.bootweb.service.AsyncTestServer;
import com.lgb.bootweb.service.PersonService;
import com.lgb.bootweb.util.AjaxMessageEntity;
import com.lgb.bootweb.util.CodisUtils;
import com.lgb.bootweb.util.PageResult;
import com.lgb.domain.Response;
import com.lgb.service.RpcPersonService;
import io.swagger.annotations.*;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Controller
@RequestMapping("/person")
@Api("PersonController相关api")
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

    @Autowired
    private CodisUtils codisUtils;

    @Autowired
    private RpcPersonService rpcPersonService;

    @RequestMapping("/query")
    public ModelAndView intoPersonList() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("person/views/personList");
        return modelAndView;
    }

    //查询
    @RequestMapping("/queryPerson/{queryId}")
    @ResponseBody
    @ApiOperation(value = "获取用户详细信息", notes = "根据url的id来获取用户详细信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Integer", paramType = "path")})
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    public Person queryPersonById(@PathVariable("queryId") Long id) {
        Person person = personService.queryPersonById(id);
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
    @ApiOperation(value = "获取用户列表", notes = "获取用户列表")
    public PageResult<Person> queryPersonByPage(HttpServletResponse response, HttpServletRequest request, Person person) {
        return personService.queryPersonByPage(person);
    }

    //新增
    @RequestMapping("/insert")
    @ResponseBody
    @ApiOperation(value = "创建用户", notes = "根据person对象创建用户")
    @ApiImplicitParam(name = "person", value = "用户详细实体person", required = true, dataType = "Person")
    @SysOptLog(optStatus = OptStatusEnum.ADD)
    public AjaxMessageEntity<Person> addPerson(Person person) {
        personService.addPerson(person);
        return new AjaxMessageEntity<>();
    }

    //删除
    @RequestMapping("/delete")
    @ResponseBody
    @ApiOperation(value = "删除用户", notes = "根据url的id来指定删除用户")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long[]", paramType = "path")
    @SysOptLog(optStatus = OptStatusEnum.DELETE)
    public AjaxMessageEntity<Person> deletePerson(@RequestParam(value = "id[]") Long[] id) {
        for (Long longId : id) {
            personService.deletePersonById(longId);
        }
        return new AjaxMessageEntity<>();
    }

    //修改
    @RequestMapping("/update")
    @ResponseBody
    @ApiOperation(value = "更新信息", notes = "根据url的id来指定更新用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "person", value = "用户实体user", required = true, dataType = "Person")
    })
    public AjaxMessageEntity<Person> updatePerson(Person person) {
        personService.updatePersonById(person);
        return new AjaxMessageEntity<>();

    }


    /**
     * 测试dubbo的调用
     *
     * @param id
     * @return
     */
    @RequestMapping("/testdubbo")
    @ResponseBody
    public AjaxMessageEntity<Person> queryforpcById(@RequestParam(value = "id[]") Long[] id) {
        for (Long longId : id) {
            Response response = rpcPersonService.queryPersonById(longId);
            com.lgb.domain.Person obj = (com.lgb.domain.Person) response.getDate();
            Person person = new Person();
            person.setAge(obj.getAge());
            person.setAddress(obj.getAddress());
            person.setHobby(obj.getHobby());
            person.setName(obj.getName());
            person.setPhoneNumber(obj.getPhoneNumber());
            person.setSex(obj.getSex());
            person.setProfession(obj.getProfession());
            personService.addPerson(person);
        }

        return new AjaxMessageEntity<>();
    }

    /**
     * 测试rocketmq
     *
     * @param id
     * @return
     */
    @RequestMapping("/testrocketmq")
    @ResponseBody
    public AjaxMessageEntity<Person> testRocketmq(@RequestParam(value = "id[]") Long[] id) {
        LOGGER.info("测试codis操作");
        for (Long longId : id) {
            Person person = personService.queryPersonById(longId);
            //测试发送消息
            String messagePerson = JSONObject.toJSONString(person);
            Message message = new Message(topicName, messagePerson.getBytes());
            try {
                defaultMQProducer.send(message); //测试发送消息
            } catch (Exception e) {
                LOGGER.info(e.getMessage());
            }

        }
        AjaxMessageEntity<Person> ajaxMessageEntity = new AjaxMessageEntity<>();
        ajaxMessageEntity.setMessager(0, "s");
        return new AjaxMessageEntity<>();
    }

    /**
     * 测试codis
     *
     * @param id
     * @return
     */
    @RequestMapping("/testcodis")
    @ResponseBody
    public AjaxMessageEntity<Person> testCodis(@RequestParam(value = "id[]") Long[] id) {
        LOGGER.info("测试codis操作");
        for (Long longId : id) {
            Person person = personService.queryPersonById(longId);


            //存储字符串和对象底层都是序列成字节数组存储到redsi,get时都是反序列化成字符串和对象
            if (true) {//存储对象
                codisUtils.setObjectByTime(longId.toString(), 160L, person);
                Person valuePerson = codisUtils.getObject(longId.toString(), Person.class);
                LOGGER.info("redis获取到的对象值：{}", valuePerson);
            }

            if (false) {//存储字符串
                codisUtils.set(longId.toString(),person.toString());
                String per= codisUtils.getString(longId.toString());
                 LOGGER.info("redis获取到的对象值：{}",per);
            }

        }
        return new AjaxMessageEntity<>();
    }

    /**
     * 测试zk分布式锁
     *
     * @param id
     * @return
     */
    @RequestMapping("/testzkLock")
    @ResponseBody
   // @Async
    public AjaxMessageEntity<Person> testZKLock(@RequestParam(value = "id[]") Long[] id) {
        boolean successFlag = false;
        CodisDistributedLock codisDistributedLock = new CodisDistributedLock();
        String key = "";
        try {
            for (Long longId : id) {
                key = longId.toString();
                successFlag = codisDistributedLock.getLock(key, 60);
                if (successFlag) {
                    Person person = personService.queryPersonById(longId);
                } else {
                    LOGGER.info("获取锁失败");
                    LOGGER.info("业务单据id=" + id + "正在被处理");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (successFlag) {
                try {
                    LOGGER.debug("这里要释放锁");
                    // 释放分布式锁
                    // codisDistributedLock.releaseLock();
                    ///codisDistributedLock.releaseLockAll();
                } catch (Exception e) {
                    LOGGER.debug("释放分布式锁：{} 失败", key, e);
                }
            }
        }
        AjaxMessageEntity<Person> ajaxMessageEntity = new AjaxMessageEntity<>();
        return ajaxMessageEntity;

    }

}
