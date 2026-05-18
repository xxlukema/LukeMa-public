package com.learn.tree;


import java.util.ArrayList;
import java.util.IntSummaryStatistics;
import java.util.List;

import org.junit.jupiter.api.Assertions;

import lombok.extern.log4j.Log4j2;


/**
 * LC - 110 - Easy - Balanced Binary Tree
 *
 * Given a binary tree, determine if it is height-balanced.
 * For this problem, a height-balanced binary tree is defined as:
 *     A binary tree in which the left and right subtrees of every node differ in height by no more than 1.
 */
@Log4j2
public class BalancedBinaryTree {

    public static void main(String[] args) {

        // Integer[] nums = { 3, 9, 20, null, null, 15, 7 };
        Integer[] nums = { 1, 2, 3, 4, 5, 6, null, 8 };

        TreeNode root = TreeNode.toTreeBfsWithNullIntegers(nums);

        BalancedBinaryTree balancedBinaryTree = new BalancedBinaryTree();

        boolean isBlancedLukeTopDown = balancedBinaryTree.isBalancedLukeDfsRecursionTopDown(root);
        log.debug("Is balanced binary tree: {}", () -> isBlancedLukeTopDown);

        boolean isBlancedLukeBottomUp = balancedBinaryTree.isBalancedLukeDfsRecursionBottomUp(root);
        Assertions.assertEquals(isBlancedLukeTopDown, isBlancedLukeBottomUp);

        boolean isBlancedLcTopDown = balancedBinaryTree.isBalancedLcTopDownRecursion(root);
        Assertions.assertEquals(isBlancedLukeTopDown, isBlancedLcTopDown);

        boolean isBlancedLcBottomUp = balancedBinaryTree.isBalancedLcBottomUp(root);
        Assertions.assertEquals(isBlancedLukeTopDown, isBlancedLcBottomUp);
    }

    /**
     * For Bottom-Up Tracking
     */
    record TreeInfo(int height, boolean balanced) {
    }

    /**
     * Luke - Recursion - Bottom-Up
     *
     * Runtime: 1 ms, faster than 99.04% of Java online submissions for Balanced Binary Tree.
     * Memory Usage: 44.3 MB, less than 56.90% of Java online submissions for Balanced Binary Tree.
     *
     * Time: O(n)
     * Space: O(n)
     */
    public boolean isBalancedLukeDfsRecursionBottomUp(TreeNode root) {
        if (root == null) {
            return true;
        }
        return isBalancedLukeDfsRecursionBottomUp(root, 0).balanced;
    }

    private TreeInfo isBalancedLukeDfsRecursionBottomUp(TreeNode root, int level) {
        if (root == null) {
            return new TreeInfo(level, true);
        }

        level++;

        TreeInfo left = isBalancedLukeDfsRecursionBottomUp(root.left, level);
        if (!left.balanced) {
            return new TreeInfo(level, false);
        }

        TreeInfo right = isBalancedLukeDfsRecursionBottomUp(root.right, level);
        if (!right.balanced) {
            return new TreeInfo(level, false);
        }

        if (Math.abs(left.height - right.height) > 1) {
            return new TreeInfo(level, false);
        } else {
            int height = Math.max(left.height, right.height);
            return new TreeInfo(height, true);
        }
    }

    /**
     * Luke - DFS - Recursion - Top-Down
     *
     * Runtime: 2 ms, faster than 33.96% of Java online submissions for Balanced Binary Tree.
     * Memory Usage: 44.3 MB, less than 56.90% of Java online submissions for Balanced Binary Tree.
     *
     * Time: O(n * log(n))
     * Space: O(n)
     */
    public boolean isBalancedLukeDfsRecursionTopDown(TreeNode root) {
        if (root == null) {
            return true;
        }

        int heightLeft = height(root.left, 0);
        int heightRight = height(root.right, 0);

        if (Math.abs((heightLeft - heightRight)) > 1) {
            return false;
        }

        return isBalancedLukeDfsRecursionTopDown(root.left) &&
                isBalancedLukeDfsRecursionTopDown(root.right);
    }

    private int height(TreeNode root, int level) {
        if (root == null) {
            return level;
        }

        level++;

        return Math.max(height(root.left, level), height(root.right, level));
    }

    /**
     * LC - Recursion - Bottom-Up
     *
     * Runtime: 2 ms, faster than 33.96% of Java online submissions for Balanced Binary Tree.
     * Memory Usage: 45.2 MB, less than 5.59% of Java online submissions for Balanced Binary Tree.
     *
     * Time: O(n)
     * Space: O(n)
     */
    public boolean isBalancedLcBottomUp(TreeNode root) {
        return isBalancedTreeHelperBottomUp(root).balanced;
    }

    // Return whether or not the tree at root is balanced while also storing
    // the tree's height in a reference variable.
    private TreeInfo isBalancedTreeHelperBottomUp(TreeNode root) {
        // An empty tree is balanced and has height = -1
        if (root == null) {
            return new TreeInfo(-1, true);
        }

        // Check subtrees to see if they are balanced.
        TreeInfo left = isBalancedTreeHelperBottomUp(root.left);

        if (!left.balanced) {
            return new TreeInfo(-1, false);
        }
        TreeInfo right = isBalancedTreeHelperBottomUp(root.right);

        if (!right.balanced) {
            return new TreeInfo(-1, false);
        }

        // Use the height obtained from the recursive calls to
        // determine if the current node is also balanced.
        if (Math.abs(left.height - right.height) < 2) {
            return new TreeInfo(Math.max(left.height, right.height) + 1, true);
        }

        return new TreeInfo(-1, false);
    }

    /**
     * LC - Top-Down - Recursion
     */
    public boolean isBalancedLcTopDownRecursion(TreeNode root) {
        // An empty tree satisfies the definition of a balanced tree
        if (root == null) {
            return true;
        }

        // Check if subtrees have height within 1. If they do, check if the
        // subtrees are balanced
        return Math.abs(height(root.left) - height(root.right)) < 2
                && isBalancedLcTopDownRecursion(root.left)
                && isBalancedLcTopDownRecursion(root.right);
    }

    // Recursively obtain the height of a tree. An empty tree has -1 height
    private int height(TreeNode root) {
        // An empty tree has height -1
        if (root == null) {
            return -1;
        }
        return 1 + Math.max(height(root.left), height(root.right));
    }

    /**
     * Luke - DFS Top-Down - Recursion
     *
     * Cannot use total depth. Need to check each node.
     *
     * Time: O(n)
     * Space: O(n)
     */
    public boolean isBalancedLukeDfsRecursionWrong(TreeNode root) {
        if (root == null) {
            return true;
        }

        List<Integer> levels = new ArrayList<>();

        traverseLukeTopDownRecursion(root, 0, levels);

        log.debug("levels: {}", levels);

        IntSummaryStatistics stat = levels.stream().mapToInt(e -> e.intValue()).summaryStatistics();

        return stat.getMax() == stat.getMin() || stat.getMax() - 1 == stat.getMin();
    }

    private void traverseLukeTopDownRecursion(TreeNode root, int level, List<Integer> levels) {
        if (root == null) {
            levels.add(level);
            return;
        }

        level++;

        log.debug("level: {}, val: {}", level, root.val);

        traverseLukeTopDownRecursion(root.left, level, levels);
        traverseLukeTopDownRecursion(root.right, level, levels);
    }

}
