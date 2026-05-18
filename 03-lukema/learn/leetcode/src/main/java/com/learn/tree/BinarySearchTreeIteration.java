package com.learn.tree;


import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.junit.jupiter.api.Assertions;


/**
 * LC - 173 - Binary Search Tree Iteration
 * 
 * Medium
 * 
 * Implement the BSTIterator class that represents an iterator over the in-order traversal of a binary search tree (BST):
 * 
 *  (1) BSTIterator(TreeNode root) Initializes an object of the BSTIterator class. The root of the BST is given as part of the constructor. 
 *      The pointer should be initialized to a non-existent number smaller than any element in the BST.
 *  (2) boolean hasNext() Returns true if there exists a number in the traversal to the right of the pointer, otherwise returns false.
 *  (3) int next() Moves the pointer to the right, then returns the number at the pointer.
 * 
 * Notice that by initializing the pointer to a non-existent smallest number, the first call to next() will return the smallest element in the BST.
 * 
 * You may assume that next() calls will always be valid. That is, there will be at least a next number in the in-order traversal when next() is called.
 * 
 * Example 1:
 * Input
 * ["BSTIterator", "next", "next", "hasNext", "next", "hasNext", "next", "hasNext", "next", "hasNext"]
 * [[[7, 3, 15, null, null, 9, 20]], [], [], [], [], [], [], [], [], []]
 * Output
 * [null, 3, 7, true, 9, true, 15, true, 20, false]
 * 
 * Explanation
 * BSTIterator bSTIterator = new BSTIterator([7, 3, 15, null, null, 9, 20]);
 * bSTIterator.next();    // return 3
 * bSTIterator.next();    // return 7
 * bSTIterator.hasNext(); // return True
 * bSTIterator.next();    // return 9
 * bSTIterator.hasNext(); // return True
 * bSTIterator.next();    // return 15
 * bSTIterator.hasNext(); // return True
 * bSTIterator.next();    // return 20
 * bSTIterator.hasNext(); // return False
 * 
 * Constraints:
 *     The number of nodes in the tree is in the range [1, 105].
 *     0 <= Node.val <= 106
 *     At most 105 calls will be made to hasNext, and next.
 * 
 * Follow up:
 *     Could you implement next() and hasNext() to run in average O(1) time and use O(h) memory, where h is the height of the tree?
 */
public class BinarySearchTreeIteration {

    public static void main(String[] args) {

        final Integer[] nums = { 7, 3, 15, null, null, 9, 20 };

        final TreeNode root = TreeNode.toTreeBfsWithNullIntegers(nums);

        BSTIterator bSTIterator = new BSTIterator(root);

        Assertions.assertEquals(3, bSTIterator.next()); // return 3
        Assertions.assertEquals(7, bSTIterator.next()); // return 7
        Assertions.assertTrue(bSTIterator.hasNext()); // return True
        Assertions.assertEquals(9, bSTIterator.next()); // return 9
        Assertions.assertTrue(bSTIterator.hasNext()); // return True
        Assertions.assertEquals(15, bSTIterator.next()); // return 15
        Assertions.assertTrue(bSTIterator.hasNext()); // return True
        Assertions.assertEquals(20, bSTIterator.next()); // return 20
        Assertions.assertFalse(bSTIterator.hasNext()); // return False

    }
}


/**
 * Luke - Iterative - Preorder Traversal - With List
 * 
 * Runtime: 29 ms, faster than 37.79% of Java online submissions for Binary Search Tree Iterator.
 * Memory Usage: 52 MB, less than 41.55% of Java online submissions for Binary Search Tree Iterator.
 * 
 * Time: Constructor O(N), next O(1), hasNext: O(1)
 * Space: O(N) for List size and Stack size
 */
class BSTIterator {

    TreeNode it;

    int pos = 0;

    List<TreeNode> list;

    public BSTIterator(TreeNode root) {

        it = new TreeNode(-1);
        list = new ArrayList<>();

        Stack<TreeNode> stack = new Stack<>();

        while (root != null || !stack.isEmpty()) {
            while (root != null) {
                stack.push(root);
                root = root.left;
            }

            root = stack.pop();
            list.add(root);

            root = root.right;
        }
    }

    public int next() {
        return list.get(pos++).val;
    }

    public boolean hasNext() {
        return pos < list.size();
    }
}
