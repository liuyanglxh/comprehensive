package com.liuyang.arithmetic;

import java.util.ArrayList;
import java.util.List;

/**
 * 最低票价
 * https://leetcode-cn.com/problems/minimum-cost-for-tickets/
 */
public class MincostTicket {

    public int mincostTickets(int[] days, int[] costs) {
        int total = 0;

        int[] nearDays = this.rmIsolateDays(days);

        total += (days.length - nearDays.length) * costs[2];



        return 0;
    }

    private int[] rmIsolateDays(int[] days) {
        List<Integer> indice = new ArrayList<>();
        if (days[1] - days[0] > 30) {
            indice.add(1);
        }
        for (int i = 1; i < days.length - 1; i++) {
            if (days[i] - days[i - 1] > 30 && days[i + 1] - days[i] > 30) {
                indice.add(i);
            }
        }
        if (days[days.length - 1] - days[days.length - 2] > 30) {
            indice.add(days.length - 1);
        }

        days = new int[indice.size()];
        for (int i = 0; i < indice.size(); i++) {
            days[i] = indice.get(i);
        }

        return days;
    }


}
