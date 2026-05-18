package com.learn.backtrack;


import lombok.extern.log4j.Log4j2;


/**
 * LC - 37 - Sudoku Solver
 *
 * Hard
 *
 * Write a program to solve a Sudoku puzzle by filling the empty cells.
 *
 * A sudoku solution must satisfy all of the following rules:
 *     Each of the digits 1-9 must occur exactly once in each row.
 *     Each of the digits 1-9 must occur exactly once in each column.
 *     Each of the digits 1-9 must occur exactly once in each of the 9 3x3 sub-boxes of the grid.
 *
 * The '.' character indicates empty cells.
 *
 * Example 1:
 * Input: board = [
 * ["5","3",".",".","7",".",".",".","."],
 * ["6",".",".","1","9","5",".",".","."],
 * [".","9","8",".",".",".",".","6","."],
 * ["8",".",".",".","6",".",".",".","3"],
 * ["4",".",".","8",".","3",".",".","1"],
 * ["7",".",".",".","2",".",".",".","6"],
 * [".","6",".",".",".",".","2","8","."],
 * [".",".",".","4","1","9",".",".","5"],
 * [".",".",".",".","8",".",".","7","9"]]
 * Output: [
 * ["5","3","4","6","7","8","9","1","2"],
 * ["6","7","2","1","9","5","3","4","8"],
 * ["1","9","8","3","4","2","5","6","7"],
 * ["8","5","9","7","6","1","4","2","3"],
 * ["4","2","6","8","5","3","7","9","1"],
 * ["7","1","3","9","2","4","8","5","6"],
 * ["9","6","1","5","3","7","2","8","4"],
 * ["2","8","7","4","1","9","6","3","5"],
 * ["3","4","5","2","8","6","1","7","9"]]
 * Explanation: The input board is shown above and the only valid solution is shown below:
 *
 * Constraints:
 *     board.length == 9
 *     board[i].length == 9
 *     board[i][j] is a digit or '.'.
 *     It is guaranteed that the input board has only one solution.
 */
@Log4j2
public class SudokuSolver {

    public static void main(String[] args) {

        final char[][] board = {
                { '5', '3', '.', '.', '7', '.', '.', '.', '.' },
                { '6', '.', '.', '1', '9', '5', '.', '.', '.' },
                { '.', '9', '8', '.', '.', '.', '.', '6', '.' },
                { '8', '.', '.', '.', '6', '.', '.', '.', '3' },
                { '4', '.', '.', '8', '.', '3', '.', '.', '1' },
                { '7', '.', '.', '.', '2', '.', '.', '.', '6' },
                { '.', '6', '.', '.', '.', '.', '2', '8', '.' },
                { '.', '.', '.', '4', '1', '9', '.', '.', '5' },
                { '.', '.', '.', '.', '8', '.', '.', '7', '9' } };

        SudokuSolver sudokuSolver = new SudokuSolver();

        sudokuSolver.print(board);

        // boolean isCharValid = sudokuSolver.isCharValid('3', board, 3, 7, 9, 9);
        // log.debug("isCharValid: {}", isCharValid);

        sudokuSolver.solveSudokuLuke(board);
        // sudokuSolver.solveSudokuLc(board);

        sudokuSolver.print(board);

    }

    /**
     * LC
     * @param board
     */
    public void solveSudokuLc(char[][] board) {
        fillSudokoLc(board);
    }

