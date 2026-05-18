package com.learn.other;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.extern.log4j.Log4j2;


/**
 * LC-254 Factor Combinations
 *
 * Medium
 *
 * Numbers can be regarded as the product of their factors.
 *
 *     For example, 8 = 2 x 2 x 2 = 2 x 4.
 *
 * Given an integer n, return all possible combinations of its factors. You may return the answer in any order.
 *
 * Note that the factors should be in the range [2, n - 1].
 *
 * Example 1:
 * Input: n = 1
 * Output: []
 *
 * Example 2:
 * Input: n = 12
 * Output: [[2,6],[3,4],[2,2,3]]
 *
 * Example 3:
 * Input: n = 37
 * Output: []
 *
 * Constraints:
 *     1 <= n <= 10 ^ 7
 */
@Log4j2
public class FactorCombinations {

    public static void main(String[] args) {

        /**
         * Output:
         * [[2,32],[2,2,16],[2,2,2,8],[2,2,2,2,4],[2,2,2,2,2,2],[2,2,4,4],[2,4,8],[4,16],[8,8]]
         * Expected:
         * [[2,32],[4,16],[2,2,16],[8,8],[2,4,8],[2,2,2,8],[4,4,4],[2,2,4,4],[2,2,2,2,4],[2,2,2,2,2,2]]
         * Diff:
         * [[4,4,4]]
         */
        final int n = 64;

        FactorCombinations factorCombinations = new FactorCombinations();

        var ret = factorCombinations.getFactorsLuke(n);

        // ret = factorCombinations.removeDuplicates(ret);

        log.debug("Factor Combinations: {}", ret);
        log.debug("Factor Combinations {} OK", () -> "ret");

    }

    /**
     * Recite from LC - backtracking with iteration start point control
     *
     * Runtime: 7 ms Beats 83.32%
     * Memory: 53.9 MB Beats 51.37%
     *
     * Time: O(sqrt(N)) * O(sqrt(sqrt(N))) * ...
     * Space: O(sqrt(N))
     */
    public List<List<Integer>> getFactorsLuke(int n) {

        final List<List<Integer>> result = new ArrayList<>();

        if (n < 2) {
            return result;
        }

        final LinkedList<Integer> llist = new LinkedList<>();

        /**
         * kick start
         */
        backtrackLuke(result, llist, 2, n);

        return result;
    }

    private void backtrackLuke(final List<List<Integer>> result, final LinkedList<Integer> llist, final int start, final int end) {
        /**
         * boundary condition
         */
        if (!llist.isEmpty()) {
            llist.add(end);
            result.add(new ArrayList<>(llist));
            llist.removeLast();
        }

        int max = (int) Math.sqrt(end);
        for (int i = start; i <= max; i++) {
            if (end % i != 0) {
                continue;
            }

            llist.add(i);
            backtrackLuke(result, llist, i, end / i);
            llist.removeLast();
        }
    }

    /**
     * Time Limit Exceeded for n = 10 ^ 7
     */
    public List<List<Integer>> getFactorsLukeWrong(int n) {
        List<List<Integer>> result = new ArrayList<>();
        if (n < 2) {
            return result;
        }

        final int upper = (int) Math.sqrt(n);

        for (int i = 2; i <= upper; i++) {

            if (n % i != 0) {
                continue;
            }

            List<Integer> cur = new ArrayList<>();
            cur.add(i);
            cur.add(n / i);

            result.add(cur);

            List<List<Integer>> result1 = getFactorsLuke(i);
            result1.add(List.of(i));

            // result1 = removeDuplicates(result1);

            List<List<Integer>> result2 = getFactorsLuke(n / i);
            result2.add(List.of(n / i));

            // result2 = removeDuplicates(result2);

            // log.debug("i: {}, result1: {}. n / i: {}, result2: {}", i, result1, n / i, result2);

            for (List<Integer> list1 : result1) {
                for (List<Integer> list2 : result2) {
                    List<Integer> list = new ArrayList<>();
                    list.addAll(List.copyOf(list1));
                    list.addAll(List.copyOf(list2));
                    result.add(list);
                }
            }
        }

        return removeDuplicates(result);
    }

    List<List<Integer>> removeDuplicates(final List<List<Integer>> result) {
        Set<String> seen = new HashSet<>();

        return result.stream().filter(list -> {
            String key = list.stream().sorted().map(String::valueOf).collect(Collectors.joining("-"));
            if (seen.contains(key)) {
                return false;
            } else {
                seen.add(key);
                return true;
            }
        }).collect(Collectors.toList());
    }

    /**
     * LC - backtrack with control iteration start point
     *
     * Runtime: 7 ms Beats 83.32%
     * Memory: 53.9 MB Beats 51.37%
     *
     * Time: O(sqrt(N)) * O(sqrt(sqrt(N))) * ...
     * Space: O(sqrt(N))
     */
    public List<List<Integer>> getFactorsLc(int n) {
        final List<List<Integer>> result = new ArrayList<>();

        if (n < 2) {
            return result;
        }

        final LinkedList<Integer> llist = new LinkedList<>();

        backtrackLc(result, llist, 2, n);
        return result;
    }

    public void backtrackLc(final List<List<Integer>> result, final LinkedList<Integer> llist, final int start, final int end) {

        /**
         * 1. boundary condition
         */
        if (llist.size() != 0) {
            llist.add(end);
            result.add(new ArrayList<>(llist));
            llist.removeLast();
        }

        for (int i = start, max = (int) Math.sqrt(end); i <= max; ++i) {
            if (end % i != 0) {
                continue;
            }
            llist.add(i);
            backtrackLc(result, llist, i, end / i);
            llist.removeLast();
        }
    }

}
