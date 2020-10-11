package com.liuyang.jvm;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class FrequentGC {

    public static void main1(String[] args) throws InterruptedException {
        //不断创建新对象，造成频繁ygc
//        for (int i = 0; i < 10; i++) {
//            new Thread(() -> {
//                while (true) {
//                    try {
//                        Thread.sleep(20);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    List<String> list = new ArrayList<>();
//                    for (int j = 0; j < 1000; j++) {
//                        list.add(String.valueOf(j));
//                    }
//                }
//            }).start();ls
//        }

        ExecutorService pool = Executors.newFixedThreadPool(2);
        while (true) {
            pool.execute(() -> {
                List<String> list = new ArrayList<>();
                for (int j = 0; j < 1000; j++) {
                    list.add(String.valueOf(j));
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

//        //生成一个存活时间较长的大对象，进入老年代
//        new Thread(() -> {
//            List<String> list = new ArrayList<>();
//            for (int j = 0; j < 100000; j++) {
//                list.add(String.valueOf(j));
//            }
//            try {
//                Thread.sleep(2000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }).start();
//
//        while (true) {
//            Thread.sleep(200000);
//        }
    }

    public static void main(String[] args) throws InterruptedException {
        new FrequentGC().test2();
    }

    @Test
    public void test1() throws InterruptedException {
        List<Thread> list = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            list.add(new Thread(() -> {
                while (true) {
                    byte[] bytes = new byte[new Random().nextInt(2048)];
                    if (bytes.length == 10000) break;
                }
            }, "test-" + i));
        }
        list.forEach(Thread::start);

        new CountDownLatch(1).await();
    }

    /**
     * 测试栈上分配及标量替换
     *
     * @throws InterruptedException
     */
    @Test
    public void test2() throws InterruptedException {
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                Random r = new Random();
                while (true) {
                    if (new Item(r.nextInt(Integer.MAX_VALUE)).test()) {
                        break;
                    }
                }
            }, "thread-" + i).start();
        }

        new CountDownLatch(1).await();
    }


    @Test
    public void test3() {
        ExecutorService pool = null;
        ThreadPoolExecutor p2 = null;
    }

    @Test
    public void test4() {
        while (true) {
            if ("a".length() == 0) {
                break;
            }
        }
    }

    class Item {
        int a;

        Item(int a) {
            this.a = a;
        }

        boolean test() {
            return a == 10;
        }
    }

}
