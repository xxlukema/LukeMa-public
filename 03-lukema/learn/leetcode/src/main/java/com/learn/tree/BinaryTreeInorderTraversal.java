package com.learn.tree;


import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.junit.jupiter.api.Assertions;

import lombok.extern.log4j.Log4j2;


/**
 * LC 94
 * 
 * @see com.learn.test.tree.TreeTest
 */
@Log4j2
public class BinaryTreeInorderTraversal {

    public static void main(String[] args) {

        // Integer[] nums = {10, null, 12, 13, 14, 15, null, 16, 17};
        Integer[] nums = { 10, null, 12, 13, 14, 15, null, 16, 17 };

        BinaryTreeInorderTraversal binaryTreeInorderTraversal = new BinaryTreeInorderTraversal();

        TreeNode root = TreeNode.toTreeBfsWithNullIntegers(nums);

        log.debug("root: {}", () -> root);

        List<Integer> retLukeIterative = binaryTreeInorderTraversal.inorderTraversalIterateStack(root);
        log.debug("Binary tree Inorder traversal - Iterative: {}", () -> retLukeIterative);

        List<Integer> retLukeRecursion = binaryTreeInorderTraversal.inorderTraversalLukeRecursion(root);
        Assertions.assertEquals(retLukeRecursion, retLukeIterative);

        log.debug(() -> "Binary tree Inorder traversal - Recursion OK");

        TreeNode rootMorrisNoRecover = TreeNode.toTreeBfsWithNullIntegers(nums);

        List<Integer> retMorrisNoRecover = binaryTreeInorderTraversal.inorderTraversalLukeMorrisNoRecover(rootMorrisNoRecover);
        Assertions.assertEquals(retLukeRecursion, retMorrisNoRecover);

        log.debug(() -> "Binary tree Inorder traversal - Luke Morris No Recover OK");

        TreeNode rootMorrisRecover = TreeNode.toTreeBfsWithNullIntegers(nums);

        List<Integer> retMorrisRecover = binaryTreeInorderTraversal.inorderTraversalLukeMorrisRecover(rootMorrisRecover);
        Assertions.assertEquals(retLukeRecursion, retMorrisRecover);

        log.debug(() -> "Binary tree Inorder traversal - Luke Morris With Recover OK");
    }

    /**
     * Luke: Morris Traversal - With Recovery of Original Tree.
     * 
     * The difference between "With Recovery" and "Non Recover" of original tree is that "With Recovery will traverse" the left
     * branch twice: With the first traversal to build the most bottom right pointer to morris, and the second traveral is to recover
     * the tree by removing the new link. "With Recovery" the time complex is O(2N). "Non Recovery" time complexity is O(N).
     * 
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Binary Tree Inorder Traversal.
     * Memory Usage: 42.4 MB, less than 31.07% of Java online submissions for Binary Tree Inorder Traversal.
     * 
     * Time: O(2N)
     * Space: O(1)
     */
    public List<Integer> inorderTraversalLukeMorrisRecover(TreeNode root) {
        List<Integer> list = new ArrayList<>();

        if (root == null) {
            return list;
        }

        /**
         * Assign reference to morris to keep root value unchanged. So that root value can be re-used, when it is needed.
         */
        TreeNode morris = root;

        while (morris != null) {

            if (morris.left == null) {
                /**
                 * Inorder traversing
                 */
                list.add(morris.val);

                morris = morris.right;
            } else {
                /**
                 * morris.left --- named "predecessor" for Inorder traversal.
                 * morris.left --- named "morrisLeft" for Preorder traversal.
                 */
                TreeNode predecessor = morris.left;
                TreeNode curr = predecessor;
                while (curr.right != null && curr.right != morris) {
                    curr = curr.right;
                }

                if (curr.right == null) {
                    /**
                     * The first traverse. Build a new link. The link will be removed in the second recovery traversal.
                     */
                    curr.right = morris;

                    /**
                     * Preorder - Move morris to predecessor.
                     */
                    morris = predecessor;
                } else {
                    /**
                     * The second traversal. Remove the link created within the above "if" condition.
                     */
                    curr.right = null;

                    list.add(morris.val);

                    morris = morris.right;
                }
            }
        }

        return list;
    }

    /**
     * Luke: Morris Traversal - No Recovery of Original Tree.
     * 
     * The difference between "With Recovery" and "Non Recover" of original tree is that "With Recovery will traverse" the left
     * branch twice: With the first traversal to build the most bottom right pointer to morris, and the second traveral is to recover
     * the tree by removing the new link. "With Recovery" the time complex is O(2N). "Non Recovery" time complexity is O(N).
     * 
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Binary Tree Inorder Traversal.
     * Memory Usage: 42.9 MB, less than 8.90% of Java online submissions for Binary Tree Inorder Traversal.
     * 
     * Time: O(N)
     * Space: O(1)
     */
    public List<Integer> inorderTraversalLukeMorrisNoRecover(TreeNode root) {
        List<Integer> list = new ArrayList<>();

        if (root == null) {
            return list;
        }

        /**
         * Assign reference to morris to keep root value unchanged. So that root value can be re-used, when it is needed.
         */
        TreeNode morris = root;

        while (morris != null) {

            if (morris.left == null) {
                /**
                 * No predecessor. Add this node value to result list.
                 */
                list.add(morris.val);

                /**
                 * Progress morris.
                 */
                morris = morris.right;
            } else {
                TreeNode predecessor = morris.left;
                TreeNode curr = predecessor;

                while (curr.right != null) {
                    curr = curr.right;
                }

                curr.right = morris;

                /**
                 * Important! morris.left was predecessor before. Now, nullify it to prevent the while loop go to left again.
                 */
                morris.left = null;

                /**
                 * Move morris back to predecessor.
                 */
                morris = predecessor;
            }
        }

        return list;
    }

    /**
     * Luke: Recursion
     * <p>
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Binary Tree Inorder Traversal.
     * Memory Usage: 42.5 MB, less than 21.50% of Java online submissions for Binary Tree Inorder Traversal.
     * <p>
     * Time: O(N)
     * Space: O(H) - H: Height of the tree.
     */
    public List<Integer> inorderTraversalLukeRecursion(TreeNode root) {
        List<Integer> list = new ArrayList<>();

        inOrder(root, list);

        return list;
    }

    private void inOrder(TreeNode root, List<Integer> list) {
        if (root == null) {
            return;
        }

        inOrder(root.left, list);
        list.add(root.val);
        inOrder(root.right, list);
    }

    /**
     * LC: Iterating using stack
     * <p>
     * Time: O(N)
     * Space: O(N)
     */
    public List<Integer> inorderTraversalIterateStack(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();

        while (root != null || !stack.isEmpty()) {
            while (root != null) {
                stack.push(root);
                root = root.left;
            }

            root = stack.pop();
            list.add(root.val);

            root = root.right;
        }

        return list;
    }

}
