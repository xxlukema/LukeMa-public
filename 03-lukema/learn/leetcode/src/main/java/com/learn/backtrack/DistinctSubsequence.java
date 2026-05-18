package com.learn.backtrack;


import java.util.HashMap;

import org.junit.jupiter.api.Assertions;

import lombok.extern.log4j.Log4j2;


/**
 * LC - 115 - Distinct Sequence
 *
 * Hard
 *
 * Given two strings s and t, return the number of distinct subsequences of s which equals t.
 * A string's subsequence is a new string formed from the original string by deleting some (can be none) of the characters without
 * disturbing the remaining characters' relative positions. (i.e., "ACE" is a subsequence of "ABCDE" while "AEC" is not).
 *
 * The test cases are generated so that the answer fits on a 32-bit signed integer.
 *
 * Input: s = "rabbbit", t = "rabbit"
 * Output: 3
 * Explanation:
 * As shown below, there are 3 ways you can generate "rabbit" from S.
 *
 * Input: s = "babgbag", t = "bag"
 * Output: 5
 * Explanation:
 * As shown below, there are 5 ways you can generate "bag" from S.
 */
@Log4j2
public class DistinctSubsequence {

    public static void main(String[] args) {

        // String s = "rabbbit", t = "rabbit";

        String s = """
                xslledayhxhadmctrliaxqpokyezcfhzaskeykchkmhpyjipxtsuljkwkovmvelvwxzwieeuqnjozrfwmzsylcwvsthnxujvrkszqwtglewkycikdaiocglwzukwovsghkhyidevhbgffo
                qkpabthmqihcfxxzdejletqjoxmwftlxfcxgxgvpperwbqvhxgsbbkmphyomtbjzdjhcrcsggleiczpbfjcgtpycpmrjnckslrwduqlccqmgrdhxolfjafmsrfdghnatexyanldrdpxvvg
                ujsztuffoymrfteholgonuaqndinadtumnuhkboyzaqguwqijwxxszngextfcozpetyownmyneehdwqmtpjloztswmzzdzqhuoxrblppqvyvsqhnhryvqsqogpnlqfulurexdtovqpqkfx
                xnqykgscxaskmksivoazlducanrqxynxlgvwonalpsyddqmaemcrrwvrjmjjnygyebwtqxehrclwsxzylbqexnxjcgspeynlbmetlkacnnbhmaizbadynajpibepbuacggxrqavfnwpcwx
                bzxfymhjcslghmajrirqzjqxpgtgisfjreqrqabssobbadmtmdknmakdigjqyqcruujlwmfoagrckdwyiglviyyrekjealvvigiesnvuumxgsveadrxlpwetioxibtdjblowblqvzpbrmh
                upyrdophjxvhgzclidzybajuxllacyhyphssvhcffxonysahvzhzbttyeeyiefhunbokiqrpqfcoxdxvefugapeevdoakxwzykmhbdytjbhigffkmbqmqxsoaiomgmmgwapzdosorcxxhe
                jvgajyzdmzlcntqbapbpofdjtulstuzdrffafedufqwsknumcxbschdybosxkrabyfdejgyozwillcxpcaiehlelczioskqtptzaczobvyojdlyflilvwqgyrqmjaeepydrcchfyftjigh
                ntqzoo
                """;
        String t = "rwmimatmhydhbujebqehjprrwfkoebcxxqfktayaaeheys";

        DistinctSubsequence distinctSubsequence = new DistinctSubsequence();

        var retMemo = distinctSubsequence.numDistinctLukeMemo(s, t);
        log.debug("Distinct Subsequence: {}", () -> retMemo);

        var retLcMemoLukeImproved = distinctSubsequence.numDistinctLcMemoLukeImporved(s, t);
        Assertions.assertEquals(retMemo, retLcMemoLukeImproved);

        var retLcMemoMap = distinctSubsequence.numDistinctLcMemoMap(s, t);
        Assertions.assertEquals(retMemo, retLcMemoMap);

        var retLcDp2D = distinctSubsequence.numDistinctLcDp2D(s, t);
        Assertions.assertEquals(retMemo, retLcDp2D);

        var retLcDp1D = distinctSubsequence.numDistinctLcDp1D(s, t);
        Assertions.assertEquals(retMemo, retLcDp1D);

        log.debug(() -> "Distinct Subsequence DP OK.");
    }

