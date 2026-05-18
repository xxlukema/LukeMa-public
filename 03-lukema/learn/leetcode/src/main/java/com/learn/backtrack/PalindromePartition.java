package com.learn.backtrack;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import lombok.extern.log4j.Log4j2;


/**
 * LC - 131 - Palindrome Partition
 *
 * Medium
 *
 * Given a string s, partition s such that every substring of the partition is a palindrome. Return all possible palindrome partitioning of s.
 * A palindrome string is a string that reads the same backward as forward.
 *
 * Example 1:
 * Input: s = "aab"
 * Output: [["a","a","b"],["aa","b"]]
 *
 * Example 2:
 * Input: s = "a"
 * Output: [["a"]]
 *
 * Constraints:
 *     1 <= s.length <= 16
 *     s contains only lowercase English letters.
 *
 */
@Log4j2
public class PalindromePartition {

    public static void main(String[] args) {

        // final String s = "aab";
        final String s = "efe";

        PalindromePartition palindromePartition = new PalindromePartition();

        var ret = palindromePartition.partitionLukeBrute(s);
        log.debug("Partition Luke brute: {}", () -> ret);

    }

    /**
     * Luke - Brute
     *
     * Runtime: 28 ms, faster than 20.22% of Java online submissions for Palindrome Partitioning.
     * Memory Usage: 211.8 MB, less than 5.61% of Java online submissions for Palindrome Partitioning.
     *
     * Time: O(N ^ 3)
     * Space: O(N ^ 2)
     */
    public List<List<String>> partitionLukeBrute(String s) {
        final List<List<String>> partn = new ArrayList<>();
        final LinkedList<String> list = new LinkedList<>();
        if (s == null || s.isEmpty()) {
            return partn;
        }

        partitionLukeBrute(s, 0, s.length() - 1, partn, list);

        return partn;
    }

    /**
     * Time: O(N) for "for loop". O(N ^ 2) for "recusrion".
     * Space: O(2 ^ N) - There can be O(2 ^ N) combos allConnectingPalindromes. Worst case "aaaaaaaaaaaaa".
     */
    private void partitionLukeBrute(final String s, int left, int right, final List<List<String>> partn, final LinkedList<String> list) {
        if (left >= s.length()) {
            partn.add(List.copyOf(list));
            return;
        }

        List<InnerPalindromePartition> allConnectingPalindromes = allConnectingPalindromes(s, left, right);
        for (InnerPalindromePartition item : allConnectingPalindromes) {
            list.add(s.substring(item.left, item.right + 1));
            partitionLukeBrute(s, item.right + 1, s.length() - 1, partn, list);
            list.removeLast();
        }
    }

    /**
     * Improvable
     *
     * Only return connecting Palindromes. No jumps. All starts with "left" index.
     *
     * Time: O(N) for this, * O(N) for isPalindrome()
     * Space: O(N) - N - String.length()
     */
    private List<InnerPalindromePartition> allConnectingPalindromes(final String s, int left, int right) {
        List<InnerPalindromePartition> all = new ArrayList<>();

        int end = left;
        while (end <= right) {
            if (isPalindromeLukeBrute(s, left, end)) {
                all.add(new InnerPalindromePartition(left, end));
            }
            end++;
        }

        return all;
    }

    /**
     * Time: O(N)
     * Space: O(1)
     */
    private boolean isPalindromeLukeBrute(final String s, int left, int right) {
        // int a = (left + right) / 2;
        // int z = (left + right) / 2 + (left + right) % 2;

        while (left <= right && s.charAt(left) == s.charAt(right)) {
            left++;
            right--;
        }

        return left > right;
    }

    /**
     * InnerPalindromePartition(int left, int right)
     */
    public record InnerPalindromePartition(int left, int right) {
    }

    /**
     * LC - DFS - Recursion
     *
     * Runtime: 9 ms, faster than 98.15% of Java online submissions for Palindrome Partitioning.
     * Memory Usage: 55 MB, less than 89.36% of Java online submissions for Palindrome Partitioning.
     *
     * Time: O(N) * O(2 ^ N) - O(N) for "for loop". O(2 ^ N) for recursion.
     * Space: O(N ^ 2) - O(N) for currentList size, and O(N) for recursion stack size. It is "multiply", not "add".
     */
    public List<List<String>> partitionLcDfsRecursion(String s) {
        List<List<String>> result = new ArrayList<List<String>>();
        dfsLcRecursion(0, result, new ArrayList<String>(), s);
        return result;
    }

    /**
     * Time: O(N) * O(2 ^ N) - O(N) for "for loop". O(2 ^ N) for recursion.
     * Space: O(N ^ 2) - O(N) for currentList size, and O(N) for recursion stack size. It is "multiply", not "add".
     */
    void dfsLcRecursion(int start, List<List<String>> result, List<String> currentList, String s) {
        if (start >= s.length()) {
            result.add(new ArrayList<String>(currentList));
        }
        for (int end = start; end < s.length(); end++) {
            if (isPalindromeLcBrute(s, start, end)) {
                // add current substring in the currentList
                currentList.add(s.substring(start, end + 1));
                dfsLcRecursion(end + 1, result, currentList, s);
                // backtrack and remove the current substring from currentList
                currentList.remove(currentList.size() - 1);
            }
        }
    }

    /**
     * Time: O(N)
     * Space: O(1)
     */
    boolean isPalindromeLcBrute(String s, int low, int high) {
        while (low < high) {
            if (s.charAt(low++) != s.charAt(high--)) {
                return false;
            }
        }
        return true;
    }

    /**
     * LC - DP
     *
     * Runtime: 18 ms, faster than 62.30% of Java online submissions for Palindrome Partitioning.
     * Memory Usage: 136.3 MB, less than 69.87% of Java online submissions for Palindrome Partitioning.
     *
     * Time : O(N * 2 ^ N) - N is the length of string s. In the worst case, there could be 2 ^ N possible substrings and it will
     *                       take O(N) to generate each substring
     * Space: O(N * N) - N is the length of the string s. The recursive call stack would require N space.
     *                   Additionally we also use 2 dimensional array dp of size N * N.
     */
    public List<List<String>> partitionLcDp(String s) {
        final int LEN = s.length();
        final boolean[][] dp = new boolean[LEN][LEN];
        final List<List<String>> result = new ArrayList<>();
        dfsLcDp(result, s, 0, new ArrayList<>(), dp);
        return result;
    }

    void dfsLcDp(final List<List<String>> result, final String s, int start, final List<String> currentList, final boolean[][] dp) {
        if (start >= s.length()) {
            result.add(new ArrayList<>(currentList));
        }
        for (int end = start; end < s.length(); end++) {
            if (s.charAt(start) == s.charAt(end) && (end - start <= 2 || dp[start + 1][end - 1])) {
                dp[start][end] = true;
                currentList.add(s.substring(start, end + 1));
                dfsLcDp(result, s, end + 1, currentList, dp);
                currentList.remove(currentList.size() - 1);
            }
        }
    }
}
