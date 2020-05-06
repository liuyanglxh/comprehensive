package com.liuyang.arithmetic;

/**
 * 链表翻转
 */
public class LinkReverse {

    public static class LinkNode<T> {
        T data;
        LinkNode<T> next;

        LinkNode(T data, LinkNode<T> next) {
            this.data = data;
            this.next = next;
        }
    }

    public static void main(String[] args) {

        for (int i = 0; i < 10; i++) {
            LinkNode<Integer> head = create(i);
            print(head);
            new LinkReverse();
            LinkNode<Integer> reverse = reverse(head);
            print(reverse);
        }

//        LinkNode<Integer> reverse = reverse(create(10));

    }

    static void print(LinkNode<Integer> node) {
        while (node != null) {
            System.out.print(node.data + " ");
            node = node.next;
        }
        System.out.println();
    }

    static LinkNode<Integer> create(int num) {
        if (num == 0) {
            return null;
        }
        LinkNode<Integer> head = new LinkNode<>(0, null);
        LinkNode<Integer> current = head;
        for (int i = 1; i < num; i++) {
            current.next = new LinkNode<>(i, null);
            current = current.next;
        }

        return head;
    }

    public static <T> LinkNode<T> reverse(LinkNode<T> head) {
        //长度为0、1
        if (head == null || head.next == null) {
            return head;
        }

        LinkNode<T> pre = null;//前1个
        LinkNode<T> target = head;//正在处理的
        LinkNode<T> next = target.next;//后1个

        while (next != null) {

            target.next = pre;//修改当前节点指针
            pre = target;//前一个节点往后移动
            target = next;//目标往后移动
            next = target.next;//后1个节点往后移动

        }
        target.next = pre;

        return target;
    }

}
