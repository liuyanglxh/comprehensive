package com.liuyang.designmode.observer.event;

import com.liuyang.designmode.observer.Kid;
import com.liuyang.designmode.observer.MyObserver;

public class Father implements MyObserver<Kid> {
    @Override
    public void update(MyEvent<Kid> event) {
        Kid source = event.getSource();
        System.out.println("father sees " + source.getName() + " " + event.name());
    }
}
