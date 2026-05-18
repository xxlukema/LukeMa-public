package com.learn.other;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.Assertions;

import com.learn.graph.dsu.DisjointSetUnionIntArr;
import com.learn.graph.dsu.DisjointSetUnionMap;
import com.learn.graph.dsu.UnionFindLcArray;

import lombok.extern.log4j.Log4j2;


/**
 * LC-305 Number of Islands II
 *
 * Hard
 *
 * You are given an empty 2D binary grid grid of size m x n. The grid represents a map where 0's represent water and 1's represent land.
 * Initially, all the cells of grid are water cells (i.e., all the cells are 0's).
 *
 * We may perform an add land operation which turns the water at position into a land. You are given an array positions where
 * positions[i] = [ri, ci] is the position (ri, ci) at which we should operate the ith operation.
 *
 * Return an array of integers answer where answer[i] is the number of islands after turning the cell (ri, ci) into a land.
 *
 * An island is surrounded by water and is formed by connecting adjacent lands horizontally or vertically. You may assume all four edges of
 * the grid are all surrounded by water.
 *
 * Example 1:
 * Input: m = 3, n = 3, positions = [[0,0],[0,1],[1,2],[2,1]]
 * Output: [1,1,2,3]
 * Explanation:
 * Initially, the 2d grid is filled with water.
 * - Operation #1: addLand(0, 0) turns the water at grid[0][0] into a land. We have 1 island.
 * - Operation #2: addLand(0, 1) turns the water at grid[0][1] into a land. We still have 1 island.
 * - Operation #3: addLand(1, 2) turns the water at grid[1][2] into a land. We have 2 islands.
 * - Operation #4: addLand(2, 1) turns the water at grid[2][1] into a land. We have 3 islands.
 *
 * Example 2:
 * Input: m = 1, n = 1, positions = [[0,0]]
 * Output: [1]
 *
 * Constraints:
 *     1 <= m, n, positions.length <= 104
 *     1 <= m * n <= 104
 *     positions[i].length == 2
 *     0 <= ri < m
 *     0 <= ci < n
 *
 * Follow up: Could you solve it in time complexity O(k log(mn)), where k == positions.length?
 */
@Log4j2
public class NumberOfIslandsII {

    public static void main(String[] args) {

        /**
         * Expected: [1,1,2,3]
         */
        final int m = 3, n = 3;
        /*
        final int[][] positions = {
                { 0, 0 },
                { 0, 1 },
                { 1, 2 },
                { 2, 1 } };
        */
        /*
        final int[][] positions = {
                { 0, 0 },
                { 0, 1 },
                { 1, 2 },
                { 1, 2 } };
        */

        final int[][] positions = {
                { 0, 1 },
                { 1, 2 },
                { 2, 1 },
                { 1, 0 },
                { 0, 2 },
                { 0, 0 },
                { 1, 1 } };

        NumberOfIslandsII numberOfIslandsII = new NumberOfIslandsII();

        var numIslands2Naive = numberOfIslandsII.numIslands2Naive(m, n, positions);
        log.debug("Number of Islands II: {}", () -> numIslands2Naive);
        log.debug("Number of Islands II {} OK", () -> "numIslands2Naive");

        var numIslands2LukeDisjointSetUnion = numberOfIslandsII.numIslands2LukeDisjointSetUnion(m, n, positions);
        log.debug("Number of Islands II: {}", () -> numIslands2LukeDisjointSetUnion);
        Assertions.assertTrue(numIslands2Naive.equals(numIslands2LukeDisjointSetUnion));
        log.debug("Number of Islands II {} OK", () -> "numIslands2LukeDisjointSetUnion");

        var numIslands2LukeDisjointSetUnionImproved = numberOfIslandsII.numIslands2LukeDisjointSetUnionImproved(m, n, positions);
        log.debug("Number of Islands II: {}", () -> numIslands2LukeDisjointSetUnionImproved);
        Assertions.assertTrue(numIslands2Naive.equals(numIslands2LukeDisjointSetUnionImproved));
        log.debug("Number of Islands II {} OK", () -> "numIslands2LukeDisjointSetUnionImproved");

        var numIslands2LC = numberOfIslandsII.numIslands2LC(m, n, positions);
        log.debug("Number of Islands II: {}", () -> numIslands2LC);
        Assertions.assertTrue(numIslands2Naive.equals(numIslands2LC));
        log.debug("Number of Islands II {} OK", () -> "numIslands2LC");

        var numIslands2LukeDisjointSetUionArr = numberOfIslandsII.numIslands2LukeDisjointSetUionArr(m, n, positions);
        log.debug("Number of Islands II: {}", () -> numIslands2LukeDisjointSetUionArr);
        Assertions.assertTrue(numIslands2Naive.equals(numIslands2LukeDisjointSetUionArr));
        log.debug("Number of Islands II {} OK", () -> "numIslands2LukeDisjointSetUionArr");

    }

