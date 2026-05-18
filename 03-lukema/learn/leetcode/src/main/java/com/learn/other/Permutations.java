package com.learn.other;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.extern.log4j.Log4j2;


@Log4j2
public class Permutations {

    public static void main(String[] args) {

        int[] nums = { 1, 2, 3, 4 };

        Permutations permutations = new Permutations();

        List<List<Integer>> ret = permutations.permuteLuke(nums);

        // List<List<Integer>> ret = permutations.permuteLC(nums);

        ret.forEach(elm -> {
            log.info("Permutation: {}", () -> elm);
        });

    }

    public List<List<Integer>> permuteLuke(int[] nums) {
        if (nums == null)
            return null;

        List<List<Integer>> results = new ArrayList<>();

        if (nums.length == 1) {
            results.add(Arrays.stream(nums).boxed().collect(Collectors.toList()));
        } else {
            LinkedList<Integer> perm = new LinkedList<>();

            permuteLuke(nums, results, perm);
        }

        return results;
    }

    public void permuteLuke(
            int[] nums,
            List<List<Integer>> results,
            LinkedList<Integer> perm) {

        if (perm.size() == nums.length) {
            // log.debug("perm: {}", () -> perm);
            results.add(List.copyOf(perm));

            /**
             * Extramely Important: Do not call "backtrack()" here. Instead, call retrun here to break the recursion.
             */
            return;
        } else {
            for (int i = 0; i < nums.length; i++) {
                final int n = nums[i];
                if (!perm.contains(n)) {
                    perm.add(n);
                    permuteLuke(nums, results, perm);
                    backtrackLuke(perm);
                }
            }
        }
    }

    /**
     * These is no need to check "perm.isEmpty()". When exception of removing last from empty LinkedList happens,
     * it means somewhere is calling "backtrackLuke()" unnecessarily.
     */
    void backtrackLuke(LinkedList<Integer> perm) {
        /**
         * These is no need to check "perm.isEmpty()". When exception of removing last from empty LinkedList happens,
         * it means somewhere is calling "backtrackLuke()" unnecessarily.
         */
        perm.removeLast();
    }

    public List<List<Integer>> permuteLC(int[] nums) {
        // init output list
        List<List<Integer>> output = new LinkedList<>();

        // convert nums into list since the output is a list of lists
        List<Integer> nums_lst = new ArrayList<Integer>();

        for (int num : nums) {
            nums_lst.add(num);
        }

        final int N = nums.length;
        backtrackLC(N, nums_lst, output, 0);
        return output;
    }

    public void backtrackLC(
            int n,
            List<Integer> nums,
            List<List<Integer>> output,
            int first) {

        // if all integers are used up
        if (first == n) {
            output.add(new ArrayList<Integer>(nums));
        }

        for (int i = first; i < n; i++) {
            // place i-th integer first
            // in the current permutation
            Collections.swap(nums, first, i);
            // use next integers to complete the permutations
            backtrackLC(n, nums, output, first + 1);
            // backtrack
            // Collections.swap(nums, first, i);
        }
    }

}
