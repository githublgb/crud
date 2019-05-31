package com.lgb.bootweb;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lgb.bootweb.domain.Person;

public class main {
    public static void main(String[] args) {
        ObjectMapper objectMapper = new ObjectMapper();
        Person p = new Person();
        p.setProfession("年后");
        p.setName("李古波");
        try {
           // byte[] bytes = objectMapper.writeValueAsBytes(p);
            String s = objectMapper.writeValueAsString(p);
            System.out.println(s);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
