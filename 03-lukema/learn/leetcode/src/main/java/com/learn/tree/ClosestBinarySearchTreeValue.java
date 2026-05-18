package com.learn.tree;


import org.junit.jupiter.api.Assertions;

import lombok.extern.log4j.Log4j2;


/**
 * LC-270 Closest Binary Search Tree Value
 *
 * Easy
 *
 * Given the root of a binary search tree and a target value, return the value in the BST that is closest to the target.
 *
 * Example 1:
 * Input: root = [4,2,5,1,3], target = 3.714286
 * Output: 4
 *
 * Example 2:
 * Input: root = [1], target = 4.428571
 * Output: 1
 *
 * Constraints:
 *     The number of nodes in the tree is in the range [1, 10 ^ 4].
 *     0 <= Node.val <= 10 ^ 9
 *     -10 ^ 9 <= target <= 10 ^ 9
 */
@Log4j2
public class ClosestBinarySearchTreeValue {

    public static void main(String[] args) {

        /**
         * Expected: 4
         */
        // final Integer[] nums = { 4, 2, 5, 1, 3 };
        // final double target = 3.714286;

        /**
         * Expected: 2
         */
        final Integer[] nums = { 8, 12, 45, 4, 24, 35, 47, 2, 9, 14, 25, 31, 42, 46, 48, 0, 3, 8, 11, 13, 20, null, 26, 30, 33, 41, 43, null,
                null, null, 49, null, 1, null, null, 7, null, 10, null, null, null, 17, 22, null, 27, 29, null, 32, 34, 36, null, null, 44, null,
                null, null, null, 6, null, null, null, 16, 18, 21, 23, null, null, null, null, null, null, null, null, null, 37, null, null, 5,
                null, 15, null, null, 19, null, null, null, null, null, 40, null, null, null, null, null, null, 39, null, 38 };

        final double target = 2.000;

        ClosestBinarySearchTreeValue closestBinarySearchTreeValue = new ClosestBinarySearchTreeValue();

        TreeNode root = TreeNode.toTreeBfsWithNullIntegers(nums);

        closestBinarySearchTreeValue.diff = Integer.MAX_VALUE;
        closestBinarySearchTreeValue.value = 0;

        var closestValueWholeTreeTraversal = closestBinarySearchTreeValue.closestValueWholeTreeTraversal(root, target);
        log.debug("Closest Binary Search Tree Value: {}", () -> closestValueWholeTreeTraversal);
        log.debug("Closest Binary Search Tree Value {} OK", () -> "closestValueWholeTreeTraversal");

        closestBinarySearchTreeValue.diff = Integer.MAX_VALUE;
        closestBinarySearchTreeValue.value = 0;

        var closestValueLukeBinarySearch = closestBinarySearchTreeValue.closestValueLukeBinarySearch(root, target);
        Assertions.assertEquals(closestValueWholeTreeTraversal, closestValueLukeBinarySearch);
        log.debug("Closest Binary Search Tree Value {} OK", () -> "closestValueLukeBinarySearch");

    }

    private double diff = Integer.MAX_VALUE;
    private int value = 0;

    /**
     * Luke - Whole Tree Traversal
     *
     * Time: O(N)
     * Space: O(N)
     */
    public int closestValueWholeTreeTraversal(TreeNode root, double target) {

        double curDiff = Math.abs(root.val - target);

        if (curDiff < diff) {
            diff = curDiff;
            value = root.val;
        }

        if (diff == 0) {
            return value;
        }

        if (root.left != null) {
            closestValueWholeTreeTraversal(root.left, target);
        }

        if (root.right != null) {
            closestValueWholeTreeTraversal(root.right, target);
        }

        return value;
    }

    /**
     * LC - More difficult to understand.
     *
     * @see #closestValueLukeBinarySearch(TreeNode, double)
     *
     * Runtime: 0 ms Beats 100%
     * Memory: 41.7 MB Beats 99.45%
     *
     * Time: O(Height)
     * Space: O(1)
     */
    public int closestValueLC(TreeNode root, double target) {
        int val, closest = root.val;
        while (root != null) {
            val = root.val;
            closest = Math.abs(val - target) < Math.abs(closest - target) ? val : closest;
            root = target < root.val ? root.left : root.right;
        }
        return closest;
    }

    /**
     * Luke - Improved from LC. Easier to understand.
     *
     * Runtime: 0 ms Beats 100%
     * Memory: 41.7 MB Beats 99.45%
     *
     * Time: O(Tree hieght (H))
     * Space: O(1)
     */
    public int closestValueLukeBinarySearch(TreeNode root, double target) {

        int value = root.val;
        double diff = Math.abs(value - target);

        while (root != null) {
            double curDiff = Math.abs(root.val - target);

            if (curDiff < diff) {
                diff = curDiff;
                value = root.val;
            }

            if (target < root.val) {
                root = root.left;
            } else {
                root = root.right;
            }
        }

        return value;
    }
}
