package com.learn.other;


import lombok.extern.log4j.Log4j2;


/**
 * LC - 171 - Factorial Trailing Zeros
 * 
 * Medium
 * 
 * Given an integer n, return the number of trailing zeroes in n!.
 * Note that n! = n * (n - 1) * (n - 2) * ... * 3 * 2 * 1.
 * 
 * Example 1:
 * Input: n = 3
 * Output: 0
 * Explanation: 3! = 6, no trailing zero.
 * 
 * Example 2:
 * Input: n = 5
 * Output: 1
 * Explanation: 5! = 120, one trailing zero.
 * 
 * Example 3:
 * Input: n = 0
 * Output: 0
 * 
 * Constraints:
 *     0 <= n <= 104
 * 
 * Follow up: Could you write a solution that works in logarithmic time complexity?
 */
@Log4j2
public class FactorialTrailingZeros {

    public static void main(String[] args) {

        /**
         * Output: 99
         */
        final int n = 400;

        FactorialTrailingZeros factorialTrailingZeros = new FactorialTrailingZeros();

        var trailingZeroes = factorialTrailingZeros.trailingZeroes(n);
        log.debug("Factorial trailing zeros: {}", () -> trailingZeroes);
        log.debug("Factorial trailing zeros {} OK", () -> "trailingZeroes");

    }

    /**
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Factorial Trailing Zeroes.
     * Memory Usage: 39.2 MB, less than 97.07% of Java online submissions for Factorial Trailing Zeroes.
     * 
     * Time: O(log(N) base 5)
     * Space: O(1)
     */
    public int trailingZeroes(int n) {

        int countOf5 = 0;

        /**
         * 25 has 6 '5's
         */
        while (n > 0) {
            n /= 5;
            countOf5 += n;
        }

        return countOf5;
    }
}
