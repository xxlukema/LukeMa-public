package com.learn.backtrack;


import org.junit.jupiter.api.Assertions;

import lombok.extern.log4j.Log4j2;


/**
 * LC - 132 - Palindrome Partitioning II
 *
 * Hard
 *
 * Given a string s, partition s such that every substring of the partition is a palindrome.
 * Return the minimum cuts needed for a palindrome partitioning of s.
 *
 * Example 1:
 * Input: s = "aab"
 * Output: 1
 * Explanation: The palindrome partitioning ["aa","b"] could be produced using 1 cut.
 *
 * Example 2:
 * Input: s = "a"
 * Output: 0
 *
 * Example 3:
 * Input: s = "ab"
 * Output: 1
 *
 * Constraints:
 *     1 <= s.length <= 2000
 *     s consists of lowercase English letters only.
 *
 */
@Log4j2
public class PalindromePartitioningII {

    public static void main(String[] args) {

        // final String s = "aab";
        // final String s = "efe";
        // final String s = "fff";
        // final String s = "abbab";
        final String s = "cbbbcc";
        // final String s = "abaabababaababaabaa";

        PalindromePartitioningII palindromePartitioningII = new PalindromePartitioningII();

        var minCutLukeBrute = palindromePartitioningII.minCutLukeBrute(s);
        log.debug("Palindrome partitioning II minCut Luke brute: {}", () -> minCutLukeBrute);

        var minCutLcBrute = palindromePartitioningII.minCutLcBrute(s);
        Assertions.assertEquals(minCutLukeBrute, minCutLcBrute);
        log.debug("Palindrome partitioning II minCut {} OK", () -> "minCutLcBrute");

        var minCutLcTopDownMemo = palindromePartitioningII.minCutLcTopDownMemo(s);
        Assertions.assertEquals(minCutLukeBrute, minCutLcTopDownMemo);
        log.debug("Palindrome partitioning II minCut {} OK", () -> "minCutLcTopDownMemo");

        var minCutLcTopDownMemoSpaceOptimized = palindromePartitioningII.minCutLcTopDownMemoSpaceOptimized(s);
        Assertions.assertEquals(minCutLukeBrute, minCutLcTopDownMemoSpaceOptimized);
        log.debug("Palindrome partitioning II minCut {} OK", () -> "minCutLcTopDownMemoSpaceOptimized");

        var minCutLcDpBottomUpTabulation = palindromePartitioningII.minCutLcDpBottomUpTabulation(s);
        Assertions.assertEquals(minCutLukeBrute, minCutLcDpBottomUpTabulation);
        log.debug("Palindrome partitioning II minCut {} OK", () -> "minCutLcDpBottomUpTabulation");

        var minCutLcDpOptimzedTabulation = palindromePartitioningII.minCutLcDpOptimzedTabulation(s);
        Assertions.assertEquals(minCutLukeBrute, minCutLcDpOptimzedTabulation);
        log.debug("Palindrome partitioning II minCut {} OK", () -> "minCutLcDpOptimzedTabulation");

        var minCutLukeDpBottomUpTabulation = palindromePartitioningII.minCutLukeDpBottomUpTabulation(s);
        Assertions.assertEquals(minCutLukeBrute, minCutLukeDpBottomUpTabulation);
        log.debug("Palindrome partitioning II minCut {} OK", () -> "minCutLukeDpBottomUpTabulation");

        var minCutLukeDpBottomUpTabulationOptimized = palindromePartitioningII.minCutLukeDpBottomUpTabulationOptimized(s);
        Assertions.assertEquals(minCutLukeBrute, minCutLukeDpBottomUpTabulationOptimized);
        log.debug("Palindrome partitioning II minCut {} OK", () -> "minCutLukeDpBottomUpTabulationOptimized");

        var minCutExpendfromCenter = palindromePartitioningII.minCutExpendfromCenter(s);
        Assertions.assertEquals(minCutLukeBrute, minCutExpendfromCenter);
        log.debug("Palindrome partitioning II minCut {} OK", () -> "minCutExpendfromCenter");

        var minCutLukeDpExpendingPalindromeFromCenter = palindromePartitioningII.minCutLukeDpExpendingPalindromeFromCenter(s);
        Assertions.assertEquals(minCutLukeBrute, minCutLukeDpExpendingPalindromeFromCenter);
        log.debug("Palindrome partitioning II minCut {} OK", () -> "minCutLukeDpExpendingPalindromeFromCenter");

    }

