package com.lgb.dao;

import com.lgb.domain.Person;
import com.lgb.util.PageResult;

public interface PersonDao {


    public PageResult<Person> queryPersonByPage(Person person);

    public Integer addPerson(Person person);

    public Integer deletePersonById(Long id);

    public Integer updatePersonById(Person person);

    public Person queryPersonById(Long id);

}
