package com.learn.bit;


import org.junit.jupiter.api.Assertions;

import lombok.extern.log4j.Log4j2;


/**
 * LC - 137 - Single Number II
 *
 * Medium
 *
 * Given an integer array nums where every element appears three times except for one, which appears exactly once. Find the single element and return it.
 * You must implement a solution with a linear runtime complexity and use only constant extra space.
 *
 * Example 1:
 * Input: nums = [2,2,3,2]
 * Output: 3
 *
 * Example 2:
 * Input: nums = [0,1,0,1,0,1,99]
 * Output: 99
 *
 * Constraints:
 *     1 <= nums.length <= 3 * 104
 *     -2 ^ 31 <= nums[i] <= 2 ^ 31 - 1
 *     Each element in nums appears exactly three times except for one element which appears once.
 */
@Log4j2
public class SingleNumberII {

    public static void main(String[] args) {

        final int[] nums = { 1, 3, 1, 3, 1, 3, 99 };

        SingleNumberII singleNumberII = new SingleNumberII();

        int singleNumberLcBitwise = singleNumberII.singleNumberLcBitwise(nums);
        log.debug("Single Number II: {}", () -> singleNumberLcBitwise);
        log.debug("Single Number II {} OK", () -> "singleNumberLcBitwise");

        int singleNumberLukeBitwise = singleNumberII.singleNumberLukeBitwise(nums);
        Assertions.assertEquals(singleNumberLcBitwise, singleNumberLukeBitwise);
        log.debug("Single Number II {} OK", () -> "singleNumberLukeBitwise");

    }

    /**
     * Luke - Bitwise
     *
     * It is so amazing with the following code:
     *
     *     seenOnce = ~seenTwice & (seenOnce ^ a);          <---- 1st run: a. 2nd run: 0. 3rd run: 0.
     *     seenTwice = ~seenOnce & (seenTwice ^ a);         <---- 1st run: 0. 2nd run: a. 3rd run: 0.
     *
     * ****************** Sample Code Below ****************** *
     *
     *     int a = 21;
     *
     *     int seenOnce = 0;
     *     int seenTwice = 0;
     *
     *     seenOnce = ~seenTwice & (seenOnce ^ a);
     *     seenTwice = ~seenOnce & (seenTwice ^ a);
     *     log.debug("1st a: {}, seenOnce: {}, seenTwice: {}", a, seenOnce, seenTwice);
     *     Assertions.assertEquals(a, seenOnce, "1st time.");
     *     Assertions.assertEquals(0, seenTwice, "1st time.");
     *
     *     seenOnce = ~seenTwice & (seenOnce ^ a);
     *     seenTwice = ~seenOnce & (seenTwice ^ a);
     *     log.debug("2nd a: {}, seenOnce: {}, seenTwice: {}", a, seenOnce, seenTwice);
     *     Assertions.assertEquals(0, seenOnce, "2nd time.");
     *     Assertions.assertEquals(a, seenTwice, "2nd time.");
     *
     *     seenOnce = ~seenTwice & (seenOnce ^ a);
     *     seenTwice = ~seenOnce & (seenTwice ^ a);
     *     log.debug("3rd a: {}, seenOnce: {}, seenTwice: {}", a, seenOnce, seenTwice);
     *     Assertions.assertEquals(0, seenOnce, "3rd time.");
     *     Assertions.assertEquals(0, seenTwice, "3rd time.");
     *
     *     seenOnce = ~seenTwice & (seenOnce ^ a);
     *     seenTwice = ~seenOnce & (seenTwice ^ a);
     *     log.debug("4th a: {}, seenOnce: {}, seenTwice: {}", a, seenOnce, seenTwice);
     *     Assertions.assertEquals(a, seenOnce, "4th time.");
     *     Assertions.assertEquals(0, seenTwice, "4th time.");
     *
     * Runtime: 1 ms, faster than 94.69% of Java online submissions for Single Number II.
     * Memory Usage: 44.2 MB, less than 49.21% of Java online submissions for Single Number II.
     *
     * Time: O(N)
     * Space: O(1)
     */
    public int singleNumberLukeBitwise(final int[] nums) {

        int seenOnce = 0;
        int seenTwice = 0;

        for (int num : nums) {
            seenOnce = ~seenTwice & (seenOnce ^ num);
            seenTwice = ~seenOnce & (seenTwice ^ num);

            // log.debug("num: {}, seenOnce: {}, seenTwice: {}", num, seenOnce, seenTwice);
        }

        return seenOnce;
    }

    /**
     * LC - Bitwise
     *
     * Runtime: 1 ms, faster than 94.69% of Java online submissions for Single Number II.
     * Memory Usage: 44.2 MB, less than 49.21% of Java online submissions for Single Number II.
     *
     * Time: O(N)
     * Space: O(1)
     */
    public int singleNumberLcBitwise(final int[] nums) {
        int seenOnce = 0, seenTwice = 0;

        for (int num : nums) {
            // first appearence:
            // add num to seen_once
            // don't add to seen_twice because of presence in seen_once

            // second appearance:
            // remove num from seen_once
            // add num to seen_twice

            // third appearance:
            // don't add to seen_once because of presence in seen_twice
            // remove num from seen_twice
            seenOnce = ~seenTwice & (seenOnce ^ num);
            seenTwice = ~seenOnce & (seenTwice ^ num);
        }

        return seenOnce;
    }
}
