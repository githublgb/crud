package com.lgb.bootweb.config;


import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.alibaba.rocketmq.common.protocol.heartbeat.MessageModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class RocketMqConfig {

    private Logger LOGGER = LoggerFactory.getLogger(RocketMqConfig.class);

    @Value("${rocketmq.namesrvAddr}")
    private String namesrvAddr;

    @Value("${rocketmq.producerGroupName}")
    private String producerGroupName;

    @Value("${rocketmq.topicName}")
    private String topicName;

    @Value("${rocketmq.consumerGroupName}")
    private String consumerGroupName;

    @Bean
    public DefaultMQProducer getDefaultMQProducer() {
        DefaultMQProducer defaultMQProducer = new DefaultMQProducer();
        //名称服务器地址
        defaultMQProducer.setNamesrvAddr(this.namesrvAddr);
        //生产者组
        defaultMQProducer.setProducerGroup(this.producerGroupName);
        try {
            //初始化方法
            defaultMQProducer.start();

        } catch (MQClientException e) {
           LOGGER.info(e.getErrorMessage());
        }
        return defaultMQProducer;
    }

    @Bean   //被spring管理，消费监听，消费到达会取拉取消息
    public DefaultMQPushConsumer defaultMQPushConsumer() {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("");
        try {
            consumer.setNamesrvAddr(this.namesrvAddr);
            //consumer.setInstanceName("ecf.platform.merchant");

            //消费者组
            consumer.setConsumerGroup(this.consumerGroupName);

            //消费主题下的所有二级子主题
            consumer.subscribe(this.topicName, "");

            //集群模式消费，默认为集群消费模式
            consumer.setMessageModel(MessageModel.CLUSTERING);

            //消息监听，消息到达，消费的接口实现在用户应用，push 被动消费，？
            consumer.registerMessageListener(new MessageListenerConcurrently() {

                @Override
                public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                    try {
                        for (MessageExt msg : msgs) {
                            //消息的业务消费
                            LOGGER.info("msgId" + msg.getMsgId());
                            LOGGER.info("Topic:" + new String(msg.getTopic()));
                            LOGGER.info("Message: " + new String(msg.getBody()));
                             //消息的业务处理 todo
                        }
                    } catch (Exception ex) {
                        LOGGER.error("消息消费异常", ex);

                        //返回消费失败的记录
                        return ConsumeConcurrentlyStatus.RECONSUME_LATER;

                    }
                    //返回消费成功的状态
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
            });
            consumer.start();
        } catch (MQClientException ex) {
            LOGGER.error("消息消费异常", ex);
        } catch (Exception ex) {
            LOGGER.error("消息消费异常", ex);
        }
        LOGGER.info("rockermq consumer started");
        return consumer;
    }

}
