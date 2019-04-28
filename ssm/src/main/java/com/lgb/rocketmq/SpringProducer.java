package com.lgb.rocketmq;

import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SpringProducer {
    private static Logger logger = LoggerFactory.getLogger(SpringProducer.class);

    //生产者组
    private String producerGroupName;

    //维护中心，类似zookeeper
    private String  nameServerAddr;

    //生产者
    private DefaultMQProducer producer;

    public SpringProducer(String producerGroupName, String nameServerAddr) {
        this.producerGroupName = producerGroupName;
        this.nameServerAddr = nameServerAddr;
    }


    //初始化化方法
    public void init() throws Exception{
       producer = new DefaultMQProducer(producerGroupName);
       producer.setNamesrvAddr(nameServerAddr);

       //初始化SpringProducer 整个应用生命周期里只要初始化一次
       producer.start();

    }

    //销毁方法
    public void destroy(){

        producer.shutdown();
    }

    public DefaultMQProducer getProducer(){
        return  producer;
    }
}
