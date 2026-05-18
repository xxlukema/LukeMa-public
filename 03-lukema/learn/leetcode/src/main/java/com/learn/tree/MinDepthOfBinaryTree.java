package com.learn.tree;


import lombok.extern.log4j.Log4j2;


/**
 * LC - 111 - Easy - Min Depth of Binary Tree
 *
 * Given a binary tree, find its minimum depth.
 * The minimum depth is the number of nodes along the shortest path from the root node down to the nearest leaf node.
 */
@Log4j2
public class MinDepthOfBinaryTree {

    public static void main(String[] args) {

        // Integer[] nums = { 3, 9, 20, null, null, 15, 7 };
        Integer[] nums = { 2, null, 3, null, 4, null, 5, null, 6 };

        TreeNode root = TreeNode.toTreeBfsWithNullIntegers(nums);

        MinDepthOfBinaryTree minDepthOfBinaryTree = new MinDepthOfBinaryTree();

        var minDepth = minDepthOfBinaryTree.minDepthLukeRecursion(root);
        log.debug("Min depth: {}", () -> minDepth);
    }

    /**
     * Luke - Recursion
     *
     * Runtime: 1 ms, faster than 97.73% of Java online submissions for Minimum Depth of Binary Tree.
     * Memory Usage: 96.7 MB, less than 6.59% of Java online submissions for Minimum Depth of Binary Tree.
     *
     * Time: O(n)
     * Space: O(n)
     */
    int minDepth = Integer.MAX_VALUE;

    public int minDepthLukeRecursion(TreeNode root) {
        if (root == null) {
            minDepth = 0;
            return minDepth;
        }

        minDepth(root, 0);

        return minDepth;
    }

    private void minDepth(TreeNode root, int level) {
        if (root == null) {
            minDepth = Math.min(minDepth, level);
            return;
        }

        level++;

        if (minDepth > 0 && level >= minDepth) {
            return;
        }

        if (root.left == null) {
            minDepth(root.right, level);
        } else if (root.right == null) {
            minDepth(root.left, level);
        } else {
            minDepth(root.left, level);
            minDepth(root.right, level);
        }
    }
}
