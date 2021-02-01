package com.liuyang;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Stream;

public class Concurrent {

    public static void main(String[] args) {


    }

    public static Obj getObj() {
        Obj obj = new Obj();
        obj.setA("");
        obj.setB(1);
        obj.setC(0.01);

        return obj;
    }

    public static Obj getObjByFuture() {
        //Future
        Obj obj = new Obj();
        ExecutorService pool = Executors.newFixedThreadPool(3);
        Future<Boolean> a = pool.submit(() -> {
            obj.setA("");
            return true;
        });
        Future<Boolean> b = pool.submit(() -> {
            obj.setB(1);
            return true;
        });
        Future<Boolean> c = pool.submit(() -> {
            obj.setC(0.01);
            return true;
        });
        try {
            a.get();
            b.get();
            c.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public static Obj getObjByCountDownLatch() {
        Obj obj = new Obj();
        CountDownLatch c = new CountDownLatch(3);
        ExecutorService pool = Executors.newFixedThreadPool(3);
        pool.submit(() -> {
            try {
                obj.setA("");
            } finally {
                c.countDown();
            }
        });
        pool.submit(() -> {
            try {
                obj.setB(1);
            } finally {
                c.countDown();
            }
        });
        pool.submit(() -> {
            try {
                obj.setC(0.01);
            } finally {
                c.countDown();
            }
        });
        try {
            c.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return obj;
    }

    static class Obj {
        private String a;
        private Integer b;
        private Double c;

        public String getA() {
            return a;
        }

        public Obj setA(String a) {
            this.a = a;
            return this;
        }

        public Integer getB() {
            return b;
        }

        public Obj setB(Integer b) {
            this.b = b;
            return this;
        }

        public Double getC() {
            return c;
        }

        public Obj setC(Double c) {
            this.c = c;
            return this;
        }
    }
}
