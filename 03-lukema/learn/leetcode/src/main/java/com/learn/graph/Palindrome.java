package com.learn.graph;


import java.util.HashMap;
import java.util.Map;


public class Palindrome {

}


class Solution {

    Map<String, Boolean> memo = new HashMap<>();

    public int countSubstrings(String s) {
        memo.clear();
        if (s == null || s.length() == 0) {
            return 0;
        }
        int counter = 0;
        final int N = s.length();
        for (int i = 0; i < N; i++) {
            for (int k = i; k < N; k++) {
                if (isPalindrome(s, i, k)) {
                    counter++;
                }
            }
        }
        return counter;
    }

    boolean isPalindrome(String s, int start, int end) {
        String str = s.substring(start, end + 1);
        Boolean value = memo.get(str);

        if (value != null) {
            return value;
        }

        final int N = str.length();

        if (N == 1) {
            value = true;
        } else if (N == 2 || N == 3) {
            value = s.charAt(start) == s.charAt(end);
        } else {
            if (s.charAt(start) == s.charAt(end)) {
                value = isPalindrome(s, start + 1, end - 1);
            } else {
                value = false;
            }
        }

        if (value) {
            memo.put(str, value);
        }
        return value;
    }
}
