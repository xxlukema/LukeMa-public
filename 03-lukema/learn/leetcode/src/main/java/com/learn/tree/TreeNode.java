package com.learn.tree;


import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.ConcurrentLinkedQueue;

import lombok.ToString;


/**
 * Binary Search Tree (BST) Traversals:
 *
 * https://www.youtube.com/watch?v=BHB0B1jFKQc
 *
 * https://www.youtube.com/watch?v=Zq4upTEaQyM&list=RDCMUCmJz2DV1a3yfgrR7GqRtUUA
 *
 * 1. Inorder: l-n-r: node (n) is "in". l: left. r: right. n: node
 * 2. Preorder: n-l-r: node (n) is "pre-"
 * 3. Postorder: l-r-n: node (n) is "-post"
 *
 * Binary Search Tree (RST) is:
 *
 * 1. Full: If a node has leaves, it must have both left and right leaves. A node either has no children, or it must have both children.
 * 2. Complete: Nodes are filled from left to right, no skips.
 * 3. Perfect: Perfect triangle.
 */
@ToString
public class TreeNode {
    public Integer val;
    public TreeNode left;
    public TreeNode right;

    public TreeNode(int val) {
        this.val = val;
    }

    /**
     * LC - Recursion together with leftMax and rightMin
     *
     * Time: O(n)
     * Space: O(1)
     */
    public static boolean validateBst(TreeNode root) {
        return validateBst(root, null, null);
    }

    private static boolean validateBst(TreeNode root, TreeNode leftRoot, TreeNode rightRoot) {
        if (root == null || (root.left == null && root.right == null)) {
            return true;
        }

        if (root.left != null && root.left.val >= root.val) {
            return false;
        }

        if (root.right != null && root.right.val <= root.val) {
            return false;
        }

        if (leftRoot != null && root.right != null && root.right.val >= leftRoot.val) {
            return false;
        }

        if (rightRoot != null && root.left != null && root.left.val <= rightRoot.val) {
            return false;
        }

        if (root.left != null) {
            if (!validateBst(root.left, root, null)) {
                return false;
            }
        }

        if (root.right != null) {
            if (!validateBst(root.right, null, root)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Luke - preorderTraversal Recursion
     *
     * Time: O(n)
     * Space: O(1)
     */
    public static List<Integer> preorderTraversalLukeRecursion(TreeNode root) {
        List<Integer> list = new ArrayList<>();

        preorderTraversalLuke(root, list);

        return list;
    }

    private static void preorderTraversalLuke(TreeNode root, List<Integer> list) {
        if (root != null) {
            list.add(root.val);
            preorderTraversalLuke(root.left, list);
            preorderTraversalLuke(root.right, list);
        }
    }

    /**
     * LC - preorderTraversal Stack
     * <p>
     * Time: O(n)
     * Space: O(n)
     */
    public static List<Integer> preorderTraversalLcStack(TreeNode root) {
        final List<Integer> list = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        TreeNode curr = root;

        while (curr != null || !stack.isEmpty()) {
            while (curr != null) {
                list.add(curr.val);
                stack.push(curr);
                curr = curr.left;
            }

            curr = stack.pop();
            curr = curr.right;
        }

        return list;
    }

    /**
     * Luke - inorderTraversal Recursion
     * <p>
     * Time: O(n)
     * Space: O(1)
     */
    public static List<Integer> inorderTraversalLukeRecursion(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        inorderTraversalLuke(root, list);
        return list;
    }

    private static void inorderTraversalLuke(TreeNode root, List<Integer> list) {
        if (root == null) {
            return;
        }

        inorderTraversalLuke(root.left, list);
        list.add(root.val);
        inorderTraversalLuke(root.right, list);
    }

    /**
     * LC - inorderTraversal Stack
     * <p>
     * Time: O(n)
     * Space: O(n)
     */
    public static List<Integer> inorderTraversalLcStack(TreeNode root) {
        final List<Integer> list = new ArrayList<>();
        final Stack<TreeNode> stack = new Stack<>();

        while (root != null || !stack.isEmpty()) {
            while (root != null) {
                stack.push(root);
                root = root.left;
            }

            root = stack.pop();
            list.add(root.val);

            root = root.right;
        }

        return list;
    }

    public static TreeNode toTreeBfsWithNullIntegers(final Integer[] nums) {
        if (nums == null || nums[0] == null) {
            return null;
        }

        TreeNode root = new TreeNode(nums[0]);
        Queue<TreeNode> queue = new ConcurrentLinkedQueue<>();
        queue.add(root);

        int idx = 1;
        while (idx < nums.length) {
            TreeNode curr = queue.poll();

            if (nums[idx] != null) {
                TreeNode node = new TreeNode(nums[idx]);
                curr.left = node;
                queue.add(curr.left);
            }

            idx++;

            if (idx < nums.length && nums[idx] != null) {
                TreeNode node = new TreeNode(nums[idx]);
                curr.right = node;
                queue.add(curr.right);
            }

            idx++;
        }

        return root;
    }

    public static TreeNode toTreeHeapSortSubrootNoNull(final Integer[] nums) {
        if (nums == null || nums.length == 0) {
            return null;
        }

        TreeNode[] nodes = new TreeNode[nums.length];

        for (int i = 0; i <= nums.length / 2; i++) {
            if (nums[i] != null) {
                int left = 2 * i + 1;
                int right = 2 * i + 2;

                if (nodes[i] == null) {
                    nodes[i] = new TreeNode(nums[i]);
                }

                if (left < nums.length && nums[left] != null) {
                    nodes[left] = new TreeNode(nums[left]);
                    nodes[i].left = nodes[left];
                }

                if (right < nums.length && nums[right] != null) {
                    nodes[right] = new TreeNode(nums[right]);
                    nodes[i].right = nodes[right];
                }
            }
        }

        return nodes[0];
    }

    public static TreeNode copyOf(TreeNode root) {
        if (root == null) {
            return null;
        }

        TreeNode root2 = new TreeNode(root.val);

        if (root.left != null) {
            root2.left = copyOf(root.left);
        }

        if (root.right != null) {
            root2.right = copyOf(root.right);
        }

        return root2;
    }
}
