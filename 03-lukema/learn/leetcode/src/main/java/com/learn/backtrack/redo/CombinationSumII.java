package com.learn.backtrack.redo;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import lombok.extern.log4j.Log4j2;


/**
 * LC - 40 - Combination Sum II
 *
 * Medium
 *
 * Given a collection of candidate numbers (candidates) and a target number (target), find all unique combinations in candidates where the candidate numbers sum to target.
 *
 * Each number in candidates may only be used once in the combination.
 *
 * Note: The solution set must not contain duplicate combinations.
 *
 * Example 1:
 * Input: candidates = [10,1,2,7,6,1,5], target = 8
 * Output:
 * [
 * [1,1,6],
 * [1,2,5],
 * [1,7],
 * [2,6]
 * ]
 *
 * Example 2:
 * Input: candidates = [2,5,2,1,2], target = 5
 * Output:
 * [
 * [1,2,2],
 * [5]
 * ]
 *
 * Constraints:
 *     1 <= candidates.length <= 100
 *     1 <= candidates[i] <= 50
 *     1 <= target <= 30
 */
@Log4j2
public class CombinationSumII {

    public static void main(String[] args) {

        // final int[] candidates = { 10, 1, 2, 1, 7, 1, 6, 1, 5 };
        // final int target = 8;

        final int[] candidates = { 1, 2, 4, 2, 1, 1, 2, 2, 2, 2, 1 };
        final int target = 6;

        CombinationSumII combinationSumII = new CombinationSumII();

        /*
        var ret = combinationSumII.combinationSum2Luke(candidates, target);

        log.debug("ret size: {}", ret.size());

        ret.forEach(e -> {
            log.debug(e);
        });

        var combinationSum2Lc = combinationSumII.combinationSum2Lc(candidates, target);

        log.debug("combinationSum2Lc size: {}", combinationSum2Lc.size());

        combinationSum2Lc.forEach(e -> {
            log.debug(e);
        });
        */

        var combinationSum2LcCounterNoSorting = combinationSumII.combinationSum2LcCounterNoSorting(candidates, target);

        log.debug("combinationSum2LcCounterNoSorting size: {}", combinationSum2LcCounterNoSorting.size());

        combinationSum2LcCounterNoSorting.forEach(e -> {
            log.debug(e);
        });

        var combinationSum2LcOriginal = combinationSumII.combinationSum2LcCounterOriginal(candidates, target);

        log.debug("combinationSum2LcOriginal size: {}", combinationSum2LcOriginal.size());

        combinationSum2LcOriginal.forEach(e -> {
            log.debug(e);
        });

    }

    /**
     * LC - Counter - Original - Use List to track order and frequency
     *
     * Runtime: 21 ms Beats 5.45%
     * Memory: 51.5 MB Beats 5.2%
     *
     * Time: O(2 ^ N)
     * Space: O(N)
     */
    public List<List<Integer>> combinationSum2LcCounterOriginal(int[] candidates, int target) {
        // container to hold the final combinations
        List<List<Integer>> results = new ArrayList<>();
        LinkedList<Integer> comb = new LinkedList<>();

        Map<Integer, Integer> counterMap = new HashMap<>();
        for (int candidate : candidates) {
            if (counterMap.containsKey(candidate)) {
                counterMap.put(candidate, counterMap.get(candidate) + 1);
            } else {
                counterMap.put(candidate, 1);
            }
        }

        // convert the counter table to a list of (num, count) tuples
        List<int[]> counterList = new ArrayList<>();
        counterMap.forEach((key, value) -> {
            counterList.add(new int[] { key, value });
        });

        backtrackCounterOriginal(comb, target, 0, counterList, results);
        return results;
    }

    private void backtrackCounterOriginal(
            LinkedList<Integer> comb,
            int remain,
            int idx,
            List<int[]> counter,
            List<List<Integer>> results) {

        if (remain <= 0) {
            if (remain == 0) {
                // make a deep copy of the current combination.
                results.add(new ArrayList<>(comb));
            }
            return;
        }

        for (int i = idx, counterSize = counter.size(); i < counterSize; ++i) {
            int[] entry = counter.get(i);
            Integer candidate = entry[0], freq = entry[1];

            if (freq <= 0) {
                continue;
            }

            // add a new element to the current combination
            comb.addLast(candidate);
            counter.set(i, new int[] { candidate, freq - 1 });

            // continue the exploration with the updated combination
            backtrackCounterOriginal(comb, remain - candidate, i, counter, results);

            // backtrack the changes, so that we can try another candidate
            counter.set(i, new int[] { candidate, freq });
            comb.removeLast();
        }
    }

    /**
     * LC - Counter - No sorting - Use LinkedHashMap track order and frequency
     *
     * Runtime: 19 ms Beats 6.44%
     * Memory: 45.6 MB Beats 10.90%
     *
     * Time: O(2 ^ N)
     * Space: O(N)
     */
    public List<List<Integer>> combinationSum2LcCounterNoSorting(int[] candidates, int target) {
        // container to hold the final combinations
        List<List<Integer>> results = new ArrayList<>();
        LinkedList<Integer> comb = new LinkedList<>();

        /**
         * Trick 1: Use `LinkedHashMap` to keep the order of add
         */
        final Map<Integer, Integer> counterMap = new LinkedHashMap<>();
        for (int candidate : candidates) {
            if (counterMap.containsKey(candidate)) {
                counterMap.put(candidate, counterMap.get(candidate) + 1);
            } else {
                counterMap.put(candidate, 1);
            }
        }

        // convert the counter table to a list of (num, count) tuples
        List<int[]> counterList = new ArrayList<>();
        counterMap.forEach((key, value) -> {
            counterList.add(new int[] { key, value });
        });

        // log.debug("counterList: {}", counterList);

        backtrackLcCounterNoSorting(comb, target, 0, counterMap, results);
        return results;
    }

