package com.liuyang.designmode.observer;

import com.liuyang.designmode.observer.event.MyEvent;

/**
 * 抽象观察者
 */
public interface MyObserver<T> {

    void update(MyEvent<T> event);

}
