package com.learn.lc75;


import org.junit.jupiter.api.Assertions;

import lombok.extern.log4j.Log4j2;


/**
 * LC - 1071. Greatest Common Divisor of Strings
 *
 * Easy
 *
 * For two strings s and t, we say "t divides s" if and only if s = t + ... + t (i.e., t is concatenated with itself one or more times).

Given two strings str1 and str2, return the largest string x such that x divides both str1 and str2.



Example 1:

Input: str1 = "ABCABC", str2 = "ABC"
Output: "ABC"

Example 2:

Input: str1 = "ABABAB", str2 = "ABAB"
Output: "AB"

Example 3:

Input: str1 = "LEET", str2 = "CODE"
Output: ""

Constraints:

    1 <= str1.length, str2.length <= 1000
    str1 and str2 consist of English uppercase letters.

 */

@Log4j2
public class GreatestCommonDivisorOfStrings {

    public static void main(String[] args) {

        GreatestCommonDivisorOfStrings greatestCommonDivisorOfStrings = new GreatestCommonDivisorOfStrings();

        String str1 = "ABCABCABC", str2 = "ABCABC";

        Assertions.assertTrue(greatestCommonDivisorOfStrings.canDivide("ab", "abababababab"));

        var retLuke = greatestCommonDivisorOfStrings.gcdOfStringsLukeNaive(str1, str2);
        Assertions.assertEquals("ABC", retLuke);
        log.debug("Greatest Common Divisor of Strings {} OK", () -> "gcdOfStringsLukeNaive");

        var retLc = greatestCommonDivisorOfStrings.gcdOfStringsLc(str1, str2);
        Assertions.assertEquals(retLuke, retLc);
        log.debug("Greatest Common Divisor of Strings {} OK", () -> "gcdOfStringsLc");

    }

    /**
     * LC - Euclid Algorithm
     *
     * Time: O(n) --- String concat
     * Space: O(1)
     */
    public String gcdOfStringsLc(String str1, String str2) {
        if (!(str1 + str2).equals(str2 + str1)) {
            return "";
        }

        int gcd = getGcd(str1.length(), str2.length());

        return str1.substring(0, gcd);
    }

    /**
     * Euclidean Algorithm
     *
     * Time: O(1)
     * Space: O(1)
     */
    private int getGcd(int larger, int smaller) {
        if (larger < smaller) {
            return getGcd(smaller, larger);
        }

        return smaller == 0 ? larger : getGcd(smaller, larger % smaller);
    }

    /**
     * Luke - Naive
     *
     * Time: O(N ^ 2)
     * Space: O(N)
     *
     * Runtime: 2 ms Beats 22.45%
     * Memory: 43.8 MB Beats 11.48%
     */
    public String gcdOfStringsLukeNaive(String str1, String str2) {
        if (str1.indexOf(str2) != 0 && str2.indexOf(str1) != 0) {
            return "";
        }

        if (str1.equals(str2)) {
            return str1;
        }

        /**
         * make first arg as shorter
         */
        if (str1.length() == str2.length()) {
            /**
             * length is equal but two strings are not equal:
             */
            return "";
        }

        if (str1.length() > str2.length()) {
            return gcdOfStringsLukeNaive(str2, str1);
        }

        for (int i = str1.length(); i >= 1; i--) {
            String sub = str1.substring(0, i);

            // log.debug("sub: {}", () -> sub);

            if (canDivide(sub, str1) && canDivide(sub, str2)) {
                return sub;
            }
        }

        return "";
    }

    public boolean canDivide(String divisor, String toBeDivided) {
        if (toBeDivided.length() % divisor.length() != 0) {
            return false;
        }

        for (int i = 0, len = divisor.length(), repeat = toBeDivided.length() / len; i < repeat; i++) {

            // log.debug("=== {}, {}", toBeDivided.indexOf(divisor, i * len), i * len);

            if (toBeDivided.indexOf(divisor, i * len) != i * len) {
                return false;
            }
        }

        return true;
    }
}
