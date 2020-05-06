package com.liuyang.arithmetic;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HappyNumber {

    @Test
    public void test() {
        System.out.println(isHappy(1474573736));
        System.out.println(isHappy(1));
    }

    public boolean isHappy(int n) {
        Set<Long> exist = new HashSet<>();

        long m = n;
        while (true) {
            m = this.add(m);
            if (m == 1) {
                return true;
            }
            if (exist.contains(m)) {
                return false;
            }
            exist.add(m);
        }
    }

    private long add(long m) {

        long mod = 1;
        long add = 0;

        while (m >= mod) {
            long i = mod(m, mod);
            add += i * i;
            mod *= 10;
        }

        return add;
    }

    private long mod(long m, long mod) {
        return (m / mod) % 10;
    }

}
