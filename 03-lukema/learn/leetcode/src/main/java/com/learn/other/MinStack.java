package com.learn.other;


import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import lombok.extern.log4j.Log4j2;


/**
 * LC - 155 - Min Stack
 *
 * Medium
 *
 * Design a stack that supports push, pop, top, and retrieving the minimum element in constant time.
 *
 * Implement the MinStack class:
 *     MinStack() initializes the stack object.
 *     void push(int val) pushes the element val onto the stack.
 *     void pop() removes the element on the top of the stack.
 *     int top() gets the top element of the stack.
 *     int getMin() retrieves the minimum element in the stack.
 *
 * You must implement a solution with O(1) time complexity for each function.
 *
 * Example 1:
 * Input
 * ["MinStack","push","push","push","getMin","pop","top","getMin"]
 * [[],[-2],[0],[-3],[],[],[],[]]
 *
 * Output
 * [null,null,null,null,-3,null,0,-2]
 *
 * Explanation
 * MinStack minStack = new MinStack();
 * minStack.push(-2);
 * minStack.push(0);
 * minStack.push(-3);
 * minStack.getMin(); // return -3
 * minStack.pop();
 * minStack.top();    // return 0
 * minStack.getMin(); // return -2
 *
 * Constraints:
 *     -2 ^ 31 <= val <= 2 ^ 31 - 1
 *     Methods pop, top and getMin operations will always be called on non-empty stacks.
 *     At most 3 * 104 calls will be made to push, pop, top, and getMin.
 */
@Log4j2
public class MinStack {

    public static void main(String[] args) {

        MinStack minStack = new MinStack();

        minStack.push(-2);
        minStack.push(-0);
        minStack.push(-3);

        log.debug(minStack.getMin());

        minStack.pop();

        log.debug(minStack.top());
        log.debug(minStack.getMin());

    }

    /**
     * Time: O(1)
     */

    /**
     * Approach 1: Stack with Value/Min record
     *
     * Runtime: 11 ms, faster than 21.30% of Java online submissions for Min Stack.
     * Memory Usage: 48 MB, less than 81.68% of Java online submissions for Min Stack.
     *
     * Time: O(1)
     * Space: O(N)
     */

    /*
    final Stack<ValueMin> stack;

    public MinStack() {
        stack = new Stack<>();
    }

    public void push(int val) {
        if (stack.isEmpty()) {
            stack.add(new ValueMin(val, val));
        } else {
            if (val < stack.peek().min) {
                stack.add(new ValueMin(val, val));
            } else {
                stack.add(new ValueMin(val, stack.peek().min));
            }
        }
    }

    public void pop() {
        stack.pop();
    }

    public int top() {
        return stack.peek().val;
    }

    public int getMin() {
        return stack.peek().min;
    }

    record ValueMin(int val, int min) {
    }
    */

    /**
     * Approach 2: Two Stacks
     *
     * Runtime: 10 ms, faster than 29.52% of Java online submissions for Min Stack.
     * Memory Usage: 48.3 MB, less than 64.27% of Java online submissions for Min Stack.
     *
     * Time: O(1)
     * Space: O(N)
     */

    /**
     * Trick 1: Use two stacks.
     */
    final Stack<Integer> stack;

    /**
     * Trick 2: Use a List to handle duplicated mins.
     */
    final Stack<List<Integer>> minStack;

    public MinStack() {
        stack = new Stack<>();
        minStack = new Stack<>();
    }

    public void push(int val) {
        if (minStack.isEmpty()) {
            minStack.add(new ArrayList<>());
            minStack.peek().add(val);
        } else {
            if (val < minStack.peek().get(0)) {
                minStack.add(new ArrayList<>());
                minStack.peek().add(val);
            } else if (val == minStack.peek().get(0)) {
                minStack.peek().add(val);
            }
        }
        stack.add(val);
    }

    public void pop() {
        int val = stack.pop();
        if (val == minStack.peek().get(0)) {
            minStack.peek().remove(0);
            if (minStack.peek().isEmpty()) {
                minStack.pop();
            }
        }
    }

    public int top() {
        return stack.peek();
    }

    public int getMin() {
        if (minStack.isEmpty()) {
            return stack.peek();
        } else {
            return minStack.peek().get(0);
        }
    }

}
