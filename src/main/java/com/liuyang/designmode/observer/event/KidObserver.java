package com.liuyang.designmode.observer.event;

import com.liuyang.designmode.observer.Kid;
import com.liuyang.designmode.observer.MyObserver;

public abstract class KidObserver implements MyObserver<Kid> {

    @Override
    public void update(MyEvent<Kid> event) {
        switch (event.name()) {
            case "cry":
                this.onCry(event);
                break;
            case "smile":
                this.onSmile(event);
        }
    }

    public void onCry(MyEvent<Kid> event) {

    }

    public void onSmile(MyEvent<Kid> event) {

    }
}
