package com.liuyang.designmode.state;

import com.liuyang.designmode.state.actions.*;

public enum CarState{
    DEAD(new DeadAction()),
    STOP(new StopAction()),
    START(new StartAction()),
    RUNNING(new RunningAction());

    private CarAction action;

    CarState(CarAction action) {
        this.action = action;
    }

    public CarAction getAction() {
        return action;
    }
}
