package com.learn.graph;


import com.learn.dp.DpUtils;

import lombok.extern.log4j.Log4j2;


/**
 * LC - 389 - Game Of Life
 *
 * Medium
 *
 * According to Wikipedia's article: "The Game of Life, also known simply as Life, is a cellular automaton devised
 * by the British mathematician John Horton Conway in 1970."
 *
 * The board is made up of an m x n grid of cells, where each cell has an initial state: live (represented by a 1)
 * or dead (represented by a 0). Each cell interacts with its eight neighbors (horizontal, vertical, diagonal) using
 * the following four rules (taken from the above Wikipedia article):
 *
 *     Any live cell with fewer than two live neighbors dies as if caused by under-population.
 *     Any live cell with two or three live neighbors lives on to the next generation.
 *     Any live cell with more than three live neighbors dies, as if by over-population.
 *     Any dead cell with exactly three live neighbors becomes a live cell, as if by reproduction.
 *
 * The next state is created by applying the above rules simultaneously to every cell in the current state, where
 * births and deaths occur simultaneously. Given the current state of the m x n grid board, return the next state.
 *
 * Example 1:
 * Input: board = [[0,1,0],[0,0,1],[1,1,1],[0,0,0]]
 * Output: [[0,0,0],[1,0,1],[0,1,1],[0,1,0]]
 *
 * Example 2:
 * Input: board = [[1,1],[1,0]]
 * Output: [[1,1],[1,1]]
 *
 * Constraints:
 *     m == board.length
 *     n == board[i].length
 *     1 <= m, n <= 25
 *     board[i][j] is 0 or 1.
 *
 * Follow up:
 *     1. Could you solve it in-place? Remember that the board needs to be updated simultaneously: You cannot update
 *        some cells first and then use their updated values to update other cells.
 *
 *     2. In this question, we represent the board using a 2D array. In principle, the board is infinite, which would
 *        cause problems when the active area encroaches upon the border of the array (i.e., live cells reach the border).
 *        How would you address these problems?
 */
@Log4j2
public class GameOfLife {

    public static void main(String[] args) {

        final int[][] board = {
                { 0, 1, 0 },
                { 0, 0, 1 },
                { 1, 1, 1 },
                { 0, 0, 0 } };

        GameOfLife gameOfLife = new GameOfLife();
        gameOfLife.gameOfLife(board);
        log.debug("Game of Life:");
        DpUtils.print(board);

    }

    /**
     * Luke - Use tmp 2D array
     *
     * Runtime: 1 ms Beats 73.58%
     * Memory: 42.4 MB Beats 35.95%
     *
     * Time: O(ROWS * COLS)
     * Space: O(ROWS * COLS)
     */
    public void gameOfLife(int[][] board) {
        final int ROWS = board.length;
        final int COLS = board[0].length;

        final boolean[][] nums = new boolean[ROWS][COLS];

        /**
         * walk through the board
         */
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                nums[r][c] = shouldHaveLife(board, r, c);
            }
        }

        /**
         * Update board status
         */
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                board[r][c] = nums[r][c] ? 1 : 0;
            }
        }

    }

    private boolean shouldHaveLife(final int[][] board, final int row, final int col) {
        int nbrNeighbors = 0;
        if (hasNeighbor(board, row, col + 1)) {
            nbrNeighbors++;
        }
        if (hasNeighbor(board, row + 1, col + 1)) {
            nbrNeighbors++;
        }
        if (hasNeighbor(board, row + 1, col)) {
            nbrNeighbors++;
        }
        if (hasNeighbor(board, row + 1, col - 1)) {
            nbrNeighbors++;
        }
        if (hasNeighbor(board, row, col - 1)) {
            nbrNeighbors++;
        }
        if (hasNeighbor(board, row - 1, col - 1)) {
            nbrNeighbors++;
        }
        if (hasNeighbor(board, row - 1, col)) {
            nbrNeighbors++;
        }
        if (hasNeighbor(board, row - 1, col + 1)) {
            nbrNeighbors++;
        }

        if (nbrNeighbors == 2 || nbrNeighbors == 3) {
            if (board[row][col] == 1) {
                return true;
            }
        }

        if (nbrNeighbors == 3 && board[row][col] != 1) {
            return true;
        }

        return false;
    }

    private boolean hasNeighbor(final int[][] board, final int row, final int col) {
        if (row < 0 || row >= board.length || col < 0 || col >= board[0].length) {
            return false;
        }

        return board[row][col] == 1;
    }
}
