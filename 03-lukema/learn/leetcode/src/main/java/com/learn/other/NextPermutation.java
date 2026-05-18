package com.learn.other;


import java.util.Arrays;

import lombok.extern.log4j.Log4j2;


/**
 * 31 - LC - Next Permutation
 *
 * Medium
 *
 * A permutation of an array of integers is an arrangement of its members into a sequence or linear order.
 *
 *     For example, for arr = [1,2,3], the following are all the permutations of arr: [1,2,3], [1,3,2], [2, 1, 3], [2, 3, 1], [3,1,2], [3,2,1].
 *
 * The next permutation of an array of integers is the next lexicographically greater permutation of its integer. More formally, if all the
 * permutations of the array are sorted in one container according to their lexicographical order, then the next permutation of that array is
 * the permutation that follows it in the sorted container. If such arrangement is not possible, the array must be rearranged as the lowest
 * possible order (i.e., sorted in ascending order).
 *
 *     For example, the next permutation of arr = [1,2,3] is [1,3,2].
 *     Similarly, the next permutation of arr = [2,3,1] is [3,1,2].
 *     While the next permutation of arr = [3,2,1] is [1,2,3] because [3,2,1] does not have a lexicographical larger rearrangement.
 *
 * Given an array of integers nums, find the next permutation of nums.
 *
 * The replacement must be in place and use only constant extra memory.
 *
 * Example 1:
 * Input: nums = [1,2,3]
 * Output: [1,3,2]
 *
 * Example 2:
 * Input: nums = [3,2,1]
 * Output: [1,2,3]
 *
 * Example 3:
 * Input: nums = [1,1,5]
 * Output: [1,5,1]
 *
 * Constraints:
 *     1 <= nums.length <= 100
 *     0 <= nums[i] <= 100
 */
@Log4j2
public class NextPermutation {

    public static void main(String[] args) {

        /**
         * Expected: [1,3,2]
         */
        // final int[] nums = { 1, 2, 3 };

        /**
         * Expected: [2,1,3]
         */
        final int[] nums = { 1, 3, 2 };

        /**
         * Expected: [3,1,2]
         */
        // final int[] nums = { 2, 3, 1 };

        /**
         * Expected: [4,2,0,3,0,2,2]
         */
        // final int[] nums = { 4, 2, 0, 2, 3, 2, 0 };

        /**
         * Expected: [3,1,2]
         */
        // final int[] nums = { 3, 2, 1 };

        /**
         * Expected: [1,5,1]
         */
        // final int[] nums = { 1,1,5 };

        NextPermutation nextPermutation = new NextPermutation();

        log.debug("Original: {}", () -> nums);

        int[] copy = Arrays.copyOf(nums, nums.length);

        nextPermutation.nextPermutationLuke(copy);
        log.debug("Next Permutation: {}", () -> copy);
        log.debug("Next Permutation {} OK", () -> "nextPermutationLuke");

        int[] copy2 = Arrays.copyOf(nums, nums.length);

        nextPermutation.nextPermutationLc(copy2);
        log.debug("Next Permutation: {}", () -> copy2);
        log.debug("Next Permutation {} OK", () -> "nextPermutationLc");
    }

    /**
     * LC - One Pass
     *
     * Runtime: 1 ms Beats 91.88%
     * Memory: 43.5 MB Beats 72.12%
     *
     * Time: O(N)
     * Space: O(1)
     */
    public void nextPermutationLc(int[] nums) {

        if (nums == null || nums.length < 2) {
            return;
        }

        final int N = nums.length;

        /**
         * Two pointers
         */
        int left = N - 2;

        while (true) {
            int right = left + 1;

            if (nums[left] >= nums[right]) {
                left--;
                if (left == -1) {
                    break;
                }
            } else {
                /**
                 * Find from left + 1 to right the smallest but greater than nums[left], and swap it with nums[left]
                 *
                 * From [left + 1] to [N - 1], data is already in descending order
                 */
                while (right < N) {
                    if (nums[left] < nums[right] && (right + 1 == N || nums[right + 1] <= nums[left])) {

                        int tmp = nums[left];
                        nums[left] = nums[right];
                        nums[right] = tmp;

                        reverseLc(nums, left + 1, N - 1);

                        return;
                    } else {
                        right++;
                    }
                }
            }
        }

        /**
         * Reverse
         */
        reverseLc(nums, 0, N - 1);
    }

    private void reverseLc(int[] nums, int start, int end) {
        while (start < end) {
            int tmp = nums[start];
            nums[start] = nums[end];
            nums[end] = tmp;

            start++;
            end--;
        }
    }

    /**
     * Luke - Two Pointer / Sliding Window
     *
     * Runtime: 1 ms Beats 91.88%
     * Memory: 42.4 MB Beats 93.43%
     *
     * Time: O(N ^ 2)
     * Space: O(1)
     */
    public void nextPermutationLuke(int[] nums) {

        if (nums == null || nums.length < 2) {
            return;
        }

        final int N = nums.length;

        /**
         * Two pointers
         */
        int left = N - 2;

        while (true) {
            int right = left + 1;
            /**
             * Find from left + 1 to right the smallest but greater than nums[left], and swap it with nums[left]
             */
            int min = Integer.MAX_VALUE;
            int minPos = 0;

            while (right < N) {
                if (nums[left] < nums[right]) {
                    if (nums[right] - nums[left] < min) {
                        minPos = right;
                    }
                }
                right++;
            }

            /**
             * If found
             */
            if (minPos > 0) {
                int tmp = nums[left];
                nums[left] = nums[minPos];
                nums[minPos] = tmp;

                /**
                 * Sort between left to right to ascending order to make it the smallest permuatation
                 *
                 * bobble sort
                 */
                bobbleSort(nums, left + 1, N - 1);

                return;
            } else {
                left--;
                if (left == -1) {
                    break;
                }
            }
        }

        /**
         * Reverse
         */
        int right = N - 1;
        left = 0;
        while (left < right) {
            int tmp = nums[left];
            nums[left] = nums[right];
            nums[right] = tmp;

            left++;
            right--;
        }

        bobbleSort(nums, 1, N - 1);
    }

    /**
     * BobbleSort
     *
     * Time: O(N ^ 2)
     * Space: O(1)
     *
     * @param nums
     * @param start
     * @param end
     */
    void bobbleSort(int[] nums, int start, int end) {
        for (int a = start; a <= end; a++) {
            for (int z = a + 1; z <= end; z++) {
                if (nums[a] > nums[z]) {
                    int t = nums[a];
                    nums[a] = nums[z];
                    nums[z] = t;
                }
            }
        }
    }
}
