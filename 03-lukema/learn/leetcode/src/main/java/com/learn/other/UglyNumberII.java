package com.learn.other;


import lombok.extern.log4j.Log4j2;


/**
 * LC-264 - Ugly Number II
 *
 * Medium
 *
 * An ugly number is a positive integer whose prime factors are limited to 2, 3, and 5.
 *
 * Given an integer n, return the nth ugly number.
 *
 * Example 1:
 * Input: n = 10
 * Output: 12
 * Explanation: [1, 2, 3, 4, 5, 6, 8, 9, 10, 12] is the sequence of the first 10 ugly numbers.
 *
 * Example 2:
 * Input: n = 1
 * Output: 1
 * Explanation: 1 has no prime factors, therefore all of its prime factors are limited to 2, 3, and 5.
 *
 * Constraints:
 *
 *     1 <= n <= 1690
 */
@Log4j2
public class UglyNumberII {

    public static void main(String[] args) {

        /**
         * Expected: 12
         */
        final int n = 10;

        UglyNumberII uglyNumberII = new UglyNumberII();

        var ret = uglyNumberII.nthUglyNumber(n);
        log.debug("Ugly Number II: {}", () -> ret);
        log.debug("Ugly Number II {} OK", () -> "ret");
    }

    public int nthUglyNumber(int n) {

        int count = 1;

        int cur = 1;
        int last = 1;

        int next2 = 0;
        int next3 = 0;
        int next5 = 0;

        while (count < n) {

            cur = last;
            next3 = last * 3;
            next5 = last * 5;

            while ((next2 = cur * 2) < next3) {
                cur = next2;
                count++;

                if (count == n) {
                    return cur;
                }
            }

            while ((next3 = cur * 3) < next5) {
                cur = next3;
                count++;
            }

        }

        return count;
    }
}
