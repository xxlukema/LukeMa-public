package com.learn.other;


import java.util.ArrayList;
import java.util.List;

import lombok.extern.log4j.Log4j2;


/**
 * LC - 228 - Summary Range
 *
 * Easy
 *
 * You are given a sorted unique integer array nums.
 *
 * A range [a,b] is the set of all integers from a to b (inclusive).
 *
 * Return the smallest sorted list of ranges that cover all the numbers in the array exactly. That is, each element of nums is covered by
 * exactly one of the ranges, and there is no integer x such that x is in one of the ranges but not in nums.
 *
 * Each range [a,b] in the list should be output as:
 *     "a->b" if a != b
 *     "a" if a == b
 *
 * Example 1:
 * Input: nums = [0,1,2,4,5,7]
 * Output: ["0->2","4->5","7"]
 * Explanation: The ranges are:
 * [0,2] --> "0->2"
 * [4,5] --> "4->5"
 * [7,7] --> "7"
 *
 * Example 2:
 * Input: nums = [0,2,3,4,6,8,9]
 * Output: ["0","2->4","6","8->9"]
 * Explanation: The ranges are:
 * [0,0] --> "0"
 * [2,4] --> "2->4"
 * [6,6] --> "6"
 * [8,9] --> "8->9"
 *
 * Constraints:
 *     0 <= nums.length <= 20
 *     -2 ^ 31 <= nums[i] <= 2 ^ 31 - 1
 *     All the values of nums are unique.
 *     nums is sorted in ascending order.
 */
@Log4j2
public class SummaryRanges {

    public static void main(String[] args) {

        /**
         * Expected: ["0->2","4->5","7"]
         */
        // final int[] nums = { 0, 1, 2, 4, 5, 7 };

        /**
         * Expected: ["0","2->4","6","8->9"]
         */
        final int[] nums = { 0, 2, 3, 4, 6, 8, 9 };

        SummaryRanges summaryRanges = new SummaryRanges();

        var ret = summaryRanges.summaryRanges(nums);
        log.debug("Summary Range: {}", () -> ret);
        log.debug("Summary Range {} OK", () -> "ret");
    }

    public List<String> summaryRanges(int[] nums) {
        List<String> list = new ArrayList<>();
        if (nums == null || nums.length == 0) {
            return list;
        }

        boolean isRangeOpen = false;
        boolean hasArrow = false;
        StringBuilder sb = new StringBuilder();

        for (int i = 0, n = nums.length, last = n - 1; i < n; i++) {
            int curr = nums[i];

            if (i == last) {
                if (isRangeOpen && hasArrow) {
                    sb.append(String.valueOf(curr));
                    list.add(sb.toString());
                } else {
                    list.add(String.valueOf(curr));
                }

                break;
            } else {
                if (isRangeOpen) {
                    if (curr + 1 == nums[i + 1]) {
                        // in the middle
                        if (hasArrow) {
                            continue;
                        } else {
                            sb.append("->");
                            hasArrow = true;
                        }
                    } else {
                        // if close the range or not?
                        if (i + 2 < n) {
                            if (nums[i + 1] + 1 == nums[i + 2]) {
                                // next is a new range. close this.
                                if (!hasArrow) {
                                    sb.append("->");
                                }
                                hasArrow = false;
                                isRangeOpen = false;
                                sb.append(curr);
                                list.add(sb.toString());
                                sb.delete(0, sb.length());
                                continue;
                            }
                        }
                        // close range
                        if (!hasArrow) {
                            sb.append("->");
                        }
                        hasArrow = false;
                        isRangeOpen = false;
                        sb.append(curr);
                        list.add(sb.toString());
                        sb.delete(0, sb.length());
                    }
                } else {
                    // start range
                    sb.append(String.valueOf(curr));
                    isRangeOpen = true;
                    hasArrow = false;
                }
            }
        }

        return list;
    }
}
