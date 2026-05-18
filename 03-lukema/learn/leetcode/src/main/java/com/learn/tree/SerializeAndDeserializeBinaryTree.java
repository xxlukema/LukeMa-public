package com.learn.tree;


import java.util.LinkedList;
import java.util.List;


/**
 * LC - 297 - Serialize And Deserialize Binary Tree
 *
 * Hard
 *
 * Serialization is the process of converting a data structure or object into a sequence of bits so that it can be stored in a file
 * or memory buffer, or transmitted across a network connection link to be reconstructed later in the same or another computer environment.
 *
 * Design an algorithm to serialize and deserialize a binary tree. There is no restriction on how your serialization/deserialization algorithm
 * should work. You just need to ensure that a binary tree can be serialized to a string and this string can be deserialized to the original
 * tree structure.
 *
 * Clarification: The input/output format is the same as how LeetCode serializes a binary tree. You do not necessarily need to follow this format,
 * so please be creative and come up with different approaches yourself.
 *
 * Example 1:
 * Input: root = [1,2,3,null,null,4,5]
 * Output: [1,2,3,null,null,4,5]
 *
 * Example 2:
 * Input: root = []
 * Output: []
 *
 * Constraints:
 *     The number of nodes in the tree is in the range [0, 104].
 *     -1000 <= Node.val <= 1000
 */
public class SerializeAndDeserializeBinaryTree {

    public static void main(String[] args) {

    }
}


/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class Codec {

    /**
     * TODO: Move to top props declare section
     */
    final String NULL = "null";

    /**
     * DFS - preorder
     *
     * Runtime: 41 ms Beats 41.56%
     * Memory: 52.5 MB Beats 51.18%
     *
     * Time: O(N)
     * Space: O(N)
     */
    public String serialize(TreeNode root) {
        if (root == null) {
            return null;
        }

        final StringBuilder sb = new StringBuilder();
        serializeDfs(root, sb);

        return sb.toString();
    }

    private void serializeDfs(final TreeNode root, final StringBuilder sb) {
        if (root == null) {
            sb.append(NULL).append(",");
            return;
        }

        sb.append(root.val).append(",");
        serializeDfs(root.left, sb);
        serializeDfs(root.right, sb);
    }

    /**
     * DFS - preorder - deserialize
     *
     * Runtime: 41 ms Beats 41.56%
     * Memory: 52.5 MB Beats 51.18%
     *
     * Time: O(N)
     * Space: O(N)
     */
    public TreeNode deserialize(String data) {
        if (data == null) {
            return null;
        }

        String[] fields = data.split(",");
        LinkedList<String> llist = new LinkedList<>(List.of(fields));

        return deserializeDfs(llist);
    }

    private TreeNode deserializeDfs(final LinkedList<String> llist) {
        if (llist.isEmpty()) {
            return null;
        }

        Integer val = toInteger(llist.removeFirst());
        if (val == null) {
            return null;
        }

        TreeNode root = new TreeNode(val);

        root.left = deserializeDfs(llist);
        root.right = deserializeDfs(llist);

        return root;
    }

    private Integer toInteger(String val) {
        if (val.equals(NULL)) {
            return null;
        } else {
            return Integer.valueOf(val);
        }
    }

    /**
     * The following code does not work.
     */

    /**
     * BFS --- Not working
     */
    // Encodes a tree to a single string.
    public String serializeBfs(TreeNode root) {
        if (root == null) {
            return null;
        }

        /**
         * BFS
         *
         * Do NOT use Queue, because Queue cannot contain null. Use Linkedlist
         */
        LinkedList<TreeNode> llist = new LinkedList<>();
        llist.add(root);

        StringBuilder sb = new StringBuilder();

        while (!llist.isEmpty()) {
            int size = llist.size();
            for (int i = 0; i < size; i++) {
                TreeNode cur = llist.removeFirst();
                if (cur == null) {
                    sb.append("null").append(",");
                } else {
                    sb.append(cur.val).append(",");
                    llist.add(cur.left);
                    llist.add(cur.right);
                }
            }
        }

        return sb.toString();
    }

    /**
     * BFS --- Not working
     */
    // Decodes your encoded data to tree.
    public TreeNode deserializeBfs(String data) {
        if (data == null) {
            return null;
        }

        final String[] fields = data.split(",");
        if (data.isEmpty() || fields.length == 0) {
            return null;
        }

        Integer first = toInteger(fields[0]);

        if (first == null) {
            return null;
        }

        /**
         * BSF
         *
         * Do NOT use Queue, because Queue cannot contain null. Use Linkedlist
         */
        TreeNode root = new TreeNode(first);
        LinkedList<TreeNode> llist = new LinkedList<>();
        llist.add(root);

        int i = 1;
        while (i < fields.length && !llist.isEmpty()) {
            int size = llist.size();
            for (int q = 0; q < size; q++) {

                String field = fields[i++];
                TreeNode cur = llist.removeFirst();
                if (cur == null) {
                    continue;
                }

                Integer val = toInteger(field);

                if (val != null) {
                    cur.left = new TreeNode(val);
                }
                if (cur.left != null) {
                    llist.add(cur.left);
                }

                llist.add(cur.left);

                if (i < fields.length) {
                    field = fields[i++];
                    val = toInteger(field);
                    if (val != null) {
                        cur.right = new TreeNode(val);
                    }
                    cur.right = cur.right;
                }
                llist.add(cur.right);
            }
        }

        return root;
    }

}

// Your Codec object will be instantiated and called as such:
// Codec ser = new Codec();
// Codec deser = new Codec();
// TreeNode ans = deser.deserialize(ser.serialize(root));
