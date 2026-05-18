package com.learn.other;


import org.junit.jupiter.api.Assertions;

import lombok.extern.log4j.Log4j2;


/**
 * LC - 122 - Best Time To Buy And Sell Stock II
 *
 * Medium
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
public class BestTimeToBuyAndSellStockII {

    public static void main(String[] args) {

        int[] prices = { 7, 1, 5, 3, 6, 4 };
        // int[] prices = { 7, 6, 4, 3, 1 };
        // int[] prices = { 2, 4, 1 };
        // int[] prices = { 1, 2 };

        BestTimeToBuyAndSellStockII bestTimeToBuyAndSellStockII = new BestTimeToBuyAndSellStockII();

        log.debug(() -> "Start Test...");

        var retLuke = bestTimeToBuyAndSellStockII.maxProfitLukeIterative(prices);
        log.debug("Max profit: {}", () -> retLuke);

        log.debug(() -> "Luke memo OK");

        var retLcOneWay = bestTimeToBuyAndSellStockII.maxProfitLcOneWay(prices);
        Assertions.assertEquals(retLuke, retLcOneWay);

        log.debug(() -> "LC One Way OK");

        var retLcPeakValley = bestTimeToBuyAndSellStockII.maxProfitLcPeakValley(prices);
        Assertions.assertEquals(retLuke, retLcPeakValley);

        log.debug(() -> "LC Peak Valley OK");

        log.debug(() -> "Test Complete");
    }

    /**
     * Luke - Iterative
     *
     * Runtime: 1 ms, faster than 97.55% of Java online submissions for Best Time to Buy and Sell Stock II.
     * Memory Usage: 44.4 MB, less than 37.95% of Java online submissions for Best Time to Buy and Sell Stock II.
     *
     * Time: O(N)
     * Space: O(1)
     */
    public int maxProfitLukeIterative(int[] prices) {

        final int LEN = prices.length;

        int profit = 0;

        for (int left = 0; left < LEN; left++) {
            int pur = prices[left];

            // log.debug("left 1: {}, pur: {}", left, pur);

            while (left < LEN - 1) {
                if (prices[left + 1] <= prices[left]) {
                    pur = prices[left + 1];
                    left++;
                    continue;
                } else {
                    break;
                }
            }

            // log.debug("left 2: {}, pur: {}", left, pur);

            if (left == LEN - 1) {
                break;
            } else {
                int right = left + 1;

                int sell = prices[right];

                while (right < LEN) {
                    if (prices[right] >= prices[right - 1]) {
                        sell = prices[right];
                        right++;
                        continue;
                    } else {
                        right--;
                        sell = prices[right];
                        break;
                    }
                }

                profit += sell - pur;

                // log.debug("right: {}, sell: {}, profit: {}", right, sell, profit);

                if (right == LEN) {
                    break;
                } else {
                    left = right;
                }
            }
        }

        return profit;
    }

    /**
     * LC - One Way - Date Trading
     *
     * Runtime: 1 ms, faster than 97.55% of Java online submissions for Best Time to Buy and Sell Stock II.
     * Memory Usage: 44.8 MB, less than 18.35% of Java online submissions for Best Time to Buy and Sell Stock II.
     *
     * Time: O(N)
     * Space: O(1)
     */
    public int maxProfitLcOneWay(int[] prices) {
        int maxprofit = 0;

        for (int i = 0; i < prices.length - 1; i++) {
            if (prices[i + 1] > prices[i]) {
                maxprofit += prices[i + 1] - prices[i];
            }
        }

        return maxprofit;
    }

    /**
     * LC - Peak Valley Approach - Date Trading
     *
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Best Time to Buy and Sell Stock II.
     * Memory Usage: 44.3 MB, less than 44.93% of Java online submissions for Best Time to Buy and Sell Stock II.
     *
     * Time: O(N)
     * Space: O(1)
     */
    public int maxProfitLcPeakValley(int[] prices) {
        final int LEN = prices.length;
        int maxprofit = 0;
        int i = 0;
        int valley = 0;

        while (i < LEN - 1) {
            while (i < LEN - 1 && prices[i + 1] <= prices[i]) {
                i++;
            }
            valley = prices[i];
            while (i < LEN - 1 && prices[i + 1] >= prices[i]) {
                i++;
            }
            int peak = prices[i];
            maxprofit += peak - valley;
        }

        return maxprofit;
    }

}
