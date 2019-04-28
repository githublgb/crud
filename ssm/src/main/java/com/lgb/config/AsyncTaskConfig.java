package com.lgb.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 异步线程池配置
 */
//@Configuration
//@EnableAsync   //为了让@Async注解能够生效，配置@EnableAsync
public class AsyncTaskConfig implements AsyncConfigurer {
  private static  final Logger LOGGER = LoggerFactory.getLogger(AsyncTaskConfig.class);
    @Override
    public Executor getAsyncExecutor() {
        LOGGER.info("getAsyncExecutor:方法执行了。。。。。。。。。。。");
        return asyncExecutor();
    }
    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        LOGGER.info("getAsyncUncaughtExceptionHandler:方法执行了。。。。。。。。。。。");
        return new AsyncExceptionHandler();
    }
    /**
     * 配置自己的异常线程池，boot为我们已配置好，自己不配置使用默认配置
     * @return
     */
    @Bean
    public ThreadPoolTaskExecutor asyncExecutor() {
        LOGGER.info("asyncExecutor:方法执行了。。。。。。。。。。。");
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(2);//当前线程数
        threadPoolTaskExecutor.setMaxPoolSize(10);// 最大线程数
        threadPoolTaskExecutor.setQueueCapacity(3);//线程池所使用的缓冲队列
        threadPoolTaskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());//作用异常

        //threadPoolTaskExecutor.setWaitForTasksToCompleteOnShutdown(true);//等待任务在关机时完成--表明等待所有线程执行完
        //threadPoolTaskExecutor.setAwaitTerminationSeconds(60 * 15);// 等待时间 （默认为0，此时立即停止），并没等待xx秒后强制停止
        //threadPoolTaskExecutor.setThreadNamePrefix("MyAsync-");//  线程名称前缀

        threadPoolTaskExecutor.initialize(); // 初始化
        LOGGER.info("--------------------------》》》开启异常线程池");
        return threadPoolTaskExecutor;
    }


}
