package com.liuyang.jvm;

import java.util.HashMap;
import java.util.Map;

public class JoinTest {

    public static void main(String[] args) throws InterruptedException {

        Map<String, String> map = new HashMap<String, String>() {{
            put("1", "1");
            put("2", "2");
        }};

        System.out.println(map.getClass());

    }


}
