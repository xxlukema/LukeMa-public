package com.learn.other;


import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Assertions;

import lombok.extern.log4j.Log4j2;


/**
 * LC - 127 - Longest Consecutive Sequence
 * 
 * Medium
 * 
 * Given an unsorted array of integers nums, return the length of the longest consecutive elements sequence.
 * You must write an algorithm that runs in O(n) time.
 * 
 * Example 1:
 * Input: nums = [100,4,200,1,3,2]
 * Output: 4
 * Explanation: The longest consecutive elements sequence is [1, 2, 3, 4]. Therefore its length is 4.
 * 
 * Example 2:
 * Input: nums = [0,3,7,2,5,8,4,6,0,1]
 * Output: 9
 * 
 * Constraints:
 *     0 <= nums.length <= 105
 *     -109 <= nums[i] <= 109
 * 
 */
@Log4j2
public class LongestConsecutiveSequence {

    public static void main(String[] args) {

        int[] nums = { 100, 4, 200, 1, 3, 2 };
        // int[] nums = { 0, 3, 7, 2, 5, 8, 4, 6, 0, 1 };

        LongestConsecutiveSequence longestConsecutiveSequence = new LongestConsecutiveSequence();

        var retLuke = longestConsecutiveSequence.longestConsecutiveLuke(nums);
        log.debug("Longest Consecutive Sequence Luke: {}", () -> retLuke);

        var retLc = longestConsecutiveSequence.longestConsecutiveLc(nums);
        Assertions.assertEquals(retLuke, retLc);

        log.debug(() -> "Longest Consecutive Sequence LC OK.");
    }

    /**
     * Luke - Copy of LC Iterative
     * 
     * Runtime: 23 ms, faster than 86.23% of Java online submissions for Longest Consecutive Sequence.
     * Memory Usage: 61.2 MB, less than 87.01% of Java online submissions for Longest Consecutive Sequence.
     * 
     * Time: O(n) - Although the time complexity appears to be quadratic due to the while loop nested within the for loop, closer
     *              inspection reveals it to be linear. Because the while loop is reached only when currentNum marks the beginning
     *              of a sequence (i.e. currentNum-1 is not present in nums), the while loop can only run for nnn iterations throughout
     *              the entire runtime of the algorithm. This means that despite looking like O(n ^ 2) complexity, the nested loops
     *              actually run in O(n + n) = O(n) time. All other computations occur in constant time, so the overall runtime is linear.
     * Space: O(n) - HashSet size.
     */
    public int longestConsecutiveLuke(int[] nums) {

        /**
         * Build a HashSet for the nums because HashSet has O(1) time for add/insert/delete/search.
         */

        final Set<Integer> set = new HashSet<>();

        for (int n : nums) {
            set.add(n);
        }

        int longestSequence = 0;

        for (int n : set) {

            if (!set.contains(n - 1)) {

                int counter = 1;
                int curr = n;

                while (set.contains(++curr)) {
                    counter++;
                }

                longestSequence = Math.max(longestSequence, counter);
            }
        }

        return longestSequence;
    }

    /**
     * LC - Iterative
     * 
     * Runtime: 23 ms, faster than 86.23% of Java online submissions for Longest Consecutive Sequence.
     * Memory Usage: 61.2 MB, less than 87.01% of Java online submissions for Longest Consecutive Sequence.
     * 
     * Time: O(n) - Although the time complexity appears to be quadratic due to the while loop nested within the for loop, closer
     *              inspection reveals it to be linear. Because the while loop is reached only when currentNum marks the beginning
     *              of a sequence (i.e. currentNum-1 is not present in nums), the while loop can only run for nnn iterations throughout
     *              the entire runtime of the algorithm. This means that despite looking like O(n ^ 2) complexity, the nested loops
     *              actually run in O(n + n) = O(n) time. All other computations occur in constant time, so the overall runtime is linear.
     * Space: O(n) - HashSet size.
     */
    public int longestConsecutiveLc(int[] nums) {

        /**
         * Put elements into a HashSet.
         */
        Set<Integer> numSet = new HashSet<Integer>();
        for (int num : nums) {
            numSet.add(num);
        }

        int longestStreak = 0;

        for (int num : numSet) {
            if (!numSet.contains(num - 1)) {
                int currentNum = num;
                int currentStreak = 1;

                while (numSet.contains(currentNum + 1)) {
                    currentNum += 1;
                    currentStreak += 1;
                }

                longestStreak = Math.max(longestStreak, currentStreak);
            }
        }

        return longestStreak;
    }
}
