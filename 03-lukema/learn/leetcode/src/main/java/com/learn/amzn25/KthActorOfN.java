package com.learn.amzn25;


import org.junit.jupiter.api.Assertions;

import lombok.extern.log4j.Log4j2;


/**
 * 1492 - The kth Factor of n
 *
 * Medium
 *
 * You are given two positive integers n and k. A factor of an integer n is defined as an integer i where n % i == 0.
 *
 * Consider a list of all factors of n sorted in ascending order, return the kth factor in this list or return -1 if n has less than k factors.
 *
 * Example 1:
 *
 * Input: n = 12, k = 3
 * Output: 3
 * Explanation: Factors list is [1, 2, 3, 4, 6, 12], the 3rd factor is 3.
 *
 * Example 2:
 *
 * Input: n = 7, k = 2
 * Output: 7
 * Explanation: Factors list is [1, 7], the 2nd factor is 7.
 *
 * Example 3:
 *
 * Input: n = 4, k = 4
 * Output: -1
 * Explanation: Factors list is [1, 2, 4], there is only 3 factors. We should return -1.
 *
 * Constraints:
 *
 *     1 <= k <= n <= 1000
 *
 * Follow up:
 *
 * Could you solve this problem in less than O(n) complexity?
 */
@Log4j2
public class KthActorOfN {

    public static void main(String[] args) {

        KthActorOfN kthActorOfN = new KthActorOfN();

        // int n = 12, k = 3;
        // int expected = 3;

        int n = 4, k = 1;
        int expected = 1;

        var ret = kthActorOfN.kthFactor(n, k);
        log.info("Minimum Time Visiting All Points: {}", () -> ret);
        Assertions.assertEquals(expected, ret);
        log.debug("Minimum Time Visiting All Points {} OK", () -> "minTimeToVisitAllPoints");
    }

    /**
     * Runtime: 0ms Beats100.00%of users with Java
     * Memory: 39.64MB Beats24.37%of users with Java
     *
     * Time: O(n)
     * Space: O(1)
     */
    public int kthFactor(int n, int k) {
        if (k == 1) {
            return 1;
        }

        int count = 1;

        for (int i = 2; i <= n; i++) {
            if (n % i == 0) {
                count++;
            }

            if (count == k) {
                return i;
            }
        }

        return -1;
    }

}
