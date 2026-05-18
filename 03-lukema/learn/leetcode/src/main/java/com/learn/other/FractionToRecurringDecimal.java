package com.learn.other;


import java.util.HashMap;
import java.util.Map;

import lombok.extern.log4j.Log4j2;


/**
 * LC - 166 - Fraction to Recurring Decimal
 *
 * Medium
 *
 * Given two integers representing the numerator and denominator of a fraction, return the fraction in string format.
 * If the fractional part is repeating, enclose the repeating part in parentheses.
 * If multiple answers are possible, return any of them.
 * It is guaranteed that the length of the answer string is less than 104 for all the given inputs.
 *
 * Example 1:
 * Input: numerator = 1, denominator = 2
 * Output: "0.5"
 *
 * Example 2:
 * Input: numerator = 2, denominator = 1
 * Output: "2"
 *
 * Example 3:
 * Input: numerator = 4, denominator = 333
 * Output: "0.(012)"
 *
 * Constraints:
 *     -2 ^ 31 <= numerator, denominator <= 2 ^ 31 - 1
 *     denominator != 0
 */
@Log4j2
public class FractionToRecurringDecimal {

    public static void main(String[] args) {

        final int numerator = 4, denominator = 333;

        FractionToRecurringDecimal fractionToRecurringDecimal = new FractionToRecurringDecimal();

        var ret = fractionToRecurringDecimal.fractionToDecimalLuke(numerator, denominator);
        log.debug("Fraction to recurring decimal: {}", () -> ret);
        log.debug("Fraction to recurring decimal {} OK", () -> "ret");

    }

    /**
     * LC
     */
    public String fractionToDecimal(int numerator, int denominator) {
        if (numerator == 0) {
            return "0";
        }
        StringBuilder fraction = new StringBuilder();
        // If either one is negative (not both)
        if (numerator < 0 ^ denominator < 0) {
            fraction.append("-");
        }
        // Convert to Long or else abs(-2147483648) overflows
        long dividend = Math.abs(Long.valueOf(numerator));
        long divisor = Math.abs(Long.valueOf(denominator));
        fraction.append(String.valueOf(dividend / divisor));
        long remainder = dividend % divisor;
        if (remainder == 0) {
            return fraction.toString();
        }
        fraction.append(".");
        Map<Long, Integer> map = new HashMap<>();
        while (remainder != 0) {
            if (map.containsKey(remainder)) {
                fraction.insert(map.get(remainder), "(");
                fraction.append(")");
                break;
            }
            map.put(remainder, fraction.length());
            remainder *= 10;
            fraction.append(String.valueOf(remainder / divisor));
            remainder %= divisor;
        }
        return fraction.toString();
    }

    /**
     * Luke
     */
    public String fractionToDecimalLuke(int numerator, int denominator) {
        double fraction = (double) numerator / denominator;
        String str = String.valueOf(fraction);

        if (str.indexOf(".") == -1) {
            return str;
        }

        String[] fields = str.split("\\.");
        String decimal = fields[1];

        decimal = backtrackFindRepeat(decimal, 0, 0);

        return fields[0] + "." + decimal;
    }

    private String backtrackFindRepeat(String decimal, int left, int right) {

        if (left == 0 && right == 0) {

        }

        return null;
    }
}
