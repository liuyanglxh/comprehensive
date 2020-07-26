package com.liuyang.concurrent.forkjoin;

import org.junit.Test;

import java.util.*;

public class CalculatorTest {

    @Test
    public void showWeek(){
        Calendar c = Calendar.getInstance();
        System.out.println(c.getWeeksInWeekYear());
        System.out.println(c.getWeekYear());
    }

    private int[] arr;
    private int num = 500_000_000;

    {
//        arr = new int[num];
//        for (int i = 0; i < num; i++) {
//            arr[i] = 1;
//        }
    }

    @Test
    public void testLoop() {
        this.doCalculate(new LoopCalculator());
    }

    @Test
    public void testExecutorService() {
        this.doCalculate(new ExcecutorCalculator());
    }

    @Test
    public void testForkJoin() {
        this.doCalculate(new ForkjoinCalculator());
    }

    @Test
    public void testParallel() {
        this.doCalculate(new ParallelCalculator());
    }

    private void doCalculate(Calculator calculator) {
        long start = System.currentTimeMillis();
        int sum = calculator.sum(arr);
        long end = System.currentTimeMillis();
        System.out.println("sum is " + sum);
        System.out.println("time is " + (end - start));
    }

    Set<Long> set = new HashSet<>();
    Set<Long> curr = new HashSet<>();

    @Test
    public void test() throws InterruptedException {
        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < 30; i++) {
            threads.add(new Thread(this::run));
        }
        threads.forEach(Thread::start);
        for (Thread thread : threads) {
            thread.join();
        }
        set.removeAll(curr);
        set.stream().sorted().forEach(System.out::println);
    }

    private void run() {
        Set<Thread> threads = new HashSet<>();
        curr.add(Thread.currentThread().getId());
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 1000000; i++) {
            list.add(i);
        }
        list.parallelStream().forEach(i -> {
            if (i == -1) {
                System.out.println("is 0");
            }
            threads.add(Thread.currentThread());
        });
        threads.stream().map(Thread::getId).sorted().forEach(set::add);
    }


}
