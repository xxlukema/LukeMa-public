package com.learn.tree;


import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.junit.jupiter.api.Assertions;

import lombok.extern.log4j.Log4j2;


/**
 * LC - 285 - Inorder Successor in BST
 *
 * Medium
 *
 * Given the root of a binary search tree and a node p in it, return the in-order successor of that node in the BST. If the given node has no in-order
 * successor in the tree, return null.
 *
 * The successor of a node p is the node with the smallest key greater than p.val.
 *
 * Example 1:
 * Input: root = [2,1,3], p = 1
 * Output: 2
 * Explanation: 1's in-order successor node is 2. Note that both p and the return value is of TreeNode type.
 *
 * Example 2:
 * Input: root = [5,3,6,2,4,null,null,1], p = 6
 * Output: null
 * Explanation: There is no in-order successor of the current node, so the answer is null.
 *
 * Constraints:
 *     The number of nodes in the tree is in the range [1, 104].
 *     -105 <= Node.val <= 105
 *     All Nodes will have unique values.
 */
@Log4j2
public class InorderSuccessorInBst {

    public static void main(String[] args) {

        // final Integer[] nums = { 5, 3, 6, 2, 4, null, null, 1 };
        // final Integer p = 4;

        // final Integer[] nums = { 2, null, 3 };
        // final Integer p = 2;

        final Integer[] nums = { 6, 2, 8, 0, 4, 7, 9, null, null, 3, 5 };
        final Integer p = 2;

        final TreeNode root = TreeNode.toTreeBfsWithNullIntegers(nums);

        InorderSuccessorInBst inorderSuccessorInBst = new InorderSuccessorInBst();

        TreeNode inorderSuccessorBruteDfs = inorderSuccessorInBst.inorderSuccessorBruteDfs(root, new TreeNode(p));
        log.debug("Inorder Successor in BSF: {}", () -> inorderSuccessorBruteDfs == null ? null : inorderSuccessorBruteDfs.val);
        log.debug("Inorder Successor in BSF {} OK", () -> "inorderSuccessorBruteDfs");

        /*
        TreeNode inorderSuccessorBruteDfsEarlyTerminate = inorderSuccessorInBst.inorderSuccessorBruteDfsEarlyTerminate(root, new TreeNode(p));
        Assertions.assertEquals(inorderSuccessorBruteDfs == null ? null : inorderSuccessorBruteDfs.val,
                inorderSuccessorBruteDfsEarlyTerminate == null ? null : inorderSuccessorBruteDfsEarlyTerminate.val);
        log.debug("Inorder Successor in BSF {} OK", () -> "inorderSuccessorBruteDfsEarlyTerminate");
        */

        /*
        TreeNode inorderSuccessorBruteDfsIterative = inorderSuccessorInBst.inorderSuccessorBruteDfsIterative(root, new TreeNode(p));
        Assertions.assertEquals(inorderSuccessorBruteDfs == null ? null : inorderSuccessorBruteDfs.val,
                inorderSuccessorBruteDfsIterative == null ? null : inorderSuccessorBruteDfsIterative.val);
        log.debug("Inorder Successor in BSF {} OK", () -> "inorderSuccessorBruteDfsIterative");
        */

        TreeNode inorderSuccessorBruteDfsIterativeImproved = inorderSuccessorInBst.inorderSuccessorBruteDfsIterativeImproved(root,
                new TreeNode(p));
        Assertions.assertEquals(inorderSuccessorBruteDfs == null ? null : inorderSuccessorBruteDfs.val,
                inorderSuccessorBruteDfsIterativeImproved == null ? null : inorderSuccessorBruteDfsIterativeImproved.val);
        log.debug("Inorder Successor in BSF {} OK", () -> "inorderSuccessorBruteDfsIterativeImproved");

        TreeNode inorderSuccessorLuke = inorderSuccessorInBst.inorderSuccessorLuke(root,
                new TreeNode(p));
        Assertions.assertEquals(inorderSuccessorBruteDfs == null ? null : inorderSuccessorBruteDfs.val,
                inorderSuccessorLuke == null ? null : inorderSuccessorLuke.val);
        log.debug("Inorder Successor in BSF {} OK", () -> "inorderSuccessorLuke");

    }

