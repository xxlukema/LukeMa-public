package com.learn.interview.amzn;


import java.util.Random;

import lombok.extern.log4j.Log4j2;


@Log4j2
public class QuickSort {

    public static void main(String[] args) {

        // final int[] nums = { 3, 1, 2, 9, 7, 8, 6, 5, 4, 0, 11, 3 };
        // final int[] nums = { 3, 1, 2, };
        final int[] nums = { 0, 1, 2 };

        log.debug("Before sort: {}", () -> nums);

        // QuickSort.mergeSort(nums);
        QuickSort.quickSort(nums);

        log.debug("QuickSort.mergeSort: {}", () -> nums);
        log.debug("QuickSort.mergeSort {} OK", () -> "nums");

    }

    /**
     * 1. It is not stable. But it can be made stable.
     * 2. In-place sorting.
     * 3. However, merge sort is generally considered better when data is huge and stored in external storage.
     *
     * Best Time: O(N * log(N)) --- If partition in the middle, then depth will be log(N), and all elements will be compared at each level.
     * Average Time: O(N * log(N)) --- Need to consider all the permutations.
     * Worst Time: O(N ^ 2)
     * Space: O(N) stack size.
     */
    public static void quickSort(final int[] nums) {
        /**
         * edge conditions
         */
        if (nums == null || nums.length < 2) {
            return;
        }

        /**
         * Optional accelerator
         */
        /*
        if (nums.length == 2) {
            if (nums[0] > nums[1]) {
                swap(nums, 0, 1);
            }
            return;
        }
        */

        quickSort(nums, 0, nums.length - 1);
    }

    /**
     *
     * @param nums
     * @param begin
     * @param end inclusive
     */
    private static void quickSort(int[] nums, int begin, int end) {

        if (begin >= end) {
            return;
        }

        /**
         * Optional accelerator
         */
        /*
        if (begin == end - 1) {
            if (nums[begin] > nums[end]) {
                swap(nums, begin, end);
            }
            return;
        }
        */

        int partition = partitionForQuickSort(nums, begin, end);
        quickSort(nums, begin, partition - 1);
        quickSort(nums, partition + 1, end);
    }

    private static int partitionForQuickSort(int[] nums, int begin, int end) {

        /**
         * random range: [begin, end - 1]
         */
        int random = new Random().nextInt(begin, end);
        // int random = ThreadLocalRandom.current().nextInt(begin, end);
        // int random = (int) (Math.random() * (end - begin)) + begin;
        swap(nums, random, end);

        int pivot = nums[end];

        int left = begin;
        int right = end - 1;

        while (left < right) {
            while (nums[left] < pivot && left < right) {
                left++;
            }

            while (nums[right] >= pivot && left < right) {
                right--;
            }

            swap(nums, left, right);
        }

        if (nums[left] > pivot) {
            swap(nums, left, end);
        }

        return left;
    }

    /**
     * 1. Not in-place. Needs extra memeory.
     * 2. Stable
     *
     * Time: O(N * log(N)) -- It breaks data into log(N) levels. Each level it merges every element O(N). Total O(N * log(N))
     * Space: O(N) --- It created extra space for merging. Total O(N) + O(log(N))
     */
    public static void mergeSort(final int[] nums) {
        /**
         * edge conditions
         */
        if (nums == null || nums.length < 2) {
            return;
        }

        /**
         * Optional accelerator
         */
        if (nums.length == 2) {
            if (nums[0] > nums[1]) {
                swap(nums, 0, 1);
            }
            return;
        }

        // break and conquer
        mergeSort(nums, 0, nums.length - 1);
    }

    /**
     *
     * @param nums int array
     * @param left left index (inclusive)
     * @param right right index (inclusive)
     */
    private static void mergeSort(int[] nums, int left, int right) {
        if (left == right || left < 0 || right < 0 || left > nums.length || right > nums.length) {
            return;
        }

        if (left > right) {
            // mergeSort(nums, right, left);
            return;
        }

        if (left == right - 1) {
            if (nums[left] > nums[right]) {
                swap(nums, left, right);
            }
            return;
        }

        int mid = left + (right - left) / 2;

        mergeSort(nums, left, mid);
        mergeSort(nums, mid + 1, right);

        // merge
        int idxLeft = left;
        int idxRight = mid + 1;

        int[] tmp = new int[right - left + 1];
        int pos = 0;
        while (idxLeft <= mid && idxRight <= right) {
            if (nums[idxLeft] <= nums[idxRight]) {
                tmp[pos++] = nums[idxLeft++];
            } else {
                tmp[pos++] = nums[idxRight++];
            }
        }
        while (idxLeft <= mid) {
            tmp[pos++] = nums[idxLeft++];
        }
        while (idxRight <= right) {
            tmp[pos++] = nums[idxRight++];
        }
        for (int i = 0; i < tmp.length; i++) {
            nums[left + i] = tmp[i];
        }
    }

    private static void swap(final int[] nums, final int left, final int right) {
        if (left == right) {
            return;
        }

        int tmp = nums[left];
        nums[left] = nums[right];
        nums[right] = tmp;
    }
}
