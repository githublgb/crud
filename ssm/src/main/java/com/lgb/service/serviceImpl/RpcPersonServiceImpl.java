package com.lgb.service.serviceImpl;


import com.lgb.common.BusException;
import com.lgb.dao.PersonDao;
import com.lgb.domain.Person;
import com.lgb.domain.Response;
import com.lgb.service.RpcPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("rpcPersonService")
public class RpcPersonServiceImpl implements RpcPersonService {

    @Autowired
    private PersonDao personDao;

    @Override
    public Response<Person> queryPersonById(Long id)  {

       Person person = personDao.queryPersonById(id);
        return new Response<Person>("ok","成功",person);

    }

}
