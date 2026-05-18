package com.learn.lc75;


import org.junit.jupiter.api.Assertions;

import lombok.extern.log4j.Log4j2;


/**
 * LC - 392. Is Subsequence
 *
 * Easy
 *
 * Given two strings s and t, return true if s is a subsequence of t, or false otherwise.

A subsequence of a string is a new string that is formed from the original string by deleting some (can be none) of the characters without
disturbing the relative positions of the remaining characters. (i.e., "ace" is a subsequence of "abcde" while "aec" is not).


Example 1:

Input: s = "abc", t = "ahbgdc"
Output: true

Example 2:

Input: s = "axc", t = "ahbgdc"
Output: false


Constraints:

    0 <= s.length <= 100
    0 <= t.length <= 10 ^ 4
    s and t consist only of lowercase English letters.


Follow up: Suppose there are lots of incoming s, say s1, s2, ..., sk where k >= 10 ^ 9, and you want to check one by one to see if t has its
           subsequence. In this scenario, how would you change your code?
 */

@Log4j2
public class IsSubsequence {

    public static void main(String[] args) {

        IsSubsequence isSubsequence = new IsSubsequence();

        String s = "abc", t = "ahbgdc";

        var ret = isSubsequence.isSubsequence(s, t);
        log.debug("Is Subsequence: {}", () -> ret);
        Assertions.assertEquals(true, ret);
        log.debug("Is Subsequence {} OK", () -> "isSubsequence");

    }

    /**
     * Time: O(n)
     * Space: O(1)
     *
     * Runtime: 1 ms Beats 88.90%
     * Memory: 40.6 MB Beats 66.99%
     */
    public boolean isSubsequence(String s, String t) {
        if(s.length() == 0) {
            return true;
        }

        int pos = 0;
        int i = 0, len = 0;
        boolean isMatch = false;
        for (i = 0, len = s.length(); i < len; i++) {
            char ch = s.charAt(i);
            isMatch = false;
            while (pos < t.length()) {
                if (ch == t.charAt(pos++)) {
                    isMatch = true;
                    break;
                }
            }
        }

        if (i == s.length() && isMatch) {
            return true;
        }

        return false;
    }

}
