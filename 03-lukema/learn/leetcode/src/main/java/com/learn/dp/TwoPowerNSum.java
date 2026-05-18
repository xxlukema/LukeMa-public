package com.learn.dp;


import lombok.extern.log4j.Log4j2;


/**
 * sum of 2^n + 2^(n-1) + ... + 2^(1) + 2^(0)
 * 
 * DP will not help, becuase this is Time O(n) computation even for brute force.
 * There is no re-useable data to reduce computation.
 * 
 * Unlike Fabonacci numbers, there are re-useable data to accelerate computation Time Complexity from O(2 ^ n) to O(n).
 * This calculation can not.
 */
@Log4j2
public class TwoPowerNSum {

    public static void main(String[] args) {

        final int n = 10;

        TwoPowerNSum twoPowerNSum = new TwoPowerNSum();
        var ret = twoPowerNSum.twoToThePowerOfNSumDpTopDown(n);

        log.debug("2 ^ n: {}", () -> ret);

    }

    /**
     * Brute Force
     * 
     * Time: O(n)
     * Space: O(n)
     */
    public double twoToThePowerOfNSumBrute(int n) {

        if (n == 0) {
            return 1;
        }

        return n * n + twoToThePowerOfNSumBrute(n - 1);
    }

    /**
     * DP - Top-down
     * 
     * No help to speed
     * 
     * Time: O(n)
     * Space: O(n)
     */
    public double twoToThePowerOfNSumDpTopDown(int n) {

        final double[] memo = new double[n + 1];

        return twoToThePowerOfNSumDpTopDown(n, memo);
    }

    // DP - Top-down helper
    private double twoToThePowerOfNSumDpTopDown(int n, double[] memo) {

        if (memo[n] != 0) {
            return memo[n];
        }

        if (n == 0) {
            memo[0] = 1;
            return memo[0];
        }

        memo[n] = n * n + twoToThePowerOfNSumDpTopDown(n - 1, memo);

        return memo[n];
    }

    /**
     * DP - Bottom-up
     * 
     * Time: O(n)
     * Space: O(n)
     */
    public double twoToThePowerOfNSumDpBottomUp(int n) {

        final double[] dp = new double[n + 1];

        dp[0] = 1;

        for (int i = 1; i <= n; i++) {
            dp[i] = n * n + dp[n - 1];
        }

        return dp[n];
    }
}
