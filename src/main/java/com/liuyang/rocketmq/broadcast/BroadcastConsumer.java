package com.liuyang.rocketmq.broadcast;

import com.liuyang.rocketmq.CommonUtil;
import com.liuyang.rocketmq.RocketConfig;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;

import java.util.List;

public class BroadcastConsumer {

    public static final String TAG = "broadcast";

    public static void main(String[] args) throws Exception {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(RocketConfig.CONSUMER_GROUP);
        consumer.setNamesrvAddr(RocketConfig.NAME_SVR);
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);

        //set to broadcast mode
        consumer.setMessageModel(MessageModel.BROADCASTING);

        consumer.subscribe("TopicTest", "*");
        long start = System.currentTimeMillis();
        consumer.registerMessageListener((MessageListenerConcurrently) (msgs, context) -> {
            MessageExt ext = msgs.get(0);
            boolean success = CommonUtil.success(ext, 3);
            System.out.println(new String(ext.getBody()) + "[" + success + "][" + (System.currentTimeMillis() - start) / 1000 + "]");
            return success ? ConsumeConcurrentlyStatus.CONSUME_SUCCESS : ConsumeConcurrentlyStatus.RECONSUME_LATER;
//            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });

        consumer.start();
        System.out.printf("Broadcast Consumer Started.%n");
    }
}