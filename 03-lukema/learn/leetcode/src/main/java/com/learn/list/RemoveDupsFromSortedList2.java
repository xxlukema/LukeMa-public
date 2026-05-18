package com.learn.list;


import lombok.extern.log4j.Log4j2;


/**
 * LC 82
 *
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
@Log4j2
public class RemoveDupsFromSortedList2 {

    public static void main(String[] args) {
        // int[] nums = { 0, 0, 1, 2, 3, 3, 4, 4, 5 };
        // int[] nums = { 1, 1, 2, 2, 3, 3 };
        // int[] nums = { 1, 2, 2 };
        int[] nums = { 0, 0, 1, 1, 2, 2, 2 };

        // log.debug("list: {}", Arrays.stream(nums).boxed().toList());
        ListNode head = new ListNode(nums[0]);
        ListNode node = head;

        for (int i = 1; i < nums.length; i++) {
            node.next = new ListNode(nums[i]);
            node = node.next;
        }

        log.debug("list: {}", ListNode.toList(head));

        RemoveDupsFromSortedList2 removeDupsFromSortedList2 = new RemoveDupsFromSortedList2();

        var ret = removeDupsFromSortedList2.deleteDuplicatesLuke(head);
        log.debug("Removed dups Luke: {}", () -> ListNode.toList(ret));

        head = new ListNode(nums[0]);
        node = head;

        for (int i = 1; i < nums.length; i++) {
            node.next = new ListNode(nums[i]);
            node = node.next;
        }

        log.debug("list: {}", ListNode.toList(head));

        var retLc = removeDupsFromSortedList2.deleteDuplicatesLc(head);
        log.debug("Removed dups LC: {}", () -> ListNode.toList(retLc));

    }

    /**
     * LC
     * 
     * Runtime: 1 ms, faster than 81.99% of Java online submissions for Remove Duplicates from Sorted List II.
     * Memory Usage: 43.9 MB, less than 38.26% of Java online submissions for Remove Duplicates from Sorted List II.
     * 
     * Time: O(n)
     * Space: O(1)
     */
    public ListNode deleteDuplicatesLc(ListNode head) {
        // sentinel
        ListNode sentinel = new ListNode(0, head);

        // predecessor = the last node 
        // before the sublist of duplicates
        ListNode pred = sentinel;

        while (head != null) {
            // if it's a beginning of duplicates sublist 
            // skip all duplicates
            if (head.next != null && head.val == head.next.val) {
                // move till the end of duplicates sublist
                while (head.next != null && head.val == head.next.val) {
                    head = head.next;
                }
                // skip all duplicates
                pred.next = head.next;
                // otherwise, move predecessor
            } else {
                pred = pred.next;
            }

            // move forward
            head = head.next;
        }
        return sentinel.next;
    }

    /**
     * Luke
     * 
     * Runtime: 1 ms, faster than 81.99% of Java online submissions for Remove Duplicates from Sorted List II.
     * Memory Usage: 43.3 MB, less than 76.03% of Java online submissions for Remove Duplicates from Sorted List II.
     * 
     * Time: O(n)
     * Space: O(1)
     */
    public ListNode deleteDuplicatesLuke(ListNode head) {
        ListNode curr = head;

        if (curr == null) {
            return null;
        }

        /**
         * TODO: Fix curr.val == Ingeger.MIN_VALUE
         */
        ListNode last = new ListNode(curr.val - 1);
        last.next = curr;

        /**
         * TODO: Fix curr.val == Ingeger.MIN_VALUE
         */
        ListNode lastLast = new ListNode(last.val - 2);
        lastLast.next = last;

        while (curr != null) {
            if (last.val != curr.val) {
                lastLast = last;
                last = curr;
                curr = curr.next;
                continue;
            } else {
                while (curr != null && last.val == curr.val) {
                    curr = curr.next;
                }

                // remove last
                if (head == last) {
                    head = curr;
                    lastLast = last;
                } else {
                    lastLast.next = curr;
                }

                last = curr;
                if (curr == null) {
                    return head;
                } else {
                    curr = curr.next;
                }
                continue;
            }
        }

        return head;
    }
}
