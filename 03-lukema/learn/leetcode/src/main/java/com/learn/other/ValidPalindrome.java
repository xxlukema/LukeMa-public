package com.learn.other;


import lombok.extern.log4j.Log4j2;


/**
 * LC - 125 - Valid Palindrome
 * 
 * Easy
 * 
 * A phrase is a palindrome if, after converting all uppercase letters into lowercase letters and removing all non-alphanumeric characters,
 * it reads the same forward and backward. Alphanumeric characters include letters and numbers.
 * 
 * Given a string s, return true if it is a palindrome, or false otherwise.
 * 
 * Example 1:
 * Input: s = "A man, a plan, a canal: Panama"
 * Output: true
 * Explanation: "amanaplanacanalpanama" is a palindrome.
 * 
 * Example 2:
 * Input: s = "race a car"
 * Output: false
 * Explanation: "raceacar" is not a palindrome.
 * 
 * Example 3:
 * Input: s = " "
 * Output: true
 * Explanation: s is an empty string "" after removing non-alphanumeric characters.
 * Since an empty string reads the same forward and backward, it is a palindrome.
 * 
 * Constraints:
 *     1 <= s.length <= 2 * 105
 *     s consists only of printable ASCII characters.
 */
@Log4j2
public class ValidPalindrome {

    public static void main(String[] args) {

        // String s = "A man, a plan, a canal: Panama";
        // String s = "race a car";
        // String s = " ";
        // String s = "ab";
        String s = ".,";

        ValidPalindrome validPalindrome = new ValidPalindrome();

        var ret = validPalindrome.isPalindrome(s);
        log.debug("Is palindrome: {}", () -> ret);

    }

    /**
     * Luke - Iterative
     * 
     * Runtime: 2 ms, faster than 99.95% of Java online submissions for Valid Palindrome.
     * Memory Usage: 42.5 MB, less than 93.17% of Java online submissions for Valid Palindrome.
     * 
     * Time: O(N)
     * Space: O(1)
     */
    public boolean isPalindrome(String s) {
        final int LEN = s.length();

        int left = 0;
        int right = LEN - 1;

        while (left <= right) {
            char chLeft = s.charAt(left++);
            while (left < LEN && !isAlphaNum(chLeft)) {
                chLeft = s.charAt(left++);
            }

            char chRight = s.charAt(right--);
            while (right >= 0 && !isAlphaNum(chRight)) {
                chRight = s.charAt(right--);
            }


            if(left > right + 1) {
                return true;
            }

            // log.debug("{} - {}", chLeft, chRight);

            if (chLeft != chRight) {
                chLeft = toLowerCase(chLeft);
                chRight = toLowerCase(chRight);

                /*
                if (chLeft >= 'A' && chLeft <= 'Z') {
                }
                
                if (chRight >= 'A' && chRight <= 'Z') {
                }
                */

                if (chLeft != chRight) {
                    return false;
                }
            }
        }

        return true;
    }

    char toLowerCase(char ch) {
        // return Character.toLowerCase(ch);
        if (ch >= 'A' && ch <= 'Z') {
            return (char) (ch + 'a' - 'A');
        } else {
            return ch;
        }
    }

    boolean isAlphaNum(char ch) {
        return (ch >= '0' && ch <= '9') || (ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z');
    }
}
