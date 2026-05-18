package com.learn.backtrack.redo;


import org.junit.jupiter.api.Assertions;

import lombok.extern.log4j.Log4j2;


/**
 * LC - 91 - Decoding Ways
 *
 * Medium
 *
 * A message containing letters from A-Z can be encoded into numbers using the following mapping:
 *
 * 'A' -> "1"
 * 'B' -> "2"
 * ...
 * 'Z' -> "26"
 *
 * To decode an encoded message, all the digits must be grouped then mapped back into letters using the reverse of the
 * mapping above (there may be multiple ways). For example, "11106" can be mapped into:
 *
 *     "AAJF" with the grouping (1 1 10 6)
 *     "KJF" with the grouping (11 10 6)
 *
 * Note that the grouping (1 11 06) is invalid because "06" cannot be mapped into 'F' since "6" is different from "06".
 *
 * Given a string s containing only digits, return the number of ways to decode it.
 *
 * The test cases are generated so that the answer fits in a 32-bit integer.
 *
 * Example 1:
 * Input: s = "12"
 * Output: 2
 * Explanation: "12" could be decoded as "AB" (1 2) or "L" (12).
 *
 * Example 2:
 * Input: s = "226"
 * Output: 3
 * Explanation: "226" could be decoded as "BZ" (2 26), "VF" (22 6), or "BBF" (2 2 6).
 *
 * Example 3:
 * Input: s = "06"
 * Output: 0
 * Explanation: "06" cannot be mapped to "F" because of the leading zero ("6" is different from "06").
 *
 * Constraints:
 *     1 <= s.length <= 100
 *     s contains only digits and may contain leading zero(s).
 */
@Log4j2
public class DecodingWays {

    public static void main(String[] args) {

        /**
         * Expected: 3
         */
        // final String s = "229";

        /**
         * Expected: 2
         * AB, L
         */
        // final String s = "12";

        /**
         * Expected: 1
         */
        // final String s = "10";

        /**
         * Expected: 1
         */
        // final String s = "210";

        /**
         * Expected: 1
         */
        // final String s = "2101";

        /**
         * Expected: 5
         */
        // final String s = "1123";

        /**
         * Expected: 3
         */
        final String s = "1201218";

        DecodingWays decodingWays = new DecodingWays();

        log.debug("s: {}", s);

        var numDecodingsMingKwong = decodingWays.numDecodingsMingKwong(s);
        log.debug("Decoding ways: {}", () -> numDecodingsMingKwong);
        log.debug("Decoding ways {} OK", () -> "numDecodingsMingKwong");

        var numDecodingsLukeIterative = decodingWays.numDecodingsLukeIterative(s);
        Assertions.assertEquals(numDecodingsMingKwong, numDecodingsLukeIterative);
        log.debug("Decoding ways {} OK", () -> "numDecodingsLukeIterative");

    }

    /**
     * Luke - DP - Iterative
     *
     * Runtime: 2 ms Beats 67.6%
     * Memory: 41.9 MB Beats 78.42%
     *
     * Time: O(N)
     * Space: O(1)
     */
    public int numDecodingsLukeIterative(String s) {

        if (s == null || s.isEmpty() || s.startsWith("0")) {
            return 0;
        }

        final char[] arr = s.toCharArray();
        final int N = arr.length;

        final int[] dp = new int[N];

        /**
         * base case
         */
        dp[0] = 1;

        char last = arr[0];

        for (int i = 1; i < arr.length; i++) {
            char ch = arr[i];

            if (ch == '0') {
                if (last > '2' || last == '0') {
                    return 0;
                } else {
                    /**
                     * last == '1' || last == '2'
                     */
                    dp[i] = (i - 2 < 0) ? 1 : dp[i - 2];
                }
            } else {
                int lastAndCurr = (last - '0') * 10 + (ch - '0');

                if (last == '0' || (lastAndCurr > 26)) {
                    dp[i] = dp[i - 1];
                } else if (lastAndCurr <= 26) {
                    dp[i] = (i - 2 < 0 ? 1 : dp[i - 2]) + dp[i - 1];
                } else {
                    dp[i] = (i - 2 < 0) ? 1 : dp[i - 2];
                }
            }

            last = ch;
        }

        log.debug("dp: {}", dp);

        return dp[N - 1];

    }

    /**
     * Kwong - DP
     *
     */
    public int numDecodingsMingKwong(String s) {
        // cannot map to any character due to the leading zero
        if (s.charAt(0) == '0') {
            return 0;
        }
        int n = s.length();

        // dp[i]: number of ways of decoding the substring s[:i]
        int[] dp = new int[n + 1];
        // base case
        dp[0] = 1;

        for (int i = 1; i <= n; i++) {
            // check single digit decode
            // valid deocde is possible only when s[i - 1] is not zero
            // if so, take the previous state dp[i - 1]
            // e.g. AB - 1[2]
            if (s.charAt(i - 1) != '0') {
                dp[i] = dp[i - 1];
            }
            // check double digit decode
            // by looking at the previous two digits
            // if the substring belongs to the range [10 - 26]
            // then add the previous state dp[i - 2]
            // e.g. L - [12]
            if (i >= 2) {
                // or you can use `stoi(s.substr(i - 2, 2))`
                int x = (s.charAt(i - 2) - '0') * 10 + s.charAt(i - 1) - '0';
                // check the range
                if (10 <= x && x <= 26) {
                    dp[i] += dp[i - 2];
                }
            }
        }

        log.debug("LC dp: {}", dp);

        return dp[n];
    }

    /**
     * LC - DP
     */
    public int numDecodingsLcDp(String s) {
        // DP array to store the subproblem results
        int[] dp = new int[s.length() + 1];
        dp[0] = 1;

        // Ways to decode a string of size 1 is 1. Unless the string is '0'.
        // '0' doesn't have a single digit decode.
        dp[1] = s.charAt(0) == '0' ? 0 : 1;

        for (int i = 2; i < dp.length; i++) {
            // Check if successful single digit decode is possible.
            if (s.charAt(i - 1) != '0') {
                dp[i] = dp[i - 1];
            }

            // Check if successful two digit decode is possible.
            int twoDigit = Integer.valueOf(s.substring(i - 2, i));
            if (twoDigit >= 10 && twoDigit <= 26) {
                dp[i] += dp[i - 2];
            }
        }

        return dp[s.length()];
    }

}
