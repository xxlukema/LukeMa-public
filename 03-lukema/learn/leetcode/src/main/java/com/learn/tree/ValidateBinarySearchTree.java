package com.learn.tree;


import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.junit.jupiter.api.Assertions;

import lombok.extern.log4j.Log4j2;


/**
 * LC - 98 - Validate Binart Search Tree
 * 
 * Medium
 * 
 * Given the root of a binary tree, determine if it is a valid binary search tree (BST).
 * 
 * A valid BST is defined as follows:
 * 
 *    - The left subtree of a node contains only nodes with keys less than the node's key.
 *    - The right subtree of a node contains only nodes with keys greater than the node's key.
 *    - Both the left and right subtrees must also be binary search trees.
 * 
 */
@Log4j2
public class ValidateBinarySearchTree {

    public static void main(String[] args) {

        // Integer[] nums = { 2, 1, 3 };
        // Integer[] nums = { 2, 2, 2 };
        // Integer[] nums = { 5, 1, 4, null, null, 3, 6 };
        // Integer[] nums = { 5, 4, 6, null, null, 3, 7 };
        // Integer[] nums = { 3, 1, 5, 0, 2, 4, 6 };
        Integer[] nums = { 3, 1, 5, 0, 2, 4, 6, null, null, null, 3 };
        // Integer[] nums = { -2147483648, null, 2147483647 };

        TreeNode root = TreeNode.toTreeHeapSortSubrootNoNull(nums);

        ValidateBinarySearchTree validateBinarySearchTree = new ValidateBinarySearchTree();

        log.debug(() -> "Start Testing...");

        var retLcRecursion = validateBinarySearchTree.isValidBSTLcRecursionWithLowHigh(root);
        log.debug("Validate BST: {}", () -> retLcRecursion);

        var retRecursiveGetMaxMin = validateBinarySearchTree.isValidBSTLukeRecursionRecursiveGetMaxGetMin(root);
        Assertions.assertEquals(retLcRecursion, retRecursiveGetMaxMin);
        log.debug("Validate BST retRecursiveGetMaxMin: {}", () -> retRecursiveGetMaxMin);

        var retLcDequeue = validateBinarySearchTree.isValidBSTLcDeque(root);
        Assertions.assertEquals(retLcRecursion, retLcDequeue);
        log.debug("Validate binary search tree isValidBSTLcDeque: {}", () -> retLcDequeue);

        var retLukeWithGetMaxGetMinDp = validateBinarySearchTree.isValidBSTLukeWithGetMaxGetMinDp(root);
        Assertions.assertEquals(retLcRecursion, retLukeWithGetMaxGetMinDp);
        log.debug("Validate binary search tree isValidBSTLcDeque: {}", () -> retLukeWithGetMaxGetMinDp);

        log.debug(() -> "Test Complete");
    }

    /**
     * LC - Deque
     *
     * Runtime: 7 ms, faster than 5.11% of Java online submissions for Validate Binary Search Tree.
     * Memory Usage: 45.9 MB, less than 5.82% of Java online submissions for Validate Binary Search Tree.
     *
     * Time: O(n)
     * Space: O(n)
     */
    public boolean isValidBSTLcDeque(TreeNode root) {
        Integer leftMax = null, rightMin = null, val;
        update(root, leftMax, rightMin);

        while (!stack.isEmpty()) {
            root = stack.poll();
            leftMax = leftMaxDeque.poll();
            rightMin = rightMinDeque.poll();

            if (root == null) {
                continue;
            }

            val = root.val;
            if (leftMax != null && val <= leftMax) {
                return false;
            }
            if (rightMin != null && val >= rightMin) {
                return false;
            }
            update(root.right, val, rightMin);
            update(root.left, leftMax, val);
        }
        return true;
    }

    private final Deque<TreeNode> stack = new LinkedList<>();
    private final Deque<Integer> rightMinDeque = new LinkedList<>();
    private final Deque<Integer> leftMaxDeque = new LinkedList<>();

    public void update(TreeNode root, Integer leftMax, Integer rightMin) {
        stack.add(root);
        leftMaxDeque.add(leftMax);
        rightMinDeque.add(rightMin);
    }

    /**
     * LC - Recursion with low/high param
     *
     * Runtime: 1 ms, faster than 61.12% of Java online submissions for Validate Binary Search Tree.
     * Memory Usage: 44.6 MB, less than 13.84% of Java online submissions for Validate Binary Search Tree.
     *
     * Time: O(n)
     * Space: O(n)
     */
    public boolean isValidBSTLcRecursionWithLowHigh(TreeNode root) {
        return validateLcRecursionWithLowHigh(root, null, null);
    }

