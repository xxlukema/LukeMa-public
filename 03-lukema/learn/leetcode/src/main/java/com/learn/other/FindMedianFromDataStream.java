package com.learn.other;


import java.util.Collections;
import java.util.PriorityQueue;

import lombok.extern.log4j.Log4j2;


/**
 * LC - 295 - Find Median From Data Stream
 *
 * Hard
 *
 * The median is the middle value in an "ordered integer list". If the size of the list is even, there is no middle value and the median is the mean of the two middle values.
 *
 *     For example, for arr = [2,3,4], the median is 3.
 *     For example, for arr = [2,3], the median is (2 + 3) / 2 = 2.5.
 *
 * Implement the MedianFinder class:
 *
 *     MedianFinder() initializes the MedianFinder object.
 *     void addNum(int num) adds the integer num from the data stream to the data structure.
 *     double findMedian() returns the median of all elements so far. Answers within 10-5 of the actual answer will be accepted.
 *
 * Example 1:
 * Input
 * ["MedianFinder", "addNum", "addNum", "findMedian", "addNum", "findMedian"]
 * [[], [1], [2], [], [3], []]
 * Output
 * [null, null, null, 1.5, null, 2.0]
 * Explanation
 * MedianFinder medianFinder = new MedianFinder();
 * medianFinder.addNum(1);    // arr = [1]
 * medianFinder.addNum(2);    // arr = [1, 2]
 * medianFinder.findMedian(); // return 1.5 (i.e., (1 + 2) / 2)
 * medianFinder.addNum(3);    // arr[1, 2, 3]
 * medianFinder.findMedian(); // return 2.0
 *
 * Constraints:
 *     -105 <= num <= 105
 *     There will be at least one element in the data structure before calling findMedian.
 *     At most 5 * 104 calls will be made to addNum and findMedian.
 *
 * Follow up:
 *     If all integer numbers from the stream are in the range [0, 100], how would you optimize your solution?
 *     If 99% of all integer numbers from the stream are in the range [0, 100], how would you optimize your solution?
 */
@Log4j2
public class FindMedianFromDataStream {

    public static void main(String[] args) {

        MedianFinder medianFinder = new MedianFinder();
        medianFinder.addNum(1);
        medianFinder.addNum(2);
        var ret = medianFinder.findMedian();
        log.debug("Median: {}", ret);
        medianFinder.addNum(3);
        ret = medianFinder.findMedian();
        log.debug("Median: {}", ret);
    }
}


/**
 * Follow Up:
 *
 * 1. If the range of the numbers is in [0...100], we use a bucket to collect the frequency of each number. By accumulating the
 *    frequency of elements in the bucket, we can know the median numbers.
 *
 * 2. If 1% numbers are outside of the range [0...100], we know that when the set of numbers is large, the median numbers must be in
 *    the range of [0...100], because this range contains 99% numbers. We don't need to store values of 1% numbers, but the counts of
 *    these numbers (countLessZero & countGreater100). The findMedian method is almost the same, the difference is we start counting
 *    from countLessZero value
 */

/**
 * LC - Two Heap
 */
class MedianFinder {

    final PriorityQueue<Integer> leftMaxQueue;
    final PriorityQueue<Integer> rightMinQueue;

    public MedianFinder() {
        /**
         * holds small nums (left)
         */
        leftMaxQueue = new PriorityQueue<>(Collections.reverseOrder());

        /**
         * holds large nums (right)
         */
        rightMinQueue = new PriorityQueue<>();
    }

    /**
     * LC - Two Heap
     *
     * Runtime: 301 ms Beats 20.1%
     * Memory: 125.6 MB Beats 30.67%
     *
     * Time: O(log(N)), offer() time O(log(N))
     * Space: O(N)
     */
    public void addNum(int num) {
        leftMaxQueue.offer(num);
        rightMinQueue.offer(leftMaxQueue.poll());

        if (leftMaxQueue.size() < rightMinQueue.size()) {
            leftMaxQueue.offer(rightMinQueue.poll());
        }
    }

    /**
     * LC - Two Heap
     *
     * Runtime: 301 ms Beats 20.1%
     * Memory: 125.6 MB Beats 30.67%
     *
     * Time: O(1)
     * Space: O(N)
     */
    public double findMedian() {
        if (leftMaxQueue.size() > rightMinQueue.size()) {
            return leftMaxQueue.peek();
        } else {
            return (leftMaxQueue.peek() + rightMinQueue.peek()) / 2.0;
        }
    }
}


/**
 * 1. Time Out Exception
 * 2. "-105 <= num <= 105" is a lie
 */
class MedianFinder2 {

    private int size = 0;

    final int[] frequency;

    final static int MAX = 105;
    final static int MIN = -105;

    final static int INC = -MIN;

    public MedianFinder2() {
        this.frequency = new int[211];
    }

    /**
     * Time: O(1)
     * Space: O(N)
     */
    public void addNum(int num) {
        size++;
        frequency[num + INC]++;
    }

    /**
     * Time: O(N)
     * Space: O(N)
     *
     * ["MedianFinder","addNum","addNum","findMedian","addNum","findMedian"]
     * [[],[1],[2],[],[3],[]]
     *
     * Output:
     * [null,null,null,-52.00000,null,1.00000]
     * Expected:
     * [null,null,null,1.50000,null,2.00000]
     */
    public double findMedian() {
        boolean isOdd = size % 2 == 1;

        if (isOdd) {
            // idx: [size / 2]
            int count = size / 2 + 1;
            return getOdd(count);
        } else {
            // idx: [size / 2 - 1], [size / 2]
            int count1 = size / 2;
            return getEven(count1);
        }
    }

    /**
     * Time: O(N)
     * Space: O(N)
     */
    private double getOdd(int pos) {
        int count = 0;
        int i = 0;
        int last = 0;
        while (count < pos) {
            if (frequency[i] > 0) {
                count += frequency[i];
                last = i;
            }

            i++;
        }

        return last - INC;
    }

    /**
     * Time: O(N)
     * Space: O(N)
     */
    private double getEven(int pos) {
        int count = 0;
        int i = 0;
        int last = 0;
        while (count < pos) {
            if (frequency[i] > 0) {
                count += frequency[i];
                last = i;
            }
            i++;
        }

        if (count >= pos + 1) {
            return last - INC;
        } else {
            int next = last + 1;

            while (frequency[next] == 0) {
                next++;
            }

            return (last + next) / 2.0 - INC;
        }
    }
}

/**
 * Your MedianFinder object will be instantiated and called as such:
 * MedianFinder obj = new MedianFinder();
 * obj.addNum(num);
 * double param_2 = obj.findMedian();
 */
