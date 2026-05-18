package com.learn.list;


import lombok.extern.log4j.Log4j2;


/**
 * LC 92
 */
@Log4j2
public class ReverseLinkedList2 {

    public static void main(String[] args) {

        ReverseLinkedList2 reverseLinkedList2 = new ReverseLinkedList2();

        int[] nums = { 1, 2, 3, 4, 5 };
        int left = 2;
        int right = 4;

        ListNode head = ListNode.toList(nums);

        var ret = reverseLinkedList2.reverseBetween(head, left, right);
        log.debug("Reversed list: {}", () -> ListNode.toList(ret));
    }

    /**
     * Luke: Iterative
     * 
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Reverse Linked List II.
     * Memory Usage: 42.5 MB, less than 5.77% of Java online submissions for Reverse Linked List II.
     * 
     * Time: O(N)
     * Space: O(right)
     */
    public ListNode reverseBetween(ListNode head, int left, int right) {
        if (left == right) {
            return head;
        }

        final ListNode[] nodes = new ListNode[right + 1];
        ListNode curr = head;

        int ctr = 1;
        while (ctr <= right) {
            if (ctr >= left) {
                nodes[ctr] = curr;
            }

            ctr++;
            curr = curr.next;
        }

        while (left < right) {
            int tmp = nodes[left].val;
            nodes[left].val = nodes[right].val;
            nodes[right].val = tmp;
            left++;
            right--;
        }

        return head;
    }
}
