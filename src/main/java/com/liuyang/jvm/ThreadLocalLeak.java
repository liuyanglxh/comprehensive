package com.liuyang.jvm;

/**
 * 测试threadlocal引起内存泄露
 */
public class ThreadLocalLeak {
    public static void main(String[] args) {
        while (true) {
            new Thread(() -> {
                ThreadLocal<User> tl = new ThreadLocal<>();
                tl.set(new User());
            }).start();
        }
    }

    static class User{

        @Override
        protected void finalize() throws Throwable {
            super.finalize();
            System.out.println("清理了");
        }
    }
}
