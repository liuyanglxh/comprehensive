package com.liuyang.concurrent;

import com.liuyang.common.util.TimeUtil;
import org.junit.Test;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Function;
import java.util.function.Supplier;

public class ReadWriteLock_test {

    private ReadWriteLock lock = new ReentrantReadWriteLock();
    private Lock readLock = lock.readLock();
    private Lock writeLock = lock.writeLock();

    @Test
    public void test() {

        Runnable read = () -> {
            try {
                readLock.lock();
                TimeUtil.SECOND.sleep(1);
                System.out.println(Thread.currentThread().getName());
            } finally {
                readLock.unlock();
            }
        };
        Runnable write = () -> {
            try {
                writeLock.lock();
                TimeUtil.SECOND.sleep(2);
                System.out.println(Thread.currentThread().getName());
            } finally {
                writeLock.unlock();
            }
        };

        for (int i = 0; i < 10; i++) new Thread(read, "read " + i).start();
        for (int i = 10; i < 20; i++) new Thread(read, "read " + i).start();
        for (int i = 0; i < 2; i++) new Thread(write, "write " + i).start();

        TimeUtil.SECOND.sleep(10);

    }

    @Test
    public void test2() {
        readLock.lock();
        readLock.unlock();
        writeLock.lock();
        writeLock.unlock();
    }

    @Test
    public void test3() {
        System.out.println(1 << 16);
    }

    private Supplier<Integer> get1() {
        return () -> 1;
    }

    private Supplier<Integer> get2() {
        return () -> 1;
    }

    @Test
    public void test4() {
        Supplier<Integer> g1 = get1();
        Supplier<Integer> g1_1 = get1();
        Supplier<Integer> g2 = get2();
        System.out.println(g1);
        System.out.println(g1_1);
        System.out.println(g2);
    }


}
