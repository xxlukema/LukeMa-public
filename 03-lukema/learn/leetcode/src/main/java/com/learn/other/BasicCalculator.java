package com.learn.other;


import java.util.Stack;

import lombok.extern.log4j.Log4j2;


/**
 * LC -224 - Basic Calculator
 *
 * Hard
 *
 * Given a string s representing a valid expression, implement a basic calculator to evaluate it, and return the result of the evaluation.
 *
 * Note: You are not allowed to use any built-in function which evaluates strings as mathematical expressions, such as eval().
 *
 * Example 1:
 * Input: s = "1 + 1"
 * Output: 2
 *
 * Example 2:
 * Input: s = " 2-1 + 2 "
 * Output: 3
 *
 * Example 3:
 * Input: s = "(1+(4+5+2)-3)+(6+8)"
 * Output: 23
 *
 * Constraints:
 *     1 <= s.length <= 3 * 105
 *     s consists of digits, '+', '-', '(', ')', and ' '.
 *     s represents a valid expression.
 *     '+' is not used as a unary operation (i.e., "+1" and "+(2 + 3)" is invalid).
 *     '-' could be used as a unary operation (i.e., "-1" and "-(2 + 3)" is valid).
 *     There will be no two consecutive operators in the input.
 *     Every number and running calculation will fit in a signed 32-bit integer.
 */
@Log4j2
public class BasicCalculator {

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

        BasicCalculator basicCalculator = new BasicCalculator();

