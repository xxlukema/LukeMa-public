package com.learn.backtrack;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.extern.log4j.Log4j2;


@Log4j2
public class PermutationSequence {

    public static void main(String[] args) {

        /**
         * 1 <= n <= 9
         * 1 <= k <= n!
         */
        // final int n = 3;
        // final int pos = 3;
        // final int n = 9;
        // final int pos = 78494;

        final int n = 4;
        final int pos = 9;

        PermutationSequence permutationSequence = new PermutationSequence();
        var result = permutationSequence.getPermutation(n, pos);

        log.info("{}th element of permutation({}): {}", () -> pos, () -> n, () -> result);
    }

    int counter = 0;
    static int end = 0;

    /**
     * Luke backtrack
     * 
     * (1) Time Limit Exceeded
     * 
     * (2) With improvement of (A) only create the end string, and 
     *                         (B) stop further backtracking when end is reached:
     * 
     *     Runtime: 488 ms, faster than 6.99% of Java online submissions for Permutation Sequence.
     *     Memory Usage: 42.6 MB, less than 9.09% of Java online submissions for Permutation Sequence.
     * 
     * Time: O(n ^ 2)
     * Space: O(n!)
     * 
     */
    public String getPermutation(int n, int pos) {

        var result = new ArrayList<String>();
        end = pos;

        for (int col = 1; col <= n; col++) {
            boolean[] inuse = new boolean[n + 1];
            LinkedList<Integer> perm = new LinkedList<>();
            backtrack(n, col, perm, result, inuse);
        }

        // result.forEach(e -> log.debug(e));

        // return result.get(pos - 1);
        return result.get(0);
    }

    public void backtrack(int n, int col, LinkedList<Integer> perm, List<String> result, boolean[] inuse) {
        inuse[col] = true;
        perm.add(col);

        /**
         * Special: Stop at end position
         */
        if (counter == end) {
            return;
        }

        if (perm.size() == n) {

            /**
             * Special: Stop at end position
             */
            if (++counter == end) {
                result.add(perm.stream().map(e -> String.valueOf(e)).collect(Collectors.joining()));
            }
            return;
        } else {
            for (int idx = 1; idx <= n; idx++) {
                if (!inuse[idx]) {
                    backtrack(n, idx, perm, result, inuse);
                    inuse[idx] = false;
                    perm.removeLast();
                }
            }
        }
    }

    /**
     * LC
     * 
     * Time: O(n ^ 2)
     * Space: O(n)
     */
    public String getPermutationLC(int n, int k) {
        int[] factorials = new int[n];
        List<Integer> nums = new ArrayList<>() {
            {
                add(1);
            }
        };

        factorials[0] = 1;
        for (int i = 1; i < n; ++i) {
            // generate factorial system bases 0!, 1!, ..., (n - 1)!
            factorials[i] = factorials[i - 1] * i;
            // generate nums 1, 2, ..., n
            nums.add(i + 1);
        }

        // fit k in the interval 0 ... (n! - 1)
        --k;

        // compute factorial representation of k
        StringBuilder sb = new StringBuilder();
        for (int i = n - 1; i > -1; --i) {
            int idx = k / factorials[i];
            k -= idx * factorials[i];

            sb.append(nums.get(idx));
            nums.remove(idx);
        }
        return sb.toString();
    }
}
