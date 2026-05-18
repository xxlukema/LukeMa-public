package com.learn.backtrack.redo;


import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Assertions;

import lombok.extern.log4j.Log4j2;


/**
 * LC - 52 - N-Queens II
 *
 * Hard
 *
 * The n-queens puzzle is the problem of placing n queens on an n x n chessboard such that no two queens attack each other.
 *
 * Given an integer n, return the number of distinct solutions to the n-queens puzzle.
 *
 * Example 1:
 * Input: n = 4
 * Output: 2
 * Explanation: There are two distinct solutions to the 4-queens puzzle as shown.
 *
 * Example 2:
 * Input: n = 1
 * Output: 1
 *
 * Constraints:
 *     1 <= n <= 9
 */
@Log4j2
public class NqueensII {

    public static void main(String[] args) {

        final int size = 7;

        NqueensII nqueensII = new NqueensII();

        var totalNQueensLc = nqueensII.totalNQueensLc(size);
        log.debug("NQueens II: {}", () -> totalNQueensLc);
        log.debug("NQueens II {} OK", () -> "totalNQueensLc");

        var totalNQueensLuke = nqueensII.totalNQueensLuke(size);
        Assertions.assertEquals(totalNQueensLc, totalNQueensLuke);
        log.debug("NQueens II {} OK", () -> "totalNQueensLuke");
    }

    public int totalNQueensLuke(int size) {
        return backtrackLuke(size, 0, new HashSet<>(), new HashSet<>(), new HashSet<>());
    }

    private int backtrackLuke(int size, int row, Set<Integer> diagonals, Set<Integer> antidiagonals, Set<Integer> cols) {

        /**
         * Trick: End of backtrack
         */
        if (row == size) {
            return 1;
        }

        int solutions = 0;

        for (int col = 0; col < size; col++) {
            int currDiagonal = row - col;
            int currAntigonal = row + col;

            if (diagonals.contains(currDiagonal) || antidiagonals.contains(currAntigonal) || cols.contains(col)) {
                continue;
            }

            diagonals.add(currDiagonal);
            antidiagonals.add(currAntigonal);
            cols.add(col);

            solutions += backtrackLuke(size, row + 1, diagonals, antidiagonals, cols);

            diagonals.remove(currDiagonal);
            antidiagonals.remove(currAntigonal);
            cols.remove(col);
        }

        return solutions;
    }

    public int totalNQueensLc(int size) {
        return backtrackLc(size, 0, new HashSet<>(), new HashSet<>(), new HashSet<>());
    }

    private int backtrackLc(final int size, int row, Set<Integer> diagonals, Set<Integer> antiDiagonals, Set<Integer> cols) {
        // Base case - N queens have been placed
        if (row == size) {
            return 1;
        }

        int solutions = 0;

        for (int col = 0; col < size; col++) {
            int currDiagonal = row - col;
            int currAntiDiagonal = row + col;
            // If the queen is not placeable
            if (cols.contains(col) || diagonals.contains(currDiagonal) || antiDiagonals.contains(currAntiDiagonal)) {
                continue;
            }

            // "Add" the queen to the board
            cols.add(col);
            diagonals.add(currDiagonal);
            antiDiagonals.add(currAntiDiagonal);

            // Move on to the next row with the updated board state
            solutions += backtrackLc(size, row + 1, diagonals, antiDiagonals, cols);

            // "Remove" the queen from the board since we have already
            // explored all valid paths using the above function call
            cols.remove(col);
            diagonals.remove(currDiagonal);
            antiDiagonals.remove(currAntiDiagonal);
        }

        return solutions;
    }
}
