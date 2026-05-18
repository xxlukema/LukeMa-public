package com.learn.dp;


import lombok.extern.log4j.Log4j2;


/**
 * LC - 200 - Number of Islands
 * 
 * Medium
 * 
 * Given an m x n 2D binary grid grid which represents a map of '1's (land) and '0's (water), return the number of islands.
 * 
 * An island is surrounded by water and is formed by connecting adjacent lands horizontally or vertically. You may assume all four edges of the grid are all surrounded by water.
 * 
 * Example 1:
 * Input: grid = [
 *   ["1","1","1","1","0"],
 *   ["1","1","0","1","0"],
 *   ["1","1","0","0","0"],
 *   ["0","0","0","0","0"]
 * ]
 * Output: 1
 * 
 * Example 2:
 * Input: grid = [
 *   ["1","1","0","0","0"],
 *   ["1","1","0","0","0"],
 *   ["0","0","1","0","0"],
 *   ["0","0","0","1","1"]
 * ]
 * Output: 3
 * 
 * Constraints:
 *     m == grid.length
 *     n == grid[i].length
 *     1 <= m, n <= 300
 *     grid[i][j] is '0' or '1'.
 */
@Log4j2
public class NumberOfIslands {

  public static void main(String[] args)

  {

    /**
     * Output: 1
     */
    /*
    final char[][] grid = {
        { '1', '1', '1', '1', '0' },
        { '1', '1', '0', '1', '0' },
        { '1', '1', '0', '0', '0' },
        { '0', '0', '0', '0', '0' }
    };
    */

    /**
     * Output: 3
     */
    /*
    final char[][] grid = {
        { '1', '1', '0', '0', '0' },
        { '1', '1', '0', '0', '0' },
        { '0', '0', '1', '0', '0' },
        { '0', '0', '0', '1', '1' }
    };
    */

    /**
     * Output: 2
     */
    final char[][] grid = {
        { '1', '0', '1', '1', '1' },
        { '1', '0', '1', '0', '1' },
        { '1', '1', '1', '0', '1' }
    };

    NumberOfIslands numberOfIslands = new NumberOfIslands();

    var ret = numberOfIslands.numIslands(grid);
    log.debug("Number of islands: {}", () -> ret);
    log.debug("Number of islands {} OK", () -> "ret");

    DpUtils.print(grid);
  }

  /**
   * Luke - DP memo - In-Place change connecting char '1's to 'a', 'b' , 'c', ... for each connected island.
   *                - LC DFS solution: change connecting char '1's to '0's.
   * 
   * Runtime: 8 ms, faster than 25.70% of Java online submissions for Number of Islands.
   * Memory Usage: 57.2 MB, less than 64.05% of Java online submissions for Number of Islands.
   * 
   * Time: O(M * N)
   * Space: O(1)
   */

  int currMarker = 0;

  public int numIslands(final char[][] grid) {

    /**
     * 1. Change all connecting 1s to 2s, 3s, ...
     */
    final int ROWS = grid.length;
    final int COLS = grid[0].length;

    for (int r = 0; r < ROWS; r++) {
      for (int c = 0; c < COLS; c++) {
        if (grid[r][c] == '1') {
          ++currMarker;
          char marker = (char) ('a' - 1 + currMarker);
          markConnected(grid, r, c, marker, ROWS, COLS);
        }
      }
    }

    return currMarker;
  }

  private void markConnected(final char[][] grid, int row, int col, final char marker, final int ROWS, final int COLS) {
    grid[row][col] = marker;
    if (col - 1 >= 0 && grid[row][col - 1] == '1') {
      markConnected(grid, row, col - 1, marker, ROWS, COLS);
    }
    if (col + 1 < COLS && grid[row][col + 1] == '1') {
      markConnected(grid, row, col + 1, marker, ROWS, COLS);
    }
    if (row - 1 >= 0 && grid[row - 1][col] == '1') {
      markConnected(grid, row - 1, col, marker, ROWS, COLS);
    }
    if (row + 1 < ROWS && grid[row + 1][col] == '1') {
      markConnected(grid, row + 1, col, marker, ROWS, COLS);
    }
  }
}
