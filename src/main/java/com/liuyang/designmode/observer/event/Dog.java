package com.liuyang.designmode.observer.event;

import com.liuyang.designmode.observer.Kid;
import com.liuyang.designmode.observer.MyObserver;

public class Dog implements MyObserver<Kid> {
    @Override
    public void update(MyEvent<Kid> event) {
        switch (event.name()) {
            case "cry":
                System.out.println("wang ");
                break;
            case "smile":
                System.out.println("tail....");
        }

    }
}
