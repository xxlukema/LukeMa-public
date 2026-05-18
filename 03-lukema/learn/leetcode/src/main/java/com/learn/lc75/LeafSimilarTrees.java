package com.learn.lc75;


import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.junit.jupiter.api.Assertions;

import com.learn.tree.TreeNode;

import lombok.extern.log4j.Log4j2;


/**
 * LC - 872. Leaf-Similar Trees
 *
 * Easy
 *
 * Consider all the leaves of a binary tree, from left to right order, the values of those leaves form a leaf value sequence.

For example, in the given tree above, the leaf value sequence is (6, 7, 4, 9, 8).

Two binary trees are considered leaf-similar if their leaf value sequence is the same.

Return true if and only if the two given trees with head nodes root1 and root2 are leaf-similar.

Example 1:

Input: root1 = [3,5,1,6,2,9,8,null,null,7,4], root2 = [3,5,1,6,7,4,2,null,null,null,null,null,null,9,8]
Output: true

Example 2:

Input: root1 = [1,2,3], root2 = [1,3,2]
Output: false


Constraints:

    The number of nodes in each tree will be in the range [1, 200].
    Both of the given trees will have values in the range [0, 200].
 */

@Log4j2
public class LeafSimilarTrees {

    public static void main(String[] args) {

        LeafSimilarTrees leafSimilarTrees = new LeafSimilarTrees();

        Integer[] root1 = { 3, 5, 1, 6, 2, 9, 8, null, null, 7, 4 }, root2 = { 3, 5, 1, 6, 7, 4, 2, null, null, null, null, null, null, 9, 8 };
        boolean expected = true;

        TreeNode head1 = TreeNode.toTreeBfsWithNullIntegers(root1);
        TreeNode head2 = TreeNode.toTreeBfsWithNullIntegers(root2);

        var ret = leafSimilarTrees.leafSimilar(head1, head2);
        log.debug("Leaf-Similar Trees: {}", () -> ret);
        Assertions.assertEquals(expected, ret);
        log.debug("Leaf-Similar Trees: {} OK", () -> "leafSimilar");

        var retNr = leafSimilarTrees.leafSimilarNoRecursion(head1, head2);
        log.debug("Leaf-Similar Trees: {}", () -> retNr);
        Assertions.assertEquals(expected, retNr);
        log.debug("Leaf-Similar Trees: {} OK", () -> "leafSimilarNoRecursion");

    }

    /**
     * Recursion
     *
     * Time: O(n1) + O(n2)
     * Space: O(max(n1, n2))
     *
     * Runtime: -ms Beats 100.00%
     * Memory: 40.84mb Beats 12.98%
     */
    public boolean leafSimilar(TreeNode root1, TreeNode root2) {
        List<Integer> leafs1 = traverse(root1);
        List<Integer> leafs2 = traverse(root2);

        return leafs1.equals(leafs2);
    }

    List<Integer> traverse(TreeNode root) {
        return traverse(root, new ArrayList<>());
    }

    List<Integer> traverse(TreeNode root, List<Integer> leafs) {
        if (root == null) {
            return leafs;
        }

        if (root.left == null && root.right == null) {
            leafs.add(root.val);
        } else {
            if (root.left != null) {
                traverse(root.left, leafs);
            }

            if (root.right != null) {
                traverse(root.right, leafs);
            }
        }

        return leafs;
    }

    /**
     * No recursion
     *
     * Time: O(n1) + O(n2)
     * Space: O(max(n1, n2))
     *
     * Runtime: 1ms Beats 19.19%
     * Memory: 40.55mb Beats 45.05%
     */
    public boolean leafSimilarNoRecursion(TreeNode root1, TreeNode root2) {
        List<Integer> leafs1 = traverseNoRecursion(root1);
        List<Integer> leafs2 = traverseNoRecursion(root2);

        return leafs1.equals(leafs2);
    }

    private List<Integer> traverseNoRecursion(TreeNode root) {

        List<Integer> leafs = new ArrayList<>();

        Stack<TreeNode> stack = new Stack<>();

        if (root == null) {
            return leafs;
        }

        stack.add(root);

        while (!stack.isEmpty()) {
            TreeNode end = stack.pop();
            if (end.left == null && end.right == null) {
                leafs.add(end.val);
            } else {
                /**
                 * For stack, push right before left
                 */
                if (end.right != null) {
                    stack.push(end.right);
                }

                if (end.left != null) {
                    stack.push(end.left);
                }
            }
        }

        return leafs;
    }
}
