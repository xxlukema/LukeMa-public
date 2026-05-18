package com.learn.other;


import lombok.extern.log4j.Log4j2;


/**
 * LC - 203 - Count Primes
 *
 * Medium
 *
 * Given an integer n, return the number of prime numbers that are strictly less than n.
 *
 * Example 1:
 * Input: n = 10
 * Output: 4
 * Explanation: There are 4 prime numbers less than 10, they are 2, 3, 5, 7.
 *
 * Example 2:
 * Input: n = 0
 * Output: 0
 *
 * Example 3:
 * Input: n = 1
 * Output: 0
 *
 * Constraints:
 *     0 <= n <= 5 * 106
 */
@Log4j2
public class CountPrimes {

    public static void main(String[] args) {

        /**
         * Output:
         */
        final int n = 2;

        CountPrimes countPrimes = new CountPrimes();

        var ret = countPrimes.countPrimes(n);
        log.debug("Count Primes: {}", () -> ret);
        log.debug("Count Primes {} OK", () -> "ret");
    }

    public int countPrimes(int n) {
        return 0;
    }
}
