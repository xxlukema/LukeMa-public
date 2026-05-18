package com.learn.tree;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.junit.jupiter.api.Assertions;

import lombok.extern.log4j.Log4j2;


/**
 * LC - 103 - Binary Tree Level Traversal
 */
@Log4j2
public class BinaryTreeLevelOrderTraversal {

    public static void main(String[] args) {

        Integer[] nums = { 3, 9, 20, null, null, 15, 7 };

        TreeNode root = TreeNode.toTreeBfsWithNullIntegers(nums);

        BinaryTreeLevelOrderTraversal binaryTreeLevelOrderTraversal = new BinaryTreeLevelOrderTraversal();

        List<List<Integer>> retLuke = binaryTreeLevelOrderTraversal.levelOrderLukeRecursion(root);
        log.debug("Binary Tree Level Traversal: {}", () -> retLuke);

        List<List<Integer>> retLcRecursionNoMap = binaryTreeLevelOrderTraversal.levelOrderLcRecursionNpMap(root);
        Assertions.assertEquals(retLuke, retLcRecursionNoMap);

        List<List<Integer>> retLcIterative = binaryTreeLevelOrderTraversal.levelOrderLcIterativeBfsWithQueueAndChildNodeList(root);
        Assertions.assertEquals(retLuke, retLcIterative);

        List<List<Integer>> retLcIterativeNoList = binaryTreeLevelOrderTraversal.levelOrderLcIterativeBfsNoChildNodeList(root);
        Assertions.assertEquals(retLuke, retLcIterativeNoList);
    }

    /**
     * LC - Iterative - With Queue and childNodes list
     * <p>
     * Runtime: 8 ms, faster than 8.82% of Java online submissions for Binary Tree Level Order Traversal.
     * Memory Usage: 43.9 MB, less than 17.45% of Java online submissions for Binary Tree Level Order Traversal.
     * <p>
     * Time: O(N)
     * Space: O(N)
     */
    public List<List<Integer>> levelOrderLcIterativeBfsNoChildNodeList(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) {
            return result;
        }

        final Queue<TreeNode> queue = new ConcurrentLinkedQueue<>();
        queue.add(root);

        int level = 0;
        while (!queue.isEmpty()) {
            List<Integer> list = new ArrayList<>();
            result.add(list);

            int currentQueueSize = queue.size();

            for (int i = 0; i < currentQueueSize; i++) {
                TreeNode curr = queue.remove();

                list = result.get(level);
                list.add(curr.val);

                if (curr.left != null) {
                    queue.add(curr.left);
                }

                if (curr.right != null) {
                    queue.add(curr.right);
                }
            }

            level++;
        }

        return result;
    }

    /**
     * LC - Iterative - With Queue and childNodes list
     * <p>
     * Runtime: 3 ms, faster than 8.82% of Java online submissions for Binary Tree Level Order Traversal.
     * Memory Usage: 42.8 MB, less than 82.71% of Java online submissions for Binary Tree Level Order Traversal.
     *
     * <p>
     * Time: O(N)
     * Space: O(N)
     */
    public List<List<Integer>> levelOrderLcIterativeBfsWithQueueAndChildNodeList(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) {
            return result;
        }

        final Queue<TreeNode> queue = new ConcurrentLinkedQueue<>();
        queue.add(root);

        int level = 0;
        while (!queue.isEmpty()) {
            List<TreeNode> childNodes = new ArrayList<>();

            List<Integer> list = new ArrayList<>();
            result.add(list);

            while (!queue.isEmpty()) {
                TreeNode curr = queue.remove();

                list = result.get(level);
                list.add(curr.val);

                if (curr.left != null) {
                    childNodes.add(curr.left);
                }

                if (curr.right != null) {
                    childNodes.add(curr.right);
                }
            }

            queue.addAll(childNodes);
            level++;
        }

        return result;
    }

    /**
     * LC - Recursion No Map. The index of result list is the level.
     *      Using list index to track tree level is faster than using Map.
     * <p>
     * Runtime: 1 ms, faster than 92.53% of Java online submissions for Binary Tree Level Order Traversal.
     * Memory Usage: 43.8 MB, less than 24.94% of Java online submissions for Binary Tree Level Order Traversal.
     * <p>
     * Time: O(n)
     * Space: O(n)
     */
    public List<List<Integer>> levelOrderLcRecursionNpMap(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();

        if (root == null) {
            return result;
        }

        /**
         * The index of the result list is the level number.
         * Using list index to track tree level is faster than using Map.
         */
        levelOrderLukeRecursionNoMap(root, 0, result);

        return result;
    }

    /**
     * The index of the result list is the level number.
     */
    private void levelOrderLukeRecursionNoMap(TreeNode root, int level, List<List<Integer>> result) {
        if (root == null) {
            return;
        }

        List<Integer> list;

        if (level < result.size()) {
            list = result.get(level);
        } else {
            list = new ArrayList<>();
            result.add(level, list);
        }

        list.add(root.val);

        levelOrderLukeRecursionNoMap(root.left, level + 1, result);
        levelOrderLukeRecursionNoMap(root.right, level + 1, result);
    }

    /**
     * Luke - Recursion with level map.
     *        Using map to track tree level is slower then using list index.
     * <p>
     * Runtime: 9 ms, faster than 8.82% of Java online submissions for Binary Tree Level Order Traversal.
     * Memory Usage: 44.3 MB, less than 6.93% of Java online submissions for Binary Tree Level Order Traversal.
     * <p>
     * Time: O(n)
     * Time: O(n)
     */
    public List<List<Integer>> levelOrderLukeRecursion(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();

        if (root == null) {
            return result;
        }

        Map<Integer, List<TreeNode>> levelNodesMap = new HashMap<>();
        levelOrderLukeRecursion(root, 0, levelNodesMap);

        for (int i = 0; i < levelNodesMap.size(); i++) {
            List<TreeNode> list = levelNodesMap.get(i);
            result.add(list.stream().map(e -> e.val).toList());
        }

        return result;
    }

    private void levelOrderLukeRecursion(TreeNode root, int level, Map<Integer, List<TreeNode>> levelNodesMap) {
        if (root == null) {
            return;
        }

        /**
         * Do not use "map.getOrDefault()", becuase it does not add the default to the map for the key.
         */
        List<TreeNode> list = levelNodesMap.computeIfAbsent(level, _ -> new ArrayList<>());
        list.add(root);

        levelOrderLukeRecursion(root.left, level + 1, levelNodesMap);
        levelOrderLukeRecursion(root.right, level + 1, levelNodesMap);
    }
}
