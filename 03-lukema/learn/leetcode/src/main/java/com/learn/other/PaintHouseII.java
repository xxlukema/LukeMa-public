package com.learn.other;


import java.util.stream.IntStream;

import lombok.extern.log4j.Log4j2;


/**
 * LC-264 Paint House II
 *
 * Hard
 *
 * There are a row of n houses, each house can be painted with one of the k colors. The cost of painting each house with a certain
 * color is different. You have to paint all the houses such that no two adjacent houses have the same color.
 *
 * The cost of painting each house with a certain color is represented by an n x k cost matrix costs.
 *
 *     For example, costs[0][0] is the cost of painting house 0 with color 0; costs[1][2] is the cost of painting house 1 with color 2, and so on...
 *
 * Return the minimum cost to paint all houses.
 *
 * Example 1:
 * Input: costs = [[1,5,3],[2,9,4]]
 * Output: 5
 * Explanation:
 * Paint house 0 into color 0, paint house 1 into color 2. Minimum cost: 1 + 4 = 5;
 * Or paint house 0 into color 2, paint house 1 into color 0. Minimum cost: 3 + 2 = 5.
 *
 * Example 2:
 * Input: costs = [[1,3],[2,4]]
 * Output: 5
 *
 * Constraints:
 *     costs.length == n
 *     costs[i].length == k
 *     1 <= n <= 100
 *     2 <= k <= 20
 *     1 <= costs[i][j] <= 20
 *
 * Follow up: Could you solve it in O(nk) runtime?
 */
@Log4j2
public class PaintHouseII {

    public static void main(String[] args) {

        /**
         * Expected: 5
         */
        final int[][] costs = { { 1, 5, 3 }, { 2, 9, 4 } };

        /**
         * Expected: 5
         */
        // final int[][] costs = { { 1, 3 }, { 2, 4 } };

        PaintHouseII paintHouseII = new PaintHouseII();

        var ret = paintHouseII.minCostII(costs);
        log.debug("Paint House II: {}", () -> ret);
        log.debug("Paint House II {} OK", () -> "ret");
    }

    /**
     * Luke - dp
     *
     * Runtime: 14 ms Beats 21.34%
     * Memory: 46.3 MB Beats 37.35%
     *
     * Time: O(N * k * k)
     * Space: O(N * k)
     */
    public int minCostII(int[][] costs) {
        if (costs == null || costs.length == 0 || costs[0].length == 0) {
            return 0;
        }

        final int lenHouses = costs.length;
        final int lenColors = costs[0].length;

        final int[][] dp = new int[lenHouses][lenColors];

        /**
         * 1st house costs using different colors
         */
        for (int c = 0; c < lenColors; c++) {
            dp[0][c] = costs[0][c];
        }

        /**
         * starting from 2nd house
         *
         * Time: O(N * k * k)
         */
        for (int h = 1; h < lenHouses; h++) {
            /**
             * total cost of painting second house with different colors
             */
            for (int c = 0; c < lenColors; c++) {
                int min = Integer.MAX_VALUE;
                for (int d = 0; d < lenColors; d++) {
                    if (d == c) {
                        continue;
                    }

                    if (dp[h - 1][d] < min) {
                        min = dp[h - 1][d];
                    }
                }

                dp[h][c] = costs[h][c] + min;
            }
        }

        // DpUtils.print(dp);

        return IntStream.of(dp[lenHouses - 1]).min().getAsInt();
    }

    /**
     * Follow up: Could you solve it in O(nk) runtime?
     *
     * Keep min record of dp[house - 1][min] cost and color. Also keep record of next min record dp[house - 1][nextMin]
     */
}
