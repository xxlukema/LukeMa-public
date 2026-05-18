package com.learn.dp;


import java.util.ArrayList;
import java.util.List;

import lombok.extern.log4j.Log4j2;


/**
 * LC - 296 - Best Meeting Point
 *
 * Hard
 *
 * Given an m x n binary grid where each 1 marks the home of one friend, return the minimal total travel distance.
 *
 * The total travel distance is the sum of the distances between the houses of the friends and the meeting point.
 * The distance is calculated using Manhattan Distance, where distance(p1, p2) = |p2.x - p1.x| + |p2.y - p1.y|.
 *
 * Example 1:
 * Input: grid = [[1,0,0,0,1],[0,0,0,0,0],[0,0,1,0,0]]
 * Output: 6
 * Explanation: Given three friends living at (0,0), (0,4), and (2,2).
 * The point (0,2) is an ideal meeting point, as the total travel distance of 2 + 2 + 2 = 6 is minimal.
 * So return 6.
 *
 * Example 2:
 * Input: grid = [[1,1]]
 * Output: 1
 *
 * Constraints:
 *     m == grid.length
 *     n == grid[i].length
 *     1 <= m, n <= 200
 *     grid[i][j] is either 0 or 1.
 *     There will be at least two friends in the grid.
 */
@Log4j2
public class BestMeetingPoint {

    public static void main(String[] args) {

        /**
         * Expected: 6
         */
        /*
        final int[][] grid = {
                { 1, 0, 0, 0, 1 },
                { 0, 0, 0, 0, 0 },
                { 0, 0, 1, 0, 0 } };
        */

        /**
         * Expected: 163
         */
        final int[][] grid = {
                { 1, 1, 0, 0, 0, 1 },
                { 0, 0, 1, 0, 0, 0 },
                { 0, 1, 1, 0, 0, 0 },
                { 0, 0, 1, 0, 1, 0 },
                { 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 1 },
                { 0, 1, 0, 0, 0, 0 },
                { 0, 1, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 1, 0 },
                { 0, 0, 0, 0, 0, 0 },
                { 1, 1, 1, 1, 1, 1 },
                { 0, 1, 0, 0, 1, 1 },
                { 0, 0, 0, 1, 1, 0 },
                { 0, 1, 0, 0, 0, 0 },
                { 1, 1, 1, 1, 0, 1 },
                { 0, 0, 0, 0, 0, 0 } };

        BestMeetingPoint bestMeetingPoint = new BestMeetingPoint();
        var ret = bestMeetingPoint.minTotalDistanceBrute(grid);
        log.debug("Best Meeting Point: {}", () -> ret);
        log.debug("Best Meeting Point {} OK", () -> "ret");

    }

    /**
     * 2025-11-24
     * 
     * 1. List of friends locations
     * 2. Find center of friends
     * 3. Check area around center to find min distance
     * O(ROWS * COLS)
     * 
     * Or
     * Build a function to calculate distance from any point to a friend
     * Iterate through all points in grid and calculate total distance to all friends
     * O(ROWS * COLS)
     * 
     */

    /**
     * Luke - Brute
     *
     * Runtime: 183 ms Beats 5.2%
     * Memory: 43.6 MB Beats 96.55%
     *
     * DP: Time Limit Exceeded
     *
     * Time: O(ROWS * COLS) * O(friends.length)
     * Space: O(ROWS * COLS)
     */
    public int minTotalDistanceBrute(final int[][] grid) {
        final int ROWS = grid.length;
        final int COLS = grid[0].length;

        // final int[][] dp = new int[ROWS][COLS];

        record Cell(int row, int col) {
        }

        /**
         * 1. Locate all friends
         *
         * Time: O(R * C)
         * Space: O(R * C)
         */
        List<Cell> friends = new ArrayList<>();
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                if (grid[r][c] == 1) {
                    friends.add(new Cell(r, c));
                }
            }
        }

        /**
         * 2. Center of friends
         */
        int rowCenter = 0;
        int colCenter = 0;

        int size = friends.size();

        for (Cell friend : friends) {
            rowCenter += friend.row;
            colCenter += friend.col;
        }

        rowCenter /= size;
        colCenter /= size;

        int min = Integer.MAX_VALUE;

        /**
         * Build dp - DP: Time Limit Exceeded
         */
        /*
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                // Time: O(friends.length)
                for (Cell cell : friends) {
                    dp[r][c] += distance(r, c, cell.row, cell.col);
                    if (dp[r][c] >= min) {
                        break;
                    }
                }
                min = Math.min(min, dp[r][c]);
            }
        }
        */

        size = Math.min(size, 10);

        for (int r = rowCenter - size; r < rowCenter + size; r++) {
            for (int c = colCenter - size; c < colCenter + size; c++) {
                if (c >= 0 && r >= 0 && c < COLS && r < ROWS) {
                    int dist = 0;
                    for (Cell friend : friends) {
                        dist += distance(r, c, friend.row, friend.col);
                    }

                    min = Math.min(min, dist);
                }
            }
        }

        return min;
    }

    /**
     * TODO: Use Cell as params to reduce num of params
     *
     * Time: O(1)
     * Space: O(1)
     */
    private int distance(final int r1, final int c1, final int r2, final int c2) {
        return Math.abs(c2 - c1) + Math.abs(r2 - r1);
    }

    /**
     * Luke - BFS
     */
    public int minTotalDistanceBfs(final int[][] grid) {
        final int ROWS = grid.length;
        final int COLS = grid[0].length;

        record Cell(int row, int col) {
        }

        /**
         * 1. Locate all friends
         *
         * Time: O(R * C)
         * Space: O(R * C)
         */
        List<Cell> friends = new ArrayList<>();
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                if (grid[r][c] == 1) {
                    friends.add(new Cell(r, c));
                }
            }
        }

        //

        return 0;
    }
}
