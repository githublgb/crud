package com.lgb.bootweb.config;

import com.lgb.bootweb.common.ServiceException;
import com.lgb.common.BusException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;


/**
 * 统一异常处理
 */
@ControllerAdvice
public class ExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandler.class);


    @ResponseBody
    @org.springframework.web.bind.annotation.ExceptionHandler({Exception.class})
    public String exceptionHandler(HttpServletRequest req, Exception e){
      if(e instanceof BusException){
          BusException bus = (BusException)e;
          LOGGER.info("异常信息：{}",bus.getMessage());
      }
        return e.getMessage()+"****系统异常";
    }

    @ResponseBody
    @org.springframework.web.bind.annotation.ExceptionHandler({ServiceException.class})
    public String baseExceptionHandler(HttpServletRequest req, Exception e){

        return e.getMessage()+"****自定义的异常";
    }
}
