package com.learn.other;


import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;

import lombok.extern.log4j.Log4j2;


/**
 * LC - 229 - Majority Element II
 *
 * Medium
 *
 * Given an integer array of size n, find all elements that appear more than ⌊ n/3 ⌋ times.
 *
 * Example 1:
 * Input: nums = [3,2,3]
 * Output: [3]
 *
 * Example 2:
 * Input: nums = [1]
 * Output: [1]
 *
 * Example 3:
 * Input: nums = [1,2]
 * Output: [1,2]
 *
 * Constraints:
 *     1 <= nums.length <= 5 * 104
 *     -109 <= nums[i] <= 109
 *
 * Follow up: Could you solve the problem in linear time and in O(1) space?
 */
@Log4j2
public class MajorityElementII {

    public static void main(String[] args) {

        /**
         * Expect: [3]
         */
        // final int[] nums = { 3, 2, 3 };

        /**
         * Expect: []
         */
        // final int[] nums = { 1, 2, 3 };

        /**
         * Expect: [2]
         */
        // final int[] nums = { 2, 2, 1, 3 };

        /**
         * Expect: [0]
         */
        final int[] nums = { 0, 3, 4, 0 };

        MajorityElementII majorityElementII = new MajorityElementII();

        var majorityElementLuke = majorityElementII.majorityElementLuke(nums);
        log.debug("Majority Element II: {}", () -> majorityElementLuke);
        log.debug("Majority Element II {} OK", () -> "majorityElementLuke");

        var majorityElementLc = majorityElementII.majorityElementLc(nums);
        Assertions.assertEquals(majorityElementLuke.size(), majorityElementLc.size());
        log.debug("Majority Element II {} OK", () -> "majorityElementLc");

    }

    /**
     * There can be at most one majority element which is more than ⌊n/2⌋ times.
     * There can be at most two majority elements which are more than ⌊n/3⌋ times.
     * There can be at most three majority elements which are more than ⌊n/4⌋ times.
     */

    /**
     * LC -
     */
    public List<Integer> majorityElementLuke(final int[] nums) {
        List<Integer> list = new ArrayList<>();
        if (nums == null || nums.length < 1) {
            return list;
        }

        final int N = nums.length;

        /**
         * Find the first candidate
         */
        int candiate = nums[0];
        int vote = 1;

        for (int i = 1; i < N; i++) {
            if (vote == 0) {
                candiate = nums[i];
                vote++;
            }

            if (nums[i] == candiate) {
                vote++;
            } else {
                vote--;
            }
        }

        /**
         * Verify
         */
        int count = 0;
        for (int i : nums) {
            if (i == candiate) {
                count++;
            }
        }

        if (count > N / 3) {
            list.add(candiate);
        }

        /**
         * Find the second candidate
         */
        int tmp = candiate;

        int pos = 0;
        while (pos < N && nums[pos] == candiate) {
            pos++;
        }

        if (pos == N) {
            return list;
        }

        vote = 0;
        candiate = nums[pos];

        for (int i = pos + 1; i < N; i++) {
            if (nums[i] == tmp) {
                continue;
            }

            if (vote == 0) {
                candiate = nums[i];
                vote++;
            }

            if (nums[i] == candiate) {
                vote++;
            } else {
                vote--;
            }
        }

        /**
         * Verify
         */
        count = 0;
        for (int i : nums) {
            if (i == candiate) {
                count++;
            }
        }
        if (count > N / 3) {
            list.add(candiate);
        }

        return list;
    }

    public List<Integer> majorityElementLc(int[] nums) {

        // 1st pass
        int count1 = 0;
        int count2 = 0;

        Integer candidate1 = null;
        Integer candidate2 = null;

        for (int n : nums) {
            if (candidate1 != null && candidate1 == n) {
                count1++;
            } else if (candidate2 != null && candidate2 == n) {
                count2++;
            } else if (count1 == 0) {
                candidate1 = n;
                count1++;
            } else if (count2 == 0) {
                candidate2 = n;
                count2++;
            } else {
                count1--;
                count2--;
            }
        }

        // 2nd pass
        List<Integer> result = new ArrayList<>();

        count1 = 0;
        count2 = 0;

        for (int n : nums) {
            if (candidate1 != null && n == candidate1)
                count1++;
            if (candidate2 != null && n == candidate2)
                count2++;
        }

        int n = nums.length;
        if (count1 > n / 3)
            result.add(candidate1);
        if (count2 > n / 3)
            result.add(candidate2);

        return result;
    }
}
