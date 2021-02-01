package com.liuyang.designmode.bridge;

public class Niu extends Role {


    public Niu(BackGround backGround, Position position) {
        super(backGround, position);
    }

    @Override
    public String name() {
        return "niu pi";
    }
}
