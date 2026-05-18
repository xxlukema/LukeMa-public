package com.learn.other;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import lombok.extern.log4j.Log4j2;


/**
 * LC - 138 - Copy List With Random Pointer
 *
 * Medium
 *
 * A linked list of length n is given such that each node contains an additional random pointer, which could point to any node in the list, or null.
 * Construct a deep copy of the list. The deep copy should consist of exactly n brand new nodes, where each new node has its value set to the value
 * of its corresponding original node. Both the next and random pointer of the new nodes should point to new nodes in the copied list such that the
 * pointers in the original list and copied list represent the same list state. None of the pointers in the new list should point to nodes in the
 * original list.
 *
 * For example, if there are two nodes X and Y in the original list, where X.random --> Y, then for the corresponding two nodes x and y in the copied
 * list, x.random --> y.
 *
 * Return the head of the copied linked list.
 *
 * The linked list is represented in the input/output as a list of n nodes. Each node is represented as a pair of [val, random_index] where:
 *
 *     val: an integer representing Node.val
 *     random_index: the index of the node (range from 0 to n-1) that the random pointer points to, or null if it does not point to any node.
 *
 * Your code will only be given the head of the original linked list.
 *
 * Example 1:
 * Input: head = [[7,null],[13,0],[11,4],[10,2],[1,0]]
 * Output: [[7,null],[13,0],[11,4],[10,2],[1,0]]
 *
 * Example 2:
 * Input: head = [[1,1],[2,1]]
 * Output: [[1,1],[2,1]]
 *
 * Example 3:
 * Input: head = [[3,null],[3,0],[3,null]]
 * Output: [[3,null],[3,0],[3,null]]
 *
 * Constraints:
 *     0 <= n <= 1000
 *     -104 <= Node.val <= 104
 *     Node.random is null or is pointing to some node in the linked list.
 *
 */
@Log4j2
public class CopyListWithRandomPointer {

    public static void main(String[] args) {

        final Integer[][] nums = { { 7, null }, { 13, 0 }, { 11, 4 }, { 10, 2 }, { 1, 0 } };

        Node head = Node.toList(nums);

        log.debug("head: {}", () -> head.toString());

        CopyListWithRandomPointer copyListWithRandomPointer = new CopyListWithRandomPointer();

        Node ret = copyListWithRandomPointer.copyRandomListLukeIterativeMap(head);
        log.debug("List: {}", () -> ret.toString());

    }

    /**
     * Luke - Avoid cyclic search using "Set<Node> visited = new HashSet<>();"
     *
     * Runtime: 1 ms, faster than 44.38% of Java online submissions for Copy List with Random Pointer.
     * Memory Usage: 42.1 MB, less than 86.66% of Java online submissions for Copy List with Random Pointer.
     *
     * Time: O(N)
     * Space: O(N) - The "visited" Map size.
     */
    public Node copyRandomListLukeIterativeMap(Node head) {
        if (head == null) {
            return null;
        }

        /**
         * Original node reference is the key. Cloned node reference is the value.
         */
        final Map<Node, Node> visited = new ConcurrentHashMap<>();

        Node curr = head;
        Node newHead = clone(head, visited);
        Node newCurr = newHead;

        while (curr != null) {
            if (curr.random != null) {
                newCurr.random = visited.get(curr.random);
            }
            curr = curr.next;
            newCurr = newCurr.next;
        }

        return newHead;
    }

    Node clone(Node node, final Map<Node, Node> visited) {
        /**
         * `HashMap.computeIfAbsent` throws `ConcurrentModificationException` --- fail fast
         * `ConcurrentHashMap.computeIfAbsent` throws `IllegalStateException`
         */
        /*
        return visited.computeIfAbsent(node, key -> {
            Node newNode = new Node(key.val);
            if (key.next != null) {
                newNode.next = clone(key.next, visited);
            }
            return newNode;
        });
        */

        /**
         * Use following code if use `HashMap`:
         *
         * Original node reference is the key. Cloned node reference is the value.
         */
        if (!visited.containsKey(node)) {
            Node newNode = new Node(node.val);

            visited.put(node, newNode);

            if (node.next != null) {
                newNode.next = clone(node.next, visited);
            }
            return newNode;
        }

        return visited.get(node);
    }
}


class Node {
    int val;
    Node next;
    Node random;

    public Node(int val) {
        this.val = val;
        this.next = null;
        this.random = null;
    }

    public static Node toList(final Integer[][] nums) {
        if (nums == null) {
            return null;
        }

        Node head = null;
        Node pre = null;

        List<Node> list = new ArrayList<>();

        for (int i = 0; i < nums.length; i++) {
            Node curr = new Node(nums[i][0]);
            list.add(curr);

            if (head == null) {
                head = curr;
            }

            if (pre != null) {
                pre.next = curr;
            }

            pre = curr;
        }

        Node curr = head;

        for (int i = 0; i < nums.length && curr != null; i++) {
            if (nums[i][1] != null) {
                curr.random = list.get(nums[i][1]);
            }
            curr = curr.next;
        }

        return head;
    }

    @Override
    public String toString() {
        return String.format("val: %d, random: %d, next: %s", val, random == null ? -1 : random.val, next == null ? -1 : next.toString());
    }
}
