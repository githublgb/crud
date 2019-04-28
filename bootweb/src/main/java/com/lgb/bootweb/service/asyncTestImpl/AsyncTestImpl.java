package com.lgb.bootweb.service.asyncTestImpl;

import com.lgb.bootweb.controller.PersonController;
import com.lgb.bootweb.service.AsyncTestServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class AsyncTestImpl implements AsyncTestServer {
    private static Logger LOGGER = LoggerFactory.getLogger(AsyncTestServer.class);

    @Async
    @Override
    public void testAsync() {
        int a = 10/0;
        LOGGER.info("=======================异步线程测试");
    }
}
