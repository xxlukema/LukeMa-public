package com.learn.list;


import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Assertions;

import lombok.extern.log4j.Log4j2;


/**
 * LC - 142 - Linked List Cycle II
 *
 * Medium
 *
 * Given the head of a linked list, return the node where the cycle begins. If there is no cycle, return null.
 * There is a cycle in a linked list if there is some node in the list that can be reached again by continuously following the next pointer.
 * Internally, pos is used to denote the index of the node that tail's next pointer is connected to (0-indexed). It is -1 if there is no cycle.
 * Note that pos is not passed as a parameter.
 *
 * Do not modify the linked list.
 *
 * Example 1:
 * Input: head = [3,2,0,-4], pos = 1
 * Output: tail connects to node index 1
 * Explanation: There is a cycle in the linked list, where tail connects to the second node.
 *
 * Example 2:
 * Input: head = [1,2], pos = 0
 * Output: tail connects to node index 0
 * Explanation: There is a cycle in the linked list, where tail connects to the first node.
 *
 * Example 3:
 * Input: head = [1], pos = -1
 * Output: no cycle
 * Explanation: There is no cycle in the linked list.
 *
 * Constraints:
 *     The number of the nodes in the list is in the range [0, 104].
 *     -105 <= Node.val <= 105
 *     pos is -1 or a valid index in the linked-list.
 */
@Log4j2
public class LinkedListCycleII {

    public static void main(String[] args) {

        final int[] nums = { 3, 2, 0, -4 };
        final int pos = 1;
        final ListNode head = ListNode.toList(nums, pos);

        LinkedListCycleII linkedListCycleII = new LinkedListCycleII();

        /**
         * For all solution in this class, comment out "@Data" from "ListNode" to prevent cyclic infinite loop.
         */
        ListNode detectCycleLukeSet = linkedListCycleII.detectCycleLukeSet(head);
        log.debug("Linked list cycle II: {}", () -> detectCycleLukeSet.val);
        log.debug("Linked list cycle II {} OK", () -> "detectCycleLukeSet");

        /**
         * For all solution in this class, comment out "@Data" from "ListNode" to prevent cyclic infinite loop.
         */
        ListNode detectCycleLcFloyd = linkedListCycleII.detectCycleLcFloyd(head);
        Assertions.assertEquals(detectCycleLukeSet, detectCycleLcFloyd);
        log.debug("Linked list cycle II {} OK", () -> "detectCycleLcFloyd");

    }

    /**
     * LC - Floyd
     *
     *
     * Time: O(N)
     * Space: O(1)
     */
    private ListNode getIntersect(ListNode head) {
        ListNode tortoise = head;
        ListNode hare = head;

        // A fast pointer will either loop around a cycle and meet the slow
        // pointer or reach the `null` at the end of a non-cyclic list.
        while (hare != null && hare.next != null) {
            tortoise = tortoise.next;
            hare = hare.next.next;
            if (tortoise == hare) {
                return tortoise;
            }
        }

        return null;
    }

    public ListNode detectCycleLcFloyd(ListNode head) {
        if (head == null) {
            return null;
        }

        // If there is a cycle, the fast/slow pointers will intersect at some
        // node. Otherwise, there is no cycle, so we cannot find an entrance to
        // a cycle.
        ListNode intersect = getIntersect(head);
        if (intersect == null) {
            return null;
        }

        // To find the entrance to the cycle, we have two pointers traverse at
        // the same speed -- one from the front of the list, and the other from
        // the point of intersection.
        ListNode ptr1 = head;
        ListNode ptr2 = intersect;
        while (ptr1 != ptr2) {
            ptr1 = ptr1.next;
            ptr2 = ptr2.next;
        }

        return ptr1;
    }

    /**
     * Luke - Set
     *
     * Runtime: 6 ms, faster than 19.35% of Java online submissions for Linked List Cycle II.
     * Memory Usage: 46.1 MB, less than 17.31% of Java online submissions for Linked List Cycle II.
     *
     * Time: O(N)
     * Space: O(N)
     */
    public ListNode detectCycleLukeSet(ListNode head) {
        if (head == null) {
            return null;
        }

        Set<ListNode> set = new HashSet<>();
        ListNode curr = head;
        set.add(curr);
        while (curr != null) {
            curr = curr.next;
            if (curr == null) {
                return null;
            }
            if (set.contains(curr)) {
                return curr;
            } else {
                set.add(curr);
            }
        }

        return null;
    }
}
