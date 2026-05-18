package com.learn.other;


import lombok.extern.log4j.Log4j2;


/**
 * LC - 163 - Compare Version Numbers
 * 
 * Medium
 * 
 * Given two version numbers, version1 and version2, compare them.
 * 
 * Version numbers consist of one or more revisions joined by a dot '.'. Each revision consists of digits and may contain leading zeros. Every revision contains
 * at least one character. Revisions are 0-indexed from left to right, with the leftmost revision being revision 0, the next revision being revision 1, and so on.
 * For example 2.5.33 and 0.1 are valid version numbers.
 * 
 * To compare version numbers, compare their revisions in left-to-right order. Revisions are compared using their integer value ignoring any leading zeros. 
 * This means that revisions 1 and 001 are considered equal. If a version number does not specify a revision at an index, then treat the revision as 0. For 
 * example, version 1.0 is less than version 1.1 because their revision 0s are the same, but their revision 1s are 0 and 1 respectively, and 0 < 1.
 * 
 * Return the following:
 *     If version1 < version2, return -1.
 *     If version1 > version2, return 1.
 *     Otherwise, return 0.
 * 
 * Example 1:
 * Input: version1 = "1.01", version2 = "1.001"
 * Output: 0
 * Explanation: Ignoring leading zeroes, both "01" and "001" represent the same integer "1".
 * 
 * Example 2:
 * Input: version1 = "1.0", version2 = "1.0.0"
 * Output: 0
 * Explanation: version1 does not specify revision 2, which means it is treated as "0".
 * 
 * Example 3:
 * Input: version1 = "0.1", version2 = "1.1"
 * Output: -1
 * Explanation: version1's revision 0 is "0", while version2's revision 0 is "1". 0 < 1, so version1 < version2.
 * 
 * Constraints:
 *     1 <= version1.length, version2.length <= 500
 *     version1 and version2 only contain digits and '.'.
 *     version1 and version2 are valid version numbers.
 *     All the given revisions in version1 and version2 can be stored in a 32-bit integer.
 */
@Log4j2
public class CompareVersionNumbers {

    public static void main(String[] args) {

        /**
         * Output: 0
         */
        // final String version1 = "1.01", version2 = "1.001";

        /**
         * Output: 0
         */
        // final String version1 = "1.0", version2 = "1.0.0";

        /**
         * Output: -1
         */
        // final String version1 = "0.1", version2 = "1.1";

        /**
         * Output: 1
         */
        final String version1 = "1.0.1", version2 = "1";

        CompareVersionNumbers compareVersionNumbers = new CompareVersionNumbers();

        var ret = compareVersionNumbers.compareVersion(version1, version2);
        log.debug("Compare version numbers: {}", () -> ret);
        log.debug("Compare version numbers {} OK", () -> "ret");
    }

    /**
     * Luke - String.split("\\.")
     * 
     * Use "Two Pointers" to avoid "split()". It can save "String.split()" time complexity.
     * 
     * Runtime: 1 ms, faster than 90.30% of Java online submissions for Compare Version Numbers.
     * Memory Usage: 42 MB, less than 62.28% of Java online submissions for Compare Version Numbers.
     * 
     * Time: O(N + M + max(N, M)) = O(M) + O(N)
     * Space: O(Math.max(s1.length(), s2.length()))
     */
    public int compareVersion(String version1, String version2) {

        /**
         * split() time complexity: O(N), O(M)
         */
        String[] vers1 = version1.split("\\.");
        String[] vers2 = version2.split("\\.");

        int pos = 0;
        while (pos < vers1.length && pos < vers2.length) {
            int v1 = Integer.valueOf(vers1[pos]);
            int v2 = Integer.valueOf(vers2[pos]);

            if (v1 != v2) {
                return v1 < v2 ? -1 : 1;
            } else {
                pos++;
            }
        }

        int sum = 0;
        while (pos < vers1.length) {
            sum += Integer.valueOf(vers1[pos++]);
            if (sum > 0) {
                return 1;
            }
        }

        while (pos < vers2.length) {
            sum += Integer.valueOf(vers2[pos++]);
            if (sum > 0) {
                return -1;
            }
        }

        return 0;
    }
}
