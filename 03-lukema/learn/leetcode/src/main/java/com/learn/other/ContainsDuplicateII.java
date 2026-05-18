package com.learn.other;


import java.util.HashSet;
import java.util.Set;

import lombok.extern.log4j.Log4j2;


/**
 * LC - 219 - Contains Duplicate II
 *
 * Easy
 *
 * Given an integer array nums and an integer k, return true if there are two distinct indices i and j in the array such that
 * nums[i] == nums[j] and abs(i - j) <= k.
 *
 * Example 1:
 * Input: nums = [1,2,3,1], k = 3
 * Output: true
 *
 * Example 2:
 * Input: nums = [1,0,1,1], k = 1
 * Output: true
 *
 * Example 3:
 * Input: nums = [1,2,3,1,2,3], k = 2
 * Output: false
 *
 * Constraints:
 *     1 <= nums.length <= 105
 *     -109 <= nums[i] <= 109
 *     0 <= k <= 105
 */
@Log4j2
public class ContainsDuplicateII {

    public static void main(String[] args) {

        /**
         * Expected: true
         */
        final int[] nums = { 1, 2, 3, 1 };
        final int k = 3;

        ContainsDuplicateII containsDuplicateII = new ContainsDuplicateII();

        var ret = containsDuplicateII.containsNearbyDuplicate(nums, k);
        log.debug("Contains Duplicate: {}", () -> ret);
        log.debug("Contains Duplicate {} OK", () -> "ret");

    }

    /**
     * Luke - One Way - HashSet<Integer> seen
     *
     * Runtime: 18 ms, faster than 97.43% of Java online submissions for Contains Duplicate II.
     * Memory Usage: 54.5 MB, less than 86.92% of Java online submissions for Contains Duplicate II.
     *
     * Time: O(N)
     * Space: O(k)
     */
    public boolean containsNearbyDuplicate(final int[] nums, final int k) {
        if (nums == null || nums.length < 2) {
            return false;
        }

        Set<Integer> seen = new HashSet<>();

        for (int m = 0, len = nums.length; m <= k && m < len; m++) {
            if (seen.contains(nums[m])) {
                return true;
            } else {
                seen.add(nums[m]);
            }
        }

        if (nums.length < k) {
            return false;
        }

        for (int i = k + 1, len = nums.length; i < len; i++) {
            seen.remove(nums[i - (k + 1)]);

            if (seen.contains(nums[i])) {
                return true;
            } else {
                seen.add(nums[i]);
            }
        }

        return false;
    }
}
