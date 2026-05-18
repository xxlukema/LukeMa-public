package com.learn.slidingwindow;


import org.junit.jupiter.api.Assertions;

import lombok.extern.log4j.Log4j2;


/**
 * LC - 42 - Trapping Rain Water
 *
 * Hard
 *
 * Given n non-negative integers representing an elevation map where the width of each bar is 1, compute how much water it can trap after raining.
 *
 * Example 1:
 * Input: height = [0,1,0,2,1,0,1,3,2,1,2,1]
 * Output: 6
 * Explanation: The above elevation map (black section) is represented by array [0,1,0,2,1,0,1,3,2,1,2,1]. In this case, 6 units of
 * rain water (blue section) are being trapped.
 *
 * Example 2:
 * Input: height = [4,2,0,3,2,5]
 * Output: 9
 *
 * Constraints:
 *     n == height.length
 *     1 <= n <= 2 * 104
 *     0 <= height[i] <= 105
 */
@Log4j2
public class TrappingRainWater {

    public static void main(String[] args) {

        /**
         * Expected: 6
         */
        final int[] height1 = { 0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1 };

        /**
         * Expected: 9
         */
        final int[] height2 = { 4, 2, 0, 3, 2, 5 };

        TrappingRainWater trappingRainWater = new TrappingRainWater();

        /*
        var ret = trappingRainWater.trapDp(height);
        log.debug("Trapping Rain Water: {}", () -> ret);

        Assertions.assertEquals(6, ret);
        log.debug("Trapping Rain Water {} OK", () -> "ret");
        */

        var trapLukeTwoPointer1 = trappingRainWater.trapLukeTwoPointer(height1);
        log.debug("Trapping Rain Water: {}", trapLukeTwoPointer1);
        Assertions.assertEquals(6, trapLukeTwoPointer1);

        var trapLukeTwoPointer2 = trappingRainWater.trapLukeTwoPointer(height2);
        log.debug("Trapping Rain Water: {}", trapLukeTwoPointer2);
        Assertions.assertEquals(9, trapLukeTwoPointer2);

        log.debug("Trapping Rain Water {} OK", () -> "trapLukeTwoPointer");

        var trapLukeDp1 = trappingRainWater.trapLukeDp(height1);
        log.debug("Trapping Rain Water: {}", trapLukeDp1);
        Assertions.assertEquals(6, trapLukeDp1);

        var trapLukeDp2 = trappingRainWater.trapLukeDp(height2);
        log.debug("Trapping Rain Water: {}", trapLukeDp2);
        Assertions.assertEquals(9, trapLukeDp2);

        log.debug("Trapping Rain Water {} OK", () -> "trapLukeDp");
    }

    /**
     * Luke - Two Pointers
     *
     * Runtime: 1 ms Beats 99.78%
     * Memory: 48.1 MB Beats 79.98%
     *
     * Time: O(N)
     * Space: O(1)
     */
    public int trapLukeTwoPointer(int[] height) {

        if (height == null || height.length < 2) {
            return 0;
        }

        final int LEN = height.length;

        int left = 0;
        int right = LEN - 1;

        int total = 0;

        while (left < right) {
            if (height[left] < height[right]) {
                int h = height[left];
                while (height[++left] < h) {
                    total += h - height[left];
                }
            } else {
                int h = height[right];
                while (height[--right] < h) {
                    total += h - height[right];
                }
            }
        }

        return total;
    }

    /**
     * Luke - DP
     *
     * Runtime: 318 ms Beats 5.7%
     * Memory: 49.1 MB Beats 32.26%
     *
     * Time: O(N ^ 2)
     * Space: O(1)
     */
    public int trapLukeDp(int[] height) {

        if (height == null || height.length < 2) {
            return 0;
        }

        final int LEN = height.length;

        final int[] dp = new int[LEN];

        int left = 0;
        int curr = left + 1;

        while (curr < LEN) {

            while (curr < LEN && height[curr] < height[left]) {

                int subSum = 0;
                int maxHeight = 0;

                for (int i = curr; i > left; i--) {
                    maxHeight = Math.max(maxHeight, height[i]);
                    subSum += maxHeight - height[i];
                }

                dp[curr] = dp[left] + subSum;

                // log.debug("---- curr: {}, dp: {}", curr, dp);

                curr++;
            }

            /**
             * right >= LEN || height[right] >= height[left]
             */

            if (curr >= LEN) {
                break;
            } else {
                /**
                 * height[right] >= height[left]
                 */

                int subSum = 0;

                for (int i = curr - 1; i > left; i--) {
                    subSum += height[left] - height[i];
                }

                // log.debug("--=====----- curr: {}, dp: {}, subSum: {}", curr, dp, subSum);

                dp[curr] = dp[left] + subSum;

                left = curr;
                curr++;
            }
        }

        // log.debug("dp: {}", dp);

        return dp[LEN - 1];
    }
}
