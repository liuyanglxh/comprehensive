package com.liuyang.interview;

import com.liuyang.common.util.TimeUtil;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.LockSupport;

/**
 * 1.实现一个容器，线程1写入数据，线程2监控数据，当总数达到5时，线程2给出通知并结束
 * 2.实现一个固定容量的容器，拥有put、get、getCount方法，支持2个生产者和10个消费者的阻塞调用
 */
public class I1 {

    volatile List<Integer> l = new ArrayList<>();

    @Test
    public void test() throws InterruptedException {

        Set s = new HashSet<>();

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                l.add(i);
                System.out.println("add");
            }
        });

        Thread t2 = new Thread(() -> {
            while (true) {
//                s.add(l.size());
                if (l.size() == 2) {
                    System.out.println("t2 out");
                    break;
                }
            }
        });

        t1.start();
        t2.start();
        t1.join();
        t2.join();
        s.forEach(System.out::println);

    }

    @Test
    public void test2() throws InterruptedException {
        CountDownLatch count = new CountDownLatch(5);
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                l.add(i);
                System.out.println("add " + i);
                count.countDown();
            }
        });
        new Thread(() -> {
            try {
                count.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("t2 out");
        }).start();
        t1.start();
        t1.join();

    }

    @Test
    public void test3() throws InterruptedException {
        Thread[] threads = new Thread[2];
        threads[0] = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                if (i == 5) {
                    LockSupport.unpark(threads[1]);
                    LockSupport.park();
                }
                l.add(i);
                System.out.println("add " + i);
            }
        });

        threads[1] = new Thread(() -> {
            LockSupport.park();
            System.out.println("t2 out");
            LockSupport.unpark(threads[0]);
        });

        threads[1].start();
        TimeUtil.MILLI_SECOND.sleep(1);
        threads[0].start();
        threads[1].join();
        threads[0].join();

    }

}
