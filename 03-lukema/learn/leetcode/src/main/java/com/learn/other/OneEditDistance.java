package com.learn.other;


import lombok.extern.log4j.Log4j2;


/**
 * LC - 161 - One edit Distance
 * 
 * Medium
 * 
 * Given two strings s and t, return true if they are both one edit distance apart, otherwise return false.
 * 
 * A string s is said to be one distance apart from a string t if you can:
 *     Insert exactly one character into s to get t.
 *     Delete exactly one character from s to get t.
 *     Replace exactly one character of s with a different character to get t.
 * 
 * Example 1:
 * Input: s = "ab", t = "acb"
 * Output: true
 * Explanation: We can insert 'c' into s to get t.
 * 
 * Example 2:
 * Input: s = "", t = ""
 * Output: false
 * Explanation: We cannot get t from s by only one step.
 * 
 * Constraints:
 *     0 <= s.length, t.length <= 104
 *     s and t consist of lowercase letters, uppercase letters, and digits.
 */
@Log4j2
public class OneEditDistance {

    public static void main(String[] args) {

        /**
         * Output: true
         */
        // final String s = "ab", t = "acb";

        /**
         * Output: true
         */
        // final String s = "wwwxa", t = "wwwba";

        /**
         * Output: false;
         */
        // final String s = "", t = "";

        /**
         * Output: false;
         */
        final String s = "wae", t = "wbe";

        OneEditDistance oneEditDistance = new OneEditDistance();

        var ret = oneEditDistance.isOneEditDistance(s, t);
        log.debug("One edit distance: {}", () -> ret);
    }

    /**
     * Luke - Two Pointer
     * 
     * Runtime: 1 ms, faster than 98.08% of Java online submissions for One Edit Distance.
     * Memory Usage: 43.5 MB, less than 8.95% of Java online submissions for One Edit Distance.
     * 
     * Time: O(N)
     * Space: O(1)
     */
    public boolean isOneEditDistance(final String s, final String t) {

        int left = 0;

        final int S = s.length();
        final int T = t.length();

        if (Math.abs(S - T) > 1) {
            return false;
        }

        if (s.equals(t)) {
            return false;
        }

        int minLen = Math.min(S, T);

        while (left < minLen && s.charAt(left) == t.charAt(left)) {
            left++;
        }

        left = left - 1;

        int right = 1;

        while (S - right >= 0 && T - right >= 0 && s.charAt(S - right) == t.charAt(T - right)) {
            right++;
        }

        right = right - 1;

        log.debug("left: {}, right: {}", left, right);

        int num = left + right;

        log.debug(S - num);
        log.debug(T - num);

        return Math.max(S, T) - num <= 2;
    }
}
