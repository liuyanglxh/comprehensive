package com.liuyang.concurrent.forkjoin;

import org.junit.Test;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.atomic.AtomicLong;

public class ForkJoin_t {

    @Test
    public void test() {
        ForkJoinPool pool = ForkJoinPool.commonPool();
        int[] nums = new int[100000];
        for (int i = 0; i < nums.length; i++) {
            nums[i] = 1;
        }

        MyTask t = new MyTask(0, nums.length - 1, nums, new AtomicLong(0));
        pool.execute(t);
        System.out.println(t.join());

    }

    static class MyTask extends RecursiveTask<Long> {

        private int start, end;
        private int[] nums;
        private AtomicLong count;

        private MyTask(int start, int end, int[] nums, AtomicLong count) {
            this.start = start;
            this.end = end;
            this.nums = nums;
            this.count = count;
        }

        @Override
        protected Long compute() {
            if (end - start <= 10000) {
                int num = 0;
                for (int i = start; i <= end; i++) num += nums[i];
                return count.addAndGet(num);
            } else {
                int middle = (end + start) / 2;
                MyTask t1 = new MyTask(start, middle - 1, nums, count);
                MyTask t2 = new MyTask(middle, end, nums, count);
                t1.fork();
                t2.fork();
                return t1.join() + t2.join();
            }

        }

    }


}
