package com.learn.other;


import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import lombok.extern.log4j.Log4j2;


/**
 * LC-311 Sparse Matrix Multiplication
 *
 * Medium
 *
 * Given two sparse matrices mat1 of size m x k and mat2 of size k x n, return the result of mat1 x mat2. You may assume that multiplication is always possible.
 *
 * Example 1:
 * Input: mat1 = [[1,0,0],[-1,0,3]], mat2 = [[7,0,0],[0,0,0],[0,0,1]]
 * Output: [[7,0,0],[-7,0,3]]
 *
 * Example 2:
 * Input: mat1 = [[0]], mat2 = [[0]]
 * Output: [[0]]
 *
 * Constraints:
 *     m == mat1.length
 *     k == mat1[i].length == mat2.length
 *     n == mat2[i].length
 *     1 <= m, n, k <= 100
 *     -100 <= mat1[i][j], mat2[i][j] <= 100
 */
@Log4j2
public class SparseMatrixMultiplication {

    public static void main(String[] args) {

        /*
        final int[][] mat1 = {
                { 1, 0, 0 },
                { -1, 0, 3 } };

        final int[][] mat2 = {
                { 7, 0, 0 },
                { 0, 0, 0 },
                { 0, 0, 1 } };
        */

        /**
         * Expected: 17
         */
        final int[][] mat1 = {
                { 1, -5 }
        };
        final int[][] mat2 = {
                { 12 },
                { -1 }
        };

        SparseMatrixMultiplication sparseMatrixMultiplication = new SparseMatrixMultiplication();

        var multiplyNaive = sparseMatrixMultiplication.multiplyNaive(mat1, mat2);
        log.debug("Sparse Matrix Multiplication: {}", () -> multiplyNaive);
        log.debug("Sparse Matrix Multiplication {} OK", () -> "multiplyNaive");

        var multiplyRecord = sparseMatrixMultiplication.multiplyRecord(mat1, mat2);
        log.debug("Sparse Matrix Multiplication: {}", () -> multiplyRecord);
        log.debug("Sparse Matrix Multiplication {} OK", () -> "multiplyRecord");
    }

    /**
     * Luke - Naive
     *      - Trick 1: How to effectively use space for sparse array?
     *      - Trick 2: Use Map<Cell, Integer> to store non-zero values. This saves space of array.
     *      - Trick 3: Map/Cell operation is minimum 10x slower than array operations.
     *
     * Runtime: 2 ms Beats 56.3%
     * Memory: 42.8 MB Beats 88.73%
     *
     * Time: O(M * N * K), where K is mat1[0].length
     * Space: O(1)
     */
    public int[][] multiplyNaive(int[][] mat1, int[][] mat2) {
        final int ROWS = mat1.length;
        final int LEN = mat1[0].length;
        final int COLS = mat2[0].length;

        final int[][] result = new int[ROWS][COLS];

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                int sum = 0;
                for (int i = 0; i < LEN; i++) {
                    if (mat1[row][i] != 0 && mat2[i][col] != 0) {
                        sum += mat1[row][i] * mat2[i][col];
                    }
                }
                result[row][col] = sum;
            }
        }

        return result;
    }

    /**
     * Luke - Map of records Cells
     *      - Trick 1: How to effectively use space for sparse array?
     *      - Trick 2: Use Map<Cell, Integer> to store non-zero values. This saves space of array.
     *      - Trick 3: Map/Cell operation is minimum 10x slower than array operations.
     *
     * Runtime: 20 ms Beats 5.56%
     * Memory: 43.1 MB Beats 80.63%
     *
     * Time: O(M * N * K), where K is mat1[0].length
     * Space: O(number of Cells with values)
     */
    public int[][] multiplyRecord(int[][] mat1, int[][] mat2) {
        final int ROWS = mat1.length;
        final int LEN = mat1[0].length;
        final int COLS = mat2[0].length;

        final int[][] result = new int[ROWS][COLS];

        record Cell(int row, int col) {
        }

        final Map<Cell, Integer> map1 = new HashMap<>();

        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < LEN; c++) {
                if (mat1[r][c] != 0) {
                    map1.put(new Cell(r, c), mat1[r][c]);
                }
            }
        }

        final Map<Cell, Integer> map2 = new HashMap<>();

        for (int r = 0; r < LEN; r++) {
            for (int c = 0; c < COLS; c++) {
                if (mat2[r][c] != 0) {
                    map2.put(new Cell(r, c), mat2[r][c]);
                }
            }
        }

        Set<Cell> keySet = map1.keySet();

        for (Cell cur1 : keySet) {
            for (int c = 0; c < COLS; c++) {
                Cell cur2 = new Cell(cur1.col, c);
                if (map2.containsKey(cur2)) {
                    result[cur1.row][c] += map1.get(cur1) * map2.get(cur2);
                }
            }
        }

        return result;
    }
}
