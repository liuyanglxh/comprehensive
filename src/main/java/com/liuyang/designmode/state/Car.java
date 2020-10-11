package com.liuyang.designmode.state;

import com.liuyang.designmode.state.actions.CarAction;

public class Car {

    private String name;
    private CarState state;
    private CarAction action;

    public Car(CarState state) {
        this.state = state;
    }

    void start() {
        state.getAction().start(this);
    }

    void run() {
        state.getAction().run(this);
    }

    void stop() {
        state.getAction().stop(this);
    }

    void oil() {
        state.getAction().oil(this);
    }

    public CarState getState() {
        return state;
    }

    public void setState(CarState state) {
        this.state = state;
    }

    public CarAction getAction() {
        return action;
    }

    public void setAction(CarAction action) {
        this.action = action;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
