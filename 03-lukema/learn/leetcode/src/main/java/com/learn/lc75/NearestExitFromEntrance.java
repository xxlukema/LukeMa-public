package com.learn.lc75;


import java.util.LinkedList;
import java.util.Queue;

import org.junit.jupiter.api.Assertions;

import lombok.extern.log4j.Log4j2;


/**
 * LC - 1926. Nearest Exit from Entrance in Maze
 *
 * Medium
 *
 * You are given an m x n matrix maze (0-indexed) with empty cells (represented as '.') and walls (represented as '+'). You are also
 * given the entrance of the maze, where entrance = [entrancerow, entrancecol] denotes the row and column of the cell you are initially standing at.

In one step, you can move one cell up, down, left, or right. You cannot step into a cell with a wall, and you cannot step outside the maze. Your goal
is to find the nearest exit from the entrance. An exit is defined as an empty cell that is at the border of the maze. The entrance does not count as an exit.

Return the number of steps in the shortest path from the entrance to the nearest exit, or -1 if no such path exists.

Example 1:

Input: maze = [["+","+",".","+"],[".",".",".","+"],["+","+","+","."]], entrance = [1,2]
Output: 1
Explanation: There are 3 exits in this maze at [1,0], [0,2], and [2,3].
Initially, you are at the entrance cell [1,2].
- You can reach [1,0] by moving 2 steps left.
- You can reach [0,2] by moving 1 step up.
It is impossible to reach [2,3] from the entrance.
Thus, the nearest exit is [0,2], which is 1 step away.

Example 2:

Input: maze = [["+","+","+"],[".",".","."],["+","+","+"]], entrance = [1,0]
Output: 2
Explanation: There is 1 exit in this maze at [1,2].
[1,0] does not count as an exit since it is the entrance cell.
Initially, you are at the entrance cell [1,0].
- You can reach [1,2] by moving 2 steps right.
Thus, the nearest exit is [1,2], which is 2 steps away.

Example 3:

Input: maze = [[".","+"]], entrance = [0,0]
Output: -1
Explanation: There are no exits in this maze.



Constraints:

    maze.length == m
    maze[i].length == n
    1 <= m, n <= 100
    maze[i][j] is either '.' or '+'.
    entrance.length == 2
    0 <= entrancerow < m
    0 <= entrancecol < n
    entrance will always be an empty cell.
 */

@Log4j2
public class NearestExitFromEntrance {

    public static void main(String[] args) {

        NearestExitFromEntrance nearestExitFromEntrance = new NearestExitFromEntrance();

        /*
        char[][] maze = {
                { '+', '+', '.', '+' },
                { '.', '.', '.', '+' },
                { '+', '+', '+', '.' } };
        int[] entrance = { 1, 0 };
        int expected = 3;
        */

        /*
        char[][] maze = {
                { '+', '+', '+' },
                { '.', '.', '.' },
                { '+', '+', '+' } };
        int[] entrance = { 0, 0 };
        int expected = -1;
        */

        /*
        char[][] maze = {
                { '+', '+', '.', '+' },
                { '.', '.', '.', '+' },
                { '+', '+', '+', '.' } };
        int[] entrance = { 1, 2 };
        int expected = 1;
        */

        /*
        char[][] maze = {
                { '.', '+' } };
        int[] entrance = { 1, 2 };
        int expected = 1;
        */

        char[][] maze = {
                { '.', '.' } };
        int[] entrance = { 0, 1 };
        int expected = 1;

        int retBFS = nearestExitFromEntrance.nearestExitBFS(maze, entrance);
        log.debug("Nearest Exit: {}", () -> retBFS);
        Assertions.assertEquals(expected, retBFS);
        log.debug("Nearest Exit: {} OK", () -> "nearestExit");

    }

