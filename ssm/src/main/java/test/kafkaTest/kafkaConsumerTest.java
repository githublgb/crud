package test.kafkaTest;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.kafka.core.KafkaTemplate;

public class kafkaConsumerTest {

    public static void main(String[] args) {


        ApplicationContext cxt = new ClassPathXmlApplicationContext("kafka-consumer.xml");


    }
}
