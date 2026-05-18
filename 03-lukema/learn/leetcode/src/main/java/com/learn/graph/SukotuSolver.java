package com.learn.graph;


import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import lombok.extern.log4j.Log4j2;


@Log4j2
public class SukotuSolver {

      final int N = 9;

    boolean[][] dpRow = new boolean[N][N];
    boolean[][] dpCol = new boolean[N][N];
    boolean[][] dpBox = new boolean[N][N];

    char[][] board = new char[N][N];

    public static void main(String[] args) {
        log.info("{}", () -> SukotuSolver.class.getSimpleName());

        // @formatter:off
        char [][] board =
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

        SukotuSolver sukotuSolver = new SukotuSolver();
        sukotuSolver.reset(board);

        sukotuSolver.print();

        /*
        boolean isValid = sukotuSolver.isValid();
        log.info("isValid: {}", () -> isValid);
        Assertions.assertTrue(isValid);
        */

        sukotuSolver.solveSudoku();

    }

    boolean isCompleted() {
        for (int r = 0; r < N; r++) {
            for (int c = 0; c < N; c++) {
                if (board[r][c] == '.') {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean solveSudoku() {
        Stack<Node> colStack = new Stack<>();

        // Fill truth table
        for (int r = 0; r < N; r++) {
            for (int c = 0; c < N; c++) {
                char ch = this.board[r][c];
                if (ch == '.') {

                    Integer[] values = availableValues(r, c);

                    if (values.length == 0) {
                        if (!colStack.isEmpty()) {
                            Node node = colStack.pop();
                            this.board[node.row()][node.col()] = '.';
                            update();
                            return false;
                        }
                    } else {
                        for (int i = 0; i < values.length; i++) {
                            // System.out.println("-------------------");
                            // print();
                            colStack.add(new Node(r, c));
                            this.board[r][c] = int2char(values[i]);
                            update();
                            solveSudoku();
                        }
                    }
                }
            }
        }

        return true;

        // System.out.println("-------------------");

        // print();

        // log.info("Solution: {}", () -> this.board);

    }

    /*
    public boolean solveSudoku(int row, int col) {
    
        char ch = board[row][col];
        if (ch == '.') {
            Integer[] values = availableValues(row, col);
            if (values.length == 0) {
                return false;
            } else {
    
            }
        }
    
        return true;
    }
    */

    void print() {
        StringBuilder sb = new StringBuilder();
        for (int r = 0; r < N; r++) {
            for (int c = 0; c < N; c++) {
                sb.append(board[r][c]).append(' ');
            }
            sb.append(System.lineSeparator());
        }

        System.out.println();
        System.out.println(sb.toString());
    }

    char int2char(int i) {
        return Integer.toString(i).charAt(0);
    }

    public void reset(char[][] board) {

        // Init truth table
        for (int r = 0; r < N; r++) {
            for (int c = 0; c < N; c++) {
                this.board[r][c] = board[r][c];
                char ch = board[r][c];

                if (ch == '.') {
                    continue;
                } else {
                    int value = ch - '1';
                    int idx = (r / 3) * 3 + (c / 3);
                    dpRow[r][value] = true;
                    dpCol[value][c] = true;
                    dpBox[idx][value] = true;
                }
            }
        }
    }

    public void update() {

        // Init truth table
        for (int r = 0; r < N; r++) {
            for (int c = 0; c < N; c++) {
                char ch = board[r][c];

                if (ch == '.') {
                    continue;
                } else {
                    int value = ch - '1';
                    int idx = (r / 3) * 3 + (c / 3);
                    dpRow[r][value] = true;
                    dpCol[value][c] = true;
                    dpBox[idx][value] = true;
                }
            }
        }
    }

    public Integer[] availableValues(int row, int col) {

        // Find available values
        List<Integer> ret = new ArrayList<>();
        for (int i = 1; i <= N; i++) {
            if (isValid(i, row, col)) {
                ret.add(i);
            }
        }

        return ret.toArray(new Integer[0]);
    }

    public boolean isValid(int value, int row, int col) {
        value -= 1;
        int idx = (row / 3) * 3 + (col / 3);

        return !dpRow[row][value] && !dpCol[value][col] && !dpBox[idx][value];
    }

    /*
    public boolean isValid() {
        for (int r = 0; r < N; r++) {
            for (int c = 0; c < N; c++) {
                char ch = board[r][c];
    
                if (ch == '.') {
                    continue;
                } else {
                    int value = ch - '1';
    
                    if (!isValid(value, r, c)) {
                        return false;
                    }
                }
            }
        }
    
        return true;
    }
    */

    record Node(int row, int col) {
        Node(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }
}
