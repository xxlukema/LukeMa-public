package com.learn.other;


import java.util.Stack;

import lombok.extern.log4j.Log4j2;


/**
 * LC - 227 Basic Calculator II
 *
 * Medium
 *
 * Given a string s which represents an expression, evaluate this expression and return its value.
 *
 * The integer division should truncate toward zero.
 *
 * You may assume that the given expression is always valid. All intermediate results will be in the range of [-2 ^ 31, 2 ^ 31 - 1].
 * Note: You are not allowed to use any built-in function which evaluates strings as mathematical expressions, such as eval().
 *
 * Example 1:
 * Input: s = "3+2*2"
 * Output: 7
 *
 * Example 2:
 * Input: s = " 3/2 "
 * Output: 1
 *
 * Example 3:
 * Input: s = " 3+5 / 2 "
 * Output: 5
 *
 * Constraints:
 *     1 <= s.length <= 3 * 105
 *     s consists of integers and operators ('+', '-', '*', '/') separated by some number of spaces.
 *     s represents a valid expression.
 *     All the integers in the expression are non-negative integers in the range [0, 2 ^ 31 - 1].
 *     The answer is guaranteed to fit in a 32-bit integer.
 */
@Log4j2
public class BasicCalculatorII {

    public static void main(String[] args) {

        /**
         * Expect: 23
         */
        // final String s = "(1+(4+5+2)-3 )+(6+8)";
        // final String s = "2 + 13 - 1"; //14
        // final String s = "2 + 13 - 1"; //14
        // final String s = "2 + (13 - 1)"; //14
        // final String s = "2 - (13 + 1)"; // - 12
        // final String s = "(1-(3-4))"; // 2
        final String s = "(1+(4+5+2)-3)+(6+8)"; //  9 + 14 = 23

        BasicCalculatorII basicCalculatorII = new BasicCalculatorII();

        var ret = basicCalculatorII.calculate(s);
        log.debug("Basic Calculator II: {}", () -> ret);
        log.debug("Basic Calculator II {} OK", () -> "ret");
    }

    public int calculate(String s) {
        final int N = s.length();

        int sum = 0;

        int pos = 0;

        Stack<String> stack = new Stack<>();
        int openParenthesisCounter = 0;

        while (pos < N) {
            char ch = s.charAt(pos++);

            if (Character.isDigit(ch)) {
                // digits
                StringBuilder sb = new StringBuilder();
                sb.append(ch);

                while (pos < N && Character.isDigit(ch = s.charAt(pos++))) {
                    sb.append(ch);
                }

                if (pos < N) {
                    pos--;
                }

                if (openParenthesisCounter > 0) {
                    stack.add(sb.toString());
                }

                //

            } else if (ch == '(') {
                //
                openParenthesisCounter++;
                stack.add(String.valueOf(ch));
            } else if (ch == ')') {
                //
                openParenthesisCounter--;
                String str = stack.pop();
                if (str.equals("(")) {
                    //
                }
            } else if (ch == '+') {
                //
            } else if (ch == '-') {
                //
            } else if (ch == '*') {
                //
            } else if (ch == '/') {
                //
            } else {
                continue;
            }

        }

        return sum;
    }
}
