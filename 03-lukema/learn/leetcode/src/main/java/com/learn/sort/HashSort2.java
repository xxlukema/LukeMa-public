package com.learn.sort;


public class HashSort2 {

    public static void sort(int[] nums) {
        if (nums == null) {
            return;
        }

        if (nums.length == 1) {
            return;
        }

        for (int i = (nums.length - 1) / 2; i >= 0; i--) {
            heapify(nums, i, nums.length);
        }

        for (int i = nums.length - 1; i >= 0; i--) {
            int tmp = nums[0];
            nums[0] = nums[i];
            nums[i] = tmp;

            heapify(nums, 0, i);
        }
    }

    private static void heapify(int[] nums, int idxSubTreeRoot, final int len) {
        int idxLargestValue = idxSubTreeRoot;
        int idxLeftLeaf = 2 * idxSubTreeRoot + 1;
        int idxRightLeaf = 2 * idxSubTreeRoot + 2;

        if (idxLeftLeaf >= len) {
            return;
        }

        if (nums[idxLeftLeaf] > nums[idxLargestValue]) {
            idxLargestValue = idxLeftLeaf;
        }
        if (idxRightLeaf < len && nums[idxRightLeaf] > nums[idxLargestValue]) {
            idxLargestValue = idxRightLeaf;
        }

        if (idxLargestValue != idxSubTreeRoot) {
            int tmp = nums[idxSubTreeRoot];
            nums[idxSubTreeRoot] = nums[idxLargestValue];
            nums[idxLargestValue] = tmp;

            heapify(nums, idxLargestValue, len);
        }
    }

}
