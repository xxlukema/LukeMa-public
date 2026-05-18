package com.learn.backtrack.redo;


import lombok.extern.log4j.Log4j2;


/**
 * LC - 64 - Minimum Path Sum
 *
 * Medium
 *
 * Given a m x n grid filled with non-negative numbers, find a path from top left to bottom right, which minimizes the sum of all numbers along its path.
 *
 * Note: You can only move either down or right at any point in time.
 *
 * Example 1:
 * Input: grid = [[1,3,1],[1,5,1],[4,2,1]]
 * Output: 7
 * Explanation: Because the path 1 → 3 → 1 → 1 → 1 minimizes the sum.
 *
 * Example 2:
 * Input: grid = [[1,2,3],[4,5,6]]
 * Output: 12
 *
 * Constraints:
 *     m == grid.length
 *     n == grid[i].length
 *     1 <= m, n <= 200
 *     0 <= grid[i][j] <= 100
 */
@Log4j2
public class MinimumPathSum {

    public static void main(String[] args) {

        /**
         * Expected: 7
         */
        /*
        final int[][] grid = {
                { 1, 3, 1 },
                { 1, 5, 1 },
                { 4, 2, 1 } };
        */

        /**
         * Expected: 83
         */
        final int[][] grid = {
                { 5, 0, 1, 1, 2, 1, 0, 1, 3, 6, 3, 0, 7, 3, 3, 3, 1 },
                { 1, 4, 1, 8, 5, 5, 5, 6, 8, 7, 0, 4, 3, 9, 9, 6, 0 },
                { 2, 8, 3, 3, 1, 6, 1, 4, 9, 0, 9, 2, 3, 3, 3, 8, 4 },
                { 3, 5, 1, 9, 3, 0, 8, 3, 4, 3, 4, 6, 9, 6, 8, 9, 9 },
                { 3, 0, 7, 4, 6, 6, 4, 6, 8, 8, 9, 3, 8, 3, 9, 3, 4 },
                { 8, 8, 6, 8, 3, 3, 1, 7, 9, 3, 3, 9, 2, 4, 3, 5, 1 },
                { 7, 1, 0, 4, 7, 8, 4, 6, 4, 2, 1, 3, 7, 8, 3, 5, 4 },
                { 3, 0, 9, 6, 7, 8, 9, 2, 0, 4, 6, 3, 9, 7, 2, 0, 7 },
                { 8, 0, 8, 2, 6, 4, 4, 0, 9, 3, 8, 4, 0, 4, 7, 0, 4 },
                { 3, 7, 4, 5, 9, 4, 9, 7, 9, 8, 7, 4, 0, 4, 2, 0, 4 },
                { 5, 9, 0, 1, 9, 1, 5, 9, 5, 5, 3, 4, 6, 9, 8, 5, 6 },
                { 5, 7, 2, 4, 4, 4, 2, 1, 8, 4, 8, 0, 5, 4, 7, 4, 7 },
                { 9, 5, 8, 6, 4, 4, 3, 9, 8, 1, 1, 8, 7, 7, 3, 6, 9 },
                { 7, 2, 3, 1, 6, 3, 6, 6, 6, 3, 2, 3, 9, 9, 4, 4, 8 } };

        MinimumPathSum minimumPathSum = new MinimumPathSum();

        var minPathSum = minimumPathSum.minPathSum(grid);
        log.debug("Minimum Path Sum: {}", () -> minPathSum);
        log.debug("Minimum Path Sum {} OK", () -> "minPathSum");

    }

    /**
     * Luke - DP - Tabulation
     *
     * Runtime: 2 ms Beats 92.58%
     * Memory: 46.4 MB Beats 15.83%
     *
     * Time: O(R * C)
     * Space: O(1)
     */
    public int minPathSum(final int[][] grid) {

        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            return 0;
        }

        final int ROWS = grid.length;
        final int COLS = grid[0].length;

        // final int[][] dp = new int[ROWS][COLS];

        for (int c = 1; c < COLS; c++) {
            grid[0][c] = grid[0][c - 1] + grid[0][c];
        }

        for (int r = 1; r < ROWS; r++) {
            grid[r][0] = grid[r - 1][0] + grid[r][0];
        }

        for (int r = 1; r < ROWS; r++) {
            for (int c = 1; c < COLS; c++) {
                grid[r][c] += Math.min(grid[r - 1][c], grid[r][c - 1]);
            }
        }

        return grid[ROWS - 1][COLS - 1];
    }
}
