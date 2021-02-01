package com.liuyang.rocketmq;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.junit.Test;

public class Common {

    @Test
    public void createTopic() throws MQClientException {
        DefaultMQProducer producer = new DefaultMQProducer("example_group_name");
        producer.setNamesrvAddr(RocketConfig.NAME_SVR);
        producer.start();
        producer.createTopic("", "TopicTest123", 5);

    }
}
