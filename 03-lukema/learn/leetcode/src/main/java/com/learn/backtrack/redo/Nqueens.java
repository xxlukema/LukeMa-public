package com.learn.backtrack.redo;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import lombok.extern.log4j.Log4j2;


/**
 * LC - 51- NQueens
 *
 * Hard
 *
 * The n-queens puzzle is the problem of placing n queens on an n x n chessboard such that no two queens attack each other.
 *
 * Given an integer n, return all distinct solutions to the n-queens puzzle. You may return the answer in any order.
 *
 * Each solution contains a distinct board configuration of the n-queens' placement, where 'Q' and '.' both indicate a queen and an empty space, respectively.
 *
 * Example 1:
 * Input: n = 4
 * Output: [[".Q..","...Q","Q...","..Q."],["..Q.","Q...","...Q",".Q.."]]
 * Explanation: There exist two distinct solutions to the 4-queens puzzle as shown above
 *
 * Example 2:
 * Input: n = 1
 * Output: [["Q"]]
 *
 * Constraints:
 *     1 <= n <= 9
 */
@Log4j2
public class Nqueens {

    public static void main(String[] args) {

        final int n = 4;

        Nqueens nqueens = new Nqueens();

        var ret = nqueens.solveNQueens(n);

        log.debug("N-Queens: {}", () -> ret);
        log.debug("N-Queens {} OK", () -> "ret");
    }

    /**
     * Luke - backtrack
     *
     * Runtime: 13 ms Beats 24.46%
     * Memory: 46.4 MB Beats 29.57%
     *
     * Time: O(N!)
     * Space: O(N ^ 2)
     */
    public List<List<String>> solveNQueens(int n) {
        List<List<String>> result = new ArrayList<>();

        if (n < 1) {
            return result;
        }

        final Boolean[][] board = new Boolean[n][n];
        for (int r = 0; r < n; r++) {
            Arrays.fill(board[r], false);
        }

        backtrack(n, board, 0, result);

        return result;
    }

    private boolean backtrack(
            final int N,
            final Boolean[][] board,
            int row,
            final List<List<String>> result) {

        // Start
        if (row == N) {
            //
            List<String> list = new ArrayList<>();
            /**
             * Time: O(N ^ 2)
             */
            for (int r = 0; r < N; r++) {
                list.add(Arrays.stream(board[r]).map(e -> e.booleanValue() ? "Q" : ".").collect(Collectors.joining()));
            }

            result.add(list);

            return true;
        }

        boolean isAvailable = false;
        for (int c = 0; c < N; c++) {
            if (isAvailable(board, row, c)) {
                isAvailable = true;

                board[row][c] = true;

                backtrack(N, board, row + 1, result);

                board[row][c] = false;
            }
        }

        return isAvailable;
    }

    boolean isAvailable(
            final Boolean[][] board,
            int row,
            int col) {
        // Start
        for (int r = row; r >= 0; r--) {
            if (board[r][col]) {
                return false;
            }
        }

        for (int c = col; c >= 0; c--) {
            if (board[row][c]) {
                return false;
            }
        }

        for (int i = 1, n = Math.max(row, col), N = board.length; i <= n; i++) {
            if (row - i >= 0 && col - i >= 0 && board[row - i][col - i]) {
                return false;
            }

            if (row - i >= 0 && col + i < N && board[row - i][col + i]) {
                return false;
            }
        }

        return true;
    }
}
