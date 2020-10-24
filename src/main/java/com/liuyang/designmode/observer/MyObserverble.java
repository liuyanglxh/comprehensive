package com.liuyang.designmode.observer;

/**
 * 抽象被观察者
 */
public interface MyObserverble {

    void addObserver(MyObserver observer);

    void removeObserver(MyObserver observer);
}
