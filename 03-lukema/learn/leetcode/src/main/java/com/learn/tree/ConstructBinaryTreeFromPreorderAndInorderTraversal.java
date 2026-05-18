package com.learn.tree;


import java.util.HashMap;
import java.util.Map;

import lombok.extern.log4j.Log4j2;


/**
 * LC 105
 *
 * Keys to the solution:
 *
 * 1. All values in the nodes are unique.
 * 2. The first element of preorder array is ALWAYS the root of the BST.
 *    The second (sub-)root is the second element in preorder array.
 *    The third (sub-)root is the third element in preorder array.
 * 3. Find the index of the root from inorder.
 *    The left side of ROOT of inorder array is ALWAYS the left leaf,
 *    and the right side of ROOT of the inorder array is the right leaf.
 */
@Log4j2
public class ConstructBinaryTreeFromPreorderAndInorderTraversal {
    public static void main(String[] args) {

        // int[] preorder = { 3, 9, 20, 15, 7 };
        // int[] inorder = { 9, 3, 15, 20, 7 };

        int[] preorder = { 5, 4, 2, 1, 3, 6, 7, 8, 9 };
        int[] inorder = { 1, 2, 4, 3, 5, 6, 7, 8, 9 };

        ConstructBinaryTreeFromPreorderAndInorderTraversal constructBinaryTreeFromPreorderAndInorderTraversal = new ConstructBinaryTreeFromPreorderAndInorderTraversal();
        TreeNode root = constructBinaryTreeFromPreorderAndInorderTraversal.buildTreeLcRecursion(preorder, inorder);
        log.debug("Construct Binary Tree From Preorder and Inorder Traversal: {}", () -> root);
    }

    /**
     * LC - Recursion
     * 
     * Runtime: 5 ms, faster than 40.08% of Java online submissions for Construct Binary Tree from Preorder and Inorder Traversal.
     * Memory Usage: 44.4 MB, less than 47.16% of Java online submissions for Construct Binary Tree from Preorder and Inorder Traversal.
     *
     * Time: O(n)
     * Space: O(n)
     */
    public TreeNode buildTreeLcRecursion(int[] preorder, int[] inorder) {
        if (preorder == null) {
            return null;
        }

        Map<Integer, Integer> inorderValueToIdxMap = new HashMap<>();
        for (int i = 0; i < inorder.length; i++) {
            inorderValueToIdxMap.put(inorder[i], i);
        }

        return buildTreeLcRecursion(preorder, inorder, 0, preorder.length - 1, inorderValueToIdxMap);
    }

    int preorderIdx = 0;

    public TreeNode buildTreeLcRecursion(
            int[] preorder,
            int[] inorder,
            int startIdxInorder,
            int endIdxInorder,
            Map<Integer, Integer> inorderValueToIdxMap) {

        if (startIdxInorder > endIdxInorder) {
            return null;
        }

        /**
         * The first element of preorder array is ALWAYS the root of the BST.
         * The second (sub-)root is the second element in preorder array.
         * The third (sub-)root is the third element in preorder array.
         */
        TreeNode root = new TreeNode(preorder[preorderIdx++]);

        /**
         * Find the index of ROOT from inorder array.
         * Left side of ROOT of inorder array is the left leaf.
         * Right side of ROOT of inorder array is the right leaf.
         */
        int rootIdxInorder = inorderValueToIdxMap.get(root.val);

        /**
         * For Preorder, build left subtree first, then right subtree. This is opposite to Postorder.
         * 
         * Left side of ROOT of inorder array is left leaf.
         */
        root.left = buildTreeLcRecursion(
                preorder, inorder,
                startIdxInorder, rootIdxInorder - 1,
                inorderValueToIdxMap);
        /**
         * Right side of ROOT of inorder array is right leaf.
         */
        root.right = buildTreeLcRecursion(
                preorder, inorder,
                rootIdxInorder + 1, endIdxInorder,
                inorderValueToIdxMap);

        return root;
    }

}
