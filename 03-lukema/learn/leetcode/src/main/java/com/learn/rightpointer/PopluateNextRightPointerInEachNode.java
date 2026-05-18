package com.learn.rightpointer;


import java.util.LinkedList;
import java.util.Queue;

import org.junit.jupiter.api.Assertions;

import lombok.extern.log4j.Log4j2;


/**
 * LC - 116 - Populate Next Right Pointer In Each Node - Perfect Tree
 * 
 * Medium
 */
@Log4j2
public class PopluateNextRightPointerInEachNode {

    public static void main(String[] args) {

        int[] nums = { 1, 2, 3, 4, 5, 6, 7 };

        Node root = Node.toTree(nums);

        log.debug("root: {}", root);

        PopluateNextRightPointerInEachNode popluateNextRightPointerInEachNode = new PopluateNextRightPointerInEachNode();

        Node WithQueue = popluateNextRightPointerInEachNode.connectBfsWithQueue(root);
        log.debug("Populate Next Right Pointer: {}", () -> WithQueue);

        root = Node.toTree(nums);
        Node retNoQueue = popluateNextRightPointerInEachNode.connectLcNoQueue(root);
        Assertions.assertEquals(WithQueue.toString(), retNoQueue.toString());

        log.debug(() -> "Populate Next Right Pointer Pass");

    }

    /**
     * LC - Iterative. No Recursion. No Queue.
     * 
     * Runtime: 1 ms, faster than 69.33% of Java online submissions for Populating Next Right Pointers in Each Node.
     * Memory Usage: 47.6 MB, less than 44.21% of Java online submissions for Populating Next Right Pointers in Each Node.
     * 
     * Time: O(N)
     * Space: O(1)
     */
    public Node connectLcNoQueue(Node root) {

        if (root == null) {
            return root;
        }

        // Start with the root node. There are no next pointers
        // that need to be set up on the first level
        Node leftmost = root;

        // Once we reach the final level, we are done
        while (leftmost.left != null) {

            // Iterate the "linked list" starting from the head
            // node and using the next pointers, establish the 
            // corresponding links for the next level
            Node head = leftmost;

            while (head != null) {

                // CONNECTION 1
                head.left.next = head.right;

                // CONNECTION 2
                if (head.next != null) {
                    /**
                     * Perfect tree. So if left exists, right also exists.
                     */
                    head.right.next = head.next.left;
                }

                // Progress along the list (nodes on the current level)
                head = head.next;
            }

            // Move onto the next level
            leftmost = leftmost.left;
        }

        return root;
    }

    /**
     * Luke - BFS - With Queue
     * 
     * Runtime: 6 ms, faster than 11.85% of Java online submissions for Populating Next Right Pointers in Each Node.
     * Memory Usage: 47.7 MB, less than 31.92% of Java online submissions for Populating Next Right Pointers in Each Node.
     * 
     * Time: O(N)
     * Space: O(N) - Queue size.
     */
    public Node connectBfsWithQueue(Node root) {
        if (root == null) {
            return root;
        }

        Queue<Node> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            int len = queue.size();

            for (int i = 0; i < len; i++) {
                Node node = queue.poll();

                if (i < len - 1) {
                    node.next = queue.peek();
                }

                /**
                 * Leaves
                 */
                if (node.left != null) {
                    queue.add(node.left);
                }
                if (node.right != null) {
                    queue.add(node.right);
                }
            }
        }

        return root;
    }
}
