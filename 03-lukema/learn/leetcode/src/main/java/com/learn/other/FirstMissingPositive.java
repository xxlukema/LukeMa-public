package com.learn.other;


import static org.junit.jupiter.api.Assertions.assertEquals;

import lombok.extern.log4j.Log4j2;


/**
 * LC - 45 - First Missing Positive
 *
 * Hard
 *
 * Given an unsorted integer array nums, return the smallest missing positive integer.
 *
 * You must implement an algorithm that runs in O(n) time and uses constant extra space.
 *
 * Example 1:
 * Input: nums = [1,2,0]
 * Output: 3
 * Explanation: The numbers in the range [1,2] are all in the array.
 *
 * Example 2:
 * Input: nums = [3,4,-1,1]
 * Output: 2
 * Explanation: 1 is in the array but 2 is missing.
 *
 * Example 3:
 * Input: nums = [7,8,9,11,12]
 * Output: 1
 * Explanation: The smallest positive integer 1 is missing.
 *
 * Constraints:
 *     1 <= nums.length <= 105
 *     -2 ^ 31 <= nums[i] <= 2 ^ 31 - 1
 */
@Log4j2
public class FirstMissingPositive {

    public static void main(String[] args) {
        FirstMissingPositive firstMissingPositive = new FirstMissingPositive();

        int[] nums = { 4, 3, 3, 9, 3, 0, 9, 2, 8, 3, 1 };
        // int[] nums = { 1, 2, 3, 3 };

        int[] copy = new int[nums.length];
        for (int i = 0; i < nums.length; i++) {
            copy[i] = nums[i];
        }

        int retLuke = firstMissingPositive.firstMissingPositiveLuke(copy);
        log.info("First Missing Positive Luke: {}", () -> retLuke);

        for (int i = 0; i < nums.length; i++) {
            copy[i] = nums[i];
        }

        int retLC = firstMissingPositive.firstMissingPositiveLC(copy);
        log.info("First Missing Positive LC: {}", () -> retLC);

        assertEquals(retLC, retLuke);

    }

    public int firstMissingPositiveLC(int[] nums) {
        final int N = nums.length;

        // Base case.
        boolean has1 = false;
        for (int i = 0; i < N; i++) {
            if (nums[i] == 1) {
                has1 = true;
                break;
            }
        }

        if (!has1) {
            return 1;
        }

        // Replace negative numbers, zeros,
        // and numbers larger than n by 1s.
        // After this convertion nums will contain
        // only positive numbers.
        for (int i = 0; i < N; i++) {
            if ((nums[i] < 1) || (nums[i] > N)) {
                nums[i] = 1;
            }
        }

        // Use index as a hash key and number sign as a presence detector.
        // For example, if nums[1] is negative that means that number `1`
        // is present in the array.
        // If nums[2] is positive - number 2 is missing.
        for (int i = 0; i < N; i++) {
            int a = Math.abs(nums[i]);
            // If you meet number a in the array - change the sign of a-th element.
            // Be careful with duplicates : do it only once.
            if (a == N)
                nums[0] = -Math.abs(nums[0]);
            else
                nums[a] = -Math.abs(nums[a]);
        }

        // Now the index of the first positive number
        // is equal to first missing positive.
        for (int i = 1; i < N; i++) {
            if (nums[i] > 0)
                return i;
        }

        if (nums[0] > 0)
            return N;

        return N + 1;
    }

    public int firstMissingPositiveLuke(int[] nums) {

        /**
         * Contains 1?
         */
        boolean has1 = false;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 1) {
                has1 = true;
                break;
            }
        }

        if (!has1) {
            return 1;
        }

        final int N = nums.length;

        log.debug("nums: {}", nums);

        /**
         * Set negative numbers, zeros, and numbers larger than N value to 1.
         */
        for (int i = 0; i < N; i++) {
            if (nums[i] < 1 || nums[i] > N) {
                nums[i] = 1;
            }
        }

        log.debug("after adjust to 1, nums: {}", nums);

        /**
         * Assign hash array negative values.
         */
        for (int i = 0; i < N; i++) {
            int pos = Math.abs(nums[i]);

            if (pos == N) {
                nums[0] = -Math.abs(nums[0]);
            } else {
                nums[pos] = -Math.abs(nums[pos]);
            }
            /**
             * Or:
             */
            // nums[pos % N] = -Math.abs(nums[pos % N]);
        }

        log.debug("after assign negative hash, nums: {}", nums);

        for (int i = 1; i < N; i++) {
            if (nums[i] > 0) {
                return i;
            }
        }

        if (nums[0] > 0) {
            return N;
        }

        return N + 1;
    }
}
