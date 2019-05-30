package test.kafkaTest;


import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.Map;

public class KafkaProducerTest {
    String _naem ="sss";


    public static void main(String[] args) {


        ApplicationContext cxt = new ClassPathXmlApplicationContext("kafka-producer.xml");

        ApplicationContext c = new AnnotationConfigApplicationContext();
        KafkaTemplate kafkaTemplate = (KafkaTemplate)cxt.getBean("kafkaTemplate");

        for (int i = 0; i <20 ; i++) {
            kafkaTemplate.send("test","kafka发送的数据"+i);
        }


    }
}
