package com.learn.test.amzn2024;


import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import lombok.extern.log4j.Log4j2;


@Log4j2
public class SolutionApril4 {

    /**
     * LC-475 Heaters
     *
     * Medium
     *
     * Star: 11:53 PM
     * End: 1:38 AM
     *
     * Runtime: Beats 64% of java users
     * Memory: Beats 76% of java users
     *
     * Time: O(n * log(n))
     * Space: O(n)
     */
    @Test
    public void testPseudoPalindromicPaths() {
        log.debug(() -> "Start");

        // int[] houses = { 1, 2, 3, 4 };
        // int[] heaters = { 1, 4 };
        // int expected = 1;

        int[] houses = { 1, 5 };
        int[] heaters = { 2 };
        int expected = 3;

        // int[] houses = { 1, 2, 3, 5, 15 };
        // int[] heaters = { 2, 30 };
        // int expected = 13;

        // int[] houses = { 1, 1, 1, 1, 1, 1, 999, 999, 999, 999, 999 };
        // int[] heaters = { 499, 500, 501 };
        // int expected = 498;

        // int[] houses = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, };
        // int[] heaters = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, };
        // int expected = 0;

        // int[] houses = { 1, 2, 3, };
        // int[] heaters = { 2 };
        // int expected = 1;

        var ret = findRadiusLc(houses, heaters);

        log.debug("Result: {}", () -> ret);

        Assertions.assertEquals(expected, ret);
    }

    /**
     * LC-475 Heaters
     *
     * Medium
     *
     * Star: 11:53 PM
     * End: 12:31 AM
     *
     * Runtime: Beats 99.84% of java users.
     * Memory: Beats 58% of java users.
     *
     * Time: O(n * log(n) + m * log(m)) --- Time to sort two arrays.
     * Space: O(n)
     */
    public int findRadiusLc(int[] houses, int[] heaters) {
        /**
         * Time: O(n * log(n))
         */
        Arrays.sort(houses);

        /**
         * Time: O(m * log(m))
         */
        Arrays.sort(heaters);

        int[] dist = new int[houses.length];

        Arrays.fill(dist, Integer.MAX_VALUE);

        int idxHouse = 0;
        int idxHeater = 0;

        /**
         * Time: O(n + m)
         */
        while (idxHouse < houses.length && idxHeater < heaters.length) {
            if (houses[idxHouse] <= heaters[idxHeater]) {
                dist[idxHouse] = heaters[idxHeater] - houses[idxHouse];
                idxHouse++;
            } else {
                idxHeater++;
            }
        }

        idxHouse = houses.length - 1;
        idxHeater = heaters.length - 1;

        // {1, 5}
        // {2}
        // {1, ?}
        while (idxHouse >= 0 && idxHeater >= 0) {

            log.debug("idxHouse: {}, idxHeater: {}", idxHouse, idxHeater);

            if (houses[idxHouse] > heaters[idxHeater]) {
                dist[idxHouse] = Math.min(dist[idxHouse], houses[idxHouse] - heaters[idxHeater]);
                idxHouse--;
            } else {
                idxHeater--;
            }
        }

        int radius = 0;

        for (int d : dist) {
            radius = Math.max(d, radius);
        }

        return radius;
    }

