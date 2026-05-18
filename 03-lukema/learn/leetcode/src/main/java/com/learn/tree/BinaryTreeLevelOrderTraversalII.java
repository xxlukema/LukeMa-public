package com.learn.tree;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.junit.jupiter.api.Assertions;

import lombok.extern.log4j.Log4j2;


/**
 * LC - 107 - Binary Tree Level Traversal II
 * 
 * Given the root of a binary tree, return the bottom-up level order traversal of its nodes' values. (i.e., from left to right, level by level from leaf to root).
 */
@Log4j2
public class BinaryTreeLevelOrderTraversalII {

    public static void main(String[] args) {

        Integer[] nums = { 3, 9, 20, null, null, 15, 7 };
        // Integer[] nums = { 1, null, 2 };

        TreeNode root = TreeNode.toTreeBfsWithNullIntegers(nums);

        log.debug("root: {}", () -> root);

        BinaryTreeLevelOrderTraversalII binaryTreeLevelOrderTraversalII = new BinaryTreeLevelOrderTraversalII();
        List<List<Integer>> retRecusrion = binaryTreeLevelOrderTraversalII.levelOrderBottomUpRecursionNotRealBottomUp(root);
        log.debug("Binary Tree Level Traversal II: {}", () -> retRecusrion);

        List<List<Integer>> retBfsIterative = binaryTreeLevelOrderTraversalII.levelOrderBottomUpBfsIterative(root);
        Assertions.assertEquals(retRecusrion, retBfsIterative);
    }

    /**
     * Luke - Iterative - BFS
     * 
     * Runtime: 16 ms, faster than 15.81% of Java online submissions for Binary Tree Level Order Traversal II.
     * Memory Usage: 44 MB, less than 17.72% of Java online submissions for Binary Tree Level Order Traversal II.
     * 
     * Time: O(n)
     * Space: O(n)
     */
    public List<List<Integer>> levelOrderBottomUpBfsIterative(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) {
            return result;
        }

        Queue<TreeNode> queue = new ConcurrentLinkedQueue<>();
        List<List<TreeNode>> levelNodeList = new ArrayList<>();

        queue.add(root);

        int level = 0;
        while (!queue.isEmpty()) {
            int queueSize = queue.size();
            for (int i = 0; i < queueSize; i++) {
                /**
                 * Going down. Add an empty list to keep track of the tree level.
                 * Only the first non-null node of the level adds an empty list to result. For example, the left non-null node.
                 * For the second non-null level node, "level < result.size()" because the first non-null level node
                 * already added an empty ArrayList to the result.
                 */
                if (level == levelNodeList.size()) {
                    /**
                     * This is the first non-null TreeNode. Use list index to track tree level - faster than using Map to track tree level.
                     */
                    levelNodeList.add(new ArrayList<>());
                }

                TreeNode curr = queue.poll();
                levelNodeList.get(level).add(curr);
                if (curr.left != null) {
                    queue.add(curr.left);
                }
                if (curr.right != null) {
                    queue.add(curr.right);
                }
            }

            level++;
        }

        levelNodeList.forEach(levelNodes -> {
            result.add(0, levelNodes.stream().map(e -> Integer.valueOf(e.val)).toList());
        });

        return result;
    }

    /**
     * Luke - Recusrion. This is not real bottom up traversal. It just print in bottom up way.
     * 
     * Runtime: 7 ms, faster than 15.81% of Java online submissions for Binary Tree Level Order Traversal II.
     * Memory Usage: 44.3 MB, less than 7.18% of Java online submissions for Binary Tree Level Order Traversal II.
     * 
     * Time: O(n)
     * Space: O(n)
     */
    public List<List<Integer>> levelOrderBottomUpRecursionNotRealBottomUp(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) {
            return result;
        }

        List<List<TreeNode>> levelNodes = new ArrayList<>();
        dfsTraversal(root, 0, levelNodes);

        Collections.reverse(levelNodes);

        levelNodes.stream().forEach(level -> {
            result.add(level.stream().map(e -> Integer.valueOf(e.val)).toList());
        });

        return result;
    }

    /**
     * Using list index to track tree level is faster than using Map.
     */
    private void dfsTraversal(TreeNode root, int level, List<List<TreeNode>> levelNodes) {
        if (root == null) {
            return;
        }

        if (level == levelNodes.size()) {
            /**
             * Going down. Add an empty list to keep track of the tree level.
             * Only the first non-null node of the level adds an empty list to result. For example, the left non-null node.
             * For the second non-null level node, "level < result.size()" because the first non-null level node
             * already added an empty ArrayList to the result.
             */
            levelNodes.add(new ArrayList<>());
        }

        dfsTraversal(root.left, level + 1, levelNodes);
        dfsTraversal(root.right, level + 1, levelNodes);

        /**
         * Bobbling up. The following statement will give wrong result because shallower tree branch bobbles
         * earlier prior to get the full depth of the tree, or the max of "result.sze()".
         */
        // result.get((result.size() - 1) - level).add(root.val);

        /**
         * Therefore, it is mandatory to save the tree levels nodes into anther list.
         */
        levelNodes.get(level).add(root);
    }
}
