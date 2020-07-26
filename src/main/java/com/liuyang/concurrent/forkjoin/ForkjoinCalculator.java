package com.liuyang.concurrent.forkjoin;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ForkjoinCalculator implements Calculator {

    class RecursiveCalculator extends RecursiveTask<Integer> {

        private int[] arr;
        private int from;
        private int to;

        public RecursiveCalculator(int[] arr, int from, int to) {
            this.arr = arr;
            this.from = from;
            this.to = to;
        }

        @Override
        protected Integer compute() {
            if (to - from < 10000) {
                int total = 0;
                for (int i = from; i < to; i++) {
                    total += arr[i];
                }
                return total;
            }

            int middle = (to - from) / 2;
            RecursiveCalculator left = new RecursiveCalculator(arr, from, middle);
            RecursiveCalculator right = new RecursiveCalculator(arr, middle, to);

            left.fork();
            right.fork();

            return left.join() + right.join();
        }
    }

    @Override
    public int sum(int[] arr) {
        ForkJoinPool pool = ForkJoinPool.commonPool();
        return pool.invoke(new RecursiveCalculator(arr, 0, arr.length));
    }
}
