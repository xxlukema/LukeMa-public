package com.learn.ml;


import org.junit.jupiter.api.Assertions;

import lombok.extern.log4j.Log4j2;


/**
 * 1266. Minimum Time Visiting All Points
 *
 * Easy
 *
 * On a 2D plane, there are n points with integer coordinates points[i] = [xi, yi]. Return the minimum time in seconds to visit all the points in the order given by points.
 *
 * You can move according to these rules:
 *
 *     In 1 second, you can either:
 *         move vertically by one unit,
 *         move horizontally by one unit, or
 *         move diagonally sqrt(2) units (in other words, move one unit vertically then one unit horizontally in 1 second).
 *     You have to visit the points in the same order as they appear in the array.
 *     You are allowed to pass through points that appear later in the order, but these do not count as visits.
 *
 * Example 1:
 *
 * Input: points = [[1,1],[3,4],[-1,0]]
 * Output: 7
 * Explanation: One optimal path is [1,1] -> [2,2] -> [3,3] -> [3,4] -> [2,3] -> [1,2] -> [0,1] -> [-1,0]
 * Time from [1,1] to [3,4] = 3 seconds
 * Time from [3,4] to [-1,0] = 4 seconds
 * Total time = 7 seconds
 *
 * Example 2:
 *
 * Input: points = [[3,2],[-2,2]]
 * Output: 5
 *
 * Constraints:
 *
 *     points.length == n
 *     1 <= n <= 100
 *     points[i].length == 2
 *     -1000 <= points[i][0], points[i][1] <= 1000
 */
@Log4j2
public class MinimumTimeVisitingAllPoints {

    public static void main(String[] args) {

        MinimumTimeVisitingAllPoints minimumTimeVisitingAllPoints = new MinimumTimeVisitingAllPoints();

        int[][] points = { { 1, 1 }, { 3, 4 }, { -1, 0 } };
        int expected = 7;

        var ret = minimumTimeVisitingAllPoints.minTimeToVisitAllPoints(points);
        log.info("Minimum Time Visiting All Points: {}", () -> ret);
        Assertions.assertEquals(expected, ret);
        log.debug("Minimum Time Visiting All Points {} OK", () -> "minTimeToVisitAllPoints");
    }

    /**
     * Luke
     *
     * Runtime: 1ms Beats95.62%of users with Java
     * Memory: 43.98MB Beats6.01%of users with Java
     *
     * Time: O(points.length)
     * Space: O(1)
     */
    public int minTimeToVisitAllPoints(int[][] points) {
        int len = 0;

        for (int i = 0; i < points.length - 1; i++) {
            len += findSteps(points[i], points[i + 1]);
        }

        return len;
    }

    /**
     * Time: O(1)
     * Space: O(1)
     */
    private int findSteps(int[] a, int[] b) {
        int width = Math.abs(b[0] - a[0]);
        int height = Math.abs(b[1] - a[1]);

        return Math.max(width, height);
    }
}
