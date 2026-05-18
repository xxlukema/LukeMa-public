package com.learn.list;


import lombok.extern.log4j.Log4j2;


/**
 * LC - 203 -Remove LinkedList Elements
 *
 * Easy
 *
 * Given the head of a linked list and an integer val, remove all the nodes of the linked list that has Node.val == val, and return the new head.
 *
 * Example 1:
 * Input: head = [1,2,6,3,4,5,6], val = 6
 * Output: [1,2,3,4,5]
 *
 * Example 2:
 * Input: head = [], val = 1
 * Output: []
 *
 * Example 3:
 * Input: head = [7,7,7,7], val = 7
 * Output: []
 *
 * Constraints:
 *     The number of nodes in the list is in the range [0, 104].
 *     1 <= Node.val <= 50
 *     0 <= val <= 50
 */
@Log4j2
public class RemoveLinkedListElements {

    public static void main(String[] args) {

        // final int[] nums = { 1, 2, 6, 3, 4, 5, 6 };
        // final int val = 6;

        final int[] nums = { 7, 7, 7, 7 };
        final int val = 7;

        ListNode head = ListNode.toList(nums);

        RemoveLinkedListElements removeLinkedListElements = new RemoveLinkedListElements();

        var ret = removeLinkedListElements.removeElements(head, val);
        log.debug("Remove LinkedList Elements: {}", () -> ret);
        log.debug("Remove LinkedList Elements {} OK", () -> "ret");
    }

    /**
     * Luke - Iterative - Use preHead. return preHead.next;
     *
     * Runtime: 2 ms, faster than 20.94% of Java online submissions for Remove Linked List Elements.
     * Memory Usage: 48.9 MB, less than 67.72% of Java online submissions for Remove Linked List Elements.
     *
     * Time: O(N)
     * Space: O(1)
     */
    public ListNode removeElements(final ListNode head, final int val) {

        ListNode preHead = new ListNode(val + 1);
        preHead.next = head;

        ListNode pre = preHead;
        ListNode curr = preHead;

        while (curr != null) {
            if (curr.val == val) {
                pre.next = curr.next;
            } else {
                pre = curr;
            }

            curr = curr.next;
        }

        return preHead.next;
    }
}
