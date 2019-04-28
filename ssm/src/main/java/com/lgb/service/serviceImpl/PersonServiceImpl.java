package com.lgb.service.serviceImpl;

import com.lgb.dao.PersonDao;
import com.lgb.domain.Person;
import com.lgb.service.PersonService;
import com.lgb.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonDao personDao;


    @Override
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
        return personDao.updatePersonById(person);
    }

    @Override
    public Person queryPersonById(Long id) {

        return personDao.queryPersonById(id);
    }

}
