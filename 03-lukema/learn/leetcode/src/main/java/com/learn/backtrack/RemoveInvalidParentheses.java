package com.learn.backtrack;


import java.util.ArrayList;
import java.util.List;

import lombok.extern.log4j.Log4j2;


/**
 * LC-301 Remove Invalid Parentheses
 *
 * Hard
 *
 * Given a string s that contains parentheses and letters, remove the minimum number of invalid parentheses to make the input string valid.
 *
 * Return all the possible results. You may return the answer in any order.
 *
 * Example 1:
 * Input: s = "()())()"
 * Output: ["(())()","()()()"]
 *
 * Example 2:
 * Input: s = "(a)())()"
 * Output: ["(a())()","(a)()()"]
 *
 * Example 3:
 * Input: s = ")("
 * Output: [""]
 *
 * Constraints:
 *     1 <= s.length <= 25
 *     s consists of lowercase English letters and parentheses '(' and ')'.
 *     There will be at most 20 parentheses in s.
 */
@Log4j2
public class RemoveInvalidParentheses {

    public static void main(String[] args) {

        /**
         * Expected: ["(())()","()()()"]
         */
        // final String s = "()())()";

        /**
         * Expected: ["(a())()","(a)()()"]
         */
        // final String s = "(a)())()";

        /**
         * Expected: [""]
         */
        // final String s = ")(";
        // final String s = ")";

        /**
         * Expected: ["a"]
         */
        // final String s = "a";
        // final String s = ")a))";

        /**
         * Expected: ["r()()","r(())","(r)()","(r())"]
         */
        // final String s = "(r(()()(";

        /**
         * Expected: ["l(())((z))","l((())(z))","l(((())z))"]
         */
        final String s = "l(((())((z))((";

        RemoveInvalidParentheses removeInvalidParentheses = new RemoveInvalidParentheses();

        var ret = removeInvalidParentheses.removeInvalidParentheses(s);
        log.debug("Remove Invalid Parentheses: {}, size: {}", () -> ret, () -> ret.size());
        log.debug("Remove Invalid Parentheses {} OK", () -> "ret");

    }

    /**
     * Luke: Trick 1: One way. Trick 2: `countLeft`, `countRight`, with `countRight > countLeft` as invalid expression.
     *
     * Runtime: 8 ms Beats 80.35%
     * Memory: 43 MB Beats 62.24%
     *
     * Time: O(2 ^ N)? <============ I think it is O(N ^ 2)
     * Space: O(N)
     */
    public List<String> removeInvalidParentheses(String s) {

        List<String> result = backtrack(s, 0, 0, 0);

        /**
         * reverse
         */
        List<String> listReversed = new ArrayList<>();
        for (String str : result) {
            listReversed.add(reverse(str));
        }

        List<String> tmpResult = new ArrayList<>();

        for (String str : listReversed) {
            var tmpList = backtrack(str, 0, 0, 0);
            tmpResult.addAll(tmpList);
        }

        result = new ArrayList<>();

        for (String str : tmpResult) {
            String tmp = reverse(str);
            if (!result.contains(tmp)) {
                result.add(tmp);
            }
        }

        return result;
    }

    /**
     * Time: O(2 ^ N)? <============ I think it is O(N ^ 2)
     * Space: O(N)
     */
    private List<String> backtrack(String s, int idxStart, int countLeft, int countRight) {

        List<String> result = new ArrayList<>();

        int curIdx = -1;

        // log.debug("----------input s: {}, idxStart: {}, countLeft: {}, countRight: {}", s, idxStart, countLeft, countRight);

        for (int i = idxStart; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (ch == '(') {
                countLeft++;
            } else if (ch == ')') {
                countRight++;
            }

            if (countLeft >= countRight) {
                continue;
            } else {
                curIdx = i;
                break;
            }
        }

        /**
         * no extra ')' to remove
         */
        if (curIdx == -1) {
            result.add(s);
            return result;
        }

        // log.debug("----------extra ')' detected. after removing next ')', s: {}, curIdx: {}", s, curIdx);

        /**
         * remove one ')' from startIdx to current
         */
        for (int i = 0; i <= curIdx; i++) {
            if (s.charAt(i) == ')') {
                String str = s.substring(0, i) + s.substring(i + 1);
                result.add(str);

                /**
                 * skip next ')' for repeating '))' patterns
                 */
                while (i + 1 <= curIdx && s.charAt(i + 1) == ')') {
                    i++;
                }
            }
        }

        // log.debug("------- after remove ')', result: {}", result);

        /**
         * after removing one ')', decrement countRight by 1
         */
        countRight--;

        List<String> newResult = new ArrayList<>();

        for (String str : result) {
            var list = backtrack(str, curIdx, countLeft, countRight);
            newResult.addAll(list);
        }

        return newResult;
    }

    /**
     * Time: O(N)
     * Space: O(N) --- need to build a StringBuilder
     */
    String reverse(String str) {
        StringBuilder sb = new StringBuilder(str);
        sb.reverse();
        for (int i = 0; i < sb.length(); i++) {
            if (sb.charAt(i) == ')') {
                sb.setCharAt(i, '(');
            } else if (sb.charAt(i) == '(') {
                sb.setCharAt(i, ')');
            }
        }

        return sb.toString();
    }

    boolean isValidNotInUse(String s) {
        int countLeft = 0;
        int countRight = 0;

        char[] chs = s.toCharArray();

        for (char ch : chs) {
            if (ch == '(') {
                countLeft++;
            } else if (ch == ')') {
                countRight++;
            }

            if (countRight > countLeft) {
                return false;
            }
        }

        return countLeft == countRight;
    }

}
