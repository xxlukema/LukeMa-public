package com.learn.backtrack.redo;


import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Assertions;

import lombok.extern.log4j.Log4j2;


/**
 * LC - 87 - Scramble String
 *
 * Hard
 *
 * We can scramble a string s to get a string t using the following algorithm:
 *
 *     If the length of the string is 1, stop.
 *     If the length of the string is > 1, do the following:
 *         Split the string into two non-empty substrings at a random index, i.e., if the string is s, divide it to x and y where s = x + y.
 *         Randomly decide to swap the two substrings or to keep them in the same order. i.e., after this step, s may become s = x + y or s = y + x.
 *         Apply step 1 recursively on each of the two substrings x and y.
 *
 * Given two strings s1 and s2 of the same length, return true if s2 is a scrambled string of s1, otherwise, return false.
 *
 * Example 1:
 * Input: s1 = "great", s2 = "rgeat"
 * Output: true
 * Explanation: One possible scenario applied on s1 is:
 * "great" --> "gr/eat" // divide at random index.
 * "gr/eat" --> "gr/eat" // random decision is not to swap the two substrings and keep them in order.
 * "gr/eat" --> "g/r / e/at" // apply the same algorithm recursively on both substrings. divide at random index each of them.
 * "g/r / e/at" --> "r/g / e/at" // random decision was to swap the first substring and to keep the second substring in the same order.
 * "r/g / e/at" --> "r/g / e/ a/t" // again apply the algorithm recursively, divide "at" to "a/t".
 * "r/g / e/ a/t" --> "r/g / e/ a/t" // random decision is to keep both substrings in the same order.
 * The algorithm stops now, and the result string is "rgeat" which is s2.
 * As one possible scenario led s1 to be scrambled to s2, we return true.
 *
 * Example 2:
 * Input: s1 = "abcde", s2 = "caebd"
 * Output: false
 *
 * Example 3:
 * Input: s1 = "a", s2 = "a"
 * Output: true
 *
 * Constraints:
 *     s1.length == s2.length
 *     1 <= s1.length <= 30
 *     s1 and s2 consist of lowercase English letters.
 */
@Log4j2
public class ScrambleString {

    public static void main(String[] args) {

        final String s1 = "great", s2 = "rgeat";

        ScrambleString scrambleString = new ScrambleString();

        var isScrambleLcNonOfficial = scrambleString.isScrambleLcNonOfficial(s1, s2);
        log.debug("Scramble String: {}", () -> isScrambleLcNonOfficial);
        log.debug("Scramble String {} OK", () -> "isScrambleLcNonOfficial");

        var isScrambleLuke = scrambleString.isScrambleLuke(s1, s2);
        Assertions.assertEquals(isScrambleLcNonOfficial, isScrambleLuke);
        log.debug("Scramble String {} OK", () -> "isScrambleLuke");

        // char[] chs = { 'a', 'b' };
        // char[] tmp = new char[chs.length];
        // scrambleString.scramble(chs, 0, 1, chs.length - 1, tmp);
        // log.debug("chs: {}", chs);

    }

    public boolean isScrambleLuke(String s1, String s2) {
        if (s1.length() != s2.length()) {
            return false;
        }

        if (s1.equals(s2)) {
            return true;
        }

        final char[] chs1 = s1.toCharArray();
        Map<Character, Integer> freq1 = new HashMap<>();

        for (char ch : chs1) {
            if (freq1.containsKey(ch)) {
                freq1.put(ch, freq1.get(ch) + 1);
            } else {
                freq1.put(ch, 1);
            }
        }

        final char[] chs2 = s2.toCharArray();
        Map<Character, Integer> freq2 = new HashMap<>();

        for (char ch : chs2) {
            if (freq2.containsKey(ch)) {
                freq2.put(ch, freq2.get(ch) + 1);
            } else {
                freq2.put(ch, 1);
            }
        }

        for (char ch : chs1) {
            if (freq2.get(ch) == null || freq2.get(ch).intValue() != freq1.get(ch).intValue()) {
                return false;
            }
        }

        final char[] tmp = new char[chs1.length];

        for (int i = 1, n = chs1.length - 1; i <= n; i++) {
            boolean isScramble = backtrack(chs1, 0, i, n, chs2, tmp);
            if (isScramble) {
                return true;
            }
        }

        return false;
    }

    private boolean backtrack(final char[] chs1, final int left, final int idxCut, final int right, final char[] chs2, final char[] tmp) {
        if (isEqual(chs1, chs2)) {
            return true;
        }

        //

        for (int i = left; i < idxCut; i++) {
            scramble(chs1, left, i, idxCut, tmp);
            if (isEqual(chs1, chs2)) {
                return true;
            }
        }

        return false;
    }

    void scramble(final char[] chs1, final int left, final int idxCut, final int right, final char[] tmp) {
        if (left == right) {
            return;
        }

        for (int i = left; i <= right; i++) {
            tmp[i] = chs1[i];
        }

        int pos = idxCut;

        for (int i = left; i <= right; i++) {
            chs1[i] = tmp[pos++];
            if (pos > right) {
                pos = left;
            }
        }
    }

    boolean isEqual(final char[] chs1, final char[] chs2) {
        for (int i = 0, n = chs1.length; i < n; i++) {
            if (chs1[i] != chs2[i]) {
                return false;
            }
        }

        return true;
    }

    public boolean isScrambleLcNonOfficial(String s1, String s2) {

        if (s1.length() != s2.length()) {
            return false;
        }

        if (s1.equals(s2)) {
            return true;
        }

        final Map<String, Boolean> memo = new HashMap<>();

        return solveLcNonOfficial(s1, s2, memo);
    }

    public boolean solveLcNonOfficial(String s1, String s2, Map<String, Boolean> memo) {

        if (s1.compareTo(s2) == 0) {
            return true;
        }

        int n = s1.length();

        if (n <= 1) {
            return false;
        }

        String key = s1 + "_" + s2;

        if (memo.containsKey(key)) {
            return memo.get(key);
        }

        boolean flag = false;

        for (int i = 1; i < n; i++) {

            /**
             * n = 5
             * 0 1 2 3 4
             * g r e a t
             */
            if ((solveLcNonOfficial(s1.substring(0, i), s2.substring(n - i, n), memo) &&
                    (solveLcNonOfficial(s1.substring(i, n), s2.substring(0, n - i), memo))) ||
                    ((solveLcNonOfficial(s1.substring(0, i), s2.substring(0, i), memo)) &&
                            (solveLcNonOfficial(s1.substring(i, n), s2.substring(i, n), memo)))) {
                flag = true;
                break;
            }
        }

        memo.put(key, flag);

        return flag;
    }

}
