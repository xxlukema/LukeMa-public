package com.learn.other;


import java.util.ArrayList;
import java.util.List;

import lombok.extern.log4j.Log4j2;


/**
 * LC - 162 - Missing Range
 * 
 * Easy
 * 
 * You are given an inclusive range [lower, upper] and a sorted unique integer array nums, where all elements are in the inclusive range.
 * A number x is considered missing if x is in the range [lower, upper] and x is not in nums.
 * Return the smallest sorted list of ranges that cover every missing number exactly. That is, no element of nums is in any of the ranges, 
 * and each missing number is in one of the ranges.
 * 
 * Each range [a,b] in the list should be output as:
 *     "a->b" if a != b
 *     "a" if a == b
 * 
 * Example 1:
 * Input: nums = [0,1,3,50,75], lower = 0, upper = 99
 * Output: ["2","4->49","51->74","76->99"]
 * Explanation: The ranges are:
 * [2,2] --> "2"
 * [4,49] --> "4->49"
 * [51,74] --> "51->74"
 * [76,99] --> "76->99"
 * 
 * Example 2:
 * Input: nums = [-1], lower = -1, upper = -1
 * Output: []
 * Explanation: There are no missing ranges since there are no missing numbers.
 * 
 * Constraints:
 *     -109 <= lower <= upper <= 109
 *     0 <= nums.length <= 100
 *     lower <= nums[i] <= upper
 *     All the values of nums are unique.
 */
@Log4j2
public class MissingRange {

    public static void main(String[] args) {

        final int[] nums = { 0, 1, 3, 50, 75 };
        final int lower = 0, upper = 99;

        // final int[] nums = { 1000000000 };
        // final int lower = 0, upper = 1000000000;

        MissingRange missingRange = new MissingRange();

        var ret = missingRange.findMissingRanges(nums, lower, upper);
        log.debug("Missing range: {}", () -> ret);
        log.debug("Missing range {} OK", () -> "ret");
    }

    /**
     * Luke - Iterative
     * 
     * Runtime: 10 ms, faster than 38.81% of Java online submissions for Missing Ranges.
     * Memory Usage: 42.7 MB, less than 25.99% of Java online submissions for Missing Ranges.
     * 
     * Time: O(N), where N is nums.length
     * Space: O(1)
     */
    public List<String> findMissingRanges(int[] nums, int lower, int upper) {
        List<String> missing = new ArrayList<>();

        if (nums.length == 0) {
            addRange(missing, lower, upper);
            return missing;
        }

        if (lower < nums[0]) {
            addRange(missing, lower, nums[0] - 1);
        }

        for (int i = 0; i < nums.length; i++) {
            if (i + 1 == nums.length) {
                break;
            } else {
                if (nums[i] + 1 == nums[i + 1]) {
                    continue;
                } else {
                    addRange(missing, nums[i] + 1, nums[i + 1] - 1);
                }
            }
        }

        if (upper > nums[nums.length - 1]) {
            addRange(missing, nums[nums.length - 1] + 1, upper);
        }

        return missing;
    }

    private void addRange(final List<String> missing, final int lower, final int upper) {
        if (lower == upper) {
            missing.add(String.valueOf(lower));
        } else {
            missing.add(String.valueOf(lower) + "->" + String.valueOf(upper));
        }
    }

    /**
     * Luke - Iterative - Time Limit Exceeded
     * 
     * Time: O(N)
     * Space: O(N)
     */
    public List<String> findMissingRangesIterative(int[] nums, int lower, int upper) {
        List<String> missing = new ArrayList<>();

        final List<Integer> list = new ArrayList<>();

        int idx = 0;

        for (int i = lower; i <= upper + 1; i++) {
            if ((idx >= 0 && idx < nums.length && i == nums[idx]) || i == upper + 1) {
                idx++;
                if (list.isEmpty()) {
                    continue;
                } else {
                    if (list.size() == 1) {
                        missing.add(String.valueOf(list.get(0)));
                    } else {
                        missing.add(String.valueOf(list.get(0)) + "->" + String.valueOf(list.get(list.size() - 1)));
                    }
                    list.clear();
                }
            } else {
                list.add(i);
            }
        }

        return missing;
    }
}
