package com.learn.other;


import org.junit.jupiter.api.Assertions;

import lombok.extern.log4j.Log4j2;


/**
 * LC - 6 - Zigzag Conversion
 *
 * Medium
 *
 * The string "PAYPALISHIRING" is written in a zigzag pattern on a given number of rows like this: (you may want to display this
 * pattern in a fixed font for better legibility)
 *
 * P   A   H   N
 * A P L S I I G
 * Y   I   R
 *
 * And then read line by line: "PAHNAPLSIIGYIR"
 *
 * Write the code that will take a string and make this conversion given a number of rows:
 *
 * string convert(string s, int numRows);
 *
 * Example 1:
 * Input: s = "PAYPALISHIRING", numRows = 3
 * Output: "PAHNAPLSIIGYIR"
 *
 * Example 2:
 * Input: s = "PAYPALISHIRING", numRows = 4
 * Output: "PINALSIGYAHRPI"
 * Explanation:
 * P     I    N
 * A   L S  I G
 * Y A   H R
 * P     I
 *
 * Example 3:
 * Input: s = "A", numRows = 1
 * Output: "A"
 *
 * Constraints:
 *     1 <= s.length <= 1000
 *     s consists of English letters (lower-case and upper-case), ',' and '.'.
 *     1 <= numRows <= 1000
 */
@Log4j2
public class ZigzagConversion {

    public static void main(String[] args) {

        /**
         * Expected: "PINALSIGYAHRPI"
         */
        final String s = "PAYPALISHIRING";
        final int numRows = 4;
        // final int numRows = 1;

        ZigzagConversion zigzagConversion = new ZigzagConversion();

        var convertLukeShape = zigzagConversion.convertLukeShape(s, numRows);
        log.debug("Zigzag Conversion: {}", () -> convertLukeShape);
        // Assertions.assertEquals("PINALSIGYAHRPI", convertLukeShape);
        log.debug("Zigzag Conversion {} OK", () -> "convertLukeShape");

        var convertLukeNoShape = zigzagConversion.convertLukeNoShape(s, numRows);
        Assertions.assertEquals(convertLukeShape, convertLukeNoShape);
        log.debug("Zigzag Conversion {} OK", () -> "convertLukeNoShape");

    }

    /**
     * Luke - Not keep the shape of Zigzag
     *
     * Runtime: 14 ms Beats 61.60%
     * Memory: 48.2 MB Beats 65.7%
     *
     * Time: O(N)
     * Space: O(N)
     */
    public String convertLukeNoShape(String s, int numRows) {

        if (numRows == 1) {
            return s;
        }

        final StringBuilder[] rows = new StringBuilder[numRows];
        for (int r = 0; r < numRows; r++) {
            rows[r] = new StringBuilder();
        }

        int currCount = -1;
        boolean isGoingDown = true;

        for (int i = 0, n = s.length(); i < n; i++) {
            currCount++;
            char ch = s.charAt(i);

            int row = currCount % numRows;
            if (row == 0 && i != 0) {
                isGoingDown = !isGoingDown;
                currCount++;

                row = currCount % numRows;
            }

            if (isGoingDown) {
                rows[row].append(ch);
            } else {
                int tmpRow = numRows - row - 1;
                rows[tmpRow].append(ch);
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int r = 0; r < numRows; r++) {
            log.debug(rows[r]);

            sb.append(rows[r]);
        }

        return sb.toString();
    }

    /**
     * Luke - Keep the shape of Zigzag
     *
     * Runtime: 99 ms Beats 10.35%
     * Memory: 87.1 MB Beats 5.1%
     *
     * Time: O(N)
     * Space: O(N * numRows)
     */
    public String convertLukeShape(String s, int numRows) {

        if (numRows == 1) {
            return s;
        }

        /**
         * "abc", 2 rows: 3/2 + 1 <-- COLS
         */

        final int N = s.length();

        /**
         * TODO: Fix COLS
         */
        final int COLS = N;

        final Character[][] matrix = new Character[numRows][COLS];

        /**
         * find a cyclic formular to calculate position
         */

        boolean isGoingDown = true;
        int currCount = -1;

        for (int i = 0; i < N; i++) {
            char ch = s.charAt(i);

            currCount++;

            int row = currCount % numRows;

            /**
             * turn
             */
            if (row == 0 && i != 0) {
                isGoingDown = !isGoingDown;

                currCount++;

                if (isGoingDown) {
                    currCount -= numRows;
                    currCount -= numRows;
                }

                row = currCount % numRows;
            }

            if (isGoingDown) {
                int col = currCount / numRows;

                // log.debug("i: {}, ch: {}, col: {}, row: {}, currCount: {}", i, ch, col, row, currCount);

                matrix[row][col] = ch;
            } else {
                int currRow = numRows - row - 1;
                int col = currCount / numRows;

                /**
                 * discount when go down
                 */
                currCount += numRows;

                // log.debug("---- i: {}, ch: {}, col: {}, currRow: {}, currCount: {}", i, ch, col, currRow, currCount);

                matrix[currRow][col] = ch;
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int r = 0; r < numRows; r++) {
            for (int c = 0; c < COLS; c++) {
                if (matrix[r][c] != null) {
                    sb.append(String.valueOf(matrix[r][c]));
                }
            }
        }

        return sb.toString();
    }
}