    /**
     * Luke - Use Luke's DisjointSetUionArr
     *        Trick 1: Union Find
     *        Trick 2: Convert 2D array to 1D array
     *        Trick 5: For Luke's version of `DisjointSetUnionIntArr`, **Compress** inside union makes runtime 20x faster than **without compress**
     *
     * (without union compress)
     * Runtime: 284 ms Beats 6.43%
     * Memory: 63.5 MB Beats 25.55%
     * vs
     * (with uion compress)
     * Runtime: 18 ms Beats 55.49%
     * Memory: 63.5 MB Beats 25.55%
     * vs
     * (with rank)
     * Runtime: 11 ms Beats 75.89%
     * Memory: 45.5 MB Beats 95.18%
     *
     * Time: O(M * N + L) --- L: length of positions
     * Space: O(L) --- L: length of positions
     */
    public List<Integer> numIslands2LukeDisjointSetUionArr(int m, int n, int[][] positions) {
        final int ROWS = m;
        final int COLS = n;
        final int LEN = ROWS * COLS;

        final int[] matrix = new int[LEN];

        final List<Integer> result = new ArrayList<>();
        final DisjointSetUnionIntArr duf = new DisjointSetUnionIntArr(LEN);

        // top, bottom, left, right
        final int[] dX = { -1, +1, 0, 0 };
        final int[] dY = { 0, 0, -1, +1 };

        for (int i = 0; i < positions.length; i++) {
            int r = positions[i][0];
            int c = positions[i][1];

            int idx = r * COLS + c;

            /**
             * if the cell is already filled with `1`
             */
            if (matrix[idx] == 1) {
                result.add(duf.getSize());
                continue;
            }

            matrix[idx] = 1;
            duf.add(idx);

            for (int k = 0; k < 4; k++) {
                int neighborRow = r + dX[k];
                int neighborCol = c + dY[k];

                if (neighborRow < 0 || neighborRow >= ROWS || neighborCol < 0 || neighborCol >= COLS) {
                    continue;
                }

                int neighborIdx = neighborRow * COLS + neighborCol;

                if (matrix[neighborIdx] == 1) {
                    duf.uion(neighborIdx, idx);

                }
            }

            result.add(duf.getSize());
        }

        return result;
    }

    /**
     * LC - Trick 1: Union Find
     *      Trick 2: Convert 2D array to 1D array
     *
     * Runtime: 11 ms Beats 75.89%
     * Memory: 45.5 MB Beats 95.18%
     *
     * Time: O(M * N + L) --- L: length of positions
     * Space: O(L) --- L: length of positions
     */
    public List<Integer> numIslands2LC(int m, int n, int[][] positions) {
        final int ROWS = m;
        final int COLS = n;

        final List<Integer> result = new ArrayList<>();
        final UnionFindLcArray uf = new UnionFindLcArray(ROWS * COLS);

        /**
         * top, bottom, left, right
         */
        final int[] dx = { 0, 0, -1, 1 };
        final int[] dy = { -1, 1, 0, 0 };

        for (int[] pos : positions) {
            int row = pos[0];
            int col = pos[1];

            int cur = row * COLS + col;

            if (uf.isFilled(cur)) {
                result.add(uf.getCount());
                continue;
            } else {
                List<Integer> neighbors = new ArrayList<>();

                for (int k = 0; k < 4; k++) {
                    int r = row + dx[k];
                    int c = col + dy[k];

                    if (r < 0 || c < 0 || r >= ROWS || c >= COLS) {
                        continue;
                    }

                    /**
                     * convert from 2D position to 1D idx
                     */
                    int idx = r * COLS + c;

                    if (!uf.isFilled(idx)) {
                        continue;
                    }

                    neighbors.add(idx);
                }

                uf.add(cur);
                for (int neighbor : neighbors) {
                    uf.union(neighbor, cur);
                }

                result.add(uf.getCount());
                continue;
            }
        }

        return result;
    }