    /**
     * Luke - DP - Expending Palindrome Around Center
     *
     * Runtime: 4 ms, faster than 99.13% of Java online submissions for Palindrome Partitioning II.
     * Memory Usage: 40 MB, less than 97.87% of Java online submissions for Palindrome Partitioning II.
     *
     * Time: O(N ^ 2) - The outer loop that fixes the middle index iterates N times. The are 2 inner loops iterates for N / 2 times each.
     *                  This gives us time complexity as, O(N * (N / 2 + N / 2)) = O(N ^ 2).
     * Space: O(N) - Size of dp.
     */
    public int minCutLukeDpExpendingPalindromeFromCenter(String s) {
        final int N = s.length();

        final int[] dpMinCuts = new int[N];

        for (int i = 0; i < N; i++) {
            dpMinCuts[i] = i;
        }

        for (int mid = 0; mid < N; mid++) {
            expendFromCenter(mid, mid, s, dpMinCuts);
            expendFromCenter(mid, mid + 1, s, dpMinCuts);

            // log.debug("dpMinCuts: {}", dpMinCuts);
        }

        return dpMinCuts[N - 1];
    }

    private void expendFromCenter(int start, int end, final String s, final int[] dpMinCuts) {
        final int N = s.length();

        int left = start;
        int right = end;

        /**
         * Expend from center.
         */
        while (left >= 0 && right < N && s.charAt(left) == s.charAt(right)) {
            int curr = left == 0 ? 0 : dpMinCuts[left - 1] + 1;
            dpMinCuts[right] = Math.min(dpMinCuts[right], curr);

            left--;
            right++;
        }
    }

    /**
     * LC - DP - Expend Around The Center
     *
     * Runtime: 4 ms, faster than 99.13% of Java online submissions for Palindrome Partitioning II.
     * Memory Usage: 40.2 MB, less than 96.66% of Java online submissions for Palindrome Partitioning II.
     *
     * Time: O(N ^ 2) - The outer loop that fixes the middle index iterates N times. The are 2 inner loops iterates for N / 2 times each.
     *                  This gives us time complexity as, O(N * (N / 2 + N / 2)) = O(N ^ 2).
     * Space: O(N) - Size of dp.
     */
    public int minCutExpendfromCenter(String s) {
        final int N = s.length();

        final int[] dpCuts = new int[N];

        /**
         * Init.
         */
        for (int i = 1; i <= N; i++) {
            dpCuts[i] = i;
        }

        for (int mid = 0; mid < N; mid++) {
            // check for odd length palindrome around mid index
            findMinimumCutsLcExpendFromCerter(mid, mid, dpCuts, s);
            // check for even length palindrome around mid index
            findMinimumCutsLcExpendFromCerter(mid - 1, mid, dpCuts, s);
        }
        return dpCuts[N - 1];
    }

    public void findMinimumCutsLcExpendFromCerter(int startIndex, int endIndex, final int[] dpCuts, String s) {
        for (int start = startIndex, end = endIndex, n = s.length(); start >= 0 && end < n && s.charAt(start) == s.charAt(end); start--, end++) {
            int newCut = start == 0 ? 0 : dpCuts[start - 1] + 1;
            dpCuts[end] = Math.min(dpCuts[end], newCut);
        }
    }

    /**
     * Luke - DP - Bottom-Up Tabulation Optimized
     *
     * "DP - Bottom-Up Tabulation" approach can be optimized (1) without initialization of dp and memo arrays, and (2) complete
     * computation inside the loop.
     *
     * Runtime: 42 ms, faster than 73.69% of Java online submissions for Palindrome Partitioning II.
     * Memory Usage: 48 MB, less than 56.71% of Java online submissions for Palindrome Partitioning II.
     *
     * Time: O(N ^ 2)
     * Space: O(N ^ 2)
     */
    public int minCutLukeDpBottomUpTabulationOptimized(String s) {
        final int N = s.length();
        final boolean[][] memoIsPalindrome = new boolean[N][N];
        final int[] dpMinCuts = new int[N];

        for (int end = 0; end < N; end++) {
            char chEnd = s.charAt(end);
            int minCut = end;
            for (int start = 0; start <= end; start++) {
                if (s.charAt(start) == chEnd) {
                    if (end - start <= 2 || memoIsPalindrome[start + 1][end - 1]) {
                        memoIsPalindrome[start][end] = true;
                        minCut = start == 0 ? 0 : Math.min(minCut, dpMinCuts[start - 1] + 1);
                    }
                }
            }

            dpMinCuts[end] = minCut;

            // log.debug("dpMinCuts: {}", dpMinCuts);
        }

        // DpUtils.print(memoIsPalindrome);

        return dpMinCuts[N - 1];
    }

