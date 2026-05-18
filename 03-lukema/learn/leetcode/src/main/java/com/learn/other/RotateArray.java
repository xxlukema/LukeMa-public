package com.learn.other;


import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Assertions;

import lombok.extern.log4j.Log4j2;


/**
 * LC - 189 - Rotate Array
 *
 * Medium
 *
 * Given an array, rotate the array to the right by k steps, where k is non-negative.
 *
 * Example 1:
 * Input: nums = [1,2,3,4,5,6,7], k = 3
 * Output: [5,6,7,1,2,3,4]
 * Explanation:
 * rotate 1 steps to the right: [7,1,2,3,4,5,6]
 * rotate 2 steps to the right: [6,7,1,2,3,4,5]
 * rotate 3 steps to the right: [5,6,7,1,2,3,4]
 *
 * Example 2:
 * Input: nums = [-1,-100,3,99], k = 2
 * Output: [3,99,-1,-100]
 * Explanation:
 * rotate 1 steps to the right: [99,-1,-100,3]
 * rotate 2 steps to the right: [3,99,-1,-100]
 *
 * Constraints:
 *     1 <= nums.length <= 105
 *     -2 ^ 31 <= nums[i] <= 2 ^ 31 - 1
 *     0 <= k <= 105
 */
@Log4j2
public class RotateArray {

  public static void main(String[] args) {

    /*
    final int[] nums = { 0, 1, 2, 3, 4, 5, 6, 7 };
    final int k = 3;
    */

    final int[] nums = { -1, -100, 3, 99 };
    final int k = 3;

    RotateArray rotateArray = new RotateArray();

    int[] nums1 = Arrays.copyOf(nums, nums.length);
    rotateArray.rotateLukeBrute(nums1, k);
    log.debug("Rotate array: {}", () -> nums1);
    log.debug("Rotate array {} OK", () -> "rotateLukeBrute");

    int[] nums2 = Arrays.copyOf(nums, nums.length);
    rotateArray.rotateLukeExtraArray(nums2, k);
    Assertions.assertEquals(toString(nums1), toString(nums2));
    log.debug("Rotate array {} OK", () -> "rotateLukeExtraArray");

    int[] nums3 = Arrays.copyOf(nums, nums.length);
    rotateArray.rotateLcReverse(nums3, k);
    Assertions.assertEquals(toString(nums1), toString(nums3));
    log.debug("Rotate array {} OK", () -> "rotateLcReverse");

    /*
    int[] nums4 = Arrays.copyOf(nums, nums.length);
    rotateArray.rotateLcCyclicWrong(nums4, k);
    Assertions.assertEquals(toString(nums1), toString(nums4));
    log.debug("Rotate array {} OK", () -> "rotateLcCyclic");
    */

  }

  private static String toString(final int[] nums) {
    return IntStream.of(nums).boxed().map(String::valueOf).collect(Collectors.joining());
  }

  /**
   * LC - Reverse
   *
   * Time: O(N)
   * Space: O(1)
   */
  public void rotateLcReverse(final int[] nums, int k) {
    if (nums == null || nums.length == 0) {
      return;
    }

    final int N = nums.length;

    if (k > N) {
      k = k % N;
    }

    reverse(nums, 0, N - 1);
    reverse(nums, 0, N - 1 - k);
    reverse(nums, N - k, N - 1);
  }

  private void reverse(final int[] nums, int left, int right) {
    while (left < right) {
      int tmp = nums[left];
      nums[left] = nums[right];
      nums[right] = tmp;
      left++;
      right--;
    }
  }

  /**
   * LC - Cyclic
   *
   * Time: O(N)
   * Space: O(1)
   */
  public void rotateLcCyclicWrong(int[] nums, int k) {
    k = k % nums.length;
    int count = 0;
    for (int start = 0; count < nums.length; start++) {
      int current = start;
      int prev = nums[start];
      do {
        int next = (current + k) % nums.length;
        int temp = nums[next];
        nums[next] = prev;
        prev = temp;
        current = next;
        count++;
      } while (start != current);
    }
  }

  /**
   * Luke - Extra Array
   *
   * Time: O(N)
   * Space: O(K)
   */
  public void rotateLukeExtraArray(final int[] nums, int k) {
    if (nums == null || nums.length == 0) {
      return;
    }

    final int N = nums.length;

    if (k > N) {
      k = k % N;
    }

    int[] extra = new int[k];

    for (int i = 0; i < k; i++) {
      extra[i] = nums[i];
      nums[i] = nums[(i + k) % N];
    }

    for (int i = k; i < N - k; i++) {
      nums[i] = nums[i + k];
    }

    for (int i = N - k; i < N; i++) {
      nums[i] = extra[i - (N - k)];
    }
  }

  /**
   * Luke - Brute
   *
   * Time: O(N * k)
   * Space: O(1)
   */
  public void rotateLukeBrute(final int[] nums, int k) {
    if (nums == null || nums.length == 0) {
      return;
    }

    final int N = nums.length;

    if (k > N) {
      k = k % N;
    }

    for (int i = 0; i < k; i++) {
      int tmp = nums[0];
      for (int m = 0; m < N - 1; m++) {
        nums[m] = nums[m + 1];
      }
      nums[N - 1] = tmp;
    }

  }

  /**
   * Luke - Cyclic Array
   *
   * Time: O(N)
   * Space: O(K)
   */
  public void rotateLukeCyclicWrong(final int[] nums, int k) {
    if (nums == null || nums.length == 0) {
      return;
    }

    final int N = nums.length;

    if (k > N) {
      k = k % N;
    }

    for (int i = 0; i < k; i++) {
      int tmp = nums[i];

      int curr = i;
      int next = curr + k;
      while (curr <= N - 1 - k) {
        nums[curr] = nums[next];
        curr = next;
        next += k;
      }

      nums[curr] = tmp;
    }
  }
}
