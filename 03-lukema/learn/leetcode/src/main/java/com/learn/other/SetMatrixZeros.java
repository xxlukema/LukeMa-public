package com.learn.other;


import java.util.Stack;

import com.learn.graph.GraphUtils;


/**
 * LC 73
 */
public class SetMatrixZeros {

    public static void main(String[] args) {

        int[][] matrix = {
                { 0, 1, 2, 0 },
                { 3, 4, 5, 2 },
                { 1, 3, 1, 5 } };

        SetMatrixZeros setMatrixZeros = new SetMatrixZeros();
        // setMatrixZeros.setZeroes(matrix);
        setMatrixZeros.setZeroesLc2(matrix);

        GraphUtils.printGraph(matrix);

    }

    /**
     * Luke
     * 
     * Runtime: 4 ms, faster than 18.37% of Java online submissions for Set Matrix Zeroes.
     * Memory Usage: 54.9 MB, less than 6.04% of Java online submissions for Set Matrix Zeroes.
     * 
     * Time: O(m * n)
     * Space: O(m + n)
     */
    public void setZeroes(int[][] matrix) {

        record Node(int row, int col) {
        }

        Stack<Node> stack = new Stack<>();

        for (int r = 0; r < matrix.length; r++) {
            for (int c = 0; c < matrix[0].length; c++) {
                if (matrix[r][c] == 0) {
                    stack.push(new Node(r, c));
                }
            }
        }

        // All zero
        if (stack.size() == matrix.length + matrix[0].length) {
            return;
        }

        while (!stack.isEmpty()) {
            Node node = stack.pop();
            for (int c = 0; c < matrix[0].length; c++) {
                matrix[node.row][c] = 0;
            }
            for (int r = 0; r < matrix.length; r++) {
                matrix[r][node.col] = 0;
            }
        }
    }

    /**
     * LC 2 - Space: O(1)
     */
    public void setZeroesLc2(int[][] matrix) {
        Boolean firstColZero = false;
        int R = matrix.length;
        int C = matrix[0].length;

        for (int row = 0; row < R; row++) {

            // Since first cell for both first row and first column is the same i.e. matrix[0][0]
            // We can use an additional variable for either the first row/column.
            // For this solution we are using an additional variable "firstColZero" for the first column
            // and using matrix[0][0] for the first row.
            if (matrix[row][0] == 0) {
                firstColZero = true;
            }

            for (int col = 1; col < C; col++) {
                // If an element is zero, we set the first element of the corresponding row and column to 0
                if (matrix[row][col] == 0) {
                    matrix[0][col] = 0;
                    matrix[row][0] = 0;
                }
            }
        }

        // Iterate over the array once again and using the first row and first column, update the elements.
        for (int row = 1; row < R; row++) {
            for (int col = 1; col < C; col++) {
                if (matrix[row][0] == 0 || matrix[0][col] == 0) {
                    matrix[row][col] = 0;
                }
            }
        }

        // See if the first row needs to be set to zero as well
        if (matrix[0][0] == 0) {
            for (int col = 0; col < C; col++) {
                matrix[0][col] = 0;
            }
        }

        // See if the first column needs to be set to zero as well
        if (firstColZero) {
            for (int row = 0; row < R; row++) {
                matrix[row][0] = 0;
            }
        }
    }
}
