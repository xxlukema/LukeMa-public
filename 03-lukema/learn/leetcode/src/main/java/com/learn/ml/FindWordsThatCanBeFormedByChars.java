package com.learn.ml;


import org.junit.jupiter.api.Assertions;

import lombok.extern.log4j.Log4j2;


/**
 * 1160. Find Words That Can Be Formed by Characters
 *
 * Easy
 *
 * You are given an array of strings words and a string chars.
 *
 * A string is good if it can be formed by characters from chars (each character can only be used once).
 *
 * Return the sum of lengths of all good strings in words.
 *
 * Example 1:
 *
 * Input: words = ["cat","bt","hat","tree"], chars = "atach"
 * Output: 6
 * Explanation: The strings that can be formed are "cat" and "hat" so the answer is 3 + 3 = 6.
 *
 * Example 2:
 *
 * Input: words = ["hello","world","leetcode"], chars = "welldonehoneyr"
 * Output: 10
 * Explanation: The strings that can be formed are "hello" and "world" so the answer is 5 + 5 = 10.
 *
 * Constraints:
 *
 *     1 <= words.length <= 1000
 *     1 <= words[i].length, chars.length <= 100
 *     words[i] and chars consist of lowercase English letters.
 *
 */
@Log4j2
public class FindWordsThatCanBeFormedByChars {

    public static void main(String[] args) {
        FindWordsThatCanBeFormedByChars findWordsThatCanBeFormedByChars = new FindWordsThatCanBeFormedByChars();

        /*
        String[] words = { "cat", "bt", "hat", "tree" };
        String chars = "atach";
        int expected = 6;
        */

        String[] words = { "hello", "world", "leetcode" };
        String chars = "welldonehoneyr";
        int expected = 10;

        var ret = findWordsThatCanBeFormedByChars.countCharactersLuke(words, chars);
        log.info("Find Words That Can Be Formed by Characters: {}", () -> ret);
        Assertions.assertEquals(expected, ret);
        log.debug("Find Words That Can Be Formed by Characters {} OK", () -> "countCharactersLuke");

        var retLc = findWordsThatCanBeFormedByChars.countCharactersLc(words, chars);
        log.info("Find Words That Can Be Formed by Characters: {}", () -> retLc);
        Assertions.assertEquals(expected, retLc);
        log.debug("Find Words That Can Be Formed by Characters {} OK", () -> "countCharactersLc");

    }

    /**
     * Brute - Luke
     *
     * Runtime: 24ms Beats42.62%of users with Java
     * Memory: 44.48MB Beats22.01%of users with Java
     * Time: O(words.length * sum(word.length()) * chars.length)
     * Space: O(chars.length)
     */
    public int countCharactersLuke(String[] words, String chars) {
        int len = 0;
        for (String word : words) {
            len += countCharactersLuke(word, chars);
        }

        return len;
    }

    /**
     * Time: O(word.length() * chars.length())
     * Space: O(chars.length())
     */
    private int countCharactersLuke(String word, String chars) {

        StringBuilder sb = new StringBuilder(chars);

        for (char ch : word.toCharArray()) {
            boolean found = false;
            for (int i = 0; i < sb.length(); i++) {
                if (ch == sb.charAt(i)) {
                    sb.delete(i, i + 1);
                    found = true;
                    break;
                }
            }
            if (!found) {
                return 0;
            }
        }

        return chars.length() - sb.length();
    }

    /**
     * Time: O(sum(word.legnth() + chars.length))
     * Space: O(1)
     */
    public int countCharactersLc(String[] words, String chars) {

        int[] count = new int[26];
        for (char ch : chars.toCharArray()) {
            count[ch - 'a']++;
        }

        int len = 0;
        for (String word : words) {
            len += countCharactersLc(word, count);
        }

        return len;
    }

    /**
     * Time: O(word.length())
     * Space: O(1)
     */
    private int countCharactersLc(String word, int[] count) {
        int[] c = new int[26];
        for (char ch : word.toCharArray()) {
            int idx = ch - 'a';
            c[idx]++;
            if (c[idx] > count[idx]) {
                return 0;
            }
        }

        return word.length();
    }

}
