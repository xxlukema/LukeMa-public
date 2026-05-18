package com.learn.other;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import org.junit.jupiter.api.Assertions;

import lombok.extern.log4j.Log4j2;


/**
 * LC-316 Remove Duplicate Letters
 *
 * Medium
 *
 * Given a string s, remove duplicate letters so that every letter appears once and only once. You must make sure your result is
 * the smallest in lexicographical order among all possible results.
 *
 * Example 1:
 * Input: s = "bcabc"
 * Output: "abc"
 *
 * Example 2:
 * Input: s = "cbacdcbc"
 * Output: "acdb"
 *
 * Constraints:
 *     1 <= s.length <= 104
 *     s consists of lowercase English letters.
 */
@Log4j2
public class RemoveDuplicateLetters {

    public static void main(String[] args) {

        /**
         * expected: "abc"
         */
        final String s = "bcabc";

        /**
         * expected: "eacb"
         */
        // final String s = "ecbacba";

        /**
         * expected: "acdb"
         */
        // final String s = "cbacdcbc";

        /**
         * expected: "cdb"
         */
        // final String s = "cdcbc";

        RemoveDuplicateLetters removeDuplicateLetters = new RemoveDuplicateLetters();

        var removeDuplicateLettersLcStack = removeDuplicateLetters.removeDuplicateLettersLcStack(s);

        log.debug("Remove Duplicate Letters: {}", removeDuplicateLettersLcStack);
        log.debug("Remove Duplicate Letters {} OK", () -> "removeDuplicateLettersLcStack");

        var removeDuplicateLettersLuke = removeDuplicateLetters.removeDuplicateLettersLuke(s);
        Assertions.assertEquals(removeDuplicateLettersLcStack, removeDuplicateLettersLuke);
        log.debug("Remove Duplicate Letters {} OK", () -> "removeDuplicateLettersLuke");

        var removeDuplicateLettersLcRecursive = removeDuplicateLetters.removeDuplicateLettersLcRecursive(s);
        Assertions.assertEquals(removeDuplicateLettersLcStack, removeDuplicateLettersLcRecursive);
        log.debug("Remove Duplicate Letters {} OK", () -> "removeDuplicateLettersLcRecursive");

        var removeDuplicateLettersLukeRecursive = removeDuplicateLetters.removeDuplicateLettersLukeRecursive(s);
        Assertions.assertEquals(removeDuplicateLettersLcStack, removeDuplicateLettersLukeRecursive);
        log.debug("Remove Duplicate Letters {} OK", () -> "removeDuplicateLettersLukeRecursive");

        var removeDuplicateLettersLukeRcursive2 = removeDuplicateLetters.removeDuplicateLettersLukeRcursive2(s);
        Assertions.assertEquals(removeDuplicateLettersLcStack, removeDuplicateLettersLukeRcursive2);
        log.debug("Remove Duplicate Letters {} OK", () -> "removeDuplicateLettersLukeRecursive");
    }

    /**
     * LC - Stack
     *
     * Tricks - Trick 1: Use Set `seen` and Map `lastSeenIndexMap` to achieve Time O(N) performance
     *
     * Time: O(N)
     * Space: O(1).  At first glance it looks like this is O(N), but that is not true! `seen` will only contain unique elements,
     *               so it is bounded by the number of characters in the alphabet (a constant). You can only add to stack if an
     *               element has not been seen, so stack also only consists of unique elements. This means that both `stack` and
     *               `seen` are bounded by constant, giving us O(1) space complexity.
     */
    public String removeDuplicateLettersLuke(String s) {

        final Map<Character, Integer> lastSeenIndexMap = new HashMap<>();

        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            lastSeenIndexMap.put(ch, i);
        }

        final Stack<Character> stack = new Stack<>();
        final Set<Character> seen = new HashSet<>();

        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (seen.contains(ch)) {
                continue;
            }

