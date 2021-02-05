package com.liuyang.io.netty;

import org.junit.Test;

import java.lang.reflect.Field;
import java.nio.channels.SelectionKey;

public class Test_1 {

    @Test
    public void test1() throws IllegalAccessException {
        System.out.println(1 << 15 | 1 << 16);
    }
}
