package com.learn.dp;


import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;

import lombok.extern.log4j.Log4j2;


/**
 * LC-312 Burst Ballons
 *
 * Hard
 *
 * You are given n balloons, indexed from 0 to n - 1. Each balloon is painted with a number on it represented by an array nums.
 * You are asked to burst all the balloons.
 *
 * If you burst the ith balloon, you will get nums[i - 1] * nums[i] * nums[i + 1] coins. If i - 1 or i + 1 goes out of bounds
 * of the array, then treat it as if there is a balloon with a 1 painted on it.
 *
 * Return the maximum coins you can collect by bursting the balloons wisely.
 *
 * Example 1:
 * Input: nums = [3,1,5,8]
 * Output: 167
 * Explanation:
 * nums = [3,1,5,8] --> [3,5,8] --> [3,8] --> [8] --> []
 * coins =  3*1*5    +   3*5*8   +  1*3*8  + 1*8*1 = 167
 *
 * Example 2:
 * Input: nums = [1,5]
 * Output: 10
 *
 * Constraints:
 * n == nums.length
 * 1 <= n <= 300
 * 0 <= nums[i] <= 100
 */
@Log4j2
public class BurstBallons {

    public static void main(String[] args) {

        /**
         * expected: 167
         */
        final int[] nums = { 3, 1, 5, 8 };

        /**
         * expected: 10
         */
        // final int[] nums = { 1, 5 };

        /**
         * expected: TLE 1654
         */
        // final int[] nums = { 7, 9, 8, 0, 7, 1, 3, 5, 5, 2, 3 };

        BurstBallons burstBallons = new BurstBallons();

        var maxCoinsLukeBrutal = burstBallons.maxCoinsLukeBrutal(nums);
        log.debug("Burst Ballons: {}", () -> maxCoinsLukeBrutal);
        log.debug("Burst Ballons {} OK", () -> "maxCoinsLukeBrutal");

        var maxCoinsLukeDpMemo = burstBallons.maxCoinsLukeDpMemo(nums);
        Assertions.assertEquals(maxCoinsLukeBrutal, maxCoinsLukeDpMemo);
        log.debug("Burst Ballons {} OK", () -> "maxCoinsLukeDpMemo");

        var maxCoinsLcDp = burstBallons.maxCoinsLcDp(nums);
        Assertions.assertEquals(maxCoinsLukeBrutal, maxCoinsLcDp);
        log.debug("Burst Ballons {} OK", () -> "maxCoinsLcDp");
    }

    /**
     * Luke - brutal
     *
     * Time Limit Exceeded
     *
     * Time: O(N!)
     * Space: O(N ^ 2)
     */
    public int maxCoinsLukeBrutal(int[] nums) {

        int maxCoins = 0;

        final List<Integer> ballons = new ArrayList<>();
        for (int num : nums) {
            ballons.add(num);
        }

        for (int i = 0; i < nums.length; i++) {
            int sub = backtrackLukeBrutal(ballons, i, 0);
            maxCoins = Math.max(maxCoins, sub);
        }

        return maxCoins;
    }

    private int backtrackLukeBrutal(List<Integer> ballons, int idx, int coins) {

        int points = (idx - 1 < 0 ? 1 : ballons.get(idx - 1)) * ballons.get(idx) * (idx + 1 >= ballons.size() ? 1 : ballons.get(idx + 1));
        coins += points;

        int value = ballons.remove(idx);

        if (ballons.isEmpty()) {
            return coins;
        }

        int max = 0;
        for (int i = 0; i < ballons.size(); i++) {
            final List<Integer> remainingBallons = new ArrayList<>(ballons);
            int sub = backtrackLukeBrutal(remainingBallons, i, coins);
            max = Math.max(max, sub);
        }

        ballons.add(idx, value);
        coins -= points;

        return max;
    }

