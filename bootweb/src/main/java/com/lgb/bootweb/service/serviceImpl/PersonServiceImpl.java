package com.lgb.bootweb.service.serviceImpl;

import com.lgb.bootweb.Enum.ExceptionEnum;
import com.lgb.bootweb.annotation.MyAnnotation;
import com.lgb.bootweb.common.ServiceException;
import com.lgb.bootweb.dao.PersonDao;
import com.lgb.bootweb.domain.Person;
import com.lgb.bootweb.service.PersonService;
import com.lgb.bootweb.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("personService")
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonDao personDao;


    @Override
    @MyAnnotation(name = "lgb")
    public PageResult<Person> queryPersonByPage(Person person) {


        return personDao.queryPersonByPage(person);
    }

    @Override
    public Integer addPerson(Person person) {

        return personDao.addPerson(person);
    }

    @Override
    public Integer deletePersonById(Long id) {

        return personDao.deletePersonById(id);
    }

    @Override
    public Integer updatePersonById(Person person) {

        Integer integer = personDao.updatePersonById(person);
        return integer;
    }

    @Override

    public Person queryPersonById(Long id) {
        Person returnPerson = null;
        returnPerson = personDao.queryPersonById(id);

        return returnPerson;
    }

}
