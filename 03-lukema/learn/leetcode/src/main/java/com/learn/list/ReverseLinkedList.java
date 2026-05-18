package com.learn.list;


import org.junit.jupiter.api.Assertions;

import lombok.extern.log4j.Log4j2;


/**
 * LC - 206 - Reverse LinkedList
 *
 * Easy
 *
 * Given the head of a singly linked list, reverse the list, and return the reversed list.
 *
 * Example 1:
 * Input: head = [1,2,3,4,5]
 * Output: [5,4,3,2,1]
 *
 * Example 2:
 * Input: head = [1,2]
 * Output: [2,1]
 */
@Log4j2
public class ReverseLinkedList {

    public static void main(String[] args) {

        final int[] nums = { 1, 2, 3, 4, 5 };

        ListNode head = ListNode.toList(nums);

        ReverseLinkedList reverseLinkedList = new ReverseLinkedList();

        var reverseListIterative = reverseLinkedList.reverseListIterative(head);
        log.debug("Reverse LinkedList: {}", () -> reverseListIterative);
        log.debug("Reverse LinkedList {} OK", () -> "reverseListIterative");

        /**
         * Reset head
         */
        head = ListNode.toList(nums);

        var reverseListRecursive = reverseLinkedList.reverseListRecursive(head);
        Assertions.assertEquals(reverseListIterative.toString(), reverseListRecursive.toString());
        log.debug("Reverse LinkedList {} OK", () -> "reverseListRecursive");
    }

    /**
     * Luke - Iterative
     *
     * Runtime: 1 ms, faster than 13.01% of Java online submissions for Reverse Linked List.
     * Memory Usage: 42.8 MB, less than 67.40% of Java online submissions for Reverse Linked List.
     *
     * Time: O(N)
     * Space: O(1)
     */
    public ListNode reverseListIterative(ListNode head) {

        ListNode prev = null;
        ListNode curr = head;

        while (curr != null) {
            ListNode next = curr.next;
            curr.next = prev;

            prev = curr;
            curr = next;
        }

        return prev;
    }

    /**
     * Luke - Recursive
     *
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Reverse Linked List.
     * Memory Usage: 42.1 MB, less than 88.60% of Java online submissions for Reverse Linked List.
     *
     * Time: O(N)
     * Space: O(N)
     */
    public ListNode reverseListRecursive(ListNode head) {

        if (head == null) {
            return null;
        }

        return reverseListRecursive(head, null);
    }

    private ListNode reverseListRecursive(ListNode curr, ListNode prev) {
        if (curr == null) {
            return prev;
        } else {
            ListNode next = curr.next;
            curr.next = prev;

            return reverseListRecursive(next, curr);
        }
    }
}
