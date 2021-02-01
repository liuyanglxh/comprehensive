package com.liuyang.concurrent;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Deprecated
public class ReadWriteLockQueue<T> {

    private ConcurrentLinkedQueue<T> queue = new ConcurrentLinkedQueue<>();

    private ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
    private Lock addLock = reentrantReadWriteLock.readLock();
    private Condition notEmpty = addLock.newCondition();//共享锁不支持condition
    private Lock takeLock = reentrantReadWriteLock.writeLock();

    public void add(T t) {
        addLock.lock();
        try {
            queue.add(t);
            notEmpty.signal();
        } finally {
            addLock.unlock();
        }
    }

    public T poll() {
        return queue.poll();
    }

    public T poll(long waitTime) {
        T t = queue.poll();
        if (t != null) return t;

        takeLock.lock();
        try {
            if ((t = queue.poll()) != null) return t;

            if (waitTime <= 0) {
                notEmpty.await();
            } else {
                notEmpty.await(waitTime, TimeUnit.MILLISECONDS);
            }
            return queue.poll();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            takeLock.unlock();
        }
    }

    public T take() {
        return poll(0);
    }

}
