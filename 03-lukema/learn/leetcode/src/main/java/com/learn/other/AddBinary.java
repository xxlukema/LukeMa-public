package com.learn.other;


import lombok.extern.log4j.Log4j2;


@Log4j2
public class AddBinary {

    public static void main(String[] args) {

        // String a = "1010", b = "1011"; // sum: 10101
        String a = "1111", b = "1111"; // sum: 11110

        AddBinary addBinary = new AddBinary();
        var ret = addBinary.addBinary(a, b);
        log.debug("AddBinary sum: {}", () -> ret);

    }

    /**
     * Luke
     * 
     * Runtime: 3 ms, faster than 65.80% of Java online submissions for Add Binary.
     * Memory Usage: 43.2 MB, less than 28.93% of Java online submissions for Add Binary.
     * 
     * Time: O(Math.max(m, n))
     * Space: O(Math.max(m, n))
     */
    public String addBinary(String a, String b) {

        StringBuilder sb = new StringBuilder();

        String strLong = null;
        String strShort = null;

        if (a.length() < b.length()) {
            strLong = b;
            strShort = a;
        } else {
            strLong = a;
            strShort = b;
        }

        int lenL = strLong.length();
        int lenS = strShort.length();

        int idx = 0;
        int carr = 0;
        while (idx < lenL) {
            int sum = strLong.charAt(lenL - 1 - idx) - '0';
            if (idx < lenS) {
                sum += strShort.charAt(lenS - 1 - idx) - '0';
            }
            sum += carr;
            if (sum == 3) {
                sb.append(1);
                carr = 1;
            } else if (sum == 2) {
                sb.append(0);
                carr = 1;
            } else if (sum == 1) {
                sb.append(1);
                carr = 0;
            } else {
                sb.append(0);
                carr = 0;
            }

            idx++;
        }

        if (carr == 1) {
            sb.append(carr);
        }

        return sb.reverse().toString();
    }
}