    /**
     * LC - Top Down
     *      Trick 1: prepend and suspend nums with "1"s to save boundary checks
     *      Trick 2: The last ballon to burst is "idx"
     *      Trick 3: [left, i], [i], [i, right] inclusive
     *      Trick 4: !Important: Not "+ nums[i - 1] * nums[i] * nums[i + 1]", because i is the last to burst.
     *
     *
     * https://leetcode.com/problems/burst-balloons/solutions/1659162/java-dp-divide-and-conquer-sliding-window-detailed-explanation-using-image/
     *
     * We can improve the performance sightly by handling those special cases one by one. However, please notice that this optimization does not
     * improve the time complexity and can not speed up too much if the input is highly randomized.
     *
     * Runtime: 126 ms Beats 57.65%
     * Memory: 42.9 MB Beats 50.43%
     *
     * Time: O(N ^ 3) --- There are O(N ^ 2) states. For each state, determining the maximum coins requires iterating over all balloons in the
     *                    range [left, right].
     * Space: O(N ^ 2) --- O(N) for stacks to perform recursion, and O(N) to store [1] + nums + [1]
     */
    public int maxCoinsLukeDpMemo(int[] nums) {

        /**
         * Trick 1: prepend and suspend nums with "1"s to save boundary checks
         */
        final int[] newNums = new int[nums.length + 2];

        newNums[0] = 1;
        newNums[newNums.length - 1] = 1;

        for (int i = 0; i < nums.length; i++) {
            newNums[i + 1] = nums[i];
        }

        final Integer[][] memo = new Integer[newNums.length][newNums.length];

        return backtrackLukeDpMemo(newNums, 0, newNums.length - 1, memo);
    }

    /**
     * Trick 2: The last ballon to burst is "idx"
     */
    private int backtrackLukeDpMemo(int[] nums, int left, int right, Integer[][] memo) {

        if (memo[left][right] != null) {
            return memo[left][right];
        }

        /**
         * left + 1 == right
         */
        int points = 0;

        for (int i = left + 1; i < right; i++) {
            /**
             * Trick 3: [left, i], [i], [i, right] inclusive
             */
            int sub = backtrackLukeDpMemo(nums, left, i, memo)
                    /**
                     * Trick 4: !Important: Not "+ nums[i - 1] * nums[i] * nums[i + 1]", because i is the last to burst.
                     */
                    + nums[left] * nums[i] * nums[right]
                    + backtrackLukeDpMemo(nums, i, right, memo);

            points = Math.max(points, sub);
        }

        return memo[left][right] = points;
    }

    /**
     * LC - Bottom Up
     *
     * https://leetcode.com/problems/burst-balloons/solutions/1659162/java-dp-divide-and-conquer-sliding-window-detailed-explanation-using-image/
     */
    public int maxCoinsLcDp(int[] nums) {

        /**
         * Trick 1: prepend and suspend nums with "1"s to save boundary checks
         */
        final int LEN = nums.length + 2;
        final int[] newNums = new int[LEN];

        newNums[0] = 1;
        newNums[LEN - 1] = 1;

        for (int i = 0; i < nums.length; i++) {
            newNums[i + 1] = nums[i];
        }

        final int[][] dp = new int[LEN][LEN];

        /**
         * Trick 2: i is the last ballon to burst
         */
        // do not include the first one and the last one
        // since they are both fake balloons added by ourselves and we can not burst
        // them
        for (int left = LEN - 2; left >= 1; left--) {
            for (int right = left; right <= LEN - 2; right++) {
                // find the last burst one in newNums[left]...newNums[right]
                for (int i = left; i <= right; i++) {
                    // newNums[i] is the last burst one
                    int gain = newNums[left - 1] * newNums[i] * newNums[right + 1];
                    // recursively call left side and right side
                    int remaining = dp[left][i - 1] + dp[i + 1][right];
                    // update
                    dp[left][right] = Math.max(remaining + gain, dp[left][right]);
                    DpUtils.print(dp);
                }
            }
        }
        // burst newNums[1]...newNums[n-2], excluding the first one and the last one
        return dp[1][LEN - 2];
    }

}
