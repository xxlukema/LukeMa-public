package com.learn.other;


import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.Assertions;

import lombok.extern.log4j.Log4j2;


/**
 * LC - 279 - Perfect Squares
 *
 * Medium
 *
 * Given an integer n, return the least number of perfect square numbers that sum to n.
 *
 * A perfect square is an integer that is the square of an integer; in other words, it is the product of some integer with itself. For example,
 * 1, 4, 9, and 16 are perfect squares while 3 and 11 are not.
 *
 * Example 1:
 * Input: n = 12
 * Output: 3
 * Explanation: 12 = 4 + 4 + 4.
 *
 * Example 2:
 * Input: n = 13
 * Output: 2
 * Explanation: 13 = 4 + 9.
 *
 * Constraints:
 *     1 <= n <= 104
 */
@Log4j2
public class PerfectSquares {

    public static void main(String[] args) {

        /**
         * Expected: 3
         */
        // final int n = 12;

        /**
         * Expected: 3
         */
        // final int n = 22;

        /**
         * Expected: 4
         */
        // final int n = 23;

        /**
         * Expected: 4
         * 43 = 25 + 9 + 9
         */
        final int n = 22;

        PerfectSquares perfectSquares = new PerfectSquares();

        var numSquaresYouTubeDpTopDown = perfectSquares.numSquaresYouTubeDpTopDown(n);
        log.debug("Perfect Squares: {}", () -> numSquaresYouTubeDpTopDown);
        log.debug("Perfect Squares {} OK", () -> "numSquaresYouTubeDpTopDown");

        var numSquaresYouTubeDpTopDownWithInputArrayOrderControl = perfectSquares.numSquaresYouTubeDpTopDownWithInputArrayOrderControl(n);
        Assertions.assertEquals(numSquaresYouTubeDpTopDown, numSquaresYouTubeDpTopDownWithInputArrayOrderControl);
        log.debug("Perfect Squares {} OK", () -> "numSquaresYouTubeDpTopDownWithInputArrayOrderControl");

        var numSquaresLcDpBottomUp = perfectSquares.numSquaresLcDpBottomUp(n);
        Assertions.assertEquals(numSquaresYouTubeDpTopDown, numSquaresLcDpBottomUp);
        log.debug("Perfect Squares {} OK", () -> "numSquaresLcDpBottomUp");

        var numSquaresLukeDpBottomUp = perfectSquares.numSquaresLukeDpBottomUp(n);
        Assertions.assertEquals(numSquaresYouTubeDpTopDown, numSquaresLukeDpBottomUp);
        log.debug("Perfect Squares {} OK", () -> "numSquaresLukeDpBottomUp");

    }

    /**
     * LC - DP - BottomUp
     *
     * Runtime: 47 ms Beats 69.22%
     * Memory: 43.2 MB Beats 55.51%
     *
     * Time: O(n) * O(sqrt)
     * Space: O(n)
     */
    public int numSquaresLcDpBottomUp(final int n) {

        AtomicInteger count = new AtomicInteger();

        final int[] dp = new int[n + 1];
        Arrays.fill(dp, Integer.MAX_VALUE / 2);

        /**
         * bottom case
         */
        dp[0] = 0;

        for (int cur = 1; cur <= n; cur++) {
            int sqrt = (int) Math.sqrt(cur);
            for (int root = sqrt, prod = 0; (prod = root * root) >= 1; root--) {
                dp[cur] = Math.min(dp[cur], 1 + dp[cur - prod]);

                count.incrementAndGet();
            }
        }

        log.debug("-------- numSquaresLcDpBottomUp --- count: {}", count.get());

        return dp[n];
    }

    /**
     * LC - DP - BottomUp
     *
     * Time: O(n) * O(sqrt)
     * Space: O(n)
     */
    public int numSquaresLukeDpBottomUp(final int n) {

        AtomicInteger count = new AtomicInteger();

        int dp[] = new int[n + 1];
        Arrays.fill(dp, Integer.MAX_VALUE / 2);
        /**
         * bottom case
         */
        dp[0] = 0;

        for (int cur = 1; cur <= n; ++cur) {
            for (int root = 1, prod = 0; (prod = root * root) <= cur; root++) {
                dp[cur] = Math.min(dp[cur], dp[cur - prod] + 1);

                count.incrementAndGet();
            }
        }

        log.debug("-------- numSquaresLukeDpBottomUp --- count: {}", count.get());

        return dp[n];
    }

