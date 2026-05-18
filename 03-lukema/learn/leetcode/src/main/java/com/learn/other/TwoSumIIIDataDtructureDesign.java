package com.learn.other;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.extern.log4j.Log4j2;


/**
 * LC - 170 - Two Sum III - Data Structure Design
 *
 * Easy
 *
 * Design a data structure that accepts a stream of integers and checks if it has a pair of integers that sum up to a particular value.
 *
 * Implement the TwoSum class:
 *     TwoSum() Initializes the TwoSum object, with an empty array initially.
 *     void add(int number) Adds number to the data structure.
 *     boolean find(int value) Returns true if there exists any pair of numbers whose sum is equal to value, otherwise, it returns false.
 *
 * Example 1:
 * Input
 * ["TwoSum", "add", "add", "add", "find", "find"]
 * [[], [1], [3], [5], [4], [7]]
 * Output
 * [null, null, null, null, true, false]
 *
 * Explanation
 * TwoSum twoSum = new TwoSum();
 * twoSum.add(1);   // [] --> [1]
 * twoSum.add(3);   // [1] --> [1,3]
 * twoSum.add(5);   // [1,3] --> [1,3,5]
 * twoSum.find(4);  // 1 + 3 = 4, return true
 * twoSum.find(7);  // No two integers sum up to 7, return false
 *
 * Constraints:
 *     -105 <= number <= 105
 *     -2 ^ 31 <= value <= 2 ^ 31 - 1
 *     At most 104 calls will be made to add and find.
 */
@Log4j2
public class TwoSumIIIDataDtructureDesign {

    public static void main(String[] args) {

        TwoSumIIILc twoSumIII = new TwoSumIIILc();
        twoSumIII.add(1);
        twoSumIII.add(1);
        twoSumIII.add(3);
        twoSumIII.add(5);

        log.debug("map: {}", twoSumIII.map);

        log.debug("Two Sum III {} OK", () -> "Test");
    }

}


/**
 * LC - HashMap with Counter
 *
 * Runtime: 99 ms, faster than 81.74% of Java online submissions for Two Sum III - Data structure design.
 * Memory Usage: 49.3 MB, less than 93.91% of Java online submissions for Two Sum III - Data structure design.
 *
 * Time: add O(1), find O(N)
 * Space: O(1)
 */
class TwoSumIIILc {

    final Map<Integer, Integer> map;

    public TwoSumIIILc() {
        map = new HashMap<>();
    }

    /**
     * Time: O(1);
     */
    public void add(int number) {
        map.computeIfPresent(number, (_, val) -> val + 1);
        map.computeIfAbsent(number, _ -> 1);
    }

    /**
     * Time: O(N)
     * Space: O(1)
     */
    public boolean find(int value) {
        for (int key : map.keySet()) {
            int val = value - key;

            if (val == key) {
                if (map.containsKey(val)) {
                    return map.get(val) != 1;
                }
            } else {
                if (map.containsKey(val)) {
                    return true;
                }
            }
        }

        return false;
    }

}


/**
 * Luke - ArrayList - Sort - Two Pointer Search
 *
 * Runtime: 844 ms, faster than 11.65% of Java online submissions for Two Sum III - Data structure design.
 * Memory Usage: 120.3 MB, less than 9.30% of Java online submissions for Two Sum III - Data structure design.
 *
 * Time: add O(1), find O(N * (log(N) base 2))
 */
class TwoSumIIILuke {

    final List<Integer> list;

    public TwoSumIIILuke() {
        list = new ArrayList<>();
    }

    /**
     * Time: O(1);
     */
    public void add(int number) {
        list.add(number);
    }

    /**
     * Space: O(N)
     */
    public boolean find(int value) {

        /**
         * Time: O(N * (log(N) base 2))
         */
        Collections.sort(list);

        /**
         * Two pointer search
         *
         * Time: O(N)
         * Space: O(N) (need to convert list to array for fast access)
         */
        Integer[] nums = list.toArray(Integer[]::new);

        int left = 0;
        int right = nums.length - 1;

        while (left < right) {
            int sum = nums[left] + nums[right];
            if (sum == value) {
                return true;
            } else {
                if (sum < value) {
                    left++;
                } else {
                    right--;
                }
            }
        }

        return false;
    }

}
