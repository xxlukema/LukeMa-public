package com.learn.backtrack.redo;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import lombok.extern.log4j.Log4j2;


/**
 * LC - 89 - Gray Code
 *
 * An n-bit gray code sequence is a sequence of 2n integers where:
 *
 *     Every integer is in the inclusive range [0, 2n - 1],
 *     The first integer is 0,
 *     An integer appears no more than once in the sequence,
 *     The binary representation of every pair of adjacent integers differs by exactly one bit, and
 *     The binary representation of the first and last integers differs by exactly one bit.
 *
 * Given an integer n, return any valid n-bit gray code sequence.
 *
 * Example 1:
 * Input: n = 2
 * Output: [0,1,3,2]
 * Explanation:
 * The binary representation of [0,1,3,2] is [00,01,11,10].
 * - 00 and 01 differ by one bit
 * - 01 and 11 differ by one bit
 * - 11 and 10 differ by one bit
 * - 10 and 00 differ by one bit
 * [0,2,3,1] is also a valid gray code sequence, whose binary representation is [00,10,11,01].
 * - 00 and 10 differ by one bit
 * - 10 and 11 differ by one bit
 * - 11 and 01 differ by one bit
 * - 01 and 00 differ by one bit
 *
 * Example 2:
 * Input: n = 1
 * Output: [0,1]
 *
 * Constraints:
 *     1 <= n <= 16
 */
@Log4j2
public class GrayCode {

    public static void main(String[] args) {

        final int n = 3;

        GrayCode grayCode = new GrayCode();

        // var grayCodeLcApproach2 = grayCode.grayCodeLcApproach2(n);

        var grayCodeLcApproach2 = grayCode.grayCodeLcApproach2Improve(n);
        log.debug("Gray Code: {}", () -> grayCodeLcApproach2);
        log.debug("Gray Code {} OK", () -> "grayCodeLcApproach2");

    }

    /**
     * LC - Approach 2: Recursion
     *
     * YouTube - https://www.youtube.com/watch?v=ha1gEWYvr78
     *
     * Runtime: 93 ms Beats 12.25%
     * Memory: 66.1 MB Beats 10.97%
     *
     * Time: O(2 ^ N), where N is the total number of bits in the code. N bits make (2 ^ N) numbers.
     * Space: O(N)
     */
    public List<Integer> grayCodeLcApproach2(int n) {

        List<String> result = new ArrayList<>();

        /**
         * Base: 1 bit
         */

        int count = 1;
        result.add("0");
        result.add("1");

        while (count < n) {

            /**
             * Add one bit
             */
            count++;

            /**
             * Shallow copy of result
             */
            List<String> list2 = new ArrayList<>(result);

            /**
             * Append '0' to all elements
             */
            result = result.stream().map(e -> '0' + e).collect(Collectors.toList());

            /**
             * Append '1' to all elements of deep copied list
             */
            list2 = list2.stream().map(e -> '1' + e).collect(Collectors.toList());

            /**
             * Reverse the secondary list
             */
            Collections.reverse(list2);

            /**
             * Join the two lists
             */
            result.addAll(list2);
        }

        return result.stream().map(e -> Integer.parseInt(e, 2)).toList();
    }

    /**
     * LC - Approach 2: Recursion - Improve
     *
     * YouTube - https://www.youtube.com/watch?v=ha1gEWYvr78
     *
     * Runtime: 45 ms Beats 19.46% (vs Runtime: 93 ms Beats 12.25%)
     * Memory: 52.8 MB Beats 88.79% (vs Memory: 66.1 MB Beats 10.97%)
     *
     * Time: O(2 ^ N), where N is the total number of bits in the code. N bits make (2 ^ N) numbers.
     * Space: O(N)
     */
    public List<Integer> grayCodeLcApproach2Improve(int n) {

        List<StringBuilder> result = new ArrayList<>();

        /**
         * Base: 1 bit
         */

        int count = 1;
        result.add(new StringBuilder("0"));
        result.add(new StringBuilder("1"));

        while (count < n) {

            /**
             * Add one bit
             */
            count++;

            /**
             * Shallow copy of result. This will NOT work for this without use of stream.
             */
            // List<String> list2 = new ArrayList<>(result);

            /**
             * Real Deep copy of result
             */
            List<StringBuilder> list2 = new ArrayList<>();

            result.forEach(e -> {
                list2.add(new StringBuilder(e.toString()));
            });

            /**
             * Append '0' to all elements
             */
            // result = result.stream().map(e -> '0' + e).collect(Collectors.toList());
            result.forEach(e -> {
                e.insert(0, '0');
            });

            /**
             * Append '1' to all elements of deep copied list
             */
            // list2 = list2.stream().map(e -> '1' + e).collect(Collectors.toList());
            list2.forEach(e -> {
                e.insert(0, '1');
            });

            /**
             * Reverse the secondary list
             */
            Collections.reverse(list2);

            /**
             * Join the two lists
             */
            result.addAll(list2);
        }

        return result.stream().map(e -> Integer.parseInt(e.toString(), 2)).toList();
    }

}
