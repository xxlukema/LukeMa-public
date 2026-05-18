package com.learn.other;


import lombok.extern.log4j.Log4j2;


@Log4j2
public class MergeSortedArrays {

    public static void main(String[] args) {

        /**
        final int[] nums1 = { 1, 2, 3, 0, 0, 0 };
        final int m = 3;
        final int[] nums2 = { 2, 5, 6 };
        final int n = 3;
        */

        final int[] nums1 = { 0 };
        final int m = 0;
        final int[] nums2 = { 1 };
        final int n = 1;

        MergeSortedArrays mergeSortedArrays = new MergeSortedArrays();
        mergeSortedArrays.merge(nums1, m, nums2, n);

        log.debug("Merged sorted array: {}", () -> nums1);
    }

    /**
     * Luke: Brute
     * 
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Merge Sorted Array.
     * Memory Usage: 42.8 MB, less than 48.08% of Java online submissions for Merge Sorted Array.
     * 
     * Time: O(M) --- M = nums1.length
     * Space: O(1)
     */
    public void merge(final int[] nums1, final int N1, final int[] nums2, final int N2) {
        int right = nums1.length - 1;
        int pos1 = N1 - 1;
        int pos2 = N2 - 1;

        while (pos1 >= 0 && pos2 >= 0) {
            if (nums1[pos1] > nums2[pos2]) {
                nums1[right--] = nums1[pos1--];
            } else {
                nums1[right--] = nums2[pos2--];
            }
        }

        while (pos1 >= 0) {
            nums1[right--] = nums1[pos1--];
        }

        while (pos2 >= 0) {
            nums1[right--] = nums2[pos2--];
        }
    }
}