    /**
     * Luke - DisjointSet Uion Find - Improved
     *
     * Runtime: 365 ms Beats 5.13%
     * Memory: 52.2 MB Beats 63.61%
     *
     * Time: O(M * N + L) --- L: length of positions
     * Space: O(L) --- L: length of positions
     */
    public List<Integer> numIslands2LukeDisjointSetUnionImproved(int m, int n, int[][] positions) {
        final int ROWS = m;
        final int COLS = n;

        final int[][] matrix = new int[ROWS][COLS];
        final DisjointSetUnionMap<Cell> dsu = new DisjointSetUnionMap<>();

        final List<Integer> result = new ArrayList<>();

        /**
         * top, bottom, left, right
         */
        final int[] dx = { 0, 0, -1, 1 };
        final int[] dy = { -1, 1, 0, 0 };

        for (int i = 0; i < positions.length; i++) {

            int row = positions[i][0];
            int col = positions[i][1];

            if (matrix[row][col] != 0) {
                result.add(dsu.size());
                continue;
            }

            matrix[row][col] = 1;

            Cell cur = new Cell(row, col);
            dsu.add(cur);

            /**
             * Trick: cur is small and new. Therefore, it is the second param for uion(). top/bottom/left/right are existing nodes of previous steps.
             * Therefore, they are the first param for uion().
             */
            for (int k = 0; k < 4; k++) {
                int r = row + dx[k];
                int c = col + dy[k];

                if (r < 0 || c < 0 || r >= ROWS || c >= COLS) {
                    continue;
                }

                if (matrix[r][c] == 0) {
                    continue;
                }

                Cell neighbor = new Cell(r, c);
                dsu.union(neighbor, cur);
            }

            result.add(dsu.size());
        }

        return result;
    }

    /**
     * Luke - DisjointSet Uion Find
     *
     * Runtime: 345 ms Beats 5.13%
     * Memory: 52.3 MB Beats 63.61%
     *
     * Time: O(M * N + L) --- L: length of positions
     * Space: O(L) --- L: length of positions
     */
    public List<Integer> numIslands2LukeDisjointSetUnion(int m, int n, int[][] positions) {
        final int ROWS = m;
        final int COLS = n;

        final int[][] matrix = new int[ROWS][COLS];
        final DisjointSetUnionMap<Cell> dsu = new DisjointSetUnionMap<>();

        final List<Integer> result = new ArrayList<>();

        int numberIslands = 0;

        for (int i = 0; i < positions.length; i++) {

            int row = positions[i][0];
            int col = positions[i][1];

            if (matrix[row][col] != 0) {
                result.add(numberIslands);
                continue;
            }

            matrix[row][col] = 1;

            numberIslands++;

            Cell cur = new Cell(row, col);
            dsu.add(cur);

            /**
             * Trick: cur is small and new. Therefore, it is the second param for uion(). top/bottom/left/right are existing nodes of previous steps.
             * Therefore, they are the first param for uion().
             */
            // top
            if (row - 1 >= 0 && matrix[row - 1][col] == 1) {
                Cell top = new Cell(row - 1, col);
                boolean success = dsu.union(top, cur);
                if (success) {
                    numberIslands--;
                }
            }
            // bottom
            if (row + 1 < ROWS && matrix[row + 1][col] == 1) {
                Cell bottom = new Cell(row + 1, col);
                boolean success = dsu.union(bottom, cur);
                if (success) {
                    numberIslands--;
                }
            }
            // left
            if (col - 1 >= 0 && matrix[row][col - 1] == 1) {
                Cell left = new Cell(row, col - 1);
                boolean success = dsu.union(left, cur);
                if (success) {
                    numberIslands--;
                }
            }
            // right
            if (col + 1 < COLS && matrix[row][col + 1] == 1) {
                Cell right = new Cell(row, col + 1);
                boolean success = dsu.union(right, cur);
                if (success) {
                    numberIslands--;
                }
            }

            result.add(numberIslands);

            // DpUtils.print(matrix);
            // log.debug("numberIslands: {}", numberIslands);
        }

        return result;
    }

