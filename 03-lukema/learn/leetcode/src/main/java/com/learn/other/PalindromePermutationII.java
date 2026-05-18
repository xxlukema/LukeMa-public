package com.learn.other;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.extern.log4j.Log4j2;


/**
 * LC-267 Palindrome Permutation II
 *
 * Medium
 *
 * Given a string s, return all the palindromic permutations (without duplicates) of it.
 *
 * You may return the answer in any order. If s has no palindromic permutation, return an empty list.
 *
 * Example 1:
 * Input: s = "aabb"
 * Output: ["abba","baab"]
 *
 * Example 2:
 * Input: s = "abc"
 * Output: []
 *
 * Constraints:
 *     1 <= s.length <= 16
 *     s consists of only lowercase English letters.
 */
@Log4j2
public class PalindromePermutationII {

    public static void main(String[] args) {

        // final String s = "aabb";

        final String s = "aaaa";

        PalindromePermutationII palindromePermutationII = new PalindromePermutationII();

        var ret = palindromePermutationII.generatePalindromes(s);
        log.debug("Palindorme Permutation II: {}", () -> ret);
        log.debug("Palindorme Permutation II {} OK", () -> "ret");
    }

    /**
     * Luke - (1) rule out non-palindrome strings. (2) get half of the repeating chars. (3) build left half. (4) left + center + reverse(left)
     *      - Same as LC solution
     *
     * Runtime: 44 ms Beats 56.75%
     * Memory: 50.9 MB Beats 77.85%
     *
     * Time: O(N ^ 2)
     * Space: O(N)
     */
    public List<String> generatePalindromes(String s) {
        final List<String> result = new ArrayList<>();
        if (s == null || s.isEmpty()) {
            return result;
        }

        /*
        if (!isPermutatable(s)) {
            return result;
        }
        */

        /**
         * Take half amount of permutable chars and do permutation. The right side is miror of left side.
         * Same as LC solution.
         */

        final int[] freqs = new int[((int) 'z') + 1];

        for (char ch : s.toCharArray()) {
            int idx = (int) ch;
            freqs[idx]++;
        }

        int count = 0;

        for (int freq : freqs) {
            count += freq % 2;
        }

        if (count > 1) {
            return result;
        }

        StringBuilder sb = new StringBuilder();

        boolean isOddLength = s.length() % 2 == 1;
        Character center = null;

        for (int i = 0; i < freqs.length; i++) {
            if (isOddLength && freqs[i] % 2 == 1) {
                center = Character.valueOf((char) i);
            }

            for (int k = 0, len = freqs[i] / 2; k < len; k++) {
                sb.append((char) i);
            }
        }

        final Set<String> leftSideSet = new HashSet<>();
        final Set<Integer> seen = new HashSet<>();

        backtrack(sb, 0, "", leftSideSet, seen);

        for (String left : leftSideSet) {
            String right = new StringBuilder(left).reverse().toString();
            if (center == null) {
                result.add(left + right);
            } else {
                result.add(left + center + right);
            }
        }

        return result;
    }

    private void backtrack(
            final StringBuilder sb,
            final int startIdx,
            final String fromParent,
            final Set<String> leftSideSet,
            final Set<Integer> seen) {

        /**
         * edge condition
         */
        if (startIdx == sb.length()) {
            leftSideSet.add(fromParent);
            return;
        }

        for (int i = 0; i < sb.length(); i++) {
            /*
            if (i > startIdx && sb.charAt(i) == sb.charAt(startIdx)) {
                continue;
            }
            */
            if (seen.contains(i)) {
                continue;
            }
            seen.add(i);
            backtrack(sb, startIdx + 1, fromParent + sb.charAt(i), leftSideSet, seen);
            seen.remove(i);
        }
    }

    /**
     * Time: O(N)
     * Space: O(N)
     */
    boolean isPermutatable(String s) {

        /**
         * All lower case Engilish letters
         */
        final int[] freqs = new int[((int) 'z') + 1];

        for (char ch : s.toCharArray()) {
            int idx = (int) ch;
            freqs[idx]++;
        }

        int count = 0;

        for (int freq : freqs) {
            count += freq % 2;
        }

        return count <= 1;

        /*
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
        */
    }
}
