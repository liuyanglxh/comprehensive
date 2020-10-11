package com.liuyang.designmode.state.actions;

import com.liuyang.designmode.state.Car;

public class StopAction implements CarAction {
    @Override
    public void start(Car car) {
        System.out.println("start");
    }

    @Override
    public void run(Car car) {
        System.out.println("start running");
    }

    @Override
    public void stop(Car car) {
        throw new UnsupportedOperationException("already stopped");
    }

    @Override
    public void oil(Car car) {
        throw new UnsupportedOperationException("dangerous operation");
    }
}
