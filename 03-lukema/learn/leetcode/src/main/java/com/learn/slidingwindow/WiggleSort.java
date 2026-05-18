package com.learn.slidingwindow;


import java.util.Arrays;
import java.util.LinkedList;
import java.util.PriorityQueue;

import lombok.extern.log4j.Log4j2;


/**
 * LC - 280 - Waggle Sort
 *
 * Medium
 *
 * Given an integer array nums, reorder it such that nums[0] <= nums[1] >= nums[2] <= nums[3]....
 *
 * You may assume the input array always has a valid answer.
 *
 * Example 1:
 * Input: nums = [3,5,2,1,6,4]
 * Output: [3,5,1,6,2,4]
 * Explanation: [1,6,2,5,3,4] is also accepted.
 *
 * Example 2:
 * Input: nums = [6,6,5,6,3,8]
 * Output: [6,6,5,6,3,8]
 *
 * Constraints:
 *     1 <= nums.length <= 5 * 104
 *     0 <= nums[i] <= 104
 *     It is guaranteed that there will be an answer for the given input nums.
 *
 * Follow up: Could you solve the problem in O(n) time complexity?
 */
@Log4j2
public class WiggleSort {

    public static void main(String[] args) {

        final int[] nums = { 3, 5, 2, 1, 6, 4, };

        WiggleSort wiggleSort = new WiggleSort();
        wiggleSort.wiggleSortInplace(nums);

        log.debug("Wiggle Sort: {}", () -> nums);
        log.debug("Wiggle Sort {} OK", () -> "nums");

    }

    /**
     * Luke - One Way
     *
     * Runtime: 2 ms Beats 44.17%
     * Memory: 49.4 MB Beats 39.48%
     *
     * Time: O(N)
     * Space: O(1)
     */
    public void wiggleSortOneWay(final int[] nums) {

        final int LEN = nums.length;

        boolean isLow = true;

        for (int i = 0; i < LEN; i++) {
            if (isLow) {
                if (i + 1 < LEN && nums[i] > nums[i + 1]) {
                    swap(nums, i, i + 1);
                }
            } else {
                if (i + 1 < LEN && nums[i] < nums[i + 1]) {
                    swap(nums, i, i + 1);
                }
            }
            isLow = !isLow;
        }
    }

    /**
     * Luke - Two PriorityQueue
     *
     * Time: O(N long(N))
     * Space: O(N)
     */
    public void wiggleSortPriorityQueue(final int[] nums) {
        final PriorityQueue<Integer> minQueue = new PriorityQueue<>();
        final PriorityQueue<Integer> maxQueue = new PriorityQueue<>((a, b) -> b.intValue() - a.intValue());

        for (int i : nums) {
            minQueue.offer(i);
            maxQueue.offer(i);
        }

        boolean isMin = true;

        for (int i = 0; i < nums.length; i++) {
            if (isMin) {
                nums[i] = minQueue.poll();
            } else {
                nums[i] = maxQueue.poll();
            }
            isMin = !isMin;
        }
    }

    /**
     * Luke - LinkedList
     *
     * Time: O(N long(N))
     * Space: O(N)
     */
    public void wiggleSortLinkedList(final int[] nums) {

        final LinkedList<Integer> llist = new LinkedList<>();

        for (int i : nums) {
            llist.add(i);
        }

        llist.sort((a, b) -> a.intValue() - b.intValue());

        boolean isMin = true;

        for (int i = 0; i < nums.length; i++) {
            if (isMin) {
                nums[i] = llist.removeFirst();
            } else {
                nums[i] = llist.removeLast();
            }
            isMin = !isMin;
        }
    }

    /**
     * Luke - Inplace
     *
     * Time: O(N long(N))
     * Space: O(1)
     */
    public void wiggleSortInplace(final int[] nums) {

        Arrays.sort(nums);

        int left = 1;
        int right = nums.length - (nums.length % 2 == 0 ? 2 : 1);

        while (left < right) {
            swap(nums, left, right);
            left += 2;
            right += 2;
        }

    }

    void swap(final int[] nums, final int left, final int right) {
        if (left < 0 || right < 0 || left >= nums.length || right >= nums.length) {
            return;
        }
        int tmp = nums[left];
        nums[left] = nums[right];
        nums[right] = tmp;
    }

}
