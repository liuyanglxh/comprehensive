package com.liuyang.jvm;

import org.junit.Test;

import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.*;

/**
 * 弱引用是针对key的，而对value的引用是强引用，因此要注意value对key不能有任何形式（直接或间接）的强引用
 */
public class WeakHashMap_t {

    @Test
    public void test() {
        Map<Rubbish, Object> weakMap = new WeakHashMap<>();

        Rubbish r1 = new Rubbish(1);
        weakMap.put(r1, "haha");
        weakMap.put(new Rubbish(2), "haha");
        System.out.println(r1);
        System.out.println(r1.hashCode());


        for (int i = 0; i < 10000000; i++) {
            Rubbish rubbish = new Rubbish(0);
            if (rubbish == null) {
                break;
            }
        }

        System.gc();
        System.out.println(r1);
        System.out.println(r1.hashCode());

        weakMap.keySet().forEach(r -> System.out.println(r.num));
    }

    @Test
    public void test2() throws InterruptedException, ExecutionException {
        ThreadPoolExecutor pool = new ThreadPoolExecutor(10, 20, 200, TimeUnit.SECONDS, new LinkedBlockingQueue<>()) {
            @Override
            protected void afterExecute(Runnable r, Throwable t) {
                super.afterExecute(r, t);
                if (t != null) {
                    System.out.println("线程池出错了");
                    t.printStackTrace();
                } else if (r instanceof Future) {
                    System.out.println("is future");
                    Future f = (Future) r;
                    if (!f.isDone()) {
                        try {
                            f.get();
                        } catch (Exception e) {
                            System.out.println("future出错了");
                            e.printStackTrace();
                        }
                    }
                }
            }
        };
        pool.execute(() -> {
            throw new RuntimeException("test");
        });

        Future<Integer> f = pool.submit(() -> 1 / 0);


        new CountDownLatch(1).await();
    }


    class Rubbish {

        private int num;

        public Rubbish(int num) {
            this.num = num;
        }

        @Override
        protected void finalize() {
            if (num == 1 || num == 2) System.out.println(this.num + " is being collected");
        }
    }
}
