package com.learn.backtrack;


import java.util.ArrayDeque;
import java.util.Deque;

import lombok.extern.log4j.Log4j2;


/**
 * LC 84
 */
@Log4j2
public class LargestReactagleInHistogram {

    public static void main(String[] args) {

        // int[] heights = { 2, 1, 5, 6, 2, 3 };
        // int[] heights = { 2 };
        int[] heights = { 0, 9 };

        LargestReactagleInHistogram largestReactagleInHistgram = new LargestReactagleInHistogram();

        // int ret = largestReactagleInHistgram.largestRectangleAreaBrute(heights);
        // int ret = largestReactagleInHistgram.largestRectangleAreaDpTopDown(heights);
        // int ret = largestReactagleInHistgram.largestRectangleAreaLcStack(heights);
        // int ret = largestReactagleInHistgram.largestRectangleAreaLukeStack(heights);
        int ret = largestReactagleInHistgram.largestRectangleAreaLukeDivideConquer(heights);
        log.debug("Largest reactangle in histogram: {}", () -> ret);
    }

    /**
     * Luke: Divide and Conquer
     * 
     * Time Limit Exceeded
     * 
     * Time: O(N * long(N)). Worst case is O(N ^ 2) when heights are sorted.
     * Space: O(1)
     */
    public int largestRectangleAreaLukeDivideConquer(int[] heights) {
        return largestRectangleAreaLukeDivideConquer(heights, 0, heights.length - 1);
    }

    private int largestRectangleAreaLukeDivideConquer(int[] heights, int left, int right) {
        if (left > right) {
            return 0;
        } else if (left == right) {
            return heights[left] * (right - left + 1);
        } else if (left == right - 1) {
            return Math.max(Math.min(heights[left], heights[right]) * (right - left + 1), Math.max(heights[left] * 1, heights[right] * 1));
        }

        int minHeight = Integer.MAX_VALUE;
        int minIdx = 0;

        for (int i = left; i <= right; i++) {
            if (heights[i] < minHeight) {
                minIdx = i;
                minHeight = heights[i];
            }
        }

        int areaMin = (right - left + 1) * minHeight;
        int areaLeft = largestRectangleAreaLukeDivideConquer(heights, left, minIdx - 1);
        int areaRight = largestRectangleAreaLukeDivideConquer(heights, minIdx + 1, right);

        return Math.max(areaMin, Math.max(areaLeft, areaRight));
    }

    /**
     * Luke: Stack
     * 
     * Runtime: 43 ms, faster than 80.81% of Java online submissions for Largest Rectangle in Histogram.
     * Memory Usage: 79.3 MB, less than 78.45% of Java online submissions for Largest Rectangle in Histogram.
     * 
     * Time: O(n)
     * Space: O(n)
     */
    public int largestRectangleAreaLukeStack(int[] heights) {
        int maxArea = 0;
        Deque<Integer> stack = new ArrayDeque<>();
        stack.push(-1);

        /**
         * push() element indexes to stack, until it meets a same height or lower one.
         */
        for (int i = 0; i < heights.length; i++) {

            /**
             * pop() the idexes from stack if next bar is not taller than current bar, and calculate the the popped area.
             */
            while (stack.peek() != -1 && heights[stack.peek()] >= heights[i]) {
                int currHeight = heights[stack.pop()];
                /**
                 * Important: Not "int currWidth = i - currIndex";
                 */
                int currWidth = (i - 1) - stack.peek();
                maxArea = Math.max(maxArea, currHeight * currWidth);
            }

            /**
             * else, push(index)
             */
            stack.push(i);
        }

        /**
         * calculate areas in remaining stack
         */
        while (stack.peek() != -1) {
            int currHeight = heights[stack.pop()];
            /**
             * Important: Not "int currWidth = (heights.length - 1) - currIndex";
             */
            int currWidth = (heights.length - 1) - stack.peek();
            maxArea = Math.max(maxArea, currWidth * currHeight);
        }

        return maxArea;
    }

