package com.learn.other;


import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;

import lombok.extern.log4j.Log4j2;


/**
 * LC - 123 - Best Time To Buy And Sell Stock
 *
 * Hard
 *
 * You are given an array prices where prices[i] is the price of a given stock on the ith day.
 * Find the maximum profit you can achieve. You may complete at most two transactions.
 * Note: You may not engage in multiple transactions simultaneously (i.e., you must sell the stock before you buy again).
 *
 * Example 1:
 * Input: prices = [3,3,5,0,0,3,1,4]
 * Output: 6
 * Explanation: Buy on day 4 (price = 0) and sell on day 6 (price = 3), profit = 3-0 = 3.
 * Then buy on day 7 (price = 1) and sell on day 8 (price = 4), profit = 4-1 = 3.
 *
 * Example 2:
 * Input: prices = [1,2,3,4,5]
 * Output: 4
 * Explanation: Buy on day 1 (price = 1) and sell on day 5 (price = 5), profit = 5-1 = 4.
 * Note that you cannot buy on day 1, buy on day 2 and sell them later, as you are engaging multiple transactions at the same time. You must sell before buying again.
 *
 * Example 3:
 * Input: prices = [7,6,4,3,1]
 * Output: 0
 * Explanation: In this case, no transaction is done, i.e. max profit = 0.
 *
 */
@Log4j2
public class BestTimeToBuyAndSellStockIII {

    public static void main(String[] args) {

        // int[] prices = { 7, 1, 5, 3, 6, 4 };
        int[] prices = { 3, 3, 5, 0, 0, 3, 1, 4 };
        // int[] prices = { 1, 2, 3, 4, 5 };
        // int[] prices = { 7, 6, 4, 3, 1 };
        // int[] prices = { 2, 4, 1 };
        // int[] prices = { 1, 2 };

        BestTimeToBuyAndSellStockIII bestTimeToBuyAndSellStockIII = new BestTimeToBuyAndSellStockIII();

        log.debug(() -> "Start Test...");

        var retLukeDayTrade = bestTimeToBuyAndSellStockIII.maxProfitLukeIterativeDayTrading(prices);
        log.debug("Max day trade profit O(N ^ 2): {}", () -> retLukeDayTrade);
        log.debug(() -> "Luke Day Trade OK");

        var retLukeBuyAndHoldDayTrade = bestTimeToBuyAndSellStockIII.maxProfitLukeIterativeBuyAndHold(prices);
        log.debug("Max buy and hold profit O(N ^ 2): {}", () -> retLukeBuyAndHoldDayTrade);
        Assertions.assertTrue(retLukeDayTrade >= retLukeBuyAndHoldDayTrade);
        log.debug(() -> "Luke Buy and Hold OK");

        var retLukeBuyAndHoldWithListOfPeaks = bestTimeToBuyAndSellStockIII.maxProfitLukeIterativeBuyAndHoldWithListOfPeaks(prices);
        log.debug("Max buy and hold profit with list of peaks O(N ^ 2): {}", () -> retLukeBuyAndHoldWithListOfPeaks);
        Assertions.assertTrue(retLukeDayTrade >= retLukeBuyAndHoldWithListOfPeaks);
        log.debug(() -> "Luke Buy and Hold With List of Peaks OK");

        // log.debug("Luke memo OK ------: {}", bestTimeToBuyAndSellStockIII.maxProfixLcOneWay(prices, 3, 4));

        var retLcTwoWay = bestTimeToBuyAndSellStockIII.maxProfitLcTwoWay(prices);
        log.debug("LC One Way: {}", () -> retLcTwoWay);
        Assertions.assertEquals(retLukeDayTrade, retLcTwoWay);

        log.debug(() -> "LC Two Way OK");

        var retLukeTwoWay = bestTimeToBuyAndSellStockIII.maxProfitLukeTwoWay(prices);
        log.debug("Luke Two Way: {}", () -> retLukeTwoWay);
        Assertions.assertEquals(retLcTwoWay, retLukeTwoWay);

        log.debug(() -> "Luke Two Way OK");

        var retLcOneWay = bestTimeToBuyAndSellStockIII.maxProfitLcOneWay(prices);
        log.debug("LC One Way: {}", () -> retLcOneWay);
        Assertions.assertEquals(retLukeDayTrade, retLcOneWay);

        log.debug(() -> "LC One Way OK");

        var retLukeOneWay = bestTimeToBuyAndSellStockIII.maxProfitLukeOneWay(prices);
        log.debug("Luke One Way: {}", () -> retLukeOneWay);
        Assertions.assertEquals(retLcOneWay, retLukeOneWay);

        log.debug(() -> "Luke One Way OK");

        /*
        var retLcOneWay = bestTimeToBuyAndSellStockIII.maxProfitLcOneWay(prices);
        Assertions.assertEquals(retLuke, retLcOneWay);
        
        log.debug(() -> "LC One Way OK");
        
        var retLcPeakValley = bestTimeToBuyAndSellStockIII.maxProfitLcPeakValley(prices);
        Assertions.assertEquals(retLuke, retLcPeakValley);
        
        log.debug(() -> "LC Peak Valley OK");
        */

        log.debug(() -> "Test Complete");
    }

    /**
     * Luke - Iteratively call helper - Day Trade
     *
     * Time Limit Exceeded
     *
     * Time: O(N ^ 2)
     * Space: O(1)
     */
    public int maxProfitLukeIterativeDayTrading(int[] prices) {
        final int LEN = prices.length;

        int profit = 0;

        for (int i = 0; i < LEN; i++) {
            int earn1 = maxProfixLcOneWayDayTrading(prices, 0, i - 1);
            int earn2 = maxProfixLcOneWayDayTrading(prices, i, LEN - 1);
            profit = Math.max(profit, earn1 + earn2);

            // log.debug("i: {}, profit: {}, earn1: {}, earn2: {}", i, profit, earn1, earn2);
        }

        return profit;
    }

    /**
     * Incorrect. This is Date Trading. It should be "Buy at lowest and hold until highest".
     *
     * Time: O(N)
     * Space: O(1)
     */
    private int maxProfixLcOneWayDayTrading(int[] prices, int start, int end) {
        if (start >= end || prices == null || prices.length < 2) {
            return 0;
        }

        int minPrice = prices[start];
        int profit = 0;
        for (int i = start; i <= end; i++) {
            if (prices[i] < minPrice) {
                minPrice = prices[i];
            } else {
                int earn = prices[i] - minPrice;
                profit = Math.max(profit, earn);
            }
        }

        // log.debug("start: {}, end: {}, profit: {}", start, end, profit);

        return profit;
    }

    /**
     * Luke - Iteratively call helper - Buy and Hold
     *
     * Time Limit Exceeded
     *
     * Time: O(N ^ 2)
     * Space: O(1)
     */
    public int maxProfitLukeIterativeBuyAndHold(int[] prices) {
        final int LEN = prices.length;

        int profit = 0;

        for (int i = 0; i < LEN; i++) {
            int earn1 = maxProfixLukeOneWayBuyAndHold(prices, 0, i - 1);
            int earn2 = maxProfixLukeOneWayBuyAndHold(prices, i, LEN - 1);
            profit = Math.max(profit, earn1 + earn2);

            // log.debug("i: {}, profit: {}, earn1: {}, earn2: {}", i, profit, earn1, earn2);
        }

        return profit;
    }

    /**
     * Luke - Buy and Hold Helper
     *
     * Time: O(N)
     * Space: O(N)
     */
    private int maxProfixLukeOneWayBuyAndHold(int[] prices, int start, int end) {
        final int[] highesFromNowToEnd = new int[end + 1];

        int high = 0;
        for (int i = end; i >= start; i--) {
            if (prices[i] > high) {
                high = prices[i];
            }
            highesFromNowToEnd[i] = high;
        }

        int profit = 0;

        for (int i = start; i <= end; i++) {
            int earn = highesFromNowToEnd[i] - prices[i];
            profit = Math.max(profit, earn);
        }

        return profit;
    }

    /**
     * Luke - Buy and Hold
     *
     * Time Limit Exceeded
     *
     * Time: O(N ^ 2)
     * Space: O(N)
     */
    public int maxProfitLukeIterativeBuyAndHoldWithListOfPeaks(int[] prices) {
        final int LEN = prices.length;

        List<Integer> peaks = new ArrayList<>();
        boolean isSliding = true;
        for (int i = 0; i < LEN - 1; i++) {
            if (prices[i] > prices[i + 1]) {
                if (!isSliding) {
                    peaks.add(i);
                }
                isSliding = true;
            } else {
                isSliding = false;
            }
        }

        int profit = 0;

        // log.debug("Peaks: {}", peaks);

        /**
         * Optimization: Use the peak that has biggest drops following it. And after that drop, there is another good relay.
         */

        if (peaks.size() == 0) {
            profit = maxProfixLukeOneWayBuyAndHold(prices, 0, LEN - 1);
        } else {
            for (Integer day : peaks) {
                int earn1 = maxProfixLukeOneWayBuyAndHold(prices, 0, day);
                int earn2 = maxProfixLukeOneWayBuyAndHold(prices, day + 1, LEN - 1);
                profit = Math.max(profit, earn1 + earn2);

                // log.debug("day: {}, profit: {}, earn1: {}, earn2: {}", day, profit, earn1, earn2);
            }
        }

        return profit;
    }

