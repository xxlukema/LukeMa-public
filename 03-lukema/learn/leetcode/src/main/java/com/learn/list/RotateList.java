package com.learn.list;


import lombok.extern.log4j.Log4j2;


@Log4j2
public class RotateList {

    public static void main(String[] args) {

        int[] nums = { 1, 2, 3, 4, 5 };
        // int k = 2;
        int k = 0;

        ListNode head = null;
        for (int n : nums) {
            if (head == null) {
                head = new ListNode(n);
            } else {
                ListNode node = head;
                while (node.next != null) {
                    node = node.next;
                }
                node.next = new ListNode(n);
            }
        }

        RotateList rotateList = new RotateList();
        var ret = rotateList.rotateRightImproved(head, k);

        ListNode node = ret;

        while (node != null) {
            log.debug("rotate list luke: {}", node.val);
            node = node.next;
        }

    }

    /**
     * Luke Improved: Rotating only once.
     *
     * Runtime: 1 ms, faster than 79.18% of Java online submissions for Rotate List.
     * Memory Usage: 43 MB, less than 50.49% of Java online submissions for Rotate List.
     *
     * Time: O(n)
     * Space: O(n)
     */
    public ListNode rotateRightImproved(ListNode head, int k) {

        if (head == null || head.next == null) {
            return head;
        }

        int len = 0;
        ListNode node = head;
        while (node != null) {
            len++;
            node = node.next;
        }

        k = k % len;

        if (k == 0) {
            return head;
        }

        int pos = 0;
        ListNode newLastode = head;
        while (pos < len - 1 - k) {
            newLastode = newLastode.next;
            pos++;
        }

        ListNode newHead = newLastode.next;
        ListNode itNode = newHead;
        while (itNode.next != null) {
            itNode = itNode.next;
        }

        itNode.next = head;
        head = newHead;
        newLastode.next = null;

        return head;
    }

    /**
     * Luke brute: Move last node to head each time.
     *
     * Runtime: 1 ms, faster than 79.18% of Java online submissions for Rotate List.
     * Memory Usage: 43.1 MB, less than 43.01% of Java online submissions for Rotate List.
     *
     * Time: O(n * k)
     * Space: O(n)
     */
    public ListNode rotateRight(ListNode head, int k) {

        if (head == null || head.next == null) {
            return head;
        }

        int len = 0;
        ListNode node = head;
        while (node != null) {
            len++;
            node = node.next;
        }

        k = k % len;

        if (k == 0) {
            return head;
        }

        for (int i = 0; i < k; i++) {
            head = rotate(head);
        }

        return head;
    }

    public ListNode rotate(ListNode head) {

        ListNode node = head;

        // Mode to the node before lastNode:
        while (node.next.next != null) {
            node = node.next;
        }

        ListNode lastNode = node.next;
        node.next = null;
        lastNode.next = head;
        head = lastNode;

        return head;
    }
}
