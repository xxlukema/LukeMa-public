package com.learn.other;


import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Assertions;

import lombok.extern.log4j.Log4j2;


/**
 * LC - 149 - Max Points On A Line
 * 
 * Hard
 * 
 * Given an array of points where points[i] = [xi, yi] represents a point on the X-Y plane, return the maximum number of points that lie on the same straight line.
 * 
 * Example 1:
 * Input: points = [[1,1],[2,2],[3,3]]
 * Output: 3
 * 
 * Example 2:
 * Input: points = [[1,1],[3,2],[5,3],[4,1],[2,3],[1,4]]
 * Output: 4
 * 
 * Constraints:
 *     1 <= points.length <= 300
 *     points[i].length == 2
 *     -104 <= xi, yi <= 104
 *     All the points are unique.
 */
@Log4j2
public class MaxPointsOnALine {

    public static void main(String[] args) {

        // final int[][] nums = { { 1, 1 }, { 3, 2 }, { 5, 3 }, { 4, 1 }, { 2, 3 }, { 1, 4 } };
        final int[][] nums = { { 2, 3 }, { 3, 3 }, { -5, 3 } };

        MaxPointsOnALine maxPointsOnALine = new MaxPointsOnALine();

        int maxPointsBrute = maxPointsOnALine.maxPointsBrute(nums);
        log.debug("Max point on a line: {}", () -> maxPointsBrute);
        log.debug("Max point on a line {} OK", () -> "maxPointsBrute");

        int maxPointsMemo = maxPointsOnALine.maxPointsMemo(nums);
        Assertions.assertEquals(maxPointsBrute, maxPointsMemo);
        log.debug("Max point on a line {} OK", () -> "maxPointsMemo");

    }

    /**
     * Luke - DP memo
     * 
     * Use "0 + ..." to prevent "-0.0"
     * 
     * Runtime: 862 ms, faster than 5.07% of Java online submissions for Max Points on a Line.
     * Memory Usage: 148.8 MB, less than 5.03% of Java online submissions for Max Points on a Line.
     * 
     * Time: O(N ^ 2) * O(N)
     * Space: O(N)
     */
    public int maxPointsMemo(int[][] points) {
        if (points == null || points.length == 0) {
            return 0;
        }

        if (points.length <= 2) {
            return points.length;
        }

        final Map<KandB, Integer> memo = new HashMap<>();

        int maxPoints = backtrackMemo(points, 0, 0, memo);

        return maxPoints;
    }

    private int backtrackMemo(final int[][] points, int leftIdx, int rightIdx, final Map<KandB, Integer> memo) {
        final int N = points.length;

        /**
         * kick off the backtrack
         */
        if (leftIdx == 0 && rightIdx == 0) {
            int maxPoints = 0;

            /**
             * Time: O(N ^ 2 / 2) for two for loops.
             */
            for (int left = 0; left < N - 2; left++) {
                for (int right = left + 1; right < N - 1; right++) {
                    /**
                     * Time: O(N)
                     */
                    maxPoints = Math.max(maxPoints, backtrackMemo(points, left, right, memo));
                }
            }

            return maxPoints;
        }

        KandB kb = getKandB(points, leftIdx, rightIdx);

        if (kb == null) {
            int count = 0;
            for (int i = 0; i < N; i++) {
                if (points[i][0] == points[leftIdx][0]) {
                    count++;
                }
            }

            log.debug("left: {}, right: {}, count: {}---2222----", leftIdx, rightIdx, count);

            return count;
        }

        if (memo.containsKey(kb)) {
            return memo.get(kb);
        } else {
            int count = 2;

            /**
             * Time: O(N). But it happens the first time to calculate memo.
             */
            for (int i = 0; i < N; i++) {
                if (i != leftIdx && i != rightIdx) {
                    KandB tmpKB = getKandB(points, leftIdx, i);
                    if (tmpKB == null) {
                        tmpKB = getKandB(points, rightIdx, i);
                    }
                    if (kb.equals(tmpKB)) {
                        count++;
                    }

                    log.debug("left: {}, right: {}, count: {}, kb: {}, tmpKB: {} ========", leftIdx, rightIdx, count, kb, tmpKB);
                }
            }

            memo.put(kb, count);

            log.debug("left: {}, right: {}, count: {}-------", leftIdx, rightIdx, count);

            return count;
        }
    }

    /**
     * Slow floating point calculations.
     * 
     * Time: O(1)
     * Space: O(1)
     */
    KandB getKandB(final int[][] points, int left, int right) {
        if (left == right || points[left][0] == points[right][0]) {
            return null;
        }

        /**
         * Use "0 + ..." to prevent "-0.0"
         */
        float k = 0 + (float) (points[right][1] - points[left][1]) / (points[right][0] - points[left][0]);
        float b = (float) points[left][1] - k * points[left][0];

        return new KandB(k, b);
    }

    /**
     * KandB (float k, float b)
     */
    public record KandB(float k, float b) {
    }

    /**
     * Luke - Brute
     * 
     * Runtime: 131 ms, faster than 9.46% of Java online submissions for Max Points on a Line.
     * Memory Usage: 41.5 MB, less than 96.75% of Java online submissions for Max Points on a Line.
     * 
     * Time: O(N ^ 2) * O(N)
     * Space: O(1). Iterative. No recursion.
     */
    public int maxPointsBrute(int[][] points) {
        if (points == null || points.length == 0) {
            return 0;
        }

        if (points.length <= 2) {
            return points.length;
        }

        int maxPoints = backtrackBrute(points, 0, 0);

        return maxPoints;
    }

    private int backtrackBrute(final int[][] points, int leftIdx, int rightIdx) {
        final int N = points.length;

        /**
         * kick off the backtrack
         */
        if (leftIdx == 0 && rightIdx == 0) {
            int maxPoints = 0;

            /**
             * Time: O(N ^ 2 / 2) for the two for loops.
             */
            for (int left = 0; left < N - 2; left++) {
                for (int right = left + 1; right < N - 1; right++) {
                    /**
                     * Time: O(N)
                     */
                    maxPoints = Math.max(maxPoints, backtrackBrute(points, left, right));
                }
            }

            return maxPoints;
        }


        /**
         * Time: O(N)
         */
        int count = 0;
        for (int i = 0; i < N; i++) {
            if (isOnLineBrute(points, leftIdx, rightIdx, i)) {
                count++;
            }
        }

        log.debug("-------- left: {}, right: {}, count: {}", leftIdx, rightIdx, count);

        return count;
    }

    boolean isOnLineBrute(final int[][] points, int left, int right, int idx) {
        if (idx == left || idx == right) {
            return true;
        }

        return (points[right][1] - points[left][1]) * (points[idx][0] - points[left][0]) == (points[right][0] - points[left][0])
                * (points[idx][1] - points[left][1]);
    }
}
