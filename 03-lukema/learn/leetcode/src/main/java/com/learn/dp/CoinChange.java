package com.learn.dp;


import java.util.Arrays;

import lombok.extern.log4j.Log4j2;


/**
 * LC-322 Coin Change
 *
 * Medium
 *
 * You are given an integer array coins representing coins of different denominations and an integer amount representing a total amount of money.
 * Return the fewest number of coins that you need to make up that amount. If that amount of money cannot be made up by any combination of the coins, return -1.
 *
 * You may assume that you have an infinite number of each kind of coin.
 *
 * Example 1:
 * Input: coins = [1,2,5], amount = 11
 * Output: 3
 * Explanation: 11 = 5 + 5 + 1
 *
 * Example 2:
 * Input: coins = [2], amount = 3
 * Output: -1
 *
 * Example 3:
 * Input: coins = [1], amount = 0
 * Output: 0
 *
 * Constraints:
 *     1 <= coins.length <= 12
 *     1 <= coins[i] <= 2 ^ 31 - 1
 *     0 <= amount <= 10 ^ 4
 */
@Log4j2
public class CoinChange {

    public static void main(String[] args) {

        /**
         * expected: 3
         */
        // final int[] coins = { 1, 2, 5 };
        // final int amount = 11;

        /**
         * expected: 20
         */
        final int[] coins = { 186, 419, 83, 408 };
        final int amount = 6249;

        /**
         * expected: 2
         * count 0   1   2   3   4   5   6   7   8   9  10  11  12  13  14  15  16
         * 4,   [0, -1, -1, -1,  1, -1, -1, -1,  2, -1, -1, -1,  3, -1, -1, -1,  4]
         * 7,   [0, -1, -1, -1,  1, -1, -1,  1,  2, -1, -1,  2,  3, -1,  2, -1, -1]
         * 9,   [0, -1, -1, -1,  1, -1, -1,  1,  2,  1, -1, -1,  3,  2,  2, -1,  2]
         * 13,  [0, -1, -1, -1,  1, -1, -1,  1,  2,  1, -1, -1,  3,  1,  2, -1,  2]
         */
        // final int[] coins = { 4, 7, 9, 13 };
        // final int amount = 16;

        CoinChange coinChange = new CoinChange();

        var coinChangeGreedy = coinChange.coinChangeGreedy(coins, amount);
        log.debug("Coin Change: {}", () -> coinChangeGreedy);
        log.debug("Coin Change {} WRONG", () -> "coinChangeGreedy");

        var coinChangeLcDp = coinChange.coinChangeLcDp(coins, amount);
        log.debug("Coin Change: {}", () -> coinChangeLcDp);
        log.debug("Coin Change {} OK", () -> "coinChangeLcDp");

        var coinChangeLukeTopDown = coinChange.coinChangeLukeTopDown(coins, amount);
        log.debug("Coin Change: {}", () -> coinChangeLukeTopDown);
        log.debug("Coin Change {} OK", () -> "coinChangeLukeTopDown");

        var coinChangeLcTopDown = coinChange.coinChangeLcTopDown(coins, amount);
        log.debug("Coin Change: {}", () -> coinChangeLcTopDown);
        log.debug("Coin Change {} OK", () -> "coinChangeLcTopDown");

    }

    public int coinChangeLukeTopDown(int[] coins, int amount) {
        final Integer[] memo = new Integer[amount + 1];

        /*
        Arrays.sort(coins);
        
        int left = 0, right = coins.length - 1;
        while (left < right) {
            int tmp = coins[left];
            coins[left] = coins[right];
            coins[right] = tmp;
            left++;
            right--;
        }
        */

        return backtrackLukeTopDown(coins, amount, 0, memo);
    }

    int minCount = Integer.MAX_VALUE;

    int backtrackLukeTopDown(final int[] coins, final int amount, final int count, final Integer[] memo) {

        if (amount < 0 || count > minCount) {
            return -1;
        }

        /**
         * Problem: memo[amount] is not correct. It is intermediate value instead of final optimal value.
         */
        if (memo[amount] != null) {
            return memo[amount];
        }

        if (amount == 0) {
            minCount = Math.min(minCount, count);
        }

        for (int coin : coins) {
            backtrackLukeTopDown(coins, amount - coin, count + 1, memo);
        }

        if (minCount == Integer.MAX_VALUE) {
            return memo[amount] = -1;
        } else {
            return memo[amount] = minCount;
        }
    }

