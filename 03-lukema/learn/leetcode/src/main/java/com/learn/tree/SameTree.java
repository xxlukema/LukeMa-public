package com.learn.tree;


import lombok.extern.log4j.Log4j2;


/**
 * LC - 100 - Same Tree
 */
@Log4j2
public class SameTree {

    public static void main(String[] args) {

        Integer[] nums1 = { 1, 2, 3 };
        Integer[] nums2 = { 1, 2, 3 };

        TreeNode p = TreeNode.toTreeHeapSortSubrootNoNull(nums1);
        TreeNode q = TreeNode.toTreeHeapSortSubrootNoNull(nums2);

        log.debug("tree: {}", () -> p);

        SameTree sameTree = new SameTree();

        boolean isSameTree = sameTree.isSameTree(p, q);
        log.debug("Is same tree: {}", () -> isSameTree);

    }

    /**
     * Luke - Recursion
     * 
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Same Tree.
     * Memory Usage: 41.9 MB, less than 26.74% of Java online submissions for Same Tree.
     * 
     * Time: O(m), where m = Math.min(p.size(), q.size())
     * Space: O(1)
     */
    public boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == null && q == null) {
            return true;
        }

        if (p != null && q != null && p.val == q.val) {
            return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
        }

        return false;
    }
}
