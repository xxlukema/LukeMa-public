package com.learn.other;


import lombok.extern.log4j.Log4j2;


@Log4j2
public class EditDistance {

    public static void main(String[] args) {

        // String word1 = "herseorrsw";
        // String word2 = "porwrs";

        String word1 = "intention";
        String word2 = "execution";

        EditDistance editDistance = new EditDistance();
        var ret = editDistance.minDistance(word1, word2);

        log.debug("Edit distance Luke: {}", () -> ret);

    }

    /**
     * LC DP
     * 
     * Runtime: 9 ms, faster than 51.96% of Java online submissions for Edit Distance.
     * Memory Usage: 45.3 MB, less than 25.90% of Java online submissions for Edit Distance.
     * 
     * Time: O(m * n)
     * Space: O(m * n)
     */
    public int minDistance(String word1, String word2) {

        if (word2.isEmpty()) {
            return word1.length();
        }

        if (word1.isEmpty()) {
            return word2.length();
        }

        if (word1.equals(word2)) {
            return 0;
        }

        final int ROWS = word2.length() + 1;
        final int COLS = word1.length() + 1;

        int[][] dp = new int[ROWS][COLS];

        // 0th col
        for (int row = 0; row < ROWS; row++) {
            dp[row][0] = row;
        }

        // 0th row
        for (int col = 0; col < COLS; col++) {
            dp[0][col] = col;
        }

        for (int r = 1; r < ROWS; r++) {
            for (int c = 1; c < COLS; c++) {
                if (word1.charAt(c - 1) == word2.charAt(r - 1)) {
                    dp[r][c] = dp[r - 1][c - 1];
                } else {
                    dp[r][c] = Math.min(dp[r][c - 1], Math.min(dp[r - 1][c - 1], dp[r - 1][c])) + 1;
                }
            }
        }

        return dp[ROWS - 1][COLS - 1];
    }
}
