package com.learn.other;


import lombok.extern.log4j.Log4j2;


/**
 * LC 74
 */
@Log4j2
public class Search2DMatrix {

    public static void main(String[] args) {

        /**
        int[][] matrix = {
                { 1, 3, 5, 7 },
                { 10, 11, 16, 20 },
                { 23, 30, 34, 60 } };
         */

        // int[][] matrix = { { 1 } };

        // int[][] matrix = { { 1 }, { 3 } };

        int[][] matrix = { { 1, 1 } };

        int target = 2;
        // int target = 13;

        Search2DMatrix search2dMatrix = new Search2DMatrix();
        var ret = search2dMatrix.searchMatrixLc(matrix, target);

        log.debug("Search 2D array: {}", () -> ret);
    }

    /**
     * LC
     * 
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Search a 2D Matrix.
     * Memory Usage: 42.7 MB, less than 52.51% of Java online submissions for Search a 2D Matrix.
     * 
     * Time: O(log(m * n)) = O(log(m) + log(n))
     * Space: O(1)
     */
    public boolean searchMatrixLc(int[][] matrix, int target) {
        final int ROWS = matrix.length;

        if (ROWS == 0) {
            return false;
        }

        final int COLS = matrix[0].length;

        // binary search
        int left = 0;
        int right = ROWS * COLS - 1;
        while (left <= right) {
            int pivotIdx = (left + right) / 2;
            int pivotElement = matrix[pivotIdx / COLS][pivotIdx % COLS];
            if (target == pivotElement) {
                return true;
            } else {
                if (target < pivotElement) {
                    right = pivotIdx - 1;
                } else {
                    left = pivotIdx + 1;
                }
            }
        }

        return false;
    }

    /**
     * Luke
     * 
     * Runtime: 1 ms, faster than 36.89% of Java online submissions for Search a 2D Matrix.
     * Memory Usage: 41.8 MB, less than 95.00% of Java online submissions for Search a 2D Matrix.
     * 
     * Time: O(log(m) + log(n)) = O(log(m * n))
     * Space: O(1)
     */
    public boolean searchMatrix(int[][] matrix, int target) {

        // Search row
        int idx = binarySearchForRow(matrix, target, 0, matrix.length - 1);

        // Searc col
        return binarySearchForRow(matrix, target, idx, 0, matrix[0].length - 1);
    }

    private boolean binarySearchForRow(int[][] matrix, int target, int row, int low, int high) {

        if (high == low) {
            return matrix[row][low] == target;
        }

        if (high == low + 1) {
            return matrix[row][low] == target || matrix[row][high] == target;
        }

        int mid = (low + high) / 2;

        if (matrix[row][mid] == target) {
            return true;
        } else if (matrix[row][mid] > target) {
            return binarySearchForRow(matrix, target, row, low, mid);
        } else {
            return binarySearchForRow(matrix, target, row, mid, high);
        }
    }

    private int binarySearchForRow(int[][] matrix, int target, int low, int high) {
        if (low == high) {
            return low;
        }

        // Unable to find. Too small
        if (matrix[low][0] > target) {
            return low;
        }

        if (matrix[high][0] <= target) {
            return high;
        }

        int mid = (low + high) / 2;
        if (matrix[mid][0] <= target && matrix[mid + 1][0] > target) {
            return mid;
        } else if (matrix[mid][0] < target) {
            return binarySearchForRow(matrix, target, mid, high);
        } else {
            return binarySearchForRow(matrix, target, low, mid);
        }
    }

}
