package com.learn.graph;


import java.util.LinkedList;
import java.util.Queue;

import com.learn.dp.DpUtils;

import lombok.extern.log4j.Log4j2;


/**
 * LC - 286 - Walls And gates
 *
 * Medium
 *
 * You are given an m x n grid rooms initialized with these three possible values.
 *
 *     -1 A wall or an obstacle.
 *     0 A gate.
 *     INF Infinity means an empty room. We use the value 2 ^ 31 - 1 = 2,147,483,647 to represent INF as you may assume that the distance to a gate is less than 2147483647.
 *
 * Fill each empty room with the distance to its nearest gate. If it is impossible to reach a gate, it should be filled with INF.
 *
 * Example 1:
 * Input: rooms = [[2147483647,-1,0,2147483647],[2147483647,2147483647,2147483647,-1],[2147483647,-1,2147483647,-1],[0,-1,2147483647,2147483647]]
 * Output: [[3,-1,0,1],[2,2,1,-1],[1,-1,2,-1],[0,-1,3,4]]
 *
 * Example 2:
 * Input: rooms = [[-1]]
 * Output: [[-1]]
 *
 * Constraints:
 *     m == rooms.length
 *     n == rooms[i].length
 *     1 <= m, n <= 250
 *     rooms[i][j] is -1, 0, or 2 ^ 31 - 1.
 */
@Log4j2
public class WallsAndGates {

    public static void main(String[] args) {

        final int[][] rooms = {
                { 2147483647, -1, 0, 2147483647 },
                { 2147483647, 2147483647, 2147483647, -1 },
                { 2147483647, -1, 2147483647, -1 },
                { 0, -1, 2147483647, 2147483647 } };

        @SuppressWarnings("unused")
        final int[][] rooms2 = {
                { 0, 0 },
                { 0, 0 } };

        WallsAndGates wallsAndGates = new WallsAndGates();
        // wallsAndGates.wallsAndGates(rooms);
        wallsAndGates.wallsAndGatesLcDirection(rooms);
        DpUtils.print(rooms);
        log.debug(() -> "Walls and Gates OK");
    }

