package com.learn.other;


import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;


/**
 * LC - 225 - Implement Stack Using Queues
 *
 * Easy
 *
 * Implement a last-in-first-out (LIFO) stack using only two queues. The implemented stack should support all the functions of a normal stack (push, top, pop, and empty).
 *
 * Implement the MyStack class:
 *     void push(int x) Pushes element x to the top of the stack.
 *     int pop() Removes the element on the top of the stack and returns it.
 *     int top() Returns the element on the top of the stack.
 *     boolean empty() Returns true if the stack is empty, false otherwise.
 *
 * Notes:
 *     You must use only standard operations of a queue, which means that only push to back, peek/pop from front, size and is empty operations are valid.
 *     Depending on your language, the queue may not be supported natively. You may simulate a queue using a list or deque (double-ended queue) as long as
 *     you use only a queue's standard operations.
 *
 * Example 1:
 * Input
 * ["MyStack", "push", "push", "top", "pop", "empty"]
 * [[], [1], [2], [], [], []]
 * Output
 * [null, null, null, 2, 2, false]
 * Explanation
 * MyStack myStack = new MyStack();
 * myStack.push(1);
 * myStack.push(2);
 * myStack.top(); // return 2
 * myStack.pop(); // return 2
 * myStack.empty(); // return False
 *
 * Constraints:
 *     1 <= x <= 9
 *     At most 100 calls will be made to push, pop, top, and empty.
 *     All the calls to pop and top are valid.
 */
public class ImplementStackUsingQueues {

    public static void main(String[] args) {

    }

}


/**
 * Luke - Two Queues
 *
 * Runtime: 3 ms, faster than 37.65% of Java online submissions for Implement Stack using Queues.
 * Memory Usage: 41.7 MB, less than 65.35% of Java online submissions for Implement Stack using Queues.
 *
 * Time: O(N)
 * Space: O(1)
 */
class MyStack {

    private Queue<Integer> main;
    private Queue<Integer> active;
    private Queue<Integer> nonActive;

    int top = -1;

    public MyStack() {
        main = new ConcurrentLinkedQueue<>();
        nonActive = new ConcurrentLinkedQueue<>();
        active = main;
    }

    /**
     * Time: O(1)
     * Space: O(1)
     */
    public void push(int x) {
        active.add(x);
        top = x;
    }

    /**
     * Time: O(N)
     * Space: O(1)
     */
    public int pop() {
        while (!active.isEmpty()) {
            Integer i = active.poll();
            if (!active.isEmpty()) {
                nonActive.add(i);
                top = i;
            } else {
                Queue<Integer> tmp = active;
                active = nonActive;
                nonActive = tmp;
                return i;
            }
        }
        return -1;
    }

    /**
     * Time: O(1)
     * Space: O(1)
     */
    public int top() {
        /*
        while (!active.isEmpty()) {
            Integer i = active.poll();
            nonActive.add(i);
            if (active.isEmpty()) {
                Queue<Integer> tmp = active;
                active = nonActive;
                nonActive = tmp;
                return i;
            }
        }
        return -1;
        */
        return top;
    }

    /**
     * Time: O(1)
     * Space: O(1)
     */
    public boolean empty() {
        return active.isEmpty();
    }
}

/**
 * Your MyStack object will be instantiated and called as such:
 * MyStack obj = new MyStack();
 * obj.push(x);
 * int param_2 = obj.pop();
 * int param_3 = obj.top();
 * boolean param_4 = obj.empty();
 */
