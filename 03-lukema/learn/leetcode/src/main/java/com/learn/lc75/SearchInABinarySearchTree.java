package com.learn.lc75;


import com.learn.tree.TreeNode;

import lombok.extern.log4j.Log4j2;


/**
 * LC - 700. Search in a Binary Search Tree
 *
 * Easy
 *
 * You are given the root of a binary search tree (BST) and an integer val.

Find the node in the BST that the node's value equals val and return the subtree rooted with that node. If such a node does not exist, return null.

Example 1:

Input: root = [4,2,7,1,3], val = 2
Output: [2,1,3]

Example 2:

Input: root = [4,2,7,1,3], val = 5
Output: []

Constraints:

    The number of nodes in the tree is in the range [1, 5000].
    1 <= Node.val <= 10 ^ 7
    root is a binary search tree.
    1 <= val <= 10 ^ 7
 */

@Log4j2
public class SearchInABinarySearchTree {

    public static void main(String[] args) {

        SearchInABinarySearchTree searchInABinarySearchTree = new SearchInABinarySearchTree();

        Integer[] root = { 4, 2, 7, 1, 3 };
        int val = 2;

        TreeNode head = TreeNode.toTreeBfsWithNullIntegers(root);

        var ret = searchInABinarySearchTree.searchBST(head, val);
        log.debug(": {}", () -> ret);
        log.debug(": {} OK", () -> "searchBST");

    }

    /**
     * Time: O(log(N))
     * Space: O(1)
     *
     * Runtime: -ms Beats 100.00
     * Memory: 44.05mb Beats 67.75
     */
    public TreeNode searchBST(TreeNode root, int val) {
        if (root == null) {
            return null;
        }

        if (root.val == val) {
            return root;
        } else {
            if (val < root.val) {
                return searchBST(root.left, val);
            } else {
                return searchBST(root.right, val);
            }
        }
    }
}
