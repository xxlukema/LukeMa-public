package com.learn.dp;


import lombok.extern.log4j.Log4j2;


/**
 * LC - 213- House Robber II
 *
 * Medium
 *
 * You are a professional robber planning to rob houses along a street. Each house has a certain amount of money stashed. All houses at this place
 * are arranged in a circle. That means the first house is the neighbor of the last one. Meanwhile, adjacent houses have a security system connected,
 * and it will automatically contact the police if two adjacent houses were broken into on the same night.
 *
 * Given an integer array nums representing the amount of money of each house, return the maximum amount of money you can rob tonight without alerting the police.
 *
 * Example 1:
 * Input: nums = [2,3,2]
 * Output: 3
 * Explanation: You cannot rob house 1 (money = 2) and then rob house 3 (money = 2), because they are adjacent houses.
 *
 * Example 2:
 * Input: nums = [1,2,3,1]
 * Output: 4
 * Explanation: Rob house 1 (money = 1) and then rob house 3 (money = 3).
 * Total amount you can rob = 1 + 3 = 4.
 *
 * Example 3:
 * Input: nums = [1,2,3]
 * Output: 3
 *
 * Constraints:
 *     1 <= nums.length <= 100
 *     0 <= nums[i] <= 1000
 */
@Log4j2
public class HouseRobberII {

    public static void main(String[] args) {

        // final int[] nums = { 2, 7, 9, 3, 1 };

        /**
         * Output: 4
         */
        // final int[] nums = { 1, 2, 3, 1 };

        /**
         * Output: 3
         */
        // final int[] nums = { 2, 3, 2 };

        /**
         * Output: 340
         */
        final int[] nums = { 200, 3, 140, 20, 10 };

        HouseRobberII houseRobberII = new HouseRobberII();

        var ret = houseRobberII.rob(nums);

        log.debug("House Robber II: {}", () -> ret);
        log.debug("House Robber II {} OK", () -> "ret");
    }

    /**
     * Luke - Force either side value to 0
     *
     * Runtime: 1 ms, faster than 39.84% of Java online submissions for House Robber II.
     * Memory Usage: 41.7 MB, less than 33.35% of Java online submissions for House Robber II.
     *
     * Time: O(N)
     * Space; O(1)
     */
    public int rob(final int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        final int N = nums.length;

        if (N == 1) {
            return nums[0];
        }

        /**
         * Force 1st house value 0.
         */
        int tmp = nums[0];
        nums[0] = 0;
        int max1 = doRob(nums);
        /**
         * Restore 1st house value
         */
        nums[0] = tmp;

        /**
         * Force last house value 0.
         */
        tmp = nums[N - 1];
        nums[N - 1] = 0;
        int max2 = doRob(nums);
        /**
         * Restore last house value
         */
        nums[N - 1] = tmp;

        return Math.max(max1, max2);
    }

    private int doRob(final int[] nums) {

        int leftLeft = nums[0];
        int left = Math.max(leftLeft, nums[1]);

        int max = Math.max(leftLeft, left);

        for (int i = 2, n = nums.length; i < n; i++) {
            max = Math.max(left, leftLeft + nums[i]);
            leftLeft = left;
            left = max;
        }

        return max;
    }
}
