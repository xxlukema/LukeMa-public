package com.learn.other;


import org.junit.jupiter.api.Assertions;

import lombok.extern.log4j.Log4j2;


/**
 * LC - 221 Maximal Square
 *
 * Medium
 */
@Log4j2
public class MaximalSquare {

    public static void main(String[] args) {

        /**
         * Expected: 4
         */
        /*
        final char[][] matrix = {
                { '1', '0', '1', '0', '0' },
                { '1', '0', '1', '1', '1' },
                { '1', '1', '1', '1', '1' },
                { '1', '0', '0', '1', '0' } };
        */

        /*
         * Expected: 1
         */
        final char[][] matrix = {
                { '0', '1' },
                { '1', '0' } };

        /*
         * Expected: 4
         */
        /*
        final char[][] matrix = {
                { '1', '1' },
                { '1', '1' } };
        */

        /*
         * Expected: 1
         */
        /*
        final char[][] matrix = {
                { '1' } };
        */

        /*
         * Expected: 1
         */
        /*
        final char[][] matrix = {
                { '0', '1' } };
        */

        MaximalSquare maximalSquare = new MaximalSquare();

        var maximalSquareLukeBrute = maximalSquare.maximalSquareLukeBrute(matrix);
        log.debug("Maximal Square: {}", () -> maximalSquareLukeBrute);
        log.debug("Maximal Square {} OK", () -> "maximalSquareLukeBrute");

        var maximalSquareLcDp = maximalSquare.maximalSquareLcDp(matrix);
        Assertions.assertEquals(maximalSquareLukeBrute, maximalSquareLcDp);
        log.debug("Maximal Square {} OK", () -> "maximalSquareLcDp");

    }

    /**
     * LC - DP - 1D
     *
     * I do not understand this yet. skip for now.
     *
     * Time: O(M * N)
     * Space: O(N)
     */
    public int maximalSquareLcDp1D(char[][] matrix) {
        final int rows = matrix.length, cols = rows > 0 ? matrix[0].length : 0;

        final int[] dp = new int[cols + 1];

        int maxsqlen = 0, prev = 0;
        for (int r = 1; r <= rows; r++) {
            for (int c = 1; c <= cols; c++) {
                int temp = dp[c];
                if (matrix[r - 1][c - 1] == '1') {
                    dp[c] = Math.min(Math.min(dp[c - 1], prev), dp[c]) + 1;
                    maxsqlen = Math.max(maxsqlen, dp[c]);
                } else {
                    dp[c] = 0;
                }
                prev = temp;
            }
        }
        return maxsqlen * maxsqlen;
    }

    /**
     * LC - DP - 2D
     *
     * dp(r, c) = min(dp(r − 1, c), dp(r − 1, c − 1), dp(r, c − 1)) + 1
     *
     * Runtime: 23 ms, faster than 18.00% of Java online submissions for Maximal Square.
     * Memory Usage: 67.4 MB, less than 23.58% of Java online submissions for Maximal Square.
     *
     * Time: O(M * N)
     * Space: O(M * N)
     */
    public int maximalSquareLcDp(final char[][] matrix) {
        final int ROWS = matrix.length;
        final int COLS = matrix[0].length;

        final int[][] dp = new int[ROWS][COLS];

        for (int r = 0; r < ROWS; r++) {
            dp[r][0] = matrix[r][0] == '1' ? 1 : 0;
        }

        for (int c = 0; c < COLS; c++) {
            dp[0][c] = matrix[0][c] == '1' ? 1 : 0;
        }

        for (int r = 1; r < ROWS; r++) {
            for (int c = 1; c < COLS; c++) {
                if (matrix[r][c] == '1') {
                    // int min = IntStream.of(dp[r - 1][c - 1], dp[r][c - 1], dp[r - 1][c]).min().getAsInt();
                    int min = Math.min(Math.min(dp[r - 1][c - 1], dp[r][c - 1]), dp[r - 1][c]);
                    dp[r][c] = 1 + min;
                }
            }
        }

        int maxWidth = 0;
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                maxWidth = Math.max(maxWidth, dp[r][c]);
            }
        }

        // DpUtils.print(dp);

        return maxWidth * maxWidth;
    }

    /**
     * Luke
     *
     * Runtime: 186 ms, faster than 6.64% of Java online submissions for Maximal Square.
     * Memory Usage: 55.9 MB, less than 86.44% of Java online submissions for Maximal Square.
     *
     * Time: O(M * N) * O(maxWidth * maxWidth)
     * Space: O(1)
     */
    public int maximalSquareLukeBrute(final char[][] matrix) {
        final int ROWS = matrix.length;

        if (ROWS == 0) {
            return 0;
        }

        final int COLS = matrix[0].length;

        /*
        if (ROWS < 1) {
            return 0;
        }

        if (COLS < 1) {
            return 0;
        }

        if (ROWS == 1) {
            for (int c = 0; c < COLS; c++) {
                if (matrix[0][c] == '1') {
                    return 1;
                }
            }
        }

        if (COLS == 1) {
            for (int r = 0; r < ROWS; r++) {
                if (matrix[r][0] == '1') {
                    return 1;
                }
            }
        }
        */

        int maxWidth = 0;
        for (int r = 0; r < ROWS - maxWidth; r++) {
            for (int c = 0; c < COLS - maxWidth; c++) {
                if (matrix[r][c] == '1') {
                    var width = maxWidth(matrix, r, c, ROWS, COLS);
                    maxWidth = Math.max(maxWidth, width);
                }
            }
        }

        return maxWidth * maxWidth;
    }

    private int maxWidth(char[][] matrix, int row, int col, int ROWS, int COLS) {
        int width = 1;

        while (true) {
            for (int r = 0; r < width; r++) {
                for (int c = 0; c < width; c++) {
                    if (matrix[r + row][c + col] != '1') {
                        return width - 1;
                    }
                }
            }

            width++;

            if (row + width > ROWS || col + width > COLS) {
                return width - 1;
            }
        }
    }

}
