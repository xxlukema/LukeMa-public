package com.learn.dp;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.Assertions;

import lombok.extern.log4j.Log4j2;


/**
 * LC-315 Count of Smaller Numbers After Self
 *
 * Hard
 *
 * Given an integer array nums, return an integer array counts where counts[i] is the number of smaller elements to the right of nums[i].
 *
 * Example 1:
 * Input: nums = [5,2,6,1]
 * Output: [2,1,1,0]
 * Explanation:
 * To the right of 5 there are 2 smaller elements (2 and 1).
 * To the right of 2 there is only 1 smaller element (1).
 * To the right of 6 there is 1 smaller element (1).
 * To the right of 1 there is 0 smaller element.
 *
 * Example 2:
 * Input: nums = [-1]
 * Output: [0]
 *
 * Example 3:
 * Input: nums = [-1,-1]
 * Output: [0,0]
 *
 * Constraints:
 *     1 <= nums.length <= 10 ^ 5
 *     -10 ^ 4 <= nums[i] <= 10 ^ 4
 */
@Log4j2
public class CountOfSmallerNumbersAfterSelf {

    public static void main(String[] args) {

        /**
         * Expected: [2,1,1,0]
         */
        // final int[] nums = { 5, 2, 6, 1 };

        /**
         * Expected: [2,0,0]
         */
        // final int[] nums = { 2, 0, 1 };

        // final int[] nums = { -1 };

        // final int[] nums = { -1, -1 };

        // final int[] nums = { 67, 90, 23, 66, 23 };
        // final int[] nums = { 6812, 665, 911, 5890, -3018, -207, -3050, 4694, -4558, 6382, -680, 4998, };
        final int[] nums = { 26, 78, 27, 100, 33, 67, 90, 23, 66, 5, 38, 7, 35, 23, 52, 22, 83, 51, 98, 69, 81, 32, 78, 28, 94, 13, 2, 97, 3, 76,
                99, 51, 9, 21, 84, 66, 65, 36, 100, 41 };

        CountOfSmallerNumbersAfterSelf countOfSmallerNumbersAfterSelf = new CountOfSmallerNumbersAfterSelf();

        var countSmallerLukeNaive = countOfSmallerNumbersAfterSelf.countSmallerLukeNaive(nums);
        log.debug("Count of Smaller Numbers After Self: {}", () -> countSmallerLukeNaive);
        log.debug("Count of Smaller Numbers After Self {} OK", () -> "countSmallerLukeNaive");

        var countSmallerLukeKeyMap = countOfSmallerNumbersAfterSelf.countSmallerLukeKeyMap(nums);
        Assertions.assertEquals(countSmallerLukeNaive, countSmallerLukeKeyMap);
        log.debug("Count of Smaller Numbers After Self {} OK", () -> "countSmallerLukeKeyMap");

    }

    /**
     * Luke - Naive
     *
     * Time Limit Exceeded
     *
     * Time: O(N ^ 2)
     * Space: O(N)
     */
    public List<Integer> countSmallerLukeNaive(int[] nums) {

        final List<Integer> result = new ArrayList<>();
        if (nums == null || nums.length == 0) {
            return result;
        }

        final int[] count = new int[nums.length];
        count[nums.length - 1] = 0;

        int minIdx = nums.length - 1;
        int min = nums[minIdx];

        for (int i = nums.length - 2; i >= 0; i--) {

            /**
             * if no min
             */
            if (nums[i] <= min) {
                min = nums[i];
                minIdx = i;
                count[i] = 0;
                continue;
            }

            int pos = i + 1;
            while (pos < nums.length) {
                if (nums[i] > nums[pos]) {
                    count[i] += 1;
                }
                pos++;
            }
        }

        for (int v : count) {
            result.add(v);
        }

        return result;
    }

    /**
     * Luke - Time Limit Exceeded
     *
     * Time: O(N ^ 2) for [1,1,1,1,1,1]
     */
    public List<Integer> countSmallerLukeKeyMap(int[] nums) {

        final List<Integer> result = new ArrayList<>();
        if (nums == null || nums.length == 0) {
            return result;
        }

        // Map<Integer, Integer> keyCountMap = new TreeMap<>();
        LinkedList<Integer> keyList = new LinkedList<>();

        for (int i = nums.length - 1; i >= 0; i--) {
            int key = nums[i];

            if (keyList.isEmpty()) {
                keyList.add(key);
                result.add(0, 0);
            } else {
                /**
                 * B Search keys
                 */
                Integer[] keys = keyList.toArray(new Integer[0]);

                log.debug("keys: {}", () -> keys);

                int left = 0;
                int right = keys.length - 1;
                int med = left;

                /**
                 * B search:
                 * Time: O(log(N))
                 *
                 * 10, 20, 40 <==== 30
                 * 40 <==== 30
                 */
                while (left < right) {
                    med = left + (right - left) / 2;

                    if (key > keys[med]) {
                        left = med + 1;
                    } else {
                        /**
                         * Time: O(N) for [1,1,1,1,1,1]
                         */
                        right = med - 1;
                    }
                }

                // log.debug("key: {}, left: {}, nums[left]: {}", key, left, keys[left]);

                while (left >= 0 && key <= keys[left]) {
                    left--;
                }

                if (left < 0) {
                    keyList.addFirst(key);
                    result.add(0, 0);
                } else {
                    keyList.add(left + 1, key);
                    result.add(0, left + 1);
                }
            }
        }

        return result;
    }
}
