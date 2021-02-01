package com.liuyang.rocketmq.broadcast;

import com.liuyang.rocketmq.RocketConfig;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

public class BroadcastProducer {

    public static final String TAG = "broadcast";

    public static void main(String[] args) throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("ProducerGroupName");
        producer.setNamesrvAddr(RocketConfig.NAME_SVR);
        producer.start();

        for (int i = 0; i < 1; i++) {
            Message msg = new Message("TopicTest",
                    TAG,
                    "OrderID188",
                    "Hello world 123".getBytes(RemotingHelper.DEFAULT_CHARSET));
            SendResult sendResult = producer.send(msg);
            System.out.printf("%s%n", sendResult);
        }
        producer.shutdown();
    }
}