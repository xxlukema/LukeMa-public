package com.learn.other;


import lombok.extern.log4j.Log4j2;


/**
 * LC - 44 - Wildcard Matching
 *
 * Hard
 *
 * Given an input string (s) and a pattern (p), implement wildcard pattern matching with support for '?' and '*' where:
 *
 *     '?' Matches any single character.
 *     '*' Matches any sequence of characters (including the empty sequence).
 *
 * The matching should cover the entire input string (not partial).
 *
 * Example 1:
 * Input: s = "aa", p = "a"
 * Output: false
 * Explanation: "a" does not match the entire string "aa".
 *
 * Example 2:
 * Input: s = "aa", p = "*"
 * Output: true
 * Explanation: '*' matches any sequence.
 *
 * Example 3:
 * Input: s = "cb", p = "?a"
 * Output: false
 * Explanation: '?' matches 'c', but the second letter is 'a', which does not match 'b'.
 *
 * Constraints:
 *     0 <= s.length, p.length <= 2000
 *     s contains only lowercase English letters.
 *     p contains only lowercase English letters, '?' or '*'.
 */
@Log4j2
public class WildcardMatching {

    public static void main(String[] args) {

        // String str = "sefe";
        // String ptn = "s*ef*e";

        // String str = "swveedswdderrrggrrqwwssrrrm";
        // String ptn = "s?v";
        // String ptn = "s***v**";
        // String ptn = "s?v*r*qw*m";
        //     sIdx   0123456789

        // String str = "aa";
        // String ptn = "a";

        // String str = "aaaa";
        // String ptn = "***a";

        /**
         * Expected: true
         */
        // String str = "c";
        // String ptn = "*?*";

        // String str = "hi";
        // String ptn = "*?";

        // String str = "bbbaba";
        // String ptn = "bb*?a";

        // String str = "mississippi";
        // String ptn = "m??*ss*?i*pi";

        String str = "abbabaaabbabbaababbabbbbbabbbabbbabaaaaababababbbabababaabbababaabbbbbbaaaabababbbaabbbbaabbbbababababbaabbaababaabbbababababbbbaaabbbbbabaaaabba";
        // String str = "abbabaaabbabbaababbabbbbbabbbabbbabaaaaababababbbabababaabbababaabbbbbbaaaababa";
        String ptn = "**aa*****ba*a*bb**aa*ab****a*aaaaaa***a*aaaa**bbabb*b*b**aaaaaaaaa*a********ba*bbb***a*ba*bb*bb**a*b*bb";
        // String ptn = "**aa*****ba*a*bb**aa*ab****a*aaa";

        // String str = "adceb";
        // String ptn = "*a*b";

        // String str = "";
        // String ptn = "***";

        // String str = "abcabczzzde";
        // String ptn = "*abc???de*";

        WildcardMatching wildcardMatching = new WildcardMatching();

        /*
        boolean isMatch = wildcardMatching.isMatchLuke(str, ptn);
        log.info("isMatch Luke {}", () -> isMatch);
        */

        // var isMatchLukeBacktrack = wildcardMatching.isMatchLukeBacktrack(str, ptn);
        // log.debug("Wild Card Matching: {}", () -> isMatchLukeBacktrack);
        // log.debug("Wild Card Matching {} OK", () -> "isMatchLukeBacktrack");

        var isMatchLukeDpTabulation = wildcardMatching.isMatchLukeDpTabulation(str, ptn);
        log.debug("Wild Card Matching: {}", () -> isMatchLukeDpTabulation);
        // Assertions.assertEquals(isMatchLukeBacktrack, isMatchLukeDpTabulation);
        log.debug("Wild Card Matching {} OK", () -> "isMatchLukeDpTabulation");

    }

    /**
     * Luke - DP - Tabulation
     *
     * https://www.youtube.com/watch?v=3ZDZ-N0EPV0
     * https://www.youtube.com/watch?v=7SHV_QfVROE
     *
     * Runtime: 48 ms Beats 47.28%
     * Memory: 51.4 MB Beats 29.59%
     *
     * Time: O(N * M)
     * Space: O(N * M)
     */
    public boolean isMatchLukeDpTabulation(String s, String p) {
        if (s.equals(p)) {
            return true;
        }

        if (p.isEmpty()) {
            return false;
        }

        /**
         * Removed deplicated "*" from p
         * Or
         * p = p.replaceAll("[\\*]+", "*");
         */
        p = p.replaceAll("[\\*]{1,}", "*");

        if (p.equals("*")) {
            return true;
        }

        // log.debug("ptr: {}", p);

        final int ROWS = s.length() + 1;
        final int COLS = p.length() + 1;

        final boolean[][] dp = new boolean[ROWS][COLS];

        dp[0][0] = true;

        for (int col = 1; col < COLS; col++) {
            dp[0][col] = dp[0][col - 1] && p.charAt(col - 1) == '*';
        }

        for (int row = 1; row < ROWS; row++) {
            dp[row][0] = false;
        }

        for (int row = 1; row < ROWS; row++) {
            char sch = s.charAt(row - 1);
            for (int col = 1; col < COLS; col++) {
                char pch = p.charAt(col - 1);

                if (pch == sch || pch == '?') {
                    dp[row][col] = dp[row - 1][col - 1];
                } else if (pch == '*') {
                    dp[row][col] = dp[row - 1][col] || dp[row][col - 1];
                } else {
                    dp[row][col] = false;
                }
            }
        }

        return dp[ROWS - 1][COLS - 1];
    }

