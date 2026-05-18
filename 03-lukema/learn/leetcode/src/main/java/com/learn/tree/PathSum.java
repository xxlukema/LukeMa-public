package com.learn.tree;


import lombok.extern.log4j.Log4j2;


/**
 * LC - 112 - Easy - Path Sum
 * 
 * Given the root of a binary tree and an integer targetSum, return true if the tree has a root-to-leaf path such that adding
 * up all the values along the path equals targetSum.
 * 
 * A leaf is a node with no children.
 */
@Log4j2
public class PathSum {

    public static void main(String[] args) {

        // Integer[] nums = { 5, 4, 8, 11, null, 13, 4, 7, 2, null, null, null, 1 };
        // int targetSum = 22;

        Integer[] nums = { 1, 2 };
        int targetSum = 1;

        TreeNode root = TreeNode.toTreeBfsWithNullIntegers(nums);

        PathSum pathSum = new PathSum();
        boolean hasPathSum = pathSum.hasPathSum(root, targetSum);
        log.debug("Has path sum: {}", () -> hasPathSum);
    }

    /**
     * Luke - DFS - Recursion
     * 
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Path Sum.
     * Memory Usage: 44.2 MB, less than 12.56% of Java online submissions for Path Sum.
     * 
     * Time: O(n)
     * Space: O(n)
     */
    public boolean hasPathSum(TreeNode root, int targetSum) {
        if (root == null) {
            return false;
        }

        return hasPathSum(root, targetSum, 0);
    }

    public boolean hasPathSum(TreeNode root, int targetSum, int sum) {
        if (root == null) {
            return sum == targetSum;
        }

        sum += root.val;

        boolean hasPathSum = false;

        if (root.left == null) {
            hasPathSum = hasPathSum(root.right, targetSum, sum);
        } else if (root.right == null) {
            hasPathSum = hasPathSum(root.left, targetSum, sum);
        } else {
            hasPathSum = hasPathSum(root.left, targetSum, sum) || hasPathSum(root.right, targetSum, sum);
        }

        return hasPathSum;
    }
}