    /**
     * Luke - DP - Bottom-Up Tabulation
     *
     * Runtime: 21 ms, faster than 89.88% of Java online submissions for Palindrome Partitioning II.
     * Memory Usage: 44.4 MB, less than 64.31% of Java online submissions for Palindrome Partitioning II.
     *
     * Time: O(N ^ 2)
     * Space: O(N ^ 2)
     */
    public int minCutLukeDpBottomUpTabulation(String s) {
        final int N = s.length();

        /**
         * OK to use "boolean" instead of "Boolean" because the memo will be initialized first.
         * If no initialization, use "Boolean" so that "NULL" means "unvisited", and "TRUE/FALSE" means visited.
         */
        final boolean[][] memoIsPalindrome = new boolean[N][N];

        /*
         * Time: O(N ^ 2)
         * Space: O(N ^ 2)
         */
        initPalindromeDpTabulation(s, memoIsPalindrome);

        final int[] dpCuts = new int[N];

        /**
         * Time: O(N)
         * Space: O(N)
         */
        for (int start = 0; start < N; start++) {
            dpCuts[start] = start == 0 ? 0 : dpCuts[start - 1] + 1;
        }

        /**
         * Time: O(N ^ 2)
         * Space: O(N ^ 2)
         */
        for (int start = 0; start < N; start++) {
            for (int end = start; end < N; end++) {
                if (memoIsPalindrome[start][end]) {
                    dpCuts[end] = start == 0 ? 0 : Math.min(dpCuts[end], dpCuts[start - 1] + 1);

                    // log.debug("dpCuts: {}", dpCuts);
                }
            }
        }

        return dpCuts[N - 1];
    }

    /**
     * Only init Top-Right corner, including the diagonal line.
     *
     * Time: O(N ^ 2)
     * Space: O(N ^ 2)
     */
    private void initPalindromeDpTabulation(String s, boolean[][] memoIsPalindrome) {
        final int N = s.length();

        for (int end = 0; end < N; end++) {
            char chEnd = s.charAt(end);
            for (int start = 0; start <= end; start++) {
                if (s.charAt(start) == chEnd) {
                    if (end - start <= 2 || memoIsPalindrome[start + 1][end - 1]) {
                        memoIsPalindrome[start][end] = true;
                    }
                }
            }
        }

        // DpUtils.print(memoIsPalindrome);
    }

