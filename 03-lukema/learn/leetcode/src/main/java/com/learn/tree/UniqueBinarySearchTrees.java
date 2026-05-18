package com.learn.tree;


import org.junit.jupiter.api.Assertions;

import lombok.extern.log4j.Log4j2;


/**
 * LC 96
 */
@Log4j2
public class UniqueBinarySearchTrees {

    public static void main(String[] args) {

        final int N = 5;

        UniqueBinarySearchTrees uniqueBinarySearchTrees = new UniqueBinarySearchTrees();
        int ret = uniqueBinarySearchTrees.numTreesDp(N);
        log.debug("Unique binary search trees: {}", () -> ret);

        int retMath = uniqueBinarySearchTrees.numTreesMath(N);
        Assertions.assertEquals(ret, retMath);
    }

    /**
     * LC DP
     *
     * Catalan number: C(n) = sum(0 to n - 1) of (C(i) * C(n - i - 1))  --- C4 = C0 * C3 + C1 * C2 + C2 * C1 + C3 * C0
     *
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Unique Binary Search Trees.
     * Memory Usage: 40.6 MB, less than 64.00% of Java online submissions for Unique Binary Search Trees.
     *
     * Time: O(N ^ 2)
     * Space: O(N)
     */
    public int numTreesDp(final int N) {
        int[] memo = new int[N + 1];
        memo[0] = 1;
        memo[1] = 1;

        for (int i = 2; i <= N; i++) {
            for (int k = 0; k < i; k++) {
                memo[i] += memo[k] * memo[i - k - 1];
            }
        }

        return memo[N];
    }

    /**
     * LC Math
     *
     * Catalan number: C(0) = 1, C(n + 1)= C(n) * 2 * (2 * n + 1) / (n + 2)
     *
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Unique Binary Search Trees.
     * Memory Usage: 41.3 MB, less than 15.73% of Java online submissions for Unique Binary Search Trees.
     *
     * Time: O(N)
     * Space: O(1)
     */
    public int numTreesMath(final int N) {
        // Note: we should use long here instead of int, otherwise overflow
        long C = 1;
        for (int i = 0; i < N; ++i) {
            C = C * 2 * (2 * i + 1) / (i + 2);

            /**
             * !Important: This will not work
             */
            // C = 2 * (2 * i + 1) / (i + 2) * C;
        }
        return (int) C;
    }

}
