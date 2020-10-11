package com.liuyang.designmode.state;

import org.junit.Test;

public class CarTest {

    @Test
    public void test1() {
        new Car(CarState.DEAD).start();
        new Car(CarState.START).start();
        new Car(CarState.RUNNING).start();
        new Car(CarState.STOP).start();
        new Car(CarState.DEAD).oil();
        new Car(CarState.DEAD).stop();
    }

}
