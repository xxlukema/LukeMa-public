package com.learn.other;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.Assertions;

import lombok.extern.log4j.Log4j2;


/**
 * LC - 220 - Contains Duplicate III
 *
 * Hard
 *
 * You are given an integer array nums and two integers indexDiff and valueDiff.
 *
 * Find a pair of indices (i, j) such that:
 *     i != j,
 *     abs(i - j) <= indexDiff.
 *     abs(nums[i] - nums[j]) <= valueDiff, and
 *
 * Return true if such pair exists or false otherwise.
 *
 * Example 1:
 * Input: nums = [1,2,3,1], indexDiff = 3, valueDiff = 0
 * Output: true
 * Explanation: We can choose (i, j) = (0, 3).
 * We satisfy the three conditions:
 * i != j --> 0 != 3
 * abs(i - j) <= indexDiff --> abs(0 - 3) <= 3
 * abs(nums[i] - nums[j]) <= valueDiff --> abs(1 - 1) <= 0
 *
 * Example 2:
 * Input: nums = [1,5,9,1,5,9], indexDiff = 2, valueDiff = 3
 * Output: false
 * Explanation: After trying all the possible pairs (i, j), we cannot satisfy the three conditions, so we return false.
 *
 * Constraints:
 *     2 <= nums.length <= 105
 *     -109 <= nums[i] <= 109
 *     1 <= indexDiff <= nums.length
 *     0 <= valueDiff <= 109
 */
@Log4j2
public class ContainsDuplicateIII {

    public static void main(String[] args) {

        /**
         * Expected: true
         */
        final int[] nums = { 1, 2, 3, 1 };
        final int indexDiff = 3, valueDiff = 0;

        /**
         * Expected: true
         */
        // final int[] nums = { 2, 2 };
        // final int indexDiff = 2, valueDiff = 0;

        /**
         * Expected: false
         */
        // final int[] nums = { 1, 5, 9, 1, 5, 9 };
        // final int indexDiff = 2, valueDiff = 3;

        ContainsDuplicateIII containsDuplicateIII = new ContainsDuplicateIII();

        var containsNearbyAlmostDuplicateLukeRadix = containsDuplicateIII.containsNearbyAlmostDuplicateLukeRadix(nums, indexDiff, valueDiff);
        log.debug("Contains Duplicate III: {}", () -> containsNearbyAlmostDuplicateLukeRadix);
        log.debug("Contains Duplicate III {} OK", () -> "containsNearbyAlmostDuplicateLukeRadix");

        var containsNearbyAlmostDuplicateLcTreeSet = containsDuplicateIII.containsNearbyAlmostDuplicateLcTreeSet(nums, indexDiff, valueDiff);
        Assertions.assertEquals(containsNearbyAlmostDuplicateLukeRadix, containsNearbyAlmostDuplicateLcTreeSet);
        log.debug("Contains Duplicate III {} OK", () -> "containsNearbyAlmostDuplicateLcTreeSet");

        var containsNearbyAlmostDuplicateLcBucket = containsDuplicateIII.containsNearbyAlmostDuplicateLcBucket(nums, indexDiff, valueDiff);
        Assertions.assertEquals(containsNearbyAlmostDuplicateLukeRadix, containsNearbyAlmostDuplicateLcBucket);
        log.debug("Contains Duplicate III {} OK", () -> "containsNearbyAlmostDuplicateLcBucket");

    }

    /**
     * LC - BucketSort
     *
     *
     * Time: O(N)
     * Space: O(min(N, indexDiff))
     */
    public boolean containsNearbyAlmostDuplicateLcBucket(final int[] nums, final int indexDiff, final int valueDiff) {
        if (nums == null || nums.length < 2) {
            return false;
        }

        if (valueDiff < 0) {
            return false;
        }

        final Map<Integer, Integer> map = new HashMap<>();

        /**
         * Trick 1: "valueDiff + 1" to avoid divide by zero.
         */
        final int w = valueDiff + 1;

        for (int i = 0; i < nums.length; ++i) {
            int m = getID(nums[i], w);

            // check if bucket m is empty, each bucket may contain at most one element
            /**
             * Trick 2: If there is alreay one in the bucket, it is a match.
             */
            if (map.containsKey(m)) {
                return true;
            }
            // check the neighbor buckets for almost duplicate
            if (map.containsKey(m - 1) && Math.abs(nums[i] - map.get(m - 1)) < w) {
                return true;
            }
            if (map.containsKey(m + 1) && Math.abs(nums[i] - map.get(m + 1)) < w) {
                return true;
            }

            // now bucket m is empty and no almost duplicate in neighbor buckets
            map.put(m, nums[i]);

            if (i >= indexDiff) {
                /**
                 * Trick 4: remove the most left element from map to maintain the size in indexDiff.
                 */
                map.remove(getID(nums[i - indexDiff], w));
            }
        }

        return false;
    }

    // Get the ID of the bucket from element value x and bucket width w
    // In Java, `-3 / 5 = 0` and but we need `-3 / 5 = -1`.
    private int getID(int x, int valueDiff) {
        /**
         * Trick 3: Use valueDiff as divisor
         */
        return x < 0 ? (x + 1) / valueDiff - 1 : x / valueDiff;
    }

