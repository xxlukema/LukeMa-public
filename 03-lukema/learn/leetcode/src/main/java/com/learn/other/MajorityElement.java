package com.learn.other;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Assertions;

import lombok.extern.log4j.Log4j2;


/**
 * LC - 169 - Majority Element
 *
 * Easy
 *
 * Given an array nums of size n, return the majority element.
 * The majority element is the element that appears more than ⌊n / 2⌋ times. You may assume that the majority element always exists in the array.
 *
 * Example 1:
 * Input: nums = [3,2,3]
 * Output: 3
 *
 * Example 2:
 * Input: nums = [2,2,1,1,1,2,2]
 * Output: 2
 *
 * Constraints:
 *     n == nums.length
 *     1 <= n <= 5 * 104
 *     -109 <= nums[i] <= 109
 *
 * Follow-up: Could you solve the problem in linear time and in O(1) space?
 */
@Log4j2
public class MajorityElement {

    public static void main(String[] args) {

        /**
         * Output: 2
         */
        // final int[] nums = { 2, 2, 1, 1, 1, 2, 2 };

        // final int[] nums = { -2100, 2000, 1000 };
        // final int[] nums = { 2, 2 };
        // final int[] nums = { 320, 300, 312, 311, 400 };

        // final int[] nums = { 3, 3, 4 };

        final int[] nums = { 3, 2, 3 };

        MajorityElement majorityElement = new MajorityElement();

        var majorityElementLukeRadixSort = majorityElement.majorityElementLukeRadixSort(nums);
        log.debug("Majority element: {}", () -> majorityElementLukeRadixSort);
        log.debug("Majority element {} OK", () -> "majorityElementLukeRadixSort");

        var majorityElementBoyerMooreLuke = majorityElement.majorityElementBoyerMooreLuke(nums);
        Assertions.assertEquals(majorityElementLukeRadixSort, majorityElementBoyerMooreLuke);
        log.debug("Majority element {} OK", () -> "majorityElementBoyerMooreLuke");

        var majorityElementBoyerMooreLc = majorityElement.majorityElementBoyerMooreLc(nums);
        Assertions.assertEquals(majorityElementLukeRadixSort, majorityElementBoyerMooreLc);
        log.debug("Majority element {} OK", () -> "majorityElementBoyerMooreLc");

    }

    /**
     * LC - Boyer-Moore Voting Algorithm
     *
     *
     * Time: O(N)
     * Space: O(1)
     */
    public Integer majorityElementBoyerMooreLc(int[] nums) {
        int count = 0;
        Integer candidate = null;

        for (int num : nums) {
            if (count == 0) {
                candidate = num;
            }
            count += (num == candidate) ? 1 : -1;
        }

        return candidate;
    }

    /**
     * Luke - Boyer-Moore Voting Algorithm
     *
     * Runtime: 2 ms, faster than 89.73% of Java online submissions for Majority Element.
     * Memory Usage: 56.4 MB, less than 35.21% of Java online submissions for Majority Element.
     *
     * Time: O(N)
     * Space: O(1)
     */
    public int majorityElementBoyerMooreLuke(int[] nums) {
        int count = 1;
        int candidate = nums[0];

        for (int i = 1; i < nums.length; i++) {
            if (count == 0) {
                candidate = nums[i];
            }

            if (nums[i] == candidate) {
                count++;
            } else {
                count--;
            }
        }

        return candidate;
    }

    /**
     * Luke - Radix Sort
     *
     * Runtime: 27 ms, faster than 9.15% of Java online submissions for Majority Element.
     * Memory Usage: 54.6 MB, less than 84.23% of Java online submissions for Majority Element.
     *
     *
     * Time: O(N)
     * Space: O(10)
     */
    public int majorityElementLukeRadixSort(final int[] nums) {
        if (nums == null || nums.length == 0) {
            return -1;
        }

        if (nums.length == 1) {
            return nums[0];
        }

        final int N = nums.length;

        /**
         * 1. Find min
         */
        int min = nums[0];
        int max = nums[0];
        for (int i = 1; i < N; i++) {
            min = Math.min(min, nums[i]);
            max = Math.max(max, nums[i]);
        }

        /**
         * 2. Make all elements positive numbers for Radix sort
         */
        if (min < 0) {
            for (int i = 0; i < N; i++) {
                nums[i] -= min;
            }

            max -= min;
        }

        /**
         * 3. Radix Sort
         */
        final Map<Integer, List<Integer>> map = new HashMap<>();

        /**
         * !!! Importan: divisor must ALWAYS starts with 1. Not improvable!!!
         */
        int divisor = 1;
        int quotient = max / divisor;

        log.debug("quotient: {}, divisor: {}", quotient, divisor);

        while (quotient != 0) {
            /**
             * 1. Put to buckets
             */
            for (int i = 0; i < N; i++) {
                int rem = nums[i] / divisor % 10;

                if (!map.containsKey(rem)) {
                    map.put(rem, new ArrayList<>());
                }

                map.get(rem).add(nums[i]);
            }

            log.debug("--- map: {}", () -> map);

            /**
             * 2. Put back to array
             */
            int pos = 0;
            for (int i = 0; i < 10; i++) {
                if (map.containsKey(i)) {
                    for (int val : map.get(i)) {
                        nums[pos++] = val;
                    }

                    map.get(i).clear();
                }
            }

            quotient = quotient / divisor;
            divisor *= 10;

            log.debug("=== nums: {}", () -> nums);
        }

        /**
         * 4. Recover negative values if min is negative
         */
        if (min < 0) {
            for (int i = 0; i < N; i++) {
                nums[i] += min;
            }
        }

        log.debug("==2222= nums: {}", () -> nums);

        int mid = Math.max((N - 1) / 2, N / 2);

        log.debug("mid: {}", mid);

        return nums[mid];
    }
}
