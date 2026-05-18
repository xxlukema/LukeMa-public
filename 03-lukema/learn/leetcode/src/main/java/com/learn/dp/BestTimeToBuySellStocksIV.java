package com.learn.dp;


import java.util.Arrays;

import org.junit.jupiter.api.Assertions;

import lombok.extern.log4j.Log4j2;


/**
 * LC - 188 - Best Time To Buy Sell Stocks IV
 *
 * Hard
 *
 * You are given an integer array prices where prices[i] is the price of a given stock on the ith day, and an integer k.
 *
 * Find the maximum profit you can achieve. You may complete at most k transactions.
 *
 * Note: You may not engage in multiple transactions simultaneously (i.e., you must sell the stock before you buy again).
 *
 * Example 1:
 * Input: k = 2, prices = [2,4,1]
 * Output: 2
 * Explanation: Buy on day 1 (price = 2) and sell on day 2 (price = 4), profit = 4-2 = 2.
 *
 * Example 2:
 * Input: k = 2, prices = [3,2,6,5,0,3]
 * Output: 7
 * Explanation: Buy on day 2 (price = 2) and sell on day 3 (price = 6), profit = 6-2 = 4. Then buy on day 5 (price = 0) and sell on day 6 (price = 3), profit = 3-0 = 3.
 *
 * Constraints:
 *     1 <= k <= 100
 *     1 <= prices.length <= 1000
 *     0 <= prices[i] <= 1000
 */
@Log4j2
public class BestTimeToBuySellStocksIV {

    public static void main(String[] args) {

        /**
         * Expect: 7
         */
        final int k = 1;
        final int[] prices = { 3, 2, 6, 5, 0, 3, 1, 2 };

        BestTimeToBuySellStocksIV bestTimeToBuySellStocksIV = new BestTimeToBuySellStocksIV();

        var maxProfitYouTube = bestTimeToBuySellStocksIV.maxProfitYouTube(k, prices);
        log.debug("Best Time To Buy Sell Stocks IV: {}", () -> maxProfitYouTube);
        log.debug("Best Time To Buy Sell Stocks IV {} OK", () -> "maxProfitYouTube");

        var maxProfitLuke = bestTimeToBuySellStocksIV.maxProfitLuke(k, prices);
        Assertions.assertEquals(maxProfitYouTube, maxProfitLuke);
        log.debug("Best Time To Buy Sell Stocks IV {} OK", () -> "maxProfitLuke");

    }

    public int maxProfitLuke(int k, int[] prices) {
        if (k == 0 || prices == null || prices.length < 2) {
            return 0;
        }

        final int N = prices.length;

        if (k >= N / 2) {
            int profit = 0;
            for (int i = 0, n = N - 1; i < n; i++) {
                if (prices[i + 1] > prices[i]) {
                    profit += prices[i + 1] - prices[i];
                }
            }

            return profit;
        }

        final int[] profits = new int[k];

        for (int day = 1; day < N; day++) {
            for (int trans = 1; trans < k; trans++) {

                /*
                 * price drop: no transaction
                 */
                if (prices[day] <= prices[day - 1]) {
                    profits[trans] = profits[trans - 1];
                } else {
                    profits[trans] = profits[trans - 1] + (prices[day] - prices[day - 1]);

                }
            }
        }

        return profits[k - 1];
    }

    /**
     * YouTube - https://www.youtube.com/watch?v=ZRK5t8svQ9o
     *
     * Runtime: 5 ms Beats 58.27%
     * Memory: 40.8 MB Beats 93.70%
     *
     * Time: O(N) if (2k >= N)
     * Time: O(N * k) if 2k < N
     * Space: O(k)
     */
    public int maxProfitYouTube(int k, int[] prices) {
        if (k == 0 || prices == null || prices.length < 2) {
            return 0;
        }

        final int N = prices.length;

        if (k >= N / 2) {
            int profit = 0;
            for (int i = 0, n = N - 1; i < n; i++) {
                if (prices[i + 1] > prices[i]) {
                    profit += prices[i + 1] - prices[i];
                }
            }

            return profit;
        }

        final int[] costs = new int[k];
        Arrays.fill(costs, 1_000_000);
        final int[] profits = new int[k];

        for (int i = 0; i < N; i++) {
            for (int t = 0; t < k; t++) {
                costs[t] = Math.min(costs[t], t == 0 ? prices[i] : prices[i] - profits[t - 1]);
                profits[t] = Math.max(profits[t], prices[i] - costs[t]);
            }
        }

        log.debug("prices: {}", prices);
        log.debug("buys: {}", costs);
        log.debug("sells: {}", profits);

        return profits[k - 1];
    }
}
