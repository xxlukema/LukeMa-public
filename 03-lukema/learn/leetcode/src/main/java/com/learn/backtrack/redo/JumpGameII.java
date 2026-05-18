package com.learn.backtrack.redo;


import org.junit.jupiter.api.Assertions;

import lombok.extern.log4j.Log4j2;


/**
 * LC - 45 - Jump Game II
 *
 * Medium
 *
 * You are given a 0-indexed array of integers nums of length n. You are initially positioned at nums[0].
 *
 * Each element nums[i] represents the maximum length of a forward jump from index i. In other words, if you are at nums[i], you can jump to any nums[i + j] where:
 *
 *     0 <= j <= nums[i] and
 *     i + j < n
 *
 * Return the minimum number of jumps to reach nums[n - 1]. The test cases are generated such that you can reach nums[n - 1].
 *
 * Example 1:
 * Input: nums = [2,3,1,1,4]
 * Output: 2
 * Explanation: The minimum number of jumps to reach the last index is 2. Jump 1 step from index 0 to 1, then 3 steps to the last index.
 *
 * Example 2:
 * Input: nums = [2,3,0,1,4]
 * Output: 2
 *
 * Constraints:
 *     1 <= nums.length <= 104
 *     0 <= nums[i] <= 1000
 */
@Log4j2
public class JumpGameII {

    public static void main(String[] args) {

        final int[] nums = { 2, 3, 1, 1, 4 };
        // final int[] nums = { 2, 1 };
        // final int[] nums = { 1, 2, 3 };
        // final int[] nums = { 5, 9, 3, 2, 1, 0, 2, 3, 3, 1, 0, 0 };
        // final int[] nums = { 6, 9, 1, 5, 6, 0, 0, 5, 9 };

        JumpGameII jumpGameII = new JumpGameII();

        var jumpLc = jumpGameII.jumpLc(nums);
        log.debug("Jump Game II: {}", () -> jumpLc);
        log.debug("Jump Game II {} OK", () -> "jumpLc");

        var jumpLukeDp = jumpGameII.jumpLukeDp(nums);
        Assertions.assertEquals(jumpLc, jumpLukeDp);
        log.debug("Jump Game II {} OK", () -> "jumpLukeDp");
    }

    /**
     * LC - Iterative
     *
     *
     * Time: O(N)
     * Space: O(1)
     */
    public int jumpLc(int[] nums) {
        if (nums == null || nums.length < 2) {
            return 0;
        }

        int jumps = 0, currentJumpEnd = 0, farthest = 0;

        for (int i = 0; i < nums.length - 1; i++) {
            // we continuously find the how far we can reach in the current jump
            farthest = Math.max(farthest, i + nums[i]);
            // if we have come to the end of the current jump,
            // we need to make another jump
            if (i == currentJumpEnd) {
                jumps++;
                currentJumpEnd = farthest;
            }
        }
        return jumps;
    }

    /**
     * Luke - DP
     *
     * Runtime: 2 ms Beats 87.18%
     * Memory: 50.4 MB Beats 6.99%
     *
     * Time: O(N)
     * Space: O(1)
     */
    public int jumpLukeDp(int[] nums) {
        if (nums == null || nums.length < 2) {
            return 0;
        }

        final int LEN = nums.length;
        final int[] dp = new int[LEN];

        /**
         * Time: O(N)
         */
        for (int i = 0, n = LEN; i < n; i++) {
            dp[i] = i + nums[i];
        }

        int jumps = 0;
        int start = 0;
        int end = 0;

        /**
         * Time: O(N)
         */
        while (end < LEN) {
            int maxDistance = 0;
            for (int i = start; i <= end; i++) {
                maxDistance = Math.max(maxDistance, dp[i]);
                if (maxDistance >= LEN - 1) {
                    return jumps + 1;
                }
            }
            jumps++;
            start = end + 1;
            end = maxDistance;
        }

        return jumps;
    }
}
