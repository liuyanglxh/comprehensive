package com.liuyang.concurrent;

import com.liuyang.common.util.TimeUtil;
import org.junit.Test;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadPool_T {

    /**
     * 测试线程池特性是下面的哪一种：
     * 1.核心线程不够了，马上启用备用线程
     * 2.核心线程不够，放入队列，队列满了，再启用备用线程
     */
    @Test
    public void test1() throws Exception {
        /*
        线程池，核心线程1个，队列长度1，开启2个任务，输出线程名称
         */
        ThreadPoolExecutor pool = new ThreadPoolExecutor(1, 10, 1000, TimeUnit.SECONDS, new ArrayBlockingQueue<>(1), new MyFactory());

        int time = 1;
        pool.execute(() -> {
            TimeUtil.SECOND.sleep(time);
            System.out.println(Thread.currentThread().getName());
        });
        pool.execute(() -> {
            TimeUtil.SECOND.sleep(time);
            System.out.println(Thread.currentThread().getName());
        });
//        pool.execute(() -> {
//            TimeUtil.SECOND.sleep(time);
//            System.out.println(Thread.currentThread().getName());
//        });


        TimeUtil.SECOND.sleep(5);
    }

    class MyFactory implements ThreadFactory {

        private AtomicInteger count = new AtomicInteger(0);

        @Override
        public Thread newThread(Runnable r) {
            String prefix = "test-";
            return new Thread(r, prefix + count.getAndAdd(1));
        }
    }

}
