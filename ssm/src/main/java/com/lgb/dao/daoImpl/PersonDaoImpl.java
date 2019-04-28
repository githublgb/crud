package com.lgb.dao.daoImpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lgb.dao.PersonDao;
import com.lgb.domain.Person;
import com.lgb.util.PageResult;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PersonDaoImpl implements PersonDao {


    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;


    @Override
    public PageResult<Person> queryPersonByPage(Person person) {
        PageHelper.startPage(person.getPageIndex(), person.getPageSize());
        List<Person> list = sqlSessionTemplate.selectList("com.lgb.domain.Person.queryPersonByPage", person);
        PageInfo<Person> pageInfo = new PageInfo<>(list);


        return new PageResult<Person>(Long.valueOf(pageInfo.getTotal()), list);
    }

    @Override
    public Integer addPerson(Person person) {
        return sqlSessionTemplate.insert("com.lgb.domain.Person.insert", person);
    }

    @Override
    public Integer deletePersonById(Long id) {
        return sqlSessionTemplate.delete("com.lgb.domain.Person.deleteById", id);
    }

    @Override
    public Integer updatePersonById(Person person) {
        return sqlSessionTemplate.delete("com.lgb.domain.Person.updatePersonById", person);
    }

    @Override
    public Person queryPersonById(Long id) {
        return sqlSessionTemplate.selectOne("com.lgb.domain.Person.queryPersonById",id);
    }
}
