package com.learn.dp;


import org.junit.jupiter.api.Assertions;

import lombok.extern.log4j.Log4j2;


/**
 * LC - 10 Regular Expression Matching
 *
 * Hard
 *
 * Given an input string s and a pattern p, implement regular expression matching with support for '.' and '*' where:
 *
 *     '.' Matches any single character.​​​​
 *     '*' Matches zero or more of the preceding element.
 *
 * The matching should cover the entire input string (not partial).
 *
 * Example 1:
 * Input: s = "aa", p = "a"
 * Output: false
 * Explanation: "a" does not match the entire string "aa".
 *
 * Example 2:
 * Input: s = "aa", p = "a*"
 * Output: true
 * Explanation: '*' means zero or more of the preceding element, 'a'. Therefore, by repeating 'a' once, it becomes "aa".
 *
 * Example 3:
 * Input: s = "ab", p = ".*"
 * Output: true
 * Explanation: ".*" means "zero or more (*) of any character (.)".
 *
 * Constraints:
 *     1 <= s.length <= 20
 *     1 <= p.length <= 30
 *     s contains only lowercase English letters.
 *     p contains only lowercase English letters, '.', and '*'.
 *     It is guaranteed for each appearance of the character '*', there will be a previous valid character to match.
 */
@Log4j2
public class RegularExpressionMatching {

    public static void main(String[] args) {

        /**
         * Expected: true
         */
        // final String s = "aa", p = "a*";

        /**
         * Expected: true
         */
        // final String s = "aab", p = "c*a*b";

        /**
         * Expected: true
         */
        // final String s = "ab", p = ".*c";

        /**
         * Expected: true
         */
        final String s = "aabcbcbcaccbcaabc", p = ".*a*aa*.*b*.c*.*a*";

        RegularExpressionMatching regularExpressionMatching = new RegularExpressionMatching();

        var isMatchLcDp = regularExpressionMatching.isMatchLcDp(s, p);
        log.debug("Regular Expression Matching: {}", () -> isMatchLcDp);
        log.debug("Regular Expression Matching {} OK", () -> "isMatchLcDp");

        var isMatchLukeRecursion = regularExpressionMatching.isMatchLukeRecursion(s, p);
        Assertions.assertEquals(isMatchLcDp, isMatchLukeRecursion);
        log.debug("Regular Expression Matching {} OK", () -> "isMatchLukeRecursion");

        var isMatchLukeDp = regularExpressionMatching.isMatchLukeDp(s, p);
        Assertions.assertEquals(isMatchLcDp, isMatchLukeDp);
        log.debug("Regular Expression Matching {} OK", () -> "isMatchLukeDp");

    }

    /**
     * Luke - DP - BottomUp
     *
     * Runtime: 6 ms Beats 54.99%
     * Memory: 42.8 MB Beats 43.15%
     *
     * Time: O(T * P)
     * Space: O(T * P)
     */
    public boolean isMatchLukeDp(String text, String pattern) {

        boolean[][] dp = new boolean[text.length() + 1][pattern.length() + 1];

        dp[text.length()][pattern.length()] = true;

        for (int idxText = text.length(); idxText >= 0; idxText--) {
            for (int idxPattern = pattern.length() - 1; idxPattern >= 0; idxPattern--) {

                boolean isFirstCharMatch = idxText < text.length() &&
                        (pattern.charAt(idxPattern) == text.charAt(idxText) || pattern.charAt(idxPattern) == '.');

                if (idxPattern < pattern.length() - 1 && pattern.charAt(idxPattern + 1) == '*') {
                    // ".*" or "a*, b*, ..."
                    dp[idxText][idxPattern] = dp[idxText][idxPattern + 2] || (isFirstCharMatch && dp[idxText + 1][idxPattern]);
                } else {
                    dp[idxText][idxPattern] = isFirstCharMatch && dp[idxText + 1][idxPattern + 1];
                }
            }
        }

        return dp[0][0];
    }

    /**
     * LC - DP - Bottom-Up
     *
     * Time: O(T * P), with T, P be the lengths of the text and the pattern respectively.
     * Space: O(T * P)
     */
    public boolean isMatchLcDp(String text, String pattern) {

        boolean[][] dp = new boolean[text.length() + 1][pattern.length() + 1];
        dp[text.length()][pattern.length()] = true;

        for (int idxText = text.length(); idxText >= 0; idxText--) {
            for (int idxPattern = pattern.length() - 1; idxPattern >= 0; idxPattern--) {

                boolean isFirstCharMatch = (idxText < text.length() &&
                        (pattern.charAt(idxPattern) == text.charAt(idxText) || pattern.charAt(idxPattern) == '.'));

                if (idxPattern + 1 < pattern.length() && pattern.charAt(idxPattern + 1) == '*') {
                    dp[idxText][idxPattern] = dp[idxText][idxPattern + 2] || isFirstCharMatch && dp[idxText + 1][idxPattern];
                } else {
                    dp[idxText][idxPattern] = isFirstCharMatch && dp[idxText + 1][idxPattern + 1];
                }
            }
        }
        return dp[0][0];
    }

    /**
     * Luke - Recursion
     *
     * Runtime: 247 ms Beats 5%
     * Memory: 118.6 MB Beats 5.68%
     *
     * Time: O(2 ^ (T + P / 2)) * O(T + P)
     * Space: O(2 ^ (T + P / 2)) * O(T + P)
     */
    public boolean isMatchLukeRecursion(String text, String pattern) {
        /**
         * TODO: Normalize pattern: Remove extra [*.] from pattern.
         */
        // TODO

        if (pattern.isEmpty()) {
            return text.isEmpty();
        }

        boolean isFirstCharMatch = !text.isEmpty() &&
                (pattern.charAt(0) == '.' || pattern.charAt(0) == text.charAt(0));

        if (pattern.length() >= 2 && pattern.charAt(1) == '*') {
            // super sub-pattern ".*" or "?*"
            return isMatchLukeRecursion(text, pattern.substring(2)) ||
                    (isFirstCharMatch && isMatchLukeRecursion(text.substring(1), pattern));
        } else {
            // first char match. text next char
            return isFirstCharMatch && isMatchLukeRecursion(text.substring(1), pattern.substring(1));
        }
    }

    /**
     * LC - Recursion
     *
     * Time: O((T + P) * (2 ^ (T + P / 2)​), with T, P be the lengths of the text and the pattern respectively.
     * Space: O((T + P) * (2 ^ (T + P / 2)​)
     */
    public boolean isMatchLcRecursion(String text, String pattern) {
        if (pattern.isEmpty()) {
            return text.isEmpty();
        }

        boolean isFirstCharMatch = (!text.isEmpty() &&
                (pattern.charAt(0) == text.charAt(0) || pattern.charAt(0) == '.'));

        if (pattern.length() >= 2 && pattern.charAt(1) == '*') {
            return (isMatchLcRecursion(text, pattern.substring(2)) ||
                    (isFirstCharMatch && isMatchLcRecursion(text.substring(1), pattern)));
        } else {
            return isFirstCharMatch && isMatchLcRecursion(text.substring(1), pattern.substring(1));
        }
    }

}
