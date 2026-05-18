package com.learn.other;


import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Assertions;

import lombok.extern.log4j.Log4j2;


/**
 * LC - 202 - Happy Number
 * 
 * Medium
 * 
 * Write an algorithm to determine if a number n is happy.
 * 
 * A happy number is a number defined by the following process:
 * 
 *     Starting with any positive integer, replace the number by the sum of the squares of its digits.
 *     Repeat the process until the number equals 1 (where it will stay), or it loops endlessly in a cycle which does not include 1.
 *     Those numbers for which this process ends in 1 are happy.
 * 
 * Return true if n is a happy number, and false if not.
 * 
 * Example 1:
 * Input: n = 19
 * Output: true
 * Explanation:
 * 1 ^ 2 + 9 ^ 2 = 82
 * 8 ^ 2 + 2 ^ 2 = 68
 * 6 ^ 2 + 8 ^ 2 = 100
 * 1 ^ 2 + 0 ^ 2 + 0 ^ 2 = 1
 * 
 * Example 2:
 * Input: n = 2
 * Output: false
 * 
 * Constraints:
 *     1 <= n <= 2 ^ 31 - 1
 */
@Log4j2
public class HappyNumber {

  public static void main(String[] args) {

    /**
     * Output: true
     */
    // final int n = 19;
    final int n = 9;

    HappyNumber happyNumber = new HappyNumber();

    var isHappyLukeIterative = happyNumber.isHappyLukeIterative(n);
    log.debug("Happy Number: {}", () -> isHappyLukeIterative);
    log.debug("Happy Number {} OK", () -> "isHappyLukeIterative");

    var isHappyLcFloydCycleFinding = happyNumber.isHappyLcFloydCycleFinding(n);
    Assertions.assertEquals(isHappyLukeIterative, isHappyLcFloydCycleFinding);
    log.debug("Happy Number {} OK", () -> "isHappyLcFloydCycleFinding");
  }

  /**
   * Luke - Iterative
   * 
   * Runtime: 1 ms, faster than 98.68% of Java online submissions for Happy Number.
   * Memory Usage: 39.6 MB, less than 90.25% of Java online submissions for Happy Number.
   * 
   * Time: O(log(N))
   * Space: O(log(N))
   */
  public boolean isHappyLukeIterative(int n) {

    if (n == 1) {
      return true;
    }

    int quotient = n;
    int sum = 0;

    Set<Integer> seen = new HashSet<>();

    while (true) {
      sum = 0;

      while (true) {
        int remainder = quotient % 10;
        quotient = quotient / 10;

        sum += remainder * remainder;

        if (quotient == 0) {
          break;
        }
      }

      // log.debug("sum: {}", sum);

      if (sum <= 0) {
        return false;
      } else if (sum == 1) {
        return true;
      } else {
        if (seen.contains(sum)) {
          return false;
        } else {
          seen.add(sum);
        }
        quotient = sum;
      }
    }
  }

  /**
   * LC - Floyd Cycle Finding
   * 
   * Time: O(log(N))
   * Space: O(1)
   */
  public boolean isHappyLcFloydCycleFinding(int n) {
    int slowRunner = n;
    int fastRunner = getNext(n);
    while (fastRunner != 1 && slowRunner != fastRunner) {
      slowRunner = getNext(slowRunner);
      fastRunner = getNext(getNext(fastRunner));
    }
    return fastRunner == 1;
  }

  public int getNext(int n) {
    int totalSum = 0;
    while (n > 0) {
      int d = n % 10;
      n = n / 10;
      totalSum += d * d;
    }
    return totalSum;
  }
}
