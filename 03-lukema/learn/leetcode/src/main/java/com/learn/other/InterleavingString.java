package com.learn.other;


import org.junit.jupiter.api.Assertions;

import lombok.extern.log4j.Log4j2;


/**
 * LC 97
 */
@Log4j2
public class InterleavingString {

    public static void main(String[] args) {

        // String s1 = "aabcc";
        // String s2 = "dbbca";
        // String s3 = "aadbbbaccc";

        String s1 = "aabcc", s2 = "dbbca", s3 = "aadbbcbcac";
        // String s1 = "a", s2 = "c", s3 = "ac";

        // String s1 = "aa", s2 = "ab", s3 = "abaa";
        // String s1 = "", s2 = "a", s3 = "a";
        // String s1 = "ab", s2 = "cd", s3 = "abcd";

        InterleavingString interleavingString = new InterleavingString();

        boolean retLukeDp = interleavingString.isInterleaveLukeDp(s1, s2, s3);
        log.debug("Interleaving String DP: {}", () -> retLukeDp);

        boolean retLcRecursion = interleavingString.isInterleaveLcRecursion(s1, s2, s3);
        Assertions.assertEquals(retLcRecursion, retLukeDp);

        boolean retLcDp = interleavingString.isInterleaveLcDp(s1, s2, s3);
        Assertions.assertEquals(retLcDp, retLukeDp);

        boolean retLcDp1D = interleavingString.isInterleaveLcDp1D(s1, s2, s3);
        Assertions.assertEquals(retLcDp, retLcDp1D);

        boolean retLukeBreakConquer = interleavingString.isInterleaveLukeBreakConquer(s1, s2, s3);
        Assertions.assertEquals(retLcDp, retLukeBreakConquer);
    }

    /**
     * LC - DP - 1D
     *
     * Runtime: 5 ms, faster than 55.87% of Java online submissions for Interleaving String.
     * Memory Usage: 42.1 MB, less than 67.77% of Java online submissions for Interleaving String.
     *
     * Time: O(m * n), where m = s1.length() and n = s2.length()
     * Space: O(n)
     */
    public boolean isInterleaveLcDp1D(String s1, String s2, String s3) {
        if (s3.length() != s1.length() + s2.length()) {
            return false;
        }

        /**
         * s2 as cols
         */
        final boolean dp[] = new boolean[s2.length() + 1];

        for (int row = 0; row <= s1.length(); row++) {
            for (int col = 0; col <= s2.length(); col++) {
                if (row == 0 && col == 0) {
                    dp[col] = true;
                } else if (row == 0) {
                    dp[col] = dp[col - 1] && s2.charAt(col - 1) == s3.charAt(row + col - 1);
                } else if (col == 0) {
                    dp[col] = dp[col] && s1.charAt(row - 1) == s3.charAt(row + col - 1);
                } else {
                    dp[col] = (dp[col] && s1.charAt(row - 1) == s3.charAt(row + col - 1))
                            || (dp[col - 1] && s2.charAt(col - 1) == s3.charAt(row + col - 1));
                }
            }
        }

        return dp[s2.length()];
    }

    /**
     * LC - DP
     *
     * Runtime: 4 ms, faster than 63.93% of Java online submissions for Interleaving String.
     * Memory Usage: 40.7 MB, less than 92.70% of Java online submissions for Interleaving String.
     *
     * Time: O(m * n), where m = s1.length() and n = s2.length()
     * Space: O(m * n)
     */
    public boolean isInterleaveLcDp(String s1, String s2, String s3) {
        if (s3.length() != s1.length() + s2.length()) {
            return false;
        }

        /**
         * s1 is row
         * s2 is col
         */
        boolean dp[][] = new boolean[s1.length() + 1][s2.length() + 1];

        dp[0][0] = true;

        for (int row = 1; row < dp.length; row++) {
            char ch1 = s1.charAt(row - 1);
            char ch3 = s3.charAt(row - 1);
            dp[row][0] = dp[row - 1][0] && (ch1 == ch3);
        }

        for (int col = 1; col < dp[0].length; col++) {
            char ch2 = s2.charAt(col - 1);
            char ch3 = s3.charAt(col - 1);
            dp[0][col] = dp[0][col - 1] && (ch2 == ch3);
        }

        for (int row = 1; row < dp.length; row++) {
            char ch1 = s1.charAt(row - 1);
            for (int col = 1, n = s2.length(); col <= n; col++) {
                char ch2 = s2.charAt(col - 1);

                /**
                 * Why "row + col - 1"?
                 */
                char ch3 = s3.charAt(row + col - 1);

                dp[row][col] = (dp[row - 1][col] && ch1 == ch3) || (dp[row][col - 1] && ch2 == ch3);
            }
        }
        return dp[s1.length()][s2.length()];
    }

