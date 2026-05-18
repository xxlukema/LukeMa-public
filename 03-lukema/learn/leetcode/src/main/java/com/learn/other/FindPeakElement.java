package com.learn.other;


import org.junit.jupiter.api.Assertions;

import lombok.extern.log4j.Log4j2;


/**
 * LC - 162 - Find Peak Element
 *
 * Medium
 *
 * NOte: It asks for "Peak", no "Maximum".
 *
 * A peak element is an element that is strictly greater than its neighbors.
 * Given a 0-indexed integer array nums, find a peak element, and return its index. If the array contains multiple peaks, return the index to any of the peaks.
 * You may imagine that nums[-1] = nums[n] = -∞. In other words, an element is always considered to be strictly greater than a neighbor that is outside the array.
 *
 * You must write an algorithm that runs in O(log n) time.
 *
 * Example 1:
 * Input: nums = [1,2,3,1]
 * Output: 2
 * Explanation: 3 is a peak element and your function should return the index number 2.
 *
 * Example 2:
 * Input: nums = [1,2,1,3,5,6,4]
 * Output: 5
 * Explanation: Your function can return either index number 1 where the peak element is 2, or index number 5 where the peak element is 6.
 *
 * Constraints:
 *     1 <= nums.length <= 1000
 *     -2 ^ 31 <= nums[i] <= 2 ^ 31 - 1
 *     nums[i] != nums[i + 1] for all valid i.
 */
@Log4j2
public class FindPeakElement {

    public static void main(String[] args) {

        final int[] nums = { 1, 2, 1, 3, 5, 6, 4 };

        FindPeakElement findPeakElement = new FindPeakElement();

        var findPeakElementRecursiveBSearch = findPeakElement.findPeakElementRecursiveBSearch(nums);
        log.debug("Find peak element: {}", () -> findPeakElementRecursiveBSearch);
        log.debug("Find peak element {} OK", () -> "findPeakElementRecursiveBSearch");

        var findPeakElementIterativeBSearch = findPeakElement.findPeakElementIterativeBSearch(nums);
        Assertions.assertEquals(findPeakElementRecursiveBSearch, findPeakElementIterativeBSearch);
        log.debug("Find peak element {} OK", () -> "findPeakElementIterativeBSearch");

        var findPeakElementLcIterative = findPeakElement.findPeakElementLcIterative(nums);
        Assertions.assertEquals(findPeakElementRecursiveBSearch, findPeakElementLcIterative);
        log.debug("Find peak element {} OK", () -> "findPeakElementLcIterative");

        var findPeakElementLcRecursive = findPeakElement.findPeakElementLcRecursive(nums);
        Assertions.assertEquals(findPeakElementRecursiveBSearch, findPeakElementLcRecursive);
        log.debug("Find peak element {} OK", () -> "findPeakElementLcRecursive");

    }

    /**
     * Luke - BSearch Recursive
     *
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Find Peak Element.
     * Memory Usage: 43 MB, less than 37.30% of Java online submissions for Find Peak Element.
     *
     * Time: O(log(N) base 2)
     * Space: O(log(N) base 2)
     */
    public int findPeakElementRecursiveBSearch(final int[] nums) {
        if (nums == null) {
            return -1;
        }

        return findPeakElementRecursiveBSearch(nums, 0, nums.length - 1);
    }

    private int findPeakElementRecursiveBSearch(final int[] nums, final int left, final int right) {

        if (left < 0 || right >= nums.length) {
            return -1;
        }

        if (left == right) {
            // return nums[left];
            return left;
        }

        if (left + 1 == right) {
            // return Math.max(nums[left], nums[right]);
            if (nums[left] > nums[right]) {
                return left;
            } else {
                return right;
            }
        }

        int mid = left + (right - left) / 2;

        if (nums[mid] < nums[mid + 1]) {
            return findPeakElementRecursiveBSearch(nums, mid + 1, right);
        } else {
            return findPeakElementRecursiveBSearch(nums, left, mid);
        }
    }

    /**
     * Luke - Iterative
     *
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Find Peak Element.
     * Memory Usage: 42.9 MB, less than 45.56% of Java online submissions for Find Peak Element.
     *
     * Time: O(log(N) base 2)
     * Space: O(1)
     */
    public int findPeakElementIterativeBSearch(final int[] nums) {
        if (nums == null) {
            return -1;
        }

        int left = 0;
        int right = nums.length - 1;

        while (left <= right) {
            if (left == right) {
                return left;
            } else if (left + 1 == right) {
                if (nums[left] > nums[right]) {
                    return left;
                } else {
                    return right;
                }
            } else {
                int mid = left + (right - left) / 2;
                if (nums[mid] < nums[mid + 1]) {
                    left = mid + 1;
                } else {
                    right = mid;
                }
            }
        }

        return -1;
    }

    /**
     * LC - Iterative
     *
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Find Peak Element.
     * Memory Usage: 43.3 MB, less than 23.55% of Java online submissions for Find Peak Element.
     *
     * Time: O(log(N) base 2)
     * Space: O(1)
     */
    public int findPeakElementLcIterative(int[] nums) {
        int left = 0;
        int right = nums.length - 1;

        while (left < right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] > nums[mid + 1]) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }

        return left;
    }

    /**
     * LC - Recursive
     *
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Find Peak Element.
     * Memory Usage: 41.9 MB, less than 90.13% of Java online submissions for Find Peak Element.
     *
     * Time: O(log(N) base 2)
     * Space: O(log(N) base 2)
     */
    public int findPeakElementLcRecursive(int[] nums) {
        return findPeakElementLcRecursive(nums, 0, nums.length - 1);
    }

    private int findPeakElementLcRecursive(int[] nums, int left, int right) {
        if (left == right) {
            return left;
        } else {
            int mid = left + (right - left) / 2;
            if (nums[mid] < nums[mid + 1]) {
                return findPeakElementLcRecursive(nums, mid + 1, right);
            } else {
                return findPeakElementLcRecursive(nums, left, mid);
            }
        }
    }
}
