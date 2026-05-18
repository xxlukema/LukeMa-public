package com.learn.list;


import lombok.extern.log4j.Log4j2;


/**
 * LC 83
 */
@Log4j2
public class RemoveDupsFromSortedList {

    public static void main(String[] args) {

        // int[] nums = { 0, 0, 1, 2, 3, 3, 4, 4, 5 };
        // int[] nums = { 1, 1, 2, 2, 3, 3 };
        // int[] nums = { 1, 2, 2 };
        // int[] nums = { 0, 0, 1, 1, 2, 2, 2 };
        int[] nums = { 1, 1, 2, };

        // log.debug("list: {}", Arrays.stream(nums).boxed().toList());
        ListNode head = new ListNode(nums[0]);
        ListNode node = head;

        for (int i = 1; i < nums.length; i++) {
            node.next = new ListNode(nums[i]);
            node = node.next;
        }

        log.debug("list: {}", ListNode.toList(head));

        RemoveDupsFromSortedList removeDupsFromSortedList = new RemoveDupsFromSortedList();

        var ret = removeDupsFromSortedList.deleteDuplicatesLuke(head);
        log.debug("Removed dups Luke: {}", () -> ListNode.toList(ret));

    }

    /**
     * Luke
     * 
     * Runtime: 1 ms, faster than 76.82% of Java online submissions for Remove Duplicates from Sorted List.
     * Memory Usage: 44.9 MB, less than 5.77% of Java online submissions for Remove Duplicates from Sorted List.
     * 
     * Time: O(n)
     * Space: O(1)
     * 
     */
    private ListNode deleteDuplicatesLuke(ListNode head) {

        ListNode curr = head;
        ListNode last = null;

        while (curr != null) {
            if (last == null) {
                last = curr;
                curr = curr.next;
            }

            while (curr != null && last.val == curr.val) {
                curr = curr.next;
            }

            if (curr == null) {
                last.next = null;
                return head;
            } else {
                last.next = curr;
                last = curr;
                curr = curr.next;
            }
        }

        return head;
    }

}
