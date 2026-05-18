package com.learn.dp;


import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Assertions;

import lombok.extern.log4j.Log4j2;


/**
 * LC-309 Best Time to Buy and Sell Stock with Cooldown
 *
 * Medium
 *
 * You are given an array prices where prices[i] is the price of a given stock on the ith day.
 *
 * Find the maximum profit you can achieve. You may complete as many transactions as you like (i.e., buy one and sell one share of the
 * stock multiple times) with the following restrictions:
 *
 *     After you sell your stock, you cannot buy stock on the next day (i.e., cooldown one day).
 *
 * Note: You may not engage in multiple transactions simultaneously (i.e., you must sell the stock before you buy again).
 *
 * Example 1:
 * Input: prices = [1,2,3,0,2]
 * Output: 3
 * Explanation: transactions = [buy, sell, cooldown, buy, sell]
 *
 * Example 2:
 * Input: prices = [1]
 * Output: 0
 *
 * Constraints:
 *     1 <= prices.length <= 5000
 *     0 <= prices[i] <= 1000
 */
@Log4j2
public class BestTimeToBuyAndSellStockWithCooldown {

    public static void main(String[] args) {

        /**
         * Expected: 3
         */
        final int[] prices = { 1, 2, 3, 0, 2 };

        /**
         * Expected: 3
         */
        // final int[] prices = { 1, 2, 4 };

        BestTimeToBuyAndSellStockWithCooldown bestTimeToBuyAndSellStockWithCooldown = new BestTimeToBuyAndSellStockWithCooldown();

        var maxProfitLukeDp = bestTimeToBuyAndSellStockWithCooldown.maxProfitLukeDp(prices);
        log.debug("Best Time to Buy and Sell Stock with Cooldown: {}", () -> maxProfitLukeDp);
        log.debug("Best Time to Buy and Sell Stock with Cooldown {} OK", () -> "maxProfitLukeDp");

        var maxProfitYouTube = bestTimeToBuyAndSellStockWithCooldown.maxProfitYouTube(prices);
        Assertions.assertEquals(maxProfitLukeDp, maxProfitYouTube);
        log.debug("Best Time to Buy and Sell Stock with Cooldown {} OK", () -> "maxProfitYouTube");

        var maxProfitLukeBacktrack = bestTimeToBuyAndSellStockWithCooldown.maxProfitLukeBacktrack(prices);
        Assertions.assertEquals(maxProfitLukeDp, maxProfitLukeBacktrack);
        log.debug("Best Time to Buy and Sell Stock with Cooldown {} OK", () -> "maxProfitLukeBacktrack");

        var maxProfitLukeBacktrackMemo = bestTimeToBuyAndSellStockWithCooldown.maxProfitLukeBacktrackMemo(prices);
        Assertions.assertEquals(maxProfitLukeDp, maxProfitLukeBacktrackMemo);
        log.debug("Best Time to Buy and Sell Stock with Cooldown {} OK", () -> "maxProfitLukeBacktrackMemo");

    }

    /**
     * Luke - Top Down - Backtrack
     *
     * https://www.youtube.com/watch?v=yP-1vo7vdz4
     *
     * Time Limit Exceeded
     *
     * Time: O(2 ^ N)
     * Space: O(N)
     */
    public int maxProfitLukeBacktrack(int[] prices) {
        return backtrack(prices, 0, false);
    }

    /**
     * Luke - Top Down
     *
     * https://www.youtube.com/watch?v=yP-1vo7vdz4
     *
     * Time Limit Exceeded
     *
     * Time: O(2 ^ N)
     * Space: O(N)
     */
    private int backtrack(int[] prices, int idx, boolean bought) {
        if (idx >= prices.length) {
            return 0;
        }

        // option 1: sell
        int profit1 = 0;

        // option 2: buy
        int profit2 = 0;

        int price = prices[idx];

        if (bought) {
            /**
             * sell
             */
            // option 1: sell
            // cooldown for a day
            profit1 = price + backtrack(prices, idx + 2, false);
        } else {
            /**
             * buy
             */
            profit2 = -price + backtrack(prices, idx + 1, true);
        }

        // option3: hold
        int profit3 = backtrack(prices, idx + 1, bought);

        return Collections.max(List.of(profit1, profit2, profit3));
    }

