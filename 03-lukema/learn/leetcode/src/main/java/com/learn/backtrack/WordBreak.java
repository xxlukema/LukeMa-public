package com.learn.backtrack;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Assertions;

import lombok.extern.log4j.Log4j2;


/**
 * LC - 139 - Word Break
 *
 * Medium
 *
 * Given a string s and a dictionary of strings wordDict, return true if s can be segmented into a space-separated sequence of one or more dictionary words.
 * Note that the same word in the dictionary may be reused multiple times in the segmentation.
 *
 * Example 1:
 * Input: s = "leetcode", wordDict = ["leet","code"]
 * Output: true
 * Explanation: Return true because "leetcode" can be segmented as "leet code".
 *
 * Example 2:
 * Input: s = "applepenapple", wordDict = ["apple","pen"]
 * Output: true
 * Explanation: Return true because "applepenapple" can be segmented as "apple pen apple".
 *
 * Note that you are allowed to reuse a dictionary word.
 *
 * Example 3:
 * Input: s = "catsandog", wordDict = ["cats","dog","sand","and","cat"]
 * Output: false
 *
 * Constraints:
 *     1 <= s.length <= 300
 *     1 <= wordDict.length <= 1000
 *     1 <= wordDict[i].length <= 20
 *     s and wordDict[i] consist of only lowercase English letters.
 *     All the strings of wordDict are unique.
 */
@Log4j2
public class WordBreak {

    public static void main(String[] args) {

        /*
        final String s = "catsandog";
        final String[] dict = { "cats", "dog", "sand", "and", "cat" };
        */

        /*
        final String s = "bb";
        final String[] dict = { "a", "b", "bbb", "bbbb" };
        */

        /*
        final String s = "aaaaaaa";
        final String[] dict = { "aaaa", "aaa" };
        */

        final String s = "catskicatcats";
        final String[] dict = { "cats", "cat", "dog", "ski" };

        /*
        final String s = "applepenapple";
        final String[] dict = { "apple", "pen" };
        */

        final List<String> wordDict = List.of(dict);

        WordBreak wordBreak = new WordBreak();

        var wordBreakLukeBrute = wordBreak.wordBreakLukeBrute(s, wordDict);
        log.debug("Word Break: {}", () -> wordBreakLukeBrute);
        log.debug("Work Break {} OK", () -> "wordBreakLukeBrute");

        var wordBreakLukeMemo = wordBreak.wordBreakLukeMemo(s, wordDict);
        Assertions.assertEquals(wordBreakLukeBrute, wordBreakLukeMemo);
        log.debug("Work Break {} OK", () -> "wordBreakLukeMemo");

        var wordBreakLcDp = wordBreak.wordBreakLcDp(s, wordDict);
        Assertions.assertEquals(wordBreakLukeBrute, wordBreakLcDp);
        log.debug("Work Break {} OK", () -> "wordBreakLcDp");

        var wordBreakLukeDp = wordBreak.wordBreakLukeDp(s, wordDict);
        Assertions.assertEquals(wordBreakLukeBrute, wordBreakLukeDp);
        log.debug("Work Break {} OK", () -> "wordBreakLukeDp");

        var wordBreakLukeDpImproved = wordBreak.wordBreakLukeDpImproved(s, wordDict);
        Assertions.assertEquals(wordBreakLukeBrute, wordBreakLukeDpImproved);
        log.debug("Work Break {} OK", () -> "wordBreakLukeDpImproved");

    }

    /**
     * Luke - DP Improved
     *
     * Runtime: 8 ms, faster than 67.45% of Java online submissions for Word Break.
     * Memory Usage: 42.1 MB, less than 94.26% of Java online submissions for Word Break.
     *
     * Time: O(N ^ 3)
     * Space: O(N)
     */
    public boolean wordBreakLukeDpImproved(final String s, final List<String> wordDict) {
        if (s.length() == 0) {
            return true;
        }

        /**
         * Create a sub-wordDict with words only contained in s.
         */
        List<String> wordDictShort = wordDict.stream().filter(e -> s.indexOf(e) > -1).toList();

        final int N = s.length();

        final boolean[] dp = new boolean[N + 1];
        dp[0] = true;

        int idx = 0;
        while (idx < N) {
            for (String word : wordDictShort) {
                if (startsWith(s, word, idx) && dp[idx]) {
                    dp[idx + word.length()] = true;
                }
            }

            idx++;
        }

        return dp[N];
    }

