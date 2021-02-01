package com.liuyang.rocketmq;

import org.apache.rocketmq.common.message.MessageExt;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class CommonUtil {

    private static final Map<String, AtomicInteger> countMap = new ConcurrentHashMap<>();

    public static boolean success(MessageExt ext, int count) {
        String msgId = ext.getMsgId();
        boolean enough = countMap.computeIfAbsent(msgId, a -> new AtomicInteger()).addAndGet(1) == count;
        if (!enough) {
            return false;
        }
        countMap.remove(msgId);
        return true;
    }
}
