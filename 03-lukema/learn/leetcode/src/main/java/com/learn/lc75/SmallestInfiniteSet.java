package com.learn.lc75;


import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;


/**
 * LC - 2336. Smallest Number in Infinite Set
 *
 * Medium
 *
 * You have a set which contains all positive integers [1, 2, 3, 4, 5, ...].

Implement the SmallestInfiniteSet class:

    SmallestInfiniteSet() Initializes the SmallestInfiniteSet object to contain all positive integers.
    int popSmallest() Removes and returns the smallest integer contained in the infinite set.
    void addBack(int num) Adds a positive integer num back into the infinite set, if it is not already in the infinite set.

Example 1:

Input
["SmallestInfiniteSet", "addBack", "popSmallest", "popSmallest", "popSmallest", "addBack", "popSmallest", "popSmallest", "popSmallest"]
[[], [2], [], [], [], [1], [], [], []]
Output
[null, null, 1, 2, 3, null, 1, 4, 5]

Explanation
SmallestInfiniteSet smallestInfiniteSet = new SmallestInfiniteSet();
smallestInfiniteSet.addBack(2);    // 2 is already in the set, so no change is made.
smallestInfiniteSet.popSmallest(); // return 1, since 1 is the smallest number, and remove it from the set.
smallestInfiniteSet.popSmallest(); // return 2, and remove it from the set.
smallestInfiniteSet.popSmallest(); // return 3, and remove it from the set.
smallestInfiniteSet.addBack(1);    // 1 is added back to the set.
smallestInfiniteSet.popSmallest(); // return 1, since 1 was added back to the set and
                                   // is the smallest number, and remove it from the set.
smallestInfiniteSet.popSmallest(); // return 4, and remove it from the set.
smallestInfiniteSet.popSmallest(); // return 5, and remove it from the set.

Constraints:

    1 <= num <= 1000
    At most 1000 calls will be made in total to popSmallest and addBack.
 */

/**
 * Runtime: 35ms Beats 21.53
 * Memory: 44.56mb Beats 45.19
 */
public class SmallestInfiniteSet {

    public static void main(String[] args) {

    }

    Set<Integer> set;

    PriorityQueue<Integer> pq;

    public SmallestInfiniteSet() {
        set = new HashSet<>();
        pq = new PriorityQueue<>((a, b) -> a.intValue() - b.intValue());

        for(int i = 1; i <= 1000; i++) {
            set.add(i);
        }

        /**
         * Time: O(n)
         */
        pq.addAll(set);
    }

    /**
     * Time: O(1)
     */
    public int popSmallest() {
        Integer smallest = pq.poll();
        set.remove(smallest);

        return smallest;
    }

    /**
     * Time: O(log(n)) to add to PriorityQueue
     */
    public void addBack(int num) {
        if (!set.contains(Integer.valueOf(num))) {
            set.add(num);
            pq.add(num);
        }
    }
}

/**
 * Your SmallestInfiniteSet object will be instantiated and called as such:
 * SmallestInfiniteSet obj = new SmallestInfiniteSet();
 * int param_1 = obj.popSmallest();
 * obj.addBack(num);
 */
