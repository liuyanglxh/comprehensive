package com.liuyang.concurrent.forkjoin;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ExcecutorCalculator implements Calculator {

    //线程池大小和可用cpu核心数一致
    private ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    @Override
    public int sum(int[] arr) {
        int maxSize = 1000;
        //小于10000，直接循环执行
        if (arr.length <= maxSize) {
            return this.sum(arr, 0, arr.length);
        }
        int index = 0;
        List<Future<Integer>> futures = new ArrayList<>();
        while (index < arr.length) {
            int from = index;
            int to = from + 1000;
            Future<Integer> future = executorService.submit(() -> this.sum(arr, from, to));
            futures.add(future);
            index += maxSize;
        }
        int total = 0;
        for (Future<Integer> future : futures) {
            try {
                Integer subSum = future.get();
                total += subSum;
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        return total;
    }

    private int sum(int[] arr, int from, int to) {
        int total = 0;
        for (int i = from; i < to; i++) {
            total += arr[i];
        }
        return total;
    }


}
