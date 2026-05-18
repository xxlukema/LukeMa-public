package com.learn.other;


import lombok.extern.log4j.Log4j2;


@Log4j2
public class PowerOfN {

    public static void main(String[] args) {

        double x = 2.00000;
        final int n = 10;

        PowerOfN powerOfN = new PowerOfN();
        double res = powerOfN.myPow(x, n);

        log.info("{} to the power of {} is {}.", () -> x, () -> n, () -> res);
    }

    public double myPow(double x, int n) {

        if (x == 0) {
            return 0;
        }

        if (n == 0 || x == 1) {
            return 1;
        }

        if (n == 1) {
            return x;
        }

        boolean isNNegative = n < 0 ? true : false;

        n = Math.abs(n);

        double res = x;

        int count = 1;

        while (n / 2 >= count) {
            count *= 2;
            res *= res;
        }

        res *= myPow(x, n - count);

        if (isNNegative) {
            res = 1.0 / res;
        }

        return res;
    }
}
