package com.learn.test;


import org.junit.jupiter.api.Test;

import lombok.extern.log4j.Log4j2;


@Log4j2
public class ArrayCloneTest {

    /**
     * [].clone is good for 1-d array.
     * [][].clone is not good for 2-d array. change one array will affact another array.
     */
    @Test
    public void testClone() {
        int[] arr = { 1, 2, 3, 4, 5, 6 };

        var ans = arr.clone();

        for (int i = 0, len = ans.length; i < len / 2; i++) {
            int tmp = ans[i];
            ans[i] = ans[len - 1 - i];
            ans[len - 1 - i] = tmp;
        }

        log.debug("arr: {}, ans: {}", arr, ans);

        int[][] nums = { { 1, 2, 3 }, { 2, 2, 3 } };
        var res = nums.clone();
        res[1][1] = 9;

        log.debug("nums: {}, res: {}", nums, res);
    }

}
