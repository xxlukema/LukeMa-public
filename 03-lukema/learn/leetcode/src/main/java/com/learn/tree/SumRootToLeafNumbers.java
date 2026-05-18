package com.learn.tree;


import org.junit.jupiter.api.Assertions;

import lombok.extern.log4j.Log4j2;


/**
 * LC - 129 - Sum Roof To Leaf Numbers
 *
 * Medium
 *
 * You are given the root of a binary tree containing digits from 0 to 9 only.
 * Each root-to-leaf path in the tree represents a number.
 *     For example, the root-to-leaf path 1 -> 2 -> 3 represents the number 123.
 * Return the total sum of all root-to-leaf numbers. Test cases are generated so that the answer will fit in a 32-bit integer.
 *
 * A leaf node is a node with no children.
 *
 * Example 1:
 * Input: root = [1,2,3]
 * Output: 25
 * Explanation:
 * The root-to-leaf path 1->2 represents the number 12.
 * The root-to-leaf path 1->3 represents the number 13.
 * Therefore, sum = 12 + 13 = 25.
 *
 * Example 2:
 * Input: root = [4,9,0,5,1]
 * Output: 1026
 * Explanation:
 * The root-to-leaf path 4->9->5 represents the number 495.
 * The root-to-leaf path 4->9->1 represents the number 491.
 * The root-to-leaf path 4->0 represents the number 40.
 * Therefore, sum = 495 + 491 + 40 = 1026.
 *
 * Constraints:
 *     The number of nodes in the tree is in the range [1, 1000].
 *     0 <= Node.val <= 9
 *     The depth of the tree will not exceed 10
 *
 */
@Log4j2
public class SumRootToLeafNumbers {

    public static void main(String[] args) {

        // final Integer[] nums = { 4, 9, 0, 5, 1 };
        final Integer[] nums = { 4, 9, 0, 5, 1, 0, 0, 6 };
        // final Integer[] nums = { 4, 9 };

        final TreeNode root = TreeNode.toTreeBfsWithNullIntegers(nums);

        SumRootToLeafNumbers sumRootToLeafNumbers = new SumRootToLeafNumbers();

        var retLukeStateful = sumRootToLeafNumbers.sumNumbersStatefulTopDown(root);
        log.debug("Sum root to leaf numbers Luke stateful Top-Down: {}", () -> retLukeStateful);

        log.debug(() -> "Luke stateful Top-Down OK.");

        var retLukeStateLessTopDown = sumRootToLeafNumbers.sumNumbersStatelessTopDown(root);
        Assertions.assertEquals(retLukeStateful, retLukeStateLessTopDown);

        log.debug(() -> "Luke stateless Top-Down OK.");

    }

    /**
     * Luke - Stateful Recursion Top-Down
     *
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Sum Root to Leaf Numbers.
     * Memory Usage: 42.4 MB, less than 12.27% of Java online submissions for Sum Root to Leaf Numbers.
     *
     * Time: O(N)
     * Space: O(H) - Height of tree.
     */

    int stateTotal = 0;

    public int sumNumbersStatefulTopDown(TreeNode root) {
        if (root == null) {
            return stateTotal;
        }

        sumNumbersStatefulTopDown(root, 0);

        return stateTotal;
    }

    private void sumNumbersStatefulTopDown(TreeNode root, int pathSum) {
        if (root == null) {
            return;
        }

        pathSum = pathSum * 10 + root.val;

        if (root.left == null && root.right == null) {
            stateTotal += pathSum;
        }

        sumNumbersStatefulTopDown(root.left, pathSum);
        sumNumbersStatefulTopDown(root.right, pathSum);
    }

    /**
     * Luke - Stateless Recursion Top-Down
     *
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Sum Root to Leaf Numbers.
     * Memory Usage: 41.3 MB, less than 74.72% of Java online submissions for Sum Root to Leaf Numbers.
     *
     * Time: O(N)
     * Space: O(Height)
     */
    public int sumNumbersStatelessTopDown(TreeNode root) {
        if (root == null) {
            return 0;
        }

        return sumNumbersStatelessTopDown(root, 0);
    }

    private int sumNumbersStatelessTopDown(TreeNode root, int pathSum) {
        if (root == null) {
            return 0;
        }

        pathSum = pathSum * 10 + root.val;

        // log.debug("vvvvvvv val: {}, pathSum: {}", root.val, pathSum);

        /**
         * This step (test leaf) is neccessary for Top-Down:
         */
        if (root.left == null && root.right == null) {
            return pathSum;
        } else {
            int left = sumNumbersStatelessTopDown(root.left, pathSum);
            int right = sumNumbersStatelessTopDown(root.right, pathSum);

            // log.debug("^^^^^^^^^^^^^^^ val: {}, left: {}, right: {}, sum: {}", root.val, left, right, left + right);

            return left + right;
        }
    }

    /**
     * Luke - Stateless Recursion Bottom-Up
     *
     * Bottom-Up in this situation is not easy because it is needed to track the tree level from Bottom-Up
     * to calculate how many 10s is needed for calculation of "root.val * 10 ^ levalFromLeaf"
     */
}
