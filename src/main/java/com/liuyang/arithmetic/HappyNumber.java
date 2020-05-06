package com.liuyang.arithmetic;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

public class HappyNumber {

    @Test
    public void test() {
        System.out.println(isHappy(1474573736));
        System.out.println(isHappy(1));
        System.out.println(isHappy(19));
    }

    public boolean isHappy(int n) {
        Set<Long> exist = new HashSet<>();

        long m = n;
        while (true) {
            m = this.divAdd(m);
            if (m == 1) {
                return true;
            }
            if (exist.contains(m)) {
                return false;
            }
            exist.add(m);
        }
    }

    private long divAdd(long m) {
        int num = 0;

        while (m > 0) {
            num += m % 10;
            m /= 10;
        }

        return num;
    }


}
