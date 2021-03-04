package com.liuyang.common.util;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 测试专用，用于统计代码耗时
 * 有bug，但是测试环境不影响
 */
public class TimeMarker {

    private static final Logger logger = LoggerFactory.getLogger(TimeMarker.class);

    private static final ThreadLocal<Stack> HOLDERS = new ThreadLocal<>();

    public static void clear() {
        HOLDERS.remove();
    }

    public static void mark(String info) {
        Node node = new Node(info);
        Stack holder = HOLDERS.get();
        if (holder == null) {
            holder = new Stack();
            HOLDERS.set(holder);
        }

        Node prev = holder.peek();
        if (prev != null) {
            prev.addSub(node);
        }

        holder.push(node);
    }

    public static void tryInfo() {
        Stack holder = HOLDERS.get();
        Node node = holder.pop();
        if (node == null) return;
        node.stop();
        Node peek = holder.peek();
        //说明是最后一个节点了，则输出信息
        if (peek == null) {
            String info = JSONObject.toJSONString(node.getCostInfo());
            logger.info(info);
        }
    }

    private static class Node {
        Node prev;
        Node next;
        String info;
        long startTime = System.currentTimeMillis();
        long endTime;
        List<Node> subNodes;

        Node(String info) {
            this.info = info;
        }

        void stop() {
            endTime = System.currentTimeMillis();
        }

        void addSub(Node node) {
            if (subNodes == null) subNodes = new ArrayList<>();
            subNodes.add(node);
        }

        CostInfo getCostInfo() {
            CostInfo map = new CostInfo();
            map.name = info;
            map.cost = (endTime - startTime);
            if (subNodes != null) {
                map.sub = subNodes.stream().map(Node::getCostInfo)
                        .sorted(Comparator.comparing(CostInfo::getCost).reversed())
                        .collect(Collectors.toList());
            }
            return map;
        }
    }

    private static class CostInfo {
        public String name;
        public Long cost;
        public List<CostInfo> sub;

        public long getCost() {
            return cost;
        }
    }

    private static class Stack {
        Node head;
        Node tail;

        void push(Node node) {
            if (head == null) {
                head = node;
            } else {
                tail.next = node;
                node.prev = tail;
            }
            tail = node;
        }

        Node pop() {
            if (tail == null) return null;
            Node node;
            if (head == tail) {
                node = head;
                head = null;
                tail = null;
            } else {
                node = tail;
                tail = tail.prev;
                tail.next = null;
            }

            if (shouldRemove()) HOLDERS.remove();

            return node;
        }

        Node peek() {
            return tail;
        }

        boolean shouldRemove() {
            return head == null;
        }
    }


    public static void main(String[] args) throws InterruptedException {
        TimeMarker.mark("a in main");
        a();
        TimeMarker.tryInfo();
    }

    static void a() throws InterruptedException {
        Thread.sleep(30);
        TimeMarker.mark("ai in a");
        a(0);
        TimeMarker.tryInfo();
    }

    static void a(int i) throws InterruptedException {
        TimeMarker.mark("ai sleep");
        Thread.sleep(20);
        TimeMarker.tryInfo();
    }

    static void b() throws InterruptedException {
        TimeMarker.mark("a in b");
        a();
        TimeMarker.tryInfo();

    }

    static void c() throws InterruptedException {
        TimeMarker.mark("c sleep");
        Thread.sleep(10);
        TimeMarker.tryInfo();
        TimeMarker.mark("a in c");
        a();
        TimeMarker.tryInfo();
        Thread.sleep(20);
        TimeMarker.mark("b in c");
        b();
        TimeMarker.tryInfo();
    }

    static void d() throws InterruptedException {
        Thread.sleep(10);
    }
}