    /**
     * @see #numIslands2LukeDisjointSetUnion()
     */
    public record Cell(int row, int col) {
    }

    /**
     * Luke - Naive Improvement:
     *        LC uses Disjoint Set (Uion Find). The Disjoint Set idea can be applied to improve Naive:
     *        (1) Instead of assign a new position an incrementing counter, re-use existing count if it is connected to
     *            an existing cell, so that do not have to call `dfsMarkVisited` all the way down the path of connected cells.
     *        (2) If a new position connects to multiple exisiting cells, update all the cell counters on the path.
     *        (3) If a new position has no connections to other cells, increment the counter and mark the cell.
     *        (4) This will avoid repeated visiting the path.
     *
     * Time: O(L) --- L: positions.length
     * Space: O(L) --- L: positios.length
     */
    // public List<Integer> numIslands2NaiveImproved(int m, int n, int[][] positions) {}

    /**
     * Luke - Naive
     *
     * Runtime: 1248 ms Beats 5.13%
     * Memory: 45.9 MB Beats 84.14%
     *
     * Time: O(L ^ 2) --- L: positions.length
     * Space: O(L) --- L: positios.length
     */
    public List<Integer> numIslands2Naive(int m, int n, int[][] positions) {
        final int[][] matrix = new int[m][n];
        final List<Integer> result = new ArrayList<>();
        final AtomicInteger counter = new AtomicInteger();

        for (int i = 0; i < positions.length; i++) {
            bfsNaive(matrix, positions[i], counter, result);
        }

        return result;
    }

    int numIslands = 0;

    private void bfsNaive(int[][] matrix, int[] position, AtomicInteger counter, List<Integer> result) {

        if (result.size() == 0) {
            int count = counter.incrementAndGet();
            numIslands = 1;
            matrix[position[0]][position[1]] = count;

            result.add(1);
            return;
        }

        if (matrix[position[0]][position[1]] == 0) {
            int count = counter.incrementAndGet();
            numIslands++;
            matrix[position[0]][position[1]] = count;

            // top
            visitNaive(matrix, count, position[0] - 1, position[1]);
            // bottom
            visitNaive(matrix, count, position[0] + 1, position[1]);
            // left
            visitNaive(matrix, count, position[0], position[1] - 1);
            // right
            visitNaive(matrix, count, position[0], position[1] + 1);

            result.add(numIslands);
            return;
        } else {
            result.add(numIslands);
            return;
        }
    }

    private void visitNaive(int[][] matrix, int count, int row, int col) {
        if (row < 0 || col < 0 || row >= matrix.length || col >= matrix[0].length) {
            return;
        }

        if (matrix[row][col] == 0 || matrix[row][col] == count) {
            return;
        }

        numIslands--;

        dfsMarkVisitedNaive(matrix, count, row, col);
    }

    /**
     * Time: O(M * N) <=== It is actually O(L), where L = positions.length
     * Space: O(M * N) <=== It is actually O(L), where L = positions.length
     */
    private void dfsMarkVisitedNaive(int[][] matrix, int count, int row, int col) {
        if (row < 0 || col < 0 || row >= matrix.length || col >= matrix[0].length) {
            return;
        }

        if (matrix[row][col] == 0 || matrix[row][col] == count) {
            return;
        }

        matrix[row][col] = count;

        // top
        dfsMarkVisitedNaive(matrix, count, row - 1, col);
        // bottom
        dfsMarkVisitedNaive(matrix, count, row + 1, col);
        // left
        dfsMarkVisitedNaive(matrix, count, row, col - 1);
        // right
        dfsMarkVisitedNaive(matrix, count, row, col + 1);
    }

}