    /**
     * Why seeking iterative dynamic program?
     *
     * (1) Seeing that we can have a large number of recursion calls, we may run into "size issues" for "very large strings". So, it's better to
     *     write an iterative version of the same solution to avoid those problems.
     * (2) Also, an iterative dynamic programming based solution is almost always (almost) faster than its recursive memoization-based counterpart.
     *
     */

    /**
     * LC - DP - 1D dp array
     *
     * This is very tricky. Use:
     *     dp[col] += lastIterationRight;
     *     Do NOT use:
     *     dp[col] += dp[col + 1];
     *
     * Runtime: 9 ms, faster than 97.27% of Java online submissions for Distinct Subsequences.
     * Memory Usage: 41.7 MB, less than 97.59% of Java online submissions for Distinct Subsequences.
     *
     * Time: O(M * N)
     * Space: N(N + 1) = O(N)
     */
    public int numDistinctLcDp1D(String s, String t) {
        final int M = s.length();
        final int N = t.length();

        final int[] dp = new int[N + 1];
        dp[N] = 1;

        for (int row = M - 1; row >= 0; row--) {
            char sCh = s.charAt(row);
            int lastIterationRight = dp[N];
            for (int col = N - 1; col >= 0; col--) {
                char tCh = t.charAt(col);

                /**
                 * tmp will be dp[col + 1] for after col--.
                 */
                int tmp = dp[col];
                if (sCh == tCh) {
                    /**
                     * Important! lastIterationRight is the dp[col + 1] of last interation right element.
                     *            NOT this round of dp[col + 1]
                     */
                    // dp[col] += dp[col + 1]; /** Wrong! Use "dp[col + 1]" of last iteration. */
                    dp[col] += lastIterationRight;
                }

                /**
                 * Update this for after "col--" use.
                 */
                lastIterationRight = tmp;
            }
        }

        return dp[0];
    }

    /**
     * LC - DP - 2D dp array
     *
     * Runtime: 15 ms, faster than 91.98% of Java online submissions for Distinct Subsequences.
     * Memory Usage: 49 MB, less than 85.66% of Java online submissions for Distinct Subsequences.
     *
     * Runtime: O(M * N) - Loop inside loop.
     * Space: O(M * N) - 2D array.
     */
    public int numDistinctLcDp2D(String s, String t) {
        final int M = s.length();
        final int N = t.length();

        final int[][] dp = new int[M + 1][N + 1];

        /**
         * Basecase init: For an empty s, it cannot form a match to t of any char.
         * But it can match an empty t. That will be initialized in next initalizer.
         */
        for (int col = N - 1; col >= 0; col--) {
            dp[M][col] = 0;
        }

        /**
         * Basecase init: Enpty s can match empty t.
         */
        for (int row = M; row >= 0; row--) {
            dp[row][N] = 1;
        }

        for (int row = M - 1; row >= 0; row--) {
            char sCh = s.charAt(row);
            for (int col = N - 1; col >= 0; col--) {
                char tCh = t.charAt(col);
                /**
                 * Compare: "if (s.charAt(row) == t.charAt(col)) {"
                 *
                 * Runtime: 27 ms, faster than 61.27% of Java online submissions for Distinct Subsequences.
                 * Memory Usage: 50.6 MB, less than 21.60% of Java online submissions for Distinct Subsequences.
                 *
                 * Without chaching "sCh" and "tCh": (1) Runtime doubles. (2) Memory use stays the same. (3) There is no need to cache tCh
                 * because col keeps changing. However, tCh is cached for code clearity.
                 */
                if (sCh == tCh) {
                    dp[row][col] = dp[row + 1][col] + dp[row + 1][col + 1];
                } else {
                    dp[row][col] = dp[row + 1][col];
                }
            }
        }

        return dp[0][0];
    }

