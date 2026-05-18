package com.learn.dp;


import org.junit.jupiter.api.Assertions;

import lombok.extern.log4j.Log4j2;


/**
 * LC - 130 - Surround Regions
 * 
 * Medium
 * 
 * Given an m x n matrix board containing 'X' and 'O', capture all regions that are 4-directionally surrounded by 'X'.
 * A region is captured by flipping all 'O's into 'X's in that surrounded region.
 * 
 * Example 1:
 * Input: board = [
 * ["X","X","X","X"],
 * ["X","O","O","X"],
 * ["X","X","O","X"],
 * ["X","O","X","X"]]
 * Output: [
 * ["X","X","X","X"],
 * ["X","X","X","X"],
 * ["X","X","X","X"],
 * ["X","O","X","X"]]
 * Explanation: Notice that an 'O' should not be flipped if:
 * - It is on the border, or
 * - It is adjacent to an 'O' that should not be flipped.
 * The bottom 'O' is on the border, so it is not flipped.
 * The other three 'O' form a surrounded region, so they are flipped.
 * 
 * Example 2:
 * Input: board = [["X"]]
 * Output: [["X"]]
 * 
 * Constraints:
 *     m == board.length
 *     n == board[i].length
 *     1 <= m, n <= 200
 *     board[i][j] is 'X' or 'O'.
 * 
 */
@Log4j2
public class SurroundedRegions {

    public static void main(String[] args) {

        SurroundedRegions surroundedRegions = new SurroundedRegions();

        /**
         * data set 0
         */
        final char[][] board0 = {
                { 'X', 'X', 'X', 'X' },
                { 'X', 'O', 'O', 'X' },
                { 'X', 'X', 'O', 'X' },
                { 'X', 'O', 'X', 'X' }
        };

        surroundedRegions.solve(board0);

        /**
         * expected for data set 0
         */
        final char[][] expected0 = {
                { 'X', 'X', 'X', 'X' },
                { 'X', 'X', 'X', 'X' },
                { 'X', 'X', 'X', 'X' },
                { 'X', 'O', 'X', 'X' }
        };

        print(board0);
        print(expected0);
        Assertions.assertEquals(toString(expected0), toString(board0));

        log.debug(() -> "-------- data set 0 OK --------");

        /**
         * data set 1
         */
        final char[][] board1 = {
                { 'O', 'X', 'X', 'O', 'X' },
                { 'X', 'O', 'O', 'X', 'O' },
                { 'X', 'O', 'X', 'O', 'X' },
                { 'O', 'X', 'O', 'O', 'O' },
                { 'X', 'X', 'O', 'X', 'O' }
        };

        surroundedRegions.solve(board1);

        /**
         * expected for data set 1
         */
        final char[][] expected1 = {
                { 'O', 'X', 'X', 'O', 'X' },
                { 'X', 'X', 'X', 'X', 'O' },
                { 'X', 'X', 'X', 'O', 'X' },
                { 'O', 'X', 'O', 'O', 'O' },
                { 'X', 'X', 'O', 'X', 'O' }
        };

        print(board1);
        print(expected1);
        Assertions.assertEquals(toString(expected1), toString(board1));

        log.debug(() -> "-------- data set 1 OK --------");

        /**
         * data set 2
         */
        final char[][] board2 = {
                { 'X', 'X', 'X', 'X', 'X' },
                { 'X', 'O', 'O', 'O', 'X' },
                { 'X', 'X', 'O', 'O', 'X' },
                { 'X', 'X', 'X', 'O', 'X' },
                { 'X', 'O', 'X', 'X', 'X' }
        };

        surroundedRegions.solve(board2);

        /**
         * expected for data set 2
         */
        final char[][] expected2 = {
                { 'X', 'X', 'X', 'X', 'X' },
                { 'X', 'X', 'X', 'X', 'X' },
                { 'X', 'X', 'X', 'X', 'X' },
                { 'X', 'X', 'X', 'X', 'X' },
                { 'X', 'O', 'X', 'X', 'X' }
        };

        print(board2);
        print(expected2);
        Assertions.assertEquals(toString(expected2), toString(board2));

        log.debug(() -> "-------- data set 2 OK --------");

        /**
        * data set 3
        */
        final char[][] board3 = {
                { 'O', 'O', 'O', 'O', 'X', 'X' },
                { 'O', 'O', 'O', 'O', 'O', 'O' },
                { 'O', 'X', 'O', 'X', 'O', 'O' },
                { 'O', 'X', 'O', 'O', 'X', 'O' },
                { 'O', 'X', 'O', 'X', 'O', 'O' },
                { 'O', 'X', 'O', 'O', 'O', 'O' }
        };

        surroundedRegions.solve(board3);

        /**
         * expected for data set 3
         */
        final char[][] expected3 = {
                { 'O', 'O', 'O', 'O', 'X', 'X' },
                { 'O', 'O', 'O', 'O', 'O', 'O' },
                { 'O', 'X', 'O', 'X', 'O', 'O' },
                { 'O', 'X', 'O', 'O', 'X', 'O' },
                { 'O', 'X', 'O', 'X', 'O', 'O' },
                { 'O', 'X', 'O', 'O', 'O', 'O' }
        };

        print(board3);
        print(expected3);
        Assertions.assertEquals(toString(expected3), toString(board3));

        log.debug(() -> "-------- data set 3 OK --------");

        /**
         * data set 4
         */
        final char[][] board4 = {
                { 'X', 'O', 'X', 'O', 'X', 'O', 'O', 'O', 'X', 'O' },
                { 'X', 'O', 'O', 'X', 'X', 'X', 'O', 'O', 'O', 'X' },
                { 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'X', 'X' },
                { 'O', 'O', 'O', 'O', 'O', 'O', 'X', 'O', 'O', 'X' },
                { 'O', 'O', 'X', 'X', 'O', 'X', 'X', 'O', 'O', 'O' },
                { 'X', 'O', 'O', 'X', 'X', 'X', 'O', 'X', 'X', 'O' },
                { 'X', 'O', 'X', 'O', 'O', 'X', 'X', 'O', 'X', 'O' },
                { 'X', 'X', 'O', 'X', 'X', 'O', 'X', 'O', 'O', 'X' },
                { 'O', 'O', 'O', 'O', 'X', 'O', 'X', 'O', 'X', 'O' },
                { 'X', 'X', 'O', 'X', 'X', 'X', 'X', 'O', 'O', 'O' }
        };

        surroundedRegions.solve(board4);

        /**
         * expected for data set 4
         */
        final char[][] expected4 = {
                { 'X', 'O', 'X', 'O', 'X', 'O', 'O', 'O', 'X', 'O' },
                { 'X', 'O', 'O', 'X', 'X', 'X', 'O', 'O', 'O', 'X' },
                { 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'X', 'X' },
                { 'O', 'O', 'O', 'O', 'O', 'O', 'X', 'O', 'O', 'X' },
                { 'O', 'O', 'X', 'X', 'O', 'X', 'X', 'O', 'O', 'O' },
                { 'X', 'O', 'O', 'X', 'X', 'X', 'X', 'X', 'X', 'O' },
                { 'X', 'O', 'X', 'X', 'X', 'X', 'X', 'O', 'X', 'O' },
                { 'X', 'X', 'O', 'X', 'X', 'X', 'X', 'O', 'O', 'X' },
                { 'O', 'O', 'O', 'O', 'X', 'X', 'X', 'O', 'X', 'O' },
                { 'X', 'X', 'O', 'X', 'X', 'X', 'X', 'O', 'O', 'O' }
        };

        print(board4);
        print(expected4);
        Assertions.assertEquals(toString(expected4), toString(board4));

        log.debug(() -> "-------- data set 4 OK --------");
    }

