package com.liuyang.concurrent.lock_t;

import com.liuyang.common.util.TimeUtil;
import org.junit.Test;

import java.util.concurrent.locks.StampedLock;

public class StampedLock_t {

    StampedLock lock = new StampedLock();

    @Test
    public void test() {
        String tName = Thread.currentThread().getName();
        long start = System.currentTimeMillis();

        long readStamp = lock.readLock();
        System.out.println((System.currentTimeMillis() - start) + "读锁" + tName);

        new Thread(() -> writeLock(start)).start();
        new Thread(() -> readLock(start)).start();

        TimeUtil.SECOND.sleep(3);
        lock.unlockRead(readStamp);
        System.out.println((System.currentTimeMillis() - start) + "释放读" + tName);

        TimeUtil.SECOND.sleep(2);
    }

    private void readLock(long start) {
        String tName = Thread.currentThread().getName();
        TimeUtil.MILLI_SECOND.sleep(1);
        long stamp = lock.readLock();
        try {
            System.out.println((System.currentTimeMillis() - start) + "写锁" + tName);
            TimeUtil.SECOND.sleep(1);
        } finally {
            lock.unlockRead(stamp);
            System.out.println((System.currentTimeMillis() - start) + "释放写" + tName);
        }
    }

    private void writeLock(long start) {
        String tName = Thread.currentThread().getName();
        TimeUtil.SECOND.sleep(1);
        long stamp = lock.writeLock();
        try {
            System.out.println((System.currentTimeMillis() - start) + "写锁" + tName);
            TimeUtil.SECOND.sleep(1);
        } finally {
            lock.unlockWrite(stamp);
            System.out.println((System.currentTimeMillis() - start) + "释放写" + tName);
        }
    }
}
