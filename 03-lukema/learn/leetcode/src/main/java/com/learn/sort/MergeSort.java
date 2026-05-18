package com.learn.sort;

/**
 * https://www.javatpoint.com/merge-sort
 * 
 * 1. Time Complexity
 * Case           Time Complexity
 * Best Case      O(n*log n)
 * Average Case   O(n*log n)
 * Worst Case     O(n*log n)
 * 
 * 2. Space Complexity     O(n)
 * Stable               YES
 */
public class MergeSort {

  public static void sort(int nums[]) {

    if (nums == null || nums.length < 2) {
      return;
    }

    mergeSort(nums, 0, (nums.length - 1));
  }

  private static void mergeSort(int nums[], int idxBegin, int idxEnd) {
    if (idxEnd > idxBegin) {
      int middle = (idxBegin + idxEnd) / 2;
      mergeSort(nums, idxBegin, middle);
      mergeSort(nums, middle + 1, idxEnd);
      merge(nums, idxBegin, middle, idxEnd);
    }
  }

  private static void merge(int nums[], int idxBegin, int middle, int idxEnd) {
    /**
     * Create two temp arrays to hold current array data
     */
    int[] leftTempArray = new int[middle - idxBegin + 1];
    int[] rightTempArray = new int[idxEnd - middle];

    /**
     * Copy data to temp arrays
     */
    for (int i = 0; i < leftTempArray.length; i++) {
      leftTempArray[i] = nums[idxBegin + i];
    }

    for (int i = 0; i < rightTempArray.length; i++) {
      rightTempArray[i] = nums[middle + 1 + i];
    }

    /**
     * Merge temp arrays to current data array
     */
    int posData = idxBegin;
    int posTempLeft = 0;
    int posTempRight = 0;

    while (posTempLeft < leftTempArray.length && posTempRight < rightTempArray.length) {
      if (leftTempArray[posTempLeft] < rightTempArray[posTempRight]) {
        nums[posData++] = leftTempArray[posTempLeft++];
      } else {
        nums[posData++] = rightTempArray[posTempRight++];
      }
    }

    /**
     * Copy the remaining data from leftTempArray.
     */
    while (posTempLeft < leftTempArray.length) {
      nums[posData++] = leftTempArray[posTempLeft++];
    }

    /**
     * Copy the remaining data from rightTempArray.
     */
    while (posTempRight < rightTempArray.length) {
      nums[posData++] = rightTempArray[posTempRight++];
    }
  }

}
