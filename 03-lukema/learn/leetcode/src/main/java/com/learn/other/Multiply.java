package com.learn.other;

import static org.junit.jupiter.api.Assertions.assertEquals;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class Multiply {

    public static void main(String[] args) {

        String num1 = "123";
        String num2 = "456";
        // String num1 = "50";
        // String num2 = "5";

        Multiply multiply = new Multiply();
        String ret = multiply.multiply(num1, num2);
        log.info("multiply Luke: {}", () -> ret);

        String retLC = multiply.multiplyLC(num1, num2);
        log.info("multiply LC: {}", () -> retLC);

        assertEquals(ret, retLC);

    }

    public String multiply(String num1, String num2) {

        StringBuilder sb = new StringBuilder();

        for (int i = num1.length() - 1; i >= 0; i--) {
            char ch1 = num1.charAt(i);
            int int1 = ch1 - '0';

            for (int k = num2.length() - 1; k >= 0; k--) {
                char ch2 = num2.charAt(k);
                int int2 = ch2 - '0';
                int pos = (num2.length() - 1 - k) + (num1.length() - 1 - i);
                int prod = int1 * int2;
                add(sb, prod, pos);
            }
        }

        // Remove leading 0
        while (sb.length() > 1 && sb.charAt(0) == '0') {
            sb.delete(0, 1);
        }

        return sb.toString();
    }

    private void add(StringBuilder sb, int num, int pos) {

        if (sb.length() == 0) {
            sb.append(num);
            return;
        }

        while (sb.length() - 1 < pos) {
            sb.insert(0, 0);
        }

        int idx = sb.length() - 1 - pos;

        char ch = sb.charAt(idx);
        int val = ch - '0';
        num += val;
        int rem = num % 10;
        int carr = num / 10;
        sb.replace(idx, idx + 1, String.valueOf(rem));
        if (carr == 0) {
            return;
        }

        pos++;
        add(sb, carr, pos);
    }

    public String multiplyLC(String num1, String num2) {
        if (num1.equals("0") || num2.equals("0")) {
            return "0";
        }

        StringBuilder firstNumber = new StringBuilder(num1);
        StringBuilder secondNumber = new StringBuilder(num2);

        // Reverse both the numbers.
        firstNumber.reverse();
        secondNumber.reverse();

        // To store the multiplication result of each digit of secondNumber with firstNumber.
          final int N = firstNumber.length() + secondNumber.length();
        StringBuilder answer = new StringBuilder();
        for (int i = 0; i < N; ++i) {
            answer.append(0);
        }

        for (int place2 = 0; place2 < secondNumber.length(); place2++) {
            int digit2 = secondNumber.charAt(place2) - '0';

            // For each digit in secondNumber multiply the digit by all digits in firstNumber.
            for (int place1 = 0; place1 < firstNumber.length(); place1++) {
                int digit1 = firstNumber.charAt(place1) - '0';

                // The number of zeros from multiplying to digits depends on the 
                // place of digit2 in secondNumber and the place of the digit1 in firstNumber.
                int currentPos = place1 + place2;

                // The digit currently at position currentPos in the answer string
                // is carried over and summed with the current result.
                int carry = answer.charAt(currentPos) - '0';
                int multiplication = digit1 * digit2 + carry;

                // Set the ones place of the multiplication result.
                answer.setCharAt(currentPos, (char) (multiplication % 10 + '0'));

                // Carry the tens place of the multiplication result by 
                // adding it to the next position in the answer array.
                int value = (answer.charAt(currentPos + 1) - '0') + multiplication / 10;
                answer.setCharAt(currentPos + 1, (char) (value + '0'));
            }
        }

        // Pop excess 0 from the rear of answer.
        if (answer.charAt(answer.length() - 1) == '0') {
            answer.deleteCharAt(answer.length() - 1);
        }

        answer.reverse();
        return answer.toString();
    }

}
