package com.liuyang.jvm;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FrequentGC {

    public static void main(String[] args) throws InterruptedException {
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

}
