package com.learn.other;


import java.util.stream.IntStream;

import lombok.extern.log4j.Log4j2;


/**
 * LC-256 Paint House
 *
 * Medium
 *
 * There is a row of n houses, where each house can be painted one of three colors: red, blue, or green. The cost of painting each house with a
 * certain color is different. You have to paint all the houses such that no two adjacent houses have the same color.
 *
 * The cost of painting each house with a certain color is represented by an n x 3 cost matrix costs.
 *
 *     For example, costs[0][0] is the cost of painting house 0 with the color red; costs[1][2] is the cost of painting house 1 with color green, and so on...
 *
 * Return the minimum cost to paint all houses.
 *
 * Example 1:
 * Input: costs = [[17,2,17],[16,16,5],[14,3,19]]
 * Output: 10
 * Explanation: Paint house 0 into blue, paint house 1 into green, paint house 2 into blue.
 * Minimum cost: 2 + 5 + 3 = 10.
 *
 * Example 2:
 * Input: costs = [[7,6,2]]
 * Output: 2
 *
 * Constraints:
 *     costs.length == n
 *     costs[i].length == 3
 *     1 <= n <= 100
 *     1 <= costs[i][j] <= 20
 */
@Log4j2
public class PaintHouse {

    public static void main(String[] args) {

        /**
         * Expected: 10
         */
        final int[][] costs = {
                { 17, 2, 17 },
                { 16, 16, 5 },
                { 14, 3, 19 } };

        PaintHouse paintHouse = new PaintHouse();

        var minCostDp = paintHouse.minCostDp(costs);
        log.debug("Paint House: {}", () -> minCostDp);
        log.debug("Paint House {} OK", () -> "minCostDp");

    }

    /**
     * LC - https://www.youtube.com/watch?v=-w67-4tnH5U
     *
     * Runtime: 12 ms Beats 7.65%
     * Memory: 43.8 MB Beats 24.5%
     *
     * Time: O(N)
     * Space: O(N)
     */
    public int minCostDp(int[][] costs) {
        if (costs == null || costs.length == 0) {
            return 0;
        }

        final int houseLen = costs.length;
        final int colorLen = costs[0].length;

        /**
         * Space: O(houseLen) * O(colorLen)
         */
        final int[][] dp = new int[houseLen][colorLen];

        for (int i = 0; i < colorLen; i++) {
            dp[0][i] = costs[0][i];
        }

        /**
         * Time: O(houseLen) * O(colorLen)
         */
        for (int h = 1; h < houseLen; h++) {
            for (int c = 0; c < colorLen; c++) {
                int lastDpMin = Integer.MAX_VALUE;
                for (int lc = 0; lc < colorLen; lc++) {
                    if (lc == c) {
                        continue;
                    }
                    lastDpMin = Math.min(lastDpMin, dp[h - 1][lc]);
                }

                dp[h][c] = costs[h][c] + lastDpMin;
            }
        }

        /**
         * Time: O(colorLen)
         * Space: O(1)
         */
        return IntStream.of(dp[houseLen - 1]).min().getAsInt();
    }

    /**
     * LC - https://www.youtube.com/watch?v=-w67-4tnH5U
     *
     * Runtime: 5 ms Beats 20.50%
     * Memory: 44 MB Beats 14.98%
     *
     * Time: O(N)
     * Space: O(1)
     */
    public int minCostDpImprovedSpace(int[][] costs) {
        if (costs == null || costs.length == 0) {
            return 0;
        }

        final int houseLen = costs.length;
        final int colorLen = costs[0].length;

        /**
         * Space: O(colorLen)
         */
        final int[] dp = new int[colorLen];

        for (int i = 0; i < colorLen; i++) {
            dp[i] = costs[0][i];
        }

        /**
         * Time: O(houseLen) * O(colorLen)
         */
        for (int h = 1; h < houseLen; h++) {
            int tmp0 = dp[0];
            int tmp1 = dp[1];
            int tmp2 = dp[2];

            dp[0] = costs[h][0] + Math.min(tmp1, tmp2);
            dp[1] = costs[h][1] + Math.min(tmp0, tmp2);
            dp[2] = costs[h][2] + Math.min(tmp0, tmp1);
        }

        /**
         * Time: O(colorLen)
         * Space: O(1)
         */
        return IntStream.of(dp).min().getAsInt();
    }

}
