package com.liuyang.concurrent;

import com.liuyang.common.util.TimeUtil;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class Semaphore_test {


    @Test
    public void test() throws InterruptedException {
        Semaphore s1 = new Semaphore(1);
        Semaphore s2 = new Semaphore(1);

        Thread t1 = new Thread(() -> {
            while (true) {
                try {
                    s1.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + ":" + "获取到了");
                TimeUtil.SECOND.sleep(1);
                s2.release();
            }
        }, "'t1'");

        Thread t2 = new Thread(() -> {
            while (true) {
                try {
                    s2.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + ":" + "获取到了");
                TimeUtil.SECOND.sleep(1);
                s1.release();
            }
        }, "'t2'");

        t1.start();
        t2.start();

        new CountDownLatch(1).await();

    }

    @Test
    public void test2() throws InterruptedException {
        Semaphore s = new Semaphore(1);

        s.acquire();

        Thread t2 = new Thread(() -> {
            try {
                boolean b = s.tryAcquire(3, TimeUnit.SECONDS);
                System.out.println("t2 acquired " + b);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        t2.start();

        TimeUtil.SECOND.sleep(2);
        System.out.println("t1 release");
        s.release();

        t2.join();

    }

}
