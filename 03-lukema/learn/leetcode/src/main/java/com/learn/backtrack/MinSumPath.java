package com.learn.backtrack;


import java.util.LinkedList;
import java.util.PriorityQueue;

import org.junit.jupiter.api.Assertions;

import lombok.extern.log4j.Log4j2;


@Log4j2
public class MinSumPath {

    public static void main(String[] args) {

        /*
        int[][] grid = {
                { 1, 3, 1 },
                { 1, 5, 1 },
                { 4, 2, 1 } };
        */

        int[][] grid = {
                { 5, 0, 1, 1, 2, 1, 0, 1, 3, 6, 3, 0, 7, 3, 3, 3, 1 },
                { 1, 4, 1, 8, 5, 5, 5, 6, 8, 7, 0, 4, 3, 9, 9, 6, 0 },
                { 2, 8, 3, 3, 1, 6, 1, 4, 9, 0, 9, 2, 3, 3, 3, 8, 4 },
                { 3, 5, 1, 9, 3, 0, 8, 3, 4, 3, 4, 6, 9, 6, 8, 9, 9 },
                { 3, 0, 7, 4, 6, 6, 4, 6, 8, 8, 9, 3, 8, 3, 9, 3, 4 },
                { 8, 8, 6, 8, 3, 3, 1, 7, 9, 3, 3, 9, 2, 4, 3, 5, 1 },
                { 7, 1, 0, 4, 7, 8, 4, 6, 4, 2, 1, 3, 7, 8, 3, 5, 4 },
                { 3, 0, 9, 6, 7, 8, 9, 2, 0, 4, 6, 3, 9, 7, 2, 0, 7 },
                { 8, 0, 8, 2, 6, 4, 4, 0, 9, 3, 8, 4, 0, 4, 7, 0, 4 },
                { 3, 7, 4, 5, 9, 4, 9, 7, 9, 8, 7, 4, 0, 4, 2, 0, 4 },
                { 5, 9, 0, 1, 9, 1, 5, 9, 5, 5, 3, 4, 6, 9, 8, 5, 6 },
                { 5, 7, 2, 4, 4, 4, 2, 1, 8, 4, 8, 0, 5, 4, 7, 4, 7 },
                { 9, 5, 8, 6, 4, 4, 3, 9, 8, 1, 1, 8, 7, 7, 3, 6, 9 },
                { 7, 2, 3, 1, 6, 3, 6, 6, 6, 3, 2, 3, 9, 9, 4, 4, 8 } };

        MinSumPath minSumPath = new MinSumPath();

        var ret = minSumPath.minPathSumBacktrackDfsLuke(grid);
        log.debug("Min sum path luke DFS backtrack: {}", () -> ret);

        // var retBFS = minSumPath.minPathSumBFSLukeMax17by5Matrix(grid);
        // log.debug("Min sum path luke BFS: {}", () -> retBFS);

        // Assertions.assertEquals(ret, retBFS);

        var retDp2D = minSumPath.minPathSumDp2DLuke(grid);
        log.debug("Min sum path luke DP 2D: {}", () -> retDp2D);

        Assertions.assertEquals(ret, retDp2D);

        var retDp1D = minSumPath.minPathSumDp1DTopDownLuke(grid);

        log.debug("Min sum path luke DP 1D: {}", () -> retDp1D);

        Assertions.assertEquals(ret, retDp1D);

    }