    /**
     * Luke - BFS
     *
     * Time: O(ROWS * COLS)
     * Space: O(ROWS * COLS)
     *
     * Runtime: 9ms Beats 36.32%
     * Memory: 44.86mb Beats 37.92%
     *
     * Runtime: 10ms Beats 28.83%
     * Memory: 44.46mb Beats 78.86%
     */
    public int nearestExitBFS(char[][] maze, int[] entrance) {

        if (maze.length < 1 || maze[0].length < 1) {
            return -1;
        }

        if (maze[entrance[0]][entrance[1]] != '.') {
            return -1;
        }

        int ROWS = maze.length;
        int COLS = maze[0].length;

        Queue<int[]> queue = new LinkedList<>();

        queue.add(entrance);
        /**
         * Trick: Mark the cell `visited` as soon as enque it.
         */
        maze[entrance[0]][entrance[1]] = 'V';

        int steps = 0;

        while (!queue.isEmpty()) {
            for (int i = 0, len = queue.size(); i < len; i++) {
                int[] pos = queue.poll();

                if (isEdge(pos, ROWS, COLS) && steps > 0) {
                    return steps;
                }

                /**
                 * mark current pos as visited 'V'
                 *
                 * Too late! Correct: Mark it as visited as soon as put the pos into queue.
                 */
                // maze[pos[0]][pos[1]] = 'V';

                int[] top = { pos[0] - 1, pos[1] };
                int[] bot = { pos[0] + 1, pos[1] };
                int[] lef = { pos[0], pos[1] - 1 };
                int[] rig = { pos[0], pos[1] + 1 };

                if (isInMaze(top, ROWS, COLS) && maze[top[0]][top[1]] == '.') {
                    queue.add(top);
                    /**
                    * Trick: Mark the cell `visited` as soon as enque it.
                    */
                    maze[top[0]][top[1]] = 'V';
                }
                if (isInMaze(bot, ROWS, COLS) && maze[bot[0]][bot[1]] == '.') {
                    queue.add(bot);
                    /**
                    * Trick: Mark the cell `visited` as soon as enque it.
                    */
                    maze[bot[0]][bot[1]] = 'V';
                }
                if (isInMaze(lef, ROWS, COLS) && maze[lef[0]][lef[1]] == '.') {
                    queue.add(lef);
                    /**
                    * Trick: Mark the cell `visited` as soon as enque it.
                    */
                    maze[lef[0]][lef[1]] = 'V';
                }
                if (isInMaze(rig, ROWS, COLS) && maze[rig[0]][rig[1]] == '.') {
                    queue.add(rig);
                    /**
                    * Trick: Mark the cell `visited` as soon as enque it.
                    */
                    maze[rig[0]][rig[1]] = 'V';
                }

                /*
                // move up
                if (pos[0] - 1 >= 0) {
                    int[] top = { pos[0] - 1, pos[1] };
                    if (maze[top[0]][top[1]] == '.') {
                        queue.add(top);
                        ////////////////////////////////////////////////////////
                        // Trick: Mark the cell `visited` as soon as enque it.
                        ////////////////////////////////////////////////////////
                        maze[top[0]][top[1]] = 'V';
                    }
                }

                // move down
                if (pos[0] + 1 <= ROWS - 1) {
                    int[] bot = { pos[0] + 1, pos[1] };
                    if (maze[bot[0]][bot[1]] == '.') {
                        queue.add(bot);
                        ////////////////////////////////////////////////////////
                        // Trick: Mark the cell `visited` as soon as enque it.
                        ////////////////////////////////////////////////////////
                        maze[bot[0]][bot[1]] = 'V';
                    }
                }

                // move left
                if (pos[1] - 1 >= 0) {
                    int[] lef = { pos[0], pos[1] - 1 };
                    if (maze[lef[0]][lef[1]] == '.') {
                        queue.add(lef);
                        ////////////////////////////////////////////////////////
                        // Trick: Mark the cell `visited` as soon as enque it.
                        ////////////////////////////////////////////////////////
                        maze[lef[0]][lef[1]] = 'V';
                    }
                }

                // move right
                if (pos[1] + 1 <= COLS - 1) {
                    int[] rig = { pos[0], pos[1] + 1 };
                    if (maze[rig[0]][rig[1]] == '.') {
                        queue.add(rig);
                        ////////////////////////////////////////////////////////
                        // Trick: Mark the cell `visited` as soon as enque it.
                        ////////////////////////////////////////////////////////
                        maze[rig[0]][rig[1]] = 'V';
                    }
                }
                */
            }

            steps++;
        }

        return -1;
    }

    boolean isEdge(int[] pos, final int ROWS, final int COLS) {
        return pos[0] == 0 || pos[1] == 0 || pos[0] == ROWS - 1 || pos[1] == COLS - 1;
    }

    boolean isInMaze(int[] pos, final int ROWS, final int COLS) {
        return pos[0] >= 0 && pos[1] >= 0 && pos[0] <= ROWS - 1 && pos[1] <= COLS - 1;
    }
}
