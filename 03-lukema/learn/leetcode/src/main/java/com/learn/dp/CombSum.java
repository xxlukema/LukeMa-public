package com.learn.dp;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class CombSum {

    public static void main(String[] args) {
        int[] nums = { 2, 3, 5 };
        int target = 8;

        CombSum comSum = new CombSum();

        List<List<Integer>> result = comSum.combinationSum(nums, target);
        log.info("result: {}", () -> result);
    }

    /*
    private List<List<Integer>> combinationSumDp(int[] nums, int target) {
        List<List<Integer>>[] dp = new List[target + 1];
    
        for (int i = 0; i <= target; i++) {
            dp[i] = new ArrayList<>();
        }
    
        dp[0].add(new ArrayList<>());
    
        for (int candidate : nums) {
            for (int j = candidate; j <= target; j++) {
                for (List<Integer> comb : dp[j - candidate]) {
                    List<Integer> newComb = new ArrayList<>(comb);
                    newComb.add(candidate);
                    dp[j].add(newComb);
                }
            }
        }
    
        return dp[target];
    }
    */

    public List<List<Integer>> combinationSum(int[] nums, int target) {
        List<List<Integer>> result = new ArrayList<>();
        LinkedList<Integer> subsolution = new LinkedList<>();

        backtrack(nums, target, 0, result, subsolution);

        return result;
    }

    void backtrack(int[] nums, int remain, int start, List<List<Integer>> result,
            LinkedList<Integer> subsolution) {
        if (remain == 0) {
            result.add(new ArrayList<>(subsolution));
            return;
        }

        if (remain < 0) {
            return;
        }

        for (int i = start; i < nums.length; i++) {
            subsolution.add(nums[i]);
            backtrack(nums, remain - nums[i], i, result, subsolution);
            subsolution.removeLast();
        }
    }
}
