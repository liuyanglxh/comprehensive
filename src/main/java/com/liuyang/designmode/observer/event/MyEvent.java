package com.liuyang.designmode.observer.event;

public interface MyEvent<T> {

    T getSource();

    String name();
}
