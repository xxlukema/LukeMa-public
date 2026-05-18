package com.learn.list;


import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import org.junit.jupiter.api.Assertions;

import lombok.extern.log4j.Log4j2;


/**
 * LC - 143 - Reorder List
 *
 * Medium
 *
 * You are given the head of a singly linked-list. The list can be represented as:
 * L0 → L1 → … → Ln - 1 → Ln
 * Reorder the list to be on the following form:
 * L0 → Ln → L1 → Ln - 1 → L2 → Ln - 2 → …
 * You may not modify the values in the list's nodes. Only nodes themselves may be changed.
 *
 * Example 1:
 * Input: head = [1,2,3,4]
 * Output: [1,4,2,3]
 *
 * Example 2:
 * Input: head = [1,2,3,4,5]
 * Output: [1,5,2,4,3]
 *
 * Constraints:
 *     The number of nodes in the list is in the range [1, 5 * 104].
 *     1 <= Node.val <= 1000
 */
@Log4j2
public class ReorderList {

    public static void main(String[] args) {

        // final int[] nums = { 3, 2, 0, -4 };
        // final int[] nums = { 1, 2, 3, 4, 5 };
        // final int[] nums = { 0, 1, 2, 3 };
        final int[] nums = { 0, 1, 2, 3, 4 };
        ListNode head = ListNode.toList(nums);

        ReorderList reorderList = new ReorderList();

        /**
         * For all solution in this class, Add "@Data" from "ListNode" to print the list. There is no cyclic infinite loop in this question.
         */
        reorderList.reorderListLukeMap(head);
        String reorderListLukeMap = head.toString();

        log.debug("Linked list cycle II: {}", reorderListLukeMap);
        log.debug("Linked list cycle II {} OK", () -> "reorderListLukeMap");

        /**
         * Re-create the hed for new test
         */
        head = ListNode.toList(nums);

        reorderList.reorderListLukeFastSlowPointer(head);
        String reorderListLukeFastSlowPointer = head.toString();
        Assertions.assertEquals(reorderListLukeMap, reorderListLukeFastSlowPointer);
        log.debug("Linked list cycle II {} OK", () -> "reorderListLukeFastSlowPointer");

        /**
         * Re-create the hed for new test
         */
        head = ListNode.toList(nums);

        reorderList.reorderListLcFastSlowPointer(head);
        String reorderListLcFastSlowPointer = head.toString();
        Assertions.assertEquals(reorderListLukeMap, reorderListLcFastSlowPointer);
        log.debug("Linked list cycle II {} OK", () -> "reorderListLcFastSlowPointer");

        /**
         * Re-create the hed for new test
         */
        head = ListNode.toList(nums);

        reorderList.reorderListLcStack(head);
        String reorderListStack = head.toString();
        Assertions.assertEquals(reorderListLukeMap, reorderListStack);
        log.debug("Linked list cycle II {} OK", () -> "reorderListStack");

    }

    /**
     * Luke - Fast Slow Pointers
     *
     * Runtime: 1 ms, faster than 100.00% of Java online submissions for Reorder List.
     * Memory Usage: 45.3 MB, less than 89.99% of Java online submissions for Reorder List.
     *
     * Time: O(N)
     * Space: O(1)
     */
    public void reorderListLukeFastSlowPointer(ListNode head) {
        if (head == null) {
            return;
        }

        /**
         * Find middle.
         */
        ListNode fast = head;
        ListNode slow = head;

        /**
         * With "fast.next.next != null", slow will end on "1" for data [0, 1, 2, 3].
         * Without "fast.next.next != null", slow will end on "2" for data [0, 1, 2, 3].
         */
        while (fast != null && fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        // log.debug("---------- slow: {}", slow);

        /**
         * In case of only one node, nothing to do.
         */
        if (slow == fast) {
            return;
        }

        while (fast != null && fast.next != null) {
            fast = fast.next;
        }

        /**
         * Reverse second half of the list
         */
        ListNode curr = slow.next;
        slow.next = null;

        ListNode tmp = null;
        ListNode pre = null;

        while (curr != null) {
            tmp = curr.next;
            curr.next = pre;
            pre = curr;
            curr = tmp;
        }

        ListNode end = fast;

        /**
         * Merge two lists;
         */
        ListNode left = head;
        ListNode right = end;

        while (left != null && right != null) {
            tmp = left.next;
            left.next = right;
            left = tmp;

            tmp = right.next;
            right.next = left;
            right = tmp;
        }
    }

    public void reorderListLcFastSlowPointer(ListNode head) {
        if (head == null) {
            return;
        }

        // find the middle of linked list [Problem 876]
        // in 1->2->3->4->5->6 find 4
        ListNode slow = head;
        ListNode fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        // log.debug("---------- slow: {}", slow);

        // reverse the second part of the list [Problem 206]
        // convert 1->2->3->4->5->6 into 1->2->3->4 and 6->5->4
        // reverse the second half in-place
        ListNode prev = null;
        ListNode curr = slow;
        ListNode tmp = null;

        while (curr != null) {
            tmp = curr.next;

            curr.next = prev;
            prev = curr;
            curr = tmp;
        }

        // merge two sorted linked lists [Problem 21]
        // merge 1->2->3->4 and 6->5->4 into 1->6->2->5->3->4
        ListNode first = head;
        ListNode right = prev;
        while (right != null && right.next != null) {
            tmp = first.next;
            first.next = right;
            first = tmp;

            tmp = right.next;
            right.next = first;
            right = tmp;
        }
    }

    /**
     * Luke - Two Pointer + Map(idx, node)
     *
     * Runtime: 11 ms, faster than 10.16% of Java online submissions for Reorder List.
     * Memory Usage: 55.8 MB, less than 5.11% of Java online submissions for Reorder List.
     *
     * Time: O(N)
     * Space: O(N)
     */
    public void reorderListLukeMap(ListNode head) {
        Map<Integer, ListNode> map = new HashMap<>();
        ListNode curr = head;
        int idx = 0;
        while (curr != null) {
            map.put(idx++, curr);
            curr = curr.next;
        }
        final int N = map.size();
        int left = 0;
        int right = N - 1;
        ListNode leftNode = null;
        ListNode rightNode = null;
        while (left <= right) {

            // log.debug("left: {}, right: {}", left, right);

            leftNode = map.get(left++);
            if (rightNode != null) {
                rightNode.next = leftNode;
            }
            rightNode = map.get(right--);
            leftNode.next = rightNode;
        }

        if (rightNode != null) {
            rightNode.next = null;
        }
    }

    /**
     * LC - Stack
     *
     *
     * Time: O(N)
     * Space: O(N)
     */
    public void reorderListLcStack(ListNode head) {
        final Stack<ListNode> stack = new Stack<>();
        ListNode node = head;

        while (node != null) {
            stack.add(node);
            node = node.next;
        }

        node = head;

        while (node != null) {
            ListNode next = node.next;
            ListNode endNode = stack.pop();

            node.next = endNode;
            endNode.next = next;
            node = next;
            if (node != null && node.next == endNode) {
                node.next = null;
                break;
            }
        }
    }
}