    /**
     * Luke - BFS to avoid overalpping (step on one another)
     *
     * Input:
     * Expected:
     *
     * Runtime: 36 ms Beats 17.91%
     * Memory: 56.9 MB Beats 54.94%
     *
     * Time: O(ROW * COLS)
     * Space: O(ROWS * COLS)
     */
    public void wallsAndGates(int[][] rooms) {
        /**
         * Edge conditions
         */
        if (rooms == null || rooms.length == 0) {
            return;
        }

        /**
         * BFS
         */
        // 1. Find all gates
        final int ROWS = rooms.length;
        final int COLS = rooms[0].length;

        final Queue<Cell> queue = new LinkedList<>();

        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                if (rooms[r][c] == 0) {
                    queue.offer(new Cell(r, c));
                }
            }
        }

        final Queue<Cell> recoverQueue = new LinkedList<>();
        recoverQueue.addAll(queue);

        // 2. BFS from all gates
        int level = -1;
        while (!queue.isEmpty()) {
            level++;
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                Cell cell = queue.poll();
                travel(rooms, cell, queue, ROWS, COLS, level);
            }
        }

        while (!recoverQueue.isEmpty()) {
            Cell cell = recoverQueue.poll();
            rooms[cell.row][cell.col] = 0;
        }
    }

    /**
     * Traversal
     *
     * Time: O(4)
     * Space: O(4)
     */
    private void travel(
            final int[][] rooms,
            final Cell cell,
            final Queue<Cell> queue,
            final int ROWS,
            final int COLS,
            final int level) {
        // Edge conditions
        /*
        if (cell.row < 0 || cell.col >= ROWS || cell.col < 0 || cell.col >= COLS) {
            return;
        }
        */

        /**
         * walls,
         * gates,
         * visited
         */
        if (rooms[cell.row][cell.col] < Integer.MAX_VALUE && rooms[cell.row][cell.col] != 0) {
            return;
        }

        if (rooms[cell.row][cell.col] == 0) {
            rooms[cell.row][cell.col] = ROWS + COLS + 1;
        } else {
            rooms[cell.row][cell.col] = level;
        }

        if (cell.row - 1 >= 0 && rooms[cell.row - 1][cell.col] == Integer.MAX_VALUE) {
            queue.offer(new Cell(cell.row - 1, cell.col));
        }
        if (cell.row + 1 < ROWS && rooms[cell.row + 1][cell.col] == Integer.MAX_VALUE) {
            queue.offer(new Cell(cell.row + 1, cell.col));
        }
        if (cell.col - 1 >= 0 && rooms[cell.row][cell.col - 1] == Integer.MAX_VALUE) {
            queue.offer(new Cell(cell.row, cell.col - 1));
        }
        if (cell.col + 1 < COLS && rooms[cell.row][cell.col + 1] == Integer.MAX_VALUE) {
            queue.offer(new Cell(cell.row, cell.col + 1));
        }
    }

    /**
     * LC - Direction
     */
    private static final int[][] DIRECTION = {
            { -1, 0 },
            { 1, 0 },
            { 0, -1 },
            { 0, 1 }
    };

    private static final int GATE = 0;
    private static final int WALL = -1;
    private static final int EMPTY = Integer.MAX_VALUE;

    /**
     * LC - BFS to avoid overalpping (step on one another)
     *
     * Input:
     * Expected:
     *
     * Runtime: 20 ms Beats 51.43%
     * Memory: 56.3 MB Beats 76.46%
     *
     * Time: O(ROW * COLS)
     * Space: O(ROWS * COLS)
     */
    public void wallsAndGatesLcDirection(int[][] rooms) {
        /**
         * Edge conditions
         */
        if (rooms == null || rooms.length == 0) {
            return;
        }

        /**
         * BFS
         */
        // 1. Find all gates
        final int ROWS = rooms.length;
        final int COLS = rooms[0].length;

        final Queue<Cell> queue = new LinkedList<>();

        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                if (rooms[r][c] == 0) {
                    queue.offer(new Cell(r, c));
                }
            }
        }

        final Queue<Cell> recoverQueue = new LinkedList<>();
        recoverQueue.addAll(queue);

        // 2. BFS from all gates
        int level = -1;
        while (!queue.isEmpty()) {
            level++;
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                Cell cell = queue.poll();
                travelLcDirection(rooms, cell, queue, ROWS, COLS, level);
            }
        }

        while (!recoverQueue.isEmpty()) {
            Cell cell = recoverQueue.poll();
            rooms[cell.row][cell.col] = GATE;
        }
    }

    /**
     * Traversal
     *
     * Time: O(4)
     * Space: O(4)
     */
    private void travelLcDirection(
            final int[][] rooms,
            final Cell cell,
            final Queue<Cell> queue,
            final int ROWS,
            final int COLS,
            final int level) {
        // Edge conditions
        /*
        if (cell.row < 0 || cell.col >= ROWS || cell.col < 0 || cell.col >= COLS) {
            return;
        }
        */

        /**
         * walls,
         * gates,
         * visited
         */
        if (rooms[cell.row][cell.col] == WALL) {
            return;
        }

        if (rooms[cell.row][cell.col] == EMPTY || rooms[cell.row][cell.col] == GATE) {
            if (rooms[cell.row][cell.col] == 0) {
                rooms[cell.row][cell.col] = ROWS + COLS + 1;
            } else {
                rooms[cell.row][cell.col] = level;
            }

            for (int i = 0; i < DIRECTION.length; i++) {

                int r = cell.row + DIRECTION[i][0];
                int c = cell.col + DIRECTION[i][1];

                if (r >= 0 && r < ROWS && c >= 0 && c < COLS && rooms[r][c] == EMPTY) {
                    queue.offer(new Cell(r, c));
                }
            }
        }
    }

    record Cell(int row, int col) {
    }
}