        var ret = basicCalculator.calculateLuke(s);
        log.debug("Basic Calculator: {}", () -> ret);
        log.debug("Basic Calculator {} OK", () -> "ret");

    }

    /**
     * Luke - Stack
     *
     * Runtime: 126 ms, faster than 7.12% of Java online submissions for Basic Calculator.
     * Memory Usage: 57.8 MB, less than 7.87% of Java online submissions for Basic Calculator.
     *
     * Time: O(N)
     * Space: O(N) Stack<String> size
     */
    public int calculateLuke(String s) {
        s = s.replaceAll(" ", "");

        int sum = 0;

        int pos = 0;
        final int LEN = s.length();

        boolean isAdd = true;

        Stack<String> stack = new Stack<>();

        int openParenthesisCounter = 0;

        while (pos < LEN) {
            char ch = s.charAt(pos++);

            // log.debug("ch: {}, pos: {}, sum: {}, statck: {}", ch, pos, sum, stack);

            if (ch == '(') {
                //
                StringBuilder sb = new StringBuilder();
                stack.push(String.valueOf(ch));
                sb.append(ch);
                openParenthesisCounter++;
            } else if (ch == ')') {
                //
                Stack<String> localStack = new Stack<>();

                String str = null;
                while (!(str = stack.pop()).equals("(")) {
                    localStack.add(str);
                }
                int localSum = 0;
                boolean localIsAdd = true;

                while (!localStack.isEmpty()) {
                    str = localStack.pop();
                    if (str.equals("+")) {
                        localIsAdd = true;
                    } else if (str.equals("-")) {
                        localIsAdd = false;
                    } else {
                        if (localIsAdd) {
                            localSum += Integer.valueOf(str);
                        } else {
                            localSum -= Integer.valueOf(str);
                        }
                    }
                }

                openParenthesisCounter--;

                if (openParenthesisCounter > 0) {
                    stack.add(String.valueOf(localSum));
                } else {
                    if (isAdd) {
                        sum += localSum;
                    } else {
                        sum -= localSum;
                    }
                }

                // log.debug("---=========- sum: {}, localSum: {}: stack: {}, localStack: {}", sum, localSum, stack, localStack);

            } else if (ch == '+') {
                if (openParenthesisCounter > 0) {
                    stack.add(String.valueOf(ch));
                } else {
                    isAdd = true;
                }
            } else if (ch == '-') {
                if (openParenthesisCounter > 0) {
                    stack.add(String.valueOf(ch));
                } else {
                    isAdd = false;

                    // log.debug("-------- toggled");
                }
            } else {
                StringBuffer sb = new StringBuffer();
                sb.append(ch);
                while (pos < LEN && Character.isDigit((ch = s.charAt(pos++)))) {
                    sb.append(ch);
                }

                if (!Character.isDigit(ch)) {
                    pos--;
                }

                // log.debug("-----===-sb: {}, isAdd: {}", sb.toString(), isAdd);

                if (openParenthesisCounter == 0) {
                    int num = Integer.valueOf(sb.toString());
                    if (isAdd) {
                        sum += num;
                    } else {
                        sum -= num;
                    }
                } else {
                    stack.add(sb.toString());
                }
            }
        }

        return sum;
    }

    /**
     * LC - Stack - String Reversal
     *
     * Time: O(N)
     * Space: O(N)
     */
    public int calculateLcStringReversal(String s) {

        int operand = 0;
        int n = 0;
        Stack<Object> stack = new Stack<>();

        for (int i = s.length() - 1; i >= 0; i--) {

            char ch = s.charAt(i);

            if (Character.isDigit(ch)) {

                // Forming the operand - in reverse order.
                operand = (int) Math.pow(10, n) * (int) (ch - '0') + operand;
                n += 1;

            } else if (ch != ' ') {
                if (n != 0) {

                    // Save the operand on the stack
                    // As we encounter some non-digit.
                    stack.push(operand);
                    n = 0;
                    operand = 0;

                }
                if (ch == '(') {

                    int res = evaluateExprLc(stack);
                    stack.pop();

                    // Append the evaluated result to the stack.
                    // This result could be of a sub-expression within the parenthesis.
                    stack.push(res);

                } else {
                    // For other non-digits just push onto the stack.
                    stack.push(ch);
                }
            }
        }

        //Push the last operand to stack, if any.
        if (n != 0) {
            stack.push(operand);
        }

        // Evaluate any left overs in the stack.
        return evaluateExprLc(stack);
    }

    private int evaluateExprLc(Stack<Object> stack) {

        // If stack is empty or the expression starts with
        // a symbol, then append 0 to the stack.
        // i.e. [1, '-', 2, '-'] becomes [1, '-', 2, '-', 0]
        if (stack.empty() || !(stack.peek() instanceof Integer)) {
            stack.push(0);
        }

        int res = (int) stack.pop();

        // Evaluate the expression till we get corresponding ')'
        while (!stack.empty() && !((char) stack.peek() == ')')) {

            char sign = (char) stack.pop();

            if (sign == '+') {
                res += (int) stack.pop();
            } else {
                res -= (int) stack.pop();
            }
        }
        return res;
    }

    /**
     * LC - Stack - No String Reversal
     *
     * Time: O(N)
     * Space: O(N)
     */
    public int calculateLcNoStringReversal(String s) {

        Stack<Integer> stack = new Stack<>();
        int operand = 0;
        int result = 0; // For the on-going result
        int sign = 1; // 1 means positive, -1 means negative

        for (int i = 0; i < s.length(); i++) {

            char ch = s.charAt(i);
            if (Character.isDigit(ch)) {

                // Forming operand, since it could be more than one digit
                operand = 10 * operand + (int) (ch - '0');

            } else if (ch == '+') {

                // Evaluate the expression to the left,
                // with result, sign, operand
                result += sign * operand;

                // Save the recently encountered '+' sign
                sign = 1;

                // Reset operand
                operand = 0;

            } else if (ch == '-') {

                result += sign * operand;
                sign = -1;
                operand = 0;

            } else if (ch == '(') {

                // Push the result and sign on to the stack, for later
                // We push the result first, then sign
                stack.push(result);
                stack.push(sign);

                // Reset operand and result, as if new evaluation begins for the new sub-expression
                sign = 1;
                result = 0;

            } else if (ch == ')') {

                // Evaluate the expression to the left
                // with result, sign and operand
                result += sign * operand;

                // ')' marks end of expression within a set of parenthesis
                // Its result is multiplied with sign on top of stack
                // as stack.pop() is the sign before the parenthesis
                result *= stack.pop();

                // Then add to the next operand on the top.
                // as stack.pop() is the result calculated before this parenthesis
                // (operand on stack) + (sign on stack * (result from parenthesis))
                result += stack.pop();

                // Reset the operand
                operand = 0;
            }
        }
        return result + (sign * operand);
    }
}
