package com.liuyang.rocketmq.scheduled;

import com.liuyang.rocketmq.RocketConfig;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;

import java.util.List;

public class ScheduledMessageConsumer {

    public static void main(String[] args) throws Exception {
        // Instantiate message consumer
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(RocketConfig.CONSUMER_GROUP);
        consumer.setNamesrvAddr(RocketConfig.NAME_SVR);
        // Subscribe topics
        consumer.subscribe(RocketConfig.TOPIC, "*");
//        consumer.setMessageModel(MessageModel.BROADCASTING);
        // Register message listener
        consumer.registerMessageListener((MessageListenerConcurrently) (messages, context) -> {
            messages.stream().map(Message::getBody).map(String::new).forEach(System.out::println);
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });
        
        // Launch consumer
        consumer.start();
    }
}