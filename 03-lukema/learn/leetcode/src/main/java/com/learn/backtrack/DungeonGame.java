package com.learn.backtrack;


import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Queue;

import org.junit.jupiter.api.Assertions;

import lombok.extern.log4j.Log4j2;


/**
 * LC - 174 - Dungeon Game
 *
 * Hard
 *
 * The demons had captured the princess and imprisoned her in the bottom-right corner of a dungeon. The dungeon consists of m x n rooms laid
 * out in a 2D grid. Our valiant knight was initially positioned in the top-left room and must fight his way through dungeon to rescue the princess.
 *
 * The knight has an initial health point represented by a positive integer. If at any point his health point drops to 0 or below, he dies immediately.
 *
 * Some of the rooms are guarded by demons (represented by negative integers), so the knight loses health upon entering these rooms; other rooms are
 * either empty (represented as 0) or contain magic orbs that increase the knight's health (represented by positive integers).
 *
 * To reach the princess as quickly as possible, the knight decides to move only rightward or downward in each step.
 *
 * Return the knight's minimum initial health so that he can rescue the princess.
 *
 * Note that any room can contain threats or power-ups, even the first room the knight enters and the bottom-right room where the princess is imprisoned.
 *
 * Example 1:
 * Input: dungeon = [[-2,-3,3],[-5,-10,1],[10,30,-5]]
 * Output: 7
 * Explanation: The initial health of the knight must be at least 7 if he follows the optimal path: RIGHT-> RIGHT -> DOWN -> DOWN.
 *
 * Example 2:
 * Input: dungeon = [[0]]
 * Output: 1
 *
 * Constraints:
 *     m == dungeon.length
 *     n == dungeon[i].length
 *     1 <= m, n <= 200
 *     -1000 <= dungeon[i][j] <= 1000
 */
@Log4j2
public class DungeonGame {

    public static void main(String[] args) {

        /**
         * Output: 7
         */
        /*
        final int[][] dungeon = {
                { -2, -3, 3 },
                { -5, -10, 1 },
                { 10, 30, -5 } };
        */

        /**
         * Output: 1
         */
        final int[][] dungeon = {
                { 1, 2, 1 },
                { -2, -3, -3 },
                { 3, 2, -2 } };

        /**
         * Output: 1
         */
        /*
        final int[][] dungeon = {
                { 0 } };
        */

        /**
         * Output: 1
         */
        /*
        final int[][] dungeon = {
                { 100 } };
        */

        /**
         * Output: 1
         */
        /*
        final int[][] dungeon = {
                { 2, 1 } };
        */

        DungeonGame dungeonGame = new DungeonGame();

        var calculateMinimumHPLukeBrute = dungeonGame.calculateMinimumHPLukeBrute(dungeon);
        log.debug("Dungeon game: {}", () -> calculateMinimumHPLukeBrute);
        log.debug("Dungeon game {} OK", () -> "calculateMinimumHPLukeBrute");

        var calculateMinimumHPLcDp = dungeonGame.calculateMinimumHPLcDp(dungeon);
        Assertions.assertEquals(calculateMinimumHPLukeBrute, calculateMinimumHPLcDp);
        log.debug("Dungeon game {} OK", () -> "calculateMinimumHPLcDp");

        var calculateMinimumHPLukeBackwardBfs = dungeonGame.calculateMinimumHPLukeBackwardBfs(dungeon);
        Assertions.assertEquals(calculateMinimumHPLukeBrute, calculateMinimumHPLukeBackwardBfs);
        log.debug("Dungeon game {} OK", () -> "calculateMinimumHPLukeBackwardBfs");
    }

    /**
     * LC - DP
     */
    int inf = Integer.MAX_VALUE;

    public int getMinHealth(final int[][] dungeon, final int[][] dp, int currCell, int nextRow, int nextCol) {
        final int ROWS = dungeon.length;
        final int COLS = dungeon[0].length;

        if (nextRow >= ROWS || nextCol >= COLS) {
            return inf;
        }
        int nextCell = dp[nextRow][nextCol];
        // hero needs at least 1 point to survive
        return Math.max(1, nextCell - currCell);
    }

