package com.learn.other;


import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Assertions;

import lombok.extern.log4j.Log4j2;


/**
 * LC - 276 - Paint Fence
 *
 * Medium
 *
 * You are painting a fence of n posts with k different colors. You must paint the posts following these rules:
 *
 *     Every post must be painted exactly one color.
 *     There cannot be three or more consecutive posts with the same color.
 *
 * Given the two integers n and k, return the number of ways you can paint the fence.
 *
 * Example 1:
 * Input: n = 3, k = 2
 * Output: 6
 * Explanation: All the possibilities are shown.
 * Note that painting all the posts red or all the posts green is invalid because there cannot be three posts in a row with the same color.
 *
 * Example 2:
 * Input: n = 1, k = 1
 * Output: 1
 *
 * Example 3:
 * Input: n = 7, k = 2
 * Output: 42
 *
 * Constraints:
 *     1 <= n <= 50
 *     1 <= k <= 10 ^ 5
 *     The testcases are generated such that the answer is in the range [0, 2 ^ 31 - 1] for the given n and k.
 */
@Log4j2
public class PaintFence {

    public static void main(String[] args) {

        /**
         * Expected: 6
         */
        // final int n = 3, k = 2;

        /**
         * Expected: 42
         */
        // final int n = 7, k = 2;

        /**
         * Expected: 1,402,817,466
         */
        final int n = 43, k = 2;

        PaintFence paintFence = new PaintFence();

        var numWaysNoMemo = paintFence.numWaysNoMemo(n, k);
        log.debug("Paint Fence: {}", () -> numWaysNoMemo);
        log.debug("Paint Fence {} OK", () -> "numWaysNoMemo");

        var numWaysDpTopDownMemo = paintFence.numWaysDpTopDownMemo(n, k);
        Assertions.assertEquals(numWaysNoMemo, numWaysDpTopDownMemo);
        log.debug("Paint Fence {} OK", () -> "numWaysDpTopDownMemo");

        var numWaysDpTopDownMemoMap = paintFence.numWaysDpTopDownMemoMap(n, k);
        Assertions.assertEquals(numWaysNoMemo, numWaysDpTopDownMemoMap);
        log.debug("Paint Fence {} OK", () -> "numWaysDpTopDownMemoMap");

        var numWaysDpBottomUp = paintFence.numWaysDpBottomUp(n, k);
        Assertions.assertEquals(numWaysNoMemo, numWaysDpBottomUp);
        log.debug("Paint Fence {} OK", () -> "numWaysDpBottomUp");

        var numWaysDpBottomUpConstantSpace = paintFence.numWaysDpBottomUpConstantSpace(n, k);
        Assertions.assertEquals(numWaysNoMemo, numWaysDpBottomUpConstantSpace);
        log.debug("Paint Fence {} OK", () -> "numWaysDpBottomUpConstantSpace");

    }

    /**
     * LC - Logics: f(i) = (k - 1) * f(Use different color with previous post) + f(Use same color as on previous post)
     *
     * Indicators of dynamic programming:
     *
     * 1. For the "number of ways" to do something
     * 2. We need to make decisions that may depend on previously made decisions
     *
     * Three components to solve dynamic programing:
     *
     * 1. Establishing a base case
     * 2. A function or array that represents the answer to the problem for a given state
     * 3. A way to transition between states, such as `totalWays(3)` and `totalWays(4)`. This is called a **recurrence relation** and the hardest part of the solution
     *
     * Finding The Recurrence Relation:
     *
     * We know the values for totalWays(1) and totalWays(2), now we need a formula for totalWays(i), where 3 <= i <= n. Let's think about
     * how many ways there are to paint the ithi^{th}ith post. We have two options:
     *
     *     Use a different color than the previous post. If we use a different color, then there are k - 1 colors for us to use. This means
     *     there are (k - 1) * totalWays(i - 1) ways to paint the (i)th post a different color than the (i−1)th post.
     *
     *     Use the same color as the previous post. There is only one color for us to use, so there are 1 * totalWays(i - 1) ways to paint the
     *     (i)th post the same color as the (i−1)th post. However, we have the added restriction of not being allowed to paint three posts in
     *     a row the same color. Therefore, we can paint the (i)th post the same color as the (i−1)th post only if the (i−1)th(i - 1) post is
     *     a different color than the (i−2)th post.
     *
     *     So, how many ways are there to paint the (i−1)th post a different color than the (i−2)th post? Well, as stated in the first option,
     *     there are (k - 1) * totalWays(i - 1) ways to paint the (i)th post a different color than the (i−1)th post, so that means there are
     *     1 * (k - 1) * totalWays(i - 2) ways to paint the (i−1)th post a different color than the (i−2)th post.
     *
     *     Adding these two scenarios together gives totalWays(i) = (k - 1) * totalWays(i - 1) + (k - 1) * totalWays(i - 2), which can be simplified to:
     *
     *     totalWays(i) = (k - 1) * (totalWays(i - 1) + totalWays(i - 2))
     *
     *
     * Runtime: 2,038 ms Beats 16.43%
     * Memory: 39.2 MB Beats 82.96%
     *
     * Time: O(n)
     * Space: O(n)
     */
    public int numWaysNoMemo(int n, int k) {
        if (n == 0) {
            return 0;
        } else if (n == 1) {
            return k;
        } else if (n == 2) {
            return k * k;
        }

        return (k - 1) * (numWaysNoMemo(n - 1, k) + numWaysNoMemo(n - 2, k));
    }

