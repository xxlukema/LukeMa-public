package com.learn.other;


import org.junit.jupiter.api.Assertions;

import lombok.extern.log4j.Log4j2;


/**
 * LC - 154 - Find Minimum in Rotate Sorted Array II
 * 
 * Hard
 * 
 * Suppose an array of length n sorted in ascending order is rotated between 1 and n times. For example, the array nums = [0,1,4,4,5,6,7] might become:
 *     [4,5,6,7,0,1,4] if it was rotated 4 times.
 *     [0,1,4,4,5,6,7] if it was rotated 7 times.
 * Notice that rotating an array [a[0], a[1], a[2], ..., a[n-1]] 1 time results in the array [a[n-1], a[0], a[1], a[2], ..., a[n-2]].
 * Given the sorted rotated array nums that may contain duplicates, return the minimum element of this array.
 * You must decrease the overall operation steps as much as possible.
 * 
 * Example 1:
 * Input: nums = [1,3,5]
 * Output: 1
 * 
 * Example 2:
 * Input: nums = [2,2,2,0,1]
 * Output: 0
 * 
 * Constraints:
 *     n == nums.length
 *     1 <= n <= 5000
 *     -5000 <= nums[i] <= 5000
 *     nums is sorted and rotated between 1 and n times.
 * 
 * Follow up: This problem is similar to Find Minimum in Rotated Sorted Array, but nums may contain duplicates. Would this affect the runtime complexity? How and why?
 */
@Log4j2
public class FindMinInRotateSortedArrayII {

    public static void main(String[] args) {

        /**
         * Output: 1
         */
        // final int[] nums = { 3, 4, 5, 1, 2 };

        // final int[] nums = { 4, 5, 6, 7, 0, 1, 2 };

        // final int[] nums = { 2, 2, 2, 0, 2 };

        // final int[] nums = { 0, 0, 1, 3 };

        final int[] nums = { 2, 2, 2, 0, 1 };

        FindMinInRotateSortedArrayII findMinInRotateSortedArrayII = new FindMinInRotateSortedArrayII();

        int findMinRecursive = findMinInRotateSortedArrayII.findMinRecursive(nums);
        log.debug("Find Min In Rotate Sorted Array: {}", () -> findMinRecursive);
        log.debug("Find Min In Rotate Sorted Array {} OK", () -> "findMinRecursive");

        int findMinLcIterative = findMinInRotateSortedArrayII.findMinLcIterative(nums);
        Assertions.assertEquals(findMinRecursive, findMinLcIterative);
        log.debug("Find Min In Rotate Sorted Array {} OK", () -> "findMinLcIterative");

        int findMinIterative = findMinInRotateSortedArrayII.findMinLukeIterative(nums);
        Assertions.assertEquals(findMinRecursive, findMinIterative);
        log.debug("Find Min In Rotate Sorted Array {} OK", () -> "findMinIterative");

    }

    /**
     * LC - Iterative
     * 
     * Time: Average O(log(N)). Worst O(N)
     */
    public int findMinLcIterative(int[] nums) {
        int low = 0;
        int high = nums.length - 1;

        while (low < high) {
            /**
             * Use of "int mid = left + (right - left) / 2;" instead of " int mid = (left + right) / 2;" can prevent two integers add overflow.
             */
            int pivot = low + (high - low) / 2;
            if (nums[pivot] < nums[high]) {
                high = pivot;
            } else if (nums[pivot] > nums[high]) {
                low = pivot + 1;
            } else {
                high -= 1;
            }
        }
        return nums[low];
    }

    /**
     * Luke - Iterative
     * 
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Find Minimum in Rotated Sorted Array II.
     * Memory Usage: 42 MB, less than 96.87% of Java online submissions for Find Minimum in Rotated Sorted Array II.
     * 
     * Time: Average O(log(N)), worst O(N)
     * Space: O(1)
     */
    public int findMinLukeIterative(final int[] nums) {
        int left = 1;
        int right = nums.length - 1;

        int min = nums[0];

        while (left <= right) {
            /**
             * Use of "int mid = left + (right - left) / 2;" instead of " int mid = (left + right) / 2;" can prevent two integers add overflow.
             */
            int mid = left + (right - left) / 2;
            if (nums[left] < nums[mid]) {
                min = Math.min(min, nums[left]);
                left = mid + 1;
            } else if (nums[left] > nums[mid]) {
                right = mid;
            } else {
                min = Math.min(min, nums[right]);
                right--;
            }
        }

        return min;
    }

    /**
     * Luke - Binary Search
     * 
     * Runtime: 1 ms, faster than 82.35% of Java online submissions for Find Minimum in Rotated Sorted Array II.
     * Memory Usage: 43.9 MB, less than 28.33% of Java online submissions for Find Minimum in Rotated Sorted Array II.
     * 
     * Time: average O(log(N)). worst: O(N)
     * Space: O(log(N)): Recursion statck depth.
     */
    public int findMinRecursive(final int[] nums) {
        return findMinRecursive(nums, 0, nums.length - 1);
    }

    public int findMinRecursive(final int[] nums, final int left, final int right) {
        if (left == right) {
            return nums[left];
        } else if (left + 1 == right) {
            return Math.min(nums[left], nums[right]);
        } else {
            /**
             * Use of "int mid = left + (right - left) / 2;" instead of " int mid = (left + right) / 2;" can prevent two integers add overflow.
             */
            int mid = left + (right - left) / 2;
            if (nums[left] == nums[mid]) {
                if (nums[mid + 1] == nums[right]) {
                    return Math.min(findMinRecursive(nums, left, mid), findMinRecursive(nums, mid + 1, right));
                } else {
                    return Math.min(nums[left], findMinRecursive(nums, mid + 1, right));
                }
            } else if (nums[left] < nums[mid]) {
                return Math.min(nums[left], findMinRecursive(nums, mid + 1, right));
            } else {
                return findMinRecursive(nums, left, mid);
            }
        }
    }
}
