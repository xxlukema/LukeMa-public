package com.learn.backtrack;


import org.junit.jupiter.api.Assertions;

import lombok.extern.log4j.Log4j2;


/**
 * LC - 152 - Max Product Subarray
 * 
 * Medium
 * 
 * Given an integer array nums, find a contiguous non-empty subarray within the array that has the largest product, and return the product.
 * The test cases are generated so that the answer will fit in a 32-bit integer.
 * A subarray is a contiguous subsequence of the array.
 * 
 * Example 1:
 * Input: nums = [2,3,-2,4]
 * Output: 6
 * Explanation: [2,3] has the largest product 6.
 * 
 * Example 2:
 * Input: nums = [-2,0,-1]
 * Output: 0
 * Explanation: The result cannot be 2, because [-2,-1] is not a subarray.
 * 
 * Constraints:
 *     1 <= nums.length <= 2 * 104
 *     -10 <= nums[i] <= 10
 *     The product of any prefix or suffix of nums is guaranteed to fit in a 32-bit integer.
 */
@Log4j2
public class MaxProductSubarray {

    public static void main(String[] args) {

        /**
         * Output: 6
         */
        // final int[] nums = { 2, 3, -2, 4 };

        /**
         * Output: 0
         */
        // final int[] nums = { -2, 0, -1 };

        /**
         * Output: 2
         */
        final int[] nums = { 0, 2 };

        MaxProductSubarray maxProductSubarray = new MaxProductSubarray();
        int maxProductLukeBrute = maxProductSubarray.maxProductLukeBrute(nums);
        log.debug("Max product subarray: {}", () -> maxProductLukeBrute);
        log.debug("Max product subarray {} OK", () -> "maxProductLukeBrute");

        int maxProductTopDownMemo = maxProductSubarray.maxProductTopDownMemo(nums);
        Assertions.assertEquals(maxProductLukeBrute, maxProductTopDownMemo);
        log.debug("Max product subarray {} OK", () -> "maxProductTopDownMemo");

        int maxProductBottomUp = maxProductSubarray.maxProductBottomUp(nums);
        Assertions.assertEquals(maxProductLukeBrute, maxProductBottomUp);
        log.debug("Max product subarray {} OK", () -> "maxProductBottomUp");

        int maxProductBrute = maxProductSubarray.maxProductBrute(nums);
        Assertions.assertEquals(maxProductLukeBrute, maxProductBrute);
        log.debug("Max product subarray {} OK", () -> "maxProductBrute");

        int maxProductDp = maxProductSubarray.maxProductDp(nums);
        Assertions.assertEquals(maxProductLukeBrute, maxProductDp);
        log.debug("Max product subarray {} OK", () -> "maxProductDp");

    }

    /**
     * LC - DP
     * 
     * Runtime: 2 ms, faster than 77.77% of Java online submissions for Maximum Product Subarray.
     * Memory Usage: 44.5 MB, less than 77.44% of Java online submissions for Maximum Product Subarray.
     * 
     * Time: O(N)
     * Space: O(1)
     */
    public int maxProductDp(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }

        final int N = nums.length;

        /**
         * For positive numbers
         */
        int max_so_far = nums[0];

        /**
         * For negative numbers
         */
        int min_so_far = nums[0];

        int result = nums[0];

        for (int i = 1; i < N; i++) {
            int curr = nums[i];
            int tmp_max = Math.max(curr, Math.max(max_so_far * curr, min_so_far * curr));
            min_so_far = Math.min(curr, Math.min(max_so_far * curr, min_so_far * curr));

            max_so_far = tmp_max;

            result = Math.max(result, max_so_far);
        }

