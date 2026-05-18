package com.learn.dp;


import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;

import lombok.extern.log4j.Log4j2;


/**
 * LC - 188 - Best Time To Buy Sell Stock IV
 *
 * Hard
 *
 * You are given an integer array prices where prices[i] is the price of a given stock on the ith day, and an integer k.
 *
 * Find the maximum profit you can achieve. You may complete "at most k transactions".
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
 *     0 <= k <= 100
 *     0 <= prices.length <= 1000
 *     0 <= prices[i] <= 1000
 */
@Log4j2
public class BestTimeToBuyAndSellStockIV {

    public static void main(String[] args) {

        final int k = 2;
        final int[] prices = { 3, 2, 6, 5, 0, 3 };

        BestTimeToBuyAndSellStockIV bestTimeToBuyAndSellStockIV = new BestTimeToBuyAndSellStockIV();

        var maxProfitLcDp = bestTimeToBuyAndSellStockIV.maxProfitLcDp(k, prices);
        log.debug("Best time to buy and sell stock IV: {}", () -> maxProfitLcDp);
        log.debug("Best time to buy and sell stock IV {} OK", () -> "maxProfitLcDp");

        var maxProfitLcMerge = bestTimeToBuyAndSellStockIV.maxProfitLcMerge(k, prices);
        Assertions.assertEquals(maxProfitLcDp, maxProfitLcMerge);
        log.debug("Best time to buy and sell stock IV {} OK", () -> "maxProfitLcMerge");

    }

    /**
     * LC - DP
     *
     * Time: O(N * k) if 2k < N, O(N) if 2k > n
     * Space: O(N * k)
     */
    public int maxProfitLcDp(int k, final int[] prices) {
        final int N = prices.length;

        // solve special cases
        if (N <= 0 || k <= 0) {
            return 0;
        }

        if (2 * k > N) {
            int res = 0;
            for (int i = 1; i < N; i++) {
                res += Math.max(0, prices[i] - prices[i - 1]);
            }
            return res;
        }

        // dp[i][used_k][ishold] = balance
        // ishold: 0 nothold, 1 hold
        final int[][][] dp = new int[N][k + 1][2];

        // initialize the array with -inf
        // we use -1e9 here to prevent overflow
        for (int i = 0; i < N; i++) {
            for (int j = 0; j <= k; j++) {
                dp[i][j][0] = -1000000000;
                dp[i][j][1] = -1000000000;
            }
        }

        // set starting value
        dp[0][0][0] = 0;
        dp[0][1][1] = -prices[0];

        // fill the array
        for (int row = 1; row < N; row++) {
            for (int col = 0; col <= k; col++) {
                // transition equation
                dp[row][col][0] = Math.max(dp[row - 1][col][0], dp[row - 1][col][1] + prices[row]);
                // you can't hold stock without any transaction
                if (col > 0) {
                    dp[row][col][1] = Math.max(dp[row - 1][col][1], dp[row - 1][col - 1][0] - prices[row]);
                }
            }
        }

        int res = 0;
        for (int j = 0; j <= k; j++) {
            res = Math.max(res, dp[N - 1][j][0]);
        }

        return res;
    }

    /**
     * LC - Merge
     *
     * Time: O(N * (N - k) if 2k < N, O(N) if 2k > n
     * Space: O(N)
     */
    public int maxProfitLcMerge(int k, int[] prices) {
        int n = prices.length;

        // solve special cases
        if (n <= 0 || k <= 0) {
            return 0;
        }

        // find all consecutively increasing subsequence
        ArrayList<int[]> transactions = new ArrayList<>();
        int start = 0;
        int end = 0;
        for (int i = 1; i < n; i++) {
            if (prices[i] >= prices[i - 1]) {
                end = i;
            } else {
                if (end > start) {
                    int[] t = { start, end };
                    transactions.add(t);
                }
                start = i;
            }
        }
        if (end > start) {
            int[] t = { start, end };
            transactions.add(t);
        }

        while (transactions.size() > k) {
            // check delete loss
            int delete_index = 0;
            int min_delete_loss = Integer.MAX_VALUE;
            for (int i = 0; i < transactions.size(); i++) {
                int[] t = transactions.get(i);
                int profit_loss = prices[t[1]] - prices[t[0]];
                if (profit_loss < min_delete_loss) {
                    min_delete_loss = profit_loss;
                    delete_index = i;
                }
            }

            // check merge loss
            int merge_index = 0;
            int min_merge_loss = Integer.MAX_VALUE;
            for (int i = 1; i < transactions.size(); i++) {
                int[] t1 = transactions.get(i - 1);
                int[] t2 = transactions.get(i);
                int profit_loss = prices[t1[1]] - prices[t2[0]];
                if (profit_loss < min_merge_loss) {
                    min_merge_loss = profit_loss;
                    merge_index = i;
                }
            }

            // delete or merge
            if (min_delete_loss <= min_merge_loss) {
                transactions.remove(delete_index);
            } else {
                int[] t1 = transactions.get(merge_index - 1);
                int[] t2 = transactions.get(merge_index);
                t1[1] = t2[1];
                transactions.remove(merge_index);
            }

        }

        int res = 0;
        for (int[] t : transactions) {
            res += prices[t[1]] - prices[t[0]];
        }

        return res;
    }

    /**
     * Time: O(n ^ 2)
     * Space: O(1)
     */
    int maxProfit(int left, int right, final int[] prices) {

        while (right - 1 > left && prices[right - 1] > prices[right]) {
            right--;
        }

        int profit = 0;

        while (left < right) {
            while (left + 1 < right && prices[left + 1] < prices[left]) {
                left++;
            }

            for (int k = right; k > left; k--) {
                profit = Math.max(profit, prices[k] - prices[left]);
            }

            left++;
        }

        return profit;
    }
}
