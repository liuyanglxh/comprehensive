package com.liuyang.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

/**
 * 避免缓存穿透
 */
public class ResourceLock<T> {

    private Map<T, T> lockMap = new ConcurrentHashMap<>();

    public <V> V tryLock(T t, Supplier<V> successJob, Supplier<V> failJob) throws InterruptedException {
        Object obj = lockMap.put(t, t);
        boolean success = obj == null;
        T waitObj = lockMap.get(t);

        synchronized (waitObj) {
            if (success) {
                V v = successJob.get();
                waitObj.notifyAll();
                return v;
            } else {
                waitObj.wait(2000);
                return failJob.get();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        List<Thread> list = new ArrayList<>();

        ResourceLock<Integer> lock = new ResourceLock<>();

        int id = 10;
        for (int i = 0; i < 20; i++) {
            list.add(new Thread(() -> {
                try {
                    String s = lock.tryLock(id, () -> getFromDb(id), () -> getFromCache(id));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }));
        }

        list.forEach(Thread::start);

        Thread.sleep(10000);

    }

    private static String getFromDb(Integer id) {
        try {
            System.out.println("from db");
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return id.toString();
    }

    private static String getFromCache(Integer id) {
        System.out.println("from cache");
        return id.toString();
    }

}














