package com.learn.graph;


import org.junit.jupiter.api.Assertions;

import lombok.extern.log4j.Log4j2;


@Log4j2
public class SukotuValidator {

    // @SuppressWarnings("unused")
    public static void main(String[] args) {
        // @formatter:off
        // valid
        char [][] board2 = 
                        {{'5','3','.','.','7','.','.','.','.'}
                        ,{'6','.','.','1','9','5','.','.','.'}
                        ,{'.','9','8','.','.','.','.','6','.'}
                        ,{'8','.','.','.','6','.','.','.','3'}
                        ,{'4','.','.','8','.','3','.','.','1'}
                        ,{'7','.','.','.','2','.','.','.','6'}
                        ,{'.','6','.','.','.','.','2','8','.'}
                        ,{'.','.','.','4','1','9','.','.','5'}
                        ,{'.','.','.','.','8','.','.','7','9'}};
         // @formatter:on

        // @formatter:off
        // invalid
         char [][] board3 = 
                       {{'.','.','4','.','.','.','6','3','.'},
                        {'.','.','.','.','.','.','.','.','.'},
                        {'5','.','.','.','.','.','.','9','.'},
                        {'.','.','.','5','6','.','.','.','.'},
                        {'4','.','3','.','.','.','.','.','1'},
                        {'.','.','.','7','.','.','.','.','.'},
                        {'.','.','.','5','.','.','.','.','.'},
                        {'.','.','.','.','.','.','.','.','.'},
                        {'.','.','.','.','.','.','.','.','.'}};
         // @formatter:on

        // @formatter:off
        // valid
         char [][] board4 = 
                       {{'.','.','.','.','.','.','.','.','.'},
                        {'.','.','.','.','.','.','.','.','.'},
                        {'.','.','.','.','.','.','.','.','.'},
                        {'.','.','.','.','.','.','.','.','.'},
                        {'.','.','.','.','.','.','.','.','.'},
                        {'.','.','.','.','.','.','.','.','.'},
                        {'.','.','.','.','.','.','.','.','.'},
                        {'.','.','.','.','.','.','.','.','.'},
                        {'.','.','.','.','.','.','.','.','.'}};
         // @formatter:on

        // @formatter:off
        // valid
         char [][] board =
                       {{'.','.','5','.','.','.','.','.','6'},
                        {'.','.','.','.','1','4','.','.','.'},
                        {'.','.','.','.','.','.','.','.','.'},
                        {'.','.','.','.','.','9','2','.','.'},
                        {'5','.','.','.','.','2','.','.','.'},
                        {'.','.','.','.','.','.','.','3','.'},
                        {'.','.','.','5','4','.','.','.','.'},
                        {'3','.','.','.','.','.','4','2','.'},
                        {'.','.','.','2','7','.','6','.','.'}};
         // @formatter:on

        // @formatter:off
        char [][] board5 = 
                       {{'5','3','.','.','7','.','.','.','.'},
                        {'6','.','.','1','9','5','.','.','.'},
                        {'.','9','8','.','.','.','.','6','.'},
                        {'8','.','.','.','6','.','.','.','3'},
                        {'4','.','.','8','.','3','.','.','1'},
                        {'7','.','.','.','2','.','.','.','6'},
                        {'.','6','.','.','.','.','2','8','.'},
                        {'.','.','.','4','1','9','.','.','5'},
                        {'.','.','.','.','8','.','.','7','9'}};
        // @formatter:on

        SukotuValidator sukotu = new SukotuValidator();
        boolean isValid = sukotu.isValidSudoku(board);
        log.info("Mine isValid: {}", isValid);
        Assertions.assertTrue(isValid);

        isValid = sukotu.isValidSudokuStandard(board);
        log.info("Standard isValid: {}", isValid);
        Assertions.assertTrue(isValid);

        // board4
        isValid = sukotu.isValidSudoku(board4);
        log.info("board4 Mine isValid: {}", isValid);
        Assertions.assertTrue(isValid);

        isValid = sukotu.isValidSudokuStandard(board4);
        log.info("board4 Standard isValid: {}", isValid);
        Assertions.assertTrue(isValid);

        // board3
        isValid = sukotu.isValidSudoku(board3);
        log.info("board3 Mine isValid: {}", isValid);
        Assertions.assertFalse(isValid);

        isValid = sukotu.isValidSudokuStandard(board3);
        log.info("board3 Standard isValid: {}", isValid);
        Assertions.assertFalse(isValid);

        // board2
        isValid = sukotu.isValidSudoku(board2);
        log.info("board2 Mine isValid: {}", isValid);
        Assertions.assertTrue(isValid);

        isValid = sukotu.isValidSudokuStandard(board2);
        log.info("board2 Standard isValid: {}", isValid);
        Assertions.assertTrue(isValid);

        // board5
        isValid = sukotu.isValidSudoku(board5);
        log.info("board5 Mine isValid: {}", isValid);
        Assertions.assertTrue(isValid);

        isValid = sukotu.isValidSudokuStandard(board5);
        log.info("board5 Standard isValid: {}", isValid);
        Assertions.assertTrue(isValid);

    }

