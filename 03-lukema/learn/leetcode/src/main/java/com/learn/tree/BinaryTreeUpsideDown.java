package com.learn.tree;


import lombok.extern.log4j.Log4j2;


/**
 * LC - 156 - Binary Tree Upside Down
 * 
 * Medium
 * 
 * Given the root of a binary tree, turn the tree upside down and return the new root.
 * You can turn a binary tree upside down with the following steps:
 *     The original left child becomes the new root.
 *     The original root becomes the new right child.
 *     The original right child becomes the new left child.
 * The mentioned steps are done level by level. It is guaranteed that every right node has a sibling (a left node with the same parent) and has no children.
 * 
 * Example 1:
 * Input: root = [1,2,3,4,5]
 * Output: [4,5,2,null,null,3,1]
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
 *     The number of nodes in the tree will be in the range [0, 10].
 *     1 <= Node.val <= 10
 *     Every right node in the tree has a sibling (a left node that shares the same parent).
 *     Every right node in the tree has no children.
 */
@Log4j2
public class BinaryTreeUpsideDown {

    public static void main(String[] args) {

        final Integer[] nums = { 1, 2, 3, 4, 5 };
        // final Integer[] nums = { 1, 2, 3 };
        // final Integer[] nums = { 1 };

        final TreeNode root = TreeNode.toTreeBfsWithNullIntegers(nums);

        // log.debug("Binary tree upside down root: {}", () -> root);

        BinaryTreeUpsideDown binaryTreeUpsideDown = new BinaryTreeUpsideDown();

        TreeNode ret = binaryTreeUpsideDown.upsideDownBinaryTree(root);
        log.debug("Binary tree upside down: {}", () -> ret);

    }

    /**
     * Solution from https://kennyzhuang.gitbooks.io/leetcode-lock/content/156_binary_tree_upside_down.html
     * 
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Binary Tree Upside Down.
     * Memory Usage: 41 MB, less than 83.82% of Java online submissions for Binary Tree Upside Down.
     * 
     * Time: O(N)
     * Space: O(1)
     */
    public TreeNode upsideDownBinaryTree(TreeNode root) {

        TreeNode curr = root;
        TreeNode pre = null;
        TreeNode preRight = null;

        while (curr != null) {
            TreeNode tmp = curr.left;

            curr.left = preRight;
            preRight = curr.right;
            curr.right = pre;

            pre = curr;

            curr = tmp;
        }

        return pre;
    }
}
