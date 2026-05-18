package com.learn.lc75;


import com.learn.list.ListNode;

import lombok.extern.log4j.Log4j2;


/**
 * LC - 2095. Delete the Middle Node of a Linked List
 *
 * Medium
 *
 * You are given the head of a linked list. Delete the middle node, and return the head of the modified linked list.

The middle node of a linked list of size n is the ⌊n / 2⌋th node from the start using 0-based indexing, where ⌊x⌋ denotes the largest integer less than or equal to x.

    For n = 1, 2, 3, 4, and 5, the middle nodes are 0, 1, 1, 2, and 2, respectively.

Example 1:

Input: head = [1,3,4,7,1,2,6]
Output: [1,3,4,1,2,6]
Explanation:
The above figure represents the given linked list. The indices of the nodes are written below.
Since n = 7, node 3 with value 7 is the middle node, which is marked in red.
We return the new list after removing this node.

Example 2:

Input: head = [1,2,3,4]
Output: [1,2,4]
Explanation:
The above figure represents the given linked list.
For n = 4, node 2 with value 3 is the middle node, which is marked in red.

Example 3:

Input: head = [2,1]
Output: [2]
Explanation:
The above figure represents the given linked list.
For n = 2, node 1 with value 1 is the middle node, which is marked in red.
Node 0 with value 2 is the only node remaining after removing node 1.

Constraints:

    The number of nodes in the list is in the range [1, 10 ^ 5].
    1 <= Node.val <= 10 ^ 5
 */

@Log4j2
public class DeleteTheMiddleNodeOfLinkedList {

    public static void main(String[] args) {

        DeleteTheMiddleNodeOfLinkedList deleteTheMiddleNodeOfLinkedList = new DeleteTheMiddleNodeOfLinkedList();

        int[] head = { 1, 3, 4, 7, 1, 2, 6 };
        //             sF
        //                s  F
        //                   s     F
        //                      s        F
        /**
         * int[] head = { 1, 3, 4, 7, 1, 2, 6 };
         *                s     F
         *                   s        F
         *                      s           F
         */

        // int[] head = { 1 };

        ListNode root = ListNode.toList(head);

        var ret = deleteTheMiddleNodeOfLinkedList.deleteMiddle(root);
        log.debug("Delete the Middle Node of a Linked List: {}", () -> ret);

         int[] head2 = { 1, 3, 4, 7, 1, 2, 6 };
         ListNode root2 = ListNode.toList(head2);

        var retLc = deleteTheMiddleNodeOfLinkedList.deleteMiddleLc(root2);
        log.debug("Delete the Middle Node of a Linked List: {}", () -> retLc);

    }

    /**
     * One Pass fast/slow pointers
     *
     * Time: O(n)
     * Space: O(1)
     *
     * Runtime: 4ms Beats 79.88%
     * Memory: 63.50mb Beats 80.53%
     * @param head
     * @return
     */
    public ListNode deleteMiddle(ListNode head) {

        ListNode slow = head;
        ListNode fast = head;
        ListNode preSlow = null;

        if (fast == null) {
            return null;
        }

        // log.debug("slow: {}, fast: {}", slow.getVal(), fast == null ? "end" : fast.getVal());

        while (fast != null && fast.next != null) {
            fast = fast.next;
            if (fast != null) {
                fast = fast.next;
            }
            preSlow = slow;
            slow = slow.next;

            // log.debug("pre: {}, slow: {}, fast: {}", preSlow.getVal(), slow.getVal(), fast == null ? "end" : fast.getVal());
        }

        if (preSlow == null) {
            return null;
        } else {
            preSlow.next = slow.next;
        }

        return head;
    }

    public ListNode deleteMiddleLc(ListNode head) {
        if(head == null || head.next == null) {
            return null;
        }

        ListNode slow = head;
        ListNode fast = head.next.next;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        slow.next = slow.next.next;

        return head;
    }
}