    /**
     * Luke - Backtrack
     *
     * Time Limit Exceeded
     *
     * Time: O(2 ^ N)
     * Space: O(N)
     *
     */
    public boolean isMatchLukeBacktrack(String s, String p) {
        if (s.equals(p)) {
            return true;
        }

        if (p.isEmpty()) {
            return false;
        }

        /**
         * Removed deplicated "*" from p
         * Or
         * p = p.replaceAll("[\\*]+", "*");
         */
        p = p.replaceAll("[\\*]{1,}", "*");

        if (p.equals("*")) {
            return true;
        }

        // log.debug("ptr: {}", p);

        final int SLEN = s.length();
        final int PLEN = p.length();

        return backtrackLuke(s, p, 0, 0, SLEN, PLEN);
    }

    private boolean backtrackLuke(final String s, final String p, final int sIdx, final int pIdx, final int SLEN, final int PLEN) {

        if (sIdx == SLEN && pIdx == PLEN) {
            return true;
        }

        if (sIdx == SLEN) {
            if (p.substring(pIdx).equals("*")) {
                return true;
            } else {
                return false;
            }
        }

        if (pIdx == PLEN) {
            return false;
        }

        char pCh = p.charAt(pIdx);

        // log.debug("----- sCh: {}, sIdx: {}, pCh: {}, pIdx: {}", s.charAt(sIdx), sIdx, pCh, pIdx);

        if (pCh == '?' || pCh == s.charAt(sIdx)) {
            return backtrackLuke(s, p, sIdx + 1, pIdx + 1, SLEN, PLEN);
        } else if (pCh == '*') {

            if (pIdx + 1 == PLEN) {
                /**
                 * ptrn ends with '*'.
                 */
                return true;
            } else {
                int idxAfterStar = pIdx + 1;
                char nextPCh = p.charAt(idxAfterStar);

                if (nextPCh == '?') {

                    for (int i = sIdx; i < SLEN; i++) {
                        boolean isMatch = backtrackLuke(s, p, i, idxAfterStar, SLEN, PLEN);
                        if (isMatch) {
                            return true;
                        }
                    }

                    return false;
                } else {

                    int nextIdxS = sIdx;
                    while ((nextIdxS = s.indexOf(nextPCh, nextIdxS)) > -1) {

                        boolean isMatch = backtrackLuke(s, p, nextIdxS, idxAfterStar, SLEN, PLEN);
                        if (isMatch) {
                            return true;
                        } else {
                            nextIdxS++;
                        }
                    }

                    return false;
                }
            }
        } else {
            return false;
        }
    }

    /**
     * LC - Backtrack
     */
    public boolean isMatchLcBacktrack(String s, String p) {
        int sLen = s.length(), pLen = p.length();
        int sIdx = 0, pIdx = 0;
        int starIdx = -1, sTmpIdx = -1;

        while (sIdx < sLen) {
            // If the pattern caracter = string character
            // or pattern character = '?'
            if (pIdx < pLen && (p.charAt(pIdx) == '?' || p.charAt(pIdx) == s.charAt(sIdx))) {
                ++sIdx;
                ++pIdx;

                // If pattern character = '*'
            } else if (pIdx < pLen && p.charAt(pIdx) == '*') {
                // Check the situation
                // when '*' matches no characters
                starIdx = pIdx;
                sTmpIdx = sIdx;
                ++pIdx;

                // If pattern character != string character
                // or pattern is used up
                // and there was no '*' character in pattern
            } else if (starIdx == -1) {
                return false;

                // If pattern character != string character
                // or pattern is used up
                // and there was '*' character in pattern before
            } else {
                // Backtrack: check the situation
                // when '*' matches one more character
                pIdx = starIdx + 1;
                sIdx = sTmpIdx + 1;
                sTmpIdx = sIdx;
            }
        }

        // The remaining characters in the pattern should all be '*' characters
        for (int i = pIdx; i < pLen; i++) {
            if (p.charAt(i) != '*') {
                return false;
            }

        }
        return true;
    }

