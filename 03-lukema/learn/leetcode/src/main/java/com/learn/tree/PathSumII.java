package com.learn.tree;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import lombok.extern.log4j.Log4j2;


/**
 * LC - 112 - Medium - Path Sum II
 * 
 * Given the root of a binary tree and an integer targetSum, return all root-to-leaf paths where the sum of the node values in the path
 * equals targetSum. Each path should be returned as a list of the node values, not node references. 
 * 
 * A root-to-leaf path is a path starting from the root and ending at any leaf node. A leaf is a node with no children.
 */
@Log4j2
public class PathSumII {

    public static void main(String[] args) {

        Integer[] nums = { 5, 4, 8, 11, null, 13, 4, 7, 2, null, null, 5, 1 };
        int targetSum = 22;

        TreeNode root = TreeNode.toTreeBfsWithNullIntegers(nums);

        PathSumII pathSumII = new PathSumII();

        List<List<Integer>> ret = pathSumII.pathSum(root, targetSum);
        log.debug("Path sum II: {}", () -> ret);
    }

    /**
     * Luke - DFS - Recursion
     * 
     * Runtime: 1 ms, faster than 100.00% of Java online submissions for Path Sum II.
     * Memory Usage: 42.4 MB, less than 96.41% of Java online submissions for Path Sum II.
     * 
     * Time: O(n)
     * Space: O(n)
     */
    public List<List<Integer>> pathSum(TreeNode root, int targetSum) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) {
            return result;
        }

        pathSum(root, targetSum, 0, result, new LinkedList<>());

        return result;
    }

    private void pathSum(TreeNode root, int targetSum, int sum, List<List<Integer>> result, LinkedList<Integer> nodeList) {
        if (root == null) {
            if (sum == targetSum) {
                result.add(List.copyOf(nodeList));
            }

            /**
             * Add a blank node to prevent "java.util.NoSuchElementException" with "nodeList.removeLast();"
             */
            nodeList.add(null);

            return;
        }

        sum += root.val;
        nodeList.add(root.val);

        if (root.left == null) {
            pathSum(root.right, targetSum, sum, result, nodeList);
            nodeList.removeLast();
        } else if (root.right == null) {
            pathSum(root.left, targetSum, sum, result, nodeList);
            nodeList.removeLast();
        } else {
            pathSum(root.right, targetSum, sum, result, nodeList);
            nodeList.removeLast();

            pathSum(root.left, targetSum, sum, result, nodeList);
            nodeList.removeLast();
        }
    }
}
