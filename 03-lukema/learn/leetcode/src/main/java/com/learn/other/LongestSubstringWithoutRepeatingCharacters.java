package com.learn.other;


import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Assertions;

import lombok.extern.log4j.Log4j2;


/**
 * LC - 3 - Longest Substring Without Repeating Characters
 *
 * Medium
 *
 * Given a string s, find the length of the longest substring without repeating characters.
 *
 * Example 1:
 * Input: s = "abcabcbb"
 * Output: 3
 * Explanation: The answer is "abc", with the length of 3.
 *
 * Example 2:
 * Input: s = "bbbbb"
 * Output: 1
 * Explanation: The answer is "b", with the length of 1.
 *
 * Example 3:
 * Input: s = "pwwkew"
 * Output: 3
 * Explanation: The answer is "wke", with the length of 3.
 *
 * Notice that the answer must be a substring, "pwke" is a subsequence and not a substring.
 *
 * Constraints:
 *     0 <= s.length <= 5 * 104
 *     s consists of English letters, digits, symbols and spaces.
 */
@Log4j2
public class LongestSubstringWithoutRepeatingCharacters {

    public static void main(String[] args) {

        /**
         * Expected: 3
         */
        // final String s = "abcabcbb";

        /**
         * Expected: 3
         */
        final String s = "pwwkew";

        /**
         * Expected: 2
         */
        // final String s = "abba";

        /**
         * Expected: 3
         */
        // final String s = "dvdf";

        /**
         * Expected: 4
         */
        // final String s = "tmmzuxt";

        /**
         * Expected: 4
         */
        // final String s = "uqinntq";

        /**
         * Expected: 2
         */
        // final String s = "aab";

        LongestSubstringWithoutRepeatingCharacters longestSubstringWithoutRepeatingCharacters = new LongestSubstringWithoutRepeatingCharacters();

        var lengthOfLongestSubstringLuke = longestSubstringWithoutRepeatingCharacters.lengthOfLongestSubstringLuke(s);
        log.debug("Longest Substring Without Repeating Characters: {}", () -> lengthOfLongestSubstringLuke);
        log.debug("Longest Substring Without Repeating Characters {} OK", () -> "lengthOfLongestSubstringLuke");

        var lengthOfLongestSubstringSlidingWindow = longestSubstringWithoutRepeatingCharacters.lengthOfLongestSubstringSlidingWindowLc(s);
        Assertions.assertEquals(lengthOfLongestSubstringLuke, lengthOfLongestSubstringSlidingWindow);
        log.debug("Longest Substring Without Repeating Characters {} OK", () -> "lengthOfLongestSubstringSlidingWindow");
    }

    /**
     * Luke - DP + Lookback Map
     *
     * Runtime 12 ms Beats 66.26%
     * Memory 45.5 MB Beats 45.26%
     *
     * Time: O(N)
     * Space: O(N)
     */
    public int lengthOfLongestSubstringLuke(String s) {

        final int[] dp = new int[s.length()];

        /**
         * Space: O(N)
         */
        Map<Character, Integer> seen = new HashMap<>();

        int start = 0;

        /**
         * Time: O(N)
         * Space: O(N)
         */
        char[] chars = s.toCharArray();

        int maxLen = 0;

        for (int right = 0, n = chars.length; right < n; right++) {
            char ch = chars[right];
            Character c = Character.valueOf(ch);
            /**
             * seen.get(): Time O(1)
             */
            if (seen.get(c) == null) {
                dp[right] = (right == 0) ? 1 : dp[right - 1] + 1;
            } else {
                int idx = seen.get(c);
                if (idx >= start) {
                    // seen
                    dp[right] = right - idx;
                    start = idx + 1;
                } else {
                    /**
                     * seen.put() Time: O(1)
                     */
                    dp[right] = dp[right - 1] + 1;
                }
            }

            seen.put(c, right);

            maxLen = Math.max(maxLen, dp[right]);
        }

        // final String s = "pwwkew";
        // final String s = "aab";
        // final String s = "uqinntq";
        log.debug("dp: {}", () -> dp);

        return maxLen;
    }

    /**
     * LC - Sliding Window
     *
     * Runtime: 11 ms Beats 75.6%
     * Memory: 44.4 MB Beats 67.84%
     *
     * Time: O(N)
     * Space: O(min(M, N))
     */
    public int lengthOfLongestSubstringSlidingWindowLc(String s) {
        int n = s.length(), maxLen = 0;
        Map<Character, Integer> seen = new HashMap<>(); // current index of character

        for (int left = 0, right = 0; right < n; right++) {
            Character ch = s.charAt(right);

            if (seen.containsKey(ch)) {
                left = Math.max(seen.get(ch), left);
            }
            maxLen = Math.max(maxLen, right - left + 1);
            seen.put(ch, right + 1);
        }
        return maxLen;
    }
}
