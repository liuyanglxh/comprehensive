package com.liuyang.jvm;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class G1Logs {

    public static void main(String[] args) {
        ThreadLocalRandom current = ThreadLocalRandom.current();
        List<String> lst = new ArrayList<>();

        while (true) {
            int i = current.nextInt();
            if (i % 10 == 0) {
                lst.add(String.valueOf(i));
            }
            if (lst.size() == 1000000) {
                lst.clear();
            }
        }
    }
}
