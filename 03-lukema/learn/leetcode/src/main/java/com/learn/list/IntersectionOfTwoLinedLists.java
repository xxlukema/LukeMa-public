package com.learn.list;


import java.util.HashSet;
import java.util.Set;


/**
 * LC - 160 - Intersection Of Two Linked Lists
 * 
 * Easy
 * 
 * Given the heads of two singly linked-lists headA and headB, return the node at which the two lists intersect. If the two linked lists have no intersection at all, return null.
 * 
 * For example, the following two linked lists begin to intersect at node c1:
 * The test cases are generated such that there are no cycles anywhere in the entire linked structure.
 * Note that the linked lists must retain their original structure after the function returns.
 * 
 * Custom Judge:
 * 
 * The inputs to the judge are given as follows (your program is not given these inputs):
 *     intersectVal - The value of the node where the intersection occurs. This is 0 if there is no intersected node.
 *     listA - The first linked list.
 *     listB - The second linked list.
 *     skipA - The number of nodes to skip ahead in listA (starting from the head) to get to the intersected node.
 *     skipB - The number of nodes to skip ahead in listB (starting from the head) to get to the intersected node.
 * 
 * The judge will then create the linked structure based on these inputs and pass the two heads, headA and headB to your program. If you correctly return the intersected node, 
 * then your solution will be accepted.
 * 
 * Example 1:
 * Input: intersectVal = 8, listA = [4,1,8,4,5], listB = [5,6,1,8,4,5], skipA = 2, skipB = 3
 * Output: Intersected at '8'
 * Explanation: The intersected node's value is 8 (note that this must not be 0 if the two lists intersect).
 * From the head of A, it reads as [4,1,8,4,5]. From the head of B, it reads as [5,6,1,8,4,5]. There are 2 nodes before the intersected node in A; There are 3 nodes before
 * the intersected node in B.
 * 
 * - Note that the intersected node's value is not 1 because the nodes with value 1 in A and B (2nd node in A and 3rd node in B) are different node references. In other words,
 * they point to two different locations in memory, while the nodes with value 8 in A and B (3rd node in A and 4th node in B) point to the same location in memory.
 * 
 * Example 2:
 * Input: intersectVal = 2, listA = [1,9,1,2,4], listB = [3,2,4], skipA = 3, skipB = 1
 * Output: Intersected at '2'
 * Explanation: The intersected node's value is 2 (note that this must not be 0 if the two lists intersect).
 * From the head of A, it reads as [1,9,1,2,4]. From the head of B, it reads as [3,2,4]. There are 3 nodes before the intersected node in A; There are 1 node before the
 * intersected node in B.
 * 
 * Example 3:
 * Input: intersectVal = 0, listA = [2,6,4], listB = [1,5], skipA = 3, skipB = 2
 * Output: No intersection
 * Explanation: From the head of A, it reads as [2,6,4]. From the head of B, it reads as [1,5]. Since the two lists do not intersect, intersectVal must be 0, while skipA and
 * skipB can be arbitrary values.
 * Explanation: The two lists do not intersect, so return null.
 * 
 * Constraints:
 *     The number of nodes of listA is in the m.
 *     The number of nodes of listB is in the n.
 *     1 <= m, n <= 3 * 104
 *     1 <= Node.val <= 105
 *     0 <= skipA < m
 *     0 <= skipB < n
 *     intersectVal is 0 if listA and listB do not intersect.
 *     intersectVal == listA[skipA] == listB[skipB] if listA and listB intersect.
 * 
 * Follow up: Could you write a solution that runs in O(m + n) time and use only O(1) memory?
 */
public class IntersectionOfTwoLinedLists {

    public static void main(String[] args) {

    }

    /**
     * Luke - HashSet
     * 
     * Runtime: 18 ms, faster than 11.02% of Java online submissions for Intersection of Two Linked Lists.
     * Memory Usage: 55.2 MB, less than 63.47% of Java online submissions for Intersection of Two Linked Lists.
     * 
     * Time: O(M + N)
     * Space: O(M)
     */
    public ListNode getIntersectionNodeSet(ListNode headA, ListNode headB) {

        /**
         * Space: O(M)
         */
        Set<ListNode> set = new HashSet<>();

        ListNode curr = headA;

        /**
         * Time: O(M)
         */
        while (curr != null) {
            set.add(curr);
            curr = curr.next;
        }

        curr = headB;

        /**
         * Time: O(N)
         */
        while (curr != null) {
            if (set.contains(curr)) {
                return curr;
            }

            curr = curr.next;
        }

        return null;
    }

    /**
     * LC - Two Pointers - How to break the loop if two lists have no intersection?
     * 
     * Runtime: 1 ms, faster than 99.74% of Java online submissions for Intersection of Two Linked Lists.
     * Memory Usage: 54.6 MB, less than 76.61% of Java online submissions for Intersection of Two Linked Lists.
     * 
     * Time: O(M + N)
     * Space: O(1)
     */
    public ListNode getIntersectionNodeLcTwoPointers(ListNode headA, ListNode headB) {

        ListNode pA = headA;
        ListNode pB = headB;

        /**
         * How is the loop broken out if there is no intersection?
         */
        while (pA != pB) {
            pA = pA == null ? headB : pA.next;
            pB = pB == null ? headA : pB.next;
        }
        return pA;
    }

    /**
     * Luke - Two Pointers
     * 
     * Runtime: 1 ms, faster than 99.74% of Java online submissions for Intersection of Two Linked Lists.
     * Memory Usage: 55.4 MB, less than 43.97% of Java online submissions for Intersection of Two Linked Lists.
     * 
     * Time: O(M + N + Math.max(M, N))
     * Space: O(1)
     */
    public ListNode getIntersectionNodeLukeTwoPointers(ListNode headA, ListNode headB) {

        ListNode pA = headA;
        ListNode pB = headB;

        int lenA = 0;

        /**
         * O(M)
         */
        while (pA != null) {
            lenA++;
            pA = pA.next;
        }

        int lenB = 0;

        while (pB != null) {
            lenB++;
            pB = pB.next;
        }

        /**
         * Reset pA, pB
         */
        pA = headA;
        pB = headB;

        /**
         * Skip leading nodes and then compare
         * 
         * Time: O(max(M, N))
         * Space: O(1)
         */
        if (lenA > lenB) {
            for (int i = 0; i < lenA - lenB; i++) {
                pA = pA.next;
            }
        } else if (lenA < lenB) {
            for (int i = 0; i < lenB - lenA; i++) {
                pB = pB.next;
            }
        }

        while (pA != null && pA != pB) {
            pA = pA.next;
            pB = pB.next;
        }

        return pA;
    }
}
