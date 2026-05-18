package com.learn.backtrack;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;

import lombok.extern.log4j.Log4j2;


/**
 * LC - 120 - Triangle
 *
 * Medium
 *
 * Given a triangle array, return the minimum path sum from top to bottom.
 * For each step, you may move to an adjacent number of the row below. More formally, if you are on index i on the current
 * row, you may move to either index i or index i + 1 on the next row.
 */
@Log4j2
public class Triangle {

    public static void main(String[] args) {

        final Integer[][] nums = {
                { 2 },
                { 3, 4 },
                { 6, 5, 7 },
                { 4, 1, 8, 3 } };

        List<List<Integer>> triangleData = new ArrayList<>();

        for (int row = 0; row < nums.length; row++) {
            triangleData.add(Stream.of(nums[row]).collect(Collectors.toList()));
        }

        log.debug(() -> "Start Test...");

        Triangle triangle = new Triangle();

        var retRecursion = triangle.minimumTotalRecursion(triangleData);
        log.debug("Min of triangle: {}", () -> retRecursion);
        log.debug(() -> "No Memo OK. But got Time Out Exception");

        var retDpInPalce = triangle.minimumTotalDpInpalce(triangleData);
        Assertions.assertEquals(retRecursion, retDpInPalce);
        log.debug(() -> "DP In-Place OK");

        /**
         * Rebuild triangle after in-place dp.
         */
        triangleData = new ArrayList<>();
        for (int row = 0; row < nums.length; row++) {
            triangleData.add(Stream.of(nums[row]).collect(Collectors.toList()));
        }

        var retDpBottomUpInPlace = triangle.minimumTotalDpBottomUpInplace(triangleData);
        Assertions.assertEquals(retRecursion, retDpBottomUpInPlace);
        log.debug(() -> "DP Bottom-Up In-Place OK");

        /**
         * Rebuild triangle after in-place dp.
         */

        triangleData = new ArrayList<>();
        for (int row = 0; row < nums.length; row++) {
            triangleData.add(Stream.of(nums[row]).collect(Collectors.toList()));
        }

        var retDpBottomUpExtraRow = triangle.minimumTotalDpBottomUpExtraRow(triangleData);
        Assertions.assertEquals(retRecursion, retDpBottomUpExtraRow);
        log.debug(() -> "DP Bottom-Up Extra Row OK");

        var retDpExtraRow = triangle.minimumTotalDpExtraRow(triangleData);
        Assertions.assertEquals(retRecursion, retDpExtraRow);
        log.debug(() -> "DP Extra Row OK");

        var retMemoCorrect = triangle.minimumTotalRecursionMemoCorrect(triangleData);
        Assertions.assertEquals(retRecursion, retMemoCorrect);
        log.debug(() -> "Memo Correct OK");

        var retMemoWrong = triangle.minimumTotalRecursionMemoWrong(triangleData);
        Assertions.assertEquals(retRecursion, retMemoWrong);
        log.debug(() -> "Memo Wrong OK");

        log.debug(() -> "Test Complete");
    }

    /**
     * Luke - DP - Bottom-Up - In-Place
     * 
     * Runtime: 7 ms, faster than 35.18% of Java online submissions for Triangle.
     * Memory Usage: 44.3 MB, less than 36.48% of Java online submissions for Triangle.
     * 
     * Time: O(N ^ 2)
     * Space: O(1)
     */
    public int minimumTotalDpBottomUpInplace(List<List<Integer>> triangle) {
        for (int row = triangle.size() - 2; row >= 0; row--) {
            for (int col = 0; col < triangle.get(row).size(); col++) {
                int min = Math.min(triangle.get(row + 1).get(col), triangle.get(row + 1).get(col + 1));
                triangle.get(row).set(col, min + triangle.get(row).get(col));
            }
        }
        return triangle.get(0).get(0);
    }

    /**
     * Luke - DP - Bottom-Up - Extra Row
     * 
     * Runtime: 5 ms, faster than 53.22% of Java online submissions for Triangle.
     * Memory Usage: 44.3 MB, less than 36.48% of Java online submissions for Triangle.
     * 
     * Time: O(N ^ 2)
     * Space: O(N)
     */
    public int minimumTotalDpBottomUpExtraRow(List<List<Integer>> triangle) {
        final List<Integer> sums = triangle.get(triangle.size() - 1).stream().collect(Collectors.toList());

        for (int row = triangle.size() - 2; row >= 0; row--) {
            for (int col = 0; col < triangle.get(row).size(); col++) {
                int min = Math.min(sums.get(col), sums.get(col + 1));
                sums.set(col, min + triangle.get(row).get(col));
            }
        }
        return sums.get(0);
    }

