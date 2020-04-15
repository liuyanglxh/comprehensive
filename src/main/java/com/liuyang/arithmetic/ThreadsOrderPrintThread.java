package com.liuyang.arithmetic;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 3个线程顺序输出a、b、c
 */
public class ThreadsOrderPrintThread {
    private static volatile int num = 3;

    @Test
    public void test1() throws InterruptedException {

        PrintThread p = new PrintThread("c", new PrintThread("b", new PrintThread("a")));
        p.start();
        p.join();

    }

    class PrintThread extends Thread {

        private String msg;

        private PrintThread pre;

        public PrintThread(String msg) {
            this.msg = msg;
        }

        public PrintThread(String msg, PrintThread pre) {
            this.msg = msg;
            this.pre = pre;
        }


        @Override
        public void run() {
            try {
                this.doTheJoin();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        private void doTheJoin() throws InterruptedException {
            /*
            先异步开启pre线程，然后执行当前线程工作，完成后，等待pre线程结束
             */
            if (pre != null) {
                pre.start();
                this.doSomeThing();
                pre.join();
            } else {
                doSomeThing();
            }
            System.out.println(this.msg);
        }

        /*
        当前线程的工作
         */
        private void doSomeThing() throws InterruptedException {
            Thread.sleep(ThreadLocalRandom.current().nextInt(100,200));
            System.out.println("msg is " + msg);
        }
    }

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
