package com.liuyang.jvm;

/**
 * 空间担保分配失败
 * -Xms20M -Xmx20M -Xmn10M -XX:PrintGCDetails -XX:SurvivorRatio=8
 * -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8 -XX:+UseConcMarkSweepGC
 */
public class GCTest2 {

    private static final int _1MB = 1024 * 1024;

    public static void main(String[] args) {
//        oom();
//        fullGC();
//        cms();
        cms2();
    }

    private static void cms2() {
        byte[] a1, a2, a3, a4, a5, a6, a7, a8, a9, a10;
        byte[] b, c;
        a1 = create(_1MB, 1);
        a2 = create(_1MB, 2);
        a3 = create(_1MB, 3);
        a4 = create(_1MB, 4);
        a5 = create(_1MB, 5);

        b = create(5 * _1MB, 6);
        b = null;

        a6 = create(_1MB, 7);
        a7 = create(_1MB, 8);
        //gc
        a8 = create(_1MB, 9);

        b = create(5 * _1MB, 10);
        b = null;

        a1 = null;
        a3 = null;
        a5 = null;
        a7 = null;

        b = create(4 * _1MB, 11);
        b = null;
        b = create(4 * _1MB, 12);
    }

    private static void cms() {
        byte[] a1, a2, a3, a4, a5, a6, a7, a8, a9, a10;
        a1 = create(_1MB, 1);//1
        a2 = create(_1MB, 2);//2
        a3 = create(_1MB, 3);//3
        a4 = create(_1MB, 4);//4

        a5 = create(5 * _1MB, 5);//5
        a5 = null;
        a7 = create(_1MB, 6);//6
        a8 = create(_1MB, 7);//7
        //minor gc
        a9 = create(_1MB, 8);//8
        //  1   6
        a10 = create(5 * _1MB, 9);//9
        //  6   6
        a10 = null;

        a1 = null;
        a3 = null;
        a7 = null;

        a5 = create(5 * _1MB, 10);//10
        a10 = create(5 * _1MB, 11);//11
    }

    private static void fullGC() {
        byte[] a1, a2, a3, a4;

        int time = 0;
        a1 = create(6 * _1MB, ++time);
        //发生一次minorGC，然后继续在eden分配
        System.out.println("minor1");
        a1 = null;
        a1 = create(6 * _1MB, ++time);
        //发生一次minorGC，a1晋升到老年代，a2在eden分配
        System.out.println("minor2");
        a2 = create(6 * _1MB, ++time);
        a1 = null;
        //空间不够，触发minorGC，a2会晋升到老年代，但是空间不够，会触发oldGC
        System.out.println("cms");
        a3 = create(6 * _1MB, ++time);
    }

    private static void oom() {
        byte[] a1, a2, a3, a4;

        int time = 0;
        a1 = create(6 * _1MB, ++time);
        a2 = create(6 * _1MB, ++time);
        a3 = create(6 * _1MB, ++time);
    }

    private static byte[] create(int c, int time) {
        byte[] bytes = new byte[c];
        System.out.println("create" + time);
        return bytes;
    }
}
