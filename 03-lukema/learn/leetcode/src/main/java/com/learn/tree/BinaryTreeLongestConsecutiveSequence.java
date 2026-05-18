package com.learn.tree;


import java.util.concurrent.atomic.AtomicInteger;

import lombok.extern.log4j.Log4j2;


/**
 * LC - 298 - Binary Tree Longest Consecutive Sequence
 *
 * Medium
 *
 * Given the root of a binary tree, return the length of the longest consecutive sequence path.
 *
 * A consecutive sequence path is a path where the values increase by one along the path.
 *
 * Note that the path can start at any node in the tree, and you cannot go from a node to its parent in the path.
 *
 * Example 1:
 * Input: root = [1,null,3,2,4,null,null,null,5]
 * Output: 3
 * Explanation: Longest consecutive sequence path is 3-4-5, so return 3.
 *
 * Example 2:
 * Input: root = [2,null,3,2,null,1]
 * Output: 2
 * Explanation: Longest consecutive sequence path is 2-3, not 3-2-1, so return 2.
 *
 * Constraints:
 *     The number of nodes in the tree is in the range [1, 3 * 104].
 *     -3 * 104 <= Node.val <= 3 * 104
 */
@Log4j2
public class BinaryTreeLongestConsecutiveSequence {
    public static void main(String[] args) {

        /**
         * Expected: 3
         */
        // final Integer[] nums = { 1, null, 3, 2, 4, null, null, null, 5 };

        /**
         * Expected: 2
         */
        final Integer[] nums = { 2, null, 3, 2, null, 1 };

        final TreeNode root = TreeNode.toTreeBfsWithNullIntegers(nums);

        BinaryTreeLongestConsecutiveSequence binaryTreeLongestConsecuativeSequence = new BinaryTreeLongestConsecutiveSequence();
        var ret = binaryTreeLongestConsecuativeSequence.longestConsecutive(root);
        log.debug("Binary Tree Longest Consecutive Sequence: {}", () -> ret);
        log.debug("Binary Tree Longest Consecutive Sequence {} OK", () -> "ret");

    }

    /**
     * Luke - DFS - Stateless
     *
     * Runtime: 2 ms Beats 58.7%
     * Memory: 51.3 MB Beats 24.87%
     *
     * Time: O(N): iterate every node
     * Space: O(N): stack depth
     */
    public int longestConsecutive(TreeNode root) {
        if (root == null) {
            return 0;
        }

        final AtomicInteger ai = new AtomicInteger();

        longestConsecutive(root, 1, ai);

        return ai.get();
    }

    private void longestConsecutive(final TreeNode root, final int level, final AtomicInteger ai) {
        if (root == null) {
            return;
        }

        if (level > ai.get()) {
            ai.set(level);
        }

        if (root.left != null) {
            if (root.left.val == root.val + 1) {
                longestConsecutive(root.left, level + 1, ai);
            } else {
                longestConsecutive(root.left, 1, ai);
            }
        }

        if (root.right != null) {
            if (root.right.val == root.val + 1) {
                longestConsecutive(root.right, level + 1, ai);
            } else {
                longestConsecutive(root.right, 1, ai);
            }
        }
    }
}
