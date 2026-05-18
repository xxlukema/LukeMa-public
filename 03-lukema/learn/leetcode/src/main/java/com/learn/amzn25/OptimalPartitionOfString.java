package com.learn.amzn25;


import org.junit.jupiter.api.Assertions;

import lombok.extern.log4j.Log4j2;


/**
 * 2405. Optimal Partition of String
 *
 * Medium
 *
 * Given a string s, partition the string into one or more substrings such that the characters in each substring are unique. That is, no letter appears in a single substring more than once.
 *
 * Return the minimum number of substrings in such a partition.
 *
 * Note that each character should belong to exactly one substring in a partition.
 *
 * Example 1:
 *
 * Input: s = "abacaba"
 * Output: 4
 * Explanation:
 * Two possible partitions are ("a","ba","cab","a") and ("ab","a","ca","ba").
 * It can be shown that 4 is the minimum number of substrings needed.
 *
 * Example 2:
 *
 * Input: s = "ssssss"
 * Output: 6
 * Explanation:
 * The only valid partition is ("s","s","s","s","s","s").
 *
 * Constraints:
 *
 *     1 <= s.length <= 105
 *     s consists of only English lowercase letters.
 */
@Log4j2
public class OptimalPartitionOfString {

    public static void main(String[] args) {

        OptimalPartitionOfString optimalPartitionOfString = new OptimalPartitionOfString();

        // String s = "hdklqkcssgxlvehva";
        // int expected = 4;

        String s = "abacaba";
        int expected = 4;

        var ret = optimalPartitionOfString.partitionString(s);
        log.info("Minimum Time Visiting All Points: {}", () -> ret);
        Assertions.assertEquals(expected, ret);
        log.debug("Minimum Time Visiting All Points {} OK", () -> "minTimeToVisitAllPoints");

    }

    /**
     * Runtime: 7ms Beats95.47%of users with Java
     * Memory: 44.15MB Beats68.69%of users with Java
     *
     * Time: O(n)
     * Space: O(1)
     */
    public int partitionString(String s) {
        int min = 1;
        int[] count = new int[26];

        for (char ch : s.toCharArray()) {
            int pos = ch - 'a';
            if (count[pos] > 0) {
                min++;
                count = new int[26];
            }
            count[pos]++;
        }

        return min;
    }
}
