package com.learn.backtrack.redo;


import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.learn.dp.DpUtils;

import lombok.extern.log4j.Log4j2;


/**
 * LC - 130 - Surrounded Regions
 *
 * Medium
 *
 * Given an m x n matrix board containing 'X' and 'O', capture all regions that are 4-directionally surrounded by 'X'.
 *
 * A region is captured by flipping all 'O's into 'X's in that surrounded region.
 *
 * Example 1:
 * Input: board = [["X","X","X","X"],["X","O","O","X"],["X","X","O","X"],["X","O","X","X"]]
 * Output: [["X","X","X","X"],["X","X","X","X"],["X","X","X","X"],["X","O","X","X"]]
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
 */
@Log4j2
public class SurroundedRegions {

    public static void main(String[] args) {

        final char[][] board = {
                { 'X', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O' },
                { 'O', 'X', 'O', 'O', 'O', 'O', 'X', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'X', 'X' },
                { 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'X', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'X' },
                { 'O', 'O', 'X', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'X', 'O' },
                { 'O', 'O', 'O', 'O', 'O', 'X', 'O', 'O', 'O', 'O', 'X', 'O', 'O', 'O', 'O', 'O', 'X', 'O', 'O', 'X' },
                { 'X', 'O', 'O', 'O', 'X', 'O', 'O', 'O', 'O', 'O', 'X', 'O', 'X', 'O', 'X', 'O', 'X', 'O', 'X', 'O' },
                { 'O', 'O', 'O', 'O', 'X', 'O', 'O', 'X', 'O', 'O', 'O', 'O', 'O', 'X', 'O', 'O', 'X', 'O', 'O', 'O' },
                { 'X', 'O', 'O', 'O', 'X', 'X', 'X', 'O', 'X', 'O', 'O', 'O', 'O', 'X', 'X', 'O', 'X', 'O', 'O', 'O' },
                { 'O', 'O', 'O', 'O', 'O', 'X', 'X', 'X', 'X', 'O', 'O', 'O', 'O', 'X', 'O', 'O', 'X', 'O', 'O', 'O' },
                { 'X', 'O', 'O', 'O', 'O', 'X', 'O', 'O', 'O', 'O', 'O', 'O', 'X', 'X', 'O', 'O', 'X', 'O', 'O', 'X' },
                { 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'X', 'O', 'O', 'X', 'O', 'O', 'O', 'X', 'O', 'X' },
                { 'O', 'O', 'O', 'O', 'X', 'O', 'X', 'O', 'O', 'X', 'X', 'O', 'O', 'O', 'O', 'O', 'X', 'O', 'O', 'O' },
                { 'X', 'X', 'O', 'O', 'O', 'O', 'O', 'X', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O' },
                { 'O', 'X', 'O', 'X', 'O', 'O', 'O', 'X', 'O', 'X', 'O', 'O', 'O', 'X', 'O', 'X', 'O', 'X', 'O', 'O' },
                { 'O', 'O', 'X', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'X', 'O', 'O', 'O', 'O', 'O', 'X', 'O', 'X', 'O' },
                { 'X', 'X', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'X', 'O', 'X', 'X', 'O', 'O', 'O', 'X', 'O', 'O' },
                { 'O', 'O', 'X', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'X', 'O', 'O', 'X', 'O', 'X', 'O', 'X', 'O', 'O' },
                { 'O', 'O', 'O', 'X', 'O', 'O', 'O', 'O', 'O', 'X', 'X', 'X', 'O', 'O', 'X', 'O', 'O', 'O', 'X', 'O' },
                { 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O' },
                { 'X', 'O', 'O', 'O', 'O', 'X', 'O', 'O', 'O', 'X', 'X', 'O', 'O', 'X', 'O', 'X', 'O', 'X', 'O', 'O' } };

        /*
        final char[][] board = {
                { 'O', 'O', 'O', 'O', 'X', 'X' },
                { 'O', 'O', 'O', 'O', 'O', 'O' },
                { 'O', 'X', 'O', 'X', 'O', 'O' },
                { 'O', 'X', 'O', 'O', 'X', 'O' },
                { 'O', 'X', 'O', 'X', 'O', 'O' },
                { 'O', 'X', 'O', 'O', 'O', 'O' } };
        */

        /*
        final char[][] board = {
                { 'O', 'O', 'O' },
                { 'O', 'O', 'O' },
                { 'O', 'O', 'O' } };
        */

        @SuppressWarnings("unused")
        final char[][] board0 = {
                { 'X', 'O', 'X', 'X' },
                { 'O', 'X', 'O', 'X' },
                { 'X', 'O', 'X', 'O' },
                { 'O', 'X', 'O', 'X' } };

        @SuppressWarnings("unused")
        final char[][] board1 = {
                { 'X', 'X', 'X', 'X' },
                { 'X', 'O', 'O', 'X' },
                { 'X', 'X', 'O', 'X' },
                { 'X', 'O', 'X', 'X' } };

        @SuppressWarnings("unused")
        final char[][] board2 = {
                { 'O', 'X', 'X', 'O', 'X' },
                { 'X', 'O', 'O', 'X', 'O' },
                { 'X', 'O', 'X', 'O', 'X' },
                { 'O', 'X', 'O', 'O', 'O' },
                { 'X', 'X', 'O', 'X', 'O' } };

        @SuppressWarnings("unused")
        final char[][] expected = {
                { 'O', 'X', 'X', 'O', 'X' },
                { 'X', 'X', 'X', 'X', 'O' },
                { 'X', 'X', 'X', 'O', 'X' },
                { 'O', 'X', 'O', 'O', 'O' },
                { 'X', 'X', 'O', 'X', 'O' } };

        SurroundedRegions surroundedRegions = new SurroundedRegions();

        DpUtils.print(board);

        // surroundedRegions.solveLcDfs(board);
        surroundedRegions.solveLukeBfs(board);
        // surroundedRegions.solveLcBfs(board);

        DpUtils.print(board);

        log.debug("Surrounded Regions: {}", () -> board);
        log.debug("Surrounded Regions {} OK", () -> "solveLcBfs");

    }

    protected Integer ROWS = 0;
    protected Integer COLS = 0;

    public void solveLcBfs(char[][] board) {
        if (board == null || board.length == 0) {
            return;
        }

        this.ROWS = board.length;
        this.COLS = board[0].length;

        List<Cell> borders = new LinkedList<>();
        // Step 1). construct the list of border cells
        for (int r = 0; r < this.ROWS; ++r) {
            borders.add(new Cell(r, 0));
            borders.add(new Cell(r, this.COLS - 1));
        }
        for (int c = 0; c < this.COLS; ++c) {
            borders.add(new Cell(0, c));
            borders.add(new Cell(this.ROWS - 1, c));
        }

        // Step 2). mark the escaped cells
        for (Cell cell : borders) {
            this.BFS(board, cell.row, cell.col);
        }

        // Step 3). flip the cells to their correct final states
        for (int r = 0; r < this.ROWS; ++r) {
            for (int c = 0; c < this.COLS; ++c) {
                if (board[r][c] == 'O')
                    board[r][c] = 'X';
                if (board[r][c] == 'E')
                    board[r][c] = 'O';
            }
        }
    }

    protected void BFS(char[][] board, int r, int c) {
        LinkedList<Cell> queue = new LinkedList<>();
        queue.offer(new Cell(r, c));

        while (!queue.isEmpty()) {
            Cell cell = queue.pollFirst();
            int row = cell.row, col = cell.col;
            if (board[row][col] != 'O')
                continue;

            board[row][col] = 'E';
            if (col < this.COLS - 1)
                queue.offer(new Cell(row, col + 1));
            if (row < this.ROWS - 1)
                queue.offer(new Cell(row + 1, col));
            if (col > 0)
                queue.offer(new Cell(row, col - 1));
            if (row > 0)
                queue.offer(new Cell(row - 1, col));
        }
    }

    /**
     * Luke - Search from edge - BFS
     *
     * Runtime: 5 ms Beats 46.59%
     * Memory: 52.1 MB Beats 22.33%
     *
     * Time: O(N) since each 'O' cell is visited once.
     * Space: O(N) Queue size.
     */
    public void solveLukeBfs(final char[][] board) {

        if (board.length < 3 || board[0].length < 3) {
            return;
        }

        final int ROWS = board.length;
        final int COLS = board[0].length;

        for (int row = 0; row < ROWS; row++) {
            if (board[row][0] == 'O')
                visitBfs(board, row, 0, ROWS, COLS);
            if (board[row][COLS - 1] == 'O')
                visitBfs(board, row, COLS - 1, ROWS, COLS);
        }

        for (int col = 0; col < COLS; col++) {
            if (board[0][col] == 'O')
                visitBfs(board, 0, col, ROWS, COLS);
            if (board[ROWS - 1][col] == 'O')
                visitBfs(board, ROWS - 1, col, ROWS, COLS);
        }

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                if (board[row][col] == '.') {
                    board[row][col] = 'O';
                } else if (board[row][col] == 'O') {
                    board[row][col] = 'X';
                }
            }
        }
    }

    /**
     * Time: O(N) since each 'O' cell is visited once.
     * Space: O(N) Queue size.
     */
    private void visitBfs(char[][] board, int row, int col, int ROWS, int COLS) {
        if (board[row][col] == 'O') {

            Queue<Cell> queue = new LinkedList<>();
            queue.offer(new Cell(row, col));

            while (!queue.isEmpty()) {

                int size = queue.size();
                for (int i = 0; i < size; i++) {
                    Cell cell = queue.poll();

                    if (board[cell.row][cell.col] != 'O') {
                        continue;
                    }

                    board[cell.row][cell.col] = '.';

                    if (cell.row > 0) {
                        queue.offer(new Cell(cell.row - 1, cell.col));
                    }
                    if (cell.row < ROWS - 1) {
                        queue.offer(new Cell(cell.row + 1, cell.col));
                    }
                    if (cell.col > 0) {
                        queue.offer(new Cell(cell.row, cell.col - 1));
                    }
                    if (cell.col < COLS - 1) {
                        queue.offer(new Cell(cell.row, cell.col + 1));
                    }
                }
            }
        }
    }

    public record Cell(int row, int col) {
    }

    /**
     * LC - Search from edge - DFS
     *
     * Runtime: 2 ms Beats 89.32%
     * Memory: 51.3 MB Beats 67.63%
     *
     * Time: O(ROWS * COLS)
     * Space: O(N): recursion stack depth
     */
    public void solveLcDfs(final char[][] board) {

        if (board.length < 3 || board[0].length < 3) {
            return;
        }

        final int ROWS = board.length;
        final int COLS = board[0].length;

        for (int row = 0; row < ROWS; row++) {
            visitDfs(board, row, 0, ROWS, COLS);
            visitDfs(board, row, COLS - 1, ROWS, COLS);
        }

        for (int col = 0; col < COLS; col++) {
            visitDfs(board, 0, col, ROWS, COLS);
            visitDfs(board, ROWS - 1, col, ROWS, COLS);
        }

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                if (board[row][col] == '.') {
                    board[row][col] = 'O';
                } else if (board[row][col] == 'O') {
                    board[row][col] = 'X';
                }
            }
        }
    }

    private void visitDfs(char[][] board, int row, int col, final int ROWS, final int COLS) {
        if (row < 0 || row >= ROWS || col < 0 || col >= COLS) {
            return;
        }

        if (board[row][col] != 'O') {
            return;
        }

        if (board[row][col] == 'O') {
            board[row][col] = '.';

            visitDfs(board, row - 1, col, ROWS, COLS);
            visitDfs(board, row + 1, col, ROWS, COLS);
            visitDfs(board, row, col - 1, ROWS, COLS);
            visitDfs(board, row, col + 1, ROWS, COLS);
        }

    }

    /**
     * Luke - Wrong - Search from inside
     */
    public void solveLukeWrong(final char[][] board) {

        if (board.length < 3 || board[0].length < 3) {
            return;
        }

        final int ROWS = board.length;
        final int COLS = board[0].length;

        for (int row = 1; row < ROWS - 1; row++) {
            for (int col = 1; col < COLS - 1; col++) {
                if (backtrack(board, row, col, ROWS, COLS)) {
                    board[row][col] = 'X';
                } else {
                    board[row][col] = 'O';
                }
            }
        }
    }

    private boolean backtrack(final char[][] board, final int row, final int col, final int ROWS, final int COLS) {
        if (board[row][col] == 'O' && (row == 0 || col == 0 || row == ROWS - 1 || col == COLS - 1)) {
            return false;
        }

        // DpUtils.print(board);

        if (board[row][col] != 'O') {
            if (board[row][col] == '.' || board[row][col] == 'X') {
                return true;
            } else {
                return false;
            }
        }

        /**
         * '.' visiting
         */
        board[row][col] = '.';

        boolean isSurrounded = (board[row - 1][col] != 'O' || backtrack(board, row - 1, col, ROWS, COLS)) &&
                (board[row][col - 1] != 'O' || backtrack(board, row, col - 1, ROWS, COLS)) &&
                (board[row][col + 1] != 'O' || backtrack(board, row, col + 1, ROWS, COLS)) &&
                (board[row + 1][col] != 'O' || backtrack(board, row + 1, col, ROWS, COLS));

        if (isSurrounded) {
            board[row][col] = 'X';
        } else {
            /**
             * visited and is not surrounded
             */
            board[row][col] = '-';
        }

        return isSurrounded;
    }

}
