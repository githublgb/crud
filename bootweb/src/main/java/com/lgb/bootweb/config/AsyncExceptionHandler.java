package com.lgb.bootweb.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;

import java.lang.reflect.Method;

/**
 * 异步线程异常发生时，系统回调的函数，这里负责存crash信息
 */
public class AsyncExceptionHandler  implements AsyncUncaughtExceptionHandler {

    private static Logger LOGGER = LoggerFactory.getLogger(AsyncExceptionHandler.class);
    @Override
    public void handleUncaughtException(Throwable ex, Method method, Object... params) {
        LOGGER.error("-------------》》》捕获线程异常信息");
        LOGGER.error("异常消息- " + ex.getMessage());
        LOGGER.error("方法名称 - " + method.getName());
        for (Object param : params)
            LOGGER.info("参数值 - " + param);
    }
}