    /**
     * LC - DP
     */
    public boolean isMatchLcDp(String s, String p) {
        int sLen = s.length(), pLen = p.length();

        // base cases
        if (p.equals(s)) {
            return true;
        }

        if (pLen > 0 && p.chars().allMatch(c -> c == '*')) {
            return true;
        }

        if (p.isEmpty() || s.isEmpty()) {
            return false;
        }

        // init all matrix except [0][0] element as False
        boolean[][] d = new boolean[pLen + 1][sLen + 1];
        d[0][0] = true;

        // DP compute
        for (int pIdx = 1; pIdx < pLen + 1; pIdx++) {
            // the current character in the pattern is '*'
            if (p.charAt(pIdx - 1) == '*') {
                int sIdx = 1;

                // d[p_idx - 1][s_idx - 1] is a string-pattern match
                // on the previous step, i.e. one character before.
                // Find the first idx in string with the previous math.
                while ((!d[pIdx - 1][sIdx - 1]) && (sIdx < sLen + 1)) {
                    sIdx++;
                }

                // If (string) matches (pattern),
                // when (string) matches (pattern)* as well
                d[pIdx][sIdx - 1] = d[pIdx - 1][sIdx - 1];

                // If (string) matches (pattern),
                // when (string)(whatever_characters) matches (pattern)* as well
                while (sIdx < sLen + 1) {
                    d[pIdx][sIdx++] = true;
                }

                // the current character in the pattern is '?'
            } else if (p.charAt(pIdx - 1) == '?') {
                for (int sIdx = 1; sIdx < sLen + 1; sIdx++) {
                    d[pIdx][sIdx] = d[pIdx - 1][sIdx - 1];
                }

                // the current character in the pattern is not '*' or '?'
            } else {
                for (int sIdx = 1; sIdx < sLen + 1; sIdx++) {
                    // Match is possible if there is a previous match
                    // and current characters are the same
                    d[pIdx][sIdx] = d[pIdx - 1][sIdx - 1] &&
                            (p.charAt(pIdx - 1) == s.charAt(sIdx - 1));
                }
            }
        }

        return d[pLen][sLen];
    }

    /**
     * Luke
     */
    public boolean isMatchLuke(String str, String ptn) {

        if (str.length() == 0) {
            if (ptn.equals("*")) {
                return true;
            } else {
                return false;
            }
        }

        return isMatchLuke(str, 0, ptn, 0);
    }

    public boolean isMatchLuke(String str, int idxStr, String ptn, int idxPtn) {

        if (idxPtn > ptn.length() - 1) {
            if (idxStr > str.length() - 1) {
                return true;
            } else {
                return false;
            }
        }

        int idxPtnQues = ptn.indexOf('?', idxPtn);
        int idxPtnStar = ptn.indexOf('*', idxPtn);

        if (idxPtnQues == -1) {
            idxPtnQues = Integer.MAX_VALUE;
        }

        if (idxPtnStar == -1) {
            idxPtnStar = Integer.MAX_VALUE;
        }

        int idxPtnSpec = Math.min(idxPtnQues, idxPtnStar);
        if (idxPtnSpec == Integer.MAX_VALUE) {
            idxPtnSpec = -1;
        }

        if (idxPtnSpec > idxPtn) {
            String substr = ptn.substring(idxPtn, idxPtnSpec);
            if (str.indexOf(substr) == 0) {
                return isMatchLuke(str, idxStr + substr.length(), ptn, idxPtn + substr.length());
            } else {
                return false;
            }
        } else if (idxPtnSpec == 0) {
            char chPtn = ptn.charAt(idxPtn);
            if (chPtn == '?') {
                return isMatchLuke(str, idxStr + 1, ptn, idxPtn + 1);
            } else if (chPtn == '*') {
                if (idxPtn + 1 > ptn.length() - 1) {
                    char chPtnNext = ptn.charAt(idxPtn + 1);
                    if (chPtnNext == '*' || chPtnNext == '?') {
                        return isMatchLuke(str, idxStr, ptn, idxPtn + 1);
                    } else {
                        String sub = ptn.substring(idxPtn + 1);
                        if (sub.indexOf('?') == -1 && sub.indexOf('*') == -1) {
                            int idxLast = str.lastIndexOf(sub, idxStr);
                            if (idxLast == str.length() - sub.length() - 1) {
                                return true;
                            } else {
                                return false;
                            }
                        }

                        int ques = sub.indexOf('?');
                        if (ques == -1) {
                            ques = Integer.MAX_VALUE;
                        }
                        int star = sub.indexOf('*');
                        if (star == -1) {
                            star = Integer.MAX_VALUE;
                        }
                        int idx = Math.min(ques, star);
                        String nextSub = sub.substring(0, idx);
                        if (str.indexOf(nextSub, idxStr) == -1) {
                            return false;
                        } else {
                            return isMatchLuke(str, idxStr + idx, ptn, idxPtn + 1);
                        }
                    }
                } else {
                    return true;
                }
            } else {
                if (chPtn == str.charAt(idxStr)) {
                    return isMatchLuke(str, idxStr + 1, ptn, idxPtn + 1);
                } else {
                    return false;
                }
            }
        } else {
            return ptn.substring(idxPtn).equals(str.substring(idxStr));
        }
    }
}
