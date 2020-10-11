package com.liuyang.interview;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.LockSupport;
import java.util.stream.Stream;

/**
 * 2个线程交替打印出AaBbCc...Zz
 * 线程1打印ABC...Z
 * 线程2打印abc...z
 */
public class I2 {

    /**
     * 使用Semaphore
     */
    @Test
    public void test() throws InterruptedException {
        Semaphore s1 = new Semaphore(1);
        Semaphore s2 = new Semaphore(1);

        Thread t1 = new Thread(() -> {
            try {
                s2.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (int i = 'A'; i <= 'Z'; i++) {
                try {
                    s1.acquire();
                    System.out.println((char) i);
                    s2.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });
        Thread t2 = new Thread(() -> {
            for (int i = 1; i <= 26; i++) {
                try {
                    s2.acquire();
                    System.err.println(i);
                    s1.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t1.start();
        t2.start();
        t1.join();
        t2.join();
    }

    /**
     * 乐观锁
     */
    @Test
    public void test2() throws InterruptedException {

        AtomicInteger count = new AtomicInteger(0);


        Thread t1 = new Thread(() -> {
            char i = 'A';
            while (i <= 'Z') {
                if (count.get() % 2 == 0) {
                    i++;
                    count.addAndGet(1);
                }
            }
        });
        t1.start();

        Thread t2 = new Thread(() -> {
            char i = 'a';
            while (i <= 'z') {
                if (count.get() % 2 == 1) {
                    i++;
                    count.addAndGet(1);
                }
            }
        });
        t2.start();

        t1.join();
        t2.join();

    }

    /**
     * wait notify
     */
    @Test
    public void test3() throws InterruptedException {
        Object lock = new Object();
        CountDownLatch c = new CountDownLatch(2);

        new Thread(() -> {

            char i = 'A';
            while (true) {
                synchronized (lock) {
                    try {
                        System.out.println(i);
                        lock.notifyAll();
                        if (i == 'Z') {
                            break;
                        }
                        i++;
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            c.countDown();
        }).start();

        new Thread(() -> {
            char i = 'a';
            while (true) {
                synchronized (lock) {
                    try {
                        System.out.println("    " + i);
                        lock.notifyAll();
                        if (i == 'z') {
                            break;
                        }
                        i++;
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            c.countDown();
        }).start();

        c.await();

    }

    /**
     * LockSupport
     */
    @Test
    public void test4() throws InterruptedException {
        CountDownLatch c = new CountDownLatch(2);

        Thread[] ts = new Thread[2];

        ts[0] = new Thread(() -> {
            char i = 'A';
            while (true) {
                System.out.println(i++);
                LockSupport.unpark(ts[1]);
                if (i > 'Z') {
                    break;
                }
                LockSupport.park();
            }
            c.countDown();
        });

        ts[1] = new Thread(() -> {
            char i = 'a';
            LockSupport.park();
            while (true) {
                System.out.println("   " + i++);
                LockSupport.unpark(ts[0]);
                if (i > 'z') {
                    break;
                }
                LockSupport.park();
            }
            c.countDown();
        });

        Stream.of(ts).forEach(Thread::start);


        c.await();
    }


    /**
     * 自旋式
     */
    @Test
    public void test5() throws InterruptedException {

        class NumHolder {
            volatile int i;

            void add() {
                i++;
            }

            int get() {
                return i;
            }
        }

        NumHolder count = new NumHolder();

        Thread t1 = new Thread(() -> {
            char i = 'A';
            while (i <= 'Z') {
                if (count.get() % 2 == 0) {
                    System.out.println(i++);
                    count.add();
                }
            }
        });
        t1.start();

        Thread t2 = new Thread(() -> {
            char i = 'a';
            while (i <= 'z') {
                if (count.get() % 2 == 1) {
                    System.out.println("   " + i++);
                    count.add();
                }
            }
        });
        t2.start();

        t1.join();
        t2.join();
    }

    /**
     * BlockingQueue take()方法会阻塞
     */
    @Test
    public void test6() {

    }

    /**
     * Exchanger
     */
    @Test
    public void test7() {

    }


}
