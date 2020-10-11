package com.liuyang.concurrent;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThreadLocalLeak_test {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Test
    public void test() {
        ThreadLocal<byte[]> t = new ThreadLocal<>();
        while (true) {
            new Thread(() -> {
                byte[] bs = new byte[1024];
                t.set(bs);
            }).start();
        }
    }


}
