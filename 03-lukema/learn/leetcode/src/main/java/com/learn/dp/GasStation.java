package com.learn.dp;


import org.junit.jupiter.api.Assertions;

import lombok.extern.log4j.Log4j2;


/**
 * LC - 134 - Gas Station
 * 
 * Medium
 * 
 * There are n gas stations along a circular route, where the amount of gas at the ith station is "gas[i]".
 * You have a car with an unlimited gas tank and it costs "cost[i]" of gas to travel from the ith station to its next "(i + 1)"th station.
 * You begin the journey with an empty tank at one of the gas stations.
 * 
 * Given two integer arrays "gas" and "cost", return the starting gas station's index if you can travel around the circuit once in the
 * clockwise direction, otherwise return -1. If there exists a solution, it is guaranteed to be unique.
 * 
 * Example 1:
 * Input: gas = [1,2,3,4,5], cost = [3,4,5,1,2]
 * Output: 3
 * Explanation:
 * Start at station 3 (index 3) and fill up with 4 unit of gas. Your tank = 0 + 4 = 4
 * Travel to station 4. Your tank = 4 - 1 + 5 = 8
 * Travel to station 0. Your tank = 8 - 2 + 1 = 7
 * Travel to station 1. Your tank = 7 - 3 + 2 = 6
 * Travel to station 2. Your tank = 6 - 4 + 3 = 5
 * Travel to station 3. The cost is 5. Your gas is just enough to travel back to station 3.
 * Therefore, return 3 as the starting index.
 * 
 * Example 2:
 * Input: gas = [2,3,4], cost = [3,4,3]
 * Output: -1
 * Explanation:
 * You can't start at station 0 or 1, as there is not enough gas to travel to the next station.
 * Let's start at station 2 and fill up with 4 unit of gas. Your tank = 0 + 4 = 4
 * Travel to station 0. Your tank = 4 - 3 + 2 = 3
 * Travel to station 1. Your tank = 3 - 3 + 3 = 3
 * You cannot travel back to station 2, as it requires 4 unit of gas but you only have 3.
 * Therefore, you can't travel around the circuit once no matter where you start.
 * 
 * Constraints:
 *     n == gas.length == cost.length
 *     1 <= n <= 105
 *     0 <= gas[i], cost[i] <= 104
 */
@Log4j2
public class GasStation {

    public static void main(String[] args) {

        final int[] gas = { 1, 2, 3, 4, 5 };
        final int[] cost = { 3, 4, 5, 1, 2 };

        /*
        final int[] gas = { 3, 3, 4 };
        final int[] cost = { 3, 4, 4 };
        */

        /*
        final int[] gas = { 5, 0, 9, 4, 3, 3, 9, 9, 1, 2 };
        final int[] cost = { 6, 7, 5, 9, 5, 8, 7, 1, 10, 5 };
        */

        /*
        final int[] gas = { 4, 5, 3, 1, 4 };
        final int[] cost = { 5, 4, 3, 4, 2 };
        */

        GasStation gasStation = new GasStation();

        int canCompleteCircuitLukeBrute = gasStation.canCompleteCircuitLukeBrute(gas, cost);
        log.debug("Gas station: {}", () -> canCompleteCircuitLukeBrute);
        log.debug("Gas station {} OK", () -> "canCompleteCircuitLukeBrute");

        int canCompleteCircuitLukeDp = gasStation.canCompleteCircuitLukeDp(gas, cost);
        Assertions.assertEquals(canCompleteCircuitLukeBrute, canCompleteCircuitLukeDp);
        log.debug("Gas station {} OK", () -> "canCompleteCircuitLukeDp");

        int canCompleteCircuitLukeNoDp = gasStation.canCompleteCircuitLukeIterativeNoDp(gas, cost);
        Assertions.assertEquals(canCompleteCircuitLukeBrute, canCompleteCircuitLukeNoDp);
        log.debug("Gas station {} OK", () -> "canCompleteCircuitLukeNoDp");
    }

    /**
     * Luke - Iterative - No DP
     * 
     * Runtime: 7 ms, faster than 12.00% of Java online submissions for Gas Station.
     * Memory Usage: 81 MB, less than 32.28% of Java online submissions for Gas Station.
     * 
     * Time: O(N)
     * Space: O(1)
     */
    public int canCompleteCircuitLukeIterativeNoDp(final int[] gas, final int[] cost) {
        final int N = gas.length;

        int sum = 0;
        int start = 0;
        int curr = start;
        int counter = 0;
        int total = 0;

        while (true) {

            int dp = gas[curr] - cost[curr];

            total += dp;
            counter++;
            if (counter == N && total < 0) {
                return -1;
            }

            if (dp >= 0 && sum < 0) {
                start = curr;
                sum = 0;
            }

            sum += dp;

            curr = (curr + 1) % N;

            if (curr == start) {
                if (sum < 0) {
                    return -1;
                } else {
                    return start;
                }
            }
        }
    }

    /**
     * Luke - DP
     * 
     * Runtime: 7 ms, faster than 12.00% of Java online submissions for Gas Station.
     * Memory Usage: 81.1 MB, less than 27.60% of Java online submissions for Gas Station.
     * 
     * Time: O(N)
     * Space: O(N)
     */
    public int canCompleteCircuitLukeDp(final int[] gas, final int[] cost) {
        final int N = gas.length;

        final int[] dp = new int[N];

        int sum = 0;
        for (int i = 0; i < N; i++) {
            dp[i] = gas[i] - cost[i];
            sum += dp[i];
        }

        // log.debug("dp: {}", dp);

        if (sum < 0) {
            return -1;
        }

        sum = 0;
        int start = 0;
        int curr = start;
        while (true) {
            if (dp[curr] >= 0 && sum < 0) {
                start = curr;
                sum = 0;
            }

            sum += dp[curr];

            curr = (curr + 1) % N;

            if (curr == start) {
                if (sum < 0) {
                    return -1;
                } else {
                    return start;
                }
            }
        }
    }

    /**
     * Luke - Brute backtrack
     * 
     * Time Limit Exceeded
     * 
     * Time: O(N ^ 2)
     * Space: O(1)
     */
    public int canCompleteCircuitLukeBrute(final int[] gas, final int[] cost) {
        final int N = gas.length;

        for (int start = 0; start < N; start++) {
            if (backtrackLukeBrute(start, gas, cost) == -1) {
                continue;
            } else {
                return start;
            }
        }

        return -1;
    }

    int backtrackLukeBrute(int start, final int[] gas, final int[] cost) {
        final int N = gas.length;

        int curr = start;
        int currGas = gas[curr] - cost[curr];

        curr = (curr + 1) % N;

        while (currGas >= 0) {
            if (curr == start) {
                return start;
            } else {
                currGas += gas[curr] - cost[curr];
                curr = (curr + 1) % N;
            }
        }

        return -1;
    }

    /**
     * LC - Iterative
     * 
     * Time: O(N)
     * Space: O(1)
     */
    public int canCompleteCircuit(int[] gas, int[] cost) {
        int N = gas.length;

        int total_tank = 0;
        int curr_tank = 0;
        int starting_station = 0;

        for (int i = 0; i < N; ++i) {
            total_tank += gas[i] - cost[i];
            curr_tank += gas[i] - cost[i];
            // If one couldn't get here,
            if (curr_tank < 0) {
                // Pick up the next station as the starting one.
                starting_station = i + 1;
                // Start with an empty tank.
                curr_tank = 0;
            }
        }

        return total_tank >= 0 ? starting_station : -1;
    }
}