    /**
     * LC - Memo Using 2D int array - Significantly improves runtime from 4 times faster than "Using Map"
     *
     * This resursion can utilize DP because of the the progressive nature ot calculation:
     *
     * char sCh = s.charAt(sIdx);
     * char tCh = t.charAt(tIdx);
     *
     * if (tCh == sCh) {
     *     counter = numDistinctLukeMemo(s, t, sIdx + 1, tIdx, counter, memo) + numDistinctLukeMemo(s, t, sIdx + 1, tIdx + 1, counter, memo);
     * } else {
     *     counter = numDistinctLukeMemo(s, t, sIdx + 1, tIdx, counter, memo);
     * }
     *
     * Three Improvements:
     *
     * (1) Use of Map is slower than 2D int array. But the good thing of Map is there is no need to initialize the Map:
     *     If the cell had been visited, it is in the map, comparing have to initialize memo cells to -1.
     * (2) Use of Pair that is not part of JDK. Pair is in javafx.uitl.Pair. It is in a standalone package.
     *     memo array does not need Pair.
     * (3) Removes memo as a class member, making the class stateless.
     *
     * Runtime: 4 ms, faster than 99.56% of Java online submissions for Distinct Subsequences.
     * Memory Usage: 48.9 MB, less than 85.86% of Java online submissions for Distinct Subsequences.
     *
     * Time: O(M * N) - M represents the length of string S while N represents the length of string T
     * Space: O(M * N)
     */
    public int numDistinctLcMemoLukeImporved(String s, String t) {
        /**
         * source is row
         * target is coloumn
         */
        final int[][] memo = new int[s.length()][t.length()];

        /**
         * The memo cells must be initialized to -1. Otherwise, if use 0 as initial value, thre is no way to determine
         * if the cell is initial value or calculated value. And that will cause calculation of those cells for
         * every test of the cell values. That will cause "Time Limit Exceeded".
         */
        for (int row = 0; row < memo.length; row++) {
            for (int col = 0; col < memo[0].length; col++) {
                memo[row][col] = -1;
            }
        }

        return this.recurseWithMemoArray(s, t, 0, 0, memo);
    }

    private int recurseWithMemoArray(String s, String t, int i, int j, final int[][] memo) {

        final int M = s.length();
        final int N = t.length();

        // Base case
        if (i == M || j == N || M - i < N - j) {
            return j == t.length() ? 1 : 0;
        }

        /**
         * Now, check the cell if it had been visited.
         *
         * If the cell is visited, use that cell value. Otherwise, calculate the cell value by going down into recursion.
         */
        if (memo[i][j] != -1) {
            return memo[i][j];
        }

        // Always calculate this result since it's
        // required for both the cases
        int ans = this.recurseWithMemoArray(s, t, i + 1, j, memo);

        // If the characters match, then we make another
        // recursion call and add the result to "ans"
        if (s.charAt(i) == t.charAt(j)) {
            ans += this.recurseWithMemoArray(s, t, i + 1, j + 1, memo);
        }

        // Cache the result
        memo[i][j] = ans;
        return ans;
    }

    /**
     * LC - Memo Using Map
     *
     * Three problems with this approach:
     *
     * (1) Use of Map is slower than 2D int array. But the good thing of Map is there is no need to initialize the Map:
     *     If the cell had been visited, it is in the map, comparing have to initialize memo cells to -1.
     * (2) Use of Pair that is not part of JDK. Pair is in javafx.uitl.Pair. It is in a standalone package.
     * (3) It uses memo as a class member, making the class stateful.
     *
     * Runtime: 33 ms, faster than 40.87% of Java online submissions for Distinct Subsequences.
     * Memory Usage: 50.7 MB, less than 11.89% of Java online submissions for Distinct Subsequences.
     *
     * Time: O(M * N) - M represents the length of string S while N represents the length of string T
     * Space: O(M * N)
     */
    public int numDistinctLcMemoMap(String s, String t) {
        this.memo = new HashMap<>();
        return this.recurseWithMemoMap(s, t, 0, 0);
    }

    public record Pair<T, U>(T key, U value) {
    }

