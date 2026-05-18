package com.learn.other;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.Assertions;

import lombok.extern.log4j.Log4j2;


/**
 * LC - 162 - Max Gap
 *
 * Hard
 *
 * Given an integer array nums, return the maximum difference between two successive elements in its sorted form.
 * If the array contains less than two elements, return 0.
 *
 * You must write an algorithm that runs in linear time and uses linear extra space.
 *
 * Example 1:
 * Input: nums = [3,6,9,1]
 * Output: 3
 * Explanation: The sorted form of the array is [1,3,6,9], either (3,6) or (6,9) has the maximum difference 3.
 *
 * Example 2:
 * Input: nums = [10]
 * Output: 0
 * Explanation: The array contains less than 2 elements, therefore return 0.
 *
 * Constraints:
 *     1 <= nums.length <= 105
 *     0 <= nums[i] <= 109
 */
@Log4j2
public class MaxGap {

    public static void main(String[] args) {

        /**
         * Output: 3
         */
        // final int[] nums = { 3, 6, 9, 1 };

        /**
         * Output: 2901
         */
        final int[] nums = { 15252, 16764, 27963, 7817, 26155, 20757, 3478, 22602, 20404, 6739, 16790, 10588, 16521, 6644, 20880, 15632, 27078,
                25463, 20124, 15728, 30042, 16604, 17223, 4388, 23646, 32683, 23688, 12439, 30630, 3895, 7926, 22101, 32406, 21540, 31799, 3768,
                26679, 21799, 23740 };

        /**
         * Output: 0
         */
        // final int[] nums = { 1 };

        /**
         * Output: 9_999_999
         */
        // final int[] nums = { 1, 10_000_000 };

        /**
         * Output: 97
         */
        // final int[] nums = { 100, 3, 2, 1 };

        MaxGap maxGap = new MaxGap();

        var maximumGapHashSetTimeout = maxGap.maximumGapHashSetTimeout(nums);
        log.debug("Max gap: {}", () -> maximumGapHashSetTimeout);
        log.debug("Max gap {} OK", () -> "maximumGapHashSetTimeout");

        var maximumGapLukeRadixSort = maxGap.maximumGapLukeRadixSort(nums);
        Assertions.assertEquals(maximumGapHashSetTimeout, maximumGapLukeRadixSort);
        log.debug("Max gap {} OK", () -> "maximumGapLukeRadixSort");

    }

    /**
     * Luke - Radix Sort (Positive numbers)
     *
     * Runtime: 226 ms, faster than 8.48% of Java online submissions for Maximum Gap.
     * Memory Usage: 125.5 MB, less than 5.04% of Java online submissions for Maximum Gap.
     *
     * Time: O(N). Same as Radix Sort time complexity.
     * Space: O(N). Same as Radix Sort space complexity.
     */
    public int maximumGapLukeRadixSort(int[] nums) {

        final int N = nums.length;

        if (nums == null || N == 1) {
            return 0;
        }

        /**
         * Step 1. Radix Sort
         */
        final Map<Integer, List<Integer>> map = new HashMap<>();

        int min = nums[0];
        int max = nums[0];

        for (int i = 0; i < N; i++) {
            max = Math.max(max, nums[i]);
            min = Math.min(min, nums[i]);
        }

        // log.debug("min: {}, max: {}, nums: {}", min, max, nums);

        /**
         * Trick: quotient MUST starts with max
         */
        int quotient = max;
        /**
         * Trick: divisor MUST starts with 1.
         */
        int divisor = 1;

        /**
         * WRONG! - divisor must starts with 1. It cannot be improved.
         */
        /*
        while ((min = (min / 10)) > 0) {
            divisor *= 10;
        }
        divisor /= 10;

        if (divisor == 0) {
            divisor = 1;
        }
        */

        while (quotient > 0) {

            /**
             * Put to buckets (map)
             */
            for (int i = 0; i < N; i++) {
                /**
                 * Trick: int remainder = tmpNums[i] / divisor % 10;
                 */
                int remainder = nums[i] / divisor % 10;
                if (!map.containsKey(remainder)) {
                    map.put(remainder, new ArrayList<>());
                }
                map.get(remainder).add(nums[i]);
            }

            // log.debug(" ---- div: {}, map: {}", div, map);

            /**
             * Put back to array
             */
            /**
             * Trick: User AtomicInteger to increment pos inside lambda
             */
            AtomicInteger pos = new AtomicInteger();
            for (int i = 0; i < 10; i++) {
                if (map.containsKey(i)) {
                    map.get(i).forEach(e -> {
                        nums[pos.getAndIncrement()] = e;
                    });
                }
            }

            /**
             * Trick
             */
            map.clear();

            /**
             * Trick
             */
            quotient = quotient / 10;
            divisor *= 10;
        }

        /**
         * Step 2. Calculate diff
         */
        max = 0;

        // log.debug("nums: {}", nums);

        for (int i = 0; i < N - 1; i++) {
            max = Math.max(max, nums[i + 1] - nums[i]);
        }

        return max;
    }

    /**
     * Luke - HashSet
     *
     * Time Limit Exceeded
     *
     *
     */
    public int maximumGapHashSetTimeout(int[] nums) {

        if (nums == null || nums.length == 1) {
            return 0;
        }

        Set<Integer> set = new HashSet<>();

        int min = nums[0];
        int max = nums[0];

        /**
         * Time: O(N)
         * Space: O(N)
         */
        for (int i = 0; i < nums.length; i++) {
            min = Math.min(min, nums[i]);
            max = Math.max(max, nums[i]);

            set.add(nums[i]);
        }

        int maxGap = 0;

        int curr = min + 1;

        // log.debug("curr: {}, min: {}, max: {}", curr, min, max);

        while (curr <= max) {
            if (set.contains(curr)) {
                maxGap = Math.max(maxGap, curr - min);
                min = curr;
            }
            curr++;
        }

        return maxGap;
    }
}
