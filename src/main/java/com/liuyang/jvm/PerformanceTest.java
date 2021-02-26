package com.liuyang.jvm;

import org.junit.Test;

public class PerformanceTest {

    private int times = 1_000_000;

    @Test
    public void test1() {
        long start = System.currentTimeMillis();
        for (int i = 0; i < times; i++) {
            if ((16 % 5) == 0) {
                break;
            }
        }
        System.out.println(System.currentTimeMillis() - start);
    }

    @Test
    public void test2() {
        long start = System.currentTimeMillis();
        for (int i = 0; i < times; i++) {
            if ((16 & 4) != 0) {
                break;
            }
        }
        System.out.println(System.currentTimeMillis() - start);
    }

}
