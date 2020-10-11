package com.liuyang.designmode.state.actions;

import com.liuyang.designmode.state.Car;

public class DeadAction implements CarAction {
    @Override
    public void start(Car car) {
        System.out.println("start");
    }

    @Override
    public void run(Car car) {
        throw new UnsupportedOperationException("start the car first");
    }

    @Override
    public void stop(Car car) {
        throw new UnsupportedOperationException("dead is stopped");
    }

    @Override
    public void oil(Car car) {
        System.out.println("oiling");
    }
}
