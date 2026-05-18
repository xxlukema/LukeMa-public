package com.learn.dp;


import java.util.Arrays;
import java.util.Iterator;
import java.util.PriorityQueue;

import org.junit.jupiter.api.Assertions;

import lombok.extern.log4j.Log4j2;


/**
 * LC - 300 - Longest Increasing Subsequence
 *
 * Medium
 *
 * Given an integer array nums, return the length of the longest strictly increasing subsequence.
 *
 * A subsequence is a sequence that can be derived from an array by deleting some or no elements without changing the order of the
 * remaining elements. For example, [3,6,2,7] is a subsequence of the array [0,3,1,6,2,2,7].
 *
 * Example 1:
 * Input: nums = [10,9,2,5,3,7,101,18]
 * Output: 4
 * Explanation: The longest increasing subsequence is [2,3,7,101], therefore the length is 4.
 *
 * Example 2:
 * Input: nums = [0,1,0,3,2,3]
 * Output: 4
 *
 * Example 3:
 * Input: nums = [7,7,7,7,7,7,7]
 * Output: 1
 *
 * Constraints:
 *     1 <= nums.length <= 2500
 *     -104 <= nums[i] <= 104
 * Follow up: Can you come up with an algorithm that runs in O(n log(n)) time complexity?
 */
@Log4j2
public class LongestIncreasingSubsequence {

    public static void main(String[] args) {

        /**
         * Expected: 4
         */
        // final int[] nums = { 10, 9, 2, 5, 3, 7, 101, 18 };

        /**
         * Expected: 4
         */
        final int[] nums = { 2, 15, 3, 7, 8, 6, 18 };

        /**
         * Expected: 4
         */
        // final int[] nums = { 0, 1, 0, 3, 2, 3 };

        LongestIncreasingSubsequence longestIncreasingSubsequence = new LongestIncreasingSubsequence();
        var lengthOfLISDp = longestIncreasingSubsequence.lengthOfLISDp(nums);
        log.debug("Longest Increasing Subsequence: {}", () -> lengthOfLISDp);
        log.debug("Longest Increasing Subsequence {} OK", () -> "lengthOfLISDp");

        var lengthOfLISMap = longestIncreasingSubsequence.lengthOfLISMap(nums);
        Assertions.assertEquals(lengthOfLISDp, lengthOfLISMap);
        log.debug("Longest Increasing Subsequence {} OK", () -> "lengthOfLISMap");

    }

    /**
     * Luke - PriorityQueue - Wrong. PriorotyQueue is not ordered.
     */
    public int lengthOfLISMap(int[] nums) {
        /**
         * Edge condition
         */
        if (nums == null || nums.length < 1) {
            return 0;
        }

        record Cell(int val, int maxLen) {
        }

        /**
         * PriorityQueue of cells with max Len at the front
         */
        final PriorityQueue<Cell> pQueue = new PriorityQueue<>((a, b) -> b.maxLen - a.maxLen);

        pQueue.offer(new Cell(nums[0], 1));

        /**
         * Time: O(N)
         * Space: O(1)
         */
        // int min = IntStream.of(nums).min().getAsInt();

        for (int i = 1; i < nums.length; i++) {
            Iterator<Cell> it = pQueue.iterator();
            boolean found = false;
            while (it.hasNext()) {
                Cell cell = it.next();
                if (nums[i] > cell.val) {
                    pQueue.offer(new Cell(nums[i], cell.maxLen + 1));
                    found = true;
                    break;
                }
            }

            if (!found) {
                pQueue.offer(new Cell(nums[i], 1));
            }
        }

        log.debug("pQueue: {}", pQueue);

        return pQueue.poll().maxLen;
    }

    /**
     * Luke - DP - BottomUp
     *
     * Runtime: 91 ms Beats 40.9%
     * Memory: 44.3 MB Beats 52.17%
     *
     * Time: O(N ^ 2)
     * Space: O(N)
     */
    public int lengthOfLISDp(int[] nums) {

        // [10,9,2,5,3,4,7,101,18]

        final int[] dp = new int[nums.length];
        Arrays.fill(dp, 1);

        for (int i = 1; i < nums.length; i++) {
            for (int k = i - 1; k >= 0; k--) {
                if (nums[i] > nums[k]) {
                    dp[i] = Math.max(dp[i], dp[k] + 1);
                }
            }
        }

        int longest = 0;
        for (int c : dp) {
            longest = Math.max(longest, c);
        }

        return longest;
    }
}
