package com.lgb.consumerbootweb.interfaceFeign.interfaceFeignImpl;

import com.lgb.consumerbootweb.domain.Person;
import com.lgb.consumerbootweb.interfaceFeign.ConsumerPersonInterfaceFeign;
import com.lgb.consumerbootweb.util.AjaxMessageEntity;
import com.lgb.consumerbootweb.util.PageResult;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class FeignClientFallbackFactory implements FallbackFactory<ConsumerPersonInterfaceFeign> {
    private static  final Logger LOGGER= LoggerFactory.getLogger(FeignClientFallbackFactory.class);
    @Override
    public ConsumerPersonInterfaceFeign create(Throwable throwable) {

     return new ConsumerPersonInterfaceFeign() {
         @Override
         public Person getPersonByFeignId(Long id) {
             LOGGER.info("进入回退方法中错误原因是======"+throwable.getMessage());
             Person person = new Person();
             person.setName("服务异常，特定此人");
             return person;
         }
         @Override
         public PageResult<Person> findPersonByPage(Person person) {
             return null;
         }
         @Override
         public AjaxMessageEntity<Person> addPerson(Person person) {
             return null;
         }
     };
    }
}
