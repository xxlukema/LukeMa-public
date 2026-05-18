package com.learn.tree;


import java.util.HashMap;
import java.util.Map;

import lombok.extern.log4j.Log4j2;


/**
 * LC - 106 - Construct Binary Tree from Inorder and Postorder Traversal
 */
@Log4j2
public class ConstructBinaryTreeFromInorderAndPostOrderTraversal {

    public static void main(String[] args) {

        // int[] inorder = { 9, 3, 15, 20, 7 };
        // int[] postorder = { 9, 15, 7, 20, 3 };

        int[] inorder = { 1, 2, 3, 4 };
        int[] postorder = { 2, 1, 4, 3 };

        ConstructBinaryTreeFromInorderAndPostOrderTraversal constructBinaryTreeFromInorderAndPostOrderTraversal = new ConstructBinaryTreeFromInorderAndPostOrderTraversal();

        TreeNode root = constructBinaryTreeFromInorderAndPostOrderTraversal.buildTree(inorder, postorder);
        log.debug("Construct Binary Tree From Inorder and Postorder Traversal: {}", () -> root);

    }

    /**
     * Luke - Recursion
     * 
     * Runtime: 3 ms, faster than 85.95% of Java online submissions for Construct Binary Tree from Inorder and Postorder Traversal.
     * Memory Usage: 44.3 MB, less than 52.47% of Java online submissions for Construct Binary Tree from Inorder and Postorder Traversal.
     * 
     * Time: O(n)
     * Space: O(n) - Store the map, and recursion stack.
     */
    public TreeNode buildTree(int[] inorder, int[] postorder) {
        if (inorder.length != postorder.length || inorder.length == 0) {
            return null;
        }

        Map<Integer, Integer> inorderValueToIndexMap = new HashMap<>();
        for (int i = 0; i < inorder.length; i++) {
            inorderValueToIndexMap.put(inorder[i], i);
        }

        idxPostorder = postorder.length - 1;

        return buildTreeRecursion(inorder, postorder, 0, inorder.length - 1, inorderValueToIndexMap);
    }

    int idxPostorder = 0;

    private TreeNode buildTreeRecursion(
            int[] inorder,
            int[] postorder,
            int startInorder,
            int endInorder,
            Map<Integer, Integer> inorderValueToIndexMap) {

        if (startInorder > endInorder) {
            return null;
        }

        int val = postorder[idxPostorder--];
        int mid = inorderValueToIndexMap.get(val);

        TreeNode root = new TreeNode(val);
        /**
         * For Postorder, build right subtree first, then build left subtree. This is opposite to Preorder.
         */
        root.right = buildTreeRecursion(inorder, postorder, mid + 1, endInorder, inorderValueToIndexMap);
        root.left = buildTreeRecursion(inorder, postorder, startInorder, mid - 1, inorderValueToIndexMap);

        return root;
    }
}
