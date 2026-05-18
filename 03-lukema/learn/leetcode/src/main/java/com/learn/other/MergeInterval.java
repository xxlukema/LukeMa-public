package com.learn.other;


import java.util.Arrays;
import java.util.LinkedList;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Assertions;

import lombok.extern.log4j.Log4j2;


@Log4j2
public class MergeInterval {

    public static void main(String[] args) {

        int[][] intervals1 = { { 1, 3 }, { 2, 6 }, { 8, 10 }, { 15, 18 } };
        int[][] intervals2 = { { 2, 3 }, { 4, 5 }, { 6, 7 }, { 8, 9 }, { 1, 10 } };

        MergeInterval mergeInterval = new MergeInterval();

        var ret1 = mergeInterval.mergeBruteLuke(intervals1);
        log.debug("merged Luke brute: {}", () -> ret1);

        var ret2 = mergeInterval.mergeLcSorted(intervals1);
        log.debug("merged LC sorted: {}", () -> ret2);

        Assertions.assertEquals(join(ret1), join(ret2));

        var ret3 = mergeInterval.mergeBruteLuke(intervals2);
        log.debug("merged Luke brute: {}", () -> ret3);

        var ret4 = mergeInterval.mergeLcSorted(intervals2);
        log.debug("merged LC sorted: {}", () -> ret4);

        Assertions.assertEquals(join(ret3), join(ret4));

    }

    public static String join(int[][] nums) {
        StringBuilder sb = new StringBuilder();
        for (int[] row : nums) {
            sb.append(Arrays.stream(row).boxed().map(e -> String.valueOf(e)).collect(Collectors.joining()));
        }

        return sb.toString();
    }

    /**
     * Runtime: 17 ms, faster than 26.46% of Java online submissions for Merge Intervals.
     * Memory Usage: 55.5 MB, less than 24.97% of Java online submissions for Merge Intervals.
     * LC Sorted
     * Time: O(n log n)
     */
    public int[][] mergeLcSorted(int[][] nums) {

        Arrays.sort(nums, (a, b) -> a[0] - b[0]);
        var merged = new LinkedList<int[]>();

        for (int[] row : nums) {
            /**
             * if the list of merged intervals is empty or if the current
             * interval does not overlap with the previous, simply append it.
             * otherwise, there is overlap,
             * so we merge the current and previous intervals.
             */
            if (merged.size() == 0 || merged.getLast()[1] < row[0]) {
                merged.add(row);
            } else {
                merged.getLast()[1] = Math.max(merged.getLast()[1], row[1]);
            }
        }

        return merged.toArray(new int[0][]);
    }

    /**
     * Runtime: 298 ms, faster than 5.03% of Java online submissions for Merge Intervals.
     * Memory Usage: 55.9 MB, less than 5.61% of Java online submissions for Merge Intervals.
     * Time: O(n ^ 2)
     * Space: O(n)
     */
    public int[][] mergeBruteLuke(int[][] nums) {

        var merged = new boolean[nums.length];

        for (int curr = 0; curr < nums.length - 1; curr++) {
            if (merged[curr]) {
                continue;
            }

            for (int i = curr + 1; i < nums.length; i++) {

                if (merged[i]) {
                    continue;
                }

                if (nums[curr][1] < nums[i][0] || nums[curr][0] > nums[i][1]) {
                    continue;
                } else {
                    nums[curr][0] = Math.min(nums[curr][0], nums[i][0]);
                    nums[curr][1] = Math.max(nums[curr][1], nums[i][1]);
                    merged[i] = true;
                    curr--;
                    break;
                }
            }
        }

        int mergedCounter = 0;
        for (boolean isMerged : merged) {
            if (isMerged) {
                mergedCounter++;
            }
        }

        int[][] result = new int[nums.length - mergedCounter][2];

        // log.debug("afetr merge: {} {} {}", () -> nums, () -> merged, () -> result);

        int pos = 0;
        for (int r = 0; r < nums.length; r++) {
            if (!merged[r]) {
                result[pos][0] = nums[r][0];
                result[pos][1] = nums[r][1];
                pos++;
            }
        }

        return result;
    }
}
