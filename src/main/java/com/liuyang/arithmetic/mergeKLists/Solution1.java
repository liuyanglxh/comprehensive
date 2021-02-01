package com.liuyang.arithmetic.mergeKLists;

import com.liuyang.arithmetic.ListNode;

import java.util.Comparator;
import java.util.PriorityQueue;

class Solution1 {
    public ListNode mergeKLists(ListNode[] lists) {
        ListNode head = null;
        ListNode tail = null;
        PriorityQueue<ListNode> queue = new PriorityQueue<>(Comparator.comparingInt(a -> a.val));
        for (ListNode node : lists) {
            while (node != null) {
                queue.add(node);
                node = node.next;
            }
        }

        while (queue.size() > 0) {
            ListNode poll = queue.poll();
            if (head == null) {
                head = poll;
                tail = poll;
            } else {
                tail.next = poll;
                tail = poll;
                tail.next = null;
            }
        }
        if (tail != null) tail.next = null;


        return head;
    }
}