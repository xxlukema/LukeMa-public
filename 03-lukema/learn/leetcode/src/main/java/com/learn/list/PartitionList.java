package com.learn.list;


import lombok.extern.log4j.Log4j2;


/**
 * LC 86
 */
@Log4j2
public class PartitionList {

    public static void main(String[] args) {

        // final int[] nums = { 1, 4, 3, 2, 5, 2 };
        final int[] nums = {};
        final int x = 3;

        ListNode head = ListNode.toList(nums);

        PartitionList partitionList = new PartitionList();

        ListNode ret = partitionList.partition(head, x);
        log.debug("PartitionList: {}", () -> ListNode.toList(ret));
    }

    /**
     * Luke: Brute
     * 
     * Runtime: 1 ms, faster than 69.08% of Java online submissions for Partition List.
     * Memory Usage: 43.5 MB, less than 6.26% of Java online submissions for Partition List.
     * 
     * Time: O(N)
     * Space: O(1)
     */
    public ListNode partition(ListNode head, int x) {

        final ListNode preHead = new ListNode(0, head);

        ListNode curr = head;
        ListNode last = preHead;

        /**
         * Move curr to skip the leading smaller nodes:
         */
        while (curr != null && curr.val < x) {
            last = curr;
            curr = curr.next;
        }

        if (curr == null) {
            return preHead.next;
        }

        /**
         * Do the thing
         */
        while (true) {
            /**
             * Move curr to one node before small node:
             */
            while (curr.next != null && curr.next.val >= x) {
                curr = curr.next;
            }

            if (curr.next == null) {
                break;
            }

            /**
             * Mode small node to last.next
             */
            ListNode tmpLeft = last.next;
            ListNode tmpRight = curr.next.next;

            // Update last
            last.next = curr.next;
            last.next.next = tmpLeft;
            last = last.next;

            // Update curr
            curr.next = tmpRight;
        }

        return preHead.next;
    }
}
