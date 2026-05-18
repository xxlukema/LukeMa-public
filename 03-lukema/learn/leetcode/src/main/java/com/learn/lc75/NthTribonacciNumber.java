package com.learn.lc75;


import org.junit.jupiter.api.Assertions;

import lombok.extern.log4j.Log4j2;


/**
 * LC - 1137. N-th Tribonacci Number
 *
 * Easy
 *
 * The Tribonacci sequence Tn is defined as follows:

T0 = 0, T1 = 1, T2 = 1, and Tn+3 = Tn + Tn+1 + Tn+2 for n >= 0.

Given n, return the value of Tn.

Example 1:

Input: n = 4
Output: 4
Explanation:
T_3 = 0 + 1 + 1 = 2
T_4 = 1 + 1 + 2 = 4

Example 2:

Input: n = 25
Output: 1389537

Constraints:

    0 <= n <= 37
    The answer is guaranteed to fit within a 32-bit integer, ie. answer <= 2^31 - 1.
 */

@Log4j2
public class NthTribonacciNumber {

    public static void main(String[] args) {

        NthTribonacciNumber nthTribonacciNumber = new NthTribonacciNumber();

        int n = 25;
        int expected = 1389537;

        var ret = nthTribonacciNumber.tribonacci(n);
        log.debug("N-th Tribonacci Number: {}", () -> ret);
        Assertions.assertEquals(expected, ret);
        log.debug("N-th Tribonacci Number: {} OK", () -> "tribonacci");

    }

    /**
     * Time: O(n)
     * Space: O(n)
     *
     * Runtime: -ms Beats 100.00%
     * Memory: 39.33mb Beats 57.47%
     * @param n
     * @return
     */
    public int tribonacci(int n) {

        int t0 = 0, t1 = 1, t2 = 1;

        if(n == 0) {
            return t0;
        }

        if(n == 1) {
            return t1;
        }

        if(n == 2) {
            return t2;
        }

        int[] t = new int[n + 1];

        t[0] = t0;
        t[1] = t1;
        t[2] = t2;

        int i = 3;

        while (i <= n) {
            t[i] = t[i - 3] + t[i - 2] + t[i - 1];
            i++;
        }

        return t[n];
    }
}
