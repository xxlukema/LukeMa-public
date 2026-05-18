package com.learn.other;


import org.junit.jupiter.api.Assertions;

import lombok.extern.log4j.Log4j2;


/**
 * LC-275 H-Index II
 *
 * Medium
 *
 * Given an array of integers citations where citations[i] is the number of citations a researcher received for their ith paper and
 * citations is sorted in an ascending order, return compute the researcher's h-index.
 *
 * According to the definition of h-index on Wikipedia: A scientist has an index h if h of their n papers have at least h citations
 * each, and the other n − h papers have no more than h citations each.
 *
 * If there are several possible values for h, the maximum one is taken as the h-index.
 *
 * You must write an algorithm that runs in logarithmic time.
 *
 * Example 1:
 * Input: citations = [0,1,3,5,6]
 * Output: 3
 * Explanation: [0,1,3,5,6] means the researcher has 5 papers in total and each of them had received 0, 1, 3, 5, 6 citations respectively.
 * Since the researcher has 3 papers with at least 3 citations each and the remaining two with no more than 3 citations each, their h-index is 3.
 *
 * Example 2:
 * Input: citations = [1,2,100]
 * Output: 2
 *
 * Constraints:
 *     n == citations.length
 *     1 <= n <= 10 ^ 5
 *     0 <= citations[i] <= 1000
 *     citations is sorted in ascending order.
 */
@Log4j2
public class HindexII {

    public static void main(String[] args) {

        // final int[] citations = { 0, 1, 3, 5, 6 };
        final int[] citations = { 0, 1 };

        HindexII hindexII = new HindexII();

        var hIndexIterative = hindexII.hIndexIterative(citations);
        log.debug("H-Index II: {}", () -> hIndexIterative);
        log.debug("H-Index II {} OK", () -> "hIndexIterative");

        var hIndexLcBinarySearch = hindexII.hIndexLcBinarySearch(citations);
        Assertions.assertEquals(hIndexIterative, hIndexLcBinarySearch);
        log.debug("H-Index II {} OK", () -> "hIndexLcBinarySearch");

    }

    /**
     * Luke - Iterative
     *
     * Runtime: 1 ms Beats 27.74%
     * Memory: 49.2 MB Beats 90.60%
     *
     * Time: O(N)
     * Space: O(1)
     */
    public int hIndexIterative(int[] citations) {
        for (int i = 0, len = citations.length; i < len; i++) {
            if (citations[i] >= len - i) {
                return len - i;
            }
        }
        return 0;
    }

    /**
     * LC - Binary Search
     *
     * Trick: Sorted array --> Binary search
     *
     * Runtime: 0 ms Beats 100%
     * Memory: 49.4 MB Beats 87.74%
     *
     * Time: O(log(N))
     * Space: O(1)
     */
    public int hIndexLcBinarySearch(int[] citations) {
        final int len = citations.length;

        int left = 0;
        int right = len - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            int matches = len - mid;

            if (citations[mid] == matches) {
                return matches;
            } else if (citations[mid] > matches) {
                if (mid - 1 >= 0 && citations[mid - 1] > matches) {
                    right = mid - 1;
                } else {
                    return matches;
                }
            } else {
                left = mid + 1;
            }
        }

        return 0;
    }
}