    /**
     * LC - DP - TopDown
     *
     * Runtime 0 ms Beats 100%
     * Memory 38.8 MB Beats 98.58%
     *
     * Time: O(n)
     * Space: O(n)
     */
    public int numWaysDpTopDownMemo(int n, int k) {
        if (n == 0) {
            return 0;
        }

        final int[] memo = new int[n + 1];

        return numWaysDpTopDownMemo(n, k, memo);
    }

    int numWaysDpTopDownMemo(final int n, final int k, final int[] memo) {

        if (memo[n] != 0) {
            return memo[n];
        }

        int value = 0;
        if (n == 1) {
            value = k;
        } else if (n == 2) {
            value = k * k;
        } else {
            value = (k - 1) * (numWaysDpTopDownMemo(n - 1, k, memo) + numWaysDpTopDownMemo(n - 2, k, memo));
        }

        return memo[n] = value;
    }

    /**
     * LC - DP - TopDown
     *
     * For this approach, we are using a hash map as our data structure to memoize function calls. We could also use an array since the calls to
     * totalWays are very well defined (between 1 and n). However, a hash map is used for most top-down dynamic programming solutions, as there
     * will often be multiple function arguments, the arguments might not be integers, or a variety of other reasons that require a hash map
     * instead of an array. Although using an array is slightly more efficient, using a hash map here is a good practice that can be applied to
     * other problems.
     *
     * Runtime 1 ms Beats 16.43%
     * Memory 40.8 MB Beats 55.38%
     *
     * Time: O(n)
     * Space: O(n)
     */
    public int numWaysDpTopDownMemoMap(int n, int k) {
        if (n == 0) {
            return 0;
        }

        final Map<Integer, Integer> memo = new HashMap<>();

        return numWaysDpTopDownMemoMap(n, k, memo);
    }

    int numWaysDpTopDownMemoMap(final int n, final int k, final Map<Integer, Integer> memo) {

        if (memo.get(n) != null) {
            return memo.get(n);
        }

        int value = 0;
        if (n == 1) {
            value = k;
        } else if (n == 2) {
            value = k * k;
        } else {
            value = (k - 1) * (numWaysDpTopDownMemoMap(n - 1, k, memo) + numWaysDpTopDownMemoMap(n - 2, k, memo));
        }

        memo.put(n, value);

        return value;
    }

    /**
     * LC - DP - BottomUp
     *
     * Runtime 0 ms Beats 100%
     * Memory 41 MB Beats 40.57%
     *
     * Time: O(n)
     * Space: O(n)
     */
    public int numWaysDpBottomUp(int n, int k) {
        if (n == 0) {
            return 0;
        }

        final int[] dp = new int[n];
        dp[0] = k;

        if (n == 1) {
            return k;
        }

        dp[1] = k * k;

        for (int i = 2; i < n; i++) {
            dp[i] = (k - 1) * (dp[i - 1] + dp[i - 2]);
        }

        return dp[n - 1];
    }

    /**
     * LC - DP - BottomUp - Constant Space
     *
     * Runtime 0 ms Beats 100%
     * Memory 40.8 MB Beats 55.38%
     *
     * Time: O(n)
     * Space: O(n)
     */
    public int numWaysDpBottomUpConstantSpace(int n, int k) {
        if (n == 0) {
            return 0;
        }

        if (n == 1) {
            return k;
        }

        int lastLast = k;
        int last = k * k;

        for (int i = 2; i < n; i++) {
            int cur = (k - 1) * (last + lastLast);
            lastLast = last;
            last = cur;
        }

        return last;
    }
}
