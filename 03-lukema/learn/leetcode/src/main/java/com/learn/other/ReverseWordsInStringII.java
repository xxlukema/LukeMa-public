package com.learn.other;


import lombok.extern.log4j.Log4j2;


/**
 * LC - 186 - Reverse words in a string II
 * 
 * Medium
 * 
 * Given a character array s, reverse the order of the words.
 * A word is defined as a sequence of non-space characters. The words in s will be separated by a single space.
 * Your code must solve the problem in-place, i.e. without allocating extra space.
 * 
 * Example 1:
 * Input: s = ["t","h","e"," ","s","k","y"," ","i","s"," ","b","l","u","e"]
 * Output: ["b","l","u","e"," ","i","s"," ","s","k","y"," ","t","h","e"]
 * 
 * Example 2:
 * Input: s = ["a"]
 * Output: ["a"]
 * 
 * Constraints:
 *     1 <= s.length <= 105
 *     s[i] is an English letter (uppercase or lowercase), digit, or space ' '.
 *     There is at least one word in s.
 *     s does not contain leading or trailing spaces.
 *     All the words in s are guaranteed to be separated by a single space.
 */
@Log4j2
public class ReverseWordsInStringII {

  public static void main(String[] args) {

    final char[] s = { 't', 'h', 'e', ' ', 's', 'k', 'y', ' ', 'i', 's', ' ', 'b', 'l', 'u', 'e' };

    log.debug("Reverse words in a string II before reverse: {}", () -> s);

    ReverseWordsInStringII reverseWordsInStringII = new ReverseWordsInStringII();

    reverseWordsInStringII.reverseWords(s);
    log.debug("Reverse words in a string II after reverse: {}", () -> s);
    log.debug("Reverse words in a string II {} OK", () -> "reverseWords");

  }


  /**
   * Luke - Two Pointers
   * 
   * Runtime: 3 ms, faster than 25.49% of Java online submissions for Reverse Words in a String II.
   * Memory Usage: 55.1 MB, less than 67.59% of Java online submissions for Reverse Words in a String II.
   * 
   * Time: O(N)
   * Space: O(1)
   */
  public void reverseWords(char[] s) {
    final int N = s.length;

    /**
     * 1. reverse whole string
     */
    int left = 0;
    int right = N - 1;

    while (left < right) {
      char chTmp = s[left];
      s[left++] = s[right];
      s[right--] = chTmp;
    }

    /**
     * 2. reverse individual words
     */
    left = 0;
    right = left + 1;
    while (right < N) {
      /**
       * move right to end of word
       */
      while (right < N && s[right] != ' ') {
        right++;
      }

      /**
       * remember right
       */
      int curr = right;

      if (right != N - 1) {
        right--;
      }
      while (left < right) {
        char chTmp = s[left];
        s[left++] = s[right];
        s[right--] = chTmp;
      }

      left = curr + 1;
      right = left + 1;
    }
  }
}
