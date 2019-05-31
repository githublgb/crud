package com.lgb.bootweb.service;

import com.lgb.bootweb.domain.Person;
import com.lgb.bootweb.util.PageResult;

import java.util.List;

public interface PersonService {


    public PageResult<Person> queryPersonByPage(Person person);

    public Integer addPerson(Person person);

    public Integer deletePersonById(Long id);

    public Integer updatePersonById(Person person);

    public Person queryPersonById(Long id);

}
