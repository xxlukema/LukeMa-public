package com.learn.dp;


import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;

import lombok.extern.log4j.Log4j2;


/**
 * LC - 119 - Pascal's Triangle II
 * 
 * Easy
 * 
 * Given an integer rowIndex, return the rowIndexth (0-indexed) row of the Pascal's triangle.
 * In Pascal's triangle, each number is the sum of the two numbers directly above it as shown:
 */
@Log4j2
public class PascalsTriangleII {

    public static void main(String[] args) {

        int rowIndex = 3;

        PascalsTriangleII pascalsTriangleII = new PascalsTriangleII();

        log.debug(() -> "Start Test...");

        var retTwoRows = pascalsTriangleII.getRowTwoRows(rowIndex);
        log.debug("Pascal's Triangle II: {}", () -> retTwoRows);
        log.debug(() -> "Two Rows OK");

        var retOneRowSet = pascalsTriangleII.getRowOneRowSet(rowIndex);
        Assertions.assertEquals(retTwoRows, retOneRowSet);
        log.debug(() -> "One Row set() OK");

        var retOneRowAddRemove = pascalsTriangleII.getRowOneRowAddRemove(rowIndex);
        Assertions.assertEquals(retTwoRows, retOneRowAddRemove);
        log.debug(() -> "One Row add() remove() OK");

        var retMath = pascalsTriangleII.getRowLcMath(rowIndex);
        Assertions.assertEquals(retTwoRows, retMath);
        log.debug(() -> "LC Math OK");

        var retDp = pascalsTriangleII.getRowLcMath(rowIndex);
        Assertions.assertEquals(retTwoRows, retDp);
        log.debug(() -> "LC DP OK");

        var retOneRowLukeDp = pascalsTriangleII.getRowOneRowDp(rowIndex);
        Assertions.assertEquals(retTwoRows, retOneRowLukeDp);
        log.debug(() -> "Luke One Row DP OK");

        log.debug(() -> "Test Complete");
    }

    /**
     * Luke - Two Rows
     * 
     * Runtime: 1 ms, faster than 92.04% of Java online submissions for Pascal's Triangle II.
     * Memory Usage: 41.9 MB, less than 45.40% of Java online submissions for Pascal's Triangle II.
     * 
     * Time: O(N ^ 2)
     * Space: O(N)
     */
    public List<Integer> getRowTwoRows(int rowIndex) {
        List<Integer> last = new ArrayList<>();
        last.add(1);
        if (rowIndex == 0) {
            return last;
        }

        last.add(1);
        if (rowIndex == 1) {
            return last;
        }

        List<Integer> curr = null;

        for (int row = 2; row <= rowIndex; row++) {
            curr = new ArrayList<>();
            curr.add(1);
            for (int col = 1; col < row; col++) {
                curr.add(last.get(col - 1) + last.get(col));
            }
            curr.add(1);
            last = curr;
        }

        return curr;
    }

    /**
     * Luke - One Row
     * 
     * Runtime: 1 ms, faster than 92.04% of Java online submissions for Pascal's Triangle II.
     * Memory Usage: 39.8 MB, less than 97.39% of Java online submissions for Pascal's Triangle II.
     * 
     * Time: O(N ^ 2)
     * Space: O(1)
     */
    public List<Integer> getRowOneRowSet(int rowIndex) {
        List<Integer> result = new ArrayList<>();
        result.add(1);
        if (rowIndex == 0) {
            return result;
        }

        result.add(1);
        if (rowIndex == 1) {
            return result;
        }

        for (int row = 2; row <= rowIndex; row++) {
            int pre1 = result.get(0);
            int pre2 = result.get(1);
            for (int col = 1; col < row; col++) {
                pre2 = result.get(col);
                result.set(col, pre1 + pre2);
                pre1 = pre2;
            }
            result.add(1);
        }

        return result;
    }

    /**
     * Luke - One Row
     * 
     * Runtime: 2 ms, faster than 58.92% of Java online submissions for Pascal's Triangle II.
     * Memory Usage: 41.9 MB, less than 35.82% of Java online submissions for Pascal's Triangle II.
     * 
     * Time: O(N ^ 2)
     * Space: O(1)
     */
    public List<Integer> getRowOneRowAddRemove(int rowIndex) {
        List<Integer> result = new ArrayList<>();
        result.add(1);
        if (rowIndex == 0) {
            return result;
        }

        result.add(1);
        if (rowIndex == 1) {
            return result;
        }

        for (int row = 2; row <= rowIndex; row++) {
            int pre1 = result.get(0);
            int pre2 = result.get(1);
            for (int col = 1; col < row; col++) {
                pre2 = result.remove(col);
                result.add(col, pre1 + pre2);
                pre1 = pre2;
            }
            result.add(1);
        }

        return result;
    }

    /**
     * Luke - One Row - DP
     * 
     * Runtime: 2 ms, faster than 58.92% of Java online submissions for Pascal's Triangle II.
     * Memory Usage: 41.6 MB, less than 69.53% of Java online submissions for Pascal's Triangle II.
     * 
     * Time: O(N ^ 2)
     * Space: O(1)
     */
    public List<Integer> getRowOneRowDp(int rowIndex) {
        List<Integer> result = new ArrayList<>() {
            {
                add(1);
            }
        };

        for (int row = 1; row <= rowIndex; row++) {
            result.add(0, 1);
            for (int col = 1; col < row; col++) {
                result.set(col, result.get(col) + result.get(col + 1));
            }
        }

        return result;
    }

    /**
     * LC - DP
     * 
     * Runtime: 1 ms, faster than 92.04% of Java online submissions for Pascal's Triangle II.
     * Memory Usage: 42.1 MB, less than 27.02% of Java online submissions for Pascal's Triangle II.
     * 
     * Time: O(N)
     * Space: O(1)
     */
    public List<Integer> getRowLcDp(int rowIndex) {
        List<Integer> row = new ArrayList<>() {
            {
                add(1);
            }
        };

        for (int r = 0; r < rowIndex; r++) {
            for (int c = r; c > 0; c--) {
                row.set(c, row.get(c) + row.get(c - 1));
            }
            row.add(1);
        }

        return row;
    }

    /**
     * LC - Math
     * 
     * Runtime: 1 ms, faster than 92.04% of Java online submissions for Pascal's Triangle II.
     * Memory Usage: 41.8 MB, less than 54.71% of Java online submissions for Pascal's Triangle II.
     * 
     * Time: O(N)
     * Space: O(1)
     */
    public List<Integer> getRowLcMath(int n) {
        List<Integer> row = new ArrayList<>() {
            {
                add(1);
            }
        };

        for (int k = 1; k <= n; k++) {
            row.add((int) ((row.get(row.size() - 1) * (long) (n - k + 1)) / k));
        }

        return row;
    }
}
