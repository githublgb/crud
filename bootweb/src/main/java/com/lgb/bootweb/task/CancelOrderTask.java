package com.lgb.bootweb.task;

import com.lgb.bootweb.domain.Person;
import com.lgb.bootweb.lock.CodisDistributedLock;
import com.lgb.bootweb.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component //扫描被上下问管理
//@EnableScheduling //开启定时任务功能
public class CancelOrderTask implements SchedulingConfigurer {
    private static final Logger LOGGER = LoggerFactory.getLogger(CancelOrderTask.class);
    private static final String DEFAULT_CRON = "*/1 * * * * ?"; //每1秒执行一次
    private String cron = DEFAULT_CRON;

    @Autowired
    private PersonService personService;

    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        scheduledTaskRegistrar.addTriggerTask(new Runnable() {
            @Override
            public void run() {
                CodisDistributedLock codisDistributedLock = new CodisDistributedLock();
                boolean  successFlag = codisDistributedLock.getLock("1", 60);
                if (successFlag) {
                    Person person = personService.queryPersonById(1L);
                } else {
                    LOGGER.info("获取锁失败");
                    LOGGER.info("业务单据为1正在被处理");
                }

               /* try {
                    Thread.sleep(6000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/
                LOGGER.info(Thread.currentThread().getName());
                LOGGER.info("spring 的定时任务");
            }
        }, new Trigger() {
            @Override
            public Date nextExecutionTime(TriggerContext triggerContext) {
                CronTrigger trigger = new CronTrigger(cron);
                Date nextExec = trigger.nextExecutionTime(triggerContext);
                return nextExec;
            }
        });

    }
}