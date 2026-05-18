package com.learn.list;


import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;


/**
 * @RequiredArgsConstructor generates a constructor with 1 parameter for each field that requires special handling.
 * All "non-initialized final" fields get a parameter, as well as any fields that are marked as @NonNull that are
 * not initialized where they are declared.
 */
@RequiredArgsConstructor
@AllArgsConstructor
/**
 * (1) Comment out @Data to prevent it from overriding "hashCode()" and "equals()". Use the default value (memory loation)
 *     for Set. Otherwise, two things will make the overriding fail: (1) The list can be cyclic. (2) The list can have
 *     dupliacted "val".
 * (2) For questions with cyclic loop (LC-141, LC-142), comment out "@Data" from "ListNode" to prevent cyclic infinite loop.
 * (3) For questions without cyclic loop (LC-143), Add "@Data" to "ListNode" to print the list.
 */
@Data
public class ListNode {

    /**
     * @NonNull does not apploy for primative fields.
     */
    public int val;
    public ListNode next;

    public ListNode(int val) {
        this.val = val;
    }

    public static ListNode toList(int[] nums, int pos) {
        List<ListNode> list = new ArrayList<>();

        ListNode head = new ListNode(nums[0]);
        ListNode node = head;

        list.add(head);

        for (int i = 1; i < nums.length; i++) {
            node.next = new ListNode(nums[i]);
            list.add(node.next);
            node = node.next;
        }

        if (pos >= 0 && pos < list.size()) {
            node.next = list.get(pos);
        }

        return head;
    }

    public static ListNode toList(int[] nums) {

        if (nums.length == 0) {
            return null;
        }

        ListNode head = new ListNode(nums[0]);
        ListNode node = head;

        for (int i = 1; i < nums.length; i++) {
            node.next = new ListNode(nums[i]);
            node = node.next;
        }

        return head;
    }

    public static List<Integer> toList(ListNode head) {
        List<Integer> list = new ArrayList<>();
        while (head != null) {
            list.add(head.val);
            head = head.next;
        }
        return list;
    }

    /**
     * Use default
     */
    /*
    @Override
    public String toString() {
        return "ListNode [val=" + val + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ListNode other = (ListNode) obj;
        if (val != other.val)
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + val;
        return result;
    }
    */

}
