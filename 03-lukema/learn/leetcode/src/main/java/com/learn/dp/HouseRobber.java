package com.learn.dp;


import java.util.Arrays;

import org.junit.jupiter.api.Assertions;

import lombok.extern.log4j.Log4j2;


/**
 * LC - 198 - House Robber
 *
 * Medium
 *
 * You are a professional robber planning to rob houses along a street. Each house has a certain amount of money stashed, the only constraint stopping
 * you from robbing each of them is that adjacent houses have security systems connected and it will automatically contact the police if two adjacent
 * houses were broken into on the same night.
 *
 * Given an integer array nums representing the amount of money of each house, return the maximum amount of money you can rob tonight without alerting the police.
 *
 * Example 1:
 * Input: nums = [1,2,3,1]
 * Output: 4
 * Explanation: Rob house 1 (money = 1) and then rob house 3 (money = 3).
 * Total amount you can rob = 1 + 3 = 4.
 *
 * Example 2:
 * Input: nums = [2,7,9,3,1]
 * Output: 12
 * Explanation: Rob house 1 (money = 2), rob house 3 (money = 9) and rob house 5 (money = 1).
 * Total amount you can rob = 2 + 9 + 1 = 12.
 *
 * Constraints:
 *     1 <= nums.length <= 100
 *     0 <= nums[i] <= 400
 */
@Log4j2
public class HouseRobber {

    public static void main(String[] args) {

        final int[] nums = { 2, 7, 9, 3, 1 };

        HouseRobber houseRobber = new HouseRobber();

        var robLukeDp = houseRobber.robLukeDp(nums);
        log.debug("House robber: {}", () -> robLukeDp);
        log.debug("House robber {} OK", () -> "robLukeDp");

        var robLukeDpImproved = houseRobber.robLukeDpImproved(nums);
        Assertions.assertEquals(robLukeDp, robLukeDpImproved);
        log.debug("House robber {} OK", () -> "robLukeDpImproved");

        var robLcRecusiveMemo = houseRobber.robLcRecusiveMemo(nums);
        Assertions.assertEquals(robLukeDp, robLcRecusiveMemo);
        log.debug("House robber {} OK", () -> "robLcRecusiveMemo");

    }

    /**
     * Luke - DP - Tabulation
     *
     * Runtime: 1 ms, faster than 19.08% of Java online submissions for House Robber.
     * Memory Usage: 41.9 MB, less than 19.11% of Java online submissions for House Robber.
     *
     * Time: O(N)
     * Space: O(N)
     */
    public int robLukeDp(final int[] nums) {

        if (nums == null || nums.length == 0) {
            return 0;
        }

        final int N = nums.length;

        if (N == 1) {
            return nums[0];
        }

        final int[] dp = new int[N];

        dp[0] = nums[0];
        dp[1] = Math.max(dp[0], nums[1]);

        for (int i = 2; i < N; i++) {
            dp[i] = Math.max(dp[i - 1], dp[i - 2] + nums[i]);
        }

        return dp[N - 1];
    }

    /**
     * Luke - DP - Tabulation
     *
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for House Robber.
     * Memory Usage: 41.4 MB, less than 55.75% of Java online submissions for House Robber.
     *
     * Time: O(N)
     * Space: O(1)
     */
    public int robLukeDpImproved(final int[] nums) {

        if (nums == null || nums.length == 0) {
            return 0;
        }

        final int N = nums.length;

        if (N == 1) {
            return nums[0];
        }

        int leftLeft = nums[0];
        int left = Math.max(leftLeft, nums[1]);
        int curr = left;

        for (int i = 2; i < N; i++) {
            curr = Math.max(left, leftLeft + nums[i]);
            leftLeft = left;
            left = curr;
        }

        return curr;
    }

    /**
     * LC - Recusive - memo
     */
    public int robLcRecusiveMemo(int[] nums) {

        final int[] memo = new int[100];

        // Fill with sentinel value representing not-calculated recursions.
        Arrays.fill(memo, -1);

        return this.robFrom(0, nums, memo);
    }

    private int robFrom(int i, final int[] nums, final int[] memo) {

        // No more houses left to examine.
        if (i >= nums.length) {
            return 0;
        }

        // Return cached value.
        if (memo[i] > -1) {
            return memo[i];
        }

        // Recursive relation evaluation to get the optimal answer.
        int ans = Math.max(this.robFrom(i + 1, nums, memo), this.robFrom(i + 2, nums, memo) + nums[i]);

        // Cache for future use.
        memo[i] = ans;

        return ans;
    }
}
