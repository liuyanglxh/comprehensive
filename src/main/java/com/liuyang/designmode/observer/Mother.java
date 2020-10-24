package com.liuyang.designmode.observer;

import com.liuyang.designmode.observer.event.KidObserver;
import com.liuyang.designmode.observer.event.MyEvent;

public class Mother extends KidObserver {

    @Override
    public void onCry(MyEvent<Kid> event) {
        System.out.println("mother feeds " + event.getSource().getName());
    }

    @Override
    public void onSmile(MyEvent<Kid> event) {
        System.out.println("mother smiles too");
    }
}
