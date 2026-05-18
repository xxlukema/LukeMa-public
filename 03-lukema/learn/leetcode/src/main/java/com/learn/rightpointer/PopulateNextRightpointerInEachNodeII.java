package com.learn.rightpointer;


import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.junit.jupiter.api.Assertions;

import lombok.extern.log4j.Log4j2;


/**
 * LC - 117 - Populate Next Right Pointer In Each Node - Non-Perfect Tree
 * 
 * Medium
 */
@Log4j2
public class PopulateNextRightpointerInEachNodeII {

    public static void main(String[] args) {

        // final Integer[] nums = { 1, 2, 3, 4, 5, null, 7 };
        // final Integer[] nums = { 1, 2, 3, 4, 5, null, 7 };
        // final Integer[] nums = { 3, 9, 20, null, null, 15, 7 };
        // final Integer[] nums = { 1, 2, 3, 4, 5, null, 6, 7, null, null, null, null, 8 };
        final Integer[] nums = { 0, 2, 4, 1, null, 3, -1, 5, 1, null, 6, null, 8 };

        Node root = Node.toTreeBfsWithNullIntegers(nums);

        log.debug("root: {}", root);

        PopulateNextRightpointerInEachNodeII populateNextRightpointerInEachNodeII = new PopulateNextRightpointerInEachNodeII();

        log.debug(() -> "Start Test...");

        Node retTwoQueue = populateNextRightpointerInEachNodeII.connectTwoQueue(root);
        log.debug("next: {}", retTwoQueue);
        log.debug(() -> "Two Queue OK");

        root = Node.toTreeBfsWithNullIntegers(nums);
        Node retLevelTraverse = populateNextRightpointerInEachNodeII.connectLevelTraverse(root);
        Assertions.assertEquals(retTwoQueue.toString(), retLevelTraverse.toString());
        log.debug(() -> "Level Traversal OK");

        root = Node.toTreeBfsWithNullIntegers(nums);
        Node retNoQueue = populateNextRightpointerInEachNodeII.connectNoQueue(root);
        Assertions.assertEquals(retTwoQueue.toString(), retNoQueue.toString());
        log.debug(() -> "No Queue OK");

        log.debug(() -> "Test Complete");
    }

    /**
     * Luke - Level Traversal
     * 
     * Runtime: 2 ms, faster than 50.69% of Java online submissions for Populating Next Right Pointers in Each Node II.
     * Memory Usage: 45.5 MB, less than 17.13% of Java online submissions for Populating Next Right Pointers in Each Node II.
     * 
     * Time: (N)
     * Space: O(N)
     */
    public Node connectLevelTraverse(Node root) {
        if (root == null) {
            return null;
        }

        List<List<Node>> levelNodes = new ArrayList<>();

        connectLevelTraverse(root, 0, levelNodes);

        levelNodes.forEach(level -> {
            for (int i = 0; i < level.size() - 1; i++) {
                level.get(i).next = level.get(i + 1);
            }
        });

        return root;
    }

    private void connectLevelTraverse(Node root, int level, List<List<Node>> levelNodes) {
        if (root == null) {
            return;
        }

        if (level == levelNodes.size()) {
            levelNodes.add(new ArrayList<>());
        }

        /**
         * Top-Down. Either way will work.
         */
        // levelNodes.get(level).add(root);

        connectLevelTraverse(root.left, level + 1, levelNodes);
        connectLevelTraverse(root.right, level + 1, levelNodes);

        /**
         * Bottom-Up. Either way will work.
         */
        levelNodes.get(level).add(root);
    }

    /**
     * Luke - No Queue - Not complete - Logic is very complex
     * 
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Populating Next Right Pointers in Each Node II.
     * Memory Usage: 45.2 MB, less than 36.71% of Java online submissions for Populating Next Right Pointers in Each Node II.
     * 
     * Time: O(N)
     * SpaceL O(1)
     */
    public Node connectNoQueue(Node root) {
        if (root == null) {
            return null;
        }

        Node leftmost = root;

        while (leftmost != null) {
            Node head = leftmost;
            Node preChild = null;
            leftmost = null;
            while (head != null) {

                if (leftmost == null) {
                    /**
                     * leftmost for next level.
                     */
                    leftmost = head.left == null ? head.right : head.left;
                }

                if (preChild == null) {
                    if (head.left != null || head.right != null) {
                        preChild = head.right == null ? head.left : head.right;
                    }
                } else {
                    if (head.left != null || head.right != null) {
                        preChild.next = head.left == null ? head.right : head.left;
                        preChild = head.right == null ? head.left : head.right;
                    }
                }

                if (head.left != null) {
                    head.left.next = head.right;
                }

                head = head.next;
            }
        }

        return root;
    }

    /**
     * Luke - queue and childrenQueue
     * 
     * Runtime: 10 ms, faster than 5.80% of Java online submissions for Populating Next Right Pointers in Each Node II.
     * Memory Usage: 45.7 MB, less than 9.65% of Java online submissions for Populating Next Right Pointers in Each Node II.
     * 
     * Time: O(N)
     * Space: O(N)
     */
    public Node connectTwoQueue(Node root) {
        if (root == null) {
            return null;
        }

        Queue<Node> queue = new ConcurrentLinkedQueue<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            Queue<Node> childrenQueue = new ConcurrentLinkedQueue<>();

            while (!queue.isEmpty()) {
                Node node = queue.poll();
                node.next = queue.peek();

                if (node.left != null) {
                    childrenQueue.add(node.left);
                    node.left.next = node.right;
                }

                if (node.right == null) {
                    if (queue.peek() != null) {
                        if (node.left != null) {
                            node.left.next = queue.peek().left == null ? queue.peek().right : queue.peek().left;
                        }
                    }
                } else {
                    childrenQueue.add(node.right);
                    if (queue.peek() != null) {
                        node.right.next = queue.peek().left == null ? queue.peek().right : queue.peek().left;
                    }
                }
            }

            queue.addAll(childrenQueue);
        }

        return root;
    }
}
