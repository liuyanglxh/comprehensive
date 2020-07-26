package com.liuyang.concurrent.forkjoin;

public class LoopCalculator implements Calculator {
    @Override
    public int sum(int[] arr) {
        int total = 0;
        for (int i : arr) {
            total += i;
        }
        return total;
    }
}
