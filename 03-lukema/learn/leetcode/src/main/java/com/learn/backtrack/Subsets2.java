package com.learn.backtrack;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.extern.log4j.Log4j2;


/**
 * LC 90
 * 
 * Since we may need to generate (2 ^ N) subsets, no solution can achieve better than exponential time complexity.
 */
@Log4j2
public class Subsets2 {

    public static void main(String[] args) {

        // int[] nums = { 1, 2, 2 };
        int[] nums = { 4, 4, 4, 1, 4 };

        Subsets2 subsets2 = new Subsets2();

        var ret = subsets2.subsetsWithDupLukeSortFirst(nums);
        log.debug("Subsets II: {}", () -> ret);
    }

    /**
     * Luke Backtrack --- Sort data first
     * 
     * Runtime: 17 ms, faster than 5.29% of Java online submissions for Subsets II.
     * Memory Usage: 47 MB, less than 5.09% of Java online submissions for Subsets II.
     * 
     * Time: O(N * 2 ^ N)
     * Space: O(N) --- Max size of "seen"
     */
    public List<List<Integer>> subsetsWithDupLukeSortFirst(final int[] nums) {

        Arrays.sort(nums);

        final List<List<Integer>> result = new ArrayList<>();
        final LinkedList<Integer> list = new LinkedList<>();
        final Set<String> seen = new HashSet<>();

        backtrack(nums, 0, result, list, seen);

        return result;
    }

    void backtrack(final int[] nums, int start, final List<List<Integer>> result, final LinkedList<Integer> list, final Set<String> seen) {

        String key = list.stream().map(String::valueOf).collect(Collectors.joining("/"));
        if (!seen.contains(key)) {
            seen.add(key);
            result.add(List.copyOf(list));
        }

        for (int i = start; i < nums.length; i++) {
            list.add(nums[i]);
            backtrack(nums, i + 1, result, list, seen);
            list.removeLast();
        }
    }

    /**
     * Luke Backtrack
     * 
     * Runtime: 17 ms, faster than 5.29% of Java online submissions for Subsets II.
     * Memory Usage: 47.2 MB, less than 5.09% of Java online submissions for Subsets II.
     * 
     * Time: O(N * 2 ^ N)
     * Space: O(N) --- Max size of "seen"
     */
    public List<List<Integer>> subsetsWithDupLukeWrong(final int[] nums) {

        final List<List<Integer>> result = new ArrayList<>();
        final LinkedList<Integer> list = new LinkedList<>();
        final Set<String> seen = new HashSet<>();

        result.add(List.copyOf(list));

        for (int i = 0; i < nums.length; i++) {
            backtrackWrong(nums, i, result, list, seen);
            list.removeLast();
        }

        return result;
    }

    void backtrackWrong(final int[] nums, int idx, final List<List<Integer>> result, final LinkedList<Integer> list, final Set<String> seen) {
        list.add(nums[idx]);

        String key = list.stream().sorted().map(String::valueOf).collect(Collectors.joining("/"));
        if (!seen.contains(key)) {
            seen.add(key);
            result.add(List.copyOf(list));
        }

        if (idx == nums.length - 1) {
            return;
        } else {
            for (int i = idx + 1; i < nums.length; i++) {
                backtrackWrong(nums, i, result, list, seen);
                list.removeLast();
            }
        }
    }
}