    /**
     * 2022-11-06 5:49 PM. End 2022-11-07 1:11 AM Time:
     *
     * Luke - Iterative
     *
     * Runtime: 8 ms Beats 37.52%
     * Memory: 49.4 MB Beats 27.32%
     *
     * Time: O(N)
     * Space: O(log(N))
     */
    public TreeNode inorderSuccessorLuke(TreeNode root, TreeNode p) {
        if (root == null || p == null) {
            return null;
        }

        /**
         * parent
         * or
         * smallest of right
         */
        TreeNode parent = null;

        while (true) {
            if (p.val == root.val) {
                /**
                 * parent
                 * or
                 * smallest of right
                 */
                if (root.right == null) {
                    return parent;
                } else {
                    root = root.right;
                    while (root.left != null) {
                        root = root.left;
                    }

                    return root;
                }
            } else {
                if (p.val < root.val) {
                    parent = root;
                    root = root.left;
                } else {
                    root = root.right;
                }
            }
        }
    }

    /**
     * LC - BST
     */
    public TreeNode inorderSuccessorBruteDfsIterativeImproved(TreeNode root, TreeNode p) {
        if (root == null || p == null) {
            return null;
        }

        TreeNode candidate = null;

        while (root != null) {

            if (p.val >= root.val) {
                root = root.right;
            } else {
                candidate = root;
                root = root.left;
            }
        }

        return candidate;
    }

    public TreeNode inorderSuccessorBruteDfsIterative(TreeNode root, TreeNode p) {
        if (root == null || p == null) {
            return null;
        }

        List<TreeNode> list = new ArrayList<>();
        // List<Integer> list2 = new ArrayList<>();

        /**
         * Inorder traversal
         */
        Stack<TreeNode> stack = new Stack<>();

        while (!stack.isEmpty() || root != null) {
            while (root != null) {
                stack.push(root);
                /**
                 * preorder
                 */
                // list.add(root);
                // list2.add(root.val);
                root = root.left;
            }

            root = stack.pop();
            /**
             * Inorder
             */
            list.add(root);
            // list2.add(root.val);
            root = root.right;
        }

        // log.debug("List2: {}", list2);

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).val == p.val) {
                if (i + 1 < list.size()) {
                    return list.get(i + 1);
                }
            }
        }

        return null;
    }

    /**
     * 2022-11-06 5:49 PM. End 6:44 PM. Time: 55 minutes
     * Luke - Brute DFS
     *
     * Runtime: 14 ms Beats 5.63%
     * Memory: 50.2 MB Beats 5.4%
     *
     * Time: O(N)
     * Space: O(log(N))
     */
    public TreeNode inorderSuccessorBruteDfs(TreeNode root, TreeNode p) {
        if (root == null || p == null) {
            return null;
        }

        List<TreeNode> list = new ArrayList<>();

        /**
         * Inorder traversal
         */
        dfsLukeBrute(root, p, list);

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).val == p.val) {
                if (i + 1 < list.size()) {
                    return list.get(i + 1);
                }
            }
        }

        return null;
    }

    private void dfsLukeBrute(TreeNode root, TreeNode p, List<TreeNode> list) {
        if (root == null) {
            return;
        }

        if (root.left != null) {
            dfsLukeBrute(root.left, p, list);
        }
        list.add(root);
        if (root.right != null) {
            dfsLukeBrute(root.right, p, list);
        }
    }

    /**
     * Luke - DFS Iterative
     *
     * Runtime: 14 ms Beats 5.63%
     * Memory: 50.2 MB Beats 5.4%
     *
     * Time: O(N)
     * Space: O(log(N))
     */
    public TreeNode inorderSuccessorBruteDfsEarlyTerminate(TreeNode root, TreeNode p) {
        if (root == null || p == null) {
            return null;
        }

        List<TreeNode> list = new ArrayList<>();

        /**
         * Inorder traversal
         */
        dfsLukeBruteEarlyTerminate(root, p, list);

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).val == p.val) {
                if (i + 1 < list.size()) {
                    return list.get(i + 1);
                }
            }
        }

        return null;
    }

    private void dfsLukeBruteEarlyTerminate(TreeNode root, TreeNode p, List<TreeNode> list) {
        if (root == null) {
            return;
        }

        if (root.left != null) {
            dfsLukeBruteEarlyTerminate(root.left, p, list);
        }
        if (root.val >= p.val) {
            list.add(root);
        }
        if (root.right != null) {
            dfsLukeBruteEarlyTerminate(root.right, p, list);
        }

    }
}