    public static boolean fillSudokoLc(char board[][]) {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (board[row][col] == '.') {
                    for (char ch = '1'; ch <= '9'; ch++) {

                        if (isCharValidLc(board, row, col, ch)) {
                            board[row][col] = ch;
                            if (fillSudokoLc(board)) {
                                return true;
                            }
                        }

                        board[row][col] = '.';
                    }
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean isCharValidLc(char board[][], int row, int col, char ch) {
        for (int i = 0; i < 9; i++) {
            if (board[row][i] == ch) {
                return false;
            }
            if (board[i][col] == ch) {
                return false;
            }
        }

        int rr = row / 3;
        int cc = col / 3;

        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                if (board[rr * 3 + r][cc * 3 + c] == ch) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Luke - backtrack with no row/col
     *
     * @param board
     */

    public void solveSudokuLuke(final char[][] board) {

        final int ROWS = board.length;
        final int COLS = board[0].length;

        // backtrackLuke(board, 0, 0, ROWS, COLS);
        backtrackLuke2(board, ROWS, COLS);

    }

    /**
     * Luke - backtrack with no row/col
     *
     * Runtime: 15 ms Beats 61.94%
     * Memory: 41.5 MB Beats 56.50%
     *
     * Time: O(N ^ 2) * O(3 N)
     * Space: O(N), recursion depth
     *
     * @param board
     * @param ROWS
     * @param COLS
     * @return
     */
    boolean backtrackLuke2(final char[][] board, final int ROWS, final int COLS) {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                if (board[row][col] == '.') {
                    for (char ch = '1'; ch <= '9'; ch++) {
                        if (isCharValid(ch, board, row, col, ROWS, COLS)) {
                            board[row][col] = ch;

                            boolean isNextCellValid = backtrackLuke2(board, ROWS, COLS);
                            if (isNextCellValid) {
                                return true;
                            } else {
                                board[row][col] = '.';
                            }
                        }
                    }

                    /**
                     * Not fillable
                     */
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Luke - Backtrack with row/col
     *
     * Runtime: 14 ms Beats 64.48%
     * Memory: 41.6 MB Beats 56.50%
     *
     * Time: O(n ^ 2) * O(3 N) = O(N ^ 3)
     * Space: O(N), recursion stack depth
     *
     * @param board
     * @param row
     * @param col
     * @param ROWS
     * @param COLS
     * @return
     */
    boolean backtrackLuke(final char[][] board, int row, int col, final int ROWS, final int COLS) {

        if (row == ROWS) {
            return true;
        }

        /**
         * find next '.' and start to fill
         */
        while (true) {
            if (board[row][col] == '.') {

                /**
                 * Pick a char to fill
                 */
                for (char ch = '1'; ch <= '9'; ch++) {

                    boolean isCharValid = isCharValid(ch, board, row, col, ROWS, COLS);

                    if (isCharValid) {

                        board[row][col] = ch;

                        // print(board);

                        int nextRow = 0;
                        int nextCol = 0;

                        if (col + 1 == COLS) {
                            nextRow = row + 1;
                            nextCol = 0;
                        } else {
                            nextRow = row;
                            nextCol = col + 1;
                        }

                        boolean isNextCellValid = backtrackLuke(board, nextRow, nextCol, ROWS, COLS);

                        if (!isNextCellValid) {
                            board[row][col] = '.';
                        } else {
                            return true;
                        }
                    }
                }

                /**
                 * If non of the chars is valid
                 */
                return false;
            } else {
                /**
                 * skip cells with numbers
                 */
                col++;
                if (col == COLS) {
                    col = 0;
                    row++;

                    if (row == ROWS) {
                        return true;
                    }
                }
            }
        }
    }

    /**
     * Time: O(3 N)
     * Space: O(1)
     *
     * @param ch
     * @param board
     * @param row
     * @param col
     * @param ROWS
     * @param COLS
     * @return true if ch is valid
     */
    boolean isCharValid(final char ch, final char[][] board, final int row, final int col, final int ROWS, final int COLS) {
        if (col < 0 || col >= COLS || row < 0 || row >= ROWS) {
            return false;
        }

        // row
        for (int r = 0; r < ROWS; r++) {
            if (board[r][col] == ch) {
                return false;
            }
        }

        // col
        for (int c = 0; c < COLS; c++) {
            if (board[row][c] == ch) {
                return false;
            }
        }

        // small block
        int rr = row / 3;
        int cc = col / 3;

        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                if (board[rr * 3 + r][cc * 3 + c] == ch) {
                    return false;
                }
            }
        }

        return true;
    }

    void print(final char[][] board) {
        StringBuilder sb = new StringBuilder();
        for (int r = 0, rows = board.length; r < rows; r++) {
            for (int c = 0, cols = board[0].length; c < cols; c++) {
                sb.append(board[r][c]).append("  ");
            }
            sb.append(System.lineSeparator());
        }

        log.debug(sb.toString());
    }
}