            while (!stack.isEmpty() && stack.peek() > ch && lastSeenIndexMap.get(stack.peek()) > i) {
                char popped = stack.pop();
                seen.remove(popped);
            }
            stack.push(ch);
            seen.add(ch);
        }

        final StringBuilder sb = new StringBuilder();
        while (!stack.isEmpty()) {
            sb.insert(0, stack.pop());
        }

        return sb.toString();
    }

    /**
     * LC - Stack
     * Tricks - Trick 1: Use Set `seen` and Map `lastSeenIndexMap` to achieve Time O(N) performance
     *
     * Time: O(N)
     * Space: O(1).  At first glance it looks like this is O(N), but that is not true! `seen` will only contain unique elements,
     *               so it is bounded by the number of characters in the alphabet (a constant). You can only add to stack if an
     *               element has not been seen, so stack also only consists of unique elements. This means that both `stack` and
     *               `seen` are bounded by constant, giving us O(1) space complexity.
     */
    public String removeDuplicateLettersLcStack(String s) {
        Stack<Character> stack = new Stack<>();
        // this lets us keep track of what's in our solution in O(1) time
        Set<Character> seen = new HashSet<>();

        // this will let us know if there are any more instances of s[i] left in s
        Map<Character, Integer> lastOccurrence = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            lastOccurrence.put(s.charAt(i), i);
        }

        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            // we can only try to add ch if it is not already in our solution
            // this is to maintain only one of each character
            if (!seen.contains(ch)) {
                // if the last letter in our solution:
                //     1. exists
                //     2. is greater than ch so removing it will make the string smaller
                //     3. it is not the last occurrence
                // we remove it from the solution to keep the solution optimal
                while (!stack.isEmpty() && stack.peek() > ch && lastOccurrence.get(stack.peek()) > i) {
                    seen.remove(stack.pop());
                }
                seen.add(ch);
                stack.push(ch);
            }
        }
        StringBuilder sb = new StringBuilder(stack.size());
        /*
        for (Character ch : stack) {
            sb.append(ch.charValue());
        }
        */

        while (!stack.isEmpty()) {
            sb.insert(0, stack.pop());
        }

        return sb.toString();
    }

    public String removeDuplicateLettersLukeRecursive(String s) {
        final int[] counter = new int[26];

        for (int i = 0; i < s.length(); i++) {
            counter[s.charAt(i) - 'a']++;
        }

        // final String s = "cbacdcbc";

        int pos = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) < s.charAt(pos)) {
                pos = i;
            }

            counter[s.charAt(i) - 'a']--;

            if (counter[s.charAt(i) - 'a'] == 0) {
                break;
            }
        }

        return s.length() == 0 ? ""
                : s.charAt(pos) + removeDuplicateLettersLukeRecursive(s.substring(pos + 1).replaceAll(String.valueOf(s.charAt(pos)), ""));
    }

    /**
     * LC - Greedy Recursive
     *
     * Time: O(N)
     * Space: O(N)
     */
    public String removeDuplicateLettersLcRecursive(String s) {
        // find pos - the index of the leftmost letter in our solution
        // we create a counter and end the iteration once the suffix doesn't have each unique character
        // pos will be the index of the smallest character we encounter before the iteration ends
        int[] counter = new int[26];
        for (int i = 0; i < s.length(); i++) {
            counter[s.charAt(i) - 'a']++;
        }

        int pos = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) < s.charAt(pos)) {
                pos = i;
            }

            if (--counter[s.charAt(i) - 'a'] == 0) {
                break;
            }
        }
        // our answer is the leftmost letter plus the recursive call on the remainder of the string
        // note that we have to get rid of further occurrences of s[pos] to ensure that there are no duplicates
        return s.length() == 0 ? "" : s.charAt(pos) + removeDuplicateLettersLcStack(s.substring(pos + 1).replaceAll("" + s.charAt(pos), ""));
    }

    // final String s = "bcabc";
    public String removeDuplicateLettersLukeRcursive2(String s) {

        /**
         * remove connecting dups
         */
        StringBuilder sb = new StringBuilder();
        for (int i = 0, len = s.length(); i < len; i++) {
            if (i + 1 < len && s.charAt(i) == s.charAt(i + 1)) {
                continue;
            }

            sb.append(s.charAt(i));
        }

        s = sb.toString();

        return removeDuplicateLettersLukeRcursive3(s);
    }

    /**
     * Luke - Wrong
     *
     * final String s = "bcabc";
     */
    private String removeDuplicateLettersLukeRcursive3(String s) {
        for (int i = 0, len = s.length(); i < len; i++) {
            char ch = s.charAt(i);
            if (i + 1 < len && s.indexOf(ch, i + 1) == -1) {
                continue;
            }

            if (i + 1 < len && s.indexOf(ch, i + 1) >= i + 1) {
                if (ch > s.charAt(i + 1)) {
                    return s.substring(0, i) + removeDuplicateLettersLukeRcursive3(s.substring(i + 1));
                } else {
                    return s.substring(0, i + 1) + removeDuplicateLettersLukeRcursive3(s.substring(i + 1).replaceAll(String.valueOf(ch), ""));
                }
            }
        }

        return s;
    }
}