    /**
     * Luke - DP
     *
     * Runtime: 4 ms, faster than 63.93% of Java online submissions for Interleaving String.
     * Memory Usage: 40.7 MB, less than 92.70% of Java online submissions for Interleaving String.
     *
     * Time: O(m * n), where m = s1.length() and n = s2.length()
     * Space: O(m * n)
     */
    public boolean isInterleaveLukeDp(String s1, String s2, String s3) {
        final int N1 = s1.length(), N2 = s2.length(), N3 = s3.length();

        if (N1 + N2 != N3) {
            return false;
        }

        /**
         * s1 is row
         * s2 is col
         */
        final boolean[][] dp = new boolean[N1 + 1][N2 + 1];

        dp[0][0] = true;

        for (int row = 1; row <= N1; row++) {
            char ch1 = s1.charAt(row - 1);
            char ch3 = s3.charAt(row - 1);
            dp[row][0] = dp[row - 1][0] && (ch1 == ch3);
        }

        for (int col = 1; col <= N2; col++) {
            char ch2 = s2.charAt(col - 1);
            char ch3 = s3.charAt(col - 1);
            dp[0][col] = dp[0][col - 1] && (ch2 == ch3);
        }

        for (int row = 1; row <= N1; row++) {
            char ch1 = s1.charAt(row - 1);
            for (int col = 1; col <= N2; col++) {
                char ch2 = s2.charAt(col - 1);

                /**
                 * Why "row + col - 1"?
                 */
                char ch3 = s3.charAt(row + col - 1);

                dp[row][col] = (dp[row][col - 1] && ch3 == ch2) || (dp[row - 1][col] && ch3 == ch1);
            }
        }

        return dp[N1][N2];
    }

    /**
     * LC - Recursion
     *
     * Time Limit Exceeded
     *
     * Time: O(2 ^ (m * n))
     * Space: O(m + n)
     */
    public boolean isInterleaveLcRecursion(String s1, String s2, String s3) {
        if (s1.length() + s2.length() != s3.length()) {
            return false;
        }
        return isInterleaveLcRecursion(s1, 0, s2, 0, "", s3);
    }

    private boolean isInterleaveLcRecursion(String s1, int pos1, String s2, int pos2, String res, String s3) {
        if (res.equals(s3) && pos1 == s1.length() && pos2 == s2.length()) {
            return true;
        }

        boolean ans = false;
        if (pos1 < s1.length()) {
            ans |= isInterleaveLcRecursion(s1, pos1 + 1, s2, pos2, res + s1.charAt(pos1), s3);
        }
        if (pos2 < s2.length()) {
            ans |= isInterleaveLcRecursion(s1, pos1, s2, pos2 + 1, res + s2.charAt(pos2), s3);
        }
        return ans;
    }

    /**
     * Luke - Break and Conquer
     *
     * Time Limit Exceeded
     *
     * Time: O(2 ^ M * 2 ^ N) = O(2 ^ (m * n))
     * Space: O(1)
     */
    public boolean isInterleaveLukeBreakConquer(String s1, String s2, String s3) {
        if (s1.length() + s2.length() != s3.length()) {
            return false;
        }

        if (s1.isEmpty()) {
            return s2.equals(s3);
        }

        if (s2.isEmpty()) {
            return s1.equals(s3);
        }

        return breakAndConquer(s1, 0, s2, 0, s3, 0);
    }

    boolean breakAndConquer(String s1, int pos1, String s2, int pos2, String s3, int pos3) {

        if (pos1 == s1.length()) {
            return s2.substring(pos2).equals(s3.substring(pos3));
        }

        if (pos2 == s2.length()) {
            return s1.substring(pos1).equals(s3.substring(pos3));
        }

        char ch1 = s1.charAt(pos1);
        char ch2 = s2.charAt(pos2);
        char ch3 = s3.charAt(pos3++);

        if (ch1 == ch3) {
            if (ch2 == ch3) {
                return breakAndConquer(s1, pos1 + 1, s2, pos2, s3, pos3) || breakAndConquer(s1, pos1, s2, pos2 + 1, s3, pos3);
            } else {
                return breakAndConquer(s1, pos1 + 1, s2, pos2, s3, pos3);
            }
        } else if (ch2 == ch3) {
            return breakAndConquer(s1, pos1, s2, pos2 + 1, s3, pos3);
        } else {
            return false;
        }
    }
}