    /**
     * LC - DP - Bottom-UP Tabulation
     *
     * Algorithm:
     *
     * -    Bottom-up Dynamic Programming follows an iterative approach to solve the problem. We have to start by finding the minimum possible cuts
     *      in the smallest substring and move towards a larger substring.
     *
     * -    This can be implemented using a nested loop. The outer loop sets the upper bound for the substring index with variable "end". The inner
     *      loop takes each substring between start and end and calculates the minimum number of cuts for substring from index 0 to "end".
     *
     * -    Build a one-dimensional array cutsDp to store the results of subproblems. cutsDp[i] stores the minimum number of cuts for a substring ending at index i.
     *
     * -    Calculating the minimum number of cuts is similar to the Memoization approach.
     *
     * -    Initially, the "minimumCut" will be equal to the maximum possible cuts for a substring. So for a substring ending at index end, the "minimumCut"
     *      would be equal to the value of index end.
     *
     * -    The minimum cut for s.substring(start, end) can be calculated as,
     *
     *         minimum(minimumCut, Minimum cuts for substring s(start, end))
     *         Minimum cuts for substring s(start, end) = 1 + Minimum cuts for substring s(0, start - 1)
     *
     * -    Minimum cuts for substring "s.substring(0, start - 1)" is equivalent to finding the result for substring ending at index start - 1 which can be given
     *      by "cutsDp[start - 1]". So, we can say that,
     *
     *         Minimum cuts for s.substring(start, end) = 1 + cutsDp[start - 1]
     *
     * -    In the end, we will store the results of the current calculation at "cutsDp[end]" as every chosen substring ends at index end.
     *
     * -    We are using a similar iterative approach to check if a substring is a palindrome or not. We will build the "palindromeDp" beforehand. While finding
     *      the minimum cuts will refer to the stored values in "palindromeDp" and proceed only if the current substring is a palindrome. Refer to above Approach
     *      in Palindrome Partitioning Solution.
     *
     * -    Return the minimum number of cuts for the original substring starting at index 0 and ending at "n - 1" which will be given by "cutsDp[n - 1]".
     *
     *
     * Time: O(N ^ 2) - We are iterating (N * N) times to build the memoPalindrome array and (N * N) times to find the minimum cuts in a nested for-loop. This gives
     *                  us a total time complexity of O(N * N).
     *
     * Space: O(N ^ 2) - We are using a 2-dimensional arrays "memoPalindrome" of size (N * N) and a 1-dimensional array "cutsDp" of size N. Thus, the space complexity
     *                   can be given by, O(N ^ 2).
     */
    public int minCutLcDpBottomUpTabulation(String s) {
        final int N = s.length();

        final Integer dpCuts[] = new Integer[N];

        /**
         * Use "boolean" is OK because the table will be initilized first.
         */
        final boolean memoIsPalindrome[][] = new boolean[N][N];

        // build the palindrome cutsDp for all susbtrings
        initPalindromeDpLcDpBottomUpTabulation(s, memoIsPalindrome);

        for (int end = 0; end < N; end++) {
            int minimumCut = end;
            for (int start = 0; start <= end; start++) {
                if (memoIsPalindrome[start][end]) {
                    minimumCut = start == 0 ? 0 : Math.min(minimumCut, dpCuts[start - 1] + 1);
                }
            }
            dpCuts[end] = minimumCut;
        }
        return dpCuts[N - 1];
    }

    /**
     * Init dpPalindrome.
     *
     * Only init Top-Right corner, including the diagonal line.
     *
     * Time: O(N ^ 2)
     * Space: O(N ^ 2)
     */
    private void initPalindromeDpLcDpBottomUpTabulation(String s, final boolean memoIsPalindrome[][]) {
        for (int end = 0, n = s.length(); end <= n; end++) {
            for (int start = 0; start <= end; start++) {
                if (s.charAt(start) == s.charAt(end) && (end - start <= 2 || memoIsPalindrome[start + 1][end - 1])) {
                    memoIsPalindrome[start][end] = true;
                }
            }
        }

        // DpUtils.print(memoIsPalindrome);
    }

    /**
     * LC - DP Optimized Tabulation
     *
     * In above "DP - Bottom-UP Tabulation", we built the palindrome array beforehand. However, both the process of building the palindrome array and finding the minimum
     * cuts iterate in a similar fashion. There is no need to build the array beforehand. We can combine both processes into a single loop. There is no need to build the
     * matrix beforehand. We can combine both processes into a single loop.
     *
     * Algorithm:
     *
     * -  Iterate over the string and generate all possible substrings in a nested for loop as in above "DP - Bottom-UP Tabulation".
     *
     * -  Check if the substring is a palindrome using the previously calculated values in "memoPalindrome". If the substring is a palindrome, update the results in the
     *    "memoPalindrome" array. Keep doing this to find the minimum cut for the substring ending at index "end" and save it in "cutsDp[end]".
     *
     * Time: O(N ^ 2)
     * Space: O(N ^ 2)
     */
    public int minCutLcDpOptimzedTabulation(String s) {
        final int N = s.length();
        final Integer dpCuts[] = new Integer[N];
        final boolean memoPalindrome[][] = new boolean[N][N];

        for (int end = 0; end < N; end++) {
            int minimumCut = end;
            for (int start = 0; start <= end; start++) {
                // check if substring (start, end) is palidrome
                if (s.charAt(start) == s.charAt(end) && (end - start <= 2 || memoPalindrome[start + 1][end - 1])) {
                    memoPalindrome[start][end] = true;
                    minimumCut = start == 0 ? 0 : Math.min(minimumCut, dpCuts[start - 1] + 1);
                }
            }
            dpCuts[end] = minimumCut;
        }
        return dpCuts[N - 1];
    }

