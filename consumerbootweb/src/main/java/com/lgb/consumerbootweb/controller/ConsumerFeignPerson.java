package com.lgb.consumerbootweb.controller;


import com.lgb.consumerbootweb.domain.Person;
import com.lgb.consumerbootweb.interfaceFeign.ConsumerPersonInterfaceFeign;
import com.lgb.consumerbootweb.util.AjaxMessageEntity;
import com.lgb.consumerbootweb.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/consumerFeignPerson")
public class ConsumerFeignPerson {

    @Autowired
    private ConsumerPersonInterfaceFeign consumerPersonInterfaceFeign;

    //根据id查询
    @RequestMapping("/feignFind/{id}")
    @ResponseBody
    public Person getPersonByFeignId(@PathVariable Long id) {

        Person person =consumerPersonInterfaceFeign.getPersonByFeignId(id);
        return person;
    }

    @RequestMapping("/findPersonByPage")
    @ResponseBody
    public PageResult<Person> findPersonByPage(){
        Person person = new Person();
        person.setPageIndex(1);
        person.setPageSize(10);
        return  consumerPersonInterfaceFeign.findPersonByPage(person);
    }

    //新增
    @RequestMapping("/findInsertPerson")
    @ResponseBody
    public AjaxMessageEntity<Person> addPerson(){
        Person person = new Person();
        person.setName("ligubo");
        person.setAddress("湖北");
        person.setHobby("体育");
        person.setProfession("学生");
        consumerPersonInterfaceFeign.addPerson(person);
        return  new AjaxMessageEntity<>();
    }
}
