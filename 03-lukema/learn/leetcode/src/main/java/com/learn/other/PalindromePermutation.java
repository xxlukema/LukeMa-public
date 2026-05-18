package com.learn.other;


import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Assertions;

import lombok.extern.log4j.Log4j2;


/**
 * LC-266 Palindrome Permutation
 *
 * Easy
 *
 * Given a string s, return true if a permutation of the string could form a palindrome and false otherwise.
 *
 * Example 1:
 * Input: s = "code"
 * Output: false
 *
 * Example 2:
 * Input: s = "aab"
 * Output: true
 *
 * Example 3:
 * Input: s = "carerac"
 * Output: true
 *
 * Constraints:
 *     1 <= s.length <= 5000
 *     s consists of only lowercase English letters.
 */
@Log4j2
public class PalindromePermutation {

    public static void main(String[] args) {

        /**
         * Expected: true
         */
        // final String s = "carerac";

        /**
         * Expected: false
         */
        // final String s = "code";

        /**
         * Expected: false
         */
        // final String s = "ab";

        /**
         * Expected: false
         */
        // final String s = "abc";

        /**
         * Expected: true
         */
        final String s = "aab";

        PalindromePermutation palindromePermutation = new PalindromePermutation();

        var canPermutePalindrome = palindromePermutation.canPermutePalindrome(s);
        log.debug("Palindrome Permutation: {}", () -> canPermutePalindrome);
        log.debug("Palindrome Permutation {} OK", () -> "canPermutePalindrome");

        var canPermutePalindromeLc = palindromePermutation.canPermutePalindromeLc(s);
        Assertions.assertEquals(canPermutePalindrome, canPermutePalindromeLc);
        log.debug("Palindrome Permutation {} OK", () -> "canPermutePalindromeLc");

        var canPermutePalindromeAsci = palindromePermutation.canPermutePalindromeAsci(s);
        Assertions.assertEquals(canPermutePalindrome, canPermutePalindromeAsci);
        log.debug("Palindrome Permutation {} OK", () -> "canPermutePalindromeAsci");

    }

    /**
     * Luke - (1) sort string and count: Time: O(N * log(N))
     *      - (2) count map: Time: O(N)
     *      - Trick: Use Map<Character, Intetger> instead of int[] count, so that it can handle any kind of characters.
     *
     * Runtime: 3 ms Beats 18.67%
     * Memory: 41.9 MB Beats 46.14%
     *
     * Time: O(N)
     * Space: O(N)
     */
    public boolean canPermutePalindrome(String s) {

        /**
         * edge conditions
         */
        if (s == null) {
            return false;
        }

        /**
         * build count map, so that the string can be any kind of chars
         *
         * Trick: Use Map<Character, Intetger> instead of int[] count, so that it can handle any kind of characters.
         */
        final Map<Character, Integer> map = new HashMap<>();

        for (int i = 0, len = s.length(); i < len; i++) {
            char ch = s.charAt(i);
            map.merge(ch, 1, (oldValue, _) -> oldValue + 1);
        }

        Boolean seen = null;
        for (Integer v : map.values()) {
            if (v % 2 == 1) {
                if (seen == null) {
                    /**
                     * first seen
                     */
                    seen = true;
                } else {
                    /**
                     * seen twice
                     */
                    return false;
                }
            }
        }

        /**
         * never seen or seen once
         */
        return seen == null || seen;
    }

    /**
     * LC - Better count
     *    - Trick: Only one or none char can have odd count
     */
    public boolean canPermutePalindromeLc(String s) {

        /**
         * edge conditions
         */
        if (s == null) {
            return false;
        }

        /**
         * build count map, so that the string can be any kind of chars
         *
         * Trick: Use Map<Character, Intetger> instead of int[] count, so that it can handle any kind of characters.
         */
        final Map<Character, Integer> map = new HashMap<>();

        for (int i = 0, len = s.length(); i < len; i++) {
            char ch = s.charAt(i);
            map.merge(ch, 1, (oldValue, _) -> oldValue + 1);
        }

        int count = 0;
        for (Integer v : map.values()) {
            count += v % 2;
        }

        return count <= 1;
    }

    /**
     * Time: O(N)
     * Space: O(N)
     */
    public boolean canPermutePalindromeAsci(String s) {

        /**
         * All lower case Engilish letters
         */
        final int[] freq = new int[('z') + 1];

        for (char ch : s.toCharArray()) {
            int idx = ch;
            freq[idx]++;
        }

        boolean isOdd = s.length() % 2 == 1;

        int count = 0;
        for (int i = 0; i < freq.length; i++) {
            count += freq[i] % 2;
            if (isOdd) {
                if (count == 2) {
                    return false;
                }
            } else {
                if (count == 1) {
                    return false;
                }
            }
        }

        return true;

        /**
         * Or
         */
        // return count <= 2;
    }
}