    /**
     * Luke - DP - In-Place Recursion
     */
    public void solve(final char[][] board) {
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[0].length; col++) {
                backtrack(board, row, col);
            }
        }
    }

    private boolean backtrack(final char[][] board, int row, int col) {
        if (row < 0 || col < 0 || row >= board.length || col >= board[0].length) {
            return false;
        }

        if (board[row][col] == 'O') {
            if (row == 0 || col == 0 || row == board.length - 1 || col == board[0].length - 1) {
                return false;
            } else {
                /**
                 * Top
                 */
                if (board[row - 1][col] == 'O') {
                    return false;
                }

                /**
                 * Left
                 */
                while (col >= 1 && board[row][col - 1] == 'O') {
                    col = col - 1;
                }
                if (col == 0) {
                    return false;
                }

                if (board[row - 1][col] == 'O') {
                    return false;
                }

                /*
                if (board[row + 1][col] == 'O') {
                    return false;
                }
                */

                board[row][col] = 'X';

                boolean right = backtrack(board, row, col + 1);
                boolean bottom = backtrack(board, row + 1, col);

                if (right && bottom) {
                    return true;
                } else {
                    board[row][col] = 'O';
                    return false;
                }
            }
        } else {
            return true;
        }
    }

    public static void print(char[][] board) {
        log.debug("board: \n{}", toString(board));
    }

    public static String toString(char[][] board) {
        if (board == null) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[0].length; col++) {
                sb.append(board[row][col]).append("  ");
            }
            if (row < board.length - 1) {
                sb.append(System.lineSeparator());
            }
        }

        return sb.toString();
    }
}
