package com.lgb.rocketmq;


import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;


public class SpringConsumer {

    private String consumerGroupName;

    private String nameServerAddr;

    private String topicName;

    private DefaultMQPushConsumer consumer;

    private MessageListenerConcurrently messageListener;

    public SpringConsumer(String consumerGroupName, String nameServerAddr, String topicName, MessageListenerConcurrently messageListener) {
        this.consumerGroupName = consumerGroupName;
        this.nameServerAddr = nameServerAddr;
        this.topicName = topicName;
        this.messageListener = messageListener;
    }

    public void init() throws Exception{
        consumer = new DefaultMQPushConsumer(consumerGroupName);
        consumer.setNamesrvAddr(nameServerAddr);

        //消费次主题下的所以消息
        consumer.subscribe(topicName,"*");

        //注册消息监听器
        consumer.registerMessageListener(messageListener);

        //从队列的头还是尾消费消息
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        consumer.start();

    }


    public  void destroy(){
        consumer.shutdown();
    }


    public DefaultMQPushConsumer consumer(){

        return  consumer;
    }


}