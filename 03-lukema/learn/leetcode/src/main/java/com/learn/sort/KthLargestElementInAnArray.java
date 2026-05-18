package com.learn.sort;


import java.util.Random;

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
        // final int[] nums = { 3, 2, 3, 1, 2, 4, 5, 5, 6 };
        final int[] nums = { 3, 2, 1 };
        final int k = 1;

        /**
         * Expected: 5
         */
        // final int[] nums = { 3, 2, 1, 5, 6, 4 };
        // final int k = 2;

        KthLargestElementInAnArray kthLargestElementInAnArray = new KthLargestElementInAnArray();

        log.debug("nums: {}", nums);

        var ret = kthLargestElementInAnArray.kthLargestElement(nums, k);
        log.debug("Kth largest element in an array: {}", () -> ret);
        log.debug("Kth largest element in an array {} OK", () -> "ret");
    }

    /**
     * LC - Tony Hoare - Quick Select
     *
     * Runtime: 14 ms Beats 87.82%
     * Memory: 76.5 MB Beats 19.83%
     *
     * Time: O(N * log(k))
     * Space: O(1)
     * Issue: Original array is changed.
     * Good: No extra memory.
     */
    public int kthLargestElement(final int[] nums, int k) {
        if (nums == null || nums.length < k) {
            throw new java.util.NoSuchElementException();
        }

        final int LEN = nums.length;

        return quickselect(nums, 0, LEN - 1, LEN - k);
    }

    int quickselect(final int[] nums, int left, int right, int idxKthSmallest) {

        log.debug("--- idxKthSmallest: {}", idxKthSmallest);

        if (left == right) {
            return nums[left];
        }

        Random random = new Random();
        int idxPivot = random.nextInt(left, right + 1);
        // int idxPivot = right;

        /**
         * Every cell in the left of pos is smaller
         */
        int pos = partition(nums, left, right, idxPivot);

        if (pos == idxKthSmallest) {
            return nums[pos];
        } else if (pos < idxKthSmallest) {
            /**
             * pos is too small
             */
            return quickselect(nums, pos + 1, right, idxKthSmallest);
        } else {
            /**
             * pos is too large
             */
            return quickselect(nums, left, pos - 1, idxKthSmallest);
        }
    }

    /**
     * @return index of new pivot. cells to the left is smaller than pivot
     */
    int partition(final int[] nums, final int left, final int right, final int idxPivot) {

        if (left == right) {
            return left;
        }

        int pivot = nums[idxPivot];

        log.debug("--- idxPivot: {}, pivot: {}, nums: {}", idxPivot, pivot, nums);

        /**
         * 1. Move pivot to the end
         */
        swap(nums, idxPivot, right);

        int cur = left;

        /**
         * 2. Move smaller cells to left
         */
        for (int i = left; i < right; i++) {
            if (nums[i] < pivot) {
                swap(nums, i, cur++);
            }
        }

        /**
         * 3. Move pivot to correct pos
         */
        swap(nums, cur, right);

        log.debug("--- nums: {}, curIdx: {}, curVal: {}", nums, cur, nums[cur]);

        /**
         * 4. return current pivot idx
         */
        return cur;
    }

    void swap(final int[] nums, final int left, final int right) {
        int tmp = nums[left];
        nums[left] = nums[right];
        nums[right] = tmp;
    }
}
