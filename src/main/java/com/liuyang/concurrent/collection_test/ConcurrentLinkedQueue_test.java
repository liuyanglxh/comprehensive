package com.liuyang.concurrent.collection_test;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

public class ConcurrentLinkedQueue_test {

    private LoadingCache<String, String> cache = CacheBuilder.newBuilder()
            .maximumSize(1000)
            .concurrencyLevel(5)
            .expireAfterWrite(2000, TimeUnit.MICROSECONDS)
            .build(new CacheLoader<String, String>() {
                @Override
                public String load(String key) throws Exception {
                    return "";
                }
            });

    @Test
    public void test() {
    }
}