    /**
     * LC - Top-Down Memoization. - Big Lesson: memo MUST be created outside of recursion. memo SHOULD be created
     *                            - inside "starter" of resursion that start calls recursive function.
     *
     * DP - It has an "Overlapping Subproblem" property.
     *
     * Time: O(N ^ 2 * N) - In the recursive method "findMinimumCut", we are calculating the result of any substring only once. We know that
     *                      a string size N has N ^ 2 possible substrings. Thus, Thus, the worst-case time complexity of the recursive method
     *                      "findMinimumCut" is O(N ^ 2).
     *                    - Additionally, within each recursion, we are also checking if a sunstring is palindrome or not. The worst-case time
     *                      complexity is O(N / 2).
     *                    - This gives us total time complexity as O(N ^ 2) * O(N / 2) = O(N ^ 2 * N)
     *
     * Space: O(N ^ 2) - We are using teo 2-D arrays memoCuts amd memoPalindrome of size N * N
     *                 - This gives us total space complexity as O(N ^ 2 + N ^ 2) = O(N ^ 2)
     *
     */
    public int minCutLcTopDownMemo(String s) {
        final int N = s.length();

        final Integer memoCuts[][] = new Integer[N][N];
        final Boolean memoPalindrome[][] = new Boolean[N][N];
        return findMinimumCutTopDownMemo(s, 0, N - 1, N - 1, memoCuts, memoPalindrome);
    }

    /**
     * Time: O(N ^ 2 * N)
     * Space: O(N ^ 2)
     */
    private int findMinimumCutTopDownMemo(
            String s,
            int start,
            int end,
            int minimumCut,
            final Integer memoCuts[][],
            final Boolean memoPalindrome[][]) {

        // base case
        if (start == end || isPalindromeTopDownMemo(s, start, end, memoPalindrome)) {
            return 0;
        }
        // check for results in memoCuts
        if (memoCuts[start][end] == null) {
            for (int currentEndIndex = start; currentEndIndex <= end; currentEndIndex++) {
                if (isPalindromeTopDownMemo(s, start, currentEndIndex, memoPalindrome)) {
                    minimumCut = Math.min(minimumCut,
                            1 + findMinimumCutTopDownMemo(s, currentEndIndex + 1, end, minimumCut, memoCuts, memoPalindrome));
                }
            }
            return memoCuts[start][end] = minimumCut;
        } else {
            return memoCuts[start][end];
        }
    }

    /**
     * Time: O(N) - Average: O(1). First call: O(N)
     * Space: O(N ^ 2)
     */
    private boolean isPalindromeTopDownMemo(String s, int start, int end, final Boolean memoPalindrome[][]) {
        if (start >= end) {
            return true;
        }
        // check for results in memoPalindrome
        if (memoPalindrome[start][end] != null) {

            // log.debug("==================== memo in action LC Top-Down ===================");

            return memoPalindrome[start][end];
        }
        return memoPalindrome[start][end] = (s.charAt(start) == s.charAt(end))
                && isPalindromeTopDownMemo(s, start + 1, end - 1, memoPalindrome);
    }

    /**
     * LC - Top-Down memoization + Space Optimization
     *
     *    - In above "Top-Down memoization" approach, we used a 2-dimensional array for "memoCuts". On careful observation, we notice that when we update or
     *    - access the stored values to or from memoCuts, the value of variable "end" always remains the same.
     *    -
     *    - Thus, we only need to track and update the "start" index when finding the minimum number of cuts. The "memoCuts[start]"" will determine the minimum
     *      number of cuts for a substring starting at index "start" and ending at index "length(s) - 1".
     *
     * Time: O(N ^ 2 * N)
     * Space> O(N ^ 2)
     *
     */
    public int minCutLcTopDownMemoSpaceOptimized(String s) {
        final int N = s.length();

        final Integer memoCuts[] = new Integer[N];
        final Boolean memoPalindrome[][] = new Boolean[N][N];

        return findMinimumCutLcTopDownMemoSpaceOptimized(s, 0, N - 1, N - 1, memoCuts, memoPalindrome);
    }

