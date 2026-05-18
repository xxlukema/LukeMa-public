package com.learn.dp;


import lombok.extern.log4j.Log4j2;


/**
 * LC - 213 - Shortest Palindrome
 *
 * Hard
 *
 * You are given a string s. You can convert s to a palindrome by adding characters in front of it.
 *
 * Return the shortest palindrome you can find by performing this transformation.
 *
 * Example 1:
 * Input: s = "aacecaaa"
 * Output: "aaacecaaa"
 *
 * Example 2:
 * Input: s = "abcd"
 * Output: "dcbabcd"
 *
 * Constraints:
 *     0 <= s.length <= 5 * 104
 *     s consists of lowercase English letters only.
 */
@Log4j2
public class ShortestPalindrome {

    public static void main(String[] args) {

        // final String s = "xzsaacecaaa";

        /**
         * Expected: "abbaabba" <--- This is LC expectation. It should be "abbabba"
         */
        final String s = "aabba";

        ShortestPalindrome shortestPalindrome = new ShortestPalindrome();

        var ret = shortestPalindrome.shortestPalindrome(s);
        log.debug("Shortest Palindrome: {}", () -> ret);
        log.debug("Shortest Palindrome {} OK", () -> "ret");

    }

    /**
     * Luke - Two Pointers / Sliding Window
     *
     * Time: O(N)
     * Space; O(1)
     */
    public String shortestPalindrome(String str) {
        if (str == null || str.length() < 2) {
            return str;
        }

        StringBuilder sb = new StringBuilder(str);
        sb.reverse();
        int left = 0;
        int right = sb.length() - 1;
        while (left < right) {
            if (sb.charAt(left) != sb.charAt(right)) {
                sb.insert(right + 1, sb.charAt(left));
            } else {
                right--;
            }
            left++;
        }

        return sb.toString();
    }
}
