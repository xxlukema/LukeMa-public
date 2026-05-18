package com.learn.slidingwindow;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.extern.log4j.Log4j2;


/**
 * LC 76
 */
@Log4j2
public class MinWindowSubstring {

    public static void main(String[] args)
        throws IOException {

        // String s = "ADOBECODEBANC";
        // String t = "ACBC";
        // String t = "C";

        // String s = "ADOBE";
        // String t = "BD";

        // String s = "abc";
        // String t = "cba";

        Path path = Path.of("s.txt");
        String s = Files.readString(path);
        s = s.trim();

        path = Path.of("t.txt");
        String t = Files.readString(path);
        t = t.trim();

        log.debug("s.length(): {}, t.length(): {}", s.length(), t.length());

        MinWindowSubstring minWindowSubstring = new MinWindowSubstring();

        var ret = minWindowSubstring.minWindowLuke(s, t);
        // var ret = minWindowSubstring.minWindowLc(s, t);
        log.debug("Min Window Substring: {}", () -> ret);
        log.debug("Min Window Substring length: {}", () -> ret.length());

    }

    /**
     * LC: Sliding Window
     *
     * Runtime: 14 ms, faster than 74.71% of Java online submissions for Minimum Window Substring.
     * Memory Usage: 43 MB, less than 88.65% of Java online submissions for Minimum Window Substring.
     *
     * Time: O(s.length() + t.length())
     * Space: O(s.length() + t.length())
     */
    public String minWindowLc(String s, String t) {

        if (s.length() == 0 || t.length() == 0) {
            return "";
        }

        // Dictionary which keeps a count of all the unique characters in t.
        Map<Character, Integer> targetMap = new HashMap<Character, Integer>();

        for (int i = 0, n = t.length(); i < n; i++) {
            int count = targetMap.getOrDefault(t.charAt(i), 0);
            targetMap.put(t.charAt(i), count + 1);
        }

        // Number of unique characters in t, which need to be present in the desired window.
        int required = targetMap.size();

        // Left and Right pointer
        int left = 0, right = 0;

        // formed is used to keep track of how many unique characters in t
        // are present in the current window in its desired frequency.
        // e.g. if t is "AABC" then the window must have two A's, one B and one C.
        // Thus formed would be = 3 when all these conditions are met.
        int formed = 0;

        // Dictionary which keeps a count of all the unique characters in the current window.
        Map<Character, Integer> srcMap = new HashMap<Character, Integer>();

        // ans list of the form (window length, left, right)
        int[] ans = { -1, 0, 0 };

        while (right < s.length()) {
            // Add one character from the right to the window
            char ch = s.charAt(right);
            int count = srcMap.getOrDefault(ch, 0);
            srcMap.put(ch, count + 1);

            // If the frequency of the current character added equals to the
            // desired count in t then increment the formed count by 1.
            /**
             * TODO: Caution! Use "Integer.intValue()" with "=="
             */
            if (targetMap.containsKey(ch) && srcMap.get(ch).intValue() == targetMap.get(ch).intValue()) {
                formed++;
            }

            // Try and contract the window till the point where it ceases to be 'desirable'.
            while (left <= right && formed == required) {
                ch = s.charAt(left);
                // Save the smallest window until now.
                if (ans[0] == -1 || right - left + 1 < ans[0]) {
                    ans[0] = right - left + 1;
                    ans[1] = left;
                    ans[2] = right;
                }

                // The character at the position pointed by the
                // `Left` pointer is no longer a part of the window.
                srcMap.put(ch, srcMap.get(ch) - 1);
                if (targetMap.containsKey(ch) && srcMap.get(ch).intValue() < targetMap.get(ch).intValue()) {
                    formed--;
                }

                // Move the left pointer ahead, this would help to look for a new window.
                left++;
            }

            // Keep expanding the window once we are done contracting.
            right++;
        }

        return ans[0] == -1 ? "" : s.substring(ans[1], ans[2] + 1);
    }

    /**
     * Luke: Sliding Window
     *
     * Runtime: 34 ms, faster than 26.09% of Java online submissions for Minimum Window Substring.
     * Memory Usage: 47.1 MB, less than 39.68% of Java online submissions for Minimum Window Substring.
     *
     * Time: O(s.length() + t.length())
     * Space: O(s.length() + t.length())
     */
    public String minWindowLuke(String s, String t) {

        if (s.length() < t.length()) {
            return "";
        }

        if (s.equals(t)) {
            return t;
        }

        /**
         * targetMap
         */
        Map<Character, Integer> targetMap = new HashMap<>();

        /**
         * Init targetMap
         */
        for (int i = 0, n = t.length(); i < n; i++) {
            Character ch = t.charAt(i);
            int count = targetMap.getOrDefault(ch, 0);
            targetMap.put(ch, ++count);
        }

        int left = 0;
        int right = left;
        int numberOfMatches = 0;
        boolean isFormed = false;
        Map<Character, Integer> srcMap = new HashMap<>();

        record Window(int left, int right) {
        }

        List<Window> windowList = new ArrayList<>();

        /**
         * "<=" is for "s.length() = t.length()"
         */
        while (left <= s.length() - t.length()) {

            /**
             * 1. Move right pointer until all target chars are matched.
             */
            while (!isFormed && right < s.length()) {
                Character ch = s.charAt(right++);
                if (targetMap.containsKey(ch)) {
                    int count = srcMap.getOrDefault(ch, 0);
                    srcMap.put(ch, ++count);
                    /**
                     * If a matching char in the src window is included more times than in target, only count the same match fequency.
                     * For more maching or less match, do not count it.
                     */
                    if (count == targetMap.get(ch)) {
                        numberOfMatches++;
                    }
                }

                /**
                 * 2. Mark "isFormed = true" when full match is found.
                 */
                if (numberOfMatches == targetMap.size()) {
                    isFormed = true;
                }
            }

            /**
             * 3. Move left pointer to right until break the "isFormed" to calculate window width.
             */
            while (isFormed && left <= s.length() - t.length()) {
                Character ch = s.charAt(left++);
                if (targetMap.containsKey(ch)) {

                    /**
                     * TODO: Problem of "var"!
                     *
                     * var is treated as "Integer". It will cause errornous result at line "if (count == targetMap.get(ch))".
                     * Unbox it into int is correct way to do it.
                     */
                    // var count = srcMap.getOrDefault(ch, 0);

                    int count = srcMap.getOrDefault(ch, 0);

                    /**
                     * decrease the counter "numberOfMatches" if a match char is slided out of the window.
                     */
                    if (count == targetMap.get(ch)) {
                        numberOfMatches--;

                        /**
                         * 4. Save the window.
                         */
                        windowList.add(new Window(left - 1, right - 1));
                        isFormed = false;
                    }
                    srcMap.put(ch, --count);
                }
            }

            if (!isFormed && right == s.length()) {
                break;
            }
        }

        /**
         * 4. Get the minimum window.
         */
        if (windowList.size() == 0) {
            return "";
        } else {
            Window window = windowList.stream().sorted((a, b) -> (a.right - a.left) - (b.right - b.left)).collect(Collectors.toList()).get(0);

            return s.substring(window.left, window.right + 1);
        }
    }
}