    /**
     * LC: Stack
     * 
     * Runtime: 43 ms, faster than 80.81% of Java online submissions for Largest Rectangle in Histogram.
     * Memory Usage: 79.3 MB, less than 78.45% of Java online submissions for Largest Rectangle in Histogram.
     * 
     * Time: O(n)
     * Space: O(n)
     */
    public int largestRectangleAreaLcStack(int[] heights) {
        Deque<Integer> stack = new ArrayDeque<>();
        stack.push(-1);
        final int N = heights.length;
        int maxArea = 0;
        for (int i = 0; i < N; i++) {
            /**
             * pop() from stack if next block is lower or is same in height:
             */
            while ((stack.peek() != -1) && (heights[stack.peek()] >= heights[i])) {
                int currentHeight = heights[stack.pop()];
                int currentWidth = (i - 1) - stack.peek();
                maxArea = Math.max(maxArea, currentHeight * currentWidth);
            }
            /**
             * else, push() to stack
             */
            stack.push(i);
        }
        while (stack.peek() != -1) {
            int currentHeight = heights[stack.pop()];
            int currentWidth = (N - 1) - stack.peek();
            maxArea = Math.max(maxArea, currentHeight * currentWidth);
        }
        return maxArea;
    }

    /**
     * Luke: DP Top-Down
     * 
     * Time Limit Exceeded
     * 
     * Time: O(n ^ 2)
     * Space: O(1)
     */
    public int largestRectangleAreaDpTopDownFixedMemoryFaster(int[] heights) {
        int maxArea = 0;
        final int N = heights.length;

        // Space: O(1)
        int height = 0;

        // Time: O(n ^ 2)
        for (int r = 0; r < N; r++) {
            for (int c = r; c < N; c++) {
                if (r == c) {
                    height = heights[c];
                } else {
                    height = Math.min(heights[c], height);
                }
                /*
                int area = height * (c - r + 1);
                if (area < maxArea) {
                    continue;
                } else {
                    maxArea = area;
                }
                */
                maxArea = Math.max(maxArea, height * (c - r + 1));
            }
        }

        return maxArea;
    }

    /**
     * Luke: DP Top-Down
     * 
     * Time Limit Exceeded
     * 
     * Time: O(n ^ 2)
     * Space: O(1)
     */
    public int largestRectangleAreaDpTopDownFixedMemory(int[] heights) {
        int maxArea = 0;
        final int N = heights.length;

        // Space: O(1)
        int height = 0;

        // Time: O(n ^ 2)
        for (int r = 0; r < N; r++) {
            for (int c = r; c < N; c++) {
                if (r == c) {
                    height = heights[c];
                } else {
                    height = Math.min(heights[c], height);
                }
                int area = height * (c - r + 1);
                maxArea = Math.max(maxArea, area);
            }
        }

        return maxArea;
    }

    /**
     * Luke: DP Top-Down
     * 
     * Time Limit Exceeded
     * 
     * Time: O(n ^ 2)
     * Space: O(n ^ 2)
     */
    public int largestRectangleAreaDpTopDown(int[] heights) {
        int maxArea = 0;
        final int N = heights.length;

        // Space: O(n ^ 2)
        int[][] minHeights = new int[N][N];

        // Time: O(n ^ 2)
        for (int r = 0; r < N; r++) {
            for (int c = r; c < N; c++) {
                if (r == c) {
                    minHeights[r][c] = heights[c];
                } else {
                    minHeights[r][c] = Math.min(heights[c], minHeights[r][c - 1]);
                    // minHeights[c][r] = minHeights[r][c];
                }
                int area = minHeights[r][c] * (c - r + 1);
                maxArea = Math.max(maxArea, area);
            }
        }

        return maxArea;
    }

    /**
     * Luke: Brute Force
     * 
     * Time Limit Exceeded
     * 
     * Time: O(n ^ 2)
     * Space: O(1)
     */
    public int largestRectangleAreaBrute(int[] heights) {
        if (heights.length == 1) {
            return heights[0];
        } else {
            int maxArea = 0;
            for (int left = 0; left < heights.length; left++) {
                int minHeight = Integer.MIN_VALUE;
                for (int right = left; right < heights.length; right++) {
                    minHeight = Math.min(minHeight, heights[right]);
                    maxArea = Math.max(maxArea, minHeight * (right - left + 1));
                }
            }
            return maxArea;
        }
    }

}
