package com.liuyang.interview;

import org.junit.Test;

import java.util.concurrent.Semaphore;

/**
 * 2个线程交替打印出A1B2C3...Z26
 */
public class I2 {

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
}
