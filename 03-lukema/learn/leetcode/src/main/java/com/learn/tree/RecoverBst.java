package com.learn.tree;


import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import lombok.extern.log4j.Log4j2;


/**
 * LC - 99
 */
@Log4j2
public class RecoverBst {

    public static void main(String[] args) {

        // Integer[] nums = { 1, 3, null, null, 2 };
        // Integer[] nums = { 3, 1, 4, null, null, 2 };
        // Integer[] nums = { 2, null, 1, null, 3 };
        // Integer[] nums = { 146, 71, -13, 55, null, 231, 399, 321, null, null, null, null, null, -33 };
        // Integer[] nums = { 4, 2, null, 1, null, null, 3 };
        // Integer[] nums = { 10, 5, 15, 0, 8, 13, 20, 2, -5, 6, 9, 12, 14, 18, 25 };
        Integer[] nums = { 3, 1, null, null, 2, null, 4 };

        TreeNode root = TreeNode.toTreeBfsWithNullIntegers(nums);

        log.debug("root: {}", () -> root);

        RecoverBst recoverBst = new RecoverBst();
        recoverBst.recoverTree(root);

        log.debug("Recover BST: {}", () -> root);
    }

    /**
     * LC - Inorder traversal
     *
     * Runtime: 4 ms, faster than 45.29% of Java online submissions for Recover Binary Search Tree.
     * Memory Usage: 48.1 MB, less than 9.27% of Java online submissions for Recover Binary Search Tree.
     *
     * Time: O(n)
     * Space: O(n)
     */
    public void recoverTree(TreeNode root) {
        if (root == null) {
            return;
        }

        List<TreeNode> list = new ArrayList<>();
        inorderTraversalRecursion(root, list);

        int left = 0;
        while (list.get(left).val < list.get(left + 1).val) {
            left++;
        }

        int right = list.size() - 1;
        while (list.get(right - 1).val < list.get(right).val) {
            right--;
        }

        int tmp = list.get(right).val;
        list.get(right).val = list.get(left).val;
        list.get(left).val = tmp;
    }

    /**
     * Inorder traversal - Recursion
     */
    public void inorderTraversalRecursion(TreeNode root, List<TreeNode> list) {
        if (root == null) {
            return;
        }

        inorderTraversalRecursion(root.left, list);
        list.add(root);
        inorderTraversalRecursion(root.right, list);
    }

    /**
     * Inorder traversal - Stack
     */
    public void inorderTraversalStack(TreeNode root, List<TreeNode> list) {
        if (root == null) {
            return;
        }

        Stack<TreeNode> stack = new Stack<>();
        while (root != null || !stack.isEmpty()) {
            while (root != null) {
                stack.push(root);
                root = root.left;
            }
            root = stack.pop();
            list.add(root);
            root = root.right;
        }
    }

    public void recoverTreeWrong(TreeNode root) {
        if (root == null) {
            return;
        }

        boolean updated = recoverTree(root, null, null) ||
                recoverTree(root.left, root, null) ||
                recoverTree(root.right, null, root);

        while (updated) {
            recoverTree(root);

            updated = recoverTree(root, null, null) ||
                    recoverTree(root.left, root, null) ||
                    recoverTree(root.right, null, root);
        }
    }

    /**
     * If traverse left subtree, rootForRight is set to null.
     * If traverse right subtree, rootForLeft is set to null.
     * All left side elements are smaller than roo.
     * All right side elements are greater then root.
     */
    private boolean recoverTree(TreeNode subroot, TreeNode rootForLeft, TreeNode rootForRight) {
        if (subroot == null) {
            return false;
        }

        boolean updated = false;

        if (subroot.left != null && subroot.right != null) {
            if (subroot.left.val > subroot.right.val) {
                int tmp = subroot.left.val;
                subroot.left.val = subroot.right.val;
                subroot.right.val = tmp;
                updated = true;
            }
        }

        if (subroot.left != null && subroot.left.val > subroot.val) {
            int tmp = subroot.left.val;
            subroot.left.val = subroot.val;
            subroot.val = tmp;
            updated = true;
        }

        if (subroot.right != null && subroot.right.val < subroot.val) {
            int tmp = subroot.right.val;
            subroot.right.val = subroot.val;
            subroot.val = tmp;
            updated = true;
        }

        /**
         * Traversing left side because "rootForLeft != null"
         */
        if (rootForLeft != null && rootForLeft != subroot) {
            /**
             * All elements at left side of root must less than root.
             */
            if (subroot.right != null && subroot.right.val > rootForLeft.val) {
                int tmp = subroot.right.val;
                subroot.right.val = rootForLeft.val;
                rootForLeft.val = tmp;
                updated = true;
            }
            /**
             * Every element at right of subroot must be greater than subroot.
             */

        }

        /**
         * Traversing right side because "rootForRight != null"
         */
        if (rootForRight != null && rootForRight != subroot) {
            if (subroot.left != null && subroot.left.val < rootForRight.val) {
                int tmp = subroot.left.val;
                subroot.left.val = rootForRight.val;
                rootForRight.val = tmp;
                updated = true;
            }
        }

        if (rootForLeft != null) {
            /**
             * left of subroot must all be less than subroot.
             */
            recoverTree(subroot.left, subroot, null);
            /**
             * right of subroot must be all greater than subroot.
             */
            recoverTree(subroot.left, subroot, null);
            /**
             * right of subroot must be all at left of root.
             */
            recoverTree(subroot.right, rootForLeft, null);
        }

        if (rootForRight != null) {
            /**
             * left side of subroot must be all at the right side of root.
             */
            recoverTree(subroot.left, null, rootForRight);
            /**
             * left side of subroot must all be smaller than subroot.
             */
            recoverTree(subroot.left, null, subroot);
            /**
             * right side of subroot must all be greater than subroot.
             */
            recoverTree(subroot.right, null, subroot);
        }

        return updated;
    }
}
