package com.learn.rightpointer;


import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import lombok.ToString;


@ToString
public class Node {
    public int val;
    public Node left;
    public Node right;
    public Node next;

    public Node() {
    }

    public Node(int val) {
        this.val = val;
    }

    public Node(int val, Node left, Node right, Node next) {
        this.val = val;
        this.left = left;
        this.right = right;
        this.next = next;
    }

    public static Node toTree(int[] nums) {
        if (nums == null || nums.length == 0) {
            return null;
        }

        Node[] nodes = new Node[nums.length];

        for (int i = 0; i <= nums.length / 2; i++) {
            Node root = nodes[i];
            if (root == null) {
                root = new Node(nums[i]);
                nodes[i] = root;
            }

            int idxLeft = 2 * i + 1;
            int idxRight = 2 * i + 2;

            if (idxLeft < nums.length) {
                Node left = new Node(nums[idxLeft]);
                root.left = left;
                nodes[idxLeft] = left;
            }

            if (idxRight < nums.length) {
                Node right = new Node(nums[idxRight]);
                root.right = right;
                nodes[idxRight] = right;
            }
        }
        return nodes[0];
    }

    public static Node toTreeSubrootNoNull(Integer[] nums) {
        if (nums == null || nums.length == 0) {
            return null;
        }

        Node[] nodes = new Node[nums.length];

        for (int i = 0; i <= nums.length / 2; i++) {
            Node root = nodes[i];
            if (root == null) {
                root = new Node(nums[i]);
                nodes[i] = root;
            }

            int idxLeft = 2 * i + 1;
            int idxRight = 2 * i + 2;

            if (idxLeft < nums.length && nums[idxLeft] != null) {
                Node left = new Node(nums[idxLeft]);
                root.left = left;
                nodes[idxLeft] = left;
            }

            if (idxRight < nums.length && nums[idxRight] != null) {
                Node right = new Node(nums[idxRight]);
                root.right = right;
                nodes[idxRight] = right;
            }
        }
        return nodes[0];
    }

    public static Node toTreeBfsWithNullIntegers(final Integer[] nums) {
        if (nums == null || nums[0] == null) {
            return null;
        }

        Node root = new Node(nums[0]);
        Queue<Node> queue = new ConcurrentLinkedQueue<>();
        queue.add(root);

        int idx = 1;
        while (idx < nums.length) {
            Node curr = queue.poll();

            if (nums[idx] != null) {
                Node node = new Node(nums[idx]);
                curr.left = node;
                queue.add(curr.left);
            }

            idx++;

            if (idx < nums.length && nums[idx] != null) {
                Node node = new Node(nums[idx]);
                curr.right = node;
                queue.add(curr.right);
            }

            idx++;
        }

        return root;
    }

}
