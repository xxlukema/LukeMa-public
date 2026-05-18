package com.learn.lc75;


import org.junit.jupiter.api.Assertions;

import lombok.extern.log4j.Log4j2;


/**
 * LC - 238. Product of Array Except Self
 *
 * Medium
 *
 * Given an integer array nums, return an array answer such that answer[i] is equal to the product of all the elements of nums except nums[i].

The product of any prefix or suffix of nums is guaranteed to fit in a 32-bit integer.

You must write an algorithm that runs in O(n) time and without using the division operation.


Example 1:

Input: nums = [1,2,3,4]
Output: [24,12,8,6]

Example 2:

Input: nums = [-1,1,0,-3,3]
Output: [0,0,9,0,0]

Constraints:

    2 <= nums.length <= 105
    -30 <= nums[i] <= 30
    The product of any prefix or suffix of nums is guaranteed to fit in a 32-bit integer.

Follow up: Can you solve the problem in O(1) extra space complexity? (The output array does not count as extra space for space complexity analysis.)

 */

@Log4j2
public class ProductOfArrayExceptForSelf {

    public static void main(String[] args) {
        ProductOfArrayExceptForSelf productOfArrayExceptForSelf = new ProductOfArrayExceptForSelf();

        int[] nums = { 1, 2, 3, 4 };

        var ret = productOfArrayExceptForSelf.productExceptSelf(nums);

        log.debug("Product of Array Except Self: {}", ret);
        Assertions.assertArrayEquals(new int[] { 24, 12, 8, 6 }, ret);
        log.debug("Product of Array Except Self {} OK", () -> "productExceptSelf");

        nums = new int[] { -1, 1, 0, -3, 3 };

        ret = productOfArrayExceptForSelf.productExceptSelf(nums);

        log.debug("Product of Array Except Self: {}", ret);
        Assertions.assertArrayEquals(new int[] { 0, 0, 9, 0, 0 }, ret);
        log.debug("Product of Array Except Self {} OK", () -> "productExceptSelf");


        ret = productOfArrayExceptForSelf.productExceptSelfLcSpaceSaver(nums);

        log.debug("Product of Array Except Self: {}", ret);
        Assertions.assertArrayEquals(new int[] { 0, 0, 9, 0, 0 }, ret);
        log.debug("Product of Array Except Self {} OK", () -> "productExceptSelfLcSpaceSaver");

    }

    /**
     * LC - pre[] and suffix[]
     *
     * Time: O(n)
     * Space: O(n)
     *
     * Runtime: 2 ms Beats 52.6%
     * Memory: 51.1 MB Beats 90.17%
     */
    public int[] productExceptSelf(int[] nums) {
        int[] pre = new int[nums.length];
        int[] suffix = new int[nums.length];

        pre[0] = 1;

        for (int i = 1; i < nums.length; i++) {
            pre[i] = nums[i - 1] * pre[i - 1];
        }

        suffix[nums.length - 1] = 1;

        for (int i = nums.length - 2; i >= 0; i--) {
            suffix[i] = nums[i + 1] * suffix[i + 1];
        }

        for (int i = 0; i < nums.length; i++) {
            pre[i] *= suffix[i];
        }

        return pre;
    }

    /**
     * LC - result[] space saver
     *
     * Time: O(n)
     * Space: O(1)
     *
     * Runtime: 1 ms Beats 100%
     * Memory: 51.9 MB Beats 78.27%
     */
    public int[] productExceptSelfLcSpaceSaver(int[] nums) {
        int[] result = new int[nums.length];

        result[0] = 1;

        for (int i = 1; i < nums.length; i++) {
            result[i] = nums[i - 1] * result[i - 1];
        }

        int curr = 1;

        for (int i = nums.length - 2; i >= 0; i--) {
            curr *= nums[i + 1];
            result[i] *= curr;
        }

        return result;
    }
}