    public boolean isValidSudokuStandard(char[][] board) {

        final int N = board.length;

        boolean[][] dpRow = new boolean[N][N];
        boolean[][] dpCol = new boolean[N][N];
        boolean[][] dpBox = new boolean[N][N];

        for (int r = 0; r < N; r++) {
            for (int c = 0; c < N; c++) {

                char ch = board[r][c];
                if (ch == '.') {
                    continue;
                }

                int value = ch - '1';

                if (dpRow[r][value]) {
                    return false;
                } else {
                    dpRow[r][value] = true;
                }

                if (dpCol[value][c]) {
                    return false;
                } else {
                    dpCol[value][c] = true;
                }

                int box = (r / 3) * 3 + (c / 3);
                if (dpBox[box][value]) {
                    return false;
                } else {
                    dpBox[box][value] = true;
                }
            }
        }

        return true;
    }

    public boolean isValidSudoku(char[][] board) {

        // Not filled
        boolean isFilled = false;
        for (int i = 0; i < 9; i++) {
            for (int k = 0; k < 9; k++) {
                if (board[i][k] != '.') {
                    isFilled = true;
                    break;
                }
            }
            if (isFilled) {
                break;
            }
        }
        if (!isFilled) {
            return true;
        }

        // rows
        for (int i = 0; i < board.length; i++) {
            boolean isValid = isValidRow(board[i], 0, 8);
            if (!isValid) {
                return false;
            }
        }

        // cols
        for (int i = 0; i < board[0].length; i++) {
            int[] dp = new int[10];
            for (int k = 0; k < board[0].length; k++) {
                char ch = board[k][i];
                if (ch != '.') {
                    int value = charToInt(ch);
                    if (dp[value] != 0) {
                        return false;
                    } else {
                        dp[value] = 1;
                    }
                }
            }
        }

        // subs
        for (int counter = 0; counter <= 6; counter += 3) {
            for (int inc = 0; inc <= 6; inc += 3) {
                int[] dp = new int[10];
                for (int m = 0; m < 3; m++) {
                    for (int n = 0; n < 3; n++) {
                        char ch = board[counter + m][inc + n];
                        if (ch != '.') {
                            int value = charToInt(ch);
                            if (dp[value] != 0) {
                                return false;
                            } else {
                                dp[value] = 1;
                            }
                        }
                    }
                }
            }
        }

        return true;
    }

    boolean isValidRow(char[] arr, int left, int right) {
        int[] dp = new int[arr.length + 1];
        for (int i = left; i <= right; i++) {
            char ch = arr[i];
            if (ch != '.') {
                int value = charToInt(ch);
                if (dp[value] != 0) {
                    return false;
                } else {
                    dp[value] = 1;
                }
            }
        }
        return true;
    }

    int charToInt(char ch) {
        return Integer.valueOf(String.valueOf(ch));
    }
}
