package com.liuyang.designmode.bridge;

/*
 * bridge模式：
 *
 * 模拟街头篮球的角色
 *  特征1：背景（来自哪个城市）
 *  特征2：位置（分位、组织、小前、大前、中锋）
 *
 * 抽象类设计：
 *  角色Role
 *  背景BackGround
 *  位置Position
 *
 * 每一个Role实现类中，都要有一个BackGround和Position字段
 * */