    /**
     * LC - One Way
     *
     *
     * Time: O(N)
     * Space: O(1)
     */
    public int maxProfitLcOneWay(int[] prices) {
        int firstCost = Integer.MAX_VALUE;
        int firstProfit = 0;
        int secondCost = Integer.MAX_VALUE;
        int secondProfit = 0;

        for (int price : prices) {
            // the maximum profit if first transaction
            firstCost = Math.min(firstCost, price);
            firstProfit = Math.max(firstProfit, price - firstCost);

            // reinvest the gained profit in the second transaction
            secondCost = Math.min(secondCost, price - firstProfit);
            secondProfit = Math.max(secondProfit, price - secondCost);

            log.debug("Lc - firstCost: {}, firstProfit: {}, secondCost: {}, secondProfit: {}", firstCost, firstProfit, secondCost, secondProfit);
        }

        return secondProfit;
    }

    /**
     * Luke One Way - Error
     */
    public int maxProfitLukeOneWay(int[] prices) {
        int firstCost = Integer.MAX_VALUE;
        int firstProfit = 0;

        int secondCost = Integer.MAX_VALUE;
        int secondProfit = 0;
        int lcSecondCost = Integer.MAX_VALUE;
        int lcSecondProfit = 0;

        for (int i = 0; i < prices.length; i++) {
            int price = prices[i];

            /**
             * First investment
             */
            firstCost = Math.min(firstCost, price);
            if (price - firstCost > firstProfit) {
                firstProfit = price - firstCost;
            }

            /**
             * Second investment
             */
            secondCost = Math.min(secondCost, price);
            secondProfit = Math.max(secondProfit, price - secondCost);

            /**
             * LC - Second profit
             */
            lcSecondCost = Math.min(lcSecondCost, price - firstProfit);
            lcSecondProfit = Math.max(lcSecondProfit, price - lcSecondCost);

            log.debug("Luke - firstCost: {}, firstProfit: {}, secondCost: {}, secondProfit: {}, lcSecondCost: {}, lcSecondProfit: {}",
                    firstCost, firstProfit, secondCost, secondProfit, lcSecondCost, lcSecondProfit);
        }

        return firstProfit + secondProfit;
    }

    /**
     * Luke - Two Way
     *
     * Runtime: 7 ms, faster than 64.80% of Java online submissions for Best Time to Buy and Sell Stock III.
     * Memory Usage: 81 MB, less than 47.16% of Java online submissions for Best Time to Buy and Sell Stock III.
     *
     * Time: O(N)
     * Space: O(N)
     */
    public int maxProfitLukeTwoWay(int[] prices) {
        if (prices == null) {
            return 0;
        }

        final int LEN = prices.length;
        if (LEN <= 1) {
            return 0;
        }

        final int[] maxProfitsLeftToRight = new int[LEN];

        int minPrice = prices[0];
        int profitLeftToRight = 0;

        for (int i = 1; i < LEN; i++) {
            profitLeftToRight = Math.max(profitLeftToRight, prices[i] - minPrice);
            maxProfitsLeftToRight[i] = profitLeftToRight;
            minPrice = Math.min(minPrice, prices[i]);
        }

        final int[] maxProfitsRightToLeft = new int[LEN];

        int maxPrice = prices[LEN - 1];
        int profitRightToLeft = 0;

        for (int i = LEN - 2; i >= 0; i--) {
            profitRightToLeft = Math.max(profitRightToLeft, maxPrice - prices[i]);
            maxProfitsRightToLeft[i] = profitRightToLeft;
            maxPrice = Math.max(maxPrice, prices[i]);
        }

        int maxProfit = 0;

        for (int i = 0; i < LEN; i++) {
            maxProfit = Math.max(maxProfit, maxProfitsLeftToRight[i] + maxProfitsRightToLeft[i]);
        }

        return maxProfit;
    }

    /**
     * LC - Two Way
     *
     * Runtime: 4 ms, faster than 90.11% of Java online submissions for Best Time to Buy and Sell Stock III.
     * Memory Usage: 51.3 MB, less than 98.28% of Java online submissions for Best Time to Buy and Sell Stock III.
     *
     * Time: O(N)
     * Space: O(N)
     */
    public int maxProfitLcTwoWay(int[] prices) {
        final int LEN = prices.length;
        if (LEN <= 1) {
            return 0;
        }

        int leftMin = prices[0];
        int rightMax = prices[LEN - 1];

        int[] leftProfits = new int[LEN];
        // pad the right DP array with an additional zero for convenience.
        int[] rightProfits = new int[LEN + 1];

        // construct the bidirectional DP array
        for (int left = 1; left < LEN; ++left) {
            leftProfits[left] = Math.max(leftProfits[left - 1], prices[left] - leftMin);
            leftMin = Math.min(leftMin, prices[left]);

            int right = LEN - 1 - left;
            rightProfits[right] = Math.max(rightProfits[right + 1], rightMax - prices[right]);
            rightMax = Math.max(rightMax, prices[right]);
        }

        int maxProfit = 0;
        for (int i = 0; i < LEN; ++i) {
            maxProfit = Math.max(maxProfit, leftProfits[i] + rightProfits[i + 1]);
        }
        return maxProfit;
    }
}