    /**
     * LC - TreeSet
     *
     * Runtime: 105 ms, faster than 17.50% of Java online submissions for Contains Duplicate III.
     * Memory Usage: 81.1 MB, less than 7.89% of Java online submissions for Contains Duplicate III.
     *
     * Time: O(N) * O(log(N))
     * O(min(N, indexDiff))
     */
    public boolean containsNearbyAlmostDuplicateLcTreeSet(final int[] nums, final int indexDiff, final int valueDiff) {
        if (nums == null || nums.length < 2) {
            return false;
        }

        TreeSet<Integer> set = new TreeSet<>();

        for (int i = 0, n = nums.length; i < n; i++) {
            int num = nums[i];
            Integer celling = set.ceiling(num);
            if (celling != null && celling.intValue() - num <= valueDiff) {

                log.debug("celling: {}, num: {}", celling, num);

                return true;
            }
            Integer floor = set.floor(num);
            if (floor != null && num - floor <= valueDiff) {

                log.debug("floor: {}, num: {}", floor, num);

                return true;
            }

            log.debug("num: {}, celling: {}, floor: {}", num, celling, floor);

            /**
             * Use indexDiff instead of (indexDiff + 1) because the above celling() and floor() calls has the
             * same effect as if the new value has already been added to the set.
             */
            if (set.size() == indexDiff) {
                set.remove(nums[i - indexDiff]);
            }

            set.add(num);
        }

        return false;
    }

    /**
     * Luke - RadixSort
     *
     * Time Limit Exceeded
     *
     * Time: O(N) * O(indexDiff)
     * Space: O(indexDiff)
     */
    public boolean containsNearbyAlmostDuplicateLukeRadix(final int[] nums, final int indexDiff, final int valueDiff) {
        if (nums == null || nums.length < 2) {
            return false;
        }

        final int N = nums.length;

        if (N <= indexDiff) {
            int tmpMinDiff = minValueDiff(nums, 0, N - 1);
            if (tmpMinDiff <= valueDiff) {
                return true;
            } else {
                return false;
            }
        }

        for (int i = 0; i < N - indexDiff; i++) {
            int tmpMinDiff = minValueDiff(nums, i, i + indexDiff);
            if (tmpMinDiff <= valueDiff) {
                return true;
            }
        }

        return false;
    }

    /**
     * Time: O(LEN)
     * Space: O(LEN)
     */
    private int minValueDiff(final int[] nums, final int start, final int endInclusive) {

        final int[] tmpNums = radixSort(nums, start, endInclusive);

        int minDiff = Integer.MAX_VALUE;
        for (int i = 0, n = tmpNums.length - 1; i < n; i++) {
            minDiff = Math.min(minDiff, tmpNums[i + 1] - tmpNums[i]);
        }

        // log.debug("minDiff: {}, tmpNums: {}", minDiff, tmpNums);

        return minDiff;
    }

    /**
     * Time: O(LEN)
     * Space: O(LEN)
     */
    private int[] radixSort(final int[] nums, final int start, final int endInclusive) {

        final int N = endInclusive - start + 1;

        final int[] tmpNums = new int[N];

        /**
         * Copy data from nums to tmpNums
         */
        for (int i = start; i <= endInclusive; i++) {
            tmpNums[i - start] = nums[i];
        }

        /**
         * find max, min
         */
        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < N; i++) {
            max = Math.max(max, tmpNums[i]);
            min = Math.min(min, tmpNums[i]);
        }

        /**
         * Trick: Make all elements to positive
         */
        if (min < 0) {
            for (int i = 0; i < N; i++) {
                tmpNums[i] -= min;
            }

            max -= min;
        }

        /**
         * Trick: quotient MUST starts with max
         */
        int quotient = max;
        /**
         * Trick: divisor MUST starts with 1.
         */
        int divisor = 1;

        final Map<Integer, List<Integer>> map = new HashMap<>();

        while (quotient > 0) {

            for (int i = 0; i < N; i++) {
                /**
                 * Trick: int remainder = tmpNums[i] / divisor % 10;
                 */
                int remainder = tmpNums[i] / divisor % 10;
                if (!map.containsKey(remainder)) {
                    map.put(remainder, new ArrayList<>());
                }
                map.get(remainder).add(tmpNums[i]);
            }

            /**
             * Trick: User AtomicInteger to increment pos inside lambda
             */
            AtomicInteger pos = new AtomicInteger();
            for (int i = 0; i < 10; i++) {
                if (map.containsKey(i)) {
                    map.get(i).forEach(e -> {
                        tmpNums[pos.getAndIncrement()] = e;
                    });
                }
            }

            /**
             * Trick
             */
            map.clear();

            /**
             * Trick
             */
            quotient /= 10;
            divisor *= 10;
        }

        /**
         * Trick: Recover nagetive values
         */
        if (min < 0) {
            for (int i = 0; i < N; i++) {
                tmpNums[i] += min;
            }
        }

        return tmpNums;
    }
}
