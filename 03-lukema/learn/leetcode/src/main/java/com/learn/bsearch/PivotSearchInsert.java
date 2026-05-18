package com.learn.bsearch;


import lombok.extern.log4j.Log4j2;


@Log4j2
public class PivotSearchInsert {

    public static void main(String[] args) {

        int[] nums = { 1, 3, 5, 6 };
        int target = 5;

        PivotSearchInsert pivotSearchInsert = new PivotSearchInsert();
        int pos = pivotSearchInsert.pivotSearchInsert(nums, target);

        log.info("pos: {}", () -> pos);
    }

    int pivotSearchInsert(int[] nums, int target) {

        int left = 0;
        int right = nums.length;
        int pivot = (left + right) / 2;

        while (left <= right) {
            if (target == nums[pivot]) {
                return pivot;
            } else if (target < nums[pivot]) {
                right = pivot - 1;
            } else {
                left = pivot + 1;
            }
        }
        return left;
    }
}
