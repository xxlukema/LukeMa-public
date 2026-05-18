package com.learn.dp;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class CombinSum {

    public static void main(String[] args) {
        int[] nums = { 2, 3, 5 };
        int target = 8;

        CombinSum combinSum = new CombinSum();

        List<List<Integer>> result = combinSum.combinationSum(nums, target);
        log.info("result: {}", () -> result);
    }

    public List<List<Integer>> combinationSum(int[] nums, int target) {
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        LinkedList<Integer> subsolution = new LinkedList<Integer>();

        this.backtrack(target, subsolution, 0, nums, result);
        return result;
    }

    protected void backtrack(int remain, LinkedList<Integer> subsolution, int start, int[] nums,
            List<List<Integer>> result) {

        if (remain == 0) {
            // make a deep copy of the current combination
            result.add(new ArrayList<Integer>(subsolution));
            return;
        } else if (remain < 0) {
            // exceed the scope, stop exploration.
            return;
        }

        for (int i = start; i < nums.length; ++i) {
            // add the number into the combination
            subsolution.add(nums[i]);
            this.backtrack(remain - nums[i], subsolution, i, nums, result);
            // backtrack, remove the number from the combination
            subsolution.removeLast();
        }
    }

}