    /**
     * LC-475 Heaters
     *
     * Medium
     *
     * Star: 11:53 PM
     * End: 12 hour + 11:46 PM - Wrong Answer. 29 / 30 testcases passed
     *
     * Runtime: Beats 41% of java users
     * Memory: Beats 81% of java users
     *
     * Time: O(n * log(n))
     * Space: O(1)
     */
    public int findRadiusLuke(int[] houses, int[] heaters) {

        /**
         * Trick 1: No need tp sort houses
         */
        // Arrays.sort(houses);

        /**
         * Trick 2: Sort heaters for `binarySearch`
         */
        Arrays.sort(heaters);

        int radius = 0;

        for (int idxHouse = 0; idxHouse < houses.length; idxHouse++) {

            /**
             * TODO: Use binary search to improve time performance
             */
            /*
            int min = Integer.MAX_VALUE;
            for (int idxHeater = 0; idxHeater < heaters.length; idxHeater++) {
                int d = Math.abs(houses[idxHouse] - heaters[idxHeater]);
                if (d < min) {
                    min = d;
                } else {
                    break;
                }
            }
            radius = Math.max(radius, min);
            */

            // int[] houses = { 1, 2, 3, 4 };
            // int[] heaters = { 1, 4 };
            int idxHeater = Arrays.binarySearch(heaters, houses[idxHouse]);

            log.debug("idxHeater: {}, houses[idxHouse]: {}", idxHeater, houses[idxHouse]);

            if (idxHeater >= 0) {
                int dist = Math.abs(houses[idxHouse] - heaters[idxHeater]);
                radius = Math.max(radius, dist);
            } else {
                int idxHeaterRight = -(idxHeater + 1);
                int idxHeaterLeft = idxHeaterRight - 1;

                int distToRight = 0;

                if (idxHeaterRight == heaters.length) {
                    distToRight = Math.abs(houses[idxHouse] - heaters[idxHeaterRight - 1]);
                } else {
                    distToRight = Math.abs(houses[idxHouse] - heaters[idxHeaterRight]);
                }

                int distToLeft = 0;

                if (idxHeaterLeft < 0) {
                    distToLeft = Math.abs(houses[idxHouse] - heaters[0]);
                } else {
                    distToLeft = Math.abs(houses[idxHouse] - heaters[idxHeaterLeft]);
                }

                int min = Math.min(distToLeft, distToRight);

                log.debug("    left: {}, right: {}, min: {}", distToLeft, distToRight, min);

                radius = Math.max(radius, min);
            }
        }

        return radius;
    }

    /**
     * LC-1814 Count Nice Pairs in an Array
     *
     * Medium
     *
     * Star: 12:09 AM
     * End: 2:20 AM - Wrong Answer. 81 / 84 testcases passed
     *
     * Runtime:
     * Memory:
     *
     * Time: O(n)
     * Space: O(n)
     */
    @Test
    public void testCountNicePairs() {
        log.debug(() -> "Start");

        // int[] nums = { 42, 11, 1, 97 };
        // int expected = 2;

        int[] nums = { 13, 10, 35, 24, 76 };
        int expected = 4;

        var ret = countNicePairs(nums);

        log.debug("Result: {}", () -> ret);

        Assertions.assertEquals(expected, ret);
    }

    public int countNicePairs(int[] nums) {

        // final int MOD = (int) Math.pow(10, 9) + 7;
        final int MOD = (int) 1e9 + 7;

        int len = nums.length;

        Map<Integer, Integer> memo = new HashMap<>();
        Map<Integer, Integer> map = new HashMap<>();

        /**
         * a + rev(b) == rev(a) + b
         * a - rev(a) == b - rev(b)
         *
         * 1. build a map of Map<a - rev(a), count>
         */

        for (int i = 0; i < len; i++) {
            int key = nums[i] - rev(nums[i], memo);

            if (map.containsKey(key)) {
                map.put(key, map.get(key) + 1);
            } else {
                map.put(key, 1);
            }
        }

        /**
         * 2. iterate
         */
        long count = 0;

        java.util.Collection<Integer> values = map.values();

        for (int v : values) {
            count = (count % MOD + (v * (v - 1)) % MOD / 2);
        }

        return (int) count % MOD;
    }

    int rev(int in, Map<Integer, Integer> memo) {

        if (memo.containsKey(in)) {
            return memo.get(in);
        }

        int out = 0;

        while (in > 0) {
            out = out * 10 + in % 10;
            in /= 10;
        }

        memo.put(in, out);

        return out;
    }

    @Test
    public void testRev() {

        int in = 23;
        var ans = rev(in, new HashMap<>());

        log.debug("in: {}, rev: {}", in, ans);
    }

    /**
     * LC-1457 Pseudo-Palindromic Paths in a Binary Tree
     *
     * Medium
     *
     * Star: 1:36 PM
     * End: 2:58 PM - Time Limit Exceeded
     * End: 3:42 PM - Runtime: Beats 16% java users. Memory: Beats 6% java users.
     *
     * Runtime: Beats 64% of java users
     * Memory: Beats 76% of java users
     *
     * Time: O(n ^ 2)
     * Space: O(n)
     */
    @Test
    public void testNext() {
        log.debug(() -> "Start");

        // Integer[] root = { 2, 3, 1, 3, 1, null, 1 };
        // int expected = 2;

        // TreeNode rootNode = TreeNode.toTreeBfsWithNullIntegers(root);

        // var ret = pseudoPalindromicPaths(rootNode);

        // log.debug("Result: {}", () -> ret);

        // Assertions.assertEquals(expected, ret);
    }
}
