package com.learn.other;


import lombok.extern.log4j.Log4j2;


/**
 * LC - 171 - Excel Sheet Column Number
 * 
 * Easy
 * 
 * Given a string columnTitle that represents the column title as appears in an Excel sheet, return its corresponding column number.
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
 * Input: columnTitle = "A"
 * Output: 1
 * 
 * Example 2:
 * Input: columnTitle = "AB"
 * Output: 28
 * 
 * Example 3:
 * Input: columnTitle = "ZY"
 * Output: 701
 * 
 * Constraints:
 *     1 <= columnTitle.length <= 7
 *     columnTitle consists only of uppercase English letters.
 *     columnTitle is in the range ["A", "FXSHRXW"].
 */
@Log4j2
public class ExcelSheetColumnNumber {

    public static void main(String[] args) {

        /**
         * Output: 28
         */
        // final String columnTitle = "AB";

        /**
         * Output: 701
         */
        final String columnTitle = "ZY";



        ExcelSheetColumnNumber excelSheetColumnNumber = new ExcelSheetColumnNumber();

        var ret = excelSheetColumnNumber.titleToNumber(columnTitle);
        log.debug("Excel sheet column Number: {}", () -> ret);
        log.debug("Excel sheet column Number {} OK", () -> "ret");

    }

    /**
     * Luke
     * 
     * Runtime: 2 ms, faster than 80.41% of Java online submissions for Excel Sheet Column Number.
     * Memory Usage: 43.2 MB, less than 21.14% of Java online submissions for Excel Sheet Column Number.
     * 
     * Time: O(N)
     * Space: O(1)
     */
    public int titleToNumber(String columnTitle) {

        int sum = 0;
        for (int i = 0; i < columnTitle.length(); i++) {

            char ch = columnTitle.charAt(i);
            int num = toNumber(ch);
            sum = 26 * sum + num;

            // log.debug("--- ch: {}, num: {}, sum: {}", ch, num, sum);
        }

        return sum;
    }

    private int toNumber(char ch) {
        return ch - 'A' + 1;
    }
}
