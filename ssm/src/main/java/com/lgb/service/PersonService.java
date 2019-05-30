package com.lgb.service;

import com.lgb.domain.Person;
import com.lgb.util.PageResult;

public interface PersonService {


    public PageResult<Person> queryPersonByPage(Person person);

    public Integer addPerson(Person person);

    public Integer deletePersonById(Long id);

    public Integer updatePersonById(Person person);

    public Person queryPersonById(Long id);

}
