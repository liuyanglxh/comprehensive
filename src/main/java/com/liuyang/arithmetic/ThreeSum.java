package com.liuyang.arithmetic;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 三数之和为0
 * https://leetcode-cn.com/problems/3sum/
 */
public class ThreeSum {

    public static void main(String[] args) {
        int[] nums = {-4, -2, -2, -2, 0, 1, 2, 2, 2, 3, 3, 4, 4, 6, 6};
//        int[] nums = {-4, -1, -1, 0, 1, 2};
        List<List<Integer>> lists = new ThreeSum().threeSum(nums);
        System.out.println(lists);
    }

    class Item {
        int value;
        int count;

        Item(int value, int count) {
            this.value = value;
            this.count = count;

        }
    }

    public List<List<Integer>> threeSum(int[] nums) {
        Map<String, List<Integer>> result = new HashMap<>();

        //元素不足3个
        if (nums.length < 3) {
            return Collections.emptyList();
        }

        Map<Integer, Item> map = new HashMap<>();

        int zeroNum = 0;
        for (int num : nums) {
            if (num == 0) {
                zeroNum++;
            } else {
                map.putIfAbsent(num, new Item(num, 0));
                map.get(num).count++;
            }
        }


        //只有1个数的
        List<Item> list = map.values().stream().sorted(Comparator.comparingInt(i -> i.value)).collect(Collectors.toList());

        //有至少3个0
        if (zeroNum >= 3) {
            result.put(buildStr(0, 0, 0), Arrays.asList(0, 0, 0));
        }

        //缺少正数或负数
        if (list.isEmpty() || list.get(0).value >= 0 || list.get(list.size() - 1).value <= 0) {
            return new ArrayList<>(result.values());
        }

        int index = 0;
        int move;

        while (list.get(index).value < 0) {
            move = index;

            Item aItem = list.get(index);
            int a = aItem.value;

            //包含0的组合
            if (zeroNum > 0 && map.containsKey(-a)) {
                result.put(buildStr(a, 0, -a), Arrays.asList(a, 0, -a));
            }

            //不包含0
            while (move < list.size()) {
                Item bItem = list.get(move);
                int b = bItem.value;
                //以当前index位置的数a为基础，根据move位置的值b，在集合中，找到一个c，令a+b+c=0
                int c = -bItem.value - aItem.value;
                boolean cItem = map.containsKey(c);
                //集合中包含c
                if (cItem && a != c) {
                    // a == b 或者 b == c  那么需要验证是否够2个
                    if ((a == b && aItem.count > 1) || (b == c && bItem.count > 1)) {
                        result.put(buildStr(a, b, c), Arrays.asList(a, b, c));
                    }
                    //第三种情况 b != c
                    else if (a != b && b != c) {
                        result.put(buildStr(a, b, c), Arrays.asList(a, b, c));
                    }
                }

                move++;
            }

            index++;
        }

        return new ArrayList<>(result.values());
    }

    private String buildStr(int... nums) {
        Arrays.sort(nums);
        return nums[0] + "," + nums[1] + "," + nums[2];
    }

}
