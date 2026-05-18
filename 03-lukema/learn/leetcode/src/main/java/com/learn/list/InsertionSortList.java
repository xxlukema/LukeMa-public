package com.learn.list;


import lombok.extern.log4j.Log4j2;


/**
 * LC - 147 - Insertion Sort List
 * 
 * Medium
 * 
 * Given the head of a singly linked list, sort the list using insertion sort, and return the sorted list's head.
 * The steps of the insertion sort algorithm:
 *     Insertion sort iterates, consuming one input element each repetition and growing a sorted output list.
 *     At each iteration, insertion sort removes one element from the input data, finds the location it belongs within the sorted list and inserts it there.
 *     It repeats until no input elements remain.
 * 
 * Example 1:
 * Input: head = [4,2,1,3]
 * Output: [1,2,3,4]
 * 
 * Example 2:
 * Input: head = [-1,5,3,4,0]
 * Output: [-1,0,3,4,5]
 * 
 * Constraints:
 *     The number of nodes in the list is in the range [1, 5000].
 *     -5000 <= Node.val <= 5000
 */
@Log4j2
public class InsertionSortList {

    public static void main(String[] args) {

        final int[] nums = { 4, 2, 1, 3 };

        final ListNode head = ListNode.toList(nums);

        InsertionSortList insertionSortList = new InsertionSortList();

        var ret = insertionSortList.insertionSortList(head);

        log.debug("Insertion sort list: {}", () -> ret);

    }

    /**
     * Luke - Iteration
     * 
     * Runtime: 3 ms, faster than 97.32% of Java online submissions for Insertion Sort List.
     * Memory Usage: 45.6 MB, less than 6.83% of Java online submissions for Insertion Sort List.
     * 
     * Time: O(N ^ 2)
     * Space: O(1)
     */
    public ListNode insertionSortList(ListNode head) {
        if (head == null) {
            return null;
        }

        ListNode pre = head;
        ListNode curr = pre.next;

        while (curr != null) {
            if (curr.val < pre.val) {
                ListNode node = curr;
                pre.next = curr.next;
                curr = curr.next;

                node.next = null;
                head = insertionSortList(head, node);
            } else {
                pre = curr;
                curr = curr.next;
            }

        }

        return head;
    }

    ListNode insertionSortList(ListNode head, ListNode node) {
        ListNode pre = head;
        ListNode curr = head.next;

        if (node.val < pre.val) {
            node.next = head;
            return node;
        } else {
            while (curr != null) {
                if (curr.val < node.val) {
                    pre = curr;
                    curr = curr.next;
                } else {
                    pre.next = node;
                    node.next = curr;

                    return head;
                }
            }

            pre.next = node;

            return head;
        }
    }
}