    /**
     * Luke - DP 1D Top-down - Start
     *
     * Time: O(m * n) - We traverse the entire matrix once.
     * Space: O(n) - Another array of row size is used.
     */
    public int minPathSumDp1DTopDownLuke(int[][] grid) {
        int[] dp = new int[grid[0].length];

        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[0].length; col++) {
                if (row != 0 && col != 0) {
                    dp[col] = grid[row][col] + Math.min(dp[col - 1], dp[col]);
                } else if (col == 0) {
                    dp[0] = dp[0] + grid[row][0];
                } else if (row == 0) {
                    dp[col] = dp[col - 1] + grid[0][col];
                } else {
                    dp[0] = grid[0][0];
                }
            }
        }

        return dp[grid[0].length - 1];
    }

    /**
     * Luke - DP 1D - End
     */
    /////////////

    /**
     * Luke - DP 2D - Start
     *
     * Time: O(m * n) - We traverse the entire matrix once.
     * Space: O(m * n) - Another matrix of the same size is used.
     */
    public int minPathSumDp2DLuke(int[][] grid) {
        int[][] dp = new int[grid.length][grid[0].length];

        for (int row = grid.length - 1; row >= 0; row--) {
            for (int col = grid[0].length - 1; col >= 0; col--) {

                if (row + 1 < grid.length && col + 1 < grid[0].length) {
                    dp[row][col] = grid[row][col] + Math.min(dp[row + 1][col], dp[row][col + 1]);
                } else if (row + 1 < grid.length) {
                    dp[row][col] = grid[row][col] + dp[row + 1][col];
                } else if (col + 1 < grid[0].length) {
                    dp[row][col] = grid[row][col] + dp[row][col + 1];
                } else {
                    dp[row][col] = grid[row][col];
                }
            }
        }

        return dp[0][0];
    }

    /**
     * Luke - DP 2D - End
     */
    ////////////

    /**
     * BFS will throw "OutOfMemory" or "Stackoverflow" Exception with matrix of int[17][6].
     * It works for int[17][5] matrix for PC with 32 GB memory, or int[17][4] matrix for PC with 16 GB memory.
     *
     * (1) Timeout Exception on LC with large matrix.
     * (2) "OutOfMemory" or "Stackoverflow" Exception for large array[17][6] for PC with 32 GB memory,
     *     or array[17][5] for PC with 16 GB memory.
     * (3) PriorityQueue size larger than 2,000.
     *
     * Time: O(m * n) + O(m * n * log(n))
     * Space: O(2 * m * n)
     */
    public int minPathSumBFSLukeMax17by5Matrix(int[][] grid) {

        minPath = 0;

        PriorityQueue<NodeBfs> q = new PriorityQueue<>((a, b) -> a.dist - b.dist);

        NodeBfs source = new NodeBfs(0, 0, grid[0][0], grid[0][0], null);
        q.add(source);

        backtrackBfsLuke(grid, q);

        return minPath;
    }

    private void backtrackBfsLuke(int[][] grid, PriorityQueue<NodeBfs> q) {

        // log.debug("q: {}", () -> q);

        if (q.isEmpty()) {
            return;
        }

        NodeBfs curr = q.poll();

        if (curr.row == grid.length - 1 && curr.col == grid[0].length - 1) {
            minPath = curr.dist;

            // String str = path.stream().map(e -> String.valueOf(e.val)).collect(Collectors.joining(" "));

            // log.debug("minPath: {}", () -> minPath);

            log.debug("q.size: {}", () -> q.size());

            NodeBfs node = curr;
            StringBuilder sb = new StringBuilder();
            while (node != null) {
                sb.append(node.val).append(' ');
                node = node.last;
            }
            sb.reverse();
            log.debug("path: {}", () -> sb.toString());

            return;
        } else {

            int nextCol = curr.col + 1;
            if (nextCol < grid[0].length) {
                NodeBfs node = new NodeBfs(curr.row, nextCol, grid[curr.row][nextCol], curr.dist + grid[curr.row][nextCol], curr);
                q.add(node);
            }

            int nextRow = curr.row + 1;
            if (nextRow < grid.length) {
                NodeBfs node = new NodeBfs(nextRow, curr.col, grid[nextRow][curr.col], curr.dist + grid[nextRow][curr.col], curr);
                q.add(node);
            }

            backtrackBfsLuke(grid, q);
        }
    }

    /**
     * DFS does not have "OutOfMemory" or "StackOveflow" kind of exceptions for big matrix until now.
     *
     * Timeout Exception on LC with large matrix.
     *
     * Time: O(m * n)
     * Space: O(m + n)
     */
    public int minPathSumBacktrackDfsLuke(int[][] grid) {

        minPath = 0;

        LinkedList<NodeDfs> path = new LinkedList<>();

        backtrackDfsLuke(grid, 0, 0, path);

        return minPath;
    }

    void backtrackDfsLuke(int[][] grid, int row, int col, LinkedList<NodeDfs> path) {

        currDistance += grid[row][col];

        /**
         * 1/2 - Add here.
         */
        path.add(new NodeDfs(grid[row][col]));

        /**
         * Backout if distance is already larger than minPath.
         */
        if (minPath > 0 && currDistance > minPath) {
            return;
        }

        if (row == grid.length - 1 && col == grid[0].length - 1) {
            minPath = currDistance;

            // log.debug("minPath: {}, currDistance: {}, Path: {}", () -> minPath, () -> currDistance, () -> path);

            return;
        } else {

            if (row + 1 < grid.length) {
                backtrackDfsLuke(grid, row + 1, col, path);
                currDistance -= grid[row + 1][col];

                /**
                 * 2/2 - Delete here.
                 */
                path.removeLast();
            }

            if (col + 1 < grid[0].length) {
                backtrackDfsLuke(grid, row, col + 1, path);
                currDistance -= grid[row][col + 1];

                /**
                 * 2/2 - Delete here.
                 */
                path.removeLast();
            }
        }
    }

    int minPath = 0;
    int currDistance = 0;

    record NodeDfs(int val) {
    }

    record NodeBfs(int row, int col, int val, int dist, NodeBfs last) {
    }

    /**
     * LC - brute force - Start
     *
     * Time: O(2 ^ (m * n)) - every node we have at most two options.
     * Space: O(m + n) - Recursion depth of (m + n)
     */
    public int calculateLcBrute(int[][] grid, int i, int j) {
        if (i == grid.length || j == grid[0].length)
            return Integer.MAX_VALUE;
        if (i == grid.length - 1 && j == grid[0].length - 1)
            return grid[i][j];
        return grid[i][j] + Math.min(calculateLcBrute(grid, i + 1, j), calculateLcBrute(grid, i, j + 1));
    }

    public int minPathSumLcBrute(int[][] grid) {
        return calculateLcBrute(grid, 0, 0);
    }

    /**
     * LC - brute force - End
     */
    //////////

    /**
     * LC - DP 2D - Start
     *
     * Time: O(m * n) - We traverse the entire matrix once.
     * Space: O(m * n) - Another matrix of the same size is used.
     */
    public int minPathSumDp2D(int[][] grid) {

        int[][] dp = new int[grid.length][grid[0].length];

        for (int row = grid.length - 1; row >= 0; row--) {

            for (int col = grid[0].length - 1; col >= 0; col--) {

                if (row == grid.length - 1 && col != grid[0].length - 1)
                    dp[row][col] = grid[row][col] + dp[row][col + 1];
                else if (col == grid[0].length - 1 && row != grid.length - 1)
                    dp[row][col] = grid[row][col] + dp[row + 1][col];
                else if (col != grid[0].length - 1 && row != grid.length - 1)
                    dp[row][col] = grid[row][col] + Math.min(dp[row + 1][col], dp[row][col + 1]);
                else
                    dp[row][col] = grid[row][col];
            }
        }
        return dp[0][0];
    }

    /**
     * LC - DP 2D - End
     */
    ////////////

    /**
     * LC - DP 1D Bottom-up - Start
     *
     * Time: O(m * n) - We traverse the entire matrix once.
     * Space: O(n) - Another array of row size is used.
     */
    public int minPathSumLcDp1D(int[][] grid) {
        int[] dp = new int[grid[0].length];
        for (int row = grid.length - 1; row >= 0; row--) {
            for (int col = grid[0].length - 1; col >= 0; col--) {
                if (row == grid.length - 1 && col != grid[0].length - 1)
                    dp[col] = grid[row][col] + dp[col + 1];
                else if (col == grid[0].length - 1 && row != grid.length - 1)
                    dp[col] = grid[row][col] + dp[col];
                else if (col != grid[0].length - 1 && row != grid.length - 1)
                    dp[col] = grid[row][col] + Math.min(dp[col], dp[col + 1]);
                else
                    dp[col] = grid[row][col];
            }
        }
        return dp[0];
    }

    /**
     * LC - DP 1D - End
     */
    ///////////

    /**
     * LC DP Bottom-up - No Exstra Space
     *
     * Time: O(m * n) - We traverse the entire matrix once.
     * Space: O(1) - No extra space is used.
     */
    public int minPathSumLcDpNoExtraSpace(int[][] grid) {

        int m = grid.length, n = grid[0].length;

        // Init col = 0
        for (int row = 1; row < m; ++row) {
            grid[row][0] += grid[row - 1][0];
        }

        // Init row = 0
        for (int col = 1; col < n; ++col) {
            grid[0][col] += grid[0][col - 1];
        }

        for (int row = 1; row < m; ++row) {
            for (int col = 1; col < n; ++col) {
                grid[row][col] += Math.min(grid[row - 1][col], grid[row][col - 1]);
            }
        }

        return grid[m - 1][n - 1];
    }
}
