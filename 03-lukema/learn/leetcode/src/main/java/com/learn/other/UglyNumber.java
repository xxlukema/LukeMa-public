package com.learn.other;


import lombok.extern.log4j.Log4j2;


/**
 * LC-263 Ugly Number
 *
 * Easy
 *
 * An ugly number is a positive integer whose prime factors are limited to 2, 3, and 5.
 *
 * Given an integer n, return true if n is an ugly number.
 *
 * Example 1:
 * Input: n = 6
 * Output: true
 * Explanation: 6 = 2 × 3
 *
 * Example 2:
 * Input: n = 1
 * Output: true
 * Explanation: 1 has no prime factors, therefore all of its prime factors are limited to 2, 3, and 5.
 *
 * Example 3:
 * Input: n = 14
 * Output: false
 * Explanation: 14 is not ugly since it includes the prime factor 7.
 *
 * Constraints:
 *     -2 ^ 31 <= n <= 2 ^ 31 - 1
 */
@Log4j2
public class UglyNumber {

    public static void main(String[] args) {

        /**
         * Expected: false;
         */
        // final int n = 14;

        /**
         * Expected: true;
         */
        final int n = 6;

        UglyNumber uglyNumber = new UglyNumber();

        var ret = uglyNumber.isUglyLuke(n);
        log.debug("Ugly Number: {}", () -> ret);
        log.debug("Ugly Number {} OK", () -> "ret");

    }

    /**
     * Luke - Brute
     *
     * Runtime: 2 ms Beats 79.22%
     * Memory: 42 MB Beats 6.85%
     *
     * Time: O(max(log base 5 N, log base 3 N, log base 2 N) = O(log(N))
     * Space: O(1)
     */
    public boolean isUglyLuke(int n) {
        /**
         * limited to 2, 3, 5
         */
        while (n >= 5) {
            int rem = n % 5;
            if (rem != 0) {
                break;
            }

            n /= 5;
        }

        while (n >= 3) {
            int rem = n % 3;
            if (rem != 0) {
                break;
            }

            n /= 3;
        }

        while (n >= 2) {
            int rem = n % 2;
            if (rem != 0) {
                break;
            }

            n /= 2;
        }

        return n == 1;
    }

    /**
     * LC
     */
    public boolean isUglyLc(int n) {
        // A non-positive integer cannot be ugly
        if (n <= 0) {
            return false;
        }

        // Factorize by dividing with permitted factors
        for (int factor : new int[] { 2, 3, 5 }) {
            n = keepDividingWhenDivisible(n, factor);
        }

        // Check if the integer is reduced to 1 or not.
        return n == 1;
    }

    // Keep dividing dividend by divisor when division is possible.
    private int keepDividingWhenDivisible(int dividend, int divisor) {
        while (dividend % divisor == 0) {
            dividend /= divisor;
        }
        return dividend;
    }
}
