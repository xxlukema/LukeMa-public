package com.learn.slidingwindow;


import lombok.extern.log4j.Log4j2;


/**
 * LC - 283 - Move Zeros
 *
 * Easy
 *
 * Given an integer array nums, move all 0's to the end of it while maintaining the relative order of the non-zero elements.
 *
 * Note that you must do this in-place without making a copy of the array.
 *
 * Example 1:
 * Input: nums = [0,1,0,3,12]
 * Output: [1,3,12,0,0]
 *
 * Example 2:
 * Input: nums = [0]
 * Output: [0]
 *
 * Constraints:
 *     1 <= nums.length <= 104
 *     -2 ^ 31 <= nums[i] <= 2 ^ 31 - 1
 *
 * Follow up: Could you minimize the total number of operations done?
 */
@Log4j2
public class MoveZeros {

    public static void main(String[] args) {

        final int[] nums = { 0, 1, 0, 3, 12 };

        MoveZeros moveZeros = new MoveZeros();
        // moveZeros.moveZeroesLuke(nums);
        moveZeros.moveZeroesLc(nums);
        log.debug("Move Zeros: {}", nums);
        log.debug("Move Zeros {} OK", "nums");

    }

    /**
     * LC
     *
     * Runtime: 2 ms Beats 88.36%
     * Memory: 55.7 MB Beats 7.66%
     *
     * Time: O(N)
     * Space: O(1)
     */
    public void moveZeroesLc(int[] nums) {
        if (nums == null || nums.length < 2) {
            return;
        }

        final int LEN = nums.length;

        int count = 0;

        for (int left = 0, right = 0; right < LEN; right++) {
            if (nums[right] == 0) {
                count++;
            } else {
                nums[left++] = nums[right];
            }
        }

        for (int i = LEN - count; i < LEN; i++) {
            nums[i] = 0;
        }
    }

    /**
     * Luke - Two Pointers
     *
     * Runtime: 32 ms Beats 11.81%
     * Memory: 43.5 MB Beats 96.15%
     *
     * Time: O(N)
     * Space: O(1)
     */
    public void moveZeroesLuke(int[] nums) {
        if (nums == null || nums.length < 2) {
            return;
        }

        final int LEN = nums.length;

        int left = 0;

        while (left < LEN) {
            while (left < LEN && nums[left] != 0) {
                left++;
            }

            if (left == LEN) {
                break;
            } else {
                int right = left + 1;
                while (right < LEN && nums[right] == 0) {
                    right++;
                }

                if (right == LEN) {
                    break;
                } else {
                    nums[left] = nums[right];
                    nums[right] = 0;
                }
            }
        }
    }
}
