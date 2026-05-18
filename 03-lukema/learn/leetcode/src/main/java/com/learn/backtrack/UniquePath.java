package com.learn.backtrack;


import java.util.LinkedList;

import org.junit.jupiter.api.Assertions;

import lombok.extern.log4j.Log4j2;


@Log4j2
public class UniquePath {

    public static void main(String[] args) {

        final int m = 4;
        final int n = 3;

        UniquePath uniquePath = new UniquePath();

        var ret = uniquePath.uniquePathsLukeBacktrack(m, n);
        log.debug("Number of unique path luke: {}", () -> ret);

        var retDp = uniquePath.uniquePathsLcDp(m, n);
        log.debug("Number of unique path LC DP: {}", () -> retDp);

        Assertions.assertEquals(retDp, ret);

    }

    /**
     * LC: Math
     *
     * (m + n - 2)! / ((m - 1)!(n - 1)!)
     *
     * Time complexity: O((M+N)(log⁡(M+N)log⁡log⁡(M+N))2)\mathcal{O}((M + N) (\log (M + N) \log \log (M + N))^2)O((M+N)(log(M+N)loglog(M+N))2).
     * Space complexity: O(1)\mathcal{O}(1)O(1).
     *
     */
    public int uniquePathsLcMath(int m, int n) {
        return 0;
    }

    /**
     * LC DP
     *
     * Runtime: 1 ms, faster than 49.78% of Java online submissions for Unique Paths.
     * Memory Usage: 41.2 MB, less than 33.71% of Java online submissions for Unique Paths.
     *
     * Time: O(m * n)
     * Space: O(m * n)
     */
    public int uniquePathsLcDp(int m, int n) {

        final int[][] memo = new int[m][n];

        for (int row = 0; row < m; row++) {
            memo[row][0] = 1;
        }

        for (int col = 0; col < n; col++) {
            memo[0][col] = 1;
        }

        for (int row = 1; row < m; row++) {
            for (int col = 1; col < n; col++) {
                memo[row][col] = memo[row - 1][col] + memo[row][col - 1];
            }
        }

        return memo[m - 1][n - 1];
    }

    /**
     * Luke: Backtrack
     *
     * Timeout
     *
     * Time: O(n ^ 2)
     * Space: O(m * n)
     */

    private int counter = 0;

    public int uniquePathsLukeBacktrack(int m, int n) {

        LinkedList<Node> path = new LinkedList<>();

        backtrack(m, n, 0, 0, path);

        return counter;
    }

    private void backtrack(int m, int n, int row, int col, LinkedList<Node> path) {

        /*
        if (row > m - 1 || col > n - 1) {
            return;
        }
        */

        path.add(new Node(row, col));

        if (row == m - 1 && col == n - 1) {
            // log.debug(path);
            counter++;
        } else {
            if (row + 1 <= m - 1) {
                backtrack(m, n, row + 1, col, path);
                path.removeLast();
            }

            if (col + 1 <= n - 1) {
                backtrack(m, n, row, col + 1, path);
                path.removeLast();
            }
        }
    }

    record Node(int row, int col) {
    }
}
