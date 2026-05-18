package com.learn.other;


import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.function.BiFunction;

import org.junit.jupiter.api.Assertions;

import com.learn.util.StringUtils;

import lombok.extern.log4j.Log4j2;


/**
 * LC - 150 - Evaluate Reverse Polish Notation
 *
 * Medium
 *
 * Evaluate the value of an arithmetic expression in Reverse Polish Notation.
 * Valid operators are +, -, *, and /. Each operand may be an integer or another expression.
 * Note that division between two integers should truncate toward zero.
 * It is guaranteed that the given RPN expression is always valid. That means the expression would always evaluate to a result,
 * and there will not be any division by zero operation.
 *
 * Example 1:
 * Input: tokens = ["2","1","+","3","*"]
 * Output: 9
 * Explanation: ((2 + 1) * 3) = 9
 *
 * Example 2:
 * Input: tokens = ["4","13","5","/","+"]
 * Output: 6
 * Explanation: (4 + (13 / 5)) = 6
 *
 * Example 3:
 * Input: tokens = ["10","6","9","3","+","-11","*","/","*","17","+","5","+"]
 * Output: 22
 * Explanation: ((10 * (6 / ((9 + 3) * -11))) + 17) + 5
 * = ((10 * (6 / (12 * -11))) + 17) + 5
 * = ((10 * (6 / -132)) + 17) + 5
 * = ((10 * 0) + 17) + 5
 * = (0 + 17) + 5
 * = 17 + 5
 * = 22
 *
 * Constraints:
 *     1 <= tokens.length <= 104
 *     tokens[i] is either an operator: "+", "-", "*", or "/", or an integer in the range [-200, 200].
 */
@Log4j2
public class EvaluateReversePolishNotation {

    public static void main(String[] args) {

        /**
         * Output: 9
         */
        // final String[] tokens = { "2", "1", "+", "3", "*" };

        /**
         * Output: 6
         */
        // final String[] tokens = { "4", "13", "5", "/", "+" };

        /**
         * Output: 22
         */
        final String[] tokens = { "10", "6", "9", "3", "+", "-11", "*", "/", "*", "17", "+", "5", "+" };

        EvaluateReversePolishNotation evaluateReversePolishNotation = new EvaluateReversePolishNotation();

        int evalRPNLukeNoLambda = evaluateReversePolishNotation.evalRPNLukeNoLambda(tokens);
        log.debug("RPN: {}", () -> evalRPNLukeNoLambda);
        log.debug("RPN {} OK", () -> "evalRPNLukeNoLambda");

        int evalRPNLukeLambda = evaluateReversePolishNotation.evalRPNLcLambda(tokens);
        Assertions.assertEquals(evalRPNLukeNoLambda, evalRPNLukeLambda);
        log.debug("RPN {} OK", () -> "evalRPNLukeLambda");

        Assertions.assertTrue(StringUtils.isNumeric("22331"));
        Assertions.assertFalse(StringUtils.isNumeric("-22331"));
        Assertions.assertFalse(StringUtils.isNumeric("222www331"));
    }

    /**
     * LC - Lambda Map
     *
     * Runtime: 9 ms, faster than 57.11% of Java online submissions for Evaluate Reverse Polish Notation.
     * Memory Usage: 43.8 MB, less than 76.43% of Java online submissions for Evaluate Reverse Polish Notation.
     *
     * Time: O(N)
     * Space: O(N)
     */
    public int evalRPNLcLambda(String[] tokens) {
        if (tokens == null || tokens.length == 0) {
            return 0;
        }

        if (tokens.length == 1) {
            return Integer.valueOf(tokens[0]);
        }

        if (tokens.length == 2) {
            return -1;
        }

        final Stack<Integer> stack = new Stack<>();

        final Map<String, BiFunction<Integer, Integer, Integer>> opMap = new HashMap<>();

        BiFunction<Integer, Integer, Integer> ADD = (a, b) -> a + b;
        BiFunction<Integer, Integer, Integer> SUB = (a, b) -> {
            return a - b;
        };
        BiFunction<Integer, Integer, Integer> MUL = (a, b) -> a * b;
        BiFunction<Integer, Integer, Integer> DIV = (a, b) -> a / b;

        opMap.put("+", ADD);
        opMap.put("-", SUB);
        opMap.put("*", MUL);
        opMap.put("/", DIV);

        stack.add(Integer.valueOf(tokens[0]));
        stack.add(Integer.valueOf(tokens[1]));

        for (int i = 2; i < tokens.length; i++) {
            String str = tokens[i];

            if (opMap.containsKey(str)) {
                int b = stack.pop();
                int a = stack.pop();
                int c = opMap.get(str).apply(a, b);
                stack.add(c);
            } else {
                stack.add(Integer.valueOf(str));
            }

            log.debug("---2222--- str: {}, stack: {}", () -> str, () -> stack);
        }

        return stack.pop();
    }

    /**
     * Luke - Iterative + Stack<Integer>
     *
     * Runtime: 7 ms, faster than 81.26% of Java online submissions for Evaluate Reverse Polish Notation.
     * Memory Usage: 44.3 MB, less than 40.06% of Java online submissions for Evaluate Reverse Polish Notation.
     *
     * Time: O(N)
     * Space: O(N)
     */
    public int evalRPNLukeNoLambda(String[] tokens) {
        if (tokens == null || tokens.length == 0) {
            return 0;
        }

        if (tokens.length == 1) {
            return Integer.valueOf(tokens[0]);
        }

        if (tokens.length == 2) {
            return -1;
        }

        Stack<Integer> stack = new Stack<>();

        stack.add(Integer.valueOf(tokens[0]));
        stack.add(Integer.valueOf(tokens[1]));

        for (int i = 2; i < tokens.length; i++) {
            String str = tokens[i];

            switch (str) {
                case "+":
                    stack.add(stack.pop() + stack.pop());
                    break;
                case "-":
                    stack.add(-stack.pop() + stack.pop());
                    break;
                case "*":
                    stack.add(stack.pop() * stack.pop());
                    break;
                case "/":
                    int tmp = stack.pop();
                    stack.add(stack.pop() / tmp);
                    break;
                default:
                    stack.add(Integer.valueOf(str));
                    break;
            }

            log.debug("---1111--- str: {}, stack: {}", () -> str, () -> stack);
        }

        return stack.pop();
    }

    /**
     * Not in use. Added for reminiscence only.
     */
    public static boolean isNumeric(final String str) {
        if (str == null || str.length() == 0) {
            return false;
        }
        return str.chars().allMatch(Character::isDigit);
    }
}
