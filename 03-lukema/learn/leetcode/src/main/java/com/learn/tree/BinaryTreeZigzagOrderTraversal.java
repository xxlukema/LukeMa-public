package com.learn.tree;


import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;

import org.junit.jupiter.api.Assertions;

import lombok.extern.log4j.Log4j2;


/**
 * LC - 103 - Binary Tree Zigzag Level Order Traversal
 */
@Log4j2
public class BinaryTreeZigzagOrderTraversal {

    public static void main(String[] args) {

        Integer[] nums = { 3, 9, 20, null, null, 15, 7 };
        // Integer[] nums = { 0, 2, 4, 1, null, 3, -1, 5, 1, null, 6, null, 8 };

        TreeNode root = TreeNode.toTreeBfsWithNullIntegers(nums);

        BinaryTreeZigzagOrderTraversal binaryTreeZigzagOrderTraversal = new BinaryTreeZigzagOrderTraversal();

        List<List<Integer>> retLc = binaryTreeZigzagOrderTraversal.zigzagLevelOrderLcIterative(root);
        log.debug("Binary Tree Zigzag Level Order Traversal: {}", () -> retLc);

        List<List<Integer>> retLuke = binaryTreeZigzagOrderTraversal.zigzagLevelOrderLukeTwoDeques(root);
        Assertions.assertEquals(retLc, retLuke);

        List<List<Integer>> retLcDfs = binaryTreeZigzagOrderTraversal.zigzagLevelOrderLcDfsRecursion(root);
        Assertions.assertEquals(retLc, retLcDfs);
    }

    /**
     * LC - Recursion - DFS
     * 
     * This is not zigzag traversal. It is DFS traversal, although it returns the correct result;
     * 
     * Runtime: 1 ms, faster than 96.25% of Java online submissions for Binary Tree Zigzag Level Order Traversal.
     * Memory Usage: 42.7 MB, less than 52.92% of Java online submissions for Binary Tree Zigzag Level Order Traversal.
     * 
     * Time: O(n)
     * Space: O(n) (1) Unlike the BFS approach, in the DFS approach, we do not need to maintain the node_queue data structure for the traversal.
     *             (2) However, the function recursion will incur additional memory consumption on the function call stack. As we can see, the 
     *                 size of the call stack for any invocation of DFS(node, level) will be exactly the number of level that the current node
     *                 resides on. Therefore, the space complexity of our DFS algorithm is O(H), where HHH is the height of the tree.
     *                 In the worst-case scenario, when the tree is very skewed, the tree height could be NNN. Thus the space complexity is also O(N).
     *             (3) Note that if the tree were guaranteed to be balanced, then the maximum height of the tree would be log(N) which would result
     *                 in a better space complexity than the BFS approach.
     */
    public List<List<Integer>> zigzagLevelOrderLcDfsRecursion(TreeNode root) {
        if (root == null) {
            return new ArrayList<List<Integer>>();
        }
        List<List<Integer>> results = new ArrayList<List<Integer>>();
        lcDfs(root, 0, results);
        return results;
    }

    protected void lcDfs(TreeNode node, int level, List<List<Integer>> results) {
        List<Integer> newLevel = null;

        if (level < results.size()) {
            newLevel = results.get(level);
        } else {
            newLevel = new LinkedList<Integer>();
            results.add(newLevel);
        }

        if (level % 2 == 0) {
            newLevel.add(node.val);
        } else {
            newLevel.add(0, node.val);
        }

        if (node.left != null) {
            lcDfs(node.left, level + 1, results);
        }
        if (node.right != null) {
            lcDfs(node.right, level + 1, results);
        }
    }

    /**
     * LC - Iterative 
     * 
     * Runtime: 2 ms, faster than 37.47% of Java online submissions for Binary Tree Zigzag Level Order Traversal.
     * Memory Usage: 42.4 MB, less than 76.54% of Java online submissions for Binary Tree Zigzag Level Order Traversal.
     * 
     * Time: O(n)
     * Space: O(n)
     */
    public List<List<Integer>> zigzagLevelOrderLcIterative(TreeNode root) {
        if (root == null) {
            return new ArrayList<List<Integer>>();
        }

        List<List<Integer>> results = new ArrayList<List<Integer>>();

        // add the root element with a delimiter to kick off the BFS loop
        LinkedList<TreeNode> queue = new LinkedList<TreeNode>();
        queue.addLast(root);
        queue.addLast(null);

        LinkedList<Integer> list = new LinkedList<Integer>();
        boolean isOrderLeft = true;

        while (!queue.isEmpty()) {
            TreeNode curr = queue.pollFirst();
            if (curr != null) {
                if (isOrderLeft)
                    list.addLast(curr.val);
                else
                    list.addFirst(curr.val);

                if (curr.left != null)
                    queue.addLast(curr.left);
                if (curr.right != null)
                    queue.addLast(curr.right);

            } else {
                // we finish the scan of one level
                results.add(list);
                list = new LinkedList<Integer>();
                // prepare for the next level
                if (queue.size() > 0)
                    queue.addLast(null);
                isOrderLeft = !isOrderLeft;
            }
        }
        return results;
    }

    /**
     * Luke - Iterative - Two Deques.
     * 
     * Runtime: 1 ms, faster than 96.25% of Java online submissions for Binary Tree Zigzag Level Order Traversal.
     * Memory Usage: 42.1 MB, less than 81.28% of Java online submissions for Binary Tree Zigzag Level Order Traversal.
     * 
     * Time: O(n)
     * Space: O(n)
     */
    public List<List<Integer>> zigzagLevelOrderLukeTwoDeques(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) {
            return result;
        }

        final Deque<TreeNode> deque = new ConcurrentLinkedDeque<>();
        deque.add(root);

        boolean isLeftToRight = true;
        while (!deque.isEmpty()) {
            List<Integer> list = new ArrayList<>();
            result.add(list);

            LinkedList<TreeNode> levelDeque = new LinkedList<>();

            while (!deque.isEmpty()) {

                TreeNode curr = null;

                if (isLeftToRight) {
                    curr = deque.pollFirst();
                } else {
                    curr = deque.pollLast();
                }

                list.add(curr.val);

                /**
                 * 0th level left to right. children right to left.
                 * 1st level right to left. children left to right.
                 */
                if (isLeftToRight) {
                    /**
                     * Reverse children.
                     */
                    if (curr.left != null) {
                        levelDeque.addLast(curr.left);
                    }
                    if (curr.right != null) {
                        levelDeque.addLast(curr.right);
                    }
                } else {
                    /**
                     * Also, Reverse children.
                     */
                    if (curr.right != null) {
                        levelDeque.addFirst(curr.right);
                    }
                    if (curr.left != null) {
                        levelDeque.addFirst(curr.left);
                    }
                }
            }

            deque.addAll(levelDeque);

            isLeftToRight = !isLeftToRight;
        }

        return result;
    }
}
