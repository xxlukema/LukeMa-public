package com.learn.tree;


import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import lombok.extern.log4j.Log4j2;


/**
 * LC - 225 - Invert Binary Tree
 *
 * Easy
 *
 * Given the root of a binary tree, invert the tree, and return its root.
 *
 * Example 1:
 * Input: root = [4,2,7,1,3,6,9]
 * Output: [4,7,2,9,6,3,1]
 *
 * Example 2:
 * Input: root = [2,1,3]
 * Output: [2,3,1]
 *
 * Example 3:
 * Input: root = []
 * Output: []
 *
 * Constraints:
 *     The number of nodes in the tree is in the range [0, 100].
 *     -100 <= Node.val <= 100
 */
@Log4j2
public class InvertBinaryTree {

    public static void main(String[] args) {

        final Integer[] nums = { 4, 2, 7, 1, 3, 6, 9 };
        final TreeNode root = TreeNode.toTreeBfsWithNullIntegers(nums);

        InvertBinaryTree invertBinaryTree = new InvertBinaryTree();

        var invertTreeLukeRecursion = invertBinaryTree.invertTreeLukeRecursion(root);
        log.debug("Invert Binary Tree: {}", () -> invertTreeLukeRecursion);
        log.debug("Invert Binary Tree {} OK", () -> "invertTreeLukeRecursion");

        var invertTreeLukeIterative = invertBinaryTree.invertTreeLukeIterative(root);
        log.debug("Invert Binary Tree: {}", () -> invertTreeLukeIterative);
        log.debug("Invert Binary Tree {} OK", () -> "invertTreeLukeIterative");

    }

    /**
     * Luke - Recursion
     *
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Invert Binary Tree.
     * Memory Usage: 41.2 MB, less than 79.27% of Java online submissions for Invert Binary Tree.
     *
     * Time: O(N)
     * Space: O(H) = Tree height (recursion statck size)
     */
    public TreeNode invertTreeLukeRecursion(TreeNode root) {
        if (root == null) {
            return root;
        }

        TreeNode tmp = root.right;
        root.right = root.left;
        root.left = tmp;

        invertTreeLukeRecursion(root.left);
        invertTreeLukeRecursion(root.right);

        return root;
    }

    /**
     * Luke - Iteration
     *
     * Runtime: 4 ms, faster than 10.25% of Java online submissions for Invert Binary Tree.
     * Memory Usage: 42.1 MB, less than 18.90% of Java online submissions for Invert Binary Tree.
     *
     * Time: O(N)
     * Space: O(N / 2) = Queue size.
     */
    public TreeNode invertTreeLukeIterative(TreeNode root) {
        if (root == null) {
            return root;
        }

        Queue<TreeNode> queue = new ConcurrentLinkedQueue<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            final int size = queue.size();
            for (int i = 0; i < size; i++) {
                TreeNode curr = queue.poll();
                TreeNode tmp = curr.left;
                curr.left = curr.right;
                curr.right = tmp;

                if (curr.left != null) {
                    queue.add(curr.left);
                }

                if (curr.right != null) {
                    queue.add(curr.right);
                }
            }
        }

        return root;
    }
}
