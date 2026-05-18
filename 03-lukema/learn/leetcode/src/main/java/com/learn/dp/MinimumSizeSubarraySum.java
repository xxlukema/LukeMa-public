package com.learn.dp;


import org.junit.jupiter.api.Assertions;

import lombok.extern.log4j.Log4j2;


/**
 * LC - 209 - Minimum Size Subarray Sum
 *
 * Medium
 *
 * Given an array of positive integers nums and a positive integer target, return the minimal length of a contiguous subarray
 * [numsl, numsl+1, ..., numsr-1, numsr] of which the sum is greater than or equal to target. If there is no such subarray, return 0 instead.
 *
 * Example 1:
 * Input: target = 7, nums = [2,3,1,2,4,3]
 * Output: 2
 * Explanation: The subarray [4,3] has the minimal length under the problem constraint.
 *
 * Example 2:
 * Input: target = 4, nums = [1,4,4]
 * Output: 1
 *
 * Example 3:
 * Input: target = 11, nums = [1,1,1,1,1,1,1,1]
 * Output: 0
 *
 * Constraints:
 *     1 <= target <= 109
 *     1 <= nums.length <= 105
 *     1 <= nums[i] <= 104
 *
 * Follow up: If you have figured out the O(n) solution, try coding another solution of which the time complexity is O(n log(n)).
 */
@Log4j2
public class MinimumSizeSubarraySum {

    public static void main(String[] args) {

        /**
         * Output: 2
         */
        final int target = 7;
        final int[] nums = { 2, 3, 1, 2, 4, 3 };

        /**
        /**
         * Output: 1
         */
        // final int target = 4;
        // final int[] nums = { 4, 3, 2, 1 };

        /**
         * Output: 2
         */
        // final int target = 11;
        // final int[] nums = { 1, 2, 3, 4, 5 };

        /**
         * Worst case
         * Output: 2
         */
        // final int target = N - 2;
        // final int[] nums = { 1, 1, 1, ... };

        MinimumSizeSubarraySum minimumSizeSubarraySum = new MinimumSizeSubarraySum();

        var minSubArrayLenLukeDp = minimumSizeSubarraySum.minSubArrayLenLukeDp(target, nums);
        log.debug("Minimum size subarray sum: {}", () -> minSubArrayLenLukeDp);
        log.debug("Minimum size subarray sum {} OK", () -> "minSubArrayLenLukeDp");

        var minSubArrayLenLukeDpImprove = minimumSizeSubarraySum.minSubArrayLenLukeDpImprove(target, nums);
        Assertions.assertEquals(minSubArrayLenLukeDp, minSubArrayLenLukeDpImprove);
        log.debug("Minimum size subarray sum {} OK", () -> "minSubArrayLenLukeDpImprove");

        var minSubArrayLenLukeImproveNoDp = minimumSizeSubarraySum.minSubArrayLenLukeImproveNoDp(target, nums);
        Assertions.assertEquals(minSubArrayLenLukeDp, minSubArrayLenLukeImproveNoDp);
        log.debug("Minimum size subarray sum {} OK", () -> "minSubArrayLenLukeImproveNoDp");

        var minSubArrayLenLcTwoPointers = minimumSizeSubarraySum.minSubArrayLenLcTwoPointers(target, nums);
        Assertions.assertEquals(minSubArrayLenLukeDp, minSubArrayLenLcTwoPointers);
        log.debug("Minimum size subarray sum {} OK", () -> "minSubArrayLenLcTwoPointers");

    }

    /**
     * LC - Two Pointers - No DP
     *
     * Runtime: 2 ms, faster than 80.31% of Java online submissions for Minimum Size Subarray Sum.
     * Memory Usage: 57.9 MB, less than 54.47% of Java online submissions for Minimum Size Subarray Sum.
     *
     * Time: O(N). Each element can be visited atmost twice, once by the right pointer and (atmost)once by the left pointer.
     * Space: O(1)
     */
    public int minSubArrayLenLcTwoPointers(int target, int[] nums) {

        if (nums == null || nums.length == 0) {
            return 0;
        }

        if (nums.length == 1) {
            return nums[0] < target ? 0 : 1;
        }

        final int N = nums.length;

        int minLen = Integer.MAX_VALUE;

        int sum = 0;
        int left = 0;

        for (int right = 0; right < N; right++) {
            sum += nums[right];

            while (sum >= target) {
                minLen = Math.min(minLen, right - left + 1);

                /**
                 * Progress left
                 */
                sum -= nums[left];
                left++;
            }
        }

        if (minLen == Integer.MAX_VALUE) {
            return 0;
        } else {
            return minLen;
        }
    }

