package com.liuyang.rocketmq.pull;

import com.liuyang.rocketmq.RocketConfig;
import org.apache.rocketmq.client.consumer.DefaultMQPullConsumer;

import java.util.HashSet;
import java.util.Set;

public class PullConsumer {

    public static void main(String[] args) throws Exception {
        DefaultMQPullConsumer consumer = new DefaultMQPullConsumer(RocketConfig.CONSUMER_GROUP);
        consumer.setNamesrvAddr(RocketConfig.NAME_SVR);

        Set<String> topics = new HashSet<>();
        topics.add(RocketConfig.TOPIC);
        consumer.setRegisterTopics(topics);

        consumer.start();


    }
}
