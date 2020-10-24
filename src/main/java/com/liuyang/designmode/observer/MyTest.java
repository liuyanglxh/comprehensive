package com.liuyang.designmode.observer;

import com.liuyang.designmode.observer.event.Dog;
import com.liuyang.designmode.observer.event.Father;
import org.junit.Test;

public class MyTest {

    @Test
    public void test1() {
        Kid kid = new Kid("jack");
        kid.addObserver(new Mother());
        kid.addObserver(new Father());
        kid.addObserver(new Dog());

        kid.cry();
        System.out.println();
        kid.smile();
    }
}
