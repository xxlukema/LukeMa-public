package com.learn.tree;


import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import lombok.extern.log4j.Log4j2;


/**
 * LC-257 Binary Tree Path
 *
 * Easy
 *
 * Given the root of a binary tree, return all root-to-leaf paths in any order.
 *
 * A leaf is a node with no children.
 *
 * Example 1:
 * Input: root = [1,2,3,null,5]
 * Output: ["1->2->5","1->3"]
 *
 * Example 2:
 * Input: root = [1]
 * Output: ["1"]
 *
 * Constraints:
 *     The number of nodes in the tree is in the range [1, 100].
 *     -100 <= Node.val <= 100
 */
@Log4j2
public class BinaryTreePath {

    public static void main(String[] args) {

        final Integer[] nums = { 1, 2, 3, null, 5 };
        // final Integer[] nums = { 1 };

        final TreeNode root = TreeNode.toTreeBfsWithNullIntegers(nums);

        BinaryTreePath binaryTreePath = new BinaryTreePath();

        var binaryTreePaths = binaryTreePath.binaryTreePaths(root);
        log.debug("Binary Tree Path: {}", () -> binaryTreePaths);
        log.debug("Binary Tree Path {} OK", () -> "binaryTreePaths");

        var binaryTreePathsRemoveRecursion = binaryTreePath.binaryTreePathsRemoveRecursion(root);
        log.debug("Binary Tree Path: {}", () -> binaryTreePathsRemoveRecursion);
        log.debug("Binary Tree Path {} OK", () -> "binaryTreePathsRemoveRecursion");
    }

    /**
     * Luke - Recursive
     *
     * Runtime: 14 ms Beats 46.25%
     * Memory: 43.4 MB Beats 43.45%
     *
     * Time: O(N)
     * Space: O(N)
     */
    public List<String> binaryTreePaths(TreeNode root) {
        List<String> result = new ArrayList<>();

        if (root == null) {
            return result;
        }

        String path = String.valueOf(root.val);

        binaryTreePaths(root, result, path);

        return result;
    }

    private void binaryTreePaths(TreeNode root, List<String> result, String path) {
        if (root.left == null && root.right == null) {
            result.add(path.toString());
            return;
        }

        if (root.left != null) {
            binaryTreePaths(root.left, result, path + "->" + root.left.val);
        }

        if (root.right != null) {
            binaryTreePaths(root.right, result, path + "->" + root.right.val);
        }
    }

    /**
     * Luke - Replace recursion with iteration
     *
     * Runtime: 11 ms Beats 55.32%
     * Memory: 42.7 MB Beats 79.15%
     *
     * Time: O(N)
     * Space: O(N)
     */
    public List<String> binaryTreePathsRemoveRecursion(TreeNode root) {
        List<String> result = new ArrayList<>();

        if (root == null) {
            return result;
        }

        Stack<String> stackParam = new Stack<>();
        stackParam.push(String.valueOf(root.val));

        Stack<TreeNode> stackTreeNode = new Stack<>();
        stackTreeNode.push(root);

        while (!stackTreeNode.isEmpty()) {
            TreeNode cur = stackTreeNode.pop();
            String param = stackParam.pop();

            if (cur.left == null && cur.right == null) {
                result.add(param);
                continue;
            }

            if (cur.right != null) {
                stackParam.push(param + "->" + cur.right.val);
                stackTreeNode.push(cur.right);
            }

            if (cur.left != null) {
                stackParam.push(param + "->" + cur.left.val);
                stackTreeNode.push(cur.left);
            }
        }

        return result;
    }
}
