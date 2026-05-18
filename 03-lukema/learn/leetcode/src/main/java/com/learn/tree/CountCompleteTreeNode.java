package com.learn.tree;


import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import lombok.extern.log4j.Log4j2;


/**
 * LC - 222 - Count Complete TreeNode
 *
 * Medium
 *
 * Given the root of a complete binary tree, return the number of the nodes in the tree.
 *
 * According to Wikipedia, every level, except possibly the last, is completely filled in a complete binary tree, and all nodes in the last level
 * are as far left as possible. It can have between 1 and 2h nodes inclusive at the last level h.
 *
 * Design an algorithm that runs in less than O(n) time complexity.
 *
 * Example 1:
 * Input: root = [1,2,3,4,5,6]
 * Output: 6
 *
 * Example 2:
 * Input: root = []
 * Output: 0
 *
 * Example 3:
 * Input: root = [1]
 * Output: 1
 *
 * Constraints:
 *     The number of nodes in the tree is in the range [0, 5 * 104].
 *     0 <= Node.val <= 5 * 104
 *     The tree is guaranteed to be complete.
 */
@Log4j2
public class CountCompleteTreeNode {

    public static void main(String[] args) {

        final Integer[] nums = { 1, 2, 3, 4, 5, 6 };

        TreeNode root = TreeNode.toTreeHeapSortSubrootNoNull(nums);

        CountCompleteTreeNode countCompleteTreeNode = new CountCompleteTreeNode();

        var ret = countCompleteTreeNode.countNodes(root);
        log.debug("Count Complete TreeNode: {}", () -> ret);
        log.debug("Count Complete TreeNode {} OK", () -> "ret");

    }

    /**
     * Luke - Recursion - Atomics
     *
     * 50 minutes
     *
     * Runtime: 3 ms, faster than 10.69% of Java online submissions for Count Complete Tree Nodes.
     * Memory Usage: 49.7 MB, less than 67.08% of Java online submissions for Count Complete Tree Nodes.
     *
     * Time: O(H) + O(bottom leaves) = O(log(N)) + O(bottom leaves). H is the tree height.
     * Space: O(H)
     */
    public int countNodes(TreeNode root) {

        if (root == null) {
            return 0;
        }

        final AtomicInteger height = new AtomicInteger();
        final AtomicInteger count = new AtomicInteger();
        final AtomicBoolean found = new AtomicBoolean();

        doCount(root, height, 1, count, found);

        int total = count.get();
        for (int i = 1, n = height.get(); i < n; i++) {
            total += Math.pow(2, i - 1);
        }

        log.debug("count: {}, height: {}", count.get(), height.get());

        return total;
    }

    private void doCount(
            final TreeNode root,
            final AtomicInteger height,
            final int level,
            final AtomicInteger count,
            final AtomicBoolean found) {

        /** Start */
        if (found.get()) {
            return;
        }

        if (root.left == null) {
            if (height.get() < level) {
                height.set(level);
                count.incrementAndGet();
            } else if (height.get() == level) {
                count.incrementAndGet();
            } else {
                found.set(true);
            }
        } else {
            doCount(root.left, height, level + 1, count, found);
        }

        if (root.right != null) {
            doCount(root.right, height, level + 1, count, found);
        }
    }

    // Return tree depth in O(d) time.
    public int computeDepth(TreeNode node) {
        int d = 0;
        while (node.left != null) {
            node = node.left;
            ++d;
        }
        return d;
    }
}
