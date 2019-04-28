package com.lgb.consumerbootweb.interfaceFeign;


import com.lgb.consumerbootweb.domain.Person;
import com.lgb.consumerbootweb.interfaceFeign.interfaceFeignImpl.FeignClienFallback;
import com.lgb.consumerbootweb.interfaceFeign.interfaceFeignImpl.FeignClientFallbackFactory;
import com.lgb.consumerbootweb.util.AjaxMessageEntity;
import com.lgb.consumerbootweb.util.PageResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@FeignClient(name = "bootweb" ,fallbackFactory = FeignClientFallbackFactory.class)
public interface ConsumerPersonInterfaceFeign {
    //根据id查询
    @RequestMapping( "/person/queryPerson/{queryId}")
    public Person getPersonByFeignId(@PathVariable("queryId") Long id);

   //分页查询
    @RequestMapping("/person/queryPersonByPage")
    @ResponseBody
    public PageResult<Person> findPersonByPage(Person person) ;

    //新增
    @RequestMapping("/person/insert")
    @ResponseBody()
    public AjaxMessageEntity<Person> addPerson(Person person);

}
