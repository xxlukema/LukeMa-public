package com.learn.other;


import org.junit.jupiter.api.Assertions;

import lombok.extern.log4j.Log4j2;


/**
 * LC - 121 - Best Time To Buy And Sell Stock
 *
 * Easy
 *
 * You are given an array prices where prices[i] is the price of a given stock on the ith day.
 * You want to maximize your profit by choosing a single day to buy one stock and choosing a different day in the future to sell that stock.
 * Return the maximum profit you can achieve from this transaction. If you cannot achieve any profit, return 0.
 *
 * Example 1:
 * Input: prices = [7,1,5,3,6,4]
 * Output: 5
 * Explanation: Buy on day 2 (price = 1) and sell on day 5 (price = 6), profit = 6-1 = 5.
 * Note that buying on day 2 and selling on day 1 is not allowed because you must buy before you sell.
 *
 * Example 2:
 * Input: prices = [7,6,4,3,1]
 * Output: 0
 * Explanation: In this case, no transactions are done and the max profit = 0.
 */
@Log4j2
public class BestTimeToBuyAndSellStock {

    public static void main(String[] args) {

        // int[] prices = { 7, 1, 5, 3, 6, 4 };
        // int[] prices = { 7, 6, 4, 3, 1 };
        // int[] prices = { 2, 4, 1 };
        // int[] prices = { 1, 2 };
        int[] prices = { 7, 1, 5, 3, 6, 4 };

        BestTimeToBuyAndSellStock bestTimeToBuyAndSellStock = new BestTimeToBuyAndSellStock();

        log.debug(() -> "Start Test...");

        var retLuke = bestTimeToBuyAndSellStock.maxProfitMemo(prices);
        log.debug("Max profit: {}", () -> retLuke);

        log.debug(() -> "Luke memo OK");

        var retLcBrute = bestTimeToBuyAndSellStock.maxProfitLcBrute(prices);
        Assertions.assertEquals(retLuke, retLcBrute);

        log.debug(() -> "LC brute OK");

        var retLcOnePass = bestTimeToBuyAndSellStock.maxProfitLcOnePass(prices);
        Assertions.assertEquals(retLuke, retLcOnePass);

        log.debug(() -> "LC One Pass OK");

        log.debug(() -> "Test Complete");
    }

    /**
     * Luke - momo
     *
     * Runtime: 4 ms, faster than 32.65% of Java online submissions for Best Time to Buy and Sell Stock.
     * Memory Usage: 86.2 MB, less than 5.01% of Java online submissions for Best Time to Buy and Sell Stock.
     *
     * Time: O(N)
     * Space: O(N)
     */
    public int maxProfitMemo(int[] prices) {
        if (prices.length < 2) {
            return 0;
        }

        final int LEN = prices.length;

        /**
         * memoization
         */
        final int[] maxFromIdxToEnd = new int[LEN];

        int max = Integer.MIN_VALUE;

        for (int i = LEN - 1; i > 0; i--) {
            max = Math.max(max, prices[i]);
            maxFromIdxToEnd[i] = max;
        }

        int left = 0;
        int profit = 0;

        while (left < LEN - 1) {
            profit = Math.max(profit, maxFromIdxToEnd[left + 1] - prices[left]);
            left++;
        }

        return profit;
    }

    /**
     * LC - Brute
     *
     * Time Limit Exceeded
     *
     * Time O(N ^ 2)
     * Space: O(1)
     */
    public int maxProfitLcBrute(int prices[]) {
        int maxprofit = 0;

        for (int i = 0; i < prices.length - 1; i++) {
            int price = prices[i];
            for (int k = i + 1; k < prices.length; k++) {
                if (prices[k] > price) {
                    maxprofit = Math.max(maxprofit, prices[k] - price);
                }
            }
        }

        return maxprofit;
    }

    /**
     * LC - One Pass
     *
     * Runtime: 3 ms, faster than 69.64% of Java online submissions for Best Time to Buy and Sell Stock.
     * Memory Usage: 76.4 MB, less than 81.42% of Java online submissions for Best Time to Buy and Sell Stock.
     *
     * Time: O(N)
     * Space: O(1)
     */
    public int maxProfitLcOnePass(int prices[]) {
        int maxprofit = 0;
        int minPrice = prices[0];

        for (int i = 1; i < prices.length; i++) {
            int price = prices[i];
            if (price < minPrice) {
                minPrice = price;
            } else {
                int profit = price - minPrice;
                maxprofit = Math.max(maxprofit, profit);
            }
        }

        return maxprofit;
    }

}
