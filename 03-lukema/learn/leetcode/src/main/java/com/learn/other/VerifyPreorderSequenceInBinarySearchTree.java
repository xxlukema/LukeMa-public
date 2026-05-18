package com.learn.other;


import java.util.Stack;

import org.junit.jupiter.api.Assertions;

import lombok.extern.log4j.Log4j2;


/**
 * LC-255 Verify Preorder Sequence In Binary Search Tree
 *
 * Medium
 *
 * Given an array of unique integers preorder, return true if it is the correct preorder traversal sequence of a binary search tree.
 *
 * Example 1:
 * Input: preorder = [5,2,1,3,6]
 * Output: true
 *
 * Example 2:
 * Input: preorder = [5,2,6,1,3]
 * Output: false
 *
 * Constraints:
 *     1 <= preorder.length <= 104
 *     1 <= preorder[i] <= 104
 *     All the elements of preorder are unique.
 *
 * Follow up: Could you do it using only constant space complexity?
 */
@Log4j2
public class VerifyPreorderSequenceInBinarySearchTree {

    public static void main(String[] args) {

        /**
         * Expected: true
         */
        final int[] preorder = { 5, 2, 1, 3, 6 };

        /**
         * Expected: true
         */
        // final int[] preorder = { 2, 1 };

        /**
         * Expected: false
         */
        // final int[] preorder = { 5, 2, 6, 1, 3 };

        VerifyPreorderSequenceInBinarySearchTree verifyPreorderSequenceInBinarySearchTree = new VerifyPreorderSequenceInBinarySearchTree();

        var verifyPreorderRecursive = verifyPreorderSequenceInBinarySearchTree.verifyPreorderRecursive(preorder);
        log.debug("Verify Preorder Sequence In Binary Search Tree: {}", () -> verifyPreorderRecursive);
        log.debug("Verify Preorder Sequence In Binary Search Tree {} OK", () -> "verifyPreorderRecursive");

        var verifyPreorderStack = verifyPreorderSequenceInBinarySearchTree.verifyPreorderStack(preorder);
        Assertions.assertEquals(verifyPreorderRecursive, verifyPreorderStack);
        log.debug("Verify Preorder Sequence In Binary Search Tree {} OK", () -> "verifyPreorderStack");
    }

    /**
     * YouTube - https://www.youtube.com/watch?v=raHLlFKtsXw
     * YouTube - https://www.youtube.com/watch?v=GYdC4hQSo8A
     *
     * Theorem: (all elements of left subtree) < root < (all elements of right subtree)
     *
     * Runtime: 510 ms Beats 8.11%
     * Memory: 54.2 MB Beats 38.31%
     *
     * Time: O(N * log(N)) average, O(N ^ 2) worst case
     * Space: O(log(N)) average (depth of tree), O(N) worst case
     */
    public boolean verifyPreorderRecursive(int[] preorder) {
        if (preorder == null) {
            return false;
        }
        return verifyPreorderRecursive(preorder, 0, preorder.length - 1);
    }

    private boolean verifyPreorderRecursive(final int[] preorder, final int start, final int end) {
        if (start >= end) {
            return true;
        }

        int root = preorder[start];

        /**
         * Find start of right subtree
         */
        int idxStartRightSubTree = -1;
        for (int i = start + 1; i <= end; i++) {
            if (preorder[i] > root) {
                idxStartRightSubTree = i;
                break;
            }
        }

        if (idxStartRightSubTree == -1) {
            /**
             * No right subtree
             */
            return verifyPreorderRecursive(preorder, start + 1, end);
        } else {
            /**
             * All right subtree elements are greater than root
             */
            for (int i = idxStartRightSubTree + 1; i <= end; i++) {
                if (preorder[i] < root) {
                    return false;
                }
            }

            return verifyPreorderRecursive(preorder, start + 1, idxStartRightSubTree - 1)
                    && verifyPreorderRecursive(preorder, idxStartRightSubTree, end);
        }
    }

    /**
     * LC - Stack
     *
     * Theorem: push left branch elements to stack, until it reaches the left leaf.
     * Then, pop from stack until next element in preorder array is greater than stack.
     * The popped element is the subtree root. cache it as min. All right subtree nodes
     * are smaller than it.
     * Repeat above steps.
     *
     * Runtime: 41 ms Beats 40.39%
     * Memory: 54.9 MB Beats 6.84%
     *
     * Time: O(N)
     * Space: O(1)
     */
    public boolean verifyPreorderStack(int[] preorder) {

        if (preorder == null) {
            return false;
        }

        if (preorder.length == 0) {
            return true;
        }

        final Stack<Integer> stack = new Stack<>();
        stack.push(preorder[0]);

        int i = 1;
        int min = Integer.MIN_VALUE;

        while (!stack.isEmpty() && i < preorder.length) {
            int cur = preorder[i++];
            if (cur < stack.peek()) {
                // left subtree
                if (cur < min) {
                    return false;
                } else {
                    stack.push(cur);
                }
            } else {
                // right subtree
                while (!stack.isEmpty() && cur > stack.peek()) {
                    /**
                     * subtree root
                     */
                    min = stack.pop();
                }
                stack.push(cur);
            }
        }

        return true;
    }

}


class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    public TreeNode(int val) {
        this.val = val;
    }

    public TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}
