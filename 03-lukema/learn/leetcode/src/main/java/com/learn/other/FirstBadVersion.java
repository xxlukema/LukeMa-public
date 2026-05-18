package com.learn.other;


/**
 * LC - 278 - First Bad Version
 *
 * Easy
 *
 * You are a product manager and currently leading a team to develop a new product. Unfortunately, the latest version of your
 * product fails the quality check. Since each version is developed based on the previous version, all the versions after a
 * bad version are also bad.
 *
 * Suppose you have n versions [1, 2, ..., n] and you want to find out the first bad one, which causes all the following ones to be bad.
 *
 * You are given an API bool isBadVersion(version) which returns whether version is bad. Implement a function to find the first bad version.
 * You should minimize the number of calls to the API.
 *
 * Example 1:
 * Input: n = 5, bad = 4
 * Output: 4
 * Explanation:
 * call isBadVersion(3) -> false
 * call isBadVersion(5) -> true
 * call isBadVersion(4) -> true
 * Then 4 is the first bad version.
 *
 * Example 2:
 * Input: n = 1, bad = 1
 * Output: 1
 *
 * Constraints:
 *     1 <= bad <= n <= 2 ^ 31 - 1
 */
public class FirstBadVersion
    extends VersionControl {

    public static void main(String[] args) {

    }

    /**
     * Luke - Binary Search - Recursion
     *
     * Runtime: 19 ms Beats 83.45%
     * Memory: 39.3 MB Beats 82.78%
     *
     * Time: O(log(n))
     * Space: O(log(n)) stack depth
     */
    public int firstBadVersionLuke(int n) {
        return firstBadVersionLuke(n, 1, n);
    }

    int firstBadVersionLuke(final int n, final int left, final int right) {
        if (left == right) {
            return left;
        }

        /**
         * Trick: "int add" overflow prevention:
         */
        int mid = left + (right - left) / 2;

        if (isBadVersion(mid)) {
            /**
             * bad must be included
             */
            return firstBadVersionLuke(n, left, mid - 1);
        } else {
            return firstBadVersionLuke(n, mid + 1, right);
        }
    }

    /**
     * LC - Binary Search - Iteration - Two Pointers
     *
     * Runtime: 22 ms Beats 72.23%
     * Memory: 40.3 MB Beats 76.11%
     *
     * Time: O(log(n))
     * Space: O(i) stack depth
     */
    public int firstBadVersionLc(int n) {

        int left = 1;
        int right = n;

        while (left < right) {
            int mid = left + (right - left) / 2;
            if (isBadVersion(mid)) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }

        return left;
    }
}


class VersionControl {
    boolean isBadVersion(int version) {
        return true;
    }
}
