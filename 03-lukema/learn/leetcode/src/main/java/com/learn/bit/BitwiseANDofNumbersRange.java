package com.learn.bit;


import org.junit.jupiter.api.Assertions;

import lombok.extern.log4j.Log4j2;


/**
 * LC -201 - Bitwise AND of Numbers Range
 *
 * Medium
 *
 * Given two integers left and right that represent the range [left, right], return the bitwise AND of all numbers in this range, inclusive.
 *
 * Example 1:
 * Input: left = 5, right = 7
 * Output: 4
 *
 * Example 2:
 * Input: left = 0, right = 0
 * Output: 0
 *
 * Example 3:
 * Input: left = 1, right = 2147483647
 * Output: 0
 *
 * Constraints:
 *     0 <= left <= right <= 2 ^ 31 - 1
 */
@Log4j2
public class BitwiseANDofNumbersRange {

    public static void main(String[] args) {

        /**
         * Output: 4
         */
        // final int left = 5, right = 7;

        /**
         * Output: Time Limit Exceeded
         */
        // final int left = 1, right = 2147483647;

        /**
         * Output: 212
         */
        final int left = 213, right = 215;

        BitwiseANDofNumbersRange bitwiseANDofNumbersRange = new BitwiseANDofNumbersRange();

        var rangeBitwiseAndLuke = bitwiseANDofNumbersRange.rangeBitwiseAndLuke(left, right);
        log.debug("Bitwise AND od Numbers Range: {}", () -> rangeBitwiseAndLuke);
        log.debug("Bitwise AND od Numbers Range {} OK", () -> "rangeBitwiseAndLuke");

        var rangeBitwiseAndLcBitShift = bitwiseANDofNumbersRange.rangeBitwiseAndLcBitShift(left, right);
        Assertions.assertEquals(rangeBitwiseAndLuke, rangeBitwiseAndLcBitShift);
        log.debug("Bitwise AND od Numbers Range {} OK", () -> "rangeBitwiseAndLcBitShift");

        var rangeBitwiseAndBrian = bitwiseANDofNumbersRange.rangeBitwiseAndBrian(left, right);
        Assertions.assertEquals(rangeBitwiseAndLuke, rangeBitwiseAndBrian);
        log.debug("Bitwise AND od Numbers Range {} OK", () -> "rangeBitwiseAndBrian");

    }

    /**
     * Luke - Iterative
     *
     * Time Limit Exceeded
     *
     * Time: O(N)
     * Space: O(1)
     */
    public int rangeBitwiseAndLuke(int left, int right) {

        int and = left;
        while (++left <= right) {
            and = and & left;
        }

        return and;

    }

    /**
     * LC - Bit Shift
     *
     * Time: O(1)
     * Space: O(1)
     */
    public int rangeBitwiseAndLcBitShift(int left, int right) {
        int shifts = 0;
        while (left < right) {
            left = left >> 1;
            right = right >> 1;

            shifts++;

            log.debug(" ------ shifts: {}, left: {}, right: {}", shifts, left, right);
        }

        return left << shifts;
    }

    /**
     * LC - Brian Kernighan
     *
     * Time: O(1)
     * Space: O(1)
     */
    public int rangeBitwiseAndBrian(int left, int right) {
        while (left < right) {
            // turn off rightmost 1-bit
            right = right & (right - 1);

            log.debug(" ====== right int: {}, right: {}", right, String.format("%16s", Integer.toBinaryString(right)));
        }
        return left & right;
    }
}
