package com.liuyang.io.netty;

import org.junit.Test;

import java.lang.reflect.Field;
import java.nio.channels.SelectionKey;

public class Test_1 {

    @Test
    public void test1() throws IllegalAccessException {
        Field[] fields = SelectionKey.class.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            System.out.println(field.getName()+":"+field.get(SelectionKey.class));
        }
    }
}
