package com.learn.other;


import lombok.extern.log4j.Log4j2;


/**
 * LC-268 Missing Number
 *
 * Easy
 *
 * Given an array nums containing n distinct numbers in the range [0, n], return the only number in the range that is missing
 * from the array.
 *
 * Example 1:
 * Input: nums = [3,0,1]
 * Output: 2
 * Explanation: n = 3 since there are 3 numbers, so all numbers are in the range [0,3]. 2 is the missing number in the
 * range since it does not appear in nums.
 *
 * Example 2:
 * Input: nums = [0,1]
 * Output: 2
 * Explanation: n = 2 since there are 2 numbers, so all numbers are in the range [0,2]. 2 is the missing number in the
 * range since it does not appear in nums.
 *
 * Example 3:
 * Input: nums = [9,6,4,2,3,5,7,0,1]
 * Output: 8
 * Explanation: n = 9 since there are 9 numbers, so all numbers are in the range [0,9]. 8 is the missing number in the
 * range since it does not appear in nums.
 *
 * Constraints:
 *     n == nums.length
 *     1 <= n <= 10 ^ 4
 *     0 <= nums[i] <= n
 *     All the numbers of nums are unique.
 *
 * Follow up: Could you implement a solution using only O(1) extra space complexity and O(n) runtime complexity?
 */
@Log4j2
public class MissingNumber {

    public static void main(String[] args) {

        /**
         * Expected: 8
         */
        final int[] nums = { 9, 6, 4, 2, 3, 5, 7, 0, 1 };

        MissingNumber missingNumber = new MissingNumber();

        var missingNumberGaus = missingNumber.missingNumberGaus(nums);
        log.debug("Missing Number: {}", () -> missingNumberGaus);
        log.debug("Missing Number {} OK", () -> "missingNumberGaus");

        var missingNumberXor = missingNumber.missingNumberXor(nums);
        log.debug("Missing Number: {}", () -> missingNumberXor);
        log.debug("Missing Number {} OK", () -> "missingNumberXor");

        var missingNumberSumWithoutGaus = missingNumber.missingNumberSumWithoutGaus(nums);
        log.debug("Missing Number: {}", () -> missingNumberSumWithoutGaus);
        log.debug("Missing Number {} OK", () -> "missingNumberSumWithoutGaus");

    }

    /**
     * Luke - (1) max = array.len, (2) sum = (1 + max) * len / 2. (3) sum - array[i]
     *
     * Time: O(N)
     * Space: O(1)
     */
    public int missingNumberGaus(int[] nums) {
        // 0, 1, 2, 3 --> sum:
        final int len = nums.length;
        final int sum = (0 + (len + 1)) * len / 2;
        int missing = sum;
        for (int cur : nums) {
            missing -= cur;
        }
        return missing;
    }

    /**
     * LC
     */
    public int missingNumberSumWithoutGaus(int[] nums) {
        final int len = nums.length;
        int sumFull = len;
        int sum = 0;
        for (int i = 0; i < len; i++) {
            sumFull += i;
            sum += nums[i];
        }
        return sumFull - sum;
    }

    /**
     * LC - Improved by Luke
     *
     * Runtime: 0 ms Beats 100%
     * Memory: 43.5 MB Beats 74.37%
     *
     * Time: O(N)
     * Space: O(1)
     */
    public int missingNumberSumWithoutGausImprove(int[] nums) {
        final int len = nums.length;
        int missing = len;
        for (int i = 0; i < len; i++) {
            missing += i;
            missing -= nums[i];
        }
        return missing;
    }

    /**
     * LC - XOR
     *
     * Runtime: 0 ms Beats 100%
     * Memory: 43.1 MB Beats 83.87%
     *
     * Time: O(N)
     * Space: O(1)
     */
    public int missingNumberXor(int[] nums) {
        int missing = nums.length;
        for (int i = 0; i < nums.length; i++) {
            missing ^= i;
            missing ^= nums[i];
        }

        return missing;
    }
}
