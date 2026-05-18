package com.learn.backtrack;


import org.junit.jupiter.api.Assertions;

import lombok.extern.log4j.Log4j2;


/**
 * LC - 167 - Two Sum II - Input Array Is Sorted
 *
 * Medium
 *
 * Given a 1-indexed array of integers numbers that is already sorted in non-decreasing order, find two numbers such that they add up to a specific
 * target number. Let these two numbers be numbers[index1] and numbers[index2] where 1 <= index1 < index2 <= numbers.length.
 * Return the indices of the two numbers, index1 and index2, added by one as an integer array [index1, index2] of length 2.
 * The tests are generated such that there is exactly one solution. You may not use the same element twice.
 *
 * Your solution must use only constant extra space.
 *
 * Example 1:
 * Input: numbers = [2,7,11,15], target = 9
 * Output: [1,2]
 * Explanation: The sum of 2 and 7 is 9. Therefore, index1 = 1, index2 = 2. We return [1, 2].
 *
 * Example 2:
 * Input: numbers = [2,3,4], target = 6
 * Output: [1,3]
 * Explanation: The sum of 2 and 4 is 6. Therefore index1 = 1, index2 = 3. We return [1, 3].
 *
 * Example 3:
 * Input: numbers = [-1,0], target = -1
 * Output: [1,2]
 * Explanation: The sum of -1 and 0 is -1. Therefore index1 = 1, index2 = 2. We return [1, 2].
 *
 * Constraints:
 *     2 <= numbers.length <= 3 * 104
 *     -1000 <= numbers[i] <= 1000
 *     numbers is sorted in non-decreasing order.
 *     -1000 <= target <= 1000
 *     The tests are generated such that there is exactly one solution.
 */
@Log4j2
public class TwoSumIIInputArrayIsSorted {

    public static void main(String[] args) {

        final int[] numbers = { 2, 7, 11, 15 };
        final int target = 9;

        TwoSumIIInputArrayIsSorted twoSumIIInputArrayIsSorted = new TwoSumIIInputArrayIsSorted();

        var twoSumLukeIterative = twoSumIIInputArrayIsSorted.twoSumLukeIterative(numbers, target);
        log.debug("Two sum II: {}", () -> twoSumLukeIterative);
        log.debug("Two sum II {} OK", () -> "twoSumLukeIterative");

        var twoSumLukeTwoPointers = twoSumIIInputArrayIsSorted.twoSumLukeTwoPointers(numbers, target);
        Assertions.assertEquals(twoSumLukeIterative[0], twoSumLukeTwoPointers[0]);
        Assertions.assertEquals(twoSumLukeIterative[1], twoSumLukeTwoPointers[1]);
        log.debug("Two sum II {} OK", () -> "twoSumLukeTwoPointers");

        var twoSumLcTwoPointers = twoSumIIInputArrayIsSorted.twoSumLcTwoPointers(numbers, target);
        Assertions.assertEquals(twoSumLukeIterative[0], twoSumLcTwoPointers[0]);
        Assertions.assertEquals(twoSumLukeIterative[1], twoSumLcTwoPointers[1]);
        log.debug("Two sum II {} OK", () -> "twoSumLcTwoPointers");
    }

    /**
     * LC - Two Pointers - Smart !!!
     *
     * Runtime: 2 ms, faster than 80.20% of Java online submissions for Two Sum II - Input Array Is Sorted.
     * Memory Usage: 50 MB, less than 62.21% of Java online submissions for Two Sum II - Input Array Is Sorted.
     *
     * Time: O(N)
     * Space: O(1)
     */
    public int[] twoSumLcTwoPointers(final int[] numbers, final int target) {
        int left = 0;
        int right = numbers.length - 1;

        while (left < right) {
            int sum = numbers[left] + numbers[right];
            if (sum == target) {
                /**
                 * 1 index
                 */
                return new int[] { left + 1, right + 1 };
            } else if (sum < target) {
                left++;
            } else {
                right--;
            }
        }

        return null;
    }

    /**
     * Luke - Two Pointers
     *
     * Time Limit Exceeded
     *
     * Time: O(N ^ 2)
     * Space: O(1)
     */
    public int[] twoSumLukeTwoPointers(final int[] numbers, final int target) {
        int left = 0;
        int right = numbers.length - 1;

        /**
         * Same element cannot be used twice
         */
        while (left < right) {
            int candidate = target - numbers[left];
            while (left < right) {
                if (candidate == numbers[right]) {
                    /**
                     * 1 indexed
                     */
                    return new int[] { left + 1, right + 1 };
                } else {
                    right--;
                }
            }

            left++;
            right = numbers.length - 1;
        }

        return null;
    }

    /**
     * Luke - Iterative
     *
     * Runtime: 9 ms, faster than 7.16% of Java online submissions for Two Sum II - Input Array Is Sorted.
     * Memory Usage: 50.3 MB, less than 27.70% of Java online submissions for Two Sum II - Input Array Is Sorted.
     *
     * Time: O(N * log(N))
     * Space: O(1)
     */
    public int[] twoSumLukeIterative(final int[] numbers, final int target) {

        final int N = numbers.length;
        final int[] answer = new int[2];

        for (int i = 0; i < N; i++) {
            int curr = numbers[i];
            int candidate = target - curr;

            if (candidate == curr) {
                /**
                 * 1. Cannot use same num twice
                 * 2. There is a solution
                 * 3. Non-decreasing sorted
                 * 4. 1 index
                 */
                answer[0] = i + 1;
                answer[1] = i + 2;

                return answer;
            } else {
                int ret = findCandidate(numbers, candidate);

                // log.debug("-------- i: {}, ret: {}", i, ret);

                if (ret == -1) {
                    continue;
                } else {
                    /**
                     * 1 indexed
                     */
                    answer[0] = i + 1;
                    answer[1] = ret + 1;

                    return answer;
                }
            }
        }

        return null;
    }

    /**
     * 0 indexed
     *
     * constant extra space: no recursion
     */
    private int findCandidate(final int[] numbers, final int candidate) {
        final int N = numbers.length;

        int left = 0;
        int right = N - 1;

        while (left <= right) {
            if (left == right) {
                if (numbers[left] == candidate) {
                    return left;
                } else {
                    return -1;
                }
            }
            if (candidate == numbers[left]) {
                return left;
            } else if (candidate == numbers[right]) {
                return right;
            } else {
                int mid = left + (right - left) / 2;
                if (numbers[mid] == candidate) {
                    return mid;
                } else {
                    if (numbers[mid] < candidate) {
                        left = mid + 1;
                    } else {
                        right = mid;
                    }
                }
            }
        }

        return -1;
    }
}
