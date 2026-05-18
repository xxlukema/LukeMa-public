package com.learn.list;


import java.util.List;

import org.junit.jupiter.api.Test;

import lombok.extern.log4j.Log4j2;


@Log4j2
class IntegerToRomanTest {

    @Test
    void test() {
        int num = 1994;
        String roman = intToRoman(num);
        log.info("Integer: {}, Roman: {}", num, roman);
    }

    public String intToRoman(int num) {
        record Node(Integer num, String sym) {
        }

        List<Node> list = List.of(
                new Node(1000, "M"),
                new Node(900, "CM"),
                new Node(500, "D"),
                new Node(400, "CD"),
                new Node(100, "C"),
                new Node(90, "XC"),
                new Node(50, "L"),
                new Node(40, "XL"),
                new Node(10, "X"),
                new Node(9, "IX"),
                new Node(5, "V"),
                new Node(4, "IV"),
                new Node(1, "I"));

        StringBuilder sb = new StringBuilder();

        for (Node node : list) {
            while (num >= node.num) {
                num -= node.num;
                sb.append(node.sym);
            }
        }

        return sb.toString();
    }

    public String intToRoman2(int num) {
        StringBuilder sb = new StringBuilder();
        int[] values = { 1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1 };
        String[] symbols = { "M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I" };

        for (int i = 0; i < values.length; i++) {
            while (num >= values[i]) {
                sb.append(symbols[i]);
                num -= values[i];
            }
        }

        return sb.toString();
    }

    public String intToRoman3(int num) {
        String[] ones = { "", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX" };
        String[] tens = { "", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC" };
        String[] hrns = { "", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM" };
        String[] ths = { "", "M", "MM", "MMM" };

        StringBuilder ans = new StringBuilder();

        ans.append(ths[num / 1000]);
        ans.append(hrns[(num % 1000) / 100]);
        ans.append(tens[(num % 100) / 10]);
        ans.append(ones[num % 10]);

        return ans.toString();
    }
}
