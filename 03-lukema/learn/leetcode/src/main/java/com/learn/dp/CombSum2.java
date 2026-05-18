package com.learn.dp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class CombSum2 {

    public static void main(String[] args) {
        // int[] nums = { 2, 3, 5 };
        // int target = 8;

        // int[] nums = { 2, 5, 2, 1, 2 };
        // int target = 5;

        // int[] nums = { 10, 1, 2, 7, 6, 1, 5 };
        // int target = 8;

        int[] nums = { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
                1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
                1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 };
        int target = 30;

        /*
        int[] nums = { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 };
        int target = 10;
        */

        CombSum2 comSum2 = new CombSum2();

        List<List<Integer>> result = comSum2.combinationSum2(nums, target);

        log.info("result: {}", result);
    }

    public List<List<Integer>> combinationSum2(int[] nums, int target) {
        // container to hold the final combinations
        List<List<Integer>> results = new ArrayList<>();
        LinkedList<Integer> comb = new LinkedList<>();

        HashMap<Integer, Integer> counter = new HashMap<>();
        for (int candidate : nums) {
            if (counter.containsKey(candidate))
                counter.put(candidate, counter.get(candidate) + 1);
            else
                counter.put(candidate, 1);
        }

        // convert the counter table to a list of (num, count) tuples
        List<int[]> counterList = new ArrayList<>();
        counter.forEach((key, value) -> {
            counterList.add(new int[] { key, value });
        });

        log.debug("counter: {}", ()->counter);
        log.debug("counterList: {}", ()->counterList);

        backtrack(comb, target, 0, counterList, results);
        return results;
    }

    private void backtrack(LinkedList<Integer> comb, int remain, int curr, List<int[]> counter,
            List<List<Integer>> results) {

        if (remain == 0) {
            // make a deep copy of the current combination.
            results.add(new ArrayList<Integer>(comb));
            return;
        }

        if (remain <= 0) {
            return;
        }

        for (int nextCurr = curr; nextCurr < counter.size(); ++nextCurr) {
            int[] entry = counter.get(nextCurr);
            Integer candidate = entry[0], freq = entry[1];

            if (freq <= 0) {
                continue;
            }

            // add a new element to the current combination
            comb.addLast(candidate);
            counter.set(nextCurr, new int[] { candidate, freq - 1 });

            // continue the exploration with the updated combination
            backtrack(comb, remain - candidate, nextCurr, counter, results);

            // backtrack the changes, so that we can try another candidate
            counter.set(nextCurr, new int[] { candidate, freq });
            comb.removeLast();
        }
    }

    public List<List<Integer>> combinationSum2Luke(int[] nums, int target) {

        List<List<Integer>> result = new ArrayList<>();
        LinkedList<Integer> subsolution = new LinkedList<>();

        backtrack2Luke(nums, target, 0, result, subsolution);

        Comparator<List<Integer>> comparator = (List<Integer> a, List<Integer> b) -> {

            Collections.sort(a);
            Collections.sort(b);

            String s1 = a.stream().map(String::valueOf).reduce("", String::concat);
            String s2 = b.stream().map(String::valueOf).reduce("", String::concat);

            return s1.hashCode() - s2.hashCode();
        };

        result = result.stream().sorted(comparator).distinct().collect(Collectors.toList());

        return result;
    }

    private void backtrack2Luke(int[] nums, int remain, int start, List<List<Integer>> result,
            LinkedList<Integer> subsolution) {

        if (remain == 0) {
            result.add(new ArrayList<>(subsolution));
            log.debug(result.size());
            return;
        }

        if (remain < 0) {
            return;
        }

        for (int i = start; i < nums.length; i++) {
            subsolution.add(nums[i]);
            backtrack2Luke(nums, remain - nums[i], i + 1, result, subsolution);
            subsolution.removeLast();
        }
    }

}