    /**
     * LC - DP - Bottom Up
     *
     * Runtime: 16 ms Beats 75.46%
     * Memory: 41.5 MB Beats 96.73%
     *
     * Time: O(coins.length * amount)
     * Space: O(amount)
     */
    public int coinChangeLcDp(int[] coins, int amount) {
        final int[] count = new int[amount + 1];
        int max = amount + 1;
        Arrays.fill(count, max);
        count[0] = 0;

        for (int i = 0; i < coins.length; i++) {
            for (int col = 1; col <= amount; col++) {
                count[col] = Math.min(count[col], col - coins[i] < 0 ? max : (1 + count[col - coins[i]]));
            }
        }

        return count[amount] >= max ? -1 : count[amount];
    }

    /**
     * Luke - DP - Bottom Up - Wrong
     */
    public int coinChangeDp(int[] coins, int amount) {

        // 83, 186, 408, 419
        Arrays.sort(coins);

        // first row
        int[] row = new int[amount + 1];
        Arrays.fill(row, -1);
        row[0] = 0;
        int count = 1;
        while (count * coins[0] <= amount) {
            row[count * coins[0]] = count;
            count++;
        }

        // log.debug("{}, {}", () -> coins[0], () -> row);

        for (int i = 1; i < coins.length; i++) {
            for (int col = coins[i]; col <= amount; col++) {
                count = col / coins[i];
                int rem = col % coins[i];

                if (rem == 0) {
                    row[col] = count;
                } else {
                    // log.debug("coins[i]: {}, col: {}, offset: {}, offset value: {}", coins[i], col, col - coins[i], row[col - coins[i]]);
                    if (row[col - coins[i] + rem] != -1) {
                        if (row[col] == -1) {
                            row[col] = row[col - coins[i]] == -1 ? -1 : (1 + row[col - coins[i]]);
                        } else {
                            row[col] = Math.min(row[col], row[col - coins[i]] == -1 ? -1 : (1 + row[col - coins[i]]));
                        }
                    }
                }
            }

            // log.debug("{}, {}", coins[i], row);
        }

        return row[amount];
    }

    /**
     * This will not work because there is no unit 1 coin.
     * 
     * Luke - Greedy - Wrong
     */
    public int coinChangeGreedy(int[] coins, int amount) {
        Arrays.sort(coins);

        int idx = coins.length - 1;

        int rem = amount;
        int count = 0;

        while (rem > 0 && idx >= 0) {
            if (coins[idx] > rem) {
                idx--;
                continue;
            }
            count += rem / coins[idx];
            rem = rem % coins[idx];
            idx--;
        }

        return count;
    }

    public int coinChangeLcBrute(int[] coins, int amount) {
        return backtrackLcBrute(0, coins, amount);
    }

    private int backtrackLcBrute(int idxCoin, int[] coins, int amount) {
        if (amount == 0) {
            return 0;
        }

        if (idxCoin < coins.length && amount > 0) {
            int maxVal = amount / coins[idxCoin];
            int minCost = Integer.MAX_VALUE;
            for (int x = 0; x <= maxVal; x++) {
                if (amount >= x * coins[idxCoin]) {
                    int res = backtrackLcBrute(idxCoin + 1, coins, amount - x * coins[idxCoin]);
                    if (res != -1) {
                        minCost = Math.min(minCost, res + x);
                    }
                }
            }
            return (minCost == Integer.MAX_VALUE) ? -1 : minCost;
        }
        return -1;
    }

    public int coinChangeLcTopDown(int[] coins, int amount) {
        if (amount < 1) {
            return 0;
        }
        return backtrackLcTopDown(coins, amount, new int[amount]);
    }

    private int backtrackLcTopDown(int[] coins, int amount, int[] memo) {
        if (amount < 0) {
            return -1;
        }
        if (amount == 0) {
            return 0;
        }
        if (memo[amount - 1] != 0) {
            return memo[amount - 1];
        }

        int min = Integer.MAX_VALUE;
        for (int coin : coins) {
            int ret = backtrackLcTopDown(coins, amount - coin, memo);
            if (ret >= 0 && ret < min) {
                min = 1 + ret;
            }
        }
        memo[amount - 1] = (min == Integer.MAX_VALUE) ? -1 : min;
        return memo[amount - 1];
    }
}
