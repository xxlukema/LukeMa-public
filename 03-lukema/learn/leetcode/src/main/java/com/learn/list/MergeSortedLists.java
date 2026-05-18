package com.learn.list;

public class MergeSortedLists {


    class Solution {

        public ListNode mergeTwoListsRecursion(ListNode list1, ListNode list2) {

            if(list1 == null) {
                return list2;
            } else if(list2 == null) {
                return list1;
            } else {
                if(list1.val < list2.val) {
                    list1.next = mergeTwoListsRecursion(list1.next, list2);
                    return list1;
                } else {
                    list2.next = mergeTwoListsRecursion(list1, list2.next);
                    return list2;
                }
            }
        }

        public ListNode mergeTwoListsWhile(ListNode list1, ListNode list2) {

            ListNode preHead = new ListNode(-1, null);
            ListNode curr = preHead;

            while(list1 != null && list2 != null) {
                if(list1.val < list2.val) {
                    curr.next = list1;
                    list1 = list1.next;
                } else {
                    curr.next = list2;
                    list2 = list2.next;
                }
                curr = curr.next;
            }

            curr.next = list1 != null ? list1 : list2;

            return preHead.next;
        }

    }
}
