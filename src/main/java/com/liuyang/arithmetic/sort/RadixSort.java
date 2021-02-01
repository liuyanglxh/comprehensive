package com.liuyang.arithmetic.sort;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 基数排序，前提：字符串的所有字符都是ascii码并且长度相等
 */
public class RadixSort {

    public void sortInt(int[] arr) {
        int bucketsNum = 10;
        int[][] buckets = new int[bucketsNum][];
        //每个桶的长度
        int[] bucketLength = new int[bucketsNum];
        for (int i = 0; i < bucketsNum; i++) {
            buckets[i] = new int[arr.length];
        }

        int mode = 1;
        while (true) {
            boolean more = false;
            for (int i : arr) {
                int a = (i / mode) % 10;
                if (a != 0) {
                    if (mode >= 1000000000) {
                        System.err.println(String.format("i=%d,a=%d",i,a));
                    }
                    more = true;
                }

                int pos = -1;
                try {
                    pos = bucketLength[a]++;
                    buckets[a][pos] = i;
                } catch (Exception e) {
                    System.out.println(String.format("a=%d,pos=%d,i=%d,mode=%d", a, pos, i, mode));
                    System.exit(0);
                }
            }

            int index = 0;
            for (int i = 0; i < buckets.length; i++) {
                int len = bucketLength[i];
                int[] bucket = buckets[i];
                for (int j = 0; j < len; j++) {
                    arr[index++] = bucket[j];
                }
                //长度设置为0，清理
                bucketLength[i] = 0;
            }

            if (!more) break;
            mode *= 10;
        }

    }

    public void sort(String[] arr, int length) {
        int bucketsNum = 256;

        List<List<String>> buckets = new ArrayList<>(bucketsNum);
        for (int i = 0; i < bucketsNum; i++) {
            buckets.add(new ArrayList<>(arr.length));
        }

        for (int pos = length - 1; pos >= 0; pos--) {
            //根据字符串在pos位置上的值，把字符串放到对应的桶中
            for (String s : arr) {
                char c = s.charAt(pos);
                int p = c;
                buckets.get(p).add(s);
            }

            //遍历所有桶，依次把字符串重新放回数组中
            int index = 0;
            for (List<String> bucket : buckets) {
                for (String s : bucket) {
                    arr[index++] = s;
                }
                bucket.clear();
            }
        }

    }

    boolean isSorted(String[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i].compareTo(arr[i + 1]) > 0) {
                return false;
            }
        }
        return true;
    }

    boolean isSorted(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i] > arr[i + 1]) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        int[] arr = new int[10000000];
        Random random = new Random();
        for (int i = 0; i < 10000000; i++) {
            int n = random.nextInt(Integer.MAX_VALUE);
            if (n < 0) n = -n;
            arr[i] = n;
        }
        RadixSort r = new RadixSort();
        long start = System.currentTimeMillis();
        r.sortInt(arr);
        System.out.println(r.isSorted(arr));
        System.out.println(System.currentTimeMillis() - start);
    }

    @Test
    public void test1() {
        /**
         * a=-1,pos=-1,i=700000011,mode=1000000000
         */
        int b = 1000000000*10;
        System.out.println(b);
    }
}
