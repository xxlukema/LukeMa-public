package com.learn.backtrack;


import java.util.Arrays;

import lombok.extern.log4j.Log4j2;


/**
 * LC-327 Count of Range Sum
 *
 * Hard
 *
 * Given an integer array nums and two integers lower and upper, return the number of range sums that lie in [lower, upper] inclusive.
 *
 * Range sum S(i, j) is defined as the sum of the elements in nums between indices i and j inclusive, where i <= j.
 *
 * Example 1:
 * Input: nums = [-2,5,-1], lower = -2, upper = 2
 * Output: 3
 * Explanation: The three ranges are: [0,0], [2,2], and [0,2] and their respective sums are: -2, -1, 2.
 *
 * Example 2:
 * Input: nums = [0], lower = 0, upper = 0
 * Output: 1
 *
 * Constraints:
 *     1 <= nums.length <= 10 ^ 5
 *     -2 ^ 31 <= nums[i] <= 2 ^ 31 - 1
 *     -10 ^ 5 <= lower <= upper <= 10 ^ 5
 *     The answer is guaranteed to fit in a 32-bit integer.
 */
@Log4j2
public class CountOfRangeSum {

    public static void main(String[] args) {

        /**
         * expected: 3
         */
        // final int[] nums = { -2, 5, -1 };
        // final int lower = -2, upper = 2;

        /**
         * expected: 3
         */
        final int[] nums = { -2147483647, 0, -2147483647, 2147483647 };
        final int lower = -564, upper = 3864;

        CountOfRangeSum countOfRangeSum = new CountOfRangeSum();

        var countRangeSumTLE = countOfRangeSum.countRangeSumTLE(nums, lower, upper);
        log.debug("Count of Range Sum: {}", () -> countRangeSumTLE);
        log.debug("Count of Range Sum {} OK", () -> "countRangeSumTLE");

        var countRangeSumMLE = countOfRangeSum.countRangeSumMLE(nums, lower, upper);
        log.debug("Count of Range Sum: {}", () -> countRangeSumMLE);
        log.debug("Count of Range Sum {} OK", () -> "countRangeSumMLE");

    }

    public int countRangeSumTLE(int[] nums, int lower, int upper) {

        final Long[] memo = new Long[nums.length];

        int ctr = 0;

        for (int endIdx = nums.length - 1; endIdx >= 0; endIdx--) {

            Arrays.fill(memo, null);

            for (int startIdx = 0; startIdx <= endIdx; startIdx++) {
                var ret = countRangeSum(nums, startIdx, endIdx, memo);
                if (ret >= lower && ret <= upper) {
                    ctr++;
                }
            }
        }

        return ctr;
    }

    /**
     * Time Limit Exceeded
     */
    long countRangeSum(int[] nums, int startIdx, int endIdx, final Long[] memo) {

        if (startIdx == endIdx) {
            return memo[startIdx] = (long) nums[startIdx];
        }

        if (memo[startIdx] != null) {
            return memo[startIdx];
        } else if (memo[startIdx + 1] != null) {
            return memo[startIdx] = nums[startIdx] + memo[startIdx + 1];
        } else if (startIdx - 1 >= 0 && memo[startIdx - 1] != null) {
            return memo[startIdx] = memo[startIdx - 1] - nums[startIdx];
        } else {
            return nums[startIdx] + countRangeSum(nums, startIdx + 1, endIdx, memo);
        }
    }

    public int countRangeSumMLE(int[] nums, int lower, int upper) {

        final Long[][] memo = new Long[nums.length][nums.length];

        int ctr = 0;

        for (int endIdx = nums.length - 1; endIdx >= 0; endIdx--) {
            for (int startIdx = 0; startIdx <= endIdx; startIdx++) {
                var ret = countRangeSumMemoryLimitExceeded(nums, startIdx, endIdx, memo);
                if (ret >= lower && ret <= upper) {
                    ctr++;
                }
            }
        }

        return ctr;
    }

    long countRangeSumMemoryLimitExceeded(int[] nums, int startIdx, int endIdx, final Long[][] memo) {

        if (startIdx == endIdx) {
            return memo[startIdx][endIdx] = (long) nums[startIdx];
        }

        if (memo[startIdx][endIdx] != null) {
            return memo[startIdx][endIdx];
            /*
            } else if (memo[startIdx + 1][endIdx] != null) {
            return memo[startIdx][endIdx] = nums[startIdx] + memo[startIdx + 1][endIdx];
            } else if (endIdx - 1 >= startIdx && memo[startIdx][endIdx - 1] != null) {
            return memo[startIdx][endIdx] = memo[startIdx][endIdx - 1] + nums[endIdx];
            */
        } else {
            return nums[startIdx] + countRangeSumMemoryLimitExceeded(nums, startIdx + 1, endIdx, memo);
        }
    }

    long countRangeSumTimeLimitExceeded(int[] nums, int startIdx, int endIdx, final Long[][] memo) {

        if (startIdx == endIdx) {
            return memo[startIdx][endIdx] = (long) nums[startIdx];
        }

        long sum = nums[startIdx];

        if (memo[startIdx + 1][endIdx] != null) {
            return memo[startIdx][endIdx] = sum + memo[startIdx + 1][endIdx];
        } else {
            return sum + countRangeSumTimeLimitExceeded(nums, startIdx + 1, endIdx, memo);
        }
    }
}
