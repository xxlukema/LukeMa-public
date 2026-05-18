package com.learn.other;


import org.junit.jupiter.api.Assertions;

import lombok.extern.log4j.Log4j2;


@Log4j2
public class ClimbingStairs {

    public static void main(String[] args) {

        final int n = 3;

        ClimbingStairs climbingStairs = new ClimbingStairs();
        var ret = climbingStairs.climbStairs(n);
        log.debug("Climbing Stairs Luke: {}", () -> ret);

        var retLcDp = climbingStairs.climbStairsLcDp(n);
        log.debug("Climbing Stairs LC DP: {}", () -> retLcDp);

        Assertions.assertEquals(retLcDp, ret);

        var retLcFab = climbingStairs.climbStairsLcFabonacci(n);
        log.debug("Climbing Stairs LC DP: {}", () -> retLcFab);

        Assertions.assertEquals(retLcDp, retLcFab);
    }

    public int climbStairs(int n) {

        if (n == 1) {
            return 1;
        }

        if (n == 2) {
            return 2;
        }

        int[] stairs = new int[n + 1];

        stairs[1] = 1;
        stairs[2] = 2;

        for (int i = 3; i <= n; i++) {
            stairs[i] = stairs[i - 1] + stairs[i - 2];
        }

        return stairs[n];
    }

    /**
     * LC DP
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Climbing Stairs.
     * Memory Usage: 41.2 MB, less than 21.25% of Java online submissions for Climbing Stairs.
     * 
     * Time: O(n)
     * Space: O(n)
     */
    public int climbStairsLcDp(int n) {
        if (n == 1) {
            return 1;
        }

        int[] dp = new int[n + 1];

        dp[1] = 1;
        dp[2] = 2;

        for (int i = 3; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }

        return dp[n];
    }

    /**
     * LC Fabonacci
     * 
     * Time: O(n)
     * Space: O(1)
     */
    public int climbStairsLcFabonacci(int n) {
        if (n == 1) {
            return 1;
        }

        int first = 1;
        int second = 2;

        for (int i = 3; i <= n; i++) {
            int third = first + second;
            first = second;
            second = third;
        }

        return second;
    }

}
