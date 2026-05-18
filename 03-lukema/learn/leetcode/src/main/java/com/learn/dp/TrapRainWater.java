package com.learn.dp;


import static org.junit.jupiter.api.Assertions.assertEquals;

import lombok.extern.log4j.Log4j2;


@Log4j2
public class TrapRainWater {

    public static void main(String[] args) {
        // int[] height = { 0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1 };
        // int[] height = { 0, 2, 0 };
        // int[] height = { 5, 4, 1, 2 };
        // int[] height = { 4, 3, 3, 9, 3, 0, 9, 2, 8, 3 };
        int[] height = { 9, 6, 8, 8, 5, 6, 3 };

        TrapRainWater trapRainWater = new TrapRainWater();

        int trapLC = trapRainWater.trapLC(height);
        log.info("trapLC: {}", () -> trapLC);

        int trapLuke = trapRainWater.trapLukeTwoPointer(height);
        log.info("trapLuke: {}", () -> trapLuke);

        assertEquals(trapLC, trapLuke);
        int trapLuke2 = trapRainWater.trapLuke2(height);

        log.info("trapLuke2: {}", () -> trapLuke2);

        assertEquals(trapLC, trapLuke2);

    }

    /**
     * Luke - Two Pointers
     *
     * Runtime: 1 ms Beats 99.78%
     * Memory: 48.1 MB Beats 79.98%
     *
     * Time: O(N)
     * Space: O(1)
     */
    public int trapLukeTwoPointer(int[] nums) {

        int sum = 0;
        int left = 0;
        int right = nums.length - 1;

        while (left < right) {
            if (nums[left] <= nums[right]) {
                int height = nums[left];
                while (nums[++left] < height) {
                    sum += height - nums[left];
                }
            } else {
                int height = nums[right];
                while (nums[--right] < height) {
                    sum += height - nums[right];
                }
            }
        }

        return sum;
    }

    public int trapLuke2(int[] nums) {
        if (nums.length < 2) {
            return 0;
        }

        int left = 0;
        int right = nums.length - 1;
        int sum = 0;
        while (left < right) {
            if (nums[left] < nums[right]) {
                for (int pos = left + 1; pos <= right; pos++) {
                    if (nums[pos] < nums[left]) {
                        sum += nums[left] - nums[pos];
                        continue;
                    } else {
                        left = pos;
                        break;
                    }
                }
            } else {
                for (int pos = right - 1; pos >= left; pos--) {
                    if (nums[pos] < nums[right]) {
                        sum += nums[right] - nums[pos];
                        continue;
                    } else {
                        right = pos;
                        break;
                    }
                }
            }
        }

        return sum;
    }

    public int trapLC(int[] height) {
        int result = 0;
        int start = 0;
        int end = height.length - 1;
        while (start < end) {
            if (height[start] <= height[end]) {
                int current = height[start];
                while (height[++start] < current) {
                    result += current - height[start];
                }
            } else {
                int current = height[end];
                while (height[--end] < current) {
                    result += current - height[end];
                }
            }
        }
        return result;
    }

}
