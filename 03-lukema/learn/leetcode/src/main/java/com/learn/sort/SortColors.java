package com.learn.sort;


import lombok.extern.log4j.Log4j2;


/**
 * LC 75 - Sort Colors
 */
@Log4j2
public class SortColors {

    public static void main(String[] args) {

        // int[] nums = { 2, 0, 2, 1, 1, 0 };
        // int[] nums = { 1, 0 };
        // int[] nums = { 0, 1 };
        // int[] nums = { 0, 1, 2 };
        // int[] nums = { 0, 1, 1 };
        // int[] nums = { 2, 0, 1 };
        // int[] nums = { 0, 0 };
        int[] nums = { 2, 2 };

        SortColors sortColors = new SortColors();

        // sortColors.sortColorsBooble(nums);
        // sortColors.sortColorsHeap(nums);
        // sortColors.sortColorsOnePathLuke(nums);
        sortColors.sortColorsOnePathLc(nums);

        log.debug("Sort color Luke: {}", () -> nums);

    }

    /**
     * LC One Path - There are ONLY three colors: red, white, or blue!
     * 
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Sort Colors.
     * Memory Usage: 42.7 MB, less than 28.94% of Java online submissions for Sort Colors.
     * 
     * Time: O(n)
     * Space: O(1)
     */
    public void sortColorsOnePathLc(int[] nums) {

        // for all idx < p0 : nums[idx < p0] = 0
        // curr is an index of element under consideration
        int endIdxOf0 = 0;
        int curr = 0;

        // for all idx > p2 : nums[idx > p2] = 2
        int startIdxOf2 = nums.length - 1;

        while (curr <= startIdxOf2) {
            if (nums[curr] == 0) {
                nums[curr] = nums[endIdxOf0];
                nums[endIdxOf0] = 0;
                curr++;
                endIdxOf0++;
            } else if (nums[curr] == 2) {
                nums[curr] = nums[startIdxOf2];
                nums[startIdxOf2] = 2;
                startIdxOf2--;
            } else {
                curr++;
            }
        }
    }

    /**
     * Luke One Path - There are ONLY three colors: red, white, or blue!
     * 
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Sort Colors.
     * Memory Usage: 43 MB, less than 9.04% of Java online submissions for Sort Colors.
     * 
     * Time: O(n)
     * Space: O(1)
     */
    public void sortColorsOnePathLuke(int[] nums) {

        int startOf1 = 0;
        int endOf1 = nums.length - 1;

        for (int i = 0; i <= endOf1; i++) {
            switch (nums[i]) {
                case 0:
                    while (startOf1 < nums.length && nums[startOf1] == 0) {
                        startOf1++;
                    }

                    if (startOf1 > nums.length) {
                        return;
                    }

                    if (startOf1 >= endOf1) {
                        return;
                    }

                    if (i > startOf1) {
                        nums[i] = nums[startOf1];
                        nums[startOf1] = 0;
                        startOf1++;
                    }

                    break;
                case 1:
                    if (i < startOf1) {
                        nums[startOf1 - 1] = 1;
                        nums[i] = 0;
                        startOf1--;
                    } else if (i > endOf1) {
                        nums[endOf1 + 1] = 1;
                        nums[i] = 2;
                        endOf1++;
                    }

                    break;
                case 2:
                    while (endOf1 >= 0 && nums[endOf1] == 2) {
                        endOf1--;
                    }

                    if (endOf1 < 0) {
                        return;
                    }

                    if (startOf1 >= endOf1) {
                        return;
                    }

                    if (i < endOf1) {
                        nums[i] = nums[endOf1];
                        nums[endOf1] = 2;
                        endOf1--;
                        i--;
                    }

                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Luke HeapShort
     * 
     * Runtime: 1 ms, faster than 40.39% of Java online submissions for Sort Colors.
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Sort Colors.
     * Memory Usage: 42.2 MB, less than 69.22% of Java online submissions for Sort Colors.
     * 
     * leftIdx = 2 * rootIdx + 1
     * rightIdx = 2 * rootIdx + 2 = leftIdx + 1
     * 
     * rootIdx = (leftIdx or rightIdx - 1) / 2;
     * 
     * Time: O(n log(n))
     * Space: O(n log(n))
     */
    public void sortColorsHeap(int[] nums) {

        final int LAST_INDEX = nums.length - 1;

        // sort from last root backward
        for (int i = (LAST_INDEX - 1) / 2; i >= 0; i--) {
            sortColorsHeap(nums, i, LAST_INDEX);
        }

        log.debug("Sort color Luke: {}", () -> nums);

        // swap root with last element and move last element pointer one up
        for (int i = LAST_INDEX; i > 0; i--) {
            int tmp = nums[0];
            nums[0] = nums[i];
            nums[i] = tmp;
            sortColorsHeap(nums, 0, i - 1);
        }
    }

    public void sortColorsHeap(int[] nums, final int rootIdx, final int lastIdx) {

        if (rootIdx < 0 || rootIdx > lastIdx) {
            return;
        }

        int left = 2 * rootIdx + 1;
        int right = left + 1;

        // No leaves
        if (left > lastIdx) {
            return;
        }

        // If left exists && left > root && (no right leaf || left > right)
        if (nums[left] > nums[rootIdx] && (right > lastIdx || nums[right] <= nums[left])) {
            // no right leaf. only compare left and root
            int tmp = nums[left];
            nums[left] = nums[rootIdx];
            nums[rootIdx] = tmp;

            sortColorsHeap(nums, left, lastIdx);
        } else if (right <= lastIdx && nums[right] > nums[rootIdx] && nums[right] >= nums[left]) {
            // right exists and is the largest
            int tmp = nums[right];
            nums[right] = nums[rootIdx];
            nums[rootIdx] = tmp;

            sortColorsHeap(nums, right, lastIdx);
        }
    }

    /**
     * Bobble sorting
     * 
     * Time: O(n ^ 2)
     * Spance: O(1)
     */
    public void sortColorsBooble(int[] nums) {

        for (int i = 0; i < nums.length - 1; i++) {
            for (int k = i + 1; k < nums.length; k++) {
                if (nums[k] < nums[i]) {
                    int tmp = nums[i];
                    nums[i] = nums[k];
                    nums[k] = tmp;
                }
            }
        }
    }

}
