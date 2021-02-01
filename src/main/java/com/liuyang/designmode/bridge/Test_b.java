package com.liuyang.designmode.bridge;

import org.junit.Test;

public class Test_b {

    @Test
    public void test(){

        Niu niu = new Niu(new Sichuan(), new ScoreGuarde());
        niu.desc();
    }
}
