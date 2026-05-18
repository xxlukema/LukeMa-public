package com.learn.other;


import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Assertions;

import lombok.extern.log4j.Log4j2;


/**
 * LC - 179 - Largest Number
 * 
 * Medium
 * 
 * Given a list of non-negative integers nums, arrange them such that they form the largest number and return it.
 * Since the result may be very large, so you need to return a string instead of an integer.
 * 
 * Example 1:
 * Input: nums = [10, 2]
 * Output: "210"
 * 
 * Example 2:
 * Input: nums = [3, 30, 34, 5, 9]
 * Output: "9534330"
 * 
 * Constraints:
 *     1 <= nums.length <= 100
 *     0 <= nums[i] <= 109
 */
@Log4j2
public class LargestNumber {

  public static void main(String[] args) {

    /**
     * Output: "95343300"
     */
    final int[] nums = { 3, 300, 34, 5, 9 };

    LargestNumber largestNumber = new LargestNumber();

    var largestStr = largestNumber.largestNumber(nums);
    log.debug("Largest number: {}", () -> largestStr);
    Assertions.assertEquals("95343300", largestStr);
    log.debug("Largest number {} OK", () -> "largestNumber");

  }

  /**
   * LC - Compare String " "
   * 
   * Runtime: 22 ms, faster than 9.84% of Java online submissions for Largest Number.
   * Memory Usage: 43.5 MB, less than 68.92% of Java online submissions for Largest Number.
   * 
   * Time: O(N (log N)) for sorting
   * Space: O(N)
   */
  public String largestNumber(final int[] nums) {
    /**
     * Handle corner case of all zeros in array
     */
    boolean isAllZero = true;
    for (int i : nums) {
      if (i != 0) {
        isAllZero = false;
        break;
      }
    }
    if (isAllZero) {
      return "0";
    }

    /**
     * Stream.stort() use TimSort.
     * Time: O(n log(n))
     * Space: O(n)
     */
    List<String> list = IntStream.of(nums).boxed().map(String::valueOf)
        .sorted((a, b) -> (b + a).compareTo(a + b))
        // .sorted((a, b) -> (b).compareTo(a))
        .collect(Collectors.toList());

    log.debug("list: {}", list);

    return String.join("", list);
  }
}
