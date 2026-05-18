package com.learn.tree;


import org.junit.jupiter.api.Assertions;

import lombok.extern.log4j.Log4j2;


/**
 * LC - 104 - Max Depth of Binary Tree
 */
@Log4j2
public class MaxDepthOfBinaryTree {

    public static void main(String[] args) {

        Integer[] nums = { 3, 9, 20, null, null, 15, 7 };

        TreeNode root = TreeNode.toTreeBfsWithNullIntegers(nums);

        MaxDepthOfBinaryTree maxDepthOfBinaryTree = new MaxDepthOfBinaryTree();
        var retLuke = maxDepthOfBinaryTree.maxDepthLukeRecursion(root);
        log.debug("Max depth of binary tree: {}", () -> retLuke);

        var retLc = maxDepthOfBinaryTree.maxDepthLcRecursionDfs(root);
        Assertions.assertEquals(retLuke, retLc);
    }

    /**
     * LC - Recursion without additional helper function
     * 
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Maximum Depth of Binary Tree.
     * Memory Usage: 43.1 MB, less than 48.61% of Java online submissions for Maximum Depth of Binary Tree.
     * 
     * Time: O(n)
     * Space: O(n) - recursion takes memory of O(H)
     */
    public int maxDepthLcRecursionDfs(TreeNode root) {
        if (root == null) {
            return 0;
        } else if (root.left == null && root.right == null) {
            return 1;
        }

        return Math.max(maxDepthLcRecursionDfs(root.left), maxDepthLcRecursionDfs(root.right)) + 1;
    }

    /**
     * Luke - Recursion - DFS
     * 
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Maximum Depth of Binary Tree.
     * Memory Usage: 42.2 MB, less than 88.45% of Java online submissions for Maximum Depth of Binary Tree.
     * 
     * Time: O(n)
     * Space: O(n) - recursion takes memory of O(H)
     */
    public int maxDepthLukeRecursion(TreeNode root) {
        if (root == null) {
            return 0;
        }

        return maxDepthLukeRecursionDfs(root, 0);
    }

    private int maxDepthLukeRecursionDfs(TreeNode root, int level) {
        if (root == null) {
            return level;
        } else if (root.left == null && root.right == null) {
            return level + 1;
        }

        return Math.max(maxDepthLukeRecursionDfs(root.left, level + 1), maxDepthLukeRecursionDfs(root.right, level + 1));
    }
}
