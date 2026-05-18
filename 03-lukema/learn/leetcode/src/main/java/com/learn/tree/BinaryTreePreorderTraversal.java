package com.learn.tree;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.Assertions;

import lombok.extern.log4j.Log4j2;


/**
 * LC - 144 - Binary Tree Preorder Traversal
 * 
 * Easy
 * 
 * Given the root of a binary tree, return the preorder traversal of its nodes' values.
 * 
 * Example 1:
 * Input: root = [1,null,2,3]
 * Output: [1,2,3]
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
 *     The number of nodes in the tree is in the range [0, 100].
 *     -100 <= Node.val <= 100
 * 
 * Follow up: Recursive solution is trivial, could you do it iteratively?
 */
@Log4j2
public class BinaryTreePreorderTraversal {

    public static void main(String[] args) {

        final Integer[] nums = { 1, null, 2, 3 };

        final TreeNode root = TreeNode.toTreeBfsWithNullIntegers(nums);

        BinaryTreePreorderTraversal binaryTreePreorderTraversal = new BinaryTreePreorderTraversal();

        List<Integer> preorderTraversalLukeRecursion = binaryTreePreorderTraversal.preorderTraversalLukeRecursion(root);
        log.debug("Tree Preorder Traversal: {}", () -> preorderTraversalLukeRecursion);
        log.debug("Tree Preorder Traversal {} OK", () -> "preorderTraversalLukeRecursion");

        List<Integer> preorderTraversalLukeIterative = binaryTreePreorderTraversal.preorderTraversalMorrisIterative(root);
        Assertions.assertEquals(preorderTraversalLukeRecursion, preorderTraversalLukeIterative);
        log.debug("Tree Preorder Traversal {} OK", () -> "preorderTraversalLukeIterative");

    }

    /**
     * Luke - Recursion
     * 
     * Runtime: 1 ms, faster than 51.68% of Java online submissions for Binary Tree Preorder Traversal.
     * Memory Usage: 42.2 MB, less than 61.14% of Java online submissions for Binary Tree Preorder Traversal.
     * 
     * Time: O(N)
     * Space: O(N) - Recursion stack size
     */
    public List<Integer> preorderTraversalLukeRecursion(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        if (root == null) {
            return list;
        }

        list.add(root.val);

        list.addAll(preorderTraversalLukeRecursion(root.left));
        list.addAll(preorderTraversalLukeRecursion(root.right));

        return list;
    }

    /**
     * LC - Morris
     * 
     * 
     * Time: O(N)
     * Space: O(N)
     */
    public List<Integer> preorderTraversalMorrisIterative(TreeNode root) {
        final LinkedList<Integer> list = new LinkedList<>();

        TreeNode morris = root;

        while (morris != null) {
            if (morris.left == null) {
                list.add(morris.val);
                morris = morris.right;
            } else {
                TreeNode predecessor = morris.left;

                TreeNode curr = predecessor;

                while ((curr.right != null) && (curr.right != morris)) {
                    curr = curr.right;
                }

                if (curr.right == null) {
                    list.add(morris.val);
                    curr.right = morris;
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
