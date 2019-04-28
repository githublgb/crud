package com.lgb.consumerbootweb.interfaceFeign.interfaceFeignImpl;

import com.lgb.consumerbootweb.domain.Person;
import com.lgb.consumerbootweb.interfaceFeign.ConsumerPersonInterfaceFeign;
import com.lgb.consumerbootweb.util.AjaxMessageEntity;
import com.lgb.consumerbootweb.util.PageResult;
import org.springframework.stereotype.Component;

@Component
public class FeignClienFallback implements ConsumerPersonInterfaceFeign {

    @Override
    public Person getPersonByFeignId(Long id) {
        Person person = new Person();
        person.setName("错误是给的特定的人");
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
}
