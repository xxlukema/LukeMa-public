package com.learn.other;


import org.junit.jupiter.api.Assertions;

import lombok.extern.log4j.Log4j2;


/**
 * LC - 153 - Find Minimum in Rotate Sorted Array
 * 
 * Medium
 * 
 * Suppose an array of length n sorted in ascending order is rotated between 1 and n times. For example, the array nums = [0,1,2,4,5,6,7] might become:
 *     [4,5,6,7,0,1,2] if it was rotated 4 times.
 *     [0,1,2,4,5,6,7] if it was rotated 7 times.
 * Notice that rotating an array [a[0], a[1], a[2], ..., a[n-1]] 1 time results in the array [a[n-1], a[0], a[1], a[2], ..., a[n-2]].
 * Given the sorted rotated array nums of unique elements, return the minimum element of this array.
 * You must write an algorithm that runs in O(log n) time.
 * 
 * Example 1:
 * Input: nums = [3,4,5,1,2]
 * Output: 1
 * Explanation: The original array was [1,2,3,4,5] rotated 3 times.
 * 
 * Example 2:
 * Input: nums = [4,5,6,7,0,1,2]
 * Output: 0
 * Explanation: The original array was [0,1,2,4,5,6,7] and it was rotated 4 times.
 * 
 * Example 3:
 * Input: nums = [11,13,15,17]
 * Output: 11
 * Explanation: The original array was [11,13,15,17] and it was rotated 4 times. 
 * 
 * Constraints:
 *     n == nums.length
 *     1 <= n <= 5000
 *     -5000 <= nums[i] <= 5000
 *     All the integers of nums are unique.
 *     nums is sorted and rotated between 1 and n times.
 */
@Log4j2
public class FindMinInRotateSortedArray {

    public static void main(String[] args) {

        /**
         * Output: 1
         */
        // final int[] nums = { 3, 4, 5, 1, 2 };

        final int[] nums = { 4, 5, 6, 7, 0, 1, 2 };

        FindMinInRotateSortedArray findMinInRotateSortedArray = new FindMinInRotateSortedArray();

        int findMinRecursive = findMinInRotateSortedArray.findMinRecursive(nums);
        log.debug("Find Min In Rotate Sorted Array: {}", () -> findMinRecursive);
        log.debug("Find Min In Rotate Sorted Array {} OK", () -> "findMinRecursive");

        int findMinIterative = findMinInRotateSortedArray.findMinIterative(nums);
        Assertions.assertEquals(findMinRecursive, findMinIterative);
        log.debug("Find Min In Rotate Sorted Array {} OK", () -> "findMinIterative");

    }

    /**
     * Luke - Iterative
     * 
     * Runtime: 1 ms, faster than 45.63% of Java online submissions for Find Minimum in Rotated Sorted Array.
     * Memory Usage: 41.9 MB, less than 92.89% of Java online submissions for Find Minimum in Rotated Sorted Array.
     * 
     * Time: O(log(N))
     * Space: O(1)
     */
    public int findMinIterative(final int[] nums) {
        int left = 0;
        int right = nums.length - 1;

        while (true) {
            if (left == right) {
                return nums[left];
            } else if (left + 1 == right) {
                return Math.min(nums[left], nums[right]);
            } else if (left + 2 == right) {
                return Math.min(nums[left], Math.min(nums[left + 1], nums[right]));
            } else {
                /**
                 * Use of "int mid = left + (right - left) / 2;" instead of " int mid = (left + right) / 2;" can prevent two integers add overflow.
                 */
                int mid = left + (right - left) / 2;
                if (nums[left] < nums[mid]) {
                    if (nums[mid + 1] <= nums[right]) {
                        return Math.min(nums[left], nums[mid + 1]);
                    } else {
                        left = mid + 1;
                        continue;
                    }
                } else {
                    right = mid;
                    continue;
                }
            }
        }
    }

    /**
     * Luke - Binary Search
     * 
     * Use of "int mid = left + (right - left) / 2;" instead of " int mid = (left + right) / 2;" can prevent two integers add overflow.
     * 
     * Runtime: 1 ms, faster than 45.63% of Java online submissions for Find Minimum in Rotated Sorted Array.
     * Memory Usage: 42.2 MB, less than 80.61% of Java online submissions for Find Minimum in Rotated Sorted Array.
     * 
     * Time: O(log(N))
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
            if (nums[left] < nums[mid]) {
                return Math.min(nums[left], findMinRecursive(nums, mid + 1, right));
            } else {
                return findMinRecursive(nums, left, mid);
            }
        }
    }
}
