package com.learn.lc75;


import org.junit.jupiter.api.Assertions;

import lombok.extern.log4j.Log4j2;


/**
 * LC - 1732. Find the Highest Altitude
 *
 * Easy
 *
 * There is a biker going on a road trip. The road trip consists of n + 1 points at different altitudes. The biker starts his trip on point 0 with altitude equal 0.

You are given an integer array gain of length n where gain[i] is the net gain in altitude between points i​​​​​​ and i + 1 for all (0 <= i < n). Return the highest altitude of a point.


Example 1:

Input: gain = [-5,1,5,0,-7]
Output: 1
Explanation: The altitudes are [0,-5,-4,1,1,-6]. The highest is 1.

Example 2:

Input: gain = [-4,-3,-2,-1,4,3,2]
Output: 0
Explanation: The altitudes are [0,-4,-7,-9,-10,-6,-3,-1]. The highest is 0.

Constraints:

    n == gain.length
    1 <= n <= 100
    -100 <= gain[i] <= 100

 */

@Log4j2
public class FindTheHighestAltitude {

    public static void main(String[] args) {

        FindTheHighestAltitude findTheHighestAltitude = new FindTheHighestAltitude();

        // int[] gain = { -5, 1, 5, 0, -7 };
        // int expected = 1;

        int[] gain = { -4, -3, -2, -1, 4, 3, 2 };
        int expected = 0;

        var ret = findTheHighestAltitude.largestAltitude(gain);
        log.debug("Find the Highest Altitude: {}", () -> ret);
        Assertions.assertEquals(expected, ret);
        log.debug("Find the Highest Altitude {} OK", () -> "largestAltitude");

    }

    /**
     * Time: O(n)
     * Space: O(1)
     *
     * Runtime: 0 ms Beats 100%
     * Memory: 40 MB Beats 93.78%
     */
    public int largestAltitude(int[] gain) {
        int max = 0;
        int altitude = 0;

        for (int i = 0; i < gain.length; i++) {
            altitude += gain[i];
            max = Math.max(max, altitude);
        }

        return max;
    }
}
