package com.learn.jpmc.kunhacker1;


public class MaxDifferenceFinder {

    public int maxDifference(int[] nums) {

        if (nums == null || nums.length < 2) {
            return -1;
        }

        int maxDiff = Integer.MIN_VALUE;

        for (int k = 1; k < nums.length; k++) {
            int curr = nums[k];

            for (int i = 0; i < k; i++) {

                int val = nums[i];
                if (val < curr) {
                    int diff = curr - val;
                    if (diff > maxDiff) {
                        maxDiff = diff;
                    }
                }
            }
        }

        if (maxDiff == Integer.MIN_VALUE) {
            return -1;
        } else {
            return maxDiff;
        }

    }

}
