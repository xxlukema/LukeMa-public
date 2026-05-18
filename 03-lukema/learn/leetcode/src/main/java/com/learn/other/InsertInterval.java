package com.learn.other;


import java.util.ArrayList;
import java.util.List;

import lombok.extern.log4j.Log4j2;


@Log4j2
public class InsertInterval {

    public static void main(String[] args) {

        // int[][] nums = { { 1, 3 }, { 6, 9 } };
        // int[] newInterval = { 2, 5 };

        int[][] nums = { { 1, 2 }, { 3, 5 }, { 6, 7 }, { 8, 10 }, { 12, 16 } };
        int[] newInterval = { 4, 8 };

        InsertInterval insertInterval = new InsertInterval();
        var result = insertInterval.insert(nums, newInterval);

        log.info("insert Luke: {}", () -> result);
    }

    /**
     * Runtime: 2 ms, faster than 78.97% of Java online submissions for Insert Interval.
     * Memory Usage: 47.6 MB, less than 60.94% of Java online submissions for Insert Interval.
     * 
     * Time: O(n)
     * Space: O(n)
     */
    public int[][] insert(int[][] nums, int[] newInterval) {

        // List<int[]> numsList = Arrays.asList(nums);
        // Collections.binarySearch(numsList, int[].class, (a, b) -> a[0] - b[0]);

        List<int[]> result = new ArrayList<>();
        result.add(newInterval);

        for (int[] row : nums) {

            if (newInterval[0] > row[1]) {
                result.add(result.size() - 1, row);
            } else if (newInterval[1] < row[0]) {
                result.add(row);
            } else {
                newInterval[0] = Math.min(newInterval[0], row[0]);
                newInterval[1] = Math.max(newInterval[1], row[1]);
            }
        }

        return result.toArray(new int[0][]);
    }
}
