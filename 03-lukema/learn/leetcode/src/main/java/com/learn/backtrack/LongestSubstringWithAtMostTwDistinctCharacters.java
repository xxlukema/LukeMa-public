package com.learn.backtrack;


import lombok.extern.log4j.Log4j2;


/**
 * LC - 159 - Longest substring with at most two distinct characters
 *
 * Medium
 *
 * Given a string s, return the length of the longest substring that contains at most two distinct characters.
 *
 * Example 1:
 * Input: s = "eceba"
 * Output: 3
 * Explanation: The substring is "ece" which its length is 3.
 *
 * Example 2:
 * Input: s = "ccaabbb"
 * Output: 5
 * Explanation: The substring is "aabbb" which its length is 5.
 *
 * Constraints:
 *     1 <= s.length <= 105
 *     s consists of English letters.
 */
@Log4j2
public class LongestSubstringWithAtMostTwDistinctCharacters {

    public static void main(String[] args) {

        /**
         * Output: 3
         */
        // final String s = "eceba";

        /**
         * Output: 5
         */
        // final String s = "ccaabbb";

        /**
         * Output: 10
         */
        final String s = "abccbbcccaaacaca";

        LongestSubstringWithAtMostTwDistinctCharacters longestSubstringWithAtMostTwDistinctCharacters = new LongestSubstringWithAtMostTwDistinctCharacters();

        int ret = longestSubstringWithAtMostTwDistinctCharacters.lengthOfLongestSubstringTwoDistinct(s);
        log.debug("Longest substring with at most two distinct characters: {}", () -> ret);
        log.debug("Longest substring with at most two distinct characters {} OK", () -> "ret");

    }

    /**
     * Luke: Iterative
     *
     * Runtime: 11 ms, faster than 96.49% of Java online submissions for Longest Substring with At Most Two Distinct Characters.
     * Memory Usage: 42.8 MB, less than 98.09% of Java online submissions for Longest Substring with At Most Two Distinct Characters.
     *
     * Time: O(N)
     * Space: O(1)
     */
    public int lengthOfLongestSubstringTwoDistinct(String s) {
        int max = 0;

        for (int left = 0, n = s.length(); left < n;) {
            char chLeft = s.charAt(left);
            char chSencond = chLeft;
            int nextLeft = left + 1;
            int right = left + 1;
            for (; right < n; right++) {
                if (s.charAt(right) == chLeft) {
                    continue;
                } else {
                    if (chSencond == chLeft) {
                        chSencond = s.charAt(right);
                        nextLeft = right;
                        continue;
                    } else {
                        if (s.charAt(right) == chSencond) {
                            continue;
                        } else {
                            max = Math.max(max, right - left);
                            left = nextLeft;
                            break;
                        }
                    }
                }
            }

            if (right == n) {
                max = Math.max(max, right - left);
                break;
            }
        }

        return max;
    }
}
