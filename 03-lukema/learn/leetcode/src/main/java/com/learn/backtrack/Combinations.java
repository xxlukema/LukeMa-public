package com.learn.backtrack;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import lombok.extern.log4j.Log4j2;


/**
 * LC 77
 */
@Log4j2
public class Combinations {

    public static void main(String[] args) {

        final int n = 4;
        final int k = 3;

        Combinations combinations = new Combinations();

        var ret = combinations.combineBacktrackLuke(n, k);
        // var ret = combinations.combineBacktrackLc(n, k);
        log.debug("Combinations: {}", () -> ret);

    }

    public void backtrackLc(int first, int n, int size, LinkedList<Integer> comb, List<List<Integer>> combs) {
        // if the combination is done
        if (comb.size() == size)
            combs.add(List.copyOf(comb));

        for (int i = first; i < n + 1; ++i) {
            // add i into the current combination
            comb.add(i);
            // use next integers to complete the combination
            backtrackLc(i + 1, n, size, comb, combs);
            // backtrack
            comb.removeLast();
        }
    }

    public List<List<Integer>> combineBacktrackLc(int n, int k) {

        List<List<Integer>> combs = new LinkedList<>();
        LinkedList<Integer> comb = new LinkedList<>();

        backtrackLc(1, n, k, comb, combs);

        return combs;
    }

    /**
     * Luke: Backtracking
     *
     * Runtime: 71 ms, faster than 12.46% of Java online submissions for Combinations.
     * Memory Usage: 67.6 MB, less than 12.88% of Java online submissions for Combinations.
     *
     * Time: O(n * k)
     * Space: O(n + k)
     */
    public List<List<Integer>> combineBacktrackLuke(int n, int size) {

        /**
         * Init nums[]
         */
        int[] nums = new int[n];

        for (int i = 0; i < n; i++) {
            nums[i] = i + 1;
        }

        List<List<Integer>> combs = new ArrayList<>();
        LinkedList<Integer> comb = new LinkedList<>();

        backtrackLuke(nums, 0, size, comb, combs);

        return combs;
    }

    // Luke backtrack
    private void backtrackLuke(
            int[] nums,
            int start,
            int size,
            LinkedList<Integer> comb,
            List<List<Integer>> combs) {

        if (comb.size() == size) {
            combs.add(List.copyOf(comb));
        } else {
            for (int i = start; i < nums.length; i++) {
                int value = nums[i];
                comb.add(value);
                backtrackLuke(nums, i + 1, size, comb, combs);
                comb.removeLast();
            }
        }
    }
}