    public int calculateMinimumHPLcDp(final int[][] dungeon) {
        final int ROWS = dungeon.length;
        final int COLS = dungeon[0].length;

        final int[][] dp = new int[ROWS][COLS];

        for (int[] arr : dp) {
            Arrays.fill(arr, this.inf);
        }

        int currCell, rightHealth, downHealth, nextHealth, minHealth;

        for (int row = ROWS - 1; row >= 0; --row) {
            for (int col = COLS - 1; col >= 0; --col) {
                currCell = dungeon[row][col];

                rightHealth = getMinHealth(dungeon, dp, currCell, row, col + 1);
                downHealth = getMinHealth(dungeon, dp, currCell, row + 1, col);
                nextHealth = Math.min(rightHealth, downHealth);

                if (nextHealth != inf) {
                    minHealth = nextHealth;
                } else {
                    minHealth = currCell >= 0 ? 1 : 1 - currCell;
                }
                dp[row][col] = minHealth;
            }
        }
        return dp[0][0];
    }

    /**
     * Luke - Backward BFS PriorityQueue
     *
     */
    public int calculateMinimumHPLukeBackwardBfs(final int[][] dungeon) {

        Queue<Cell> queue = new PriorityQueue<>((a, b) -> a.minRequired - b.minRequired);

        int minRequired = Math.max(1, (1 - dungeon[dungeon.length - 1][dungeon[0].length - 1]));
        queue.add(new Cell(dungeon.length - 1, dungeon[0].length - 1, minRequired));

        while (!queue.isEmpty()) {
            Cell curr = queue.poll();

            if (curr.col - 1 >= 0) {
                minRequired = Math.max(1, Math.max((1 - dungeon[curr.row][curr.col - 1]), (curr.minRequired - dungeon[curr.row][curr.col - 1])));
                Cell left = new Cell(curr.row, curr.col - 1, minRequired);
                queue.add(left);
            }

            if (curr.row - 1 >= 0) {
                minRequired = Math.max(1, Math.max((1 - dungeon[curr.row - 1][curr.col]), (curr.minRequired - dungeon[curr.row - 1][curr.col])));
                Cell above = new Cell(curr.row - 1, curr.col, minRequired);
                queue.add(above);
            }

            log.debug("curr: {}, queue: {}", curr, queue);

            if (curr.row == 0 && curr.col == 0) {
                return curr.minRequired;
            }

        }

        return -1;
    }

    /**
     *  Cell
     */
    record Cell(int row, int col, int minRequired) {
    }

    /**
     * Luke - Brute backtrack
     *
     * Time Limit Exceeded
     *
     * Time: O(2 ^ M * 2 ^ N)
     * Space: O(1)
     */
    public int calculateMinimumHPLukeBrute(final int[][] dungeon) {
        backtrackLukeBrute(dungeon, 0, 0, 0, Integer.MAX_VALUE);
        return pointSum;
    }

    int pointSum = Integer.MAX_VALUE;

    private void backtrackLukeBrute(final int[][] dungeon, final int row, final int col, int points, int pathMinPoints) {
        final int ROWS = dungeon.length;
        final int COLS = dungeon[0].length;

        if (row >= ROWS || col >= COLS) {
            return;
        }

        points += dungeon[row][col];
        pathMinPoints = Math.min(pathMinPoints, points);

        // log.debug("row: {}, col: {}, points: {}", row, col, points);

        if (row == ROWS - 1 && col == COLS - 1) {
            // log.debug("---------- row: {}, col: {}, points: {}", row, col, points);

            if (pathMinPoints > 0) {
                pathMinPoints = 0;
            }

            pointSum = Math.min(1 - pathMinPoints, pointSum);
            return;
        } else if (row < ROWS && col < COLS) {
            backtrackLukeBrute(dungeon, row + 1, col, points, pathMinPoints);
            backtrackLukeBrute(dungeon, row, col + 1, points, pathMinPoints);
        }
    }
}
