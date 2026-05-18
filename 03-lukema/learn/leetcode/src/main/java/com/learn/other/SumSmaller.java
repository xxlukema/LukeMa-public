package com.learn.other;


import java.util.Arrays;

import org.junit.jupiter.api.Assertions;

import lombok.extern.log4j.Log4j2;


/**
 * LC-259 Sum Smaller
 *
 * Medium
 *
 * Given an array of n integers nums and an integer target, find the number of index triplets i, j, k with 0 <= i < j < k < n
 * that satisfy the condition nums[i] + nums[j] + nums[k] < target.
 *
 * Example 1:
 * Input: nums = [-2,0,1,3], target = 2
 * Output: 2
 * Explanation: Because there are two triplets which sums are less than 2:
 * [-2,0,1]
 * [-2,0,3]
 *
 * Example 2:
 * Input: nums = [], target = 0
 * Output: 0
 *
 * Example 3:
 * Input: nums = [0], target = 0
 * Output: 0
 *
 * Constraints:
 *     n == nums.length
 *     0 <= n <= 3500
 *     -100 <= nums[i] <= 100
 *     -100 <= target <= 100
 */
@Log4j2
public class SumSmaller {

    public static void main(String[] args) {

        /**
         * Expected: 2
         */
        final int[] nums = { -2, 0, 1, 3 };
        final int target = 2;

        SumSmaller sumSmaller = new SumSmaller();

        var threeSumSmaller = sumSmaller.threeSumSmaller(nums, target);
        log.debug("Sum Smaller: {}", () -> threeSumSmaller);
        log.debug("Sum Smaller {} OK", () -> "threeSumSmaller");

        var threeSumSmallerBSearch = sumSmaller.threeSumSmallerBSearch(nums, target);
        Assertions.assertEquals(threeSumSmaller, threeSumSmallerBSearch);
        log.debug("Sum Smaller {} OK", () -> "threeSumSmallerBSearch");

        var threeSumSmallerTwoPointer = sumSmaller.threeSumSmallerTwoPointer(nums, target);
        Assertions.assertEquals(threeSumSmaller, threeSumSmallerTwoPointer);
        log.debug("Sum Smaller {} OK", () -> "threeSumSmallerTwoPointer");

    }

    /**
     * Luke - brute
     *
     * Runtime: 30 ms Beats 14.74%
     * Memory: 41.8 MB Beats 90.69%
     *
     * Time: O(N ^ 3) + O(N * log(N))
     * Space: O(N)
     */
    public int threeSumSmaller(int[] nums, int target) {
        if (nums == null || nums.length < 3) {
            return 0;
        }

        Arrays.sort(nums);

        int count = 0;

        final int len = nums.length;

        for (int left = 0, leftLen = len - 2; left < leftLen; left++) {
            for (int mid = left + 1, midLen = len - 1; mid < midLen; mid++) {
                int rem = target - nums[left] - nums[mid];
                for (int right = mid + 1; right < len; right++) {
                    if (nums[right] < rem) {
                        count++;
                    } else {
                        break;
                    }
                }
            }
        }

        return count;
    }

    /**
     * Luke - Iteration + BSearch
     *
     * Runtime: 449 ms Beats 7.6%
     * Memory: 43.7 MB Beats 11.57%
     *
     * Time: O(N ^ 2 * log(N))
     * Space: O(1)
     */
    public int threeSumSmallerBSearch(int[] nums, int target) {
        if (nums == null || nums.length < 3) {
            return 0;
        }

        Arrays.sort(nums);

        // log.debug("--- nums: {}", nums);
        // log.debug("--======- nums pos: {}", bSearch(nums, 3, 0, nums.length - 1));

        int count = 0;

        final int len = nums.length;

        for (int left = 0, leftLen = len - 2; left < leftLen; left++) {
            for (int right = left + 1, midLen = len - 1; right < midLen; right++) {
                int rem = target - nums[left] - nums[right];
                int idx = bSearch(nums, rem, right + 1, len - 1);

                // log.debug("--- rem: {}, right + 1: {}, idx: {}", rem, right + 1, idx);

                if (idx >= 0) {
                    count += idx - (right + 1) + 1;
                }
            }
        }

        return count;
    }

    /**
     * @return idx of largest element smaller than target
     *
     * Time: O(log(N))
     * Space: O(1)
     */
    int bSearch(int[] nums, int target, int left, int right) {
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] >= target) {
                right = mid - 1;
            } else {
                if (mid + 1 <= right) {
                    if (nums[mid + 1] >= target) {
                        return mid;
                    } else {
                        left = mid + 1;
                    }
                } else {
                    left = mid;
                }
            }
        }

        if (nums[left] < target) {
            return left;
        } else {
            return -1;
        }
    }

    /**
     * LC - Two pointer
     *
     * Time: O(N ^ 2)
     * Space: O(1)
     */
    public int threeSumSmallerTwoPointer(int[] nums, int target) {
        if (nums == null || nums.length < 3) {
            return 0;
        }

        Arrays.sort(nums);

        int count = 0;

        for (int i = 0, end = nums.length - 2; i < end; i++) {
            count += threeSumSmallerTwoPointer(nums, target - nums[i], i + 1);
        }

        return count;
    }

    private int threeSumSmallerTwoPointer(int[] nums, int target, int startIdx) {
        int count = 0;

        int left = startIdx;
        int right = nums.length - 1;

        while (left < right) {
            if (nums[left] + nums[right] < target) {
                count += right - left;
                left++;
            } else {
                right--;
            }
        }

        return count;
    }
}
