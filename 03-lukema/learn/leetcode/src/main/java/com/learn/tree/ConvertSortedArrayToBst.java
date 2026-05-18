package com.learn.tree;


import org.junit.jupiter.api.Assertions;

import lombok.extern.log4j.Log4j2;


/**
 * LC - 108 - Convert Sorted Array to Binary Search Tree
 * 
 * Given an integer array nums where the elements are sorted in ascending order, convert it to a height-balanced binary search tree.
 * A height-balanced binary tree is a binary tree in which the depth of the two subtrees of every node never differs by more than one.
 */
@Log4j2
public class ConvertSortedArrayToBst {

    public static void main(String[] args) {

        int[] nums = { -10, -3, 0, 5, 9 };

        ConvertSortedArrayToBst convertSortedArrayToBst = new ConvertSortedArrayToBst();

        TreeNode rootLuke = convertSortedArrayToBst.sortedArrayToBSTLuke(nums);
        log.debug("Sorted Array to BST: {}", () -> rootLuke);

        TreeNode rootLcPreorder = convertSortedArrayToBst.sortedArrayToBSTLcPreorder(nums);
        Assertions.assertEquals(TreeNode.inorderTraversalLukeRecursion(rootLuke), TreeNode.inorderTraversalLukeRecursion(rootLcPreorder));

        TreeNode rootLcPostorder = convertSortedArrayToBst.sortedArrayToBSTLcPostorder(nums);
        Assertions.assertEquals(TreeNode.inorderTraversalLukeRecursion(rootLuke), TreeNode.inorderTraversalLukeRecursion(rootLcPostorder));
    }

    /**
     * LC - Approach 1: Preorder Traversal: Always Choose Left Middle Node as a Root
     * 
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Convert Sorted Array to Binary Search Tree.
     * Memory Usage: 43.7 MB, less than 54.20% of Java online submissions for Convert Sorted Array to Binary Search Tree.
     * 
     * Time: O(n) - Since we visit each node exactly once.
     * Space: O(log(n)) - The recursion stack requires O(log(⁡N)) space because the tree is height-balanced. 
     *                    Note that the O(N) space used to store the output does not count as auxiliary space,
     *                    so it is not included in the space complexity. 
     */
    public TreeNode sortedArrayToBSTLcPreorder(int[] nums) {
        if (nums == null || nums.length == 0) {
            return null;
        }
        return sortedArrayToBstPreorder(nums, 0, nums.length - 1);
    }

    /**
     * LC - Approach 1: Preorder Traversal: Always Choose Left Middle Node as a Root
     */
    private TreeNode sortedArrayToBstPreorder(int[] nums, int start, int end) {
        if (start > end) {
            return null;
        }

        int mid = (start + end) / 2;

        TreeNode root = new TreeNode(nums[mid]);

        root.left = sortedArrayToBstPreorder(nums, start, mid - 1);
        root.right = sortedArrayToBstPreorder(nums, mid + 1, end);

        return root;
    }

    /**
     * LC - Approach 2: Postorder Traversal: Always Choose Right Middle Node as a Root
     * 
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Convert Sorted Array to Binary Search Tree.
     * Memory Usage: 43.9 MB, less than 38.14% of Java online submissions for Convert Sorted Array to Binary Search Tree.
     * 
     * Time: O(n) - Since we visit each node exactly once.
     * Space: O(log(n)) - The recursion stack requires O(log(⁡N)) space because the tree is height-balanced. 
     *                    Note that the O(N) space used to store the output does not count as auxiliary space,
     *                    so it is not included in the space complexity. 
     */
    public TreeNode sortedArrayToBSTLcPostorder(int[] nums) {
        if (nums == null || nums.length == 0) {
            return null;
        }
        return sortedArrayToBSTLcPostorder(nums, 0, nums.length - 1);
    }

    /**
     * LC - Approach 1: Preorder Traversal: Always Choose Right Middle Node as a Root
     */
    private TreeNode sortedArrayToBSTLcPostorder(int[] nums, int start, int end) {
        if (start > end) {
            return null;
        }

        int mid = (start + end) / 2;

        if ((start + end) % 2 == 1) {
            mid++;
        }

        TreeNode root = new TreeNode(nums[mid]);

        root.left = sortedArrayToBSTLcPostorder(nums, start, mid - 1);
        root.right = sortedArrayToBSTLcPostorder(nums, mid + 1, end);

        return root;
    }

    /**
     * Luke - Preorder Traversal: Always Choose Left Middle Node as a Root
     *        No "if (start > end) return null;"
     * 
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Convert Sorted Array to Binary Search Tree.
     * Memory Usage: 43.9 MB, less than 38.14% of Java online submissions for Convert Sorted Array to Binary Search Tree.
     * 
     * Time: O(n) - Since we visit each node exactly once.
     * Space: O(log(n)) - The recursion stack requires O(log(⁡N)) space because the tree is height-balanced. 
     *                    Note that the O(N) space used to store the output does not count as auxiliary space,
     *                    so it is not included in the space complexity. 
     */
    public TreeNode sortedArrayToBSTLuke(int[] nums) {
        if (nums == null || nums.length == 0) {
            return null;
        }
        return sortedArrayToBSTLuke(nums, 0, nums.length - 1);
    }

    private TreeNode sortedArrayToBSTLuke(int[] nums, int start, int end) {
        if (start == end) {
            return new TreeNode(nums[start]);
        }

        if (start == end - 1) {
            TreeNode root = new TreeNode(nums[end]);
            root.left = new TreeNode(nums[start]);
            return root;
        }

        if (start == end - 2) {
            TreeNode root = new TreeNode(nums[start + 1]);
            root.left = new TreeNode(nums[start]);
            root.right = new TreeNode(nums[end]);
            return root;
        }

        int mid = (start + end) / 2;

        TreeNode root = new TreeNode(nums[mid]);

        root.left = sortedArrayToBSTLuke(nums, start, mid - 1);
        root.right = sortedArrayToBSTLuke(nums, mid + 1, end);

        return root;
    }
}
