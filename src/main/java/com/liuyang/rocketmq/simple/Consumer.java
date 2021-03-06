package com.liuyang.rocketmq.simple;

import com.liuyang.rocketmq.RocketConfig;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.Arrays;
import java.util.List;

public class Consumer {

    static final List<String> TOPICS = Arrays.asList("TopicTest", "Jodie_topic_1023");

    public static void main(String[] args) throws InterruptedException, MQClientException {

        // Instantiate with specified consumer group name.
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("please_rename_unique_group_name");

        // Specify name server addresses.
        consumer.setNamesrvAddr(RocketConfig.NAME_SVR);

        //consumer.setPullBatchSize(1);//default=32
        consumer.setConsumeThreadMin(1);
        consumer.setConsumeThreadMax(1);

        // Subscribe one more more topics to consume.
        for (String topic : TOPICS) consumer.subscribe(topic, "*");

        // Register callback to execute on arrival of messages fetched from brokers.
        consumer.registerMessageListener(new MessageListenerConcurrently() {

            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                System.out.println(new String(msgs.get(0).getBody()));
//                System.out.println(Thread.currentThread().getName());
                /*
                 * 处理业务逻辑
                 */
                //返回执行结果
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

        //Launch the consumer instance.
        consumer.start();

        System.out.printf("Consumer Started.%n");
    }
}