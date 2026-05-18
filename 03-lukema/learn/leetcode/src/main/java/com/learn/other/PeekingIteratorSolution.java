package com.learn.other;


import java.util.Iterator;


/**
 * LC - 284 - Peeking Iterator
 *
 * Medium
 *
 * Design an iterator that supports the peek operation on an existing iterator in addition to the hasNext and the next operations.
 *
 * Implement the PeekingIterator class:
 *
 *     PeekingIterator(Iterator<int> nums) Initializes the object with the given integer iterator iterator.
 *     int next() Returns the next element in the array and moves the pointer to the next element.
 *     boolean hasNext() Returns true if there are still elements in the array.
 *     int peek() Returns the next element in the array without moving the pointer.
 *
 * Note: Each language may have a different implementation of the constructor and Iterator, but they all support the int next() and boolean hasNext() functions.
 *
 * Example 1:
 * Input
 * ["PeekingIterator", "next", "peek", "next", "next", "hasNext"]
 * [[[1, 2, 3]], [], [], [], [], []]
 * Output
 * [null, 1, 2, 2, 3, false]
 * Explanation
 * PeekingIterator peekingIterator = new PeekingIterator([1, 2, 3]); // [1,2,3]
 * peekingIterator.next();    // return 1, the pointer moves to the next element [1,2,3].
 * peekingIterator.peek();    // return 2, the pointer does not move [1,2,3].
 * peekingIterator.next();    // return 2, the pointer moves to the next element [1,2,3]
 * peekingIterator.next();    // return 3, the pointer moves to the next element [1,2,3]
 * peekingIterator.hasNext(); // return False
 *
 * Constraints:
 *     1 <= nums.length <= 1000
 *     1 <= nums[i] <= 1000
 *     All the calls to next and peek are valid.
 *     At most 1000 calls will be made to next, hasNext, and peek.
 *
 * Follow up: How would you extend your design to be generic and work with all types, not just integer?
 */
public class PeekingIteratorSolution {

    public static void main(String[] args) {

    }

}


/**
 * LC
 *
 * Runtime: 7 ms Beats 79.18%
 * Memory: 43.1 MB Beats 17.3%
 *
 * Time: O(1)
 * Space: O(1)
 */
class PeekingIterator<T>
    implements Iterator<T> {

    private Iterator<T> iterator;

    private T peekedValue;

    /**
     * Time: O(1)
     * Space: O(1)
     */
    public PeekingIterator(Iterator<T> iterator) {
        // initialize any member here.
        if (iterator != null && iterator.hasNext()) {
            this.peekedValue = iterator.next();
        }

        this.iterator = iterator;
    }

    /**
     * Time: O(1)
     * Space: O(1)
     */
    // Returns the next element in the iteration without advancing the iterator.
    public T peek() {
        return peekedValue;
    }

    /**
     * Time: O(1)
     * Space: O(1)
     */
    // hasNext() and next() should behave the same as in the Iterator interface.
    // Override them if needed.
    @Override
    public T next() {
        T tmp = peekedValue;

        if (iterator.hasNext()) {
            peekedValue = iterator.next();
        } else {
            peekedValue = null;
            throw new java.util.NoSuchElementException();
        }

        return tmp;
    }

    /**
     * Time: O(1)
     * Space: O(1)
     */
    @Override
    public boolean hasNext() {
        return peekedValue != null;
    }
}
