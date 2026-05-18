package com.learn.dp;


import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;

import org.junit.jupiter.api.Assertions;

import lombok.extern.log4j.Log4j2;


/**
 * LC - 215 Kth Largest Element In An Array
 *
 * Medium
 *
 * Given an integer array nums and an integer k, return the kth largest element in the array.
 *
 * Note that it is the kth largest element in the sorted order, not the kth distinct element.
 *
 * You must solve it in O(n) time complexity.
 *
 * Example 1:
 * Input: nums = [3,2,1,5,6,4], k = 2
 * Output: 5
 *
 * Example 2:
 * Input: nums = [3,2,3,1,2,4,5,5,6], k = 4
 * Output: 4
 *
 * Constraints:
 *     1 <= k <= nums.length <= 105
 *     -104 <= nums[i] <= 104
 */
@Log4j2
public class KthLargestElementInAnArray {

    public static void main(String[] args) {

        /**
         * Expected: 4
         */
        //                   0  1  2  3  4  5  6  7  8
        final int[] nums = { 3, 2, 3, 1, 2, 4, 5, 5, 6, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 };
        final int k = 2;

        KthLargestElementInAnArray kthLargestElementInAnArray = new KthLargestElementInAnArray();

        var findKthLargestLukePriorityQueue = kthLargestElementInAnArray.findKthLargestLukePriorityQueue(Arrays.copyOf(nums, nums.length), k);
        log.debug("Kth Largest Element in an Array: {}", () -> findKthLargestLukePriorityQueue);
        log.debug("Kth Largest Element in an Array {} OK", () -> "findKthLargestLukePriorityQueue");

        var findKthLargestLcHoare = kthLargestElementInAnArray.findKthLargestLcHoare(Arrays.copyOf(nums, nums.length), k);
        Assertions.assertEquals(findKthLargestLukePriorityQueue, findKthLargestLcHoare);
        log.debug("Kth Largest Element in an Array {} OK", () -> "findKthLargestLcHoare");

    }

    /**
     * Luke - PriorityQueue
     *
     * The idea is to init a heap "the smallest element first".
     *
     * Runtime: 141 ms, faster than 11.96% of Java online submissions for Kth Largest Element in an Array.
     * Memory Usage: 79.3 MB, less than 7.97% of Java online submissions for Kth Largest Element in an Array.
     *
     * Time: O(N * log(k)), log(k) is the time complexity of adding lement to PriorityQueue. PriorityQueue.poll()
     *                      time complexity is O(1) because the smallest element is always the first element.
     * Space: O(k)
     */
    public int findKthLargestLukePriorityQueue(int[] nums, int k) {
        if (nums == null || nums.length < k) {
            return 0;
        }

        Queue<Integer> priQueue = new PriorityQueue<>();

        for (Integer i : nums) {
            priQueue.add(i);
            if (priQueue.size() > k) {
                priQueue.poll();
            }

            // log.debug("queue: {}", queue);
        }

        return priQueue.poll();
    }

    /**
     * LC - Tony Hoare
     *
     *
     * Time: O(N) in average. O(N ^ 2) worst case
     * Space: O(1)
     */

    public int findKthLargestLcHoare(final int[] nums, final int k) {
        int size = nums.length;
        // kth largest is (N - k)th smallest
        return quickselect(0, size - 1, size - k, nums);
    }

    public void swap(int a, int b, final int[] nums) {
        int tmp = nums[a];
        nums[a] = nums[b];
        nums[b] = tmp;
    }

    /**
     * @return idxPivot
     */
    public int partition(int left, int right, int pivotIdx, final int[] nums) {
        int pivot = nums[pivotIdx];
        // 1. move pivot to end
        swap(pivotIdx, right, nums);

        int curr = left;

        // 2. move all smaller elements to the left
        for (int i = left; i <= right; i++) {
            if (nums[i] < pivot) {
                swap(curr, i, nums);
                curr++;
            }
        }

        // 3. move pivot to its final place
        swap(curr, right, nums);

        return curr;
    }

    /**
     * Returns the k-th smallest element of list within left..right.
     */
    public int quickselect(int left, int right, int kthSmallest, final int[] nums) {

        // If the list contains only one element,
        if (left == right) {
            return nums[left]; // return that element
        }

        // select a random idxPivot
        Random random = new Random();
        int idxPivot = random.nextInt(left, right);

        idxPivot = partition(left, right, idxPivot, nums);

        // the pivot is on (N - k)th smallest position
        if (kthSmallest == idxPivot) {
            return nums[kthSmallest];
        } else if (kthSmallest < idxPivot) {
            // go left side
            return quickselect(left, idxPivot - 1, kthSmallest, nums);
        } else {
            // go right side
            return quickselect(idxPivot + 1, right, kthSmallest, nums);
        }
    }

}
