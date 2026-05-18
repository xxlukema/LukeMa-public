package com.learn.other;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import lombok.extern.log4j.Log4j2;


/**
 * LC - 282 - Expression And Operators
 *
 * Hard
 *
 * Given a string num that contains only digits and an integer target, return all possibilities to insert the binary
 * operators '+', '-', '/', or '*' between the digits of num so that the resultant expression evaluates to the target value.
 *
 * Note that operands in the returned expressions should not contain leading zeros.
 *
 * Example 1:
 * Input: num = "123", target = 6
 * Output: ["1*2*3","1+2+3"]
 * Explanation: Both "1*2*3" and "1+2+3" evaluate to 6.
 *
 * Example 2:
 * Input: num = "232", target = 8
 * Output: ["2*3+2","2+3*2"]
 * Explanation: Both "2*3+2" and "2+3*2" evaluate to 8.
 *
 * Example 3:
 * Input: num = "3456237490", target = 9191
 * Output: []
 * Explanation: There are no expressions that can be created from "3456237490" to evaluate to 9191.
 *
 * Constraints:
 *     1 <= num.length <= 10
 *     num consists of only digits.
 *     -2 ^ 31 <= target <= 2 ^ 31 - 1
 */
@Log4j2
public class ExpressionAndOperators {

    public static void main(String[] args) {

        final String num = "123";
        final int target = 6;

        ExpressionAndOperators expressionAndOperators = new ExpressionAndOperators();
        var ret = expressionAndOperators.addOperators(num, target);
        log.debug("Expression And Operators: {}", () -> ret);
        log.debug("Expression And Operators {} OK", () -> "ret");

    }

    /**
     * Luke - Break and Conquer - Recursion
     */
    public List<String> addOperators(String num, int target) {
        List<String> result = new ArrayList<>();

        LinkedList<ExpressionValue> llist = new LinkedList<>();

        addOperators(num, target, result, llist);

        return result;
    }

    record ExpressionValue(String expr, int value) {
    }

    private void addOperators(
            final String num,
            final int target,
            final List<String> result,
            final LinkedList<ExpressionValue> llist) {

        // edge condition
        if (num == null || num.isEmpty()) {
            return;
        }

        for (int i = 0, len = num.length() - 1; i < len; i++) {
            String left = num.substring(0, i + 1);
            String right = num.substring(i + 1, num.length());

            if (right.startsWith("0")) {
                continue;
            }

            int leftValue = Integer.parseInt(left);

            if (llist.isEmpty()) {
                // add:
                llist.add(new ExpressionValue(left + "+", leftValue));
                addOperators(num, target - leftValue, result, llist);
                // minus
                llist.add(new ExpressionValue(left + "-", leftValue));
                addOperators(num, target + leftValue, result, llist);
                // multiply
                llist.add(new ExpressionValue(left + "*", leftValue));
                addOperators(num, target / leftValue, result, llist);
                // divide
                llist.add(new ExpressionValue(left + "/", leftValue));
                addOperators(num, target * leftValue, result, llist);
            } else {
                llist.forEach(_ -> {

                });
            }

        }

    }

    enum Ops {
        ADD, MINUS, MULTIPLY, DIVIDE;
    }
}
