package com.learn.backtrack;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Assertions;

import lombok.extern.log4j.Log4j2;


/**
 * LC - 216 - Combination Sum III
 *
 * Medium
 *
 * Find all valid combinations of k numbers that sum up to n such that the following conditions are true:
 *
 *     Only numbers 1 through 9 are used.
 *     Each number is used at most once.
 *
 * Return a list of all possible valid combinations. The list must not contain the same combination twice, and the combinations may be returned in any order.
 *
 * Example 1:
 * Input: k = 3, n = 7
 * Output: [[1,2,4]]
 * Explanation:
 * 1 + 2 + 4 = 7
 * There are no other valid combinations.
 *
 * Example 2:
 * Input: k = 3, n = 9
 * Output: [[1,2,6],[1,3,5],[2,3,4]]
 * Explanation:
 * 1 + 2 + 6 = 9
 * 1 + 3 + 5 = 9
 * 2 + 3 + 4 = 9
 *
 * There are no other valid combinations.
 *
 * Example 3:
 * Input: k = 4, n = 1
 * Output: []
 * Explanation: There are no valid combinations.
 * Using 4 different numbers in the range [1,9], the smallest sum we can get is 1+2+3+4 = 10 and since 10 > 1, there are no valid combination.
 *
 * Constraints:
 *     2 <= k <= 9
 *     1 <= n <= 60
 */
@Log4j2
public class CombinationSumIII {

    public static void main(String[] args) {

        /**
         * Expected: [[1,2,6],[1,3,5],[2,3,4]]
         */
        final int k = 3, n = 9;

        // final int k = 5, n = 15;
        // final int k = 2, n = 6;
        // final int k = 8, n = 38;
        // final int k = 3, n = 7;
        // final int k = 8, n = 44;
        // final int k = 9, n = 45;

        CombinationSumIII combinationSumIII = new CombinationSumIII();

        var combinationSum3LukeBrute = combinationSumIII.combinationSum3LukeBrute(k, n);
        log.debug("Combination Sun III: {}", () -> combinationSum3LukeBrute);
        log.debug("Combination Sun III {} OK", () -> "combinationSum3LukeBrute");

        var combinationSum3LcBruteTrick = combinationSumIII.combinationSum3LcBruteTrick(k, n);
        Assertions.assertEquals(toString(combinationSum3LukeBrute), toString(combinationSum3LcBruteTrick));
        log.debug("Combination Sun III {} OK", () -> "combinationSum3LcBruteTrick");

        var combinationSum3LukeBruteTrick = combinationSumIII.combinationSum3LukeBruteTrick(k, n);
        Assertions.assertEquals(toString(combinationSum3LukeBrute), toString(combinationSum3LukeBruteTrick));
        log.debug("Combination Sun III {} OK", () -> "combinationSum3LukeBruteTrick");

    }

    public static String toString(List<List<Integer>> list) {
        if (list == null) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        list.forEach(e -> {
            sb.append(e.stream().map(String::valueOf).collect(Collectors.joining()));
        });

        return sb.toString();
    }

    /**
     * Luke - Brute - With Trick
     *
     * To implement the algorithm, one could literally follow the steps in the Intuition section. However, we would like to highlight a <b>key trick</b> that
     * we employed, in order to ensure the non-redundancy among the digits within a single combination, as well as the non-redundancy among the combinations.
     *
     *     The trick is that we pick the candidates in order. We treat the candidate digits as a list with order, i.e. [1, 2, 3, 4, 5, 6, 7, 8. 9]. At any
     *     given step, once we pick a digit, e.g. 6, we will not consider any digits before the chosen digit for the following steps, e.g. the candidates
     *     are reduced down to [7, 8, 9].
     *
     * Runtime: 2 ms, faster than 17.70% of Java online submissions for Combination Sum III.
     * Memory Usage: 41.6 MB, less than 61.56% of Java online submissions for Combination Sum III.
     *
     * Time: O(9! / (9 - k)!) * O(K), where O(K) is the time to copy the list, and O(9! / (9 - k)!) is all the possible combinations.
     * Space: O(N + k), where O(N) is the array space, and O(k) is the recursion depth.
     */
    public List<List<Integer>> combinationSum3LukeBruteTrick(int k, int n) {
        final List<List<Integer>> result = new ArrayList<>();
        final LinkedList<Integer> list = new LinkedList<>();

        final int[] nums = new int[9];

        for (int i = 0; i < 9; i++) {
            nums[i] = i + 1;
        }

        backtrackLukeBruteTrick(nums, 0, 0, 0, k, n, result, list);

        return result;
    }

