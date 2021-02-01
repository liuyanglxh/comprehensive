package com.liuyang.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.concurrent.CyclicBarrier;

public class Test_1 {

    static final Logger logger = LoggerFactory.getLogger(Test_1.class);

    public static void main(String[] args) throws InterruptedException {
        CyclicBarrier c = new CyclicBarrier(5);
        for (int i = 0; i < 5; i++) {
            Thread.sleep(100);
            int j = i;
            new Thread(() -> {
                try {
                    System.out.println("i am prepare " + j);
                    c.await();
                    System.out.println("i am ok " + j);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    @Test
    public void test1() throws JsonProcessingException {
        MyPojo mp = new MyPojo();
        mp.put("name2", "b");
        mp.setName("a");
        String s = new ObjectMapper().writeValueAsString(mp);
        System.out.println(s);
    }

    static class MyPojo extends HashMap<String, String> {
        private String name;

        public String getName() {
            return name;
        }

        public MyPojo setName(String name) {
            this.name = name;
            return this;
        }
    }
}
