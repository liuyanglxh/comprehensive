package com.liuyang.arithmetic;

import java.util.ArrayList;
import java.util.List;

/**
 * 3个线程顺序输出a、b、c
 */
public class ThreadsOrderPrint {
    private static volatile int num = 3;

    public static void main(String[] args) {
        Object lock = new Object();

        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            threads.add(new Thread(() -> {
                synchronized (lock) {
                    switch (num) {
                        case 3:
                            System.out.println("a");
                            break;
                        case 2:
                            System.out.println("b");
                            break;
                        case 1:
                            System.out.println("c");
                            break;
                    }
                    num--;
                }
            }));
        }

        threads.forEach(Thread::start);

    }
}
