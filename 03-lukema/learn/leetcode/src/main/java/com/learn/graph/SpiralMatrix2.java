package com.learn.graph;


public class SpiralMatrix2 {

    public static void main(String[] args) {

        SpiralMatrix2 spiralMatrix2 = new SpiralMatrix2();

        final int n = 5;

        var result = spiralMatrix2.generateMatrix(n);

        GraphUtils.printGraph(result);

    }

    /**
     * Luke
     * 
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Spiral Matrix II.
     * Memory Usage: 42.1 MB, less than 41.81% of Java online submissions for Spiral Matrix II.
     * 
     * Time: O(n)
     * Space: O(n ^ 2)
     */

    int counter = 1;

    public int[][] generateMatrix(int n) {

        int[][] result = new int[n][n];

        for (int iterator = 0; iterator < (n + 1) / 2; iterator++) {
            generateMatrix(n, result, iterator);
        }

        return result;
    }

    private void generateMatrix(int n, int[][] result, int iterator) {

        // Top
        for (int col = iterator; col < n - iterator; col++) {
            result[iterator][col] = counter++;
        }

        // Right
        for (int row = iterator + 1; row < n - iterator; row++) {
            result[row][n - 1 - iterator] = counter++;
        }

        // Bottom
        if (iterator != (n + 1) / 2) {
            for (int col = n - 2 - iterator; col >= iterator; col--) {
                result[n - 1 - iterator][col] = counter++;
            }
        }

        // Left
        if (iterator != (n + 1) / 2) {
            for (int row = n - 2 - iterator; row > iterator; row--) {
                result[row][iterator] = counter++;
            }
        }
    }
}
