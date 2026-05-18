package com.learn.lc75;


import org.junit.jupiter.api.Assertions;

import lombok.extern.log4j.Log4j2;


/**
 * LC - 1456. Maximum Number of Vowels in a Substring of Given Length
 *
 * Medium
 *
 * Given a string s and an integer k, return the maximum number of vowel letters in any substring of s with length k.

Vowel letters in English are 'a', 'e', 'i', 'o', and 'u'.

Example 1:

Input: s = "abciiidef", k = 3
Output: 3
Explanation: The substring "iii" contains 3 vowel letters.

Example 2:

Input: s = "aeiou", k = 2
Output: 2
Explanation: Any substring of length 2 contains 2 vowels.

Example 3:

Input: s = "leetcode", k = 3
Output: 2
Explanation: "lee", "eet" and "ode" contain 2 vowels.



Constraints:

    1 <= s.length <= 10 ^ 5
    s consists of lowercase English letters.
    1 <= k <= s.length
 */

@Log4j2
public class MaxNumberOfVowelsInSubstring {

    public static void main(String[] args) {

        MaxNumberOfVowelsInSubstring maxNumberOfVowelsInSubstring = new MaxNumberOfVowelsInSubstring();

        // String s = "abciiidef";
        // int k = 3;
        // int expected = 3;

        String s = "tnfazcwrryitgacaabwm";
        int k = 4;
        int expected = 3;

        var ret = maxNumberOfVowelsInSubstring.maxVowels(s, k);

        log.debug("Maximum Number of Vowels in a Substring of Given Length: {}", () -> ret);
        Assertions.assertEquals(expected, ret);
        log.debug("Maximum Number of Vowels in a Substring of Given Length {} OK", () -> "maxVowels");

    }

    /**
     * Time: O(n)
     * Space: O(1)
     *
     * Runtime: 16 ms Beats 69.48%
     * Memory: 43.6 MB Beats 76.68%
     */
    public int maxVowels(String s, int k) {
        int right = 0;

        int len = s.length();

        if (k == 0) {
            return 0;
        }

        if (k == 1) {
            while (right < len) {
                if (isVowel(s.charAt(right++))) {
                    return 1;
                }
            }
        }

        int max = 0;
        int curr = 0;

        while (right < k && right < len) {
            if (isVowel(s.charAt(right++))) {
                curr++;
            }
        }

        max = curr;
        int left = 0;

        while (right < len) {

            if (isVowel(s.charAt(right++))) {
                curr++;
            }

            if (isVowel(s.charAt(left++))) {
                curr--;
            }

            max = Math.max(max, curr);
        }

        return max;
    }

    boolean isVowel(char ch) {
        return ch == 'a' || ch == 'e' || ch == 'i' || ch == 'o' || ch == 'u';
    }
}
