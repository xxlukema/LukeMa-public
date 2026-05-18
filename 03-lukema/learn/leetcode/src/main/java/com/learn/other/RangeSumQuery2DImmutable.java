package com.learn.other;


import com.learn.dp.DpUtils;

import lombok.extern.log4j.Log4j2;


/**
 * LC-304 Range Sum Query 2D - Immutable
 *
 * Medium
 *
 * Given a 2D matrix matrix, handle multiple queries of the following type:
 *
 *     Calculate the sum of the elements of matrix inside the rectangle defined by its upper left corner (row1, col1) and lower right corner (row2, col2).
 *
 * Implement the NumMatrix class:
 *
 *     * NumMatrix(int[][] matrix) Initializes the object with the integer matrix matrix.
 *     * int sumRegion(int row1, int col1, int row2, int col2) Returns the sum of the elements of matrix inside the rectangle defined by its upper left
 *       corner (row1, col1) and lower right corner (row2, col2).
 *
 * You must design an algorithm where sumRegion works on O(1) time complexity.
 *
 * Example 1:
 * Input
 * ["NumMatrix", "sumRegion", "sumRegion", "sumRegion"]
 * [[[[3, 0, 1, 4, 2], [5, 6, 3, 2, 1], [1, 2, 0, 1, 5], [4, 1, 0, 1, 7], [1, 0, 3, 0, 5]]], [2, 1, 4, 3], [1, 1, 2, 2], [1, 2, 2, 4]]
 * Output
 * [null, 8, 11, 12]
 *
 * Explanation
 * NumMatrix numMatrix = new NumMatrix([[3, 0, 1, 4, 2], [5, 6, 3, 2, 1], [1, 2, 0, 1, 5], [4, 1, 0, 1, 7], [1, 0, 3, 0, 5]]);
 * numMatrix.sumRegion(2, 1, 4, 3); // return 8 (i.e sum of the red rectangle)
 * numMatrix.sumRegion(1, 1, 2, 2); // return 11 (i.e sum of the green rectangle)
 * numMatrix.sumRegion(1, 2, 2, 4); // return 12 (i.e sum of the blue rectangle)
 *
 * Constraints:
 *     m == matrix.length
 *     n == matrix[i].length
 *     1 <= m, n <= 200
 *     -10 ^ 4 <= matrix[i][j] <= 10 ^ 4
 *     0 <= row1 <= row2 < m
 *     0 <= col1 <= col2 < n
 *     At most 10 ^ 4 calls will be made to sumRegion.
 */
@Log4j2
public class RangeSumQuery2DImmutable {

    public static void main(String[] args) {

        final int[][] matrix = {
                { -4, -5 } };

        /*
        final int[][] matrix = {
                { 3, 0, 1, 4, 2 },
                { 5, 6, 3, 2, 1 },
                { 1, 2, 0, 1, 5 },
                { 4, 1, 0, 1, 7 },
                { 1, 0, 3, 0, 5 } };
        */

        /**
         * Expected: [null,8,11,12]
         */
        //{2,1,4,3},{1,1,2,2},{1,2,2,4};

        NumMatrix numMatrix = new NumMatrix(matrix);

        var ret = numMatrix.sumRegion(0, 0, 0, 0);
        log.debug("sumRange: {}", ret);

        ret = numMatrix.sumRegion(0, 0, 0, 1);
        log.debug("sumRange: {}", ret);

        ret = numMatrix.sumRegion(0, 1, 0, 1);
        log.debug("sumRange: {}", ret);

        /*
        var ret = numMatrix.sumRegion(2, 1, 4, 2);
        log.debug("sumRange: {}", ret);

        ret = numMatrix.sumRegion(1, 1, 2, 2);
        log.debug("sumRange: {}", ret);

        ret = numMatrix.sumRegion(1, 2, 2, 4);
        log.debug("sumRange: {}", ret);
        */
    }

}


/**
 * Luke
 *
 * Runtime: 121 ms Beats 99.94%
 * Memory: 69.3 MB Beats 72.83%
 *
 * Constructor: Time: O(M * N), Space: O(M * N)
 * Time: O(1)
 * Space: O(n * m)
 */
@Log4j2
class NumMatrix {

    int[][] sum;

    public NumMatrix(int[][] matrix) {
        sum = new int[matrix.length][matrix[0].length];
        sum[0][0] = matrix[0][0];

        for (int c = 1; c < matrix[0].length; c++) {
            sum[0][c] = sum[0][c - 1] + matrix[0][c];
        }

        for (int r = 1; r < matrix.length; r++) {
            sum[r][0] = sum[r - 1][0] + matrix[r][0];

            for (int c = 1; c < matrix[0].length; c++) {
                sum[r][c] = sum[r - 1][c] + sum[r][c - 1] - sum[r - 1][c - 1] + matrix[r][c];
            }
        }

        DpUtils.print(matrix);
        DpUtils.print(sum);
    }

    public int sumRegion(int row1, int col1, int row2, int col2) {
        // @formatter: off

        log.debug("----------------- {}, {}, {}, {}", sum[row2][col2], row1 == 0 ? 0 : sum[row1 - 1][col2],
                col1 == 0 ? 0 : sum[row2][col1 - 1],
                row1 == 0 || col1 == 0 ? 0 : sum[row1 - 1][col1 - 1]);

        return sum[row2][col2] -
                (row1 == 0 ? 0 : sum[row1 - 1][col2]) -
                (col1 == 0 ? 0 : sum[row2][col1 - 1]) +
                (row1 == 0 || col1 == 0 ? 0 : sum[row1 - 1][col1 - 1]);
        // @formatter: on
    }

}

/**
 * Your NumMatrix object will be instantiated and called as such:
 * NumMatrix obj = new NumMatrix(matrix);
 * int param_1 = obj.sumRegion(row1,col1,row2,col2);
 */
