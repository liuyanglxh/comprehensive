package com.liuyang.designmode.state.actions;

import com.liuyang.designmode.state.Car;

public class StartAction implements CarAction {
    @Override
    public void start(Car car) {
        throw new UnsupportedOperationException("already started");
    }

    @Override
    public void run(Car car) {
        System.out.println("running");
    }

    @Override
    public void stop(Car car) {
        System.out.println("stop");
    }

    @Override
    public void oil(Car car) {
        throw new UnsupportedOperationException("dangerous operation");
    }
}
