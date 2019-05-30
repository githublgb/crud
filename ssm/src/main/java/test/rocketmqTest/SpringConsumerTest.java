package test.rocketmqTest;

import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;
import com.lgb.rocketmq.SpringConsumer;
import com.lgb.rocketmq.SpringProducer;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringConsumerTest {

    private ApplicationContext context;


    @Before
    public void setUp() {
        context = new ClassPathXmlApplicationContext("rocketmq-consumer.xml");
    }

    @Test
    public void senMessage() throws Exception {

        SpringConsumer consumer = (SpringConsumer) context.getBean("springConsumer");

        Thread.sleep(300 * 1000);
        consumer.destroy();
    }
}
