package com.liuyang.designmode.observer.event;

import com.liuyang.designmode.observer.Kid;

public class Cry implements MyEvent<Kid> {

    private Kid source;

    public Cry(Kid source) {
        this.source = source;
    }

    @Override
    public Kid getSource() {
        return source;
    }

    @Override
    public String name() {
        return "cry";
    }


}
