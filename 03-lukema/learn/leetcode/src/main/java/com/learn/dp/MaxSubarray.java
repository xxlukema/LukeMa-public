package com.learn.dp;


import org.junit.jupiter.api.Assertions;

import lombok.extern.log4j.Log4j2;


@Log4j2
public class MaxSubarray {

    public static void main(String[] args) {

        int[] nums = { -2, 1, -3, 4, -1, 2, 1, -5, 4 };

        MaxSubarray maxSubarray = new MaxSubarray();
        int ret = maxSubarray.maxSubArrayLuke(nums);
        log.debug("maxSubarray Luke: {}", () -> ret);

        int ret2 = maxSubarray.maxSubArrayLuke2(nums);
        log.debug("maxSubarray Luke2: {}", () -> ret2);

        int retLC = maxSubarray.maxSubArrayLC(nums);
        log.debug("maxSubarray LC: {}", () -> retLC);

        Assertions.assertEquals(retLC, ret);
        Assertions.assertEquals(retLC, ret2);

    }

    public int maxSubArrayLuke2(int[] nums) {

        int currSum = nums[0];
        int sumMax = nums[0];

        for (int i = 1; i < nums.length; i++) {
            final int n = nums[i];

            currSum = Math.max(n, currSum + n);
            sumMax = Math.max(sumMax, currSum);
        }

        return sumMax;
    }

    public int maxSubArrayLC(int[] nums) {
        // Initialize our variables using the first element.
        int currentSubarray = nums[0];
        int maxSubarray = nums[0];

        // Start with the 2nd element since we already used the first one.
        for (int i = 1; i < nums.length; i++) {
            int num = nums[i];
            // If current_subarray is negative, throw it away. Otherwise, keep adding to it.
            currentSubarray = Math.max(num, currentSubarray + num);
            maxSubarray = Math.max(maxSubarray, currentSubarray);
        }

        return maxSubarray;
    }

    public int maxSubArrayLuke(int[] nums) {

        int maxSum = Integer.MIN_VALUE;
        int start = 0;

        while (start < nums.length) {
            start = nextStartIdx(nums, start);
            // return largest
            if (start == -1) {
                for (int i = 0; i < nums.length; i++) {
                    maxSum = Math.max(maxSum, nums[i]);
                }
                return maxSum;
            } else {
                Node sumNode = nextMaxSum(nums, start);
                maxSum = Math.max(maxSum, sumNode.maxSum);
                start = sumNode.end;
            }
        }

        return maxSum;
    }

    private Node nextMaxSum(int[] nums, int start) {
        int end = start;
        int sum = 0;
        int maxSum = 0;
        while (end < nums.length) {
            sum += nums[end];
            if (sum > maxSum) {
                maxSum = sum;
            }

            if (sum <= 0) {
                break;
            } else {
                end++;
            }
        }

        return new Node(start, end, maxSum);
    }

    public int nextStartIdx(int[] nums, int start) {
        while (start < nums.length) {
            if (nums[start] > 0) {
                return start;
            } else {
                start++;
            }
        }

        return -1;
    }

    record Node(int start, int end, int maxSum) {
    }
}
