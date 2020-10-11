package com.liuyang.designmode;
/*
设计模式实例，案例：桌面游戏-坦克大战：

1.策略模式：
    坦克可以发出子弹，定义一个子弹接口Bullet，然后传入坦克对象中，根据实际情况设置多种不同的子弹实现类
2.工厂模式（二选一）：
    1）抽象工厂：
        定义一个抽象工厂，有方法：getShape、getColor、getSize等，定义好坦克的各种细节
    2）简单工厂：
        定义一个简单工厂，只有方法：getTank，在内部封装好坦克的各种细节
3.观察者模式：
    监听键盘事件
4.状态模式：
    坦克的多种行为，需要根据当前的状态来决定如何行动，例如：
    1）移动：【正常】--可以移动；【定身】--无法移动；设计一个接口：TankAction，有方法move；有2种
        实现类：NormalTankMove和ImmobileTankMove，对tank调用move方法时，根据状态，选择一个
        具体的TankAction
5.




































 * */