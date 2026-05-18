package com.learn.dp;


import java.math.BigDecimal;
import java.text.DecimalFormat;

import lombok.extern.log4j.Log4j2;


@Log4j2
public class FabonacciNumbers {

    public static void main(String[] args) {

        // final int n = 45;
        final int n = 70;

        FabonacciNumbers fabonacciNumbers = new FabonacciNumbers();

        var ret = fabonacciNumbers.fabDpBottomUp(n);
        // var ret = fabonacciNumbers.fabDpTopDown(n);
        // var ret = fabonacciNumbers.fabBrute(n);

        log.debug("Fabonacci sum: {}", () -> ret);
        log.debug("Fabonacci sum: {}", () -> String.format("%.0f", ret));
        log.debug("Fabonacci sum: {}", () -> BigDecimal.valueOf(ret).toPlainString());

        // DecimalFormat df = new DecimalFormat("#,###");
        log.debug("Fabonacci sum: {}", () -> new DecimalFormat("#,###").format(ret));

    }

    /**
     * 2025-11-25
     * 
     * 1. BFS
     * 2. Map of words with all their neighbors (Map<String, Set<String>>)
     * 3. List of words already visited
     * 
     */

    /**
     * DP - Bottom-up
     * 
     * No recursion
     * 
     * Time: O(n)
     * Space: O(n)
     */
    public double fabDpBottomUp(int n) {

        final double[] dp = new double[n + 1];

        if (n <= 0) {
            return 0;
        }

        if (n == 1) {
            return 1;
        }

        dp[0] = 0;
        dp[1] = 1;

        for (int i = 2; i <= n; i++) {
            dp[i] = dp[i - 2] + dp[i - 1];
        }

        return dp[n];
    }

    /**
     * DP - Top-down
     * 
     * With recursion
     * 
     * Time: O(n)
     * Space: O(n)
     */
    public double fabDpTopDown(int n) {

        final double[] memo = new double[n + 1];

        return fabDpTopDown(n, memo);
    }

    /**
     * Helper for DP - Top-down
     */
    private double fabDpTopDown(int n, double[] memo) {

        if (memo[n] != 0) {
            // log.debug("Found {}", () -> n);

            return memo[n];
        }

        if (n <= 0) {
            memo[0] = 0;
            return 0;
        }

        if (n == 1) {
            memo[1] = 1;
            return 1;
        }

        /**
         * This is slow (Non-DP). Every call to "fabDpTopDown(n - 1)" creates a new memo.
         * And there is no previous calculated memo re-use.
         */
        // double fab = fabDpTopDown(n - 2) + fabDpTopDown(n - 1);

        /**
         * This is fast (DP): Re-use of previous calculated memo.
         */
        double fab = fabDpTopDown(n - 2, memo) + fabDpTopDown(n - 1, memo);

        memo[n] = fab;

        return fab;
    }

    /**
     * Brute Force
     * 
     * Time: O(2 ^ n)
     * Space: O(2 * n) = O(n)
     */
    public double fabBrute(int n) {

        if (n <= 0) {
            return 0;
        }

        if (n == 1) {
            return 1;
        }

        return fabBrute(n - 2) + fabBrute(n - 1);
    }
}
