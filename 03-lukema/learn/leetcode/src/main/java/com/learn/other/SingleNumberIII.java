package com.learn.other;


import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Assertions;

import com.learn.util.ArrayUtils;

import lombok.extern.log4j.Log4j2;


/**
 * LC-260 Single Number III
 *
 * Medium
 *
 * Given an integer array nums, in which exactly two elements appear only once and all the other elements appear exactly twice. Find the
 * two elements that appear only once. You can return the answer in any order.
 *
 * You must write an algorithm that runs in linear runtime complexity and uses only constant extra space.
 *
 * Example 1:
 * Input: nums = [1,2,1,3,2,5]
 * Output: [3,5]
 * Explanation:  [5, 3] is also a valid answer.
 *
 * Example 2:
 * Input: nums = [-1,0]
 * Output: [-1,0]
 *
 * Example 3:
 * Input: nums = [0,1]
 * Output: [1,0]
 *
 * Constraints:
 *     2 <= nums.length <= 3 * 104
 *     -2 ^ 31 <= nums[i] <= 2 ^ 31 - 1
 *     Each integer in nums will appear twice, only two integers will appear once.
 */
@Log4j2
public class SingleNumberIII {

    public static void main(String[] args) {

        /**
         * Expected: [3, 5]
         */
        final int[] nums = { 1, 2, 1, 3, 2, 5 };

        SingleNumberIII singleNumberIII = new SingleNumberIII();

        var singleNumberHashSet = singleNumberIII.singleNumberHashSet(nums);
        log.debug("Single Number III: {}", () -> singleNumberHashSet);
        log.debug("Single Number III {} OK", () -> "singleNumberHashSet");

        var singleNumberLc = singleNumberIII.singleNumberLc(nums);
        Assertions.assertEquals(ArrayUtils.toString(singleNumberHashSet), ArrayUtils.toString(singleNumberLc));
        log.debug("Single Number III {} OK", () -> "singleNumberLc");
    }

    /**
     * Luke - Brute
     *
     * Runtime: 13 ms Beats 22.60%
     * Memory: 46.5 MB Beats 13.16%
     *
     * Time: O(N)
     * Space: O(N)
     */
    public int[] singleNumberHashSet(int[] nums) {
        final Set<Integer> set = new HashSet<>();

        for (int n : nums) {
            if (set.contains(n)) {
                set.remove(n);
            } else {
                set.add(n);
            }
        }

        int[] result = new int[set.size()];

        int idx = 0;
        for (int key : set) {
            result[idx++] = key;
        }

        return result;
    }

    /**
     * LC - Bit Ops
     * Time: O(N)
     * Space: O(1)
     */
    public int[] singleNumberLc(int[] nums) {

        /*
        int n = 3;
        log.debug("------: {}", n = n ^ 2);
        log.debug("------: {}", n = n ^ 2);
        log.debug("------: {}", ~2);
        log.debug("------: {}", ~2 ^ ~2 ^ 1);

        log.debug("------: {}", 5 ^ 3);

        return null;
        */

        // difference between two numbers (x and y) which were seen only once
        int bitmask = 0;
        for (int num : nums) {
            bitmask ^= num;
        }

        // log.debug("------ {}", bitmask);

        // rightmost 1-bit diff between x and y
        int diff = bitmask & (-bitmask);

        int x = 0;
        // bitmask which will contain only x
        for (int num : nums) {
            if ((num & diff) != 0) {
                x ^= num;
            }
        }

        return new int[] { x, bitmask ^ x };
    }

}
