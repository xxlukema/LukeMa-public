package com.learn.backtrack;


import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Stack;

import lombok.extern.log4j.Log4j2;


/**
 * LC 85
 */
@Log4j2
public class MaxReactangle {

    @SuppressWarnings("unused")
    public static void main(String[] args) {

        char[][] matrix1 = {
                { '1', '0', '1', '0', '0' },
                { '1', '0', '1', '1', '1' },
                { '1', '1', '1', '1', '1' },
                { '1', '0', '0', '1', '0' } };

        char[][] matrix2 = {
                { 1 } };

        char[][] matrix3 = {
                { '0', '1', '1', '0', '1' },
                { '1', '1', '0', '1', '0' },
                { '0', '1', '1', '1', '0' },
                { '1', '1', '1', '1', '0' },
                { '1', '1', '1', '1', '1' },
                { '0', '0', '0', '0', '0' } };

        MaxReactangle maxReactangle = new MaxReactangle();

        // int ret = maxReactangle.maximalRectangleLukeBrute(matrix3);
        // int ret = maxReactangle.maximalRectangleLukeBruteDp(matrix3);
        int ret = maxReactangle.maximalRectangleLukeHistogram(matrix2);
        log.debug("Max reactangle: {}", () -> ret);
    }

    /**
     * Luke: Histogram
     * 
     * Runtime: 13 ms, faster than 81.84% of Java online submissions for Maximal Rectangle.
     * Memory Usage: 55.4 MB, less than 17.87% of Java online submissions for Maximal Rectangle.
     * 
     * Time: O(M * N * log(N)) --- M: ROWS, N: COLS --- Worst case is O(N ^ 2) when heights are sorted.
     * Space: O(M * N) --- One demensional DP "O(N)" is OK.
     */
    public int maximalRectangleLukeHistogram(final char[][] matrix) {
        final int ROWS = matrix.length;
        final int COLS = matrix[0].length;

        /**
         * Use maxHeights as histgram in LC 83
         */
        final int[][] maxHeights = new int[ROWS][COLS];

        /**
         * Init maxHeights
         * 
         * Time: O(M * N) --- M: ROWS, N: COLS
         * Space: O(M * N)
         */
        for (int row = ROWS - 1; row >= 0; row--) {
            for (int col = 0; col < COLS; col++) {
                if (row == ROWS - 1) {
                    maxHeights[row][col] = matrix[row][col] == '0' ? 0 : 1;
                } else {
                    maxHeights[row][col] = matrix[row][col] == '0' ? 0 : 1 + maxHeights[row + 1][col];
                }
            }
        }

        // log.debug("maxHeights: {}", () -> maxHeights);

        /**
         * Find largest histogram for each row
         */
        int area = 0;
        for (int row = 0; row < ROWS; row++) {
            /**
             * Histogram is maxHeights or that row
             */
            int histgramArea = largestHistogramArea(maxHeights[row]);
            area = Math.max(area, histgramArea);
        }

        return area;
    }

    private int largestHistogramArea(final int[] heights) {
        final int COLS = heights.length;
        int area = 0;

        Deque<Integer> stack = new ArrayDeque<>();
        stack.push(-1);

        for (int col = 0; col < COLS; col++) {
            while (stack.peek() != -1 && heights[stack.peek()] >= heights[col]) {
                int curr = stack.pop();
                /**
                 * Important: Not "int width = col - curr";
                 */
                int width = (col - 1) - stack.peek();
                area = Math.max(area, width * heights[curr]);
            }

            stack.push(col);
        }

        while (stack.peek() != -1) {
            int curr = stack.pop();
            int height = heights[curr];
            /**
             * Important: Not "int width = (COLS - 1) - curr";
             */
            int width = (COLS - 1) - stack.peek();
            area = Math.max(area, height * width);
        }

        return area;
    }

    // Get the maximum area in a histogram given its heights
    public int leetcode84(int[] heights) {
        Stack<Integer> stack = new Stack<>();
        stack.push(-1);
        int maxarea = 0;
        for (int i = 0; i < heights.length; ++i) {
            while (stack.peek() != -1 && heights[stack.peek()] >= heights[i]) {
                maxarea = Math.max(maxarea, heights[stack.pop()] * ((i - 1) - stack.peek()));
            }
            stack.push(i);
        }
        while (stack.peek() != -1) {
            maxarea = Math.max(maxarea, heights[stack.pop()] * ((heights.length - 1) - stack.peek()));
        }
        return maxarea;
    }

