package com.liuyang.common.util;

public class TimeUtil {

    public static class BaseTime {
        private long time;

        private BaseTime(long time) {
            this.time = time;
        }

        public void sleep(int a) {
            try {
                Thread.sleep(this.time * a);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static final BaseTime SECOND = new BaseTime(1000L);
    public static final BaseTime MILLI_SECOND = new BaseTime(1L);
}
