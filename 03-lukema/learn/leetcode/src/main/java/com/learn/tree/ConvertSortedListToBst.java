package com.learn.tree;


import lombok.extern.log4j.Log4j2;


/**
 * LC - 109 - Convert Sorted List to BST
 *
 * Given the head of a singly linked list where elements are sorted in ascending order, convert it to a height balanced BST.
 * For this problem, a height-balanced binary tree is defined as a binary tree in which the depth of the two subtrees of every node never differ by more than 1.
 */
@Log4j2
public class ConvertSortedListToBst {

    public static void main(String[] args) {

        int[] nums = { -10, -3, 0, 5, 9 };

        ListNode head = ListNode.toListNode(nums);

        ConvertSortedListToBst convertSortedListToBst = new ConvertSortedListToBst();

        TreeNode root = convertSortedListToBst.sortedListToBST(head);
        log.debug("BST: {}", () -> root);

    }

    /**
     * LC - (1) Find the side of the list, then recurse.
     *      (2) Simulate inorder traversal.
     *      (3) In each recursion, the head will be move one node to next, and it is the "current middle"
     *      (4) DO NOT use "get()", because it will cause time complexity of O((log(n)) ^ 2)
     *
     * Since head is a shared variable throughout all recursions, it must be kept as an "instance variable".
     * This makes the class stateful. That is the <b>DRAWBACK</b> of this algorithm.
     *
     * Two other LC solutions are:
     * (1) Use pre-to-middle pointer to terminate the list prior to middle. Use middle pointer to find middle using iteration.
     *     And use faster pointer to detect the end of the list.
     * (2) Iterate the list to create a new ArrayList. Use the Array list's "size()" and "get()" to build the BST recursively.
     *
     * Runtime: 1 ms, faster than 88.50% of Java online submissions for Convert Sorted List to Binary Search Tree.
     * Memory Usage: 46.9 MB, less than 68.08% of Java online submissions for Convert Sorted List to Binary Search Tree.
     *
     * Time: O(n) - Since we have to process each of the nodes in the linked list once and form corresponding BST nodes.
     * Space: O(log(n)) - since now the only extra space is used by the recursion stack and since we are building a height
     *                    balanced BST, the height is bounded by log(⁡N)
     */
    public TreeNode sortedListToBST(ListNode head) {
        if (head == null) {
            return null;
        }

        /**
         * Init instance variable
         */
        this.head = head;

        /**
         * Call this once only before recursion, because head is moving to next in each iteration.
         */
        int len = findSizeOfList(head);

        return sortedListToBST(0, len - 1);
    }

    /**
     * Since head is a shared variable throughout all recursions, it must be kept as an "instance variable".
     * This makes the class stateful. That is the <b>DRAWBACK</b> of this algorithm.
     */
    private ListNode head = null;

    private TreeNode sortedListToBST(int start, int end) {
        if (start > end) {
            return null;
        }

        int mid = (start + end) / 2;

        /**
         * 1/3 Key step: Build left first! This will cause head move to next in the recursion.
         */
        TreeNode left = sortedListToBST(start, mid - 1);

        /**
         * 2/3 key step: This is the "current middle"
         */
        TreeNode root = new TreeNode(head.val);
        root.left = left;

        /**
         * 3/3 Key step: Move head to next in the recursion.
         */
        this.head = this.head.next;

        root.right = sortedListToBST(mid + 1, end);

        return root;
    }

    /**
     * Call this once only before recursion, because head is moving to next in each iteration.
     */
    private int findSizeOfList(ListNode head) {
        int len = 0;
        while (head != null) {
            len++;
            head = head.next;
        }
        return len;
    }

    /**
     * Do not use "get()"! It will cause time complexity of O((log(n)) ^ 2).
     *
     * Runtime: 227 ms, faster than 7.14% of Java online submissions for Convert Sorted List to Binary Search Tree.
     * Memory Usage: 46.6 MB, less than 76.14% of Java online submissions for Convert Sorted List to Binary Search Tree.
     *
     * Time: O(n * log(n))
     * Space: O(log(n))
     */
    protected ListNode DoNotUseGet(ListNode head, int idx) {
        int i = 0;
        ListNode curr = head;
        while (i++ != idx) {
            curr = curr.next;
        }
        return curr;
    }
}
