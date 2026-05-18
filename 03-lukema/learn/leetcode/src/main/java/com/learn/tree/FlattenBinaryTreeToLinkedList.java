package com.learn.tree;


import org.junit.jupiter.api.Assertions;

import lombok.extern.log4j.Log4j2;


/**
 * LC - 114 - Medium - Flattern Binary Tree To Linked List - Using "pre-order" Traversal
 *
 * Given the root of a binary tree, flatten the tree into a "linked list":
 *
 * The "linked list" should use the same TreeNode class where the right child pointer points to the next node in the list and the left child pointer is always null.
 * The "linked list" should be in the same order as a "pre-order" traversal of the binary tree.
 */
@Log4j2
public class FlattenBinaryTreeToLinkedList {

    public static void main(String[] args) {

        Integer[] nums = { 1, 2, 5, 3, 4, null, 6 };

        TreeNode root = TreeNode.toTreeBfsWithNullIntegers(nums);

        log.debug("root: {}", root);

        FlattenBinaryTreeToLinkedList flattenBinaryTreeToLinkedList = new FlattenBinaryTreeToLinkedList();

        flattenBinaryTreeToLinkedList.flattenLukeDfsNewTreeNode(root);
        log.debug("Flattened tree LukeDfsNewTreeNode: {}", root);

        TreeNode rootLcIterative = TreeNode.toTreeBfsWithNullIntegers(nums);
        flattenBinaryTreeToLinkedList.flattenLcIterative(rootLcIterative);
        Assertions.assertEquals(root.toString(), rootLcIterative.toString());
        log.debug(() -> "Passed Lc Iterative Same Tree");

        TreeNode rootLukeRecursion = TreeNode.toTreeBfsWithNullIntegers(nums);
        flattenBinaryTreeToLinkedList.flattenLukeRecursionSameTree(rootLukeRecursion);
        Assertions.assertEquals(root.toString(), rootLukeRecursion.toString());
        log.debug(() -> "Passed Luke Recursion Same Tree");

        TreeNode rootLcRecursion = TreeNode.toTreeBfsWithNullIntegers(nums);
        flattenBinaryTreeToLinkedList.flattenLcRecursionSameTree(rootLcRecursion);
        Assertions.assertEquals(root.toString(), rootLcRecursion.toString());
        log.debug(() -> "Passed Lc Recursion Same Tree");

        TreeNode rootLukeMorris = TreeNode.toTreeBfsWithNullIntegers(nums);
        flattenBinaryTreeToLinkedList.flattenLukeMorrisPreorderTraversalIterative(rootLukeMorris);
        Assertions.assertEquals(root.toString(), rootLukeMorris.toString());
        log.debug(() -> "Passed Luke Morris Preorder Traversal");
    }

    /**
     * LC - Recursion
     * 
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Flatten Binary Tree to Linked List.
     * Memory Usage: 43.5 MB, less than 8.27% of Java online submissions for Flatten Binary Tree to Linked List.
     * 
     * Time: O(n)
     * Space: O(n)
     */
    public void flattenLcRecursionSameTree(TreeNode root) {
        this.flattenTreeflattenRecursionSameTree(root);
    }

    private TreeNode flattenTreeflattenRecursionSameTree(TreeNode node) {

        // Handle the null scenario
        if (node == null) {
            return null;
        }

        // For a leaf node, we simply return the
        // node as is.
        if (node.left == null && node.right == null) {
            return node;
        }

        //Recursively flatten the left subtree
        TreeNode leftTail = this.flattenTreeflattenRecursionSameTree(node.left);

        // Recursively flatten the right subtree
        TreeNode rightTail = this.flattenTreeflattenRecursionSameTree(node.right);

        /**
         * The following will be executed from Bottom-Up as recursion returns (bobble up).
         */

        // If there was a left subtree, we shuffle the connections
        // around so that there is nothing on the left side
        // anymore.
        if (leftTail != null) {
            leftTail.right = node.right;
            node.right = node.left;
            node.left = null;
        }

        // We need to return the "rightmost" node after we are
        // done wiring the new connections. 
        return rightTail == null ? leftTail : rightTail;
    }

