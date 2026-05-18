package com.learn.backtrack;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import lombok.extern.log4j.Log4j2;


/**
 * LC 78
 * 
 * Since we may need to generate 2n2^n2n subsets, no solution can achieve better than exponential time complexity. 
 */
@Log4j2
public class Subsets {

    public static void main(String[] args) {

        int[] nums = { 1, 2, 3 };

        Subsets subsets = new Subsets();

        var ret = subsets.subsetsBacktrack(nums);
        log.debug("subsets Luke: {}", () -> ret);

        var retLexico = subsets.subsetsLexicograppic(nums);
        log.debug("subsets LC Lexicographic: {}", () -> retLexico);

    }

    /**
     * Luke: backtracking
     * 
     * Runtime: 2 ms, faster than 37.33% of Java online submissions for Subsets.
     * Memory Usage: 43.3 MB, less than 51.61% of Java online submissions for Subsets.
     * 
     * Permutations: N!
     * Combinations: CNk=N!/((N−k)!k!)
     * Subsets: 2 ^ N, since each element could be absent or present.
     * 
     * Time: O(n * 2 ^ n)
     * 
     * Space: O(n) - We are using O(N) space to maintain "list", and are modifying "list" in-place with backtracking.
     *               Note that for space complexity analysis, we do not count space that is only used for the purpose of returning
     *               output, so the output array is ignored.
     */
    public List<List<Integer>> subsetsBacktrack(int[] nums) {
        List<List<Integer>> subsets = new ArrayList<>();
        LinkedList<Integer> list = new LinkedList<>();

        backtrack(nums, 0, list, subsets);

        return subsets;
    }

    private void backtrack(int[] nums, int start, LinkedList<Integer> list, List<List<Integer>> subsets) {

        /**
         * DO NOT do this! Array index is protected in the coming for loop.
         * 
         * This will prevent last "subsets.add(List.copyOf(list));" from executing
         */
        /*
        if(start >= nums.length) {
            return;
        }
        */

        subsets.add(List.copyOf(list));

        for (int i = start; i < nums.length; i++) {
            list.add(nums[i]);
            backtrack(nums, i + 1, list, subsets);

            /**
             * Do clearing list from inside the backtracking function, where element is added to the end of the list.
             */
            list.removeLast();
        }
    }

    /**
     * LC: Lexicographic (Binary Sorted) Subsets
     * 
     * 
     * 
     * Time: O(n * 2 ^ n)
     * 
     * Space: O(n * 2 ^ n) - To keep all the subsets of length LEN, since each of LEN elements could be present or absent. 
     */
    public List<List<Integer>> subsetsLexicograppic(int[] nums) {
        List<List<Integer>> subsets = new ArrayList<>();

        int LEN = nums.length;

        for (int i = (int) Math.pow(2, LEN); i < (int) Math.pow(2, LEN + 1); ++i) {
            // generate bitmask, from 0..00 to 1..11
            String bitmask = Integer.toBinaryString(i).substring(1);

            log.debug("Integer.toBinaryString(i): {}, i: {}, bitmask: {}", Integer.toBinaryString(i), i, bitmask);

            // append subset corresponding to that bitmask
            List<Integer> list = new ArrayList<>();

            for (int k = 0; k < LEN; ++k) {
                if (bitmask.charAt(k) == '1')
                    list.add(nums[k]);
            }
            subsets.add(list);
        }

        return subsets;
    }

}
