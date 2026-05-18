package com.learn.tree;


import lombok.extern.log4j.Log4j2;


/**
 * LC - 101 - Symetric Tree
 */
@Log4j2
public class SymetricTree {

    public static void main(String[] args) {

        Integer[] nums = { 1, 2, 2, 3, 4, 4, 3 };

        TreeNode root = TreeNode.toTreeHeapSortSubrootNoNull(nums);

        SymetricTree symetricTree = new SymetricTree();

        boolean isSymetricTree = symetricTree.isSymmetric(root);
        log.debug("Is symetric tree: {}", () -> isSymetricTree);

    }

    /**
     * Luke - Recursion
     * 
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Symmetric Tree.
     * Memory Usage: 40.7 MB, less than 87.73% of Java online submissions for Symmetric Tree.
     * 
     * Time: O(n)
     * Space: O(1)
     */
    public boolean isSymmetric(TreeNode root) {
        if (root == null || (root.left == null && root.right == null)) {
            return true;
        }

        if (root.left != null && root.right != null && root.left.val == root.right.val) {
            return isSymmetric(root.left, root.right);
        }

        return false;
    }

    private boolean isSymmetric(TreeNode root1, TreeNode root2) {
        if (root1 == null && root2 == null) {
            return true;
        }

        if (root1 != null && root2 != null && root1.val == root2.val) {
            return isSymmetric(root1.left, root2.right) && isSymmetric(root1.right, root2.left);
        }

        return false;
    }
}
