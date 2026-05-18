package com.learn.list;


import org.junit.jupiter.api.Assertions;

import lombok.extern.log4j.Log4j2;


/**
 * LC - 148 - Sort List
 * 
 * Medium
 * 
 * Given the head of a linked list, return the list after sorting it in ascending order.
 * 
 * Example 1:
 * Input: head = [4,2,1,3]
 * Output: [1,2,3,4]
 * 
 * Example 2:
 * Input: head = [-1,5,3,4,0]
 * Output: [-1,0,3,4,5]
 * 
 * Example 3:
 * Input: head = []
 * Output: []
 * 
 * Constraints:
 *     The number of nodes in the list is in the range [0, 5 * 104].
 *     -105 <= Node.val <= 105
 * 
 * Follow up: Can you sort the linked list in O(n logn) time and O(1) memory (i.e. constant space)?
 */
@Log4j2
public class SortList {

    public static void main(String[] args) {

        final int[] nums = { 4, 2, 1, 3 };
        // final int[] nums = { 1, 2, 3, 4, 5 };

        final ListNode head = ListNode.toList(nums);

        SortList sortList = new SortList();

        // ListNode mid = sortList.cutMid(head);
        // log.debug("head: {}, mid: {}", () -> head, () -> mid);

        var sortListTopDownMergeSort = sortList.sortListTopDownMergeSort(head);
        log.debug("Sort List: {}", () -> sortListTopDownMergeSort);
        log.debug("Sort list {} OK", () -> "sortListTopDownMergeSort");

        var sortListBottomUp = sortList.sortListBottomUp(head);
        Assertions.assertEquals(sortListTopDownMergeSort.toString(), sortListBottomUp.toString());
        log.debug("Sort list {} OK", () -> "sortListBottomUp");

    }

    /**
     * Luke - Bottom-Up
     */
    public ListNode sortListBottomUp(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        return head;
    }

    /**
     * Luke - Top-Down: Merge Sort
     * 
     * Runtime: 13 ms, faster than 88.12% of Java online submissions for Sort List.
     * Memory Usage: 81.7 MB, less than 11.60% of Java online submissions for Sort List.
     * 
     * Time: O(N log(N)). The Tree of "cut in middle or split" has depth of log(N). And at each level, we merge the N nodes O(N) times.
     * Space: O(log(N)). The cursion depth is log(N).
     */
    public ListNode sortListTopDownMergeSort(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode mid = cutMid(head);
        ListNode left = head;

        return mergeSort(left, mid);
    }

    private ListNode mergeSort(ListNode left, ListNode right) {

        /**
         * This block is optional
         */
        /*
        if (left == null) {
            return right;
        }
        
        if (right == null) {
            return left;
        }
        
        if (left.next == null && right.next == null) {
            if (left.val < right.val) {
                left.next = right;
                return left;
            } else {
                right.next = left;
                return right;
            }
        }
        */

        if (left != null && left.next != null) {
            ListNode mid = cutMid(left);
            left = mergeSort(left, mid);
        }

        if (right != null && right.next != null) {
            ListNode mid = cutMid(right);
            right = mergeSort(right, mid);
        }

        ListNode newHead = new ListNode();
        ListNode curr = newHead;
        while (left != null && right != null) {
            if (left.val < right.val) {
                curr.next = left;
                left = left.next;
            } else {
                curr.next = right;
                right = right.next;
            }
            curr = curr.next;
        }

        if (left != null) {
            curr.next = left;
        }

        if (right != null) {
            curr.next = right;
        }

        return newHead.next;
    }

    /**
     * Time: O(N). Total level of splittings: log"base 2"(N). If N == 16, log"base 2"(16) = 4.
     * Space: O(1)
     */
    ListNode cutMid(ListNode head) {
        if (head == null) {
            return head;
        }

        if (head.next == null) {
            return head;
        }

        ListNode slow = head.next;
        ListNode fast = head.next.next;

        ListNode slowPre = head;

        while (fast != null && fast.next != null) {
            slowPre = slow;
            slow = slow.next;
            fast = fast.next.next;
        }

        slowPre.next = null;

        return slow;
    }

    /**
     * LC - Bottom-Up
     * 
     * Time: O(N log(N))
     * Space: O(1)
     */
    ListNode tail = new ListNode();
    ListNode nextSubList = new ListNode();

    public ListNode sortList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        int n = getCount(head);
        ListNode start = head;
        ListNode dummyHead = new ListNode();
        for (int size = 1; size < n; size = size * 2) {
            tail = dummyHead;
            while (start != null) {
                if (start.next == null) {
                    tail.next = start;
                    break;
                }
                ListNode mid = split(start, size);
                merge(start, mid);
                start = nextSubList;
            }
            start = dummyHead.next;
        }
        return dummyHead.next;
    }

    ListNode split(ListNode start, int size) {
        ListNode midPrev = start;
        ListNode end = start.next;
        //use fast and slow approach to find middle and end of second linked list
        for (int index = 1; index < size && (midPrev.next != null || end.next != null); index++) {
            if (end.next != null) {
                end = (end.next.next != null) ? end.next.next : end.next;
            }
            if (midPrev.next != null) {
                midPrev = midPrev.next;
            }
        }
        ListNode mid = midPrev.next;
        midPrev.next = null;
        nextSubList = end.next;
        end.next = null;
        // return the start of second linked list
        return mid;
    }

    void merge(ListNode list1, ListNode list2) {
        ListNode dummyHead = new ListNode();
        ListNode newTail = dummyHead;
        while (list1 != null && list2 != null) {
            if (list1.val < list2.val) {
                newTail.next = list1;
                list1 = list1.next;
                newTail = newTail.next;
            } else {
                newTail.next = list2;
                list2 = list2.next;
                newTail = newTail.next;
            }
        }
        newTail.next = (list1 != null) ? list1 : list2;
        // traverse till the end of merged list to get the newTail
        while (newTail.next != null) {
            newTail = newTail.next;
        }
        // link the old tail with the head of merged list
        tail.next = dummyHead.next;
        // update the old tail to the new tail of merged list
        tail = newTail;
    }

    int getCount(ListNode head) {
        int cnt = 0;
        ListNode ptr = head;
        while (ptr != null) {
            ptr = ptr.next;
            cnt++;
        }
        return cnt;
    }
}
