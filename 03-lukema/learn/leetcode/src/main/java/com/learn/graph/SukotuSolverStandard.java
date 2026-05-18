package com.learn.graph;


public class SukotuSolverStandard {

    public static void main(String[] args) {

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

        SukotuSolverStandard sukotuSolverStandard = new SukotuSolverStandard();

        // Good
        // System.out.println("LeetCode Standard:");
        // sukotuSolverStandard.solveSudoku(board);
        // sukotuSolverStandard.print();

        // Good
        // System.out.println("No exstra dp arrays:");
        // sukotuSolverStandard.putNumbers(board);
        // sukotuSolverStandard.print(board);

        // Good
        System.out.println("DFS:");
        sukotuSolverStandard.solveSudokuDFS(board);
        sukotuSolverStandard.print(board);
    }

    /////////////////////////
    /**
     * No exstra arrays
     */
    public boolean putNumbers(char[][] board) {
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board.length; c++) {

                //Skip all the filled cells
                if (board[r][c] != '.') {
                    continue;
                }

                //Try numbers from 0 to 9 one by one to fill the cell
                for (int n = 1; n <= 9; n++) {
                    char ch = (char) (n + '0');

                    //check if number can be placed in the cell
                    if (canPlace(board, r, c, ch)) {
                        //place number in the cell
                        board[r][c] = ch;

                        //if the sudoko in solved then return true
                        //Otherwise remove the number
                        if (putNumbers(board)) {
                            return true;
                        } else {
                            board[r][c] = '.';
                        }
                    }
                }

                //if not able to find the valid number to fill then return
                //So that another/next number can be put in previous cell
                if (board[r][c] == '.') {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean canPlace(char[][] board, int r, int c, char ch) {

        //check row feasibility
        for (int row = 0; row < 9; row++) {
            if (board[row][c] == ch) {
                return false;
            }
        }

        //check column feasibility
        for (int col = 0; col < 9; col++) {
            if (board[r][col] == ch) {
                return false;
            }
        }

        //check box feasibility
        int box = (r / 3) * 3 + (c / 3);
        for (int i = 0; i < 9; i++) {
            for (int k = 0; k < 9; k++) {
                int temp_box = (i / 3) * 3 + (k / 3);
                if (temp_box == box && board[i][k] == ch) {
                    return false;
                }
            }
        }
        return true;
    }

    /////////////////////////
    /////////////////////////

    /**
     * DFS
     */
    public void solveSudokuDFS(char[][] board) {
        if (board == null || board.length != 9 || board[0].length != 9) {
            return;
        }

        /**
         * Init dp
         */
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                if (board[r][c] == '.') {
                    continue;
                }
                int digit = board[r][c] - '0';
                dpRow[r][digit] = true;
                dpCol[c][digit] = true;
                dpBox[(r / 3) * 3 + (c / 3)][digit] = true;
            }
        }

        // dfs with backtrack
        dfs(0, board);

    }

    private boolean dfs(int pos, char[][] board) {
        // base case : reach the end
        if (pos == 81) {
            return true;
        }

        while (board[pos / 9][pos % 9] != '.') {
            pos++;
            if (pos == 81) {
                return true;
            }
        }

        // get 2D coordinate
        int row = pos / 9; // row
        int col = pos % 9; // col

        // select possible numbers
        for (int i = 1; i <= 9; i++) {
            // check used
            if (dpRow[row][i] || dpCol[col][i] || dpBox[(row / 3) * 3 + (col / 3)][i]) {
                continue;
            }

            // use current one
            board[row][col] = (char) (i + '0');
            dpRow[row][i] = true;
            dpCol[col][i] = true;
            dpBox[(row / 3) * 3 + (col / 3)][i] = true;
            // dfs
            if (dfs(pos + 1, board)) {
                return true;
            } else {
                // backtrack
                board[row][col] = '.';
                dpRow[row][i] = false;
                dpCol[col][i] = false;
                dpBox[(row / 3) * 3 + (col / 3)][i] = false;
            }
        }

        return false;
    }

    /////////////////////////
    /////////////////////////
    /////////////////////////

    // box size
    final int n = 3;
    // row size
    final int N = n * n;

    final boolean[][] dpRow = new boolean[N][N + 1];
    final boolean[][] dpCol = new boolean[N][N + 1];
    final boolean[][] dpBox = new boolean[N][N + 1];

    char[][] board;

    boolean sudokuSolved = false;

    public boolean couldPlace(int d, int row, int col) {
        /*
        Check if one could place a number d in (row, col) cell
        */
        int idx = (row / n) * n + (col / n);
        return !dpRow[row][d] && !dpCol[col][d] && !dpBox[idx][d];
    }

    public void placeNumber(int d, int row, int col) {
        /*
        Place a number d in (row, col) cell
        */
        int idx = (row / n) * n + (col / n);

        dpRow[row][d] = true;
        dpCol[col][d] = true;
        dpBox[idx][d] = true;
        board[row][col] = (char) (d + '0');
    }

    public void removeNumber(int d, int row, int col) {
        /*
        Remove a number which didn't lead to a solution
        */
        int idx = (row / n) * n + (col / n);
        dpRow[row][d] = false;
        dpCol[col][d] = false;
        dpBox[idx][d] = false;
        board[row][col] = '.';
    }

    public void stepToNextCell(int row, int col) {
        /*
        Call backtrack function in recursion
        to continue to place numbers
        till the moment we have a solution
        */
        // if we're in the last cell
        // that means we have the solution
        if ((col == N - 1) && (row == N - 1)) {
            sudokuSolved = true;
        } else {
            // if we're in the end of the row
            // go to the next row
            // else
            // go to the next column
            if (col == N - 1) {
                backtrack(row + 1, 0);
            } else {
                backtrack(row, col + 1);
            }
        }
    }

    public void backtrack(int row, int col) {
        /*
        Backtracking
        */
        // if the cell is empty
        if (board[row][col] == '.') {
            // iterate over all numbers from 1 to 9
            for (int d = 1; d < 10; d++) {
                if (couldPlace(d, row, col)) {
                    placeNumber(d, row, col);
                    stepToNextCell(row, col);
                    // if sudoku is solved, there is no need to backtrack
                    // since the single unique solution is promised
                    if (!sudokuSolved) {
                        removeNumber(d, row, col);
                    }
                }
            }
        } else {
            stepToNextCell(row, col);
        }
    }

    public void solveSudoku(char[][] board) {
        this.board = board;

        // init rows, columns and boxes
        for (int row = 0; row < N; row++) {
            for (int col = 0; col < N; col++) {
                char ch = board[row][col];
                if (ch != '.') {
                    int d = Character.getNumericValue(ch);
                    placeNumber(d, row, col);
                }
            }
        }
        backtrack(0, 0);
    }

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

    void print(char[][] board) {
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
}
