package com.learn.dp;


import java.util.ArrayList;
import java.util.List;

import lombok.extern.log4j.Log4j2;


/**
 * LC - 118 - Pascal's Triangle
 * 
 * Easy
 * 
 * Given an integer numRows, return the first numRows of Pascal's triangle.
 * In Pascal's triangle, each number is the sum of the two numbers directly above it as shown:
 */
@Log4j2
public class PascalsTriangle {

    public static void main(String[] args) {

        int numRows = 5;

        PascalsTriangle pascalsTriangle = new PascalsTriangle();

        log.debug(() -> "Start Test...");

        List<List<Integer>> ret = pascalsTriangle.generate(numRows);
        log.debug("Pascals Triangle: {}", () -> ret);

        log.debug(() -> "Test Complete");
    }

    /**
     * Luke - Iterative
     * 
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Pascal's Triangle.
     * Memory Usage: 39.9 MB, less than 97.40% of Java online submissions for Pascal's Triangle.
     * 
     * Time: O(N ^ 2)
     * Space: O(1)
     */
    public List<List<Integer>> generate(int numRows) {
        List<List<Integer>> result = new ArrayList<>();

        if (numRows == 0) {
            return result;
        }

        result.add(new ArrayList<>());
        result.get(0).add(1);

        if (numRows == 1) {
            return result;
        }

        result.add(new ArrayList<>());
        result.get(1).add(1);
        result.get(1).add(1);

        for (int row = 2; row < numRows; row++) {
            result.add(new ArrayList<>());
            List<Integer> last = result.get(row - 1);
            List<Integer> curr = result.get(row);
            curr.add(1);
            for (int col = 1; col < row; col++) {
                curr.add(last.get(col - 1) + last.get(col));
            }
            curr.add(1);
        }

        return result;
    }
}
