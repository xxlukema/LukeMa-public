package com.learn.amzn25;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import org.junit.jupiter.api.Assertions;

import com.learn.tree.TreeNode;

import lombok.extern.log4j.Log4j2;


/**
 * 1676. Lowest Common Ancestor of a Binary Tree IV
 *
 * Medium
 *
 * Given the root of a binary tree and an array of TreeNode objects nodes, return the lowest common ancestor (LCA) of all the nodes in nodes.
 * All the nodes will exist in the tree, and all values of the tree's nodes are unique.
 *
 * Extending the definition of LCA on Wikipedia: "The lowest common ancestor of n nodes p1, p2, ..., pn in a binary tree T is the lowest node
 * that has every pi as a descendant (where we allow a node to be a descendant of itself) for every valid i". A descendant of a node x is a node
 * y that is on the path from node x to some leaf node.
 *
 * Example 1:
 *
 * Input: root = [3,5,1,6,2,0,8,null,null,7,4], nodes = [4,7]
 * Output: 2
 * Explanation: The lowest common ancestor of nodes 4 and 7 is node 2.
 *
 * Example 2:
 *
 * Input: root = [3,5,1,6,2,0,8,null,null,7,4], nodes = [1]
 * Output: 1
 * Explanation: The lowest common ancestor of a single node is the node itself.
 *
 * Example 3:
 *
 * Input: root = [3,5,1,6,2,0,8,null,null,7,4], nodes = [7,6,2,4]
 * Output: 5
 * Explanation: The lowest common ancestor of the nodes 7, 6, 2, and 4 is node 5.
 *
 * Constraints:
 *
 *     The number of nodes in the tree is in the range [1, 10 ^ 4].
 *     -10 ^ 9 <= Node.val <= 10 ^ 9
 *     All Node.val are unique.
 *     All nodes[i] will exist in the tree.
 *     All nodes[i] are distinct.
 */
@Log4j2
public class LowestCommonAncestorOfABinaryTreeVIII {

    public static void main(String[] args) {
        LowestCommonAncestorOfABinaryTreeVIII lowestCommonAncestorOfABinaryTreeVIII = new LowestCommonAncestorOfABinaryTreeVIII();

        /*
        Integer[] bTree = { 3, 5, 1, 6, 2, 0, 8, null, null, 7, 4 };
        int[] iNodes = { 7, 6, 2, 4 };
        int expected = 5;
        */

        Integer[] bTree = { 12795, 1982, null, 3798, null, 430, null, 5481, null, 15224, null, 12970, null, 18652, null, 5137, null, 13230, null,
                8433, null, 19989, null, 6921 };
        int[] iNodes = { 5481, 13230, 18652 };
        int expected = 5481;

        TreeNode root = TreeNode.toTreeBfsWithNullIntegers(bTree);
        TreeNode[] nodes = new TreeNode[iNodes.length];
        for (int i = 0; i < iNodes.length; i++) {
            nodes[i] = new TreeNode(iNodes[i]);

        }

        var ret = lowestCommonAncestorOfABinaryTreeVIII.lowestCommonAncestorLuke(root, nodes);

        var title = "Lowest Common Ancestor of a Binary Tree IV";

        log.info("{}: {}", () -> title, () -> ret.val);
        Assertions.assertEquals(expected, ret.val);
        log.debug("{} {} OK", () -> title, () -> "lowestCommonAncestorLuke");

        var retLc = lowestCommonAncestorOfABinaryTreeVIII.lowestCommonAncestorLc(root, nodes);

        log.info("{}: {}", () -> title, () -> retLc.val);
        Assertions.assertEquals(expected, retLc.val);
        log.debug("{} {} OK", () -> title, () -> "lowestCommonAncestorLc");
    }

    /**
     * LC - Use Set.conatins()
     *
     * Runtime: 5ms Beats36.65%of users with Java
     * Memory: 47.94MB Beats6.38%of users with Java
     *
     * Time: O(nodes.length + tree.size())
     * Space: O(nodes.length)
     */
    private TreeNode lca = null;

    public TreeNode lowestCommonAncestorLc(TreeNode root, TreeNode[] nodes) {
        Set<Integer> targetNodes = new HashSet<>();
        for (TreeNode node : nodes) {
            targetNodes.add(node.val);
        }

        helper(root, targetNodes);

        return lca;
    }

    private int helper(TreeNode root, Set<Integer> targetNodes) {
        if (root == null) {
            return 0;
        }

        int leftCount = helper(root.left, targetNodes);
        int rightCount = helper(root.right, targetNodes);
        int count = leftCount + rightCount;

        if (targetNodes.contains(root.val)) {
            count++;
        }

        if (count == targetNodes.size() && lca == null) {
            lca = root;
        }

        return count;
    }

    /**
     * Luke - Time Limit Exceeded
     *
     * Time: O(tree.size * nodes.length)
     * Space: O(tree.height)
     */
    public TreeNode lowestCommonAncestorLuke(TreeNode root, TreeNode[] nodes) {
        if (nodes.length == 1) {
            return nodes[0];
        }

        final Map<Integer, Integer> visitedCountMap = new HashMap<>();

        final Stack<TreeNode> parents = new Stack<>();

        for (TreeNode node : nodes) {
            isContain(root, node, visitedCountMap, parents);
            // log.debug("node: {}, map: {}, parents: {}", () -> node.val, () -> visitedCountMap, () -> parents);
        }

        // BFS
        TreeNode curr = root;
        while (true) {
            if (!visitedCountMap.containsKey(curr.val)) {
                return curr;
            }

            int count = visitedCountMap.get(curr.val);
            if (curr.left != null) {
                if (visitedCountMap.containsKey(curr.left.val) && visitedCountMap.get(curr.left.val) == count) {
                    curr = curr.left;
                    continue;
                }
            }

            if (curr.right != null) {
                if (visitedCountMap.containsKey(curr.right.val) && visitedCountMap.get(curr.right.val) == count) {
                    curr = curr.right;
                    continue;
                }
            }

            return curr;
        }
    }

    /**
     * Time: O(tree.size)
     * Space: O(tree.height)
     */
    private boolean isContain(TreeNode root, TreeNode node, Map<Integer, Integer> visitedCountMap, Stack<TreeNode> visiting) {
        visiting.add(root);

        boolean found = root.val.equals(node.val);

        if (!found && root.left != null) {
            found = isContain(root.left, node, visitedCountMap, visiting);
        }

        if (!found && root.right != null) {
            found = isContain(root.right, node, visitedCountMap, visiting);
        }

        TreeNode curr = visiting.pop();

        if (found) {
            if (visitedCountMap.containsKey(curr.val)) {
                visitedCountMap.put(curr.val, visitedCountMap.get(curr.val) + 1);
            } else {
                visitedCountMap.put(curr.val, 1);
            }
        }

        return found;
    }
}
