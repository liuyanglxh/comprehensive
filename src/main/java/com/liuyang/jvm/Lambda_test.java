package com.liuyang.jvm;

import org.junit.Test;

import java.util.function.Supplier;

public class Lambda_test {

    @Test
    public void test1() {
        A a1 = this.job(true, A::new);
        A a2 = this.job(false, A::new);
    }


    private <T> T job(boolean ok, Supplier<T> s) {
        return ok ? s.get() : null;
    }

    class A {
        public A() {
            System.out.println("create");
        }
    }



}
