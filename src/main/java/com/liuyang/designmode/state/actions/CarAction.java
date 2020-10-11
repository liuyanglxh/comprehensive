package com.liuyang.designmode.state.actions;

import com.liuyang.designmode.state.Car;

public interface CarAction {

    void start(Car car);

    void run(Car car);

    void stop(Car car);

    void oil(Car car);
}
