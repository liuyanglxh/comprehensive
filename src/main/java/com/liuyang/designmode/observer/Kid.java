package com.liuyang.designmode.observer;

import com.liuyang.designmode.observer.event.Cry;
import com.liuyang.designmode.observer.event.Smile;

import java.util.HashSet;
import java.util.Set;

public class Kid implements MyObserverble {

    private Set<MyObserver> observers = new HashSet<>();
    private String name;

    public Kid(String name) {
        this.name = name;
    }

    @Override
    public void addObserver(MyObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(MyObserver observer) {
        observers.remove(observer);
    }

    public void cry() {
        System.out.println("kid cries");
        observers.forEach(o -> o.update(new Cry(this)));
    }

    public void smile() {
        System.out.println("kid smiles");
        observers.forEach(o -> o.update(new Smile(this)));
    }

    public String getName() {
        return name;
    }
}