    /**
     * YouTube - DP - TopDown
     *
     * https://www.youtube.com/watch?v=K715avFmZIk
     *
     * Runtime: 160 ms Beats 25.77%
     * Memory: 43.2 MB Beats 61.6%
     *
     * Time: O(n) = O(sqrt(n) iterations) * O(n recursion stack depth)
     * Space: O(n) for memo + O(n) due to recursion stack depth
     */
    public int numSquaresYouTubeDpTopDown(final int n) {
        AtomicInteger count = new AtomicInteger();

        final int[] memo = new int[n + 1];
        Arrays.fill(memo, -1);

        var min = numSquaresYouTubeDpTopDown(n, memo, count);

        // log.debug("------- memo: {}", memo);
        log.debug("--------- Without input array ordering ---- count: {}", count.get());

        return min;
    }

    int numSquaresYouTubeDpTopDown(final int n, final int[] memo, final AtomicInteger count) {

        count.incrementAndGet();

        if (n == 0) {
            return 0;
        } else if (n < 0) {
            /**
             * Trick: Use Integer.MAX_VALUE / 2 to prevent Integer add overflow
             */
            return Integer.MAX_VALUE / 2;
        }

        if (memo[n] != -1) {
            return memo[n];
        } else {
            /**
             * Trick: Use Integer.MAX_VALUE / 2 to prevent Integer add overflow
             */
            int min = Integer.MAX_VALUE / 2;
            /**
             * Time: O(sqrt(n) iterations) * O(n recursion stack depth)
             * Space: O(n recursion stack depth)
             */
            int root = (int) Math.sqrt(n);
            for (int i = root, prod = 0; (prod = i * i) >= 1; i--) {
                /**
                 * Time: O(n)
                 */
                int levels = 1 + numSquaresYouTubeDpTopDown(n - prod, memo, count);
                min = Math.min(min, levels);
            }

            // log.debug("======= memo: {}", memo);

            return memo[n] = min;
        }
    }

    /**
     * Luke - TopDown with input array iteration order control from left to right
     *
     * Runtime: 81 ms Beats 44.67%
     * Memory: 41.8 MB Beats 84.37%
     *
     * Time: O(n) = O(sqrt(n) iterations) * O(n recursion stack depth)
     * Space: O(n) for memo + O(n) due to recursion stack depth
     */
    public int numSquaresYouTubeDpTopDownWithInputArrayOrderControl(final int n) {
        AtomicInteger count = new AtomicInteger();

        int root = (int) Math.sqrt(n);
        final int[] squares = new int[root];
        for (int i = root; i >= 1; i--) {
            squares[root - i] = i * i;
        }

        log.debug("--- seqares: {}", squares);

        final int[] memo = new int[n + 1];
        Arrays.fill(memo, -1);

        var min = numSquaresYouTubeDpTopDownWithInputArrayOrderControl(n, squares, memo, 0, count);

        log.debug("--------- With input array ordering ------ count: {}", count.get());

        return min;
    }

    int numSquaresYouTubeDpTopDownWithInputArrayOrderControl(
            final int n,
            final int[] squares,
            final int[] memo,
            final int idx,
            final AtomicInteger count) {

        count.incrementAndGet();

        if (n == 0) {
            return 0;
        } else if (n < 0) {
            /**
             * Trick 2: Use Integer.MAX_VALUE / 2 to prevent Integer add overflow
             */
            return Integer.MAX_VALUE / 2;
        }

        if (memo[n] != -1) {
            return memo[n];
        } else {
            /**
             * Trick 2: Use Integer.MAX_VALUE / 2 to prevent Integer add overflow
             */
            int min = Integer.MAX_VALUE / 2;
            /**
             * Time: O(sqrt(n) iterations) * O(n recursion stack depth)
             * Space: O(n recursion stack depth)
             */
            for (int i = 0; i < squares.length; i++) {
                int rem = n - squares[i];
                if (rem < 0) {
                    continue;
                }

                /**
                 * Time: O(n)
                 */
                int levels = 1 + numSquaresYouTubeDpTopDownWithInputArrayOrderControl(rem, squares, memo, i, count);
                min = Math.min(min, levels);
            }

            // log.debug("======= memo: {}", memo);

            return memo[n] = min;
        }
    }

    /**
     * LC - Mathmatical
     *
     * Time: O(sqrt(n))
     * Space: O(1)
     */
    public int numSquaresMathmatical(int n) {
        // four-square and three-square theorems.
        while (n % 4 == 0) {
            n /= 4;
        }
        if (n % 8 == 7) {
            return 4;
        }

        if (this.isSquare(n)) {
            return 1;
        }
        // enumeration to check if the number can be decomposed into sum of two squares.
        for (int i = 1; i * i <= n; ++i) {
            if (this.isSquare(n - i * i)) {
                return 2;
            }
        }
        // bottom case of three-square theorem.
        return 3;
    }

    protected boolean isSquare(int n) {
        int sq = (int) Math.sqrt(n);
        return n == sq * sq;
    }
}