    /**
     * Luke - DP - Top-Down - Extra Row
     * 
     * Runtime: 10 ms, faster than 18.98% of Java online submissions for Triangle.
     * Memory Usage: 44 MB, less than 56.45% of Java online submissions for Triangle.
     * 
     * Time: O(N ^ 2)
     * Space: O(N)
     */
    public int minimumTotalDpExtraRow(List<List<Integer>> triangle) {
        final List<Integer> extra = new ArrayList<>();
        extra.add(triangle.get(0).get(0));
        for (int row = 1; row < triangle.size(); row++) {

            /**
             * Adding one element to the extra list, to prevent overrite the previous value.
             */
            extra.add(0, 0);

            for (int col = 0; col < triangle.get(row).size(); col++) {
                int minAbove = 0;
                if (col == 0) {
                    minAbove = extra.get(col + 1);
                } else if (col == triangle.get(row).size() - 1) {
                    minAbove = extra.get(col);
                } else {
                    minAbove = Math.min(extra.get(col), extra.get(col + 1));
                }

                extra.set(col, minAbove + triangle.get(row).get(col));
            }
        }

        // return triangle.get(triangle.size() - 1).stream().sorted().toList().get(0);
        // return triangle.get(triangle.size() - 1).stream().min((a, b) -> a - b).get();
        // return Collections.min(triangle.get(triangle.size() - 1));
        // return IntStream.of(nums).min().getAsInt();
        return Collections.min(extra);
    }

    /**
     * Luke - DP - Top-Down - In-Place
     * 
     * Runtime: 9 ms, faster than 23.13% of Java online submissions for Triangle.
     * Memory Usage: 44.5 MB, less than 25.24% of Java online submissions for Triangle.
     * 
     * Time: O(N ^ 2)
     * Space: O(1)
     */
    public int minimumTotalDpInpalce(List<List<Integer>> triangle) {
        for (int row = 1; row < triangle.size(); row++) {
            for (int col = 0; col < triangle.get(row).size(); col++) {
                int minAbove = 0;
                if (col == 0) {
                    minAbove = triangle.get(row - 1).get(col);
                } else if (col == triangle.get(row).size() - 1) {
                    minAbove = triangle.get(row - 1).get(col - 1);
                } else {
                    minAbove = Math.min(triangle.get(row - 1).get(col - 1), triangle.get(row - 1).get(col));
                }

                triangle.get(row).set(col, minAbove + triangle.get(row).get(col));
            }
        }

        // return triangle.get(triangle.size() - 1).stream().sorted().toList().get(0);
        // return triangle.get(triangle.size() - 1).stream().min((a, b) -> a - b).get();
        return Collections.min(triangle.get(triangle.size() - 1));
    }

    /**
     * Luke - Backtrack
     *
     * Time Limit Exceeded
     *
     * Time: O(2 ^ N) - 1 + 2 + 4 + ... + 2^n
     * Space: O(1)
     */
    public int minimumTotalRecursion(List<List<Integer>> triangle) {
        return backtrack(triangle, 0, 0, 0);
    }

    public int backtrack(List<List<Integer>> triangle, int row, int idx, int sum) {
        if (row >= triangle.size() || idx >= triangle.get(row).size()) {
            return sum;
        }

        sum += triangle.get(row).get(idx);

        int sum1 = backtrack(triangle, row + 1, idx, sum);
        int sum2 = backtrack(triangle, row + 1, idx + 1, sum);

        return Math.min(sum1, sum2);
    }