    /**
     * Luke - Improved - No DP
     *
     * Runtime: 2407 ms, faster than 5.11% of Java online submissions for Minimum Size Subarray Sum.
     * Memory Usage: 50.7 MB, less than 86.48% of Java online submissions for Minimum Size Subarray Sum.
     *
     * Time: O(N ^ 2)
     * Space: O(1)
     */
    public int minSubArrayLenLukeImproveNoDp(int target, int[] nums) {

        if (nums == null || nums.length == 0) {
            return 0;
        }

        if (nums.length == 1) {
            return nums[0] < target ? 0 : 1;
        }

        final int N = nums.length;

        int minLen = Integer.MAX_VALUE;

        int minSum = 0;
        int right = 0;
        int left = 0;

        while (right < N) {

            if (minSum == 0) {
                int pos = right;
                minSum = 0;
                while (pos >= 0 && minSum < target) {
                    minSum += nums[pos--];
                }
                left = pos;
            } else {
                minSum += nums[right];

                /**
                 * Redo calculate
                 */
                if (minSum >= target) {
                    int pos = right;
                    minSum = 0;
                    while (pos >= 0 && minSum < target) {
                        minSum += nums[pos--];
                    }
                    left = pos;
                }
            }

            if (minSum >= target) {

                if (right == 0) {
                    minLen = 1;
                } else {
                    minLen = Math.min(minLen, right - left);
                }
                minSum = 0;
            }
            right++;
        }

        if (minLen == Integer.MAX_VALUE) {
            return 0;
        } else {
            return minLen;
        }
    }

    /**
     * Luke - DP - Improved
     *
     * Runtime: 2407 ms, faster than 5.11% of Java online submissions for Minimum Size Subarray Sum.
     * Memory Usage: 50.7 MB, less than 86.48% of Java online submissions for Minimum Size Subarray Sum.
     *
     * Time: O(N ^ 2)
     * Space: O(N)
     */
    public int minSubArrayLenLukeDpImprove(int target, int[] nums) {

        if (nums == null || nums.length == 0) {
            return 0;
        }

        if (nums.length == 1) {
            return nums[0] < target ? 0 : 1;
        }

        final int N = nums.length;

        final int[] dp = new int[N];

        int minSum = 0;
        int right = 0;
        int left = 0;

        while (right < N) {

            if (minSum == 0) {
                int pos = right;
                minSum = 0;
                while (pos >= 0 && minSum < target) {
                    minSum += nums[pos--];
                }
                left = pos;
            } else {
                minSum += nums[right];

                /**
                 * Redo calculate
                 */
                if (minSum >= target) {
                    int pos = right;
                    minSum = 0;
                    while (pos >= 0 && minSum < target) {
                        minSum += nums[pos--];
                    }
                    left = pos;
                }
            }

            if (minSum >= target) {

                if (right == 0) {
                    dp[right] = 1;
                } else {
                    if (dp[right - 1] == 0) {
                        dp[right] = right - left;
                    } else {
                        dp[right] = Math.min(dp[right - 1], right - left);
                    }
                }
                minSum = 0;
            } else {
                dp[right] = right == 0 ? 0 : dp[right - 1];
            }
            right++;
        }

        // log.debug("dp: {}", () -> dp);

        return dp[N - 1];
    }

    /**
     * Luke - DP
     *
     * Time Limit Exceeded
     *
     * Time: O(N ^ 2)
     * Space: O(N)
     */
    public int minSubArrayLenLukeDp(int target, int[] nums) {

        if (nums == null || nums.length == 0) {
            return 0;
        }

        if (nums.length == 1) {
            return nums[0] < target ? 0 : 1;
        }

        final int N = nums.length;

        final int[] dp = new int[N];

        int minSum = 0;
        int right = 0;
        int left = 0;

        while (right < N) {

            int pos = right;
            minSum = 0;
            while (pos >= 0 && minSum < target) {
                minSum += nums[pos--];
            }
            left = pos;

            if (minSum >= target) {
                if (right == 0) {
                    dp[right] = 1;
                } else {
                    if (dp[right - 1] == 0) {
                        dp[right] = right - left;
                    } else {
                        dp[right] = Math.min(dp[right - 1], right - left);
                    }
                }
            } else {
                dp[right] = right == 0 ? 0 : dp[right - 1];
            }
            right++;
        }

        // log.debug("dp: {}", () -> dp);

        return dp[N - 1];
    }
}
