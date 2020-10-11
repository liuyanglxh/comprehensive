package com.liuyang.concurrent.queue_test;

import com.liuyang.common.util.TimeUtil;
import org.junit.Test;

import java.util.concurrent.*;

/**
 *
 */
public class BlockingQueue_test {

    private BlockingQueue<Integer> link = new LinkedBlockingDeque<>();
    private BlockingQueue<Integer> array = new ArrayBlockingQueue<>(10);

    @Test
    public void test1() throws InterruptedException {
        this.tst(link);
    }

    @Test
    public void test2() throws InterruptedException {
        this.tst(array);
    }

    private void tst(BlockingQueue<Integer> q) throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            q.put(i);
            System.out.println(q.poll());
        }
        System.out.println(q.size());
        q.put(11);
        System.out.println(q.size());
    }

    @Test
    public void test3() {
        new Thread(() -> {
            try {

                System.out.println("start...");
                Integer ele = link.poll(3, TimeUnit.SECONDS);
                System.out.println("ele is " + ele);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        TimeUtil.SECOND.sleep(1);
        System.out.println("添加元素咯");
        TimeUtil.SECOND.sleep(1);
        link.offer(1);
        TimeUtil.SECOND.sleep(1);

    }

    class DelayedData implements Delayed {

        private long time;
        private String name;

        public DelayedData(long time, String name) {
            this.time = time;
            this.name = name;
        }

        @Override
        public long getDelay(TimeUnit unit) {
            return unit.convert(time - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        }

        @Override
        public int compareTo(Delayed o) {
            if (this.getDelay(TimeUnit.MILLISECONDS) < o.getDelay(TimeUnit.MILLISECONDS)) {
                return -1;
            } else if (this.getDelay(TimeUnit.MILLISECONDS) > o.getDelay(TimeUnit.MILLISECONDS)) {
                return 1;
            }
            return 0;
        }
    }

    @Test
    public void test4() {
        BlockingQueue<DelayedData> delay = new DelayQueue<>();

        long now = System.currentTimeMillis();
        for (int i = 0; i < 5; i++) {
            delay.add(new DelayedData(now + (10 - i) * 1000L, String.valueOf(i)));
        }

        System.out.println(delay.size());
        System.out.println(delay.isEmpty());

        DelayedData d = delay.poll();
        while (d != null) {
            System.out.println(d.name);
            d = delay.poll();
        }

    }

    @Test
    public void test5() throws InterruptedException {
        SynchronousQueue<String> s = new SynchronousQueue<>();
        new Thread(() -> {
            try {
                TimeUtil.SECOND.sleep(1);
                System.out.println("start get");
                System.out.println(s.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        s.put("abc");
        System.out.println("finish put");
        TimeUtil.SECOND.sleep(1);
    }




}
