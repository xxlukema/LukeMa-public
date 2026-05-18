package com.learn.graph;

public class ObstacleGraid {
    
}

/**
 * https://leetcode.com/problems/unique-paths-ii/solution/
 */
class GraidWalkSolution {
    public int uniquePathsWithObstacles2(int[][] obstacleGrid) {

        int R = obstacleGrid.length;
        int C = obstacleGrid[0].length;

        // If the starting cell has an obstacle, then simply return as there would be
        // no paths to the destination.
        if (obstacleGrid[0][0] == 1) {
            return 0;
        }

        // Number of ways of reaching the starting cell = 1.
        obstacleGrid[0][0] = 1;

        // Filling the values for the first column
        for (int i = 1; i < R; i++) {
            obstacleGrid[i][0] = (obstacleGrid[i][0] == 0 && obstacleGrid[i - 1][0] == 1) ? 1 : 0;
        }

        // Filling the values for the first row
        for (int i = 1; i < C; i++) {
            obstacleGrid[0][i] = (obstacleGrid[0][i] == 0 && obstacleGrid[0][i - 1] == 1) ? 1 : 0;
        }

        // Starting from cell(1,1) fill up the values
        // No. of ways of reaching cell[i][j] = cell[i - 1][j] + cell[i][j - 1]
        // i.e. From above and left.
        for (int i = 1; i < R; i++) {
            for (int j = 1; j < C; j++) {
                if (obstacleGrid[i][j] == 0) {
                    obstacleGrid[i][j] = obstacleGrid[i - 1][j] + obstacleGrid[i][j - 1];
                } else {
                    obstacleGrid[i][j] = 0;
                }
            }
        }

        // Return value stored in rightmost bottommost cell. That is the destination.
        return obstacleGrid[R - 1][C - 1];
    }

    public int uniquePathsWithObstacles(int[][] grid) {
        final int ROWS = grid.length;
        final int COLS = grid[0].length;
        
        /**
         * If the starting cell has an obstacle, then simply return as there would be no paths to the destination.
         */
        if(grid[0][0] == 1) {
            return 0;
        }
        
        /**
         * If the destination cell has an obstacle, then simply return as there would be no paths to the destination.
         */
        if(grid[ROWS - 1][COLS - 1] == 1) {
            return 0;
        }
        
        /**
         * use grid values to record number of paths. init top left node value
         */
        grid[0][0] = 1;
        
        // for first row, fill value for each column
        for(int i=1; i<COLS; i++) {
            grid[0][i] = (grid[0][i-1] == 1 && grid[0][i] == 0) ? 1 : 0;
        }
        
        // for first column, fill value for each row
        for(int i=1; i<ROWS; i++) {
            grid[i][0] = (grid[i-1][0] == 1 && grid[i][0] == 0) ? 1 : 0;
        }
        
        for(int i=1; i<ROWS; i++) {
            for (int k=1; k<COLS; k++) {
                if(grid[i][k] == 1) {
                    grid[i][k] = 0;
                } else {
                    grid[i][k] = grid[i-1][k] + grid[i][k-1];
                }
            }
        }
        
        return grid[ROWS-1][COLS-1];
    }
}