    // Dictionary that we will use for memoization
    private HashMap<Pair<Integer, Integer>, Integer> memo;

    private int recurseWithMemoMap(String s, String t, int i, int j) {

        final int M = s.length();
        final int N = t.length();

        // Base case
        if (i == M || j == N || M - i < N - j) {
            return j == t.length() ? 1 : 0;
        }

        Pair<Integer, Integer> key = new Pair<>(i, j);

        // Check to see if the result for this recursive
        // call is already cached
        if (this.memo.containsKey(key)) {
            return this.memo.get(key);
        }

        // Always calculate this result since it's
        // required for both the cases
        int ans = this.recurseWithMemoMap(s, t, i + 1, j);

        // If the characters match, then we make another
        // recursion call and add the result to "ans"
        if (s.charAt(i) == t.charAt(j)) {
            ans += this.recurseWithMemoMap(s, t, i + 1, j + 1);
        }

        // Cache the result
        this.memo.put(key, ans);
        return ans;
    }

    /**
     * Luke Backtrack
     *
     * Time Limit Exceeded
     *
     * Time: O(2 ^ T * S)
     * Space: O(T)
     */
    public int numDistinctLukeNoDp(String s, String t) {
        return numDistinctLukeNoDp(s, t, 0, 0, 0);
    }

    private int numDistinctLukeNoDp(String s, String t, int sIdx, int tIdx, int counter) {
        if (tIdx > t.length() - 1) {
            return counter + 1;
        }

        if (sIdx == s.length()) {
            return counter;
        }

        char sCh = s.charAt(sIdx);
        char tCh = t.charAt(tIdx);

        if (tCh == sCh) {
            counter = numDistinctLukeNoDp(s, t, sIdx + 1, tIdx + 1, counter) + numDistinctLukeNoDp(s, t, sIdx + 1, tIdx, counter);
        } else {
            counter = numDistinctLukeNoDp(s, t, sIdx + 1, tIdx, counter);
        }

        return counter;
    }

    /**
     * Luke - Backtrack - DP
     *
     * Runtime: 33 ms, faster than 40.87% of Java online submissions for Distinct Subsequences.
     * Memory Usage: 50.7 MB, less than 11.89% of Java online submissions for Distinct Subsequences.
     *
     * Time: O(S.length * T.length)
     * Space: O(S.length * T.length)
     */
    public int numDistinctLukeMemo(String s, String t) {
        /**
         * source is row.
         * target is column.
         */
        final int[][] memo2 = new int[s.length()][t.length()];

        /**
         * Initialize the memo cells to -1. Otherwise, if use the initial default value of 0, there is no way determine if the cell had been
         * visited (calculated) or no. And that will cause re-calculating of the cell value again and again. That will cause "Time Limit Exceeded".
         */
        for (int row = 0; row < memo2.length; row++) {
            for (int col = 0; col < memo2[0].length; col++) {
                memo2[row][col] = -1;
            }
        }

        return numDistinctLukeMemo(s, t, 0, 0, 0, memo2);
    }

    private int numDistinctLukeMemo(String s, String t, int sIdx, int tIdx, int counter, int[][] memo) {
        if (tIdx > t.length() - 1) {
            return counter + 1;
        }

        if (sIdx == s.length()) {
            return counter;
        }

        /**
         * Now, check the cell if it had been visited.
         *
         * If the cell is visited, use that cell value. Otherwise, calculate the cell value by going down into recursion.
         */
        if (memo[sIdx][tIdx] != -1) {
            return memo[sIdx][tIdx];
        }

        char sCh = s.charAt(sIdx);
        char tCh = t.charAt(tIdx);

        if (tCh == sCh) {
            counter = numDistinctLukeMemo(s, t, sIdx + 1, tIdx, counter, memo) + numDistinctLukeMemo(s, t, sIdx + 1, tIdx + 1, counter, memo);
        } else {
            counter = numDistinctLukeMemo(s, t, sIdx + 1, tIdx, counter, memo);
        }

        memo[sIdx][tIdx] = counter;

        return memo[sIdx][tIdx];
    }

}
