package com.liuyang.arithmetic.mergeKLists;

import com.liuyang.arithmetic.ListNode;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class Solution2 {
    /**
     * 两两分组并采用多线程
     *
     * @param lists
     * @return
     */
    public ListNode mergeKLists(ListNode[] lists) {
        ExecutorService pool = Executors.newFixedThreadPool(8);
        BlockingQueue<ListNode> queue = new ArrayBlockingQueue<>(lists.length);
        AtomicInteger count = new AtomicInteger(lists.length);

        queue.addAll(Arrays.asList(lists));

        Set<ListNode> a = new HashSet<>();
        Set<ListNode> b = new HashSet<>();
        while (count.get() > 1) {
            ListNode poll = queue.poll();
            if (a.isEmpty()) {
                a.add(poll);
            } else if (b.isEmpty()) {
                b.add(poll);
                pool.execute(() -> {
                    ListNode merge = merge(a.iterator().next(), b.iterator().next());
                    count.addAndGet(-1);
                    queue.offer(merge);
                });
            }
        }

        return queue.poll();
    }

    private ListNode merge(ListNode a, ListNode b) {
        ListNode head = null;
        ListNode tail = null;
        while (a != null || b != null) {
            ListNode out;
            if (a == null) {
                out = b;
                b = b.next;
            } else if (b == null) {
                out = a;
                a = a.next;
            } else if (a.val > b.val) {
                out = b;
                b = b.next;
            } else {
                out = a;
                a = a.next;
            }
            if (head == null) {
                head = out;
                tail = out;
            } else {
                tail.next = out;
                tail = out;
            }

        }
        return head;
    }

}