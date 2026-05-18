package com.learn.bit;


import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Assertions;

import lombok.extern.log4j.Log4j2;


/**
 * LC - 136 - Single Number
 * 
 * Easy
 * 
 * Given a non-empty array of integers nums, every element appears twice except for one. Find that single one.
 * You must implement a solution with a linear runtime complexity and use only constant extra space.
 * 
 * Example 1:
 * Input: nums = [2,2,1]
 * Output: 1
 * 
 * Example 2:
 * Input: nums = [4,1,2,1,2]
 * Output: 4
 * 
 * Example 3:
 * Input: nums = [1]
 * Output: 1
 * 
 * Constraints:
 *     1 <= nums.length <= 3 * 104
 *     -3 * 104 <= nums[i] <= 3 * 104
 *     Each element in the array appears twice except for one element which appears only once.
 */
@Log4j2
public class SingleNumber {

    public static void main(String[] args) {

        final int[] nums = { 4, 1, 2, 1, 2 };

        SingleNumber singleNumber = new SingleNumber();

        int singleNumberLukeSpaceOverLimit = singleNumber.singleNumberLukeSpaceOverLimit(nums);
        log.debug("Single Number: {}", () -> singleNumberLukeSpaceOverLimit);
        log.debug("Single Number {} OK", () -> "singleNumberLukeSpaceOverLimit");

        int singleNumberLcXor = singleNumber.singleNumberLcXor(nums);
        Assertions.assertEquals(singleNumberLukeSpaceOverLimit, singleNumberLcXor);
        log.debug("Single Number {} OK", () -> "singleNumberLcXor");

        int singleNumberLukeXor = singleNumber.singleNumberLukeXor(nums);
        Assertions.assertEquals(singleNumberLukeSpaceOverLimit, singleNumberLukeXor);
        log.debug("Single Number {} OK", () -> "singleNumberLukeXor");
    }

    /**
     * Luke - XOR
     * 
     *       int a = 21;
     *    
     *       int odd = 0 ^ a;
     *       int even = 0 ^ a ^ a;
     *    
     *       log.debug(" odd a: {}, odd: {}", a, odd);
     *       Assertions.assertEquals(a, odd, "Odd.");
     *    
     *       log.debug("Odd is OK");
     *    
     *       log.debug("even a: {}, even: {}", a, even);
     *       Assertions.assertEquals(0, even, "Even.");
     *    
     *       log.debug("Even is OK");
     * 
     * Runtime: 1 ms, faster than 100.00% of Java online submissions for Single Number.
     * Memory Usage: 50.8 MB, less than 39.47% of Java online submissions for Single Number.
     * 
     * Time: O(N)
     * Space: O(1)
     */
    private int singleNumberLukeXor(int[] nums) {

        int a = 0;

        for (int i : nums) {
            // log.debug("i: {}, a: {}, a ^ i: {}", i, a, a ^ i);

            a ^= i;
        }

        return a;
    }

    /**
     * Luke - HashSet - Wrong: Space over limit.
     * 
     * Runtime: 9 ms, faster than 37.07% of Java online submissions for Single Number.
     * Memory Usage: 41.9 MB, less than 99.27% of Java online submissions for Single Number.
     * 
     * Time: O(N)
     * Space: O(N / 2)
     */
    public int singleNumberLukeSpaceOverLimit(final int[] nums) {
        // final int N = nums.length;

        Set<Integer> set = new HashSet<>();
        for (int i : nums) {
            if (set.contains(i)) {
                set.remove(i);
            } else {
                set.add(i);
            }
        }

        return set.toArray(new Integer[0])[0];
    }

    /**
     * LC - XOR
     */
    public int singleNumberLcXor(int[] nums) {
        int a = 0;
        for (int i : nums) {
            a ^= i;
        }
        return a;
    }

}
