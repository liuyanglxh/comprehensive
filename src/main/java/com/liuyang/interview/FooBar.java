package com.liuyang.interview;

import java.util.concurrent.Semaphore;

class FooBar {
    private int n;

    public FooBar(int n) {
        this.n = n;
    }

    private Semaphore s1 = new Semaphore(1);
    private Semaphore s2 = new Semaphore(1);

    public void foo(Runnable printFoo) throws InterruptedException {
        s2.acquire();
        for (int i = 0; i < n; i++) {
            s1.acquire();
        	// printFoo.run() outputs "foo". Do not change or remove this line.
        	printFoo.run();
        	s2.release();
        }
    }

    public void bar(Runnable printBar) throws InterruptedException {
        
        for (int i = 0; i < n; i++) {
            s2.acquire();
            // printBar.run() outputs "bar". Do not change or remove this line.
        	printBar.run();
        	s1.release();
        }
    }
}