package com.learn.tree;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.Assertions;

import lombok.extern.log4j.Log4j2;


/**
 * LC - 145 - Binary Tree Postorder Traversal
 * 
 * Easy
 * 
 * Given the root of a binary tree, return the postorder traversal of its nodes' values.
 * 
 * Example 1:
 * Input: root = [1,null,2,3]
 * Output: [3,2,1]
 * 
 * Example 2:
 * Input: root = []
 * Output: []
 * 
 * Example 3:
 * Input: root = [1]
 * Output: [1]
 * 
 * Constraints:
 *     The number of the nodes in the tree is in the range [0, 100].
 *     -100 <= Node.val <= 100
 * 
 * Follow up: Recursive solution is trivial, could you do it iteratively?
 */
@Log4j2
public class BinaryTreePostorderTraversal {

    public static void main(String[] args) {

        // final Integer[] nums = { 1, null, 2, 3 };
        final Integer[] nums = { 3, 1, 2 };

        final TreeNode root = TreeNode.toTreeBfsWithNullIntegers(nums);

        BinaryTreePostorderTraversal binaryTreePostorderTraversal = new BinaryTreePostorderTraversal();

        List<Integer> postorderTraversalLukeRecursion = binaryTreePostorderTraversal.postorderTraversalLukeRecursion(root);
        log.debug("Tree Preorder Traversal: {}", () -> postorderTraversalLukeRecursion);
        log.debug("Tree Preorder Traversal {} OK", () -> "postorderTraversalLukeRecursion");

        List<Integer> postorderTraversalMorrisIterative = binaryTreePostorderTraversal.postorderTraversalMorrisIterative(root);
        Assertions.assertEquals(postorderTraversalLukeRecursion, postorderTraversalMorrisIterative);
        log.debug("Tree Preorder Traversal {} OK", () -> "postorderTraversalMorrisIterative");
    }

    /**
     * Luke - Recursion
     * 
     * Runtime: 2 ms, faster than 7.21% of Java online submissions for Binary Tree Postorder Traversal.
     * Memory Usage: 41.9 MB, less than 74.94% of Java online submissions for Binary Tree Postorder Traversal.
     * 
     * Time: O(N)
     * Space: O(N)
     */
    public List<Integer> postorderTraversalLukeRecursion(TreeNode root) {
        final List<Integer> list = new ArrayList<>();
        if (root == null) {
            return list;
        }

        list.addAll(postorderTraversalLukeRecursion(root.left));
        list.addAll(postorderTraversalLukeRecursion(root.right));
        list.add(root.val);

        return list;
    }

    /**
     * Luke - Morris
     * 
     * 
     * Time: O(N)
     * Space: O(1)
     */
    public List<Integer> postorderTraversalMorrisIterative(TreeNode root) {
        final LinkedList<Integer> list = new LinkedList<>();
        if (root == null) {
            return list;
        }

        TreeNode morris = root;

        while (morris != null) {
            if (morris.left == null) {
                list.addFirst(morris.val);
                morris = morris.right;
            } else {
                TreeNode predecessor = morris.left;
                TreeNode curr = predecessor;

                while (curr.right != null && curr.right != morris) {
                    curr = curr.right;
                }

                if (curr.right == null) {
                    curr.right = morris;
                    list.addFirst(morris.val);
                    morris = predecessor;
                } else {
                    curr.right = null;
                    morris = morris.right;
                }
            }
        }

        return list;
    }
}
