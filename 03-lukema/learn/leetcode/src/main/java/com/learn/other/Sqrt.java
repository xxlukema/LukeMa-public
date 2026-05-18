package com.learn.other;


import lombok.extern.log4j.Log4j2;


@Log4j2
public class Sqrt {

    public static void main(String[] args) {

        final int n = 2147395599;
        // final int n = 29;

        Sqrt sqrt = new Sqrt();

        int ret = sqrt.mySqrt(n);

        log.debug("sqrt: {}", () -> ret);

    }

    public int mySqrt(int x) {

        if (x < 2) {
            return x;
        }

        final int LEFT = 46_341;

        return mySqrt(x, LEFT, 0, 0);
    }

    /**
     * 2 ^ 31 -1 = 2,147,483,647. sqrt of it is 46,340.9
     * 
     * Runtime: 2 ms, faster than 82.27% of Java online submissions for Sqrt(x).
     * Memory Usage: 42.2 MB, less than 6.53% of Java online submissions for Sqrt(x).
     * 
     * Time: O(log(N))
     * Space: O(log(N))
     */
    private int mySqrt(int x, int left, int right, int ctr) {

        log.debug("ctr: {}, left: {}, right: {}", ++ctr, left, right);

        if (left == right || left == right + 1) {
            return right;
        }

        int mid = (left + right) / 2;
        int prd = mid * mid;

        if (prd == x) {
            return mid;
        } else if (prd < x) {
            right = mid;
        } else {
            left = mid;
        }

        return mySqrt(x, left, right, ctr);
    }
}