        return result;
    }

    /**
     * LC - Brute
     * 
     * Runtime: 419 ms, faster than 5.00% of Java online submissions for Maximum Product Subarray.
     * Memory Usage: 44.6 MB, less than 67.43% of Java online submissions for Maximum Product Subarray.
     * 
     * Time: O(N ^ 2)
     * Space: O(1)
     */
    public int maxProductBrute(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }

        final int N = nums.length;

        int maxProduct = nums[0];

        for (int i = 0; i < N; i++) {
            int prod = 1;
            for (int k = i; k < N; k++) {
                prod *= nums[k];
                maxProduct = Math.max(maxProduct, prod);
            }
        }

        return maxProduct;
    }

    /**
     * LC - Brute
     * 
     * 
     * Time: O(N ^ 2)
     * Space: O(1)
     */
    public int maxProductLcBrute(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }

        int result = nums[0];

        for (int i = 0; i < nums.length; i++) {
            int prod = 1;
            for (int j = i; j < nums.length; j++) {
                prod *= nums[j];
                result = Math.max(result, prod);
            }
        }

        return result;
    }

    public int maxProductLcDp(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }

        int max_so_far = nums[0];
        int min_so_far = nums[0];
        int result = max_so_far;

        for (int i = 1; i < nums.length; i++) {
            int curr = nums[i];
            int temp_max = Math.max(curr, Math.max(max_so_far * curr, min_so_far * curr));
            min_so_far = Math.min(curr, Math.min(max_so_far * curr, min_so_far * curr));

            max_so_far = temp_max;

            result = Math.max(max_so_far, result);
        }

        return result;
    }

    /**
     * Luke - DP - BottomUp
     * 
     * Time Limit Exceeded
     * 
     * Time: O(N ^ 2) average. O(N ^ 3) worst case
     * Space: O(N ^ 2)
     */
    public int maxProductBottomUp(int[] nums) {
        final int N = nums.length;
        int maxProduct = Integer.MIN_VALUE;

        final Integer[][] dp = new Integer[N][N];

        /**
         * Time: O(N ^ 2)
         * Space: O(1)
         */
        for (int low = 0; low < N; low++) {
            for (int high = low; high < N; high++) {
                dp[low][high] = low == high ? nums[low] : dp[low][high - 1] * nums[high];
                maxProduct = Math.max(maxProduct, dp[low][high]);
            }
        }

        return maxProduct;
    }

    /**
     * Luke - DP - Top-Down memo
     * 
     * Time Limit Exceeded
     * 
     * Time: O(N ^ 2) average. O(N ^ 3) worst case
     * Space: O(N ^ 2)
     */
    public int maxProductTopDownMemo(int[] nums) {
        final int N = nums.length;
        int maxProduct = Integer.MIN_VALUE;

        final Integer[][] memo = new Integer[N][N];

        /**
         * Time: O(N ^ 2)
         * Space: O(1)
         */
        for (int low = 0; low < N; low++) {
            for (int high = low; high < N; high++) {
                /**
                 * Time: O(N)
                 */
                int prod = backtrackTopDownMemo(nums, low, high, memo);
                maxProduct = Math.max(maxProduct, prod);
            }
        }

        return maxProduct;
    }

    private Integer backtrackTopDownMemo(final int[] nums, final int left, final int right, final Integer[][] memo) {
        if (memo[left][right] == null) {
            int prod = 1;
            int curr = left;

            /**
             * Time: O(N)
             * Soace: O(1)
             */
            while (curr <= right) {
                prod *= nums[curr];

                memo[left][curr] = prod;

                curr++;
            }

            memo[left][right] = prod;

            return prod;
        } else {
            return memo[left][right];
        }
    }

    /**
     * Luke - Brute
     * 
     * Time Limit Exceeded
     * 
     * Time: O(N ^ 3) - Brute
     * Space: O(1) -  Iterative. No recursion. Recursion stack size O(1)
     */

    /**
     * There is no recursion. Therefore, there is no need of a property variable.
     */
    // int maxProduct = Integer.MIN_VALUE;

    public int maxProductLukeBrute(int[] nums) {
        final int N = nums.length;
        int maxProduct = Integer.MIN_VALUE;

        /**
         * Time: O(N ^ 2)
         * Space: O(1)
         */
        for (int low = 0; low < N; low++) {
            for (int high = low; high < N; high++) {
                /**
                 * Time: O(N)
                 */
                int prod = backtrackBrute(nums, low, high);
                maxProduct = Math.max(maxProduct, prod);
            }
        }

        return maxProduct;
    }

    private int backtrackBrute(final int[] nums, final int left, final int right) {
        int prod = 1;
        int curr = left;

        /**
         * Time: O(N)
         * Soace: O(1)
         */
        while (curr <= right) {
            prod *= nums[curr];
            curr++;
        }

        return prod;
    }
}
