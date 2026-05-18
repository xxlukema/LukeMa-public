package com.learn.backtrack;


import java.util.LinkedList;

import org.junit.jupiter.api.Assertions;

import lombok.extern.log4j.Log4j2;


/**
 * LC 1143
 */
@Log4j2
public class LongestCommonSubsequence {

    public static void main(String[] args) {

        // String text1 = "abcde";
        // String text2 = "swawwcve";
        // String text2 = "ace";

        // String text1 = "b1";
        // String text2 = "yby";

        // String text1 = "a";
        // String text2 = "a";

        String text1 = "pmjghexybyrgzczy";
        String text2 = "hafcdqbgncrcbihkd";

        LongestCommonSubsequence longestCommonSubsequence = new LongestCommonSubsequence();

        // int len = longestCommonSubsequence.longestCommonSubsequenceLukeBacktrack(text1, text2);
        int lenDp = longestCommonSubsequence.longestCommonSubsequenceLcDp(text1, text2);
        log.debug("Longest Common Subsequence: {}", () -> lenDp);

        int lenBreakConquer = longestCommonSubsequence.longestCommonSubsequenceBreakConquer(text1, text2);

        Assertions.assertEquals(lenDp, lenBreakConquer);

    }

    /**
     * Luke Break and Conquer
     * 
     * Time Limit Exceeded
     * 
     * https://www.youtube.com/watch?v=ASoaQq66foQ
     * 
     * Time: O(2 ^ M * 2 ^ N)
     * Space: O(1)
     */
    public int longestCommonSubsequenceBreakConquer(String text1, String text2) {
        return longestCommonSubsequenceBreakConquer(text1, 0, text2, 0);
    }

    private int longestCommonSubsequenceBreakConquer(String text1, int pos1, String text2, int pos2) {
        if (pos1 >= text1.length() || pos2 >= text2.length()) {
            return 0;
        }

        char ch1 = text1.charAt(pos1);
        char ch2 = text2.charAt(pos2);

        if (ch1 == ch2) {
            return 1 + longestCommonSubsequenceBreakConquer(text1, pos1 + 1, text2, pos2 + 1);
        } else {
            int len1 = longestCommonSubsequenceBreakConquer(text1, pos1 + 1, text2, pos2);
            int len2 = longestCommonSubsequenceBreakConquer(text1, pos1, text2, pos2 + 1);

            return Math.max(len1, len2);
        }
    }

    /**
     * Luke DP
     * 
     * Runtime: 8 ms, faster than 99.13% of Java online submissions for Longest Common Subsequence.
     * Memory Usage: 45.9 MB, less than 83.06% of Java online submissions for Longest Common Subsequence.
     * 
     * Time: O(M * N)
     * Space: O(M * N)
     */
    public int longestCommonSubsequenceLcDp(String text1, String text2) {
        final int[][] dp = new int[text1.length() + 1][text2.length() + 1];

        for (int row = text1.length() - 1; row >= 0; row--) {
            char ch1 = text1.charAt(row);
            for (int col = text2.length() - 1; col >= 0; col--) {
                char ch2 = text2.charAt(col);
                if (ch1 == ch2) {
                    dp[row][col] = 1 + dp[row + 1][col + 1];
                } else {
                    dp[row][col] = Math.max(dp[row + 1][col], dp[row][col + 1]);
                }
            }
        }

        return dp[0][0];
    }

    /**
     * Luke Backtrack
     *
     * Time Limit Exceeded
     *
     * Time: O(M ^ 2 * N ^ 2 )
     * Space: O(max(M, N))
     *
     */
    public int longestCommonSubsequenceLukeBacktrack(String text1, String text2) {
        LinkedList<Character> sequence = new LinkedList<>();

        backtrack(text1, text2, 0, 0, sequence);

        return maxLen;
    }

    int maxLen = 0;

    private void backtrack(String text1, String text2, int pos1, int pos2, LinkedList<Character> sequence) {
        if (sequence.size() > maxLen) {
            maxLen = sequence.size();
        }
        if (pos1 <= text1.length() - 1 && pos2 <= text2.length() - 1) {
            for (int i1 = pos1; i1 < text1.length(); i1++) {
                char ch1 = text1.charAt(i1);
                for (int k2 = pos2; k2 < text2.length(); k2++) {
                    char ch2 = text2.charAt(k2);
                    if (ch1 == ch2) {
                        sequence.addLast(ch1);
                        backtrack(text1, text2, i1 + 1, k2 + 1, sequence);
                        sequence.removeLast();
                    }
                }
            }
        }
    }
}