    private void backtrackLukeBruteTrick(
            final int[] nums,
            final int startIdx,
            int sum,
            int depth,
            final int nbrElements,
            final int total,
            final List<List<Integer>> result,
            final LinkedList<Integer> list) {

        /** Start */

        // log.debug("list: {}", list);

        if (depth >= nbrElements || startIdx >= 9) {
            if (sum == total && depth == nbrElements) {
                /**
                 * Time: O(K)
                 */
                result.add(new ArrayList<>(list));
            }
            return;
        } else {
            for (int i = startIdx; i < 9; i++) {
                sum += nums[i];
                depth++;
                list.add(nums[i]);

                backtrackLukeBruteTrick(nums, i + 1, sum, depth, nbrElements, total, result, list);

                list.removeLast();
                depth--;
                sum -= nums[i];
            }
        }
    }

    /**
     * LC - Brute
     */

    public List<List<Integer>> combinationSum3LcBruteTrick(int k, int n) {
        List<List<Integer>> results = new ArrayList<List<Integer>>();
        LinkedList<Integer> comb = new LinkedList<Integer>();

        backtrackLcBruteTrick(n, k, comb, 0, results);

        return results;
    }

    protected void backtrackLcBruteTrick(
            int remain,
            int k,
            LinkedList<Integer> comb,
            int next_start,
            List<List<Integer>> results) {

        if (remain == 0 && comb.size() == k) {
            // Note: it's important to make a deep copy here,
            // Otherwise the combination would be reverted in other branch of backtracking.
            results.add(new ArrayList<Integer>(comb));
            return;
        } else if (remain < 0 || comb.size() == k) {
            // Exceed the scope, no need to explore further.
            return;
        }

        // Iterate through the reduced list of candidates.
        for (int i = next_start; i < 9; ++i) {
            comb.add(i + 1);
            this.backtrackLcBruteTrick(remain - i - 1, k, comb, i + 1, results);
            comb.removeLast();
        }
    }

    /**
     * Luke - Brute
     *
     * Time Limit Exceeded
     *
     * Time: O(N) * O(N * 2 ^ k)
     * Space: O(N + k)
     */
    public List<List<Integer>> combinationSum3LukeBrute(int k, int n) {
        final List<List<Integer>> result = new ArrayList<>();
        Set<Integer> set = new HashSet<>();

        final Set<Integer> nums = new HashSet<>();

        final Set<String> found = new HashSet<>();

        int min = k * (1 + k) / 2;
        int max = k * (9 + (9 - k + 1)) / 2;

        if (n > max || n < min) {
            return result;
        }

        int maxPlus1 = n - k + 1;
        for (int i = 1; i <= 9 && i <= maxPlus1; i++) {
            nums.add(i);
        }

        int maxElement = Math.min(9, maxPlus1 - 1);

        if (n == max) {
            for (int i = maxElement, nn = maxElement - k; i > nn; i--) {
                set.add(i);
            }

            result.add(List.copyOf(set));
            return result;
        }

        // log.debug("nums: {}", nums);

        set.clear();

        for (Integer i : nums) {
            backtrackLukeBrute(nums, i, 0, 0, k, n, result, set, found);
        }

        return result;
    }

    private void backtrackLukeBrute(
            final Set<Integer> nums,
            int currVal,
            int count,
            int sum,
            final int k,
            final int n,
            final List<List<Integer>> result,
            final Set<Integer> set,
            final Set<String> found) {

        /** Start */
        if (count == k) {
            if (sum == n) {
                /**
                 * Time: O(k * log(k))
                 * Space: O(k)
                 */
                String str = set.stream().sorted().map(String::valueOf).collect(Collectors.joining(""));
                if (!found.contains(str)) {
                    found.add(str);
                    result.add(List.copyOf(set));
                }
            }
            return;
        } else {
            sum += currVal;
            set.add(currVal);

            final Set<Integer> reducedSetNums = nums.stream().collect(Collectors.toSet());
            reducedSetNums.remove(currVal);

            // log.debug("sum: {}, set.size: {}", sum, set.size());

            /**
             * Time: O(N * 2 ^ k)
             */
            for (int i : reducedSetNums) {
                backtrackLukeBrute(reducedSetNums, i, count + 1, sum, k, n, result, set, found);
            }

            set.remove(currVal);
        }
    }
}
