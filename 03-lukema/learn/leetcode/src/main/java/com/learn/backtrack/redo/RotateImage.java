package com.learn.backtrack.redo;


import com.learn.dp.DpUtils;

import lombok.extern.log4j.Log4j2;


/**
 * LC - 48 - Rotate Image
 *
 * Medium
 *
 * You are given an n x n 2D matrix representing an image, rotate the image by 90 degrees (clockwise).
 *
 * You have to rotate the image in-place, which means you have to modify the input 2D matrix directly. DO NOT allocate another 2D matrix and do the rotation.
 *
 * Example 1:
 * Input: matrix = [[1,2,3],[4,5,6],[7,8,9]]
 * Output: [[7,4,1],[8,5,2],[9,6,3]]
 *
 * Example 2:
 * Input: matrix = [[5,1,9,11],[2,4,8,10],[13,3,6,7],[15,14,12,16]]
 * Output: [[15,13,2,5],[14,3,4,1],[12,6,8,9],[16,7,10,11]]
 *
 * Constraints:
 *     n == matrix.length == matrix[i].length
 *     1 <= n <= 20
 *     -1000 <= matrix[i][j] <= 1000
 */
@Log4j2
public class RotateImage {

    public static void main(String[] args) {

        /*
        final int[][] matrix1 = {
                { 1, 2, 3 },
                { 4, 5, 6 },
                { 7, 8, 9 } };
        */

        /*
            7   4   1
            8   5   2
            9   6   3
        */

        final int[][] matrix2 = {
                { 5, 1, 9, 11 },
                { 2, 4, 8, 10 },
                { 13, 3, 6, 7 },
                { 15, 14, 12, 16 } };

        /*
              15   13    2    5
              14    3    4    1
              12    6    8    9
              16    7   10   11
        */

        RotateImage rotateImage = new RotateImage();
        log.debug("Rotate Image Before:");
        DpUtils.print(matrix2);

        rotateImage.rotate(matrix2);
        log.debug("Rotate Image After:");
        DpUtils.print(matrix2);
    }

    /**
     * Luke - Iterative
     *
     * Runtime: 1 ms Beats 13.42%
     * Memory: 42.5 MB Beats 67.95%
     *
     * Time: O(N) --- Each cell is rotated once.
     * Space: O(1)
     */
    public void rotate(int[][] m) {

        final int ROWS = m.length;

        for (int col = 0; col < ROWS / 2; col++) {
            for (int row = col; row < ROWS - col - 1; row++) {
                int tmp = m[row][col];
                m[row][col] = m[ROWS - 1 - col][row];
                m[ROWS - 1 - col][row] = m[ROWS - 1 - row][ROWS - 1 - col];
                m[ROWS - 1 - row][ROWS - 1 - col] = m[col][ROWS - 1 - row];
                m[col][ROWS - 1 - row] = tmp;
            }
        }
    }

}
