package com.liuyang.rocketmq.retry;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.liuyang.rocketmq.RocketConfig;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.Message;

import java.util.List;
import java.util.stream.Collectors;

public class RetryConsumer {

    static final ObjectMapper mapper = new ObjectMapper();

    public RetryConsumer() {
    }

    public static void main(String[] args) throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("example_group_name_100");
        consumer.setNamesrvAddr(RocketConfig.NAME_SVR);

        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_MAX_OFFSET);
        consumer.subscribe(RocketConfig.TOPIC, "*");

        long start = System.currentTimeMillis();

        consumer.registerMessageListener((MessageListenerConcurrently) (msgs, context) -> {
            List<Integer> list = msgs.stream().map(Message::getBody).map(String::new).map(Integer::valueOf).collect(Collectors.toList());
            boolean b = list.stream().anyMatch(i -> i % 3 == 0);
            System.out.println(JSONObject.toJSONString(list) + ":" + (System.currentTimeMillis() - start) / 1000);
            return b ? ConsumeConcurrentlyStatus.RECONSUME_LATER : ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });

        consumer.start();

        System.out.printf("Consumer Started.%n");
    }

}
