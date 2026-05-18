package com.learn.tree;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

import org.junit.jupiter.api.Assertions;

import lombok.extern.log4j.Log4j2;


/**
 * LC - 124 - Binary Tree Maximum Path Sum
 *
 * Hard
 *
 * A path in a binary tree is a sequence of nodes where each pair of adjacent nodes in the sequence has an edge connecting them. A node can
 * only appear in the sequence at most once. Note that the path does not need to pass through the root.
 * The path sum of a path is the sum of the node's values in the path.
 * Given the root of a binary tree, return the maximum path sum of any non-empty path.
 *
 * Input: root = [1,2,3]
 * Output: 6
 * Explanation: The optimal path is 2 -> 1 -> 3 with a path sum of 2 + 1 + 3 = 6.
 *
 * Input: root = [-10,9,20,null,null,15,7]
 * Output: 42
 * Explanation: The optimal path is 15 -> 20 -> 7 with a path sum of 15 + 20 + 7 = 42.
 *
 * Constraints:
 *     The number of nodes in the tree is in the range [1, 3 * 104].
 *     -1000 <= Node.val <= 1000
 */
@Log4j2
public class BinaryTreeMaximumPathSum {

    public static void main(String[] args) {

        // Integer[] nums = { -10, 9, 20, null, null, 15, 7 };
        // Integer[] nums = { 2, -1 };
        Integer[] nums = { -1, 5, null, 4, null, null, 2, -4 };
        // Integer[] nums = { 1, 2, null, 3, null, 4, null, 5 };
        // Integer[] nums = { 1, 2 };
        // Integer[] nums = { -2, -1 };

        TreeNode root = TreeNode.toTreeBfsWithNullIntegers(nums);

        BinaryTreeMaximumPathSum binaryTreeMaximumPathSum = new BinaryTreeMaximumPathSum();

        var retLuke = binaryTreeMaximumPathSum.maxPathSumLukeRecursion(root);
        log.debug("Binary tree maximum path sum: {}", () -> retLuke);

        var retLukePriorityQueue = binaryTreeMaximumPathSum.maxPathSumLukeRecursionWithPriorityQueue(root);
        Assertions.assertEquals(retLuke, retLukePriorityQueue);

        var retLc = binaryTreeMaximumPathSum.maxPathSumLcRecursion(root);
        Assertions.assertEquals(retLuke, retLc);
    }

    /**
     * Luke - Recursion - Improved with PriorityQueue
     *
     * Better than using stateful member to hold the state as in LC sample. But LC sample has much faster performance.
     *
     * Runtime: 6 ms, faster than 5.23% of Java online submissions for Binary Tree Maximum Path Sum.
     * Memory Usage: 48.1 MB, less than 27.43% of Java online submissions for Binary Tree Maximum Path Sum.
     *
     * Time: O(N)
     * Space: O(H) - H: Tree Height
     */
    public int maxPathSumLukeRecursionWithPriorityQueue(TreeNode root) {
        if (root == null) {
            return -4_000;
        }

        /**
         * Assending ordered.
         */
        final PriorityQueue<Integer> noBobbleUpPaths = new PriorityQueue<>(1, (a, b) -> a.intValue() - b.intValue());

        maxPathSumLukeRecursionWithPriorityQueue(root, noBobbleUpPaths);

        return noBobbleUpPaths.poll().intValue();
    }

    private int maxPathSumLukeRecursionWithPriorityQueue(TreeNode root, PriorityQueue<Integer> noBobbleUpPaths) {
        if (root == null) {
            return -4_000;
        }

        if (root.left == null && root.right == null) {
            noBobbleUpPaths.add(root.val);
            return root.val;
        }

        int left = maxPathSumLukeRecursionWithPriorityQueue(root.left, noBobbleUpPaths);
        int right = maxPathSumLukeRecursionWithPriorityQueue(root.right, noBobbleUpPaths);

        int max = Math.max(Math.max(left, right), Math.max(0, left) + root.val + Math.max(0, right));

        noBobbleUpPaths.add(max);
        while (noBobbleUpPaths.size() > 1) {
            noBobbleUpPaths.poll();
        }

        return Math.max(0, Math.max(left, right)) + root.val;
    }

    /**
     * Luke - Recursion - Using "final List<Integer> noBobbleUpPaths = new ArrayList<>();" to hold noBobbleUpPaths.
     *
     * Improvement: Use PriorityQueue with the size of the queue to 1 or 2 and replace the element with the largest numbers only.
     * Then the space complexity will be O(H) - the height of the tree for the recursion stack size.
     *
     * Runtime: 19 ms, faster than 5.23% of Java online submissions for Binary Tree Maximum Path Sum.
     * Memory Usage: 51.8 MB, less than 5.07% of Java online submissions for Binary Tree Maximum Path Sum.
     *
     * Time: O(N)
     * Space: O(N)
     */
    public int maxPathSumLukeRecursion(TreeNode root) {
        if (root == null) {
            return -4_000;
        }

        final List<Integer> noBobbleUpPaths = new ArrayList<>();

        maxPathSumLukeRecursion(root, noBobbleUpPaths);

        Collections.sort(noBobbleUpPaths);

        return noBobbleUpPaths.get(noBobbleUpPaths.size() - 1);
    }

    private int maxPathSumLukeRecursion(TreeNode root, List<Integer> noBobbleUpPaths) {
        if (root == null) {
            return -4_000;
        }

        if (root.left == null && root.right == null) {
            noBobbleUpPaths.add(root.val);
            return root.val;
        }

        int left = maxPathSumLukeRecursion(root.left, noBobbleUpPaths);
        int right = maxPathSumLukeRecursion(root.right, noBobbleUpPaths);

        int max = Collections.max(Arrays.asList(left, right, root.val, left + root.val, left + root.val + right, root.val + right));
        noBobbleUpPaths.add(max);

        return Math.max(Math.max(left, right) + root.val, root.val);
    }

    /**
     * LC - Recursion - Making the class stateful to avoid use of "final List<Integer> noBobbleUpPaths = new ArrayList<>();"
     *
     * Runtime: 1 ms, faster than 99.65% of Java online submissions for Binary Tree Maximum Path Sum.
     * Memory Usage: 47.9 MB, less than 39.84% of Java online submissions for Binary Tree Maximum Path Sum.
     *
     * Time: O(N)
     * Space: O(H) - Stack size equals to tree height.
     */
    public int maxPathSumLcRecursion(TreeNode root) {
        maxGainLcRecursion(root);
        return maxSum;
    }

    int maxSum = Integer.MIN_VALUE;

    public int maxGainLcRecursion(TreeNode node) {
        if (node == null) {
            return 0;
        }

        // max sum on the left and right sub-trees of node
        int left = Math.max(maxGainLcRecursion(node.left), 0);
        int right = Math.max(maxGainLcRecursion(node.right), 0);

        // the price to start a new path where `node` is a highest node
        int newGain = node.val + left + right;

        // update max_sum if it's better to start a new path
        maxSum = Math.max(maxSum, newGain);

        // for recursion :
        // return the max gain if continue the same path
        return node.val + Math.max(left, right);
    }

}
