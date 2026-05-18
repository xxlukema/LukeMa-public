package com.learn.sort;


import java.util.Arrays;

import lombok.extern.log4j.Log4j2;


/**
 * https://www.javatpoint.com/quick-sort
 *
 * 1. Time Complexity
 * Case             Time Complexity
 * Best Case        O(n*logn)
 * Average Case     O(n*logn)
 * Worst Case       O(n^2)
 *
 * 2. Space Complexity      O(n*logn)
 * Stable      NO
 */
@Log4j2
public class QuickSort {

    public static void main(String[] args) {
        // int[] numsOrig = { 2, 1, 3, 9, 0, 4, 6, 8, 7, 5, /* dup */ 4 };
        // int[] numsOrig = { 0, 1 };
        int[] numsOrig = { 0, 1, 2 };

        log.debug("{}", () -> "MergeSort");

        int[] numsCopy0 = Arrays.copyOf(numsOrig, numsOrig.length);
        log.debug(" before sort: {}", () -> numsCopy0);

        QuickSort.quickSort(numsCopy0);
        log.debug(" Sorted array: {}", () -> numsCopy0);
    }

    public static void quickSort(int[] nums) {

        if (nums == null || nums.length < 2) {
            return;
        }

        quickSort(nums, 0, nums.length - 1);
    }

    private static void quickSort(int nums[], int startIndex, int endIndex) {

        if (startIndex >= endIndex) {
            return;
        }

        int posPivot = partition(nums, startIndex, endIndex); // pos is partitioning index
        quickSort(nums, startIndex, posPivot - 1);
        quickSort(nums, posPivot + 1, endIndex);
    }

    /**
     * function that consider last element as pivot,
     * place the pivot at its exact position, and place
     * smaller elements to left of pivot and greater
     * elements to right of pivot.
    */
    private static int partition(int nums[], int startIndex, int endIndex) {
        /**
         * Use last as pivot
         */
        int pivot = nums[endIndex];
        int idxLeft = startIndex;
        int idxRight = endIndex - 1;

        while (idxLeft < idxRight) {
            while (nums[idxLeft] < pivot && idxLeft < idxRight) {
                idxLeft++;
            }

            while (nums[idxRight] >= pivot && idxRight > idxLeft) {
                idxRight--;
            }

            swap(nums, idxLeft, idxRight);
        }

        if (nums[idxLeft] > pivot) {
            swap(nums, idxLeft, endIndex);
        }

        return idxLeft;
    }

    private static void swap(int[] nums, int idxLeft, int idxRight) {
        if (idxLeft == idxRight) {
            return;
        }

        int tmp = nums[idxLeft];
        nums[idxLeft] = nums[idxRight];
        nums[idxRight] = tmp;
    }

}
