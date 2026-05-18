package com.learn.other;


import lombok.extern.log4j.Log4j2;


/**
 * LC 80
 */
@Log4j2
public class RemoveDuplicatesFromSortedArray {

    public static void main(String[] args) {

        // int[] nums = { 0, 0, 1, 1, 2, 3, 3 };
        int[] nums = { 1, 1, 1, 2, 3, 3 };

        RemoveDuplicatesFromSortedArray removeDuplicatesFromSortedArray = new RemoveDuplicatesFromSortedArray();

        log.debug("new array nums: {}", nums);

        var ret = removeDuplicatesFromSortedArray.removeDuplicates(nums);
        log.debug("new array k: {}, nums: {}", () -> ret, () -> nums);

    }

    /**
     * Luke: Brute force
     * 
     * Runtime: 1 ms, faster than 84.55% of Java online submissions for Remove Duplicates from Sorted Array II.
     * Memory Usage: 44.4 MB, less than 74.24% of Java online submissions for Remove Duplicates from Sorted Array II.
     * 
     * Time: O(n)
     * Space: O(1)
     */
    public int removeDuplicates(int[] nums) {

        int left = 0;
        int right = left + 1;
        boolean skipped = false;

        while (right < nums.length) {
            if (nums[left] != nums[right]) {
                left++;
                if (left != right) {
                    nums[left] = nums[right];
                }
                right++;
            } else {
                if (right == nums.length - 1) {
                    left++;
                    if (left != right) {
                        nums[left] = nums[right];
                    }
                    break;
                } else if (nums[right + 1] == nums[right]) {
                    right++;
                    skipped = true;
                    continue;
                } else {
                    left++;
                    if (left != right) {
                        nums[left] = nums[right];
                    }
                    right++;

                    if (skipped) {
                        skipped = false;
                    }
                }
            }
        }

        return left + 1;
    }
}
