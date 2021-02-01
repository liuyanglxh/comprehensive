package com.liuyang.forjob;

import org.junit.Test;

import java.util.Map;

public class Test1 {

    @Test
    public void test1() {
        a();
    }

    void a() {
        b();
    }

    void b() {
        c();
    }

    void c() {
        d();
    }

    void d() {
        e();
    }

    void e() {
        for (StackTraceElement element : Thread.currentThread().getStackTrace()) {
            String s = element.getClassName() + ":" + element.getLineNumber();
            System.out.println(s);
        }
    }
}
