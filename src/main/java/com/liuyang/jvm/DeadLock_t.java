package com.liuyang.jvm;

import com.liuyang.common.util.TimeUtil;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;

public class DeadLock_t {

    private Object lock1 = new Object();
    private Object lock2 = new Object();

    @Test
    public void test() throws InterruptedException {
        new Thread(() -> {
            synchronized (lock1) {
                System.out.println("t1-1");
                TimeUtil.SECOND.sleep(2);
                synchronized (lock2) {
                    System.out.println("t1-2...");
                }
            }
        }).start();

        new Thread(() -> {
            synchronized (lock2) {
                System.out.println("t2-1");
                TimeUtil.SECOND.sleep(2);
                synchronized (lock1) {
                    System.out.println("t2-2...");
                }
            }
        }).start();

        new CountDownLatch(1).await();
    }

}
