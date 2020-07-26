package com.liuyang.jvm;

import java.util.ArrayList;
import java.util.List;

public class Volatile {

    private static boolean flag = false;

    public static void main(String[] args) throws InterruptedException {

        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            threads.add(new Thread(() -> {
                while (true) {
                    if (flag) {
                        System.out.println("出去咯");
                        break;
                    }
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }));
        }

        threads.forEach(Thread::start);

        flag = true;

        for (Thread thread : threads) {
            thread.join();
        }

    }
}