    private boolean validateLcRecursionWithLowHigh(TreeNode root, Integer leftMax, Integer rightMin) {
        // Empty trees are valid BSTs.
        if (root == null) {
            return true;
        }
        // The current node's value must be between low and high.
        if ((rightMin != null && root.val >= rightMin) || (leftMax != null && root.val <= leftMax)) {
            return false;
        }
        // The left and right subtree must also be valid.
        return validateLcRecursionWithLowHigh(root.left, leftMax, root.val) && validateLcRecursionWithLowHigh(root.right, root.val, rightMin);
    }

    /**
     * Luke Recursion + DP
     * 
     * Bad - The DP in getMax() getMin does not work.
     *
     * Runtime: 6 ms, faster than 5.12% of Java online submissions for Validate Binary Search Tree.
     * Memory Usage: 45.3 MB, less than 5.83% of Java online submissions for Validate Binary Search Tree.
     *
     * Time: O(n) for the first time.
     * Space: O(n)
     */
    public boolean isValidBSTLukeWithGetMaxGetMinDp(TreeNode root) {
        if (root == null || (root.left == null && root.right == null)) {
            return true;
        }

        if (root.left != null) {
            if (root.left.val >= root.val) {
                return false;
            }
        }

        if (root.right != null) {
            if (root.right.val <= root.val) {
                return false;
            }
        }

        if (root.left != null && getMaxDp(root.left) >= root.val) {
            return false;
        }

        if (root.right != null && getMinDp(root.right) <= root.val) {
            return false;
        }

        return isValidBSTLukeWithGetMaxGetMinDp(root.left) && isValidBSTLukeWithGetMaxGetMinDp(root.right);
    }

    final Map<TreeNode, Integer> dpMax = new HashMap<>();
    final Map<TreeNode, Integer> dpMin = new HashMap<>();

    /**
     * Time: O(N) for the first time only
     * Space: O(N)
     */
    int getMaxDp(TreeNode root) {
        if (root == null) {
            return Integer.MIN_VALUE;
        }

        if (dpMax.get(root) != null) {
            return dpMax.get(root);
        }

        int max = Math.max(root.val, getMaxDp(root.left));
        max = Math.max(max, getMaxDp(root.right));

        dpMax.put(root, max);

        return max;
    }

    /**
     * Time: O(N)
     * Space: O(N)
     */
    int getMinDp(TreeNode root) {
        if (root == null) {
            return Integer.MAX_VALUE;
        }

        if (dpMin.get(root) != null) {
            return dpMin.get(root);
        }

        int min = Math.min(root.val, getMinDp(root.left));
        min = Math.min(min, getMinDp(root.right));

        dpMin.put(root, min);

        return min;
    }

    /**
     * Luke Recursion
     * 
     * Bad: It recursively calls getMax() and getMin(). That wastes extra time.
     *
     * Runtime: 5 ms, faster than 5.11% of Java online submissions for Validate Binary Search Tree.
     * Memory Usage: 43.8 MB, less than 63.07% of Java online submissions for Validate Binary Search Tree.
     *
     * Time: O(n ^ 2)
     * Space: O(1)
     */
    public boolean isValidBSTLukeRecursionRecursiveGetMaxGetMin(TreeNode root) {
        if (root == null || (root.left == null && root.right == null)) {
            return true;
        }

        if (root.left != null) {
            if (root.left.val >= root.val) {
                return false;
            }
        }

        if (root.right != null) {
            if (root.right.val <= root.val) {
                return false;
            }
        }

        if (root.left != null && getMax(root.left) >= root.val) {
            return false;
        }

        if (root.right != null && getMin(root.right) <= root.val) {
            return false;
        }

        return isValidBSTLukeRecursionRecursiveGetMaxGetMin(root.left) && isValidBSTLukeRecursionRecursiveGetMaxGetMin(root.right);
    }

    int getMax(TreeNode root) {
        if (root == null) {
            return Integer.MIN_VALUE;
        }

        int max = Math.max(root.val, getMax(root.left));
        return Math.max(max, getMax(root.right));
    }

    int getMin(TreeNode root) {
        if (root == null) {
            return Integer.MAX_VALUE;
        }

        int min = Math.min(root.val, getMin(root.left));
        return Math.min(min, getMin(root.right));
    }
}