    private int findMinimumCutLcTopDownMemoSpaceOptimized(
            String s,
            int start,
            int end,
            int minimumCut,
            final Integer memoCuts[],
            final Boolean memoPalindrome[][]) {
        // base case
        if (start == end || isPalindromeLcTopDownMemoSpaceOptimized(s, start, end, memoPalindrome)) {
            return 0;
        }
        // check for results in memoCuts
        if (memoCuts[start] != null) {
            return memoCuts[start];
        }
        for (int currentEndIndex = start; currentEndIndex <= end; currentEndIndex++) {
            if (isPalindromeLcTopDownMemoSpaceOptimized(s, start, currentEndIndex, memoPalindrome)) {
                minimumCut = Math.min(minimumCut,
                        1 + findMinimumCutLcTopDownMemoSpaceOptimized(s, currentEndIndex + 1, end, minimumCut, memoCuts, memoPalindrome));
            }
        }
        return memoCuts[start] = minimumCut;
    }

    private boolean isPalindromeLcTopDownMemoSpaceOptimized(String s, int start, int end, final Boolean memoPalindrome[][]) {
        if (start >= end) {
            return true;
        }
        // check for results in memoPalindrome
        if (memoPalindrome[start][end] != null) {
            return memoPalindrome[start][end];
        }
        return memoPalindrome[start][end] = (s.charAt(start) == s.charAt(end))
                && isPalindromeLcTopDownMemoSpaceOptimized(s, start + 1, end - 1, memoPalindrome);
    }

    /**
     * LC - Brute - Amazing: LC calculated minimumCut without making the class stateful!!!
     *
     * It is amazing that LC calculated minimumCut without making the class stateful.
     * The trick is to pass minimumCut to recursion and init it with string length, the return the Math.min of minimumCut of each iteration.
     *
     * Time: O(N * 2 ^ N) -- Let's understand the time complexity of the backtracking method call findMinimumCut given by T(N).
     *
     *         For a string of size N the recursive method findMinimumCut will recur for substrings of size N-1, N-2, N-3 and so on. This can be written as,
     *         T(N) = T(N-1) + T(N-2) + ... + T(1)
     *         Similarly, T(N - 1) can be written as,
     *         T(N - 1) = T(N-2) + T(N-3) + ... + T(1)
     *         Subtracting the above 2 expressions and solving the expression, we get,
     *         T(N) = 2T(N-1)
     *         T(N) = 2T(N - 1) = 4T(N - 2) = 8T(N - 3) = 2 ^ N T(1) = O(2 ^ N)
     *
     *         Therefore, the time complexity of above recurrence relation is given by,
     *         T(N) = O(2 ^ N)
     *
     *         Additionally, to check if a substring is a palindrome or not we must iterate O(N/2) times within each recursive call.
     *         This gives us total time complexity of O(2N) * O(N/2) = O(2 ^ N *N).
     *
     * Space: O(N) - The recursive method uses an internal call stack. In this case, if we place a cut after every character in the
     *               string (a|a|b), the size of the internal stack would be at most N.
     */
    public int minCutLcBrute(String s) {
        /**
         * Amazing! Calculate minimumCut in recursion without making the class stateful, and by using param only.
         * First, init the param to string.length().
         */
        return findMinimumCutLcBrute(s, 0, s.length() - 1, s.length() - 1);
    }

    /**
     * Amazing! Calculate minimumCut in recursion without making the class stateful, and by using param only.
     * First, init the param to string.length().
     */
    private int findMinimumCutLcBrute(String s, int start, int end, int minimumCut) {
        /**
         * Aother surprise of base condition!
         *
         * Base condition, no cut needed for an empty substring or palindrome substring.
         */
        if (start == end || isPalindromeLcBrute(s, start, end)) {
            return 0;
        }

        for (int currentEndIndex = start; currentEndIndex <= end; currentEndIndex++) {
            // find result for substring (start, currentEndIndex) if it is palindrome
            if (isPalindromeLcBrute(s, start, currentEndIndex)) {
                /**
                 * Math.min()
                 */
                minimumCut = Math.min(minimumCut, 1 + findMinimumCutLcBrute(s, currentEndIndex + 1, end, minimumCut));
            }
        }

        /**
         * return minmumCut without making the class stateful.
         */
        return minimumCut;
    }

