package com.learn.graph;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import lombok.extern.log4j.Log4j2;


@Log4j2
public class Nqueens {

    @SuppressWarnings("unused")
    public static void main(String[] args) {

        final int n = 6;

        /**
         * n = 6
         */
        // @formatter:off
        String[][] strs6 =
                            {{".Q....",
                              "...Q..",
                              ".....Q",
                              "Q.....",
                              "..Q...",
                              "....Q."},
                             {"..Q...",
                              ".....Q",
                              ".Q....",
                              "....Q.",
                              "Q.....",
                              "...Q.."},
                             {"...Q..",
                              "Q.....",
                              "....Q.",
                              ".Q....",
                              ".....Q",
                              "..Q..."},
                             {"....Q.",
                              "..Q...",
                              "Q.....",
                              ".....Q",
                              "...Q..",
                              ".Q...."}};
        // @formatter:on

        Nqueens nqueens = new Nqueens();

        List<List<String>> results = nqueens.solveNQueensLC(n);

        StringBuilder sb = new StringBuilder();
        sb.append(System.lineSeparator());

        results.forEach(solution -> {
            solution.forEach(e -> {
                sb.append(e).append(System.lineSeparator());
            });
            sb.append(System.lineSeparator());
        });

        log.info("Solutions: \n{}", () -> sb.toString());

    }

    public List<List<String>> solveNQueensLC(int n) {

        List<List<String>> results = new ArrayList<>();

        Character[][] board = new Character[n][n];

        this.initBoard(board);

        backtrackLC(0, board, ConcurrentHashMap.newKeySet(), ConcurrentHashMap.newKeySet(), ConcurrentHashMap.newKeySet(), results);

        return results;
    }

    /**
     * Init board
     */
    private void initBoard(Character[][] board) {
        final int N = board.length;
        for (int r = 0; r < N; r++) {
            for (int c = 0; c < N; c++) {
                board[r][c] = '.';
            }
        }
    }

    private void backtrackLC(
            int row,
            Character[][] board,
            Set<Integer> cols,
            Set<Integer> diagonals,
            Set<Integer> antiDiagonals,
            List<List<String>> results) {

        final int N = board.length;

        if (row == N) {
            results.add(this.charBoardToListOfStrings(board));
        } else {
            for (int col = 0; col < N; col++) {

                int diagonal = row + col;
                int antiDiagonal = row - col;

                if (cols.contains(col) || diagonals.contains(diagonal) || antiDiagonals.contains(antiDiagonal)) {
                    continue;
                }

                board[row][col] = 'Q';
                cols.add(col);
                diagonals.add(diagonal);
                antiDiagonals.add(antiDiagonal);

                GraphUtils.printGraph(board);

                backtrackLC(row + 1, board, cols, diagonals, antiDiagonals, results);

                board[row][col] = '.';
                cols.remove(col);
                diagonals.remove(diagonal);
                antiDiagonals.remove(antiDiagonal);
            }
        }
    }

    // Making use of a helper function to get the
    // solutions in the correct output format
    private List<String> charBoardToListOfStrings(Character[][] board) {

        final int N = board.length;
        List<String> solution = new ArrayList<>();

        for (int r = 0; r < N; r++) {
            solution.add(Arrays.stream(board[r]).map(e -> String.valueOf(e)).collect(Collectors.joining(" ")));
        }

        return solution;
    }

}