    /**
     * Luke - Recursion - Same Tree
     * 
     * Runtime: 1 ms, faster than 78.58% of Java online submissions for Flatten Binary Tree to Linked List.
     * Memory Usage: 42.6 MB, less than 52.44% of Java online submissions for Flatten Binary Tree to Linked List.
     * 
     * Time: O(n)
     * Space: O(Height)
     */
    public void flattenLukeRecursionSameTree(TreeNode root) {

        /**
         * Handle the null scenario.
         * 
         * More Importantly, this statement breaks the recursion. Reverses the recursion from "Top-Down" to "Bottom-Up"!!!
         */
        if (root == null) {
            return;
        }

        TreeNode tmpLeft = root.left;
        TreeNode tmpRight = root.right;

        /**
         * Above the following recursion call, code are executed prior to recursion. It is called "Top-Down".
         */

        flattenLukeRecursionSameTree(tmpLeft);
        flattenLukeRecursionSameTree(tmpRight);

        /**
         * The following will be executed from "Bottom-Up" as recursion returns (bobble up).
         * 
         */

        root.right = tmpLeft;
        root.left = null;

        TreeNode curr = root;
        while (curr.right != null) {
            curr = curr.right;
        }

        curr.right = tmpRight;
    }

    /**
     * Luke - Morris Traversal - Preorder Tree Traversal
     * 
     * Runtime: 1 ms, faster than 78.58% of Java online submissions for Flatten Binary Tree to Linked List.
     * Memory Usage: 43.4 MB, less than 8.27% of Java online submissions for Flatten Binary Tree to Linked List.
     * 
     * Time: O(n)
     * Space: O(1)
     */
    public void flattenLukeMorrisPreorderTraversalIterative(TreeNode root) {
        if (root == null) {
            return;
        }

        /**
         * I want to keep root intact. So I use morris to walk the nodes.
         * This is good if I want to return root in the future.
         */
        TreeNode morris = root;

        while (morris != null) {
            if (morris.left == null) {
                /**
                 * Progress morris.
                 */
                morris = morris.right;
            } else {
                /**
                 * morris.left --- named "predecessor" for Inorder traversal.
                 * morris.left --- named "morrisLeft" for Preorder traversal.
                 */
                TreeNode morrisLeft = morris.left;
                TreeNode curr = morrisLeft;

                while (curr.right != null) {
                    curr = curr.right;
                }

                curr.right = morris.right;
                morris.left = null;

                morris.right = morrisLeft;
            }
        }
    }

    /**
     * LC - Iterative
     *
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Flatten Binary Tree to Linked List.
     * Memory Usage: 41.7 MB, less than 95.68% of Java online submissions for Flatten Binary Tree to Linked List.
     *
     * Time: O(n)
     * Space: O(1)
     */
    public void flattenLcIterative(TreeNode root) {

        // Handle the null scenario
        if (root == null) {
            return;
        }

        TreeNode node = root;

        while (node != null) {

            // If the node has a left child
            if (node.left != null) {

                // Find the rightmost node
                TreeNode rightmost = node.left;
                while (rightmost.right != null) {
                    rightmost = rightmost.right;
                }

                // rewire the connections
                rightmost.right = node.right;
                node.right = node.left;
                node.left = null;
            }

            // move on to the right side of the tree
            node = node.right;
        }
    }

    /**
     * Luke - DFS - Recursion
     *
     * Runtime: 1 ms, faster than 78.58% of Java online submissions for Flatten Binary Tree to Linked List.
     * Memory Usage: 43.4 MB, less than 8.27% of Java online submissions for Flatten Binary Tree to Linked List.
     *
     * Time: O(n)
     * Space: O(n)
     */
    public void flattenLukeDfsNewTreeNode(TreeNode root) {
        if (root == null) {
            return;
        }
        TreeNode newRoot = preorderTraverse(root);
        root.val = newRoot.val;
        root.left = null;
        root.right = newRoot.right;
    }

    private TreeNode preorderTraverse(TreeNode root) {
        if (root == null) {
            return null;
        }

        TreeNode newRoot = new TreeNode(root.val);

        newRoot.right = preorderTraverse(root.left);

        /**
         * Move "curr" to right bottom.
         */
        TreeNode curr = newRoot;
        while (curr.right != null) {
            curr = curr.right;
        }

        curr.right = preorderTraverse(root.right);

        return newRoot;
    }
}