    /**
     * Luke - DP Tabulation
     *
     * Runtime: 10 ms, faster than 61.64% of Java online submissions for Word Break.
     * Memory Usage: 43 MB, less than 69.78% of Java online submissions for Word Break.
     *
     * Time: O(N ^ 3)
     * Space: O(N)
     */
    public boolean wordBreakLukeDp(final String s, final List<String> wordDict) {
        if (s.length() == 0) {
            return true;
        }

        /**
         * Create a sub-wordDict with words only contained in s.
         */
        List<String> wordDictShort = wordDict.stream().filter(e -> s.indexOf(e) > -1).toList();

        Set<String> set = new HashSet<>(wordDictShort);

        final int N = s.length();

        final boolean[] dp = new boolean[N + 1];
        dp[0] = true;

        for (int end = 1; end <= N; end++) {
            for (int start = 0; start < end; start++) {
                String str = s.substring(start, end);
                if (dp[start] && set.contains(str)) {
                    dp[end] = true;
                    break;
                }
            }
        }

        return dp[N];
    }

    /**
     * Luke - Brute
     *
     * Time Limit Exceeded
     *
     * Time: O(wordDict.size() ^ (word count in s) * 2 ^ (word count in s))
     * Space: O(N) - N is s.length(). Recursion stack size.
     */
    public boolean wordBreakLukeBrute(final String s, final List<String> wordDict) {
        if (s.length() == 0) {
            return true;
        }

        /**
         * Create a sub-wordDict with words only contained in s.
         */
        List<String> wordDictShort = wordDict.stream().filter(e -> s.indexOf(e) > -1).toList();

        return backtrackLukeBrute(s, wordDictShort, 0);
    }

    boolean backtrackLukeBrute(final String s, final List<String> wordDict, int idx) {
        final int N = s.length();

        if (idx >= N) {
            // Boundary conditions
            return true;
        } else {
            for (int i = 0; i < wordDict.size(); i++) {
                if (startsWith(s, wordDict.get(i), idx)) {
                    if (backtrackLukeBrute(s, wordDict, idx + wordDict.get(i).length())) {
                        return true;
                    }
                }
            }

            return false;
        }
    }

    /**
     * Time: O(word.length())
     * Space: O(1)
     */
    boolean startsWith(String s, String word, int idx) {
        if (idx + word.length() > s.length()) {
            return false;
        }
        for (int i = 0; i < word.length(); i++) {
            if (s.charAt(idx + i) != word.charAt(i)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Luke - Memo
     *
     * Runtime: 3 ms, faster than 93.39% of Java online submissions for Word Break.
     * Memory Usage: 40.5 MB, less than 99.03% of Java online submissions for Word Break.
     *
     * Time: O(2 ^ (word count in s) * wordDict.size())
     * Space: O(N) - N is s.length(). Recursion stack size. memo size.
     */
    public boolean wordBreakLukeMemo(final String s, final List<String> wordDict) {
        if (s.length() == 0) {
            return true;
        }

        /**
         * Create a sub-wordDict with words only contained in s.
         */
        List<String> wordDictShort = wordDict.stream().filter(e -> s.indexOf(e) > -1).toList();

        final Boolean[] memo = new Boolean[s.length() + 1];

        return backtrackLukeMemo(s, wordDictShort, 0, memo);
    }

    /**
     * Time: O(2 ^ (word count in s) * wordDict.size())
     */
    boolean backtrackLukeMemo(final String s, final List<String> wordDict, int idx, final Boolean[] memo) {
        if (memo[idx] != null) {

            // log.debug("-------- memo ----------");

            return memo[idx];
        }

        final int N = s.length();

        if (idx == N) {
            memo[idx] = true;
            return true;
        } else {
            for (int i = 0; i < wordDict.size(); i++) {
                if (startsWith(s, wordDict.get(i), idx)) {
                    if (backtrackLukeMemo(s, wordDict, idx + wordDict.get(i).length(), memo)) {
                        memo[idx] = true;
                        return true;
                    }
                }
            }

            memo[idx] = false;
            return false;
        }
    }

    /**
     * LC - DP Tabulation
     *
     */
    public boolean wordBreakLcDp(String s, List<String> wordDict) {
        /**
         * Create a sub-wordDict with words only contained in s.
         */
        List<String> wordDictShort = wordDict.stream().filter(e -> s.indexOf(e) > -1).toList();
        Set<String> wordDictSet = new HashSet<>(wordDictShort);

        final boolean[] dp = new boolean[s.length() + 1];

        dp[0] = true;

        for (int end = 1; end <= s.length(); end++) {
            for (int start = 0; start < end; start++) {
                if (dp[start] && wordDictSet.contains(s.substring(start, end))) {
                    dp[end] = true;
                    break;
                }
            }
        }
        return dp[s.length()];
    }
}
