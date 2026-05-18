package com.learn.btree;


import lombok.extern.log4j.Log4j2;


@Log4j2
public class BiTree {

    private static final int[] nums = { 5 };

    public static void main(String[] args) {
        Solution solution = new Solution();
        log.info("Solution: {}", () -> solution.search(nums, 5));
    }

}


class Solution {
    public int search(int[] nums, int target) {
        return search(nums, target, 0, nums.length - 1);
    }

    private int search(int[] nums, int target, int il, int ir) {
        if (il == ir) {
            if (peek(nums, target, il) == 0) {
                return il;
            } else {
                return -1;
            }
        }

        int idx = il + (ir - il) / 2;

        int pk = peek(nums, target, idx);

        if (pk == 0) {
            return idx;
        } else if (pk < 0) {
            return search(nums, target, il, idx);
        } else {
            return search(nums, target, idx + 1, ir);
        }
    }

    private int peek(int[] nums, int target, int idx) {
        return target - nums[idx];
    }
}
