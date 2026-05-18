package com.learn.pattern;

import org.junit.jupiter.api.Assertions;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class PatternMatch {

    public static void main(String[] args) {

        // String str = "abcdefghijklmnoppppqrstuvwxyz";
        // String pattern = ".*d.*m.*p.*";

        String str = "ai";
        String pattern = "a.*p*i";

        PatternMatch patternMatch = new PatternMatch();

        boolean isMatch = patternMatch.isMatchBottumUpLuke(str, pattern);
        log.debug("IsMatch Luke: {}", () -> isMatch);

        boolean isMatchLC = patternMatch.isMatchBottumUpLC(str, pattern);
        log.debug("IsMatch LC: {}", () -> isMatchLC);

        Assertions.assertEquals(isMatchLC, isMatch);
    }

    public boolean isMatchBottumUpLuke(String text, String pattern) {
        boolean dp[][] = new boolean[text.length() + 1][pattern.length() + 1];

        /**
         * end of text and end of pattern
         */
        dp[text.length()][pattern.length()] = true;

        for (int posT = text.length(); posT >= 0; posT--) {
            for (int posP = pattern.length() - 1; posP >= 0; posP--) {
                boolean firstMatch = posT < text.length() &&
                        (text.charAt(posT) == pattern.charAt(posP) || pattern.charAt(posP) == '.');

                if (posP + 1 < pattern.length() && pattern.charAt(posP + 1) == '*') {
                    dp[posT][posP] = dp[posT][posP + 2] || (firstMatch && dp[posT + 1][posP]);
                } else {
                    dp[posT][posP] = firstMatch && dp[posT + 1][posP + 1];
                }
            }
        }

        return dp[0][0];
    }

    public boolean isMatchBottumUpLC(String text, String pattern) {
        boolean[][] dp = new boolean[text.length() + 1][pattern.length() + 1];
        dp[text.length()][pattern.length()] = true;

        for (int posT = text.length(); posT >= 0; posT--) {
            for (int posP = pattern.length() - 1; posP >= 0; posP--) {
                boolean first_match = (posT < text.length() &&
                        (pattern.charAt(posP) == text.charAt(posT) || pattern.charAt(posP) == '.'));
                if (posP + 1 < pattern.length() && pattern.charAt(posP + 1) == '*') {
                    dp[posT][posP] = dp[posT][posP + 2] || first_match && dp[posT + 1][posP];
                } else {
                    dp[posT][posP] = first_match && dp[posT + 1][posP + 1];
                }
            }
        }
        return dp[0][0];
    }

    public boolean isMatchMomo(String text, String pattern) {
        Boolean[][] memo = new Boolean[text.length() + 1][pattern.length() + 1];

        return isMatchDp(0, 0, text, pattern, memo);
    }

    public boolean isMatchDp(int posT, int posP, String text, String pattern, Boolean[][] memo) {
        if (memo[posT][posP] != null) {
            return memo[posT][posP] == true;
        }
        boolean ans;
        if (posP == pattern.length()) {
            ans = posT == text.length();
        } else {
            boolean first_match = (posT < text.length() &&
                    (pattern.charAt(posP) == text.charAt(posT) || pattern.charAt(posP) == '.'));

            if (posP + 1 < pattern.length() && pattern.charAt(posP + 1) == '*') {
                ans = (isMatchDp(posT, posP + 2, text, pattern, memo) ||
                        first_match && isMatchDp(posT + 1, posP, text, pattern, memo));
            } else {
                ans = first_match && isMatchDp(posT + 1, posP + 1, text, pattern, memo);
            }
        }
        memo[posT][posP] = ans ? true : false;
        return ans;
    }

    public boolean isMatch(String text, String pattern) {

        if (pattern.isEmpty()) {
            return text.isEmpty();
        }

        boolean firstMatch = (!text.isEmpty() && (text.charAt(0) == pattern.charAt(0) || pattern.charAt(0) == '.'));

        if (pattern.length() >= 2 && pattern.charAt(1) == '*') {
            return isMatch(text, pattern.substring(2)) || firstMatch && isMatch(text.substring(1), pattern);
        } else {
            return firstMatch && isMatch(text.substring(1), pattern.substring(1));
        }
    }

    boolean firstCall(boolean value) {
        log.info("{}", () -> "firstCall");
        return value;
    }

    boolean secondCall() {
        log.info("{}", () -> "secondCall");
        return true;
    }

    /**
     * "(exp1 && exp2) and (exp1 || exp2)" - The final purpose is to evaluate a thruth.
     * 
     * - for (exp1 && exp2), If the first expression exp1 is thruthy, the second expression exp2 will be evaluted.
     *                       If the first expression exp1 is faulty,  the second expression exp2 will NOT be evaluted.
     * - for (exp1 || exp2), If the first expression exp1 is thruthy, the second expression exp2 will be NOT be evaluted.
     *                       If the first expression exp1 is faulty,  the second expression exp2 will be evaluted.
     */
    public void andOrSecondExp() {
        PatternMatch patternMatch = new PatternMatch();

        log.info("1. second is called: {}", () -> patternMatch.firstCall(true) && patternMatch.secondCall());
        log.info("2. second is called: {}", () -> patternMatch.firstCall(false) && patternMatch.secondCall());
        log.info("3. second is called: {}", () -> patternMatch.firstCall(true) || patternMatch.secondCall());
        log.info("4. second is called: {}", () -> patternMatch.firstCall(false) || patternMatch.secondCall());
    }
}
