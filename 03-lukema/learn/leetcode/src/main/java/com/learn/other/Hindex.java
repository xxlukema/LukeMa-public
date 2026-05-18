package com.learn.other;


import java.util.Arrays;

import org.junit.jupiter.api.Assertions;

import lombok.extern.log4j.Log4j2;


/**
 * LC-274-H-Index
 *
 * Medium
 *
 * Given an array of integers citations where citations[i] is the number of citations a researcher received
 * for their ith paper, return compute the researcher's h-index.
 *
 * According to the definition of h-index on Wikipedia: A scientist has an index h if h of their n papers have
 * at least h citations each, and the other n − h papers have no more than h citations each.
 *
 * If there are several possible values for h, the maximum one is taken as the h-index.
 *
 * Example 1:
 * Input: citations = [3,0,6,1,5]
 * Output: 3
 * Explanation: [3,0,6,1,5] means the researcher has 5 papers in total and each of them had received 3, 0, 6, 1, 5
 * citations respectively. Since the researcher has 3 papers with at least 3 citations each and the remaining two
 * with no more than 3 citations each, their h-index is 3.
 *
 * Example 2:
 * Input: citations = [1,3,1]
 * Output: 1
 *
 * Constraints:
 *     n == citations.length
 *     1 <= n <= 5000
 *     0 <= citations[i] <= 1000
 *
 * Further Thoughts:
 *     Is it possible to have multiple hhh-values?
 */
@Log4j2
public class Hindex {

    public static void main(String[] args) {

        /**
         * Expected: 3
         */
        // final int[] citations = { 3, 0, 6, 1, 5 };

        /**
         * Expected: 3
         */
        final int[] citations = { 4, 4, 0, 0 };

        /**
         * Expected: 3
         */
        // final int[] citations = { 3, 0, 6, 1, 5 };

        /**
         * Expected: 1
         */
        // final int[] citations = { 100 };

        Hindex hindex = new Hindex();

        var hIndexLuke = hindex.hIndexLuke(Arrays.copyOf(citations, citations.length));
        log.debug("H-Index: {}", () -> hIndexLuke);
        log.debug("H-Index {} OK", () -> "hIndexLuke");

        var hIndexLc = hindex.hIndexLc(Arrays.copyOf(citations, citations.length));
        Assertions.assertEquals(hIndexLuke, hIndexLc);
        log.debug("H-Index {} OK", () -> "hIndexLc");

        var hIndexRealLc = hindex.hIndexRealLc(Arrays.copyOf(citations, citations.length));
        Assertions.assertEquals(hIndexLuke, hIndexRealLc);
        log.debug("H-Index {} OK", () -> "hIndexRealLc");

    }

    /**
     * Luke - sorted array
     *
     * Runtime: 8 ms Beats 16.21%
     * Memory: 41.9 MB Beats 54.68%
     *
     * Time: O(N log(N))
     * Space: O(N)
     */
    public int hIndexLuke(int[] citations) {
        /**
         * Time: O(N long(N))
         * Space: O(N)
         */
        Arrays.sort(citations);

        for (int i = 0, len = citations.length; i < len; i++) {
            if (citations[i] >= len - i) {
                return len - i;
            }
        }

        return 0;
    }

    /**
     * LC - Non-Comparison sort
     *
     * Trick: If citation[i] >= n, then citation[i] = n, then apply frequency sorting algorithm.
     *
     * Runtime: 1 ms Beats 92.57%
     * Memory: 42.2 MB Beats 30.10%
     *
     * Time: O(N)
     * Space: O(N)
     */
    public int hIndexLc(int[] citations) {
        final int len = citations.length;
        final int[] frequency = new int[len + 1];

        /**
         * Trick: If citation[i] >= n, then citation[i] = n, then apply frequency sorting algorithm.
         */
        for (int i = 0; i < len; i++) {
            if (citations[i] >= len) {
                citations[i] = len;
            }

            frequency[citations[i]] += 1;
        }

        int pos = 0;
        for (int i = 0; i < frequency.length; i++) {
            while (frequency[i] > 0) {
                citations[pos++] = i;
                frequency[i] -= 1;
            }
        }

        for (int i = 0; i < len; i++) {
            if (citations[i] >= len - i) {
                return len - i;
            }
        }

        return 0;
    }

    /**
     * LC - Trick 1: Reassign max papers to n
     *      Trick 2: citations not changed
     *      Trick 3: Bucket sort
     *
     * Time: O(N)
     * Space: O(N)
     */
    public int hIndexRealLc(int[] citations) {
        int n = citations.length;
        int[] buckets = new int[n + 1];
        // counting papers for each citation number
        for (int c : citations) {
            // buckets[Math.min(n, c)]++;
            if (c >= n) {
                buckets[n]++;
            } else {
                buckets[c]++;
            }
        }

        log.debug("papers: {}", buckets);

        // finding the h-index
        for (int i = n, count = 0; i >= 0; i--) {
            count += buckets[i];
            if (count >= i) {
                return i;
            }
        }

        return 0;
    }

}
