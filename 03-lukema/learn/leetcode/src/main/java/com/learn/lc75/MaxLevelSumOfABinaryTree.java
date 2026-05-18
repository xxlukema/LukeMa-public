package com.learn.lc75;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.junit.jupiter.api.Assertions;

import com.learn.tree.TreeNode;

import lombok.extern.log4j.Log4j2;


/**
 * LC - 1161. Maximum Level Sum of a Binary Tree
 *
 * Medium
 *
 * Given the root of a binary tree, the level of its root is 1, the level of its children is 2, and so on.

Return the smallest level x such that the sum of all the values of nodes at level x is maximal.

Example 1:

Input: root = [1,7,0,7,-8,null,null]
Output: 2
Explanation:
Level 1 sum = 1.
Level 2 sum = 7 + 0 = 7.
Level 3 sum = 7 + -8 = -1.
So we return the level with the maximum sum which is level 2.

Example 2:

Input: root = [989,null,10250,98693,-89388,null,null,null,-32127]
Output: 2

Constraints:

    The number of nodes in the tree is in the range [1, 10 ^ 4].
    -10 ^ 5 <= Node.val <= 10 ^ 5

 */
@Log4j2
public class MaxLevelSumOfABinaryTree {

    public static void main(String[] args) {

        MaxLevelSumOfABinaryTree maxLevelSumOfABinaryTree = new MaxLevelSumOfABinaryTree();

        // Integer[] root = { 1, 7, 0, 7, -8, null, null };
        // int expected = 2;

        Integer[] root = { 989, null, 10250, 98693, -89388, null, null, null, -32127 };
        int expected = 2;

        TreeNode head = TreeNode.toTreeBfsWithNullIntegers(root);

        var ret = maxLevelSumOfABinaryTree.maxLevelSum(head);
        log.debug("Maximum Level Sum of a Binary Tree: {}", () -> ret);
        Assertions.assertEquals(expected, ret);
        log.debug("Maximum Level Sum of a Binary Tree: {} OK", () -> "maxLevelSum");

    }

    /**
     * Time: O(n)
     * Space: O(height max(n))
     *
     * Runtime: 9ms Beats 55.59%
     * Memory: 46.50mb Beats 48.53
     */
    public int maxLevelSum(TreeNode root) {
        if (root == null) {
            return 0;
        }

        List<Integer> levelSums = new ArrayList<>();

        Queue<TreeNode> queue = new LinkedList<>();

        queue.add(root);

        while (!queue.isEmpty()) {
            int sum = 0;

            for (int i = 0, len = queue.size(); i < len; i++) {
                TreeNode item = queue.remove();
                sum += item.val;

                if (item.left != null) {
                    queue.add(item.left);
                }

                if (item.right != null) {
                    queue.add(item.right);
                }
            }

            levelSums.add(sum);
        }

        int max = levelSums.get(0);
        int level = 0;

        for (int i = 1, size = levelSums.size(); i < size; i++) {
            int curr = levelSums.get(i);
            if (curr > max) {
                max = curr;
                level = i;
            }
        }

        return level + 1;
    }
}
