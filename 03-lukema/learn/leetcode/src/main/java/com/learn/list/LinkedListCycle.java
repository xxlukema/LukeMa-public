package com.learn.list;


import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Assertions;

import lombok.extern.log4j.Log4j2;


/**
 * LC - 141 - Linked List Cycle
 *
 * Easy
 *
 * Given head, the head of a linked list, determine if the linked list has a cycle in it.
 * There is a cycle in a linked list if there is some node in the list that can be reached again by continuously following the next pointer.
 * Internally, pos is used to denote the index of the node that tail's next pointer is connected to. Note that pos is not passed as a parameter.
 * Return true if there is a cycle in the linked list. Otherwise, return false.
 * (Luke Note: Ignore the "pos". Just detect if the list is cyclic.)
 *
 * Example 1:
 * Input: head = [3,2,0,-4], pos = 1
 * Output: true
 * Explanation: There is a cycle in the linked list, where the tail connects to the 1st node (0-indexed).
 *
 * Example 2:
 * Input: head = [1,2], pos = 0
 * Output: true
 * Explanation: There is a cycle in the linked list, where the tail connects to the 0th node.
 *
 * Example 3:
 * Input: head = [1], pos = -1
 * Output: false
 * Explanation: There is no cycle in the linked list.
 *
 * Constraints:
 *     The number of the nodes in the list is in the range [0, 104].
 *     -105 <= Node.val <= 105
 *     pos is -1 or a valid index in the linked-list.
 */
@Log4j2
public class LinkedListCycle {

    public static void main(String[] args) {

        final int[] nums = { 3, 2, 0, -4 };
        final int pos = 1;
        final ListNode head = ListNode.toList(nums, pos);

        LinkedListCycle linkedListCycle = new LinkedListCycle();

        /**
         * For all questions in this class, comment out "@Data" from "ListNode" to prevent cyclic infinite loop.
         */
        var hasCycleLukeVisitedSet = linkedListCycle.hasCycleLukeVisitedSet(head);
        log.debug("Linked list cycle: {}", () -> hasCycleLukeVisitedSet);
        log.debug("Linked list cycle {} OK", () -> "hasCycleLukeVisitedSet");

        /**
         * For all questions in this class, comment out "@Data" from "ListNode" to prevent cyclic infinite loop.
         */
        var hasCycleLcFastSlowPointers = linkedListCycle.hasCycleLcFastSlowPointers(head);
        Assertions.assertEquals(hasCycleLukeVisitedSet, hasCycleLcFastSlowPointers);
        log.debug("Linked list cycle {} OK", () -> "hasCycleLcFastSlowPointers");
    }

    /**
     * Luke - Use "visited" Set
     *
     * Runtime: 7 ms, faster than 14.05% of Java online submissions for Linked List Cycle.
     * Memory Usage: 48.2 MB, less than 5.21% of Java online submissions for Linked List Cycle.
     *
     * Time: O(N) - HashSet.contains() time complexity is O(1)
     * Space: O(N)
     */
    public boolean hasCycleLukeVisitedSet(ListNode head) {
        Set<ListNode> visited = new HashSet<>();

        ListNode curr = head;

        while (!(curr == null || visited.contains(curr))) {
            visited.add(curr);
            curr = curr.next;
        }

        return curr != null;
    }

    /**
     * LC - Fast/Slow Pointers
     *
     * Runtime: 1 ms, faster than 46.08% of Java online submissions for Linked List Cycle.
     * Memory Usage: 45.8 MB, less than 60.51% of Java online submissions for Linked List Cycle.
     *
     * Time: O(N)
     * Space: O(1)
     */
    public boolean hasCycleLcFastSlowPointers(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;

        while (slow != null && fast != null) {
            slow = slow.next;
            fast = fast.next;
            if (fast != null) {
                fast = fast.next;
                if (fast != null && slow != null && fast == slow) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean hasCycleLc(ListNode head) {
        if (head == null) {
            return false;
        }

        ListNode slow = head;
        ListNode fast = head.next;
        while (slow != fast) {
            if (fast == null || fast.next == null) {
                return false;
            }
            slow = slow.next;
            fast = fast.next.next;
        }
        return true;
    }
}
