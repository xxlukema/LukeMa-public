package com.learn.other;


import lombok.extern.log4j.Log4j2;


/**
 * LC - 168 - Excel Sheet Column Title
 *
 * Easy
 *
 * Given an integer columnNumber, return its corresponding column title as it appears in an Excel sheet.
 *
 * For example:
 * A -> 1
 * B -> 2
 * C -> 3
 * ...
 * Z -> 26
 * AA -> 27
 * AB -> 28
 * ...
 *
 * Example 1:
 * Input: columnNumber = 1
 * Output: "A"
 *
 * Example 2:
 * Input: columnNumber = 28
 * Output: "AB"
 *
 * Example 3:
 * Input: columnNumber = 701
 * Output: "ZY"
 *
 * Constraints:
 *     1 <= columnNumber <= 2 ^ 31 - 1
 */
@Log4j2
public class ExcelSheetColumnTitile {

    public static void main(String[] args) {

        /**
         * Output: "ZY"
         */
        final int columnNumber = 701;
        // final int columnNumber = 676; // ZZ
        // final int columnNumber = 28;

        ExcelSheetColumnTitile excelSheetColumnTitile = new ExcelSheetColumnTitile();

        var ret = excelSheetColumnTitile.convertToTitle(columnNumber);
        log.debug("Excel sheet column title: {}", () -> ret);
        log.debug("Excel sheet column title {} OK", () -> "ret");

    }

    /**
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Excel Sheet Column Title.
     * Memory Usage: 41.3 MB, less than 52.39% of Java online submissions for Excel Sheet Column Title.
     *
     * Time: O(N)
     * Space: O(1)
     */
    public String convertToTitle(int columnNumber) {
        StringBuilder sb = new StringBuilder();

        while (true) {
            columnNumber -= 1;

            char ch = toChar(columnNumber % 26);

            // log.debug("ch: {}", ch);

            sb.insert(0, ch);

            if (columnNumber < 26) {
                break;
            } else {
                columnNumber /= 26;
            }
        }

        return sb.toString();
    }

    private char toChar(int val) {
        return (char) ('A' + val);
    }
}
