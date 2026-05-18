package com.learn.tree;


import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import lombok.extern.log4j.Log4j2;


/**
 * LC-314 Binary Tree Vertical Traversal
 *
 * Medium
 *
 * Given the root of a binary tree, return the vertical order traversal of its nodes' values. (i.e., from top to bottom, column by column).
 *
 * If two nodes are in the same row and column, the order should be from left to right.
 *
 * Example 1:
 * Input: root = [3,9,20,null,null,15,7]
 * Output: [[9],[3,15],[20],[7]]
 *
 * Example 2:
 * Input: root = [3,9,8,4,0,1,7]
 * Output: [[4],[9],[3,0,1],[8],[7]]
 *
 * Example 3:
 * Input: root = [3,9,8,4,0,1,7,null,null,null,2,5]
 * Output: [[4],[9,5],[3,0,1],[8,2],[7]]
 *
 * Constraints:
 * The number of nodes in the tree is in the range [0, 100].
 * -100 <= Node.val <= 100
 */
@Log4j2
public class BinaryTreeVerticalTraversal {

    public static void main(String[] args) {

        // final Integer[] nums = { 3, 9, 20, null, null, 15, 7 };
        // final Integer[] nums = { 3, 9, 8, 4, 0, 1, 7 };
        final Integer[] nums = { 3, 9, 8, 4, 0, 1, 7, null, null, null, 2, 5 };

        BinaryTreeVerticalTraversal binaryTreeVerticalTraversal = new BinaryTreeVerticalTraversal();

        TreeNode root = TreeNode.toTreeBfsWithNullIntegers(nums);

        var verticalOrderLukeBfs = binaryTreeVerticalTraversal.verticalOrderLukeBfs(root);
        log.debug("Binary Tree Vertical Traversal: {}", () -> verticalOrderLukeBfs);
        log.debug("Binary Tree Vertical Traversal {} OK", () -> "verticalOrderLukeBfs");

        var verticalOrderLcBfs = binaryTreeVerticalTraversal.verticalOrderLcBfs(root);
        log.debug("Binary Tree Vertical Traversal: {}", () -> verticalOrderLcBfs);
        log.debug("Binary Tree Vertical Traversal {} OK", () -> "verticalOrderLcBfs");

    }

    /**
     * Luke - BFS
     *        Trick 1: Use colMap
     *        Trick 2: BFS
     *
     * Runtime: 7 ms Beats 21.29%
     * Memory: 43.7 MB Beats 30.68%
     *
     * Time: O(N * log(N))
     * Space: O(N)
     */
    public List<List<Integer>> verticalOrderLukeBfs(TreeNode root) {

        final List<List<Integer>> result = new ArrayList<>();
        if (root == null) {
            return result;
        }

        final List<List<Node>> data = new ArrayList<>();

        bfs(root, data);

        for (int col = left; col <= right; col++) {
            List<Integer> list = new ArrayList<>();
            result.add(list);

            for (int h = 0; h < data.size(); h++) {
                List<Node> nodes = data.get(h);
                while (!nodes.isEmpty() && nodes.get(0).col == col) {
                    list.add(nodes.remove(0).node.val);
                }
            }
        }

        return result;
    }

    int left = 0;
    int right = 0;

    /**
     * Time: O(N)
     * Space: O(N)
     */
    private void bfs(TreeNode root, List<List<Node>> data) {
        final Queue<Node> queue = new ArrayDeque<>();
        queue.offer(new Node(root, 0));

        while (!queue.isEmpty()) {
            final List<Node> list = new ArrayList<>();
            data.add(list);
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                Node cur = queue.poll();
                list.add(cur);
                if (cur.node.left != null) {
                    queue.offer(new Node(cur.node.left, cur.col - 1));
                    left = Math.min(left, cur.col - 1);
                }
                if (cur.node.right != null) {
                    queue.offer(new Node(cur.node.right, cur.col + 1));
                    right = Math.max(right, cur.col + 1);
                }
            }

            Collections.sort(list, (a, b) -> a.col - b.col);
        }
    }

    public record Node(TreeNode node, int col) {
    }

    /**
     * LC - BFS
     *      Trick 1: Use colMap
     *      Trick 2: BFS
     *
     * Time: O(N) --- because no sorting is needed.
     * Space: O(N)
     */
    public List<List<Integer>> verticalOrderLcBfs(TreeNode root) {
        final List<List<Integer>> output = new ArrayList<>();
        if (root == null) {
            return output;
        }

        final Map<Integer, ArrayList<Integer>> colMap = new HashMap<>();

        final Queue<Node> queue = new ArrayDeque<>();
        queue.offer(new Node(root, 0));

        int minColumn = 0, maxColumn = 0;

        while (!queue.isEmpty()) {
            Node cur = queue.poll();

            if (!colMap.containsKey(cur.col)) {
                colMap.put(cur.col, new ArrayList<Integer>());
            }
            colMap.get(cur.col).add(cur.node.val);
            minColumn = Math.min(minColumn, cur.col);
            maxColumn = Math.max(maxColumn, cur.col);

            if (cur.node.left != null) {
                queue.offer(new Node(cur.node.left, cur.col - 1));
            }
            if (cur.node.right != null) {
                queue.offer(new Node(cur.node.right, cur.col + 1));
            }
        }

        for (int i = minColumn; i < maxColumn + 1; ++i) {
            output.add(colMap.get(i));
        }

        return output;
    }
}
