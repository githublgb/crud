package com.lgb.bootweb.config;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;


/**
 * 统一异常处理
 */
@ControllerAdvice
public class ExceptionHandler {


    @ResponseBody
    @org.springframework.web.bind.annotation.ExceptionHandler({Exception.class})
    public String baseExceptionHandler(HttpServletRequest req, Exception e){
        return e.getMessage();
    }
}
