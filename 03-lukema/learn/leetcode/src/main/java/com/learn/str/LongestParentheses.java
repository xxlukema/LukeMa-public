package com.learn.str;


import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import lombok.extern.log4j.Log4j2;


@Log4j2
public class LongestParentheses {

    public static void main(String[] args) {
        LongestParentheses longestParentheses = new LongestParentheses();

        // String s = ")()())";
        // String s = "((()))())";
        // String s = "(()";
        // String s = ")(())))(())())";
        // String s = "()";
        String s = "()(())";

        int len = longestParentheses.longestValidParenthesesDpMine(s);
        log.info("dp: {}", len);

        len = longestParentheses.longestValidParenthesesStackMine(s);
        log.info("Stack: {}", len);

        len = longestParentheses.longestValidParenthesesBruteForce(s);
        log.info("Brute Force: {}", len);
    }

    public int longestValidParenthesesStackMine(String s) {
        Stack<Integer> stack = new Stack<>();
        int maxLen = 0;
        stack.push(-1);
        for (int i = 0, n = s.length(); i < n; i++) {
            char ch = s.charAt(i);
            if (ch == '(') {
                stack.push(i);
            } else {
                stack.pop();
                if (stack.isEmpty()) {
                    stack.push(i);
                } else {
                    maxLen = Math.max(maxLen, i - stack.peek());
                }
            }
        }
        return maxLen;
    }

    public int longestValidParenthesesStackStandard(String s) {

        int maxans = 0;
        Stack<Integer> stack = new Stack<>();
        stack.push(-1);

        log.debug("statck: {}", () -> stack);

        for (int i = 0, n = s.length(); i < n; i++) {
            char ch = s.charAt(i);
            if (ch == '(') {
                stack.push(i);
            } else {
                stack.pop();
                if (stack.empty()) {
                    stack.push(i);
                } else {
                    maxans = Math.max(maxans, i - stack.peek());
                }
            }

            log.debug("statck: {}, maxans: {}", stack, maxans);
        }
        return maxans;
    }

    private int longestValidParenthesesDpMine(String s) {
        int maxLen = 0;
        int[] dp = new int[s.length()];
        for (int i = 0, n = s.length(); i < n; i++) {
            char ch = s.charAt(i);
            if (ch == ')') {
                if (s.charAt(i - 1) == '(') {
                    dp[i] = dp[i - 1] + 2;
                    if (i - 2 >= 0) {
                        dp[i] += dp[i - 1];
                    }
                } else {
                    int pos = i - dp[i] - 1;
                    if (pos >= 0) {
                        if (s.charAt(pos) == ')') {
                            dp[i] = (dp[i - 1] + 2) + dp[pos];
                        } else {
                            dp[i] = (dp[i - 1] + 2);
                            if (pos - 1 >= 0) {
                                dp[i] += dp[pos - 1];
                            }
                        }
                    }
                }
            }
            maxLen = Math.max(maxLen, dp[i]);
        }
        return maxLen;
    }

    public int longestValidParenthesesDpMine2(String s) {
        int maxLen = 0;
        int[] dp = new int[s.length()];
        for (int i = 1, n = s.length(); i < n; i++) {
            char ch = s.charAt(i);
            if (ch == ')') {
                if (s.charAt(i - 1) == '(') {
                    dp[i] = (i - 2 > 0 ? dp[i - 2] : 0) + 2;
                } else {
                    int pos = i - dp[i - 1] - 1;
                    if (pos >= 0) {
                        if (s.charAt(pos) == '(') {
                            dp[i] = dp[i - 1] + 2;
                            if (pos - 1 >= 0) {
                                dp[i] += dp[pos - 1];
                            }
                        }
                    }
                }
            }
            maxLen = Math.max(maxLen, dp[i]);
        }
        return maxLen;
    }

    public int longestValidParenthesesDpStandard(String s) {
        int maxans = 0;
        int dp[] = new int[s.length()];
        for (int i = 1, n = s.length(); i < n; i++) {
            if (s.charAt(i) == ')') {
                if (s.charAt(i - 1) == '(') {
                    dp[i] = (i >= 2 ? dp[i - 2] : 0) + 2;
                } else if (i - dp[i - 1] > 0 && s.charAt(i - dp[i - 1] - 1) == '(') {
                    dp[i] = dp[i - 1] + ((i - dp[i - 1]) >= 2 ? dp[i - dp[i - 1] - 2] : 0) + 2;
                }
                maxans = Math.max(maxans, dp[i]);
            }
        }
        return maxans;
    }

    public int longestValidParenthesesBruteForce(String s) {

        List<String> list = allValidParentheses(s);

        if (list.size() == 0) {
            return 0;
        } else if (list.size() == 1) {
            return list.get(0).length();
        }

        int maxLen = 0;
        String pre = list.get(0);
        int pos = s.indexOf(pre);
        StringBuilder sb = new StringBuilder();
        sb.append(pre);
        for (int i = 1; i < list.size(); i++) {
            String curr = list.get(i);
            int endPlusOne = pos + pre.length();
            int idx = s.indexOf(curr, endPlusOne);
            if (idx != endPlusOne) {
                maxLen = Math.max(maxLen, sb.length());
                sb.delete(0, sb.length());
            }

            sb.append(curr);
            pre = curr;
            pos = idx;
        }

        return Math.max(maxLen, sb.length());
    }

    private List<String> allValidParentheses(String s) {

        List<String> list = new ArrayList<>();

        int idx = s.indexOf('(');
        while (idx > -1 && idx < s.length() - 1) {
            String str = firstValidParentheses(s, idx);
            if (str == null) {
                idx = s.indexOf('(', idx + 1);
            } else {
                list.add(str);
                idx = s.indexOf('(', idx + str.length());
            }
        }

        return list;
    }

    private String firstValidParentheses(String s, int left) {
        char[] str = s.toCharArray();
        Stack<Character> stack = new Stack<>();
        int right = left;
        while (right < str.length) {
            char ch = str[right++];
            if (ch == ')') {
                if (stack.size() == 0) {
                    return null;
                } else {
                    if (stack.peek() == '(') {
                        stack.pop();
                        if (stack.size() == 0) {
                            return s.substring(left, right);
                        } else {
                            continue;
                        }
                    } else {
                        stack.push(ch);
                        continue;
                    }
                }
            } else {
                stack.push(ch);
            }
        }

        return null;
    }

}
