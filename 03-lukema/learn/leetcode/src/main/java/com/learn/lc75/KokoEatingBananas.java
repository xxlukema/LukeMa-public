package com.learn.lc75;


import java.util.stream.IntStream;

import org.junit.jupiter.api.Assertions;

import lombok.extern.log4j.Log4j2;


/**
 * LC - 875. Koko Eating Bananas
 *
 * Medium
 *
 * Koko loves to eat bananas. There are n piles of bananas, the ith pile has piles[i] bananas. The guards have gone and will come back in h hours.

Koko can decide her bananas-per-hour eating speed of k. Each hour, she chooses some pile of bananas and eats k bananas from that pile. If the pile has
less than k bananas, she eats all of them instead and will not eat any more bananas during this hour.

Koko likes to eat slowly but still wants to finish eating all the bananas before the guards return.

Return the minimum integer k such that she can eat all the bananas within h hours.

Example 1:

Input: piles = [3,6,7,11], h = 8
Output: 4

Example 2:

Input: piles = [30,11,23,4,20], h = 5
Output: 30

Example 3:

Input: piles = [30,11,23,4,20], h = 6
Output: 23

Constraints:

    1 <= piles.length <= 10 ^ 4
    piles.length <= h <= 10 ^ 9
    1 <= piles[i] <= 10 ^ 9
 */

@Log4j2
public class KokoEatingBananas {

    public static void main(String[] args) {

        KokoEatingBananas kokoEatingBananas = new KokoEatingBananas();

        /*
        int[] piles = { 3, 6, 7, 11 };
        int h = 8;
        int expected = 4;
        */

        int[] piles = { 312884470 };
        int h = 312884469;
        int expected = 2;

        /*
        int[] piles = { 10 };
        int h = 9;
        int expected = -1;
        */

        /*
        int[] piles = { 30, 11, 23, 4, 20 };
        int h = 5;
        int expected = 30;
        */

        /*
        int[] piles = { 3, 6, 7, 11 };
        int h = 8;
        int expected = 4;
        */

        var retTLE = kokoEatingBananas.minEatingSpeedTLEBrutal(piles, h);
        log.debug("Koko Eating Bananas: {}", () -> retTLE);
        Assertions.assertEquals(expected, retTLE);
        log.debug("Koko Eating Bananas: {} OK", () -> "minEatingSpeedTLEBrutal");

        var ret = kokoEatingBananas.minEatingSpeed(piles, h);
        log.debug("Koko Eating Bananas: {}", () -> ret);
        Assertions.assertEquals(expected, ret);
        log.debug("Koko Eating Bananas: {} OK", () -> "minEatingSpeed");

    }

    /**
     * Time Limit Exceeded
     *
     * Time: O(len ^ h)
     * Space: (1)
     */
    public int minEatingSpeedTLEBrutal(int[] piles, int h) {
        int speed = 1;
        while (true) {
            int totalHours = totalEatingHours(piles, h, speed);
            if (totalHours <= h) {
                return speed;
            }

            speed++;
        }
    }

    public int totalEatingHours(int[] piles, int hours, int speed) {
        int totalHours = 0;

        for (int i = 0; i < piles.length; i++) {
            totalHours += piles[i] / speed;
            int rem = piles[i] % speed;
            if (rem > 0) {
                totalHours++;
            }
        }

        return totalHours;
    }

    /**
     * Trick 1: Use binary search
     * Trick 2: Binary search needs to avoid `mid = left + (right - left) / 2 && mid == left`
     *
     * Time: O(len ^ log(max))
     * Space: (1)
     */
    public int minEatingSpeed(int[] piles, int h) {

        int max = IntStream.of(piles).max().getAsInt();

        int left = 1;
        int right = max;

        while (left < right) {

            int mid = left + (right - left) / 2;

            int hours = totalEatingHours(piles, h, mid);

            // log.debug("hours: {}, h: {}, mid: {}", hours, h, mid);

            /**
             * Trick 1: Use `<=` because there are multiple solutions. Find the min solution.
             */
            if (hours <= h) {
                right = mid;
            } else {
                /**
                 * Trick 2: Use `left = mid + 1;`
                 */
                left = mid + 1;
            }
        }

        /*
        int hours = totalEatingHours(piles, h, right);
        return hours == h ? right : -1;
        */

        return right;
    }
}
