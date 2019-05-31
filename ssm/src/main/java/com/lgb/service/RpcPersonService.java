package com.lgb.service;


import com.lgb.domain.Person;
import com.lgb.domain.Response;

public interface RpcPersonService {
    public Response<Person> queryPersonById(Long id);
}
