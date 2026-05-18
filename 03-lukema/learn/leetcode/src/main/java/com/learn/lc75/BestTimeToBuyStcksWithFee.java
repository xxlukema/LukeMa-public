package com.learn.lc75;


import org.junit.jupiter.api.Assertions;

import lombok.extern.log4j.Log4j2;


/**
 * LC - 714. Best Time to Buy and Sell Stock with Transaction Fee
 *
 * Medium
 *
 * You are given an array prices where prices[i] is the price of a given stock on the ith day, and an integer fee representing a transaction fee.

Find the maximum profit you can achieve. You may complete as many transactions as you like, but you need to pay the transaction fee for each transaction.

Note:

    You may not engage in multiple transactions simultaneously (i.e., you must sell the stock before you buy again).
    The transaction fee is only charged once for each stock purchase and sale.

Example 1:

Input: prices = [1,3,2,8,4,9], fee = 2
Output: 8
Explanation: The maximum profit can be achieved by:
- Buying at prices[0] = 1
- Selling at prices[3] = 8
- Buying at prices[4] = 4
- Selling at prices[5] = 9
The total profit is ((8 - 1) - 2) + ((9 - 4) - 2) = 8.

Example 2:

Input: prices = [1,3,7,5,10,3], fee = 3
Output: 6

Constraints:

    1 <= prices.length <= 5 * 10 ^ 4
    1 <= prices[i] < 5 * 10 ^ 4
    0 <= fee < 5 * 10 ^ 4
 */

@Log4j2
public class BestTimeToBuyStcksWithFee {

    public static void main(String[] args) {

        BestTimeToBuyStcksWithFee bestTimeToBuyStcksWithFee = new BestTimeToBuyStcksWithFee();

        int[] prices = { 1, 3, 2, 8, 4, 9 };
        int fee = 2;
        int expected = 8;

        var ret = bestTimeToBuyStcksWithFee.maxProfit(prices, fee);
        log.debug("Best Time to Buy and Sell Stock with Transaction Fee: {}", () -> ret);
        Assertions.assertEquals(expected, ret);
        log.debug("Best Time to Buy and Sell Stock with Transaction Fee: {} OK", () -> "maxProfit");

    }

    public int maxProfit(int[] prices, int fee) {

        final int LEN = prices.length;

        int[] hold = new int[LEN];
        int[] free = new int[LEN];

        hold[0] = -prices[0];
        free[0] = 0;

        for (int i = 1; i < LEN; i++) {
            hold[i] = Math.max(hold[i - 1], free[i - 1] - prices[i]);
            free[i] = Math.max(free[i - 1], hold[i - 1] + prices[i] - fee);
        }

        log.debug("hold: {}", hold);
        log.debug("free: {}", free);

        return free[LEN - 1];
    }
}
