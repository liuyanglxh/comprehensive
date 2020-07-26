package com.liuyang.jvm;

/**
 * 大对象优先在eden区分配
 * -Xms20M -Xmx20M -Xmn10M -XX:PrintGCDetails -XX:SurvivorRatio=8
 *
 * 结论：
 * 1.serial组合比较直观
 * 2.parallel组合，在新生代有1MB左右的未知对象（集）
 * 3.parNew+CMS组合，同样有1+MB多的未知对象
 */
public class GCTest1 {

    private static final int _1MB = 1024 * 1024;

    public static void main(String[] args) {
//        oom();
        fullGC();
    }

    private static void fullGC() {
        byte[] a1, a2, a3, a4;

        a1 = create(6 * _1MB);
        //发生一次minorGC，然后继续在eden分配
        System.out.println("minor1");
        a1 = null;
        a1 = create(6 * _1MB);
        //发生一次minorGC，a1晋升到老年代，a2在eden分配
        System.out.println("minor2");
        a2 = create(6 * _1MB);
        a1 = null;
        //空间不够，触发minorGC，a2会晋升到老年代，但是空间不够，会触发fullGC
        System.out.println("cms");
        a3 = create(6 * _1MB);
    }

    private static void oom() {
        byte[] a1, a2, a3, a4;

        a1 = create(6 * _1MB);
        a2 = create(6 * _1MB);
        a3 = create(6 * _1MB);
    }

    private static byte[] create(int c) {
        byte[] bytes = new byte[c];
        System.out.println("create");
        return bytes;
    }
}
