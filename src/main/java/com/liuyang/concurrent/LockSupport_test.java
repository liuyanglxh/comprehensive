package com.liuyang.concurrent;

import com.liuyang.common.util.TimeUtil;
import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.locks.LockSupport;

public class LockSupport_test {

    @Test
    public void test1() {
        Thread t1 = new Thread(() -> {
            while (true) {
                System.out.println(System.currentTimeMillis());
                LockSupport.park();
            }
        });
        t1.start();

        while (true) {
            TimeUtil.SECOND.sleep(2);
            System.out.println("awake...");
            LockSupport.unpark(t1);
            LockSupport.unpark(t1);
        }
    }

    @Test
    public void test2() throws InterruptedException {

        Thread t1 = new Thread(() -> LockSupport.parkUntil(System.currentTimeMillis() + 200000L), "t1");

        t1.start();
        t1.join();
    }

    @Test
    public void test3() throws ExecutionException, InterruptedException {
        FutureTask<String> ft = new FutureTask<>(() -> "123");
        new Thread(ft).start();
        String s = ft.get();
        System.out.println(s);
    }

    @Test
    public void test4(){
        LockSupport.park();
    }

}