    /**
     * Luke - Top Down Memo
     *      - Trick 1: array is much faster than record/Map for TopDowm memo (25 times faster)
     *      - Trick 2: Math.max() is faster than Collections.max(List.of(...))
     *
     * https://www.youtube.com/watch?v=yP-1vo7vdz4
     *
     * Runtime: 24 ms Beats 8.24%
     * Memory: 43 MB Beats 10.29%
     *
     * Time: O(N)
     * Space: O(N)
     */
    public int maxProfitLukeBacktrackMemo(int[] prices) {

        final Map<Node, Integer> memo = new HashMap<>();

        return backtrackMemo(prices, 0, false, memo);
    }

    public record Node(int idx, boolean bought) {
    }

    /**
     * Luke - Top Down Memo
     *
     * Time: O(N)
     * Space: O(N)
     */
    private int backtrackMemo(int[] prices, int idx, boolean bought, final Map<Node, Integer> memo) {
        if (idx >= prices.length) {
            return 0;
        }

        Node node = new Node(idx, bought);

        if (memo.containsKey(node)) {
            return memo.get(node);
        }

        // option 1: sell
        int profit1 = 0;

        // option 2: buy
        int profit2 = 0;

        int price = prices[idx];

        if (bought) {
            /**
             * sell
             */
            // option 1: sell
            // cooldown for a day
            profit1 = price + backtrackMemo(prices, idx + 2, false, memo);
        } else {
            /**
             * buy
             */
            profit2 = -price + backtrackMemo(prices, idx + 1, true, memo);
        }

        // option3: hold
        int profit3 = backtrackMemo(prices, idx + 1, bought, memo);

        /**
         * Trick 2: Math.max() is faster than Collections.max(List.of(...)). Compare:
         *
         * With Collections.max(List.of(...))
         * Runtime: 25 ms Beats 7.97%
         * Memory: 43.2 MB Beats 6.97%
         * vs
         * With Math.max()
         * Runtime: 24 ms Beats 8.24%
         * Memory: 43 MB Beats 10.29%
         */
        // int max = Collections.max(List.of(profit1, profit2, profit3));
        int max = Math.max(profit1, Math.max(profit2, profit3));

        memo.put(node, max);

        return max;
    }

    /**
     * Luke - Top Down Memo
     *      - Trick 1: array is much faster than record/Map for TopDowm memo (25 times faster)
     *      - Trick 2: Math.max() is faster than Collections.max(List.of(...))
     *
     * https://www.youtube.com/watch?v=yP-1vo7vdz4
     *
     * Runtime: 25 ms Beats 7.97%
     * Memory: 43.2 MB Beats 6.97%
     * vs
     * Improved (Use array instead of record)
     * Runtime: 3 ms Beats 24.20%
     * Memory: 42.2 MB Beats 47.17%
     * vs
     * Improve + Math.max()
     * Runtime: 1 ms Beats 77.40%
     * Memory: 42 MB Beats 51.46%
     *
     * Time: O(N)
     * Space: O(N)
     */
    public int maxProfitLukeBacktrackMemoImprove(int[] prices) {

        final Integer[][] memo = new Integer[prices.length][2];

        return backtrackMemoImprove(prices, 0, 0, memo);
    }

    /**
     * Luke - Top Down Memo
     *
     * bought: 1
     * not bought: 0
     *
     * Time: O(N)
     * Space: O(N)
     */
    private int backtrackMemoImprove(int[] prices, int idx, int bought, final Integer[][] memo) {
        if (idx >= prices.length) {
            return 0;
        }

        if (memo[idx][bought] != null) {
            return memo[idx][bought];
        }

        // option 1: sell
        int profit1 = 0;

        // option 2: buy
        int profit2 = 0;

        int price = prices[idx];

        if (bought == 1) {
            /**
             * sell
             */
            // option 1: sell
            // cooldown for a day
            profit1 = price + backtrackMemoImprove(prices, idx + 2, 0, memo);
        } else {
            /**
             * buy
             */
            profit2 = -price + backtrackMemoImprove(prices, idx + 1, 1, memo);
        }

        // option3: hold
        int profit3 = backtrackMemoImprove(prices, idx + 1, bought, memo);

        /**
         * Trick 2: Math.max() is faster than Collections.max(List.of(...)). Compare:
         *
         * With Collections.max(List.of(...))
         * Runtime: 3 ms Beats 24.20%
         * Memory: 42.2 MB Beats 47.17%
         * vs
         * With Math.max()
         * Runtime: 1 ms Beats 77.40%
         * Memory: 42 MB Beats 51.46%
         */
        // memo[idx][bought] = Collections.max(List.of(profit1, profit2, profit3));
        memo[idx][bought] = Math.max(profit1, Math.max(profit2, profit3));

        return memo[idx][bought];
    }

