package com.learn.dp;


import java.util.Arrays;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Assertions;

import lombok.extern.log4j.Log4j2;


/**
 * LC - 135 - candies
 * 
 * Hard
 * 
 * There are n children standing in a line. Each child is assigned a rating value given in the integer array ratings.
 * You are giving candies to these children subjected to the following requirements:
 * 
 *     Each child must have at least one candies.
 *     Children with a higher rating get more candies than their neighbors.
 * 
 * Return the minimum number of candies you need to have to distribute the candies to the children.
 * 
 * Example 1:
 * Input: ratings = [1,0,2]
 * Output: 5
 * Explanation: You can allocate to the first, second and third child with 2, 1, 2 candies respectively.
 * 
 * Example 2:
 * Input: ratings = [1,2,2]
 * Output: 4
 * Explanation: You can allocate to the first, second and third child with 1, 2, 1 candies respectively.
 * The third child gets 1 candies because it satisfies the above two conditions.
 * 
 * Constraints:
 *     n == ratings.length
 *     1 <= n <= 2 * 104
 *     0 <= ratings[i] <= 2 * 104
 */
@Log4j2
public class Candy {

    public static void main(String[] args) {

        // final int[] ratings = { 1, 0, 2 };
        // final int[] ratings = { 1, 6, 10, 8, 7, 3, 2 };
        // final int[] ratings = { 1, 2, 2 };
        final int[] ratings = { 1, 3, 4, 5, 2 };

        Candy candy = new Candy();

        int candiesLukeBrute = candy.candiesLukeBrute(ratings);
        log.debug("Candy: {}", () -> candiesLukeBrute);
        log.debug("Candy {} OK", () -> "candiesLukeBrute");

        int candiesLukeTwoPaths = candy.candiesLukeTwoPaths(ratings);
        Assertions.assertEquals(candiesLukeBrute, candiesLukeTwoPaths);
        log.debug("Candy {} OK", () -> "candiesLukeTwoPaths");

        int candyLcOnePath = candy.candyLcOnePath(ratings);
        Assertions.assertEquals(candiesLukeBrute, candyLcOnePath);
        log.debug("Candy {} OK", () -> "candyLcOnePath");
    }

    /**
     * Luke - Two Paths - Iterative
     * 
     * Runtime: 8 ms, faster than 17.02% of Java online submissions for candies.
     * Memory Usage: 52.4 MB, less than 17.02% of Java online submissions for candies.
     * 
     * Time: O(4 N) = O(N)
     * Space: O(N)
     */
    public int candiesLukeTwoPaths(int[] ratings) {
        if (ratings == null || ratings.length == 0) {
            return 0;
        }

        final int N = ratings.length;

        if (N == 1) {
            return 1;
        }

        final int[] candies = new int[N];

        /**
         * Init candies
         */
        /*
        for (int i = 0; i < N; i++) {
            candies[i] = 1;
        }
        */

        Arrays.fill(candies, 1);

        for (int i = 1; i < N; i++) {
            if (ratings[i] > ratings[i - 1]) {
                candies[i] = 1 + candies[i - 1];
            }
        }

        for (int k = N - 2; k >= 0; k--) {
            if (ratings[k] > ratings[k + 1]) {
                candies[k] = Math.max(candies[k], (1 + candies[k + 1]));
            }
        }

        // log.debug("ratn: {}", () -> ratings);
        // log.debug("each: {}", () -> candies);

        return IntStream.of(candies).sum();
    }

    /**
     * Luke - Brute backtrack
     * 
     * 	Time Limit Exceeded
     * 
     * Time: O(N ^ 2)
     * Space: O(N)
     */
    public int candiesLukeBrute(final int[] ratings) {

        if (ratings == null || ratings.length == 0) {
            return 0;
        }

        final int N = ratings.length;

        if (N == 1) {
            return 1;
        }

        final int[] candies = new int[N];

        /**
         * Init candies
         */
        /*
        for (int i = 0; i < N; i++) {
            candies[i] = 1;
        }
        */

        Arrays.fill(candies, 1);

        backtrackBrute(ratings, candies, 0, true);

        // log.debug("ratn: {}", () -> ratings);
        // log.debug("each: {}", () -> candies);

        return IntStream.of(candies).sum();
    }

    /**
     * Time: O(2 * N ^ 2) = O(N ^ 2)
     * Space: O(N)
     */
    void backtrackBrute(final int[] ratings, final int[] candies, int idx, boolean isLeftToRight) {
        final int N = ratings.length;

        if (idx < 0 || idx >= N) {
            return;
        }

        boolean changed = false;
        if (isLeftToRight) {
            if (idx + 1 < N && ratings[idx] > ratings[idx + 1]) {
                if (candies[idx] <= candies[idx + 1]) {
                    candies[idx] = candies[idx + 1] + 1;
                    changed = true;
                }
            }
            backtrackBrute(ratings, candies, idx + 1, false);
            backtrackBrute(ratings, candies, idx + 1, true);
        } else {
            if (idx - 1 >= 0 && ratings[idx] > ratings[idx - 1]) {
                if (candies[idx] <= candies[idx - 1]) {
                    candies[idx] = candies[idx - 1] + 1;
                    changed = true;
                }
            }
            backtrackBrute(ratings, candies, idx - 1, false);
        }

        if (changed) {
            backtrackBrute(ratings, candies, 0, true);
        }
    }

    /**
     * LC - One Path
     * 
     * Time: O(N)
     * Space: O(1)
     */
    public int count(int n) {
        return (n * (n + 1)) / 2;
    }

    public int candyLcOnePath(int[] ratings) {

        if (ratings.length <= 1) {
            return ratings.length;
        }

        int candies = 0;
        int up = 0;
        int down = 0;
        int oldSlope = 0;

        for (int i = 1; i < ratings.length; i++) {
            int newSlope = (ratings[i] > ratings[i - 1]) ? 1
                    : (ratings[i] < ratings[i - 1] ? -1 : 0);

            if ((oldSlope > 0 && newSlope == 0) || (oldSlope < 0 && newSlope >= 0)) {
                candies += count(up) + count(down) + Math.max(up, down);
                up = 0;
                down = 0;
            }
            if (newSlope > 0) {
                up++;
            } else if (newSlope < 0) {
                down++;
            } else {
                candies++;
            }

            oldSlope = newSlope;
        }

        candies += count(up) + count(down) + Math.max(up, down) + 1;

        return candies;
    }
}