    /**
     * LC Top-Down DP
     */
    public int maximalRectangle(char[][] matrix) {

        if (matrix.length == 0) {
            return 0;
        }

        int maxarea = 0;

        int[] dp = new int[matrix[0].length];

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {

                // update the state of this row's histogram using the last row's histogram
                // by keeping track of the number of consecutive ones

                dp[j] = matrix[i][j] == '1' ? dp[j] + 1 : 0;
            }
            // update maxarea with the maximum area from this row's histogram
            maxarea = Math.max(maxarea, leetcode84(dp));
        }
        return maxarea;
    }

    /**
     * Luke: Brute force with DP
     * 
     * Runtime: 21 ms, faster than 73.15% of Java online submissions for Maximal Rectangle.
     * Memory Usage: 54.5 MB, less than 58.23% of Java online submissions for Maximal Rectangle.
     * 
     * Time: O(M * N ^ 2) --- M: ROWS, N: COLS
     * Space: O(M * N)
     */
    public int maximalRectangleLukeBruteDp(char[][] matrix) {
        final int ROWS = matrix.length;
        final int COLS = matrix[0].length;

        final int[][] maxHights = new int[ROWS][COLS];

        /**
         * Init dp. 
         * 
         * Time: O(M * N) --- M: ROWS, N: COLS
         * Space: O(M * N)
         */
        for (int row = ROWS - 1; row >= 0; row--) {
            for (int col = 0; col < COLS; col++) {
                if (matrix[row][col] != '0') {
                    if (row == ROWS - 1) {
                        maxHights[row][col] = 1;
                    } else {
                        maxHights[row][col] += maxHights[row + 1][col] + 1;
                    }
                }
            }
        }

        // log.debug("maxHights: {}", () -> maxHights);

        int area = 0;

        /**
         * Iterate throgh each node of matrix.
         * 
         * Time: O(M * N * ) --- M: ROWS, N: COLS
         * Space: O(1)
         */
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                /**
                 * Time: (N) --- M: ROWS, N: COLS
                 * Space: O(1)
                 */
                area = Math.max(area, maxAreaDp(matrix, ROWS, COLS, row, col, maxHights));
            }
        }

        return area;
    }

    /**
     * Time: O(N) --- M: ROWS, N: COLS
     * Space: O(1)
     */
    private int maxAreaDp(final char[][] matrix, final int ROWS, final int COLS, int row, int col, final int[][] maxHight) {
        if (matrix[row][col] == 0 || row >= ROWS || col >= COLS) {
            return 0;
        }

        int area = 0;

        int minHeight = Integer.MAX_VALUE;
        for (int c = col; c < COLS; c++) {
            if (matrix[row][c] == '0') {
                break;
            }

            int width = c - col + 1;
            minHeight = Math.min(minHeight, maxHight[row][c]);
            area = Math.max(area, width * minHeight);
        }

        return area;
    }

    /**
     * Luke: Brute Force
     * 
     * Time Limit Exceeded
     * 
     * Time: O((M * N) ^ 2) --- Without DP
     * Space: O(1) --- Without DP
     */
    public int maximalRectangleLukeBrute(char[][] matrix) {
        final int ROWS = matrix.length;
        final int COLS = matrix[0].length;

        int area = 0;
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                area = Math.max(area, maxArea(matrix, ROWS, COLS, row, col));
            }
        }

        return area;
    }

    int maxArea(final char[][] matrix, final int ROWS, final int COLS, int row, int col) {
        if (row >= ROWS || col >= COLS || matrix[row][col] == '0') {
            return 0;
        }

        /**
         * matrix[row][col] == 1
         */
        int area = 0;

        for (int r = row; r < ROWS; r++) {
            if (matrix[r][col] == '0') {
                break;
            }

            int minHeight = Integer.MAX_VALUE;
            for (int c = col; c < COLS; c++) {
                if (matrix[r][c] == '0') {
                    break;
                }

                int height = 1;
                while (r + height < ROWS && matrix[r + height][c] != '0') {
                    height++;
                }
                minHeight = Math.min(minHeight, height);
                area = Math.max(area, minHeight * (c - col + 1));
            }
        }

        return area;
    }
}
