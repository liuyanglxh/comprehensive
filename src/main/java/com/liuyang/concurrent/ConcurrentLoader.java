package com.liuyang.concurrent;

import java.util.List;
import java.util.function.Supplier;

public class ConcurrentLoader<T, K> {

    private T data;
    private List<ConcurrentLoader<?, K>> waitList;

    public <V> T lockAndGet(K k, Supplier<V> loader) {
        return null;
    }

}
