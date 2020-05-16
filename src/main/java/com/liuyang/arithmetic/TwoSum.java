package com.liuyang.arithmetic;

import java.util.HashMap;
import java.util.Map;

public class TwoSum {


    public int[] twoSum(int[] nums, int target) {

        int[] result = null;
        /**
         * key是值，value是脚标
         */
        Map<Integer, Integer> complementMap = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int curr = nums[i];
            int currComplement = target - curr;
            Integer index = complementMap.get(currComplement);
            if (index != null) {
                result = new int[2];
                result[0] = i;
                result[1] = index;
                break;
            } else {
                complementMap.put(curr, i);
            }
        }

        return result;
    }
}