    private boolean isPalindromeLcBrute(String s, int start, int end) {
        while (start < end) {
            if (s.charAt(start++) != s.charAt(end--)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Luke - DFS - With memo in "isPalindrome()"
     *
     * Time Limit Exceeded
     *
     * Time: O(N) * O(2 ^ N)
     * Space: O(N)
     */
    public int minCutLukeBrute(String s) {
        if (s == null || s.isEmpty()) {
            return 0;
        }

        final int N = s.length();

        /**
         * Important! This memo must be create here. If it is created inside recursion, the memo will be re-created for
         * each recursion call, and the values cannot be re-used.
         */
        final Boolean[][] memoPalindrome = new Boolean[N][N];
        final Integer[] memoMinCut = new Integer[N];

        minCutLukeBrute(s, 0, -1, memoMinCut, memoPalindrome);

        return minCut;
    }

    int minCut = -1;

    /**
     * Time: O(N) * O(2 ^ N)
     * Space: O(N ^ 2)
     */
    void minCutLukeBrute(final String s, int start, int level, Integer[] memoMinCut, final Boolean[][] memoPalindrome) {
        if (start >= s.length()) {
            if (minCut == -1) {
                minCut = level;
            } else {
                minCut = Math.min(minCut, level);
            }
            return;
        }

        /*
        if(memoMinCut[start] != null) {
            return memoMinCut[start];
        }
        */

        if (minCut != -1 && level > minCut) {
            /**
             * Add tail? No.
             */
            // level++;
            return;
        }

        /**
         * This is not DP. It is memo. And it is useless because the start and end are keep moving at the sametime.
         *
         * Actually, it is useful in some cases for levels after first level. For example, "aaaaaaaaaaaaa".
         *
         * This is WRONG!!! memo MUST be created in base call. Creating a memo inside recursion will not help, and it is just a tmp
         * variable and will be re-created by next recursion.
         */
        // final Boolean[][] memo = new Boolean[s.length()][s.length()];

        int end = s.length() - 1;

        while (start <= end) {
            /**
             * This is "do-or-not-do". Time for "do-or-not-do" is O(2 ^ N)
             */
            // if (isPalindrome(s, start, end, memo)) {
            if (isPalindromeLukeMemo(s, start, end, memoPalindrome)) {
                /**
                 * This "level++;" must be inside "while loop", because "minCutLukeBrute(s, end + 1, level);" and "level--;" are inside "while loop".
                 */
                level++;
                minCutLukeBrute(s, end + 1, level, memoMinCut, memoPalindrome);
                level--;
            }

            end--;
        }
    }

    /**
     * Time: O(N)
     * Space: O(1)
     */
    boolean isPalindromeLukeNoMemo(final String s, int left, int right) {
        // int a = (left + right) / 2;
        // int z = (left + right) / 2 + (left + right) % 2;

        while (left <= right && s.charAt(left) == s.charAt(right)) {
            left++;
            right--;
        }

        return left > right;
    }

    /**
     * Time: O(N)
     * Space: O(1)
     */
    boolean isPalindromeLukeMemo(final String s, int start, int end, Boolean[][] memoPalindrome) {
        if (start > end) {
            return true;
        }

        if (memoPalindrome[start][end] == null) {
            return memoPalindrome[start][end] = s.charAt(start) == s.charAt(end)
                    && isPalindromeLukeMemo(s, start + 1, end - 1, memoPalindrome);

            /*
            if (s.charAt(start) == s.charAt(end)) {
                if (end - start <= 2) {
                    dp[start][end] = true;
                    return dp[start][end];
                } else {
                    return isPalindromeMemo(s, start + 1, end - 1, dp);
                }
            } else {
                dp[start][end] = false;
                return dp[start][end];
            }
            */
        } else {
            /**
             * This is not DP. It is memo. And it is useless because the start and end are keep moving at the sametime.
             *
             * Actually, it is useful in some cases for levels after first level. For example, "aaaaaaaaaaaaa".
             */

            // log.debug("==================== memo in action Luke Brute ===================");

            return memoPalindrome[start][end];
        }
    }

}
