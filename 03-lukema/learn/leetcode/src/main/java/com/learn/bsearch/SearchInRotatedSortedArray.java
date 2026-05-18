package com.learn.bsearch;


import lombok.extern.log4j.Log4j2;


/**
 * LC 81
 */
@Log4j2
public class SearchInRotatedSortedArray {

    public static void main(String[] args) {

        int[] nums = { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1 };
        int target = 2;

        SearchInRotatedSortedArray searchInRotatedSortedArray = new SearchInRotatedSortedArray();

        var ret = searchInRotatedSortedArray.search(nums, target);
        log.debug("Found: {}", () -> ret);
    }

    /**
     * Luke: Binary search
     * 
     * Runtime: 2 ms, faster than 14.16% of Java online submissions for Search in Rotated Sorted Array II.
     * Memory Usage: 43.5 MB, less than 71.10% of Java online submissions for Search in Rotated Sorted Array II.
     * 
     * Time: O(n)
     * Space: O(1)
     */
    public boolean search(int[] nums, int target) {
        return search(nums, target, 0, nums.length - 1);
    }

    private boolean search(int[] nums, int target, int left, int right) {
        if (left > right || left < 0 || right >= nums.length) {
            return false;
        }

        int mid = (left + right) / 2;

        log.debug("left: {}, mid: {}, right: {}", nums[left], nums[mid], nums[right]);

        if (mid == left) {
            return target == nums[left] || target == nums[right];
        } else {
            if (target == nums[mid] || target == nums[mid + 1]) {
                return true;
            }
        }

        // left is not rotated
        if (nums[left] < nums[mid]) {
            if (target > nums[left] && target < nums[mid]) {
                return search(nums, target, left, mid);
            }
        } else if (nums[mid + 1] < nums[right]) { // right is not rotated 
            if (target > nums[mid] && target < nums[right]) {
                return search(nums, target, mid + 1, right);
            }
        }

        return search(nums, target, left, mid) || search(nums, target, mid + 1, right);
    }
}
