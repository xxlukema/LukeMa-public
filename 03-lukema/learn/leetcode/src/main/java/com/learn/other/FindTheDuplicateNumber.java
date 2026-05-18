package com.learn.other;


import lombok.extern.log4j.Log4j2;


/**
 * LC - 287 - Find The Duplicate Number
 *
 * Medium
 *
 * Given an array of integers nums containing n + 1 integers where each integer is in the range [1, n] inclusive.
 *
 * There is only one repeated number in nums, return this repeated number.
 * You must solve the problem without modifying the array nums and uses only constant extra space.
 *
 * Example 1:
 * Input: nums = [1,3,4,2,2]
 * Output: 2
 *
 * Example 2:
 * Input: nums = [3,1,3,4,2]
 * Output: 3
 *
 * Constraints:
 *     1 <= n <= 105
 *     nums.length == n + 1
 *     1 <= nums[i] <= n
 *     All the integers in nums appear only once except for precisely one integer which appears two or more times.
 *
 * Follow up:
 *     How can we prove that at least one duplicate number must exist in nums?
 *     Can you solve the problem in linear runtime complexity?
 */
@Log4j2
public class FindTheDuplicateNumber {

    public static void main(String[] args) {

        /**
         * Expected: 2
         */
        final int[] nums = { 1, 3, 4, 2, 2 };

        FindTheDuplicateNumber findTheDuplicateNumber = new FindTheDuplicateNumber();
        // var ret = findTheDuplicateNumber.findDuplicate(nums);
        var ret = findTheDuplicateNumber.findDuplicateFastSlow(nums);
        log.debug("Find The Duplicate Number: {}", () -> ret);
        log.debug("Find The Duplicate Number {} OK", () -> "ret");

    }

    /**
     * Negate cells
     *
     * Problems: Changed cell values
     *
     * Runtime: 16 ms Beats 46.15%
     * Memory: 75.5 MB Beats 60.4%
     *
     * Time: O(N)
     * Space: O(1)
     */
    public int findDuplicate(int[] nums) {
        int pos = nums[0];

        while (nums[pos] > 0) {
            int next = nums[pos];

            if (nums[next] < 0) {
                pos = next;
                break;
            }

            nums[pos] *= -1;
            pos = next;
        }

        for (int i = 0; i < nums.length; i++) {
            nums[i] = Math.abs(nums[i]);
        }

        return pos;
    }

    /**
     * LC - Fast/Slow Pointers
     *
     * Proof: Given N nodes. Intersect at n. Hare and tortoise meet at k.
     *        2 k = n + (N - n) + (k - n) * i
     *        Let i == 1:
     *        2 k = n + (N - n) + (k - n) = (N - n) + k
     *        k = N - n
     *        k + n = N
     *
     * Runtime: 10 ms Beats 66.40%
     * Memory: 75.2 MB Beats 73.24%
     *
     * Time: O(N)
     * Space: O(1)
     */
    public int findDuplicateFastSlow(int[] nums) {
        int tortoise = nums[0];
        int hare = nums[0];

        do {
            tortoise = nums[tortoise];
            hare = nums[nums[hare]];
        } while (tortoise != hare);

        tortoise = nums[0];
        while (tortoise != hare) {
            hare = nums[hare];
            tortoise = nums[tortoise];
        }

        return tortoise;
    }
}
