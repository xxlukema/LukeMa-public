package com.learn.backtrack;


import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Assertions;

import lombok.extern.log4j.Log4j2;


/**
 * LC 79
 */
@Log4j2
public class WordSearch {

    public static void main(String[] args) {

        /*
        char[][] board = {
                { 'A', 'B', 'C', 'E' },
                { 'S', 'F', 'C', 'S' },
                { 'A', 'D', 'E', 'E' } };
        // String word = "ABCCED";
        String word = "SEE";
        */

        /*
        char[][] board = {
                { 'a', 'b' },
                { 'c', 'd' } };
        String word = "abdc";
        */

        /*
        char[][] board = {
                { 'C', 'A', 'A' },
                { 'A', 'A', 'A' },
                { 'B', 'C', 'D' } };
        String word = "AAB";
        */

        /**
         * Worset case:
         */
        char[][] board = {
                { 'A', 'A', 'A', 'A', 'A', 'A' },
                { 'A', 'A', 'A', 'A', 'A', 'A' },
                { 'A', 'A', 'A', 'A', 'A', 'A' },
                { 'A', 'A', 'A', 'A', 'A', 'A' },
                { 'A', 'A', 'A', 'A', 'A', 'A' },
        };

        StringBuilder sb = new StringBuilder();
        for (int row = 0; row < board.length; row++) {
            sb.append(List.of(board[row]).stream().map(ch -> String.valueOf(ch)).collect(Collectors.joining()));
        }
        sb.replace(sb.length() - 1, sb.length(), "z");

        log.debug(sb.toString());

        String word = sb.toString();

        WordSearch wordSearch = new WordSearch();

        var ret = wordSearch.existLuke(board, word);
        log.debug("exists Luke: {}", () -> ret);

        var retLc = wordSearch.existLc(board, word);
        log.debug("exists LC: {}", () -> retLc);

        Assertions.assertEquals(ret, retLc);
    }

    /**
     * Luke: Backtracking
     *
     * Runtime: 152 ms, faster than 63.40% of Java online submissions for Word Search.
     * Memory Usage: 42.1 MB, less than 55.89% of Java online submissions for Word Search.
     *
     * Worst case:
     *
     *     board:
     *        A A A A
     *        A A A A
     *        A A A A
     *     Word:
     *        "AAAAAAAAAAAAB"
     *
     * Time: O(N * 3 ^ LEN), where N = ROWS * COLS. LEN = word.length()
     * Space: O(LEN + N), where N = ROWS * COLS data is used to hold visited[][]
     */
    public boolean existLuke(char[][] board, String word) {
        final int ROWS = board.length;
        final int COLS = board[0].length;

        if (word.length() > ROWS * COLS) {
            return false;
        }

        boolean[][] visited = new boolean[ROWS][COLS];

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                if (backtrackLuke(board, ROWS, COLS, word, 0, row, col, visited)) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean backtrackLuke(
            char[][] board,
            int ROWS,
            int COLS,
            String word,
            int idx,
            int row,
            int col,
            boolean[][] visited) {

        if (idx == word.length()) {
            return true;
        }

        if (row < 0 || row >= ROWS || col < 0 || col >= COLS || visited[row][col]) {
            return false;
        }

        char ch = word.charAt(idx++);

        // log.debug("str: {}, ch: {}, row: {}, col: {}, visited: {}", word.substring(0, idx), ch, row, col, visited);

        if (board[row][col] != ch) {
            return false;
        } else {
            visited[row][col] = true;

            boolean matched = backtrackLuke(board, ROWS, COLS, word, idx, row + 1, col, visited) ||
                    backtrackLuke(board, ROWS, COLS, word, idx, row - 1, col, visited) ||
                    backtrackLuke(board, ROWS, COLS, word, idx, row, col + 1, visited) ||
                    backtrackLuke(board, ROWS, COLS, word, idx, row, col - 1, visited);

            /**
             * Backtrack to recover state
             */
            visited[row][col] = false;
            return matched;
        }
    }

    /**
     * LC: Backtracking
     *
     * Runtime: 353 ms, faster than 13.36% of Java online submissions for Word Search.
     * Memory Usage: 117.5 MB, less than 16.73% of Java online submissions for Word Search.
     *
     * Time: O(N * 3 ^ LEN), where N = ROWS * COLS. LEN = word.length()
     * Space: O(LEN)
     */
    public boolean existLc(char[][] board, String word) {

        final int ROWS = board.length;
        final int COLS = board[0].length;

        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                if (this.backtrackLc(board, row, col, word, 0, ROWS, COLS)) {
                    return true;
                }
            }
        }
        return false;
    }

    protected boolean backtrackLc(char[][] board, int row, int col, String word, int index, int ROWS, int COLS) {
        /* Step 1). check the bottom case. */
        if (index >= word.length()) {
            return true;
        }

        /* Step 2). Check the boundaries. */
        if (row < 0 || row == ROWS || col < 0 || col == COLS || board[row][col] != word.charAt(index)) {
            return false;
        }

        /* Step 3). explore the neighbors in DFS */
        boolean matched = false;

        // mark the path before the next exploration
        board[row][col] = '#';

        int[] rowOffsets = { 0, 1, 0, -1 };
        int[] colOffsets = { 1, 0, -1, 0 };

        for (int d = 0; d < 4; ++d) {
            matched = this.backtrackLc(board, row + rowOffsets[d], col + colOffsets[d], word, index + 1, ROWS, COLS);
            if (matched) {
                break;
            }
        }

        /* Step 4). clean up and return the result. */
        board[row][col] = word.charAt(index);

        return matched;
    }
}
