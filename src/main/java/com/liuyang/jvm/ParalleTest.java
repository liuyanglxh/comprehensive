package com.liuyang.jvm;


public class ParalleTest {

    static class M {

        synchronized void m() {
            System.out.println("m");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("m out");
        }
        void m1(){
            System.out.println("m1");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        M m = new M();
        Thread t1 = new Thread(m::m);
        Thread t2 = new Thread(m::m1);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("ok");
    }
}
