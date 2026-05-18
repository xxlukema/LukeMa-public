package com.learn.sort;

/**
 * https://www.javatpoint.com/bubble-sort
 * 
 * 1. Time Complexity
 * Case            Time Complexity
 * Best Case       O(n)
 * Average Case    O(n^2)
 * Worst Case      O(n^2)
 * 
 * 2. Space Complexity     O(1)
 * Stable     YES
 */
public class BobbleSort {

    public static void sort(int nums[]) {

        if (nums == null || nums.length < 2) {
            return;
        }

        for (int i = 0; i < nums.length; i++) {
            for (int k = i + 1; k < nums.length; k++) {
                if (nums[k] < nums[i]) {
                    int temp = nums[i];
                    nums[i] = nums[k];
                    nums[k] = temp;
                }
            }
        }
    }

}
