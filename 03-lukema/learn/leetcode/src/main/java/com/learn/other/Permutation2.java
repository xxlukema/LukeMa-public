package com.learn.other;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import lombok.extern.log4j.Log4j2;


@Log4j2
public class Permutation2 {

    public static void main(String[] args) {
        int[] nums = { 1, 2, 3, 1 };

        Permutation2 permutation2 = new Permutation2();

        // List<List<Integer>> ret = permutations.permuteLuke(nums);

        List<List<Integer>> ret = permutation2.permuteLuke(nums);

        ret.forEach(elm -> {
            log.info("Permutation: {}", () -> elm);
        });
    }

    /**
     * Runtime: 482 ms, faster than 5.48% of Java online submissions for Permutations II.
     * Memory Usage: 139 MB, less than 5.04% of Java online submissions for Permutations II.
     */
    public List<List<Integer>> permuteLuke(int[] nums) {

        List<List<Integer>> results = new ArrayList<>();

        if (nums.length == 1) {
            results.add(Arrays.stream(nums).boxed().collect(Collectors.toList()));
        } else {
            boolean[] contains = new boolean[nums.length];
            LinkedList<Node> perm = new LinkedList<>();

            permutaLuke(nums, results, perm, contains);
        }

        /**
         * Unique
         */
        Set<String> seen = ConcurrentHashMap.newKeySet();
        List<List<Integer>> results2 = new ArrayList<>();
        results.forEach(list -> {
            String key = list.stream().map(e -> String.valueOf(e)).collect(Collectors.joining("/"));
            if (!seen.contains(key)) {
                seen.add(key);
                results2.add(list);
            }
        });

        return results2;
    }

    public void permutaLuke(int[] nums, List<List<Integer>> results, LinkedList<Node> perm, boolean[] contains) {
        if (perm.size() == nums.length) {
            results.add(perm.stream().map(e -> e.val).toList());

            // log.debug("perm 1: {}, contains: {}", () -> perm.stream().map(e -> e.val).toList(), () -> contains);

            /**
             * Extramely Important: Do not call "backtrack()" here. Instead, call retrun here to break the recursion.
             */
            return;
        } else {
            for (int idx = 0; idx < nums.length; idx++) {
                if (!contains[idx]) {
                    perm.add(new Node(idx, nums[idx]));
                    contains[idx] = true;

                    // log.debug("perm 2: {}, contains: {}", () -> perm.stream().map(e -> e.val).toList(), () -> contains);

                    permutaLuke(nums, results, perm, contains);
                    backtrackLuke(perm, contains);
                }
            }
        }
    }

    /**
     * These is no need to check "perm.isEmpty()". When exception of removing last from empty LinkedList happens,
     * it means somewhere is calling "backtrackLuke()" unnecessarily.
     */
    public void backtrackLuke(LinkedList<Node> perm, boolean[] contains) {
        /**
         * These is no need to check "perm.isEmpty()". When exception of removing last from empty LinkedList happens,
         * it means somewhere is calling "backtrackLuke()" unnecessarily.
         */
        Node node = perm.removeLast();
        contains[node.idx] = false;
        // log.debug("perm 3: {}, contains: {}", () -> perm.stream().map(e -> e.val).toList(), () -> contains);
    }

    record Node(int idx, int val) {
        Node(int idx, int val) {
            this.idx = idx;
            this.val = val;
        }
    }
}
