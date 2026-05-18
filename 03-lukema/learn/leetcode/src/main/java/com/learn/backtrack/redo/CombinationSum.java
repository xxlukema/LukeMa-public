package com.learn.backtrack.redo;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import lombok.extern.log4j.Log4j2;


/**
 * LC - 39 - Combination Sum
 *
 * Medium
 *
 * Given an array of distinct integers candidates and a target integer target, return a list of all unique combinations of candidates
 * where the chosen numbers sum to target. You may return the combinations in any order.
 *
 * The same number may be chosen from candidates an unlimited number of times. Two combinations are unique if the frequency of at least
 * one of the chosen numbers is different.
 *
 * The test cases are generated such that the number of unique combinations that sum up to target is less than 150 combinations for the given input.
 *
 * Example 1:
 *
 * Input: candidates = [2,3,6,7], target = 7
 * Output: [[2,2,3],[7]]
 * Explanation:
 * 2 and 3 are candidates, and 2 + 2 + 3 = 7. Note that 2 can be used multiple times.
 * 7 is a candidate, and 7 = 7.
 * These are the only two combinations.
 *
 * Example 2:
 * Input: candidates = [2,3,5], target = 8
 * Output: [[2,2,2,2],[2,3,3],[3,5]]
 *
 * Example 3:
 * Input: candidates = [2], target = 1
 * Output: []
 *
 * Constraints:
 *     1 <= candidates.length <= 30
 *     2 <= candidates[i] <= 40
 *     All elements of candidates are distinct.
 *     1 <= target <= 500
 */
@Log4j2
public class CombinationSum {

    public static void main(String[] args) {

        final int[] candidates = { 2, 3, 6, 7 };
        final int target = 7;

        CombinationSum combinationSum = new CombinationSum();

        var ret = combinationSum.combinationSum(candidates, target);

        log.debug("ret size: {}", ret.size());

        ret.forEach(e -> {
            log.debug(e);
        });

    }

    /**
     * Luke - Brute
     *
     * Runtime: 9 ms Beats 30.5%
     * Memory: 44.9 MB Beats 69.83%
     *
     * Time: O(N ^ (T / M + 1)), where N is the number of candidates, T be the target value, and M be the minimal value among the candidates.
     * Space: O(T / M)
     */
    public List<List<Integer>> combinationSum(int[] candidates, int target) {

        final List<List<Integer>> result = new ArrayList<>();

        if (candidates == null || candidates.length == 0) {
            return result;
        }

        /**
         * Trick 3: Use `LinkedList` for partial solution, so that `LinkedList.removeLast()` can be used to Recover the Partial Result.
         */
        final LinkedList<Integer> comb = new LinkedList<>();

        return backtrack(candidates, 0, target, result, comb, candidates.length, 0);
    }

    private List<List<Integer>> backtrack(
            final int[] candidates,
            final int idx,
            final int target,
            final List<List<Integer>> result,
            final LinkedList<Integer> comb,
            final int LEN,
            final int sum) {

        // End of backtrack

        if (sum == target) {

            /**
             * make a deep copy of the current combination
             */
            // result.add(List.copyOf(comb));
            result.add(new ArrayList<Integer>(comb));
            return result;
        } else if (sum > target) {
            /**
             * exceed the scope, stop exploration.
             */
            return result;
        }

        /**
         * !!! A good example of preventing duplicates !!!
         *
         * Use "for (int i = idx; i < LEN; i++) {",
         * instead pf "for (int i = 0; i < LEN; i++) {".
         *
         * If use "for (int i = 0; i < LEN; i++) {", there will be duplicates.
         */
        /**
         * Trick 1: Pick the candidates in order to avoid redundancy.
         */
        for (int i = idx; i < LEN; i++) {

            /**
             * Add the number into the combination
             */
            /**
             * Trick 4: Add()/removeLast() in the same block of code.
             */
            comb.add(candidates[i]);

            int newSum = sum + candidates[i];

            backtrack(candidates, i, target, result, comb, LEN, newSum);

            /**
             * Trick 4: Add()/removeLast() in the same block of code.
             */
            /**
             * Trick 2: Use list.remove(Object) list.remove(Integer.valueOf(candidates[i]));
             *          Otherwise, list.remove(candidates[i]) will remove with idx (list.remove(candidates[i])),
             *          and the result will be wrong.
             */
            // list.remove(Integer.valueOf(candidates[i]));

            /**
             * backtrack, remove the number from the combination
             */
            /**
             * Trick 3: Use `LinkedList` for partial solution, so that `LinkedList.removeLast()` can be used to Recover the Partial Result.
             */
            comb.removeLast();
        }

        return result;
    }

}
