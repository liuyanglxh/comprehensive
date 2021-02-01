package com.liuyang.designmode.bridge;

public abstract class Role {

    private BackGround backGround;
    private Position position;

    Role(BackGround backGround, Position position) {
        this.backGround = backGround;
        this.position = position;
    }

    void desc(){
        String d = String.format("this is 【%s】, a 【%s】, from 【%s】", name(), position.desc(), backGround.desc());
        System.out.println(d);
    }

    public abstract String name();
}
