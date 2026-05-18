package com.learn.other;


import org.junit.jupiter.api.Assertions;

import lombok.extern.log4j.Log4j2;


/**
 * Amazon - 4 - Median of Two Sorted Arrays
 *
 * Hard
 *
 * Given two sorted arrays nums1 and nums2 of size m and n respectively, return the median of the two sorted arrays.
 *
 * The overall run time complexity should be O(log (m+n)).
 *
 * Example 1:
 * Input: nums1 = [1,3], nums2 = [2]
 * Output: 2.00000
 * Explanation: merged array = [1,2,3] and median is 2.
 *
 * Example 2:
 * Input: nums1 = [1,2], nums2 = [3,4]
 * Output: 2.50000
 * Explanation: merged array = [1,2,3,4] and median is (2 + 3) / 2 = 2.5.
 *
 * Constraints:
 *     nums1.length == m
 *     nums2.length == n
 *     0 <= m <= 1000
 *     0 <= n <= 1000
 *     1 <= m + n <= 2000
 *     -106 <= nums1[i], nums2[i] <= 106
 */
@Log4j2
public class MedianOfTwoSortedArrays {

    public static void main(String[] args) {

        /**
         * Expected: 2.5
         */
        // final int[] nums1 = { 1, 2 }, nums2 = { 2, 3, 4 };

        /**
         * Expected: 1
         */
        // final int[] nums1 = {}, nums2 = { 1 };

        /**
         * Expected: 4
         */
        // final int[] nums1 = { 5 }, nums2 = { 1, 2, 3, 4, 5, 6 };

        /**
         * Expected: 2
         */
        // final int[] nums1 = { 2 }, nums2 = { 1, 3 };

        /**
         * Expected: 3
         */
        // final int[] nums1 = { 5 }, nums2 = { 1, 3 };

        /**
         * Expected: 1
         */
        final int[] nums1 = { 0 }, nums2 = { 1, 3 };

        /**
         * Expected: 4
         */
        // int[] nums1 = { -5, 3, 6, 12, 15 }, nums2 = { -12, -10, -6, -3, 4, 10 };

        MedianOfTwoSortedArrays medianOfTwoSortedArrays = new MedianOfTwoSortedArrays();

        var findMedianSortedArraysBinarySearch = medianOfTwoSortedArrays.findMedianSortedArraysBinarySearch(nums1, nums2);
        log.debug("Median of Two Sorted Arrays: {}", () -> findMedianSortedArraysBinarySearch);
        log.debug("Median of Two Sorted Arrays {} OK", () -> "findMedianSortedArraysBinarySearch");

        var findMedianSortedArraysLukeBinarySearch = medianOfTwoSortedArrays.findMedianSortedArraysLukeBinarySearch(nums1, nums2);
        Assertions.assertEquals(findMedianSortedArraysBinarySearch, findMedianSortedArraysLukeBinarySearch);
        log.debug("Median of Two Sorted Arrays {} OK", () -> "findMedianSortedArraysLukeBinarySearch");

    }

    /**
     * Luke - Binary Search
     *
     * Time: O(log(M + N))
     * Space: O(1)
     */
    public double findMedianSortedArraysLukeBinarySearch(int[] nums1, int[] nums2) {
        int len1 = nums1.length;
        int len2 = nums2.length;

        if (len1 > len2) {
            return findMedianSortedArraysLukeBinarySearch(nums2, nums1);
        }

        /**
         * To handle nums1 is empty. When nums1 is empty, start: -1, end: -1
         */
        int start = Math.min(len1 - 1, 0);
        int end = len1 - 1;

        /**
         * int realMidAfterMerge = len1 + (len2 - len1 - 1) / 2;
         *
         * 2 + 2 ---> 1 ---> idx should be between 1 to 2
         * 2 + 3 ---> 2 ---> idx correct
         */
        int midIdxAfterMerge = (len1 + len2 - 1) / 2;

        /**
         * To handle nums1 is empty. When nums1 is empty, start: -1, end: -1
         */
        while (start <= end) {
            int posA = (start + end) / 2;

            /**
             * discount 1. why?
             */
            int posB = midIdxAfterMerge - posA - 1;

            // log.debug("----- posA: {}, posB: {}, start: {}, end: {}, midIdxAfterMerge: {}", posA, posB, start, end, midIdxAfterMerge);

            int leftA = (posA >= 0 && len1 > 0) ? nums1[posA] : Integer.MIN_VALUE;
            int rightA = (posA + 1 < len1) ? nums1[posA + 1] : Integer.MAX_VALUE;

            int leftB = (posB >= 0) ? nums2[posB] : Integer.MIN_VALUE;
            int rightB = (posB + 1 < len2) ? nums2[posB + 1] : Integer.MAX_VALUE;

            // log.debug("leftA: {}, rightA: {} ---- leftB: {}, rightB: {}", leftA, rightA, leftB, rightB);

            if (leftA <= rightB && leftB <= rightA) {
                boolean isMergedOdd = (len1 + len2) % 2 == 1;
                if (isMergedOdd) {
                    return Math.max(leftA, leftB);
                } else {
                    return (double) (Math.max(leftA, leftB) + Math.min(rightA, rightB)) / 2.0;
                }
            } else if (leftA >= rightB) {
                start--;
            } else {
                start++;
            }
        }

        return -99;
    }

    /**
     * YouTube - Binary Search
     *
     *
     */
    public double findMedianSortedArraysBinarySearch(int[] nums1, int[] nums2) {
        int len1 = nums1.length;
        int len2 = nums2.length;

        if (len1 > len2) {
            return findMedianSortedArraysBinarySearch(nums2, nums1); // Swapping to make A smaller
        }

        int start = 0;
        int end = len1;
        int realmidinmergedarray = (len1 + len2 + 1) / 2;

        while (start <= end) {
            int mid = (start + end) / 2;
            int leftAsize = mid;
            int leftBsize = realmidinmergedarray - mid;
            int leftA = (leftAsize > 0) ? nums1[leftAsize - 1] : Integer.MIN_VALUE; // checking overflow of indices
            int leftB = (leftBsize > 0) ? nums2[leftBsize - 1] : Integer.MIN_VALUE;
            int rightA = (leftAsize < len1) ? nums1[leftAsize] : Integer.MAX_VALUE;
            int rightB = (leftBsize < len2) ? nums2[leftBsize] : Integer.MAX_VALUE;

            // if correct partition is done
            if (leftA <= rightB && leftB <= rightA) {
                /**
                 * A: 1 2 3 4
                 * B: 0 2 3
                 */
                if ((len2 + len1) % 2 == 0) {
                    return (Math.max(leftA, leftB) + Math.min(rightA, rightB)) / 2.0;
                } else {
                    return Math.max(leftA, leftB);
                }
            } else if (leftA > rightB) {
                end = mid - 1;
            } else {
                start = mid + 1;
            }
        }
        return 0.0;
    }

}
