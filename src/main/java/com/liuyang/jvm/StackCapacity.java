package com.liuyang.jvm;

public class StackCapacity {

    static int count = 0;

    public static void main(String[] args) {
        try {
            recursion1();
//            recursion2();
        } catch (Throwable e) {
            System.out.println(count);
        }
    }

    private static void recursion1() {

        int i1 = 1;
        int i2 = 2;
        int i3 = 3;
        int i4 = 4;

        int sum = i1 + i2 + i3;

        if (sum > 100) {
            return;
        }
        count++;

        recursion1();
    }

    private static void recursion2() {

        int i1 = 1;
        int sum = 0;
        sum += i1;
        i1 = 2;
        sum += i1;
        i1 = 3;
        sum += i1;
        i1 = 4;


        if (sum > 100) {
            return;
        }
        count++;

        recursion2();
    }

}