    private void backtrackLcCounterNoSorting(
            LinkedList<Integer> comb,
            int remain,
            int idx,
            final Map<Integer, Integer> counterMap,
            List<List<Integer>> results) {

        if (remain <= 0) {
            if (remain == 0) {
                // make a deep copy of the current combination.
                results.add(new ArrayList<Integer>(comb));
            }
            return;
        }

        /**
         * Iterate through counterList
         */
        int skipIdx = 0;

        for (Integer key : counterMap.keySet()) {

            skipIdx++;

            if (skipIdx < idx) {
                continue;
            }

            int freq = counterMap.get(key);

            if (freq <= 0) {
                continue;
            }

            // add a new element to the current combination
            comb.addLast(key);

            counterMap.put(key, freq - 1);

            // continue the exploration with the updated combination
            backtrackLcCounterNoSorting(comb, remain - key, skipIdx, counterMap, results);

            // backtrack the changes, so that we can try another candidate
            counterMap.put(key, freq);
            comb.removeLast();
        }
    }

    /**
     * LC -
     *
     * Runtime: 4 ms Beats 92.4%
     * Memory: 44.8 MB Beats 12.43%
     *
     * Time: O(2 ^ N)
     * Space: O(N)
     */
    public List<List<Integer>> combinationSum2LcFast(int[] candidates, int target) {
        List<List<Integer>> results = new ArrayList<>();
        LinkedList<Integer> comb = new LinkedList<>();

        Arrays.sort(candidates);

        backtrackLcFast(candidates, comb, target, 0, results);
        return results;
    }

    private void backtrackLcFast(
            int[] candidates,
            LinkedList<Integer> comb,
            Integer remain,
            Integer curr,
            List<List<Integer>> results) {

        // Start
        if (remain == 0) {
            // copy the current combination to the final list.
            results.add(new ArrayList<Integer>(comb));
            return;
        }

        for (int nextCurr = curr; nextCurr < candidates.length; ++nextCurr) {
            if (nextCurr > curr && candidates[nextCurr] == candidates[nextCurr - 1]) {
                continue;
            }

            Integer pick = candidates[nextCurr];

            /**
             * Optimization: early stopping
             *
             * Trick: break here. Since the array is sorted, elements after this will exceed the target.
             */
            if (remain - pick < 0) {
                break;
            }

            comb.addLast(pick);
            backtrackLcFast(candidates, comb, remain - pick, nextCurr + 1, results);
            comb.removeLast();
        }
    }

    /**
     * Luke - Brute
     *
     * Time Limit Exceeded
     *
     * Without Optimization:
     *
     * Runtime: 20 ms Beats 5.87%
     * Memory: 47.3 MB Beats 5.59%
     *
     * With Optiomization:
     *
     * Runtime: 4 ms Beats 92.13%
     * Memory: 44.4 MB Beats 18.22%
     *
     * Time: O(N ^ (T / M + 1)), where N is the number of candidates, T be the target value, and M be the minimal value among the candidates.
     * Space: O(T / M)
     *
     */
    public List<List<Integer>> combinationSum2LukeOptimized(int[] candidates, int target) {
        final List<List<Integer>> result = new ArrayList<>();

        if (candidates == null || candidates.length == 0) {
            return result;
        }

        Arrays.sort(candidates);

        final LinkedList<Integer> comb = new LinkedList<>();
        final int LEN = candidates.length;
        final Set<String> seen = new HashSet<>();

        return backtrackLukeOptimized(candidates, 0, target, result, comb, 0, LEN, seen);
    }

    private List<List<Integer>> backtrackLukeOptimized(
            final int[] candidates,
            final int idx,
            final int target,
            final List<List<Integer>> result,
            final LinkedList<Integer> comb,
            final int sum,
            final int LEN,
            final Set<String> seen) {

        // Start
        if (idx > LEN) {
            return result;
        }

        if (sum == target) {
            /*
            String key = comb.stream().sorted().map(String::valueOf).collect(Collectors.joining("-"));
            if (!seen.contains(key)) {
                seen.add(key);
                result.add(new ArrayList<>(comb));
            }
            */
            result.add(new ArrayList<>(comb));
            return result;
        } else if (sum > target) {
            return result;
        } else {

            for (int i = idx; i < LEN; i++) {

                /**
                 * Skip duplicate numbers ???
                 */
                if (i > idx && candidates[i] == candidates[i - 1]) {
                    continue;
                }

                int newSum = sum + candidates[i];

                /**
                 * Optimization: early stopping
                 *
                 * Trick: break here. Since the array is sorted, elements after this will exceed the target.
                 */
                if (newSum > target) {
                    break;
                }

                comb.add(candidates[i]);

                backtrackLukeOptimized(candidates, i + 1, target, result, comb, newSum, LEN, seen);

                comb.removeLast();
            }

            return result;
        }
    }
}