    /**
     * Luke - DP
     *
     * https://www.youtube.com/watch?v=jNy8yM0NBdw
     *
     * Everyday 3 states: BUY/SELL/COOLDOWN
     * Need 3 arrays to hold today's different choices
     * Next day's states depends on today's choices
     *
     * Runtime: 0 ms Beats 100%
     * Memory: 40.4 MB Beats 83.87%
     *
     * Time: O(N)
     * Space: O(N)
     */
    public int maxProfitLukeDp(int[] prices) {

        final int LEN = prices.length;

        int[] dpBuy = new int[LEN];
        int[] dpSell = new int[LEN];
        int[] dpCooldown = new int[LEN];

        /**
         * use 0 credit to buy
         */
        dpBuy[0] = 0 - prices[0];

        /**
         * nothing to sell
         */
        dpSell[0] = 0;

        /**
         * cooldown
         */
        dpCooldown[0] = 0;

        for (int i = 1; i < prices.length; i++) {
            /**
             * buy after sell and cooldown only
             */
            dpBuy[i] = Math.max(dpBuy[i - 1], dpCooldown[i - 1] - prices[i]);

            /**
             * only can sell if stock is bought.
             */
            dpSell[i] = dpBuy[i - 1] + prices[i];

            /**
             * after sell cooldown
             */
            dpCooldown[i] = Math.max(dpCooldown[i - 1], dpSell[i - 1]);
        }

        // return Collections.max(Arrays.asList(dpBuy[LEN - 1], dpSell[LEN - 1], dpCooldown[LEN - 1]));
        return Math.max(dpCooldown[LEN - 1], dpSell[LEN - 1]);
    }

    /**
     * Luke - DP Improved
     *
     * Everyday 3 states: BUY/SELL/COOLDOWN
     * Need 3 arrays to hold today's different choices
     * Next day's states depends on today's choices
     *
     * Runtime: 0 ms Beats 100%
     * Memory: 40.4 MB Beats 83.87%
     * vs
     * After improvement:
     * Runtime: 0 ms Beats 100%
     * Memory: 40.6 MB Beats 73.35%
     *
     * Time: O(N)
     * Space: O(1)
     */
    public int maxProfitLukeDpImproveMemory(int[] prices) {

        /**
         * use 0 credit to buy
         */
        int dpBuy = 0 - prices[0];

        /**
         * nothing to sell
         */
        int dpSell = 0;

        /**
         * cooldown
         */
        int dpCooldown = 0;

        for (int i = 1; i < prices.length; i++) {
            /**
             * buy after sell and cooldown only
             */
            int dpBuy2 = Math.max(dpBuy, dpCooldown - prices[i]);

            /**
             * only can sell if stock is bought.
             */
            int dpSell2 = dpBuy + prices[i];

            /**
             * after sell cooldown
             */
            int dpCooldown2 = Math.max(dpCooldown, dpSell);

            dpBuy = dpBuy2;
            dpSell = dpSell2;
            dpCooldown = dpCooldown2;
        }

        return Math.max(dpCooldown, dpSell);
    }

    /**
     * YouTube: https://www.youtube.com/watch?v=-OxZTJtn8Vs
     *
     * DP - Bottom Up
     */
    public int maxProfitYouTube(int[] prices) {
        final int LEN = prices.length;

        int sold = Integer.MIN_VALUE;
        int bought = Integer.MIN_VALUE;
        int cooldown = 0;

        for (int i = 0; i < LEN; i++) {
            int sold2 = bought + prices[i];
            int bought2 = Math.max(bought, cooldown - prices[i]);
            int cooldown2 = Math.max(cooldown, sold);

            sold = sold2;
            bought = bought2;
            cooldown = cooldown2;
        }

        return Math.max(sold, cooldown);
    }
}
