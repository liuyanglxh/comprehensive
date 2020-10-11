package com.liuyang.designmode.state.actions;

import com.liuyang.designmode.state.Car;

public class RunningAction implements CarAction {
    @Override
    public void start(Car car) {
        throw new UnsupportedOperationException("the car is running");
    }

    @Override
    public void run(Car car) {
        System.out.println(car.getName() + " : continue running");
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
