package com.lgb.rocketmq;

import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.common.message.MessageExt;

import java.io.UnsupportedEncodingException;
import java.util.List;


public class MessageListener implements MessageListenerConcurrently {
    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {

        if(list != null)
            for (MessageExt messageExt : list) {

                try {
                    //自己的消费处理
                    String msg = new String(messageExt.getBody(),"UTF-8");
                    System.out.println("消费的消息"+msg);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }
}
