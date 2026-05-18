package com.learn.other;


import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import lombok.extern.log4j.Log4j2;


/**
 * LC-306 Range Sum Query - Mutable
 *
 * Medium
 *
 * Given an integer array nums, handle multiple queries of the following types:
 *
 *     Update the value of an element in nums.
 *     Calculate the sum of the elements of nums between indices left and right inclusive where left <= right.
 *
 * Implement the NumArray class:
 *
 *     (*) NumArray(int[] nums) Initializes the object with the integer array nums.
 *     (*) void update(int index, int val) Updates the value of nums[index] to be val.
 *     (*) int sumRange(int left, int right) Returns the sum of the elements of nums between indices left and right
 *         inclusive (i.e. nums[left] + nums[left + 1] + ... + nums[right]).
 *
 * Example 1:
 * Input
 * ["NumArray", "sumRange", "update", "sumRange"]
 * [[[1, 3, 5]], [0, 2], [1, 2], [0, 2]]
 * Output
 * [null, 9, null, 8]
 *
 * Explanation
 * NumArray numArray = new NumArray([1, 3, 5]);
 * numArray.sumRange(0, 2); // return 1 + 3 + 5 = 9
 * numArray.update(1, 2);   // nums = [1, 2, 5]
 * numArray.sumRange(0, 2); // return 1 + 2 + 5 = 8
 *
 * Constraints:
 *     1 <= nums.length <= 3 * 10 ^ 4
 *     -100 <= nums[i] <= 100
 *     0 <= index < nums.length
 *     -100 <= val <= 100
 *     0 <= left <= right < nums.length
 *     At most 3 * 10 ^ 4 calls will be made to update and sumRange.
 */
@Log4j2
public class RangeSumQueryMutable {

    public static void main(String[] args) {

        final int[] nums = { 1, 3, 5 };

        NumArrayLuke numArray = new RangeSumQueryMutable().new NumArrayLuke(nums);

        var sum = numArray.sumRange(0, 2);
        log.debug("sum: {}", sum);

        numArray.update(1, 2);

        sum = numArray.sumRange(0, 2);
        log.debug("sum: {}", sum);

    }

    /**
    * Luke - Time Limit Exceeded
    */
    class NumArrayLuke {

        int[] sum;

        Map<Integer, Integer> updates;

        public NumArrayLuke(int[] nums) {
            sum = new int[nums.length];
            sum[0] = nums[0];
            for (int i = 1; i < nums.length; i++) {
                sum[i] = sum[i - 1] + nums[i];
            }
            updates = new HashMap<>();
        }

        public void update(int index, int val) {
            int cur = sumRange(index, index);
            updates.merge(index, val - cur, (o, _) -> o + val - cur);
        }

        public int sumRange(int left, int right) {
            int delta = 0;
            /**
            for (int i = left; i <= right; i++) {
            delta += updates.getOrDefault(i, 0);
            }
            */
            Set<Integer> keyset = updates.keySet();
            for (Integer key : keyset) {
                if (key >= left && key <= right) {
                    delta += updates.get(key);
                }
            }

            if (left == 0) {
                return sum[right] + delta;
            } else {
                return sum[right] - sum[left - 1] + delta;
            }
        }
    }

    /**
     * Your NumArray object will be instantiated and called as such:
     * NumArray obj = new NumArray(nums);
     * obj.update(index,val);
     * int param_2 = obj.sumRange(left,right);
     */

}
