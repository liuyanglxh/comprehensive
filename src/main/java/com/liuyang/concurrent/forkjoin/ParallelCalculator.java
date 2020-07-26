package com.liuyang.concurrent.forkjoin;

import java.util.Arrays;

public class ParallelCalculator implements Calculator {
    @Override
    public int sum(int[] arr) {
        return Arrays.stream(arr).parallel().reduce(0, Integer::sum);
    }
}
