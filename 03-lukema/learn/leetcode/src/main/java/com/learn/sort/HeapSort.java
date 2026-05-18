package com.learn.sort;


/**
 * https://www.javatpoint.com/heap-sort
 * https://www.geeksforgeeks.org/heap-sort/
 * 
 * idxSubTreeRoot = ("idxLeftLeaf or idxRightLeaf" - 1) / 2;
 * idxLeftLeaf = 2 * idxSubTreeRoot + 1;
 * idxRightLeaf = 2 * idxSubTreeRoot + 2;
 * 
 * 1. Time Complexity
 * Case            Time Complexity
 * Best Case       O(n*log n)
 * Average Case    O(n*log n)
 * Worst Case      O(n*log n)
 * 
 * 2. Space Complexity     O(1)
 * Stable     N0
 */
public class HeapSort {

    public static void sort(int[] nums) {

        if (nums == null || nums.length < 2) {
            return;
        }

        int len = nums.length;

        // Build the whole max-heap, starting from the last non-leaf node.
        for (int i = len / 2 - 1; i >= 0; i--) {
            heapify(nums, len, i);
        }

        /**
         * The root of the heap the the largest.
         * Extract the root element from max-heap, and then place it to the last position of the array.
         * Then, heapify the new tree from root. Extract the root element from max-heap, and then place
         * it to the second last position of the array.
         * Repeat until the second root is extracted. The final call of heapify will place the last two elements
         * into the right positions.
         */
        for (int i = len - 1; i > 0; i--) {
            // Move current root to end
            int tmp = nums[0];
            nums[0] = nums[i];
            nums[i] = tmp;

            // call max heapify on the reduced heap
            heapify(nums, i, 0);
        }
    }

    /**
     * idxSubTreeRoot = ("idxLeftLeaf or idxRightLeaf" - 1) / 2;
     * idxLeftLeaf = 2 * idxSubTreeRoot + 1;
     * idxRightLeaf = 2 * idxSubTreeRoot + 2;
     * 
     * Build a max-heap: Root is larger than both left and right leaves.
     * To heapify a subtree rooted with node idxSubTreeRoot which is
     * an index in nums[]. len is size of heap.
     */
    private static void heapify(int nums[], final int len, int idxSubTreeRoot) {
        int idxLargestValue = idxSubTreeRoot; // Initialize largest as root
        int idxLeftLeaf = 2 * idxSubTreeRoot + 1; // left = 2 * i + 1
        int idxRightLeaf = 2 * idxSubTreeRoot + 2; // right = 2 * i + 2

        // No leaves
        if (idxLeftLeaf >= len) {
            return;
        }

        // If left child is larger than root
        if (nums[idxLeftLeaf] > nums[idxLargestValue]) {
            idxLargestValue = idxLeftLeaf;
        }

        // If right child is larger than largest so far
        if (idxRightLeaf < len && nums[idxRightLeaf] > nums[idxLargestValue]) {
            idxLargestValue = idxRightLeaf;
        }

        /**
         * If largest is not root, swap the largest with root, and heapify the subtree starting from the leaf just
         * swapped with root.
         */
        if (idxLargestValue != idxSubTreeRoot) {
            int tmp = nums[idxSubTreeRoot];
            nums[idxSubTreeRoot] = nums[idxLargestValue];
            nums[idxLargestValue] = tmp;

            // Recursively heapify the affected sub-tree
            heapify(nums, len, idxLargestValue);
        }
    }

}
