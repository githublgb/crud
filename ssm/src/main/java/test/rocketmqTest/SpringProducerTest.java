package test.rocketmqTest;

import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.remoting.common.RemotingHelper;
import com.lgb.rocketmq.SpringProducer;


import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringProducerTest {

    private ApplicationContext context;


    @Before
    public void setUp() {
        context = new ClassPathXmlApplicationContext("rocketmq-producer.xml");
    }

    @Test
    public void senMessage() throws Exception {

        SpringProducer producer = (SpringProducer) context.getBean("springProducer");

        for (int i = 0; i < 20; i++) {
            Message msg = new Message("spring-rocketmq-topic", null,
                    ("spring rocketmq topic" + i).getBytes("UTF-8"));

            SendResult sendResult = producer.getProducer().send(msg);

        }
    }
}