    /**
     * Luke - Backtrack
     *
     * Time Limit Exceeded
     *
     * Time: O(N ^ 2) - 1 + 2 + 4 + ... + 2n
     * Space: O(1)
     */
    public int minimumTotalRecursionMemoWrong(List<List<Integer>> triangle) {
        /**
         * If it is an int array, need to init elements to -1 to indicate the cell has not been visted.
         * If use List or Map, only visited cells are in the List or Map
         *
         * However, We cannot use int array because there can be nagetive numbers in cells, and the sub-sum can be anything.
         * Therefore, use List for faster access than Map.
         */
        // final int[][] memo = new int[triangle.size()][triangle.get(triangle.size() - 1).size()];

        /**
         * We cannot use int array because there can be nagetive numbers in cells, and the sub-sum can be anything.
         * Therefore, use List for faster access than Map.
         *
         * There is no need to init the memo. If the cell is visited, it will be in the List.
         *
         * However, we cannot use List because the visited cells are not continuous (sparodical/disparate).
         */
        // final List<List<Integer>> memo = new ArrayList<>();

        /**
         * Therefore, Use Map. Claim a record Cell to hold (row, col).
         */
        Map<Cell, Integer> memo = new ConcurrentHashMap<>();

        return backtrackMemoWrong(triangle, new Cell(0, 0), 0, memo);
    }

    record Cell(int row, int col) {
    }

    public int backtrackMemoWrong(List<List<Integer>> triangle, Cell cell, int sum, Map<Cell, Integer> memo) {
        if (cell.row >= triangle.size() || cell.col >= triangle.get(cell.row).size()) {
            return sum;
        }

        if (memo.containsKey(cell)) {
            return memo.get(cell);
        }

        int val = triangle.get(cell.row).get(cell.col);

        sum += val;

        Cell cell1 = new Cell(cell.row + 1, cell.col);
        Integer sum1 = backtrackMemoWrong(triangle, cell1, sum, memo);

        Cell cell2 = new Cell(cell.row + 1, cell.col + 1);
        Integer sum2 = backtrackMemoWrong(triangle, cell2, sum, memo);

        int min = Math.min(sum1.intValue(), sum2.intValue());
        memo.put(cell, min);

        // log.debug("memo: {}", memo);

        // log.debug("cell: {}, min: {}, curr: {}, sum: {}", cell, min, triangle.get(cell.row).get(cell.col), sum);

        // log.debug("pre sum: {}, curr: {}", sum, val);

        return min;
    }

    /**
     * Luke - Backtrack
     *
     * Time Limit Exceeded
     *
     * Time: O(N ^ 2) - 1 + 2 + 4 + ... + 2n
     * Space: O(1)
     */
    public int minimumTotalRecursionMemoCorrect(List<List<Integer>> triangle) {
        /**
         * If it is an int array, need to init elements to -1 to indicate the cell has not been visted.
         * If use List or Map, only visited cells are in the List or Map
         *
         * However, We cannot use int array because there can be nagetive numbers in cells, and the sub-sum can be anything.
         * Therefore, use List for faster access than Map.
         */
        // final int[][] memo = new int[triangle.size()][triangle.get(triangle.size() - 1).size()];

        /**
         * We cannot use int array because there can be nagetive numbers in cells, and the sub-sum can be anything.
         * Therefore, use List for faster access than Map.
         *
         * There is no need to init the memo. If the cell is visited, it will be in the List.
         *
         * However, we cannot use List because the visited cells are not continuous (sparodical/disparate).
         */
        // final List<List<Integer>> memo = new ArrayList<>();

        /**
         * Therefore, Use Map. Claim a record Cell to hold (row, col).
         */
        Map<Cell, Integer> memo = new ConcurrentHashMap<>();

        return backtrackMemoCorrect(triangle, new Cell(0, 0), memo);
    }

    public int backtrackMemoCorrect(List<List<Integer>> triangle, Cell cell, Map<Cell, Integer> memo) {
        if (cell.row == triangle.size() - 1) {
            return triangle.get(cell.row).get(cell.col);
        }

        if (memo.containsKey(cell)) {
            return memo.get(cell);
        }

        int val = triangle.get(cell.row).get(cell.col);

        Cell cell1 = new Cell(cell.row + 1, cell.col);
        Integer sum1 = backtrackMemoCorrect(triangle, cell1, memo);

        Cell cell2 = new Cell(cell.row + 1, cell.col + 1);
        Integer sum2 = backtrackMemoCorrect(triangle, cell2, memo);

        int min = Math.min(sum1.intValue(), sum2.intValue());

        int sum = val + min;

        memo.put(cell, sum);

        return sum;
    }

}
