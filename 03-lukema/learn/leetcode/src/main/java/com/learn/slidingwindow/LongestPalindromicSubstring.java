package com.learn.slidingwindow;


import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Assertions;

import lombok.extern.log4j.Log4j2;


/**
 * LC - 5 - Longest Palindromic Substring
 *
 * Medium
 *
 * Given a string s, return the longest palindromic substring in s.
 *
 * A string is called a palindrome string if the reverse of that string is the same as the original string.
 *
 * Example 1:
 * Input: s = "babad"
 * Output: "bab"
 * Explanation: "aba" is also a valid answer.
 *
 * Example 2:
 * Input: s = "cbbd"
 * Output: "bb"
 *
 * Constraints:
 *     1 <= s.length <= 1000
 *     s consist of only digits and English letters.
 */
@Log4j2
public class LongestPalindromicSubstring {

    public static void main(String[] args) {

        /**
         * Expected: aba
         */
        final String s = "babad";
        // final String s = "cbbd";
        // final String s = "cb";
        // final String s = "aaaa";

        LongestPalindromicSubstring longestPalindromicSubstring = new LongestPalindromicSubstring();

        var longestPalindromeLukeBrute = longestPalindromicSubstring.longestPalindromeLukeBrute(s);
        log.debug("Longest Palindromic Substring: {}", () -> longestPalindromeLukeBrute);
        log.debug("Longest Palindromic Substring {} OK", () -> "longestPalindromeLukeBrute");

        var longestPalindromeLukeExpendFromCenter = longestPalindromicSubstring.longestPalindromeLukeExpendFromCenter(s);
        Assertions.assertEquals(longestPalindromeLukeBrute, longestPalindromeLukeExpendFromCenter);
        log.debug("Longest Palindromic Substring {} OK", () -> "longestPalindromeLukeExpendFromCenter");

    }

    /**
     * Luke - Expend from ceneter
     *
     * Runtime: 35 ms Beats 65.51%
     * Memory: 42 MB Beats 95.26%
     *
     * Time: O(N ^ 2)
     * Space: O(1)
     */
    public String longestPalindromeLukeExpendFromCenter(String s) {
        if (s == null || s.length() <= 1) {
            return s;
        }

        int wid = 0;
        int start = 0, end = 0;

        for (int i = 0, n = s.length(); i < n; i++) {
            int wid1 = expendFromCenterReturnWidth(s, i, i);
            int wid2 = expendFromCenterReturnWidth(s, i, i + 1);

            int tmp = Math.max(wid1, wid2);

            if (tmp > wid) {
                wid = tmp;

                start = i - wid / 2;
                end = wid2 >= wid1 ? i + 1 + wid2 / 2 : i + wid1 / 2;

                // log.debug("len: {}, start: {}, end: {}, i: {}, len1: {}, len2: {}", wid, start, end, i, wid1, wid2);
            }
        }

        return s.substring(start, end + 1);
    }

    /**
     * returns - width
     *
     * Time: O(N)
     * Space: O(1)
     */
    private int expendFromCenterReturnWidth(String s, int left, int right) {

        int n = s.length();

        while (left >= 0 && right < n && s.charAt(left) == s.charAt(right)) {
            left--;
            right++;
        }

        left++;
        right--;

        return right - left;
    }

    /**
     * Luke - momo
     *
     * 6:45 - 8:15
     *
     * Time Limit Exceeded
     *
     * Time: O(N ^ 3)
     * Space: O(N)
     */
    record Window(int left, int right) {
    }

    public String longestPalindromeLukeBrute(String s) {
        if (s == null || s.length() <= 1) {
            return s;
        }

        final Map<Window, Boolean> memo = new HashMap<>();

        /**
         * Time: O(N ^ 2)
         */
        for (int i = 0, n = s.length(); i < n; i++) {
            for (int k = n - 1; k >= i; k--) {
                if (s.charAt(i) != s.charAt(k)) {
                    continue;
                } else if (isPalinedromeLuke(s, i, k, memo)) {
                    break;
                }
            }
        }

        log.debug("memo: {}", memo);

        /**
         * Time: O(N) + O(N log(N))
         */
        var win = memo.keySet().stream().filter(key -> memo.get(key)).sorted((a, b) -> -(a.right - a.left) + (b.right - b.left)).toList().get(0);

        return s.substring(win.left, win.right + 1);
    }

    /**
     * Time: O(right - left)
     * Space: O((right - left) / 2) ---- stack size + map size
     */
    private boolean isPalinedromeLuke(String s, int left, int right, final Map<Window, Boolean> memo) {
        if (left > right) {
            return false;
        } else {
            Window window = new Window(left, right);
            if (memo.containsKey(window)) {
                return memo.get(window);
            } else {
                boolean isPalinedrome = false;
                if (left == right || (left + 1 == right && s.charAt(left) == s.charAt(right))) {
                    isPalinedrome = true;
                } else {
                    if (s.charAt(left) != s.charAt(right)) {
                        isPalinedrome = false;
                    } else {
                        isPalinedrome = isPalinedromeLuke(s, left + 1, right - 1, memo);
                    }
                }

                memo.put(window, isPalinedrome);
                return isPalinedrome;
            }
        }
    }

    /**
     * Approach 5: Manacher's Algorithm
     *
     * There is even an O(n) algorithm called Manacher's algorithm, explained here in detail.
     *
     * TL/DR
     *
     * https://en.wikipedia.org/wiki/Longest_palindromic_substring#Manacher's_algorithm
     */
}
