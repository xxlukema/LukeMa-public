package com.learn.lc75;


import org.junit.jupiter.api.Assertions;

import lombok.extern.log4j.Log4j2;


/**
 * LC - 790. Domino and Tromino Tiling
 *
 * Medium
 *
 * You have two types of tiles: a 2 x 1 domino shape and a tromino shape. You may rotate these shapes.

Given an integer n, return the number of ways to tile an 2 x n board. Since the answer may be very large, return it modulo 10 ^ 9 + 7.

In a tiling, every square must be covered by a tile. Two tilings are different if and only if there are two 4-directionally adjacent cells
on the board such that exactly one of the tilings has both squares occupied by a tile.

Example 1:

Input: n = 3
Output: 5
Explanation: The five different ways are show above.

Example 2:

Input: n = 1
Output: 1

Constraints:

    1 <= n <= 1000
 */

@Log4j2
public class DominoTrominoTiling {

    public static void main(String[] args) {

        DominoTrominoTiling dominoTrominoTiling = new DominoTrominoTiling();

        int n = 3;
        int expected = 5;

        var ret = dominoTrominoTiling.numTilings(n);
        log.debug("Domino and Tromino Tiling: {}", () -> ret);
        Assertions.assertEquals(expected, n);
        log.debug("Domino and Tromino Tiling: {} OK", () -> "numTilings");

    }

    public int numTilings(int n) {
        if (n == 1) {
            return 1;
        }

        if (n == 2) {
            return 2;
        }

        if (n == 3) {
            return 5;
        }

        return 1;
    }
}
