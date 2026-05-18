package com.learn.bit;

public class Division {
    public int divide(int dividend, int divisor) {
        boolean isNeg = false;
        if ((dividend < 0 && divisor > 0) || (dividend > 0 && divisor < 0)) {
            isNeg = true;
        }

        if (dividend == 0) {
            return 0;
        }

        if (divisor == 1) {
            return dividend;
        }

        if (dividend == divisor) {
            return 1;
        }

        if (divisor == -1) {
            if (dividend == Integer.MIN_VALUE) {
                return Integer.MAX_VALUE;
            } else {
                return -dividend;
            }
        }

        boolean isMin = false;
        if (dividend == Integer.MIN_VALUE) {
            if (dividend == -1) {
                return Integer.MAX_VALUE;
            }
            isMin = true;
        }

        if (divisor == Integer.MIN_VALUE) {
            return 0;
        }

        int quo = 0;
        divisor = Math.abs(divisor);

        if (isMin) {
            dividend = Integer.MAX_VALUE - divisor + 1;
            quo++;
        } else {
            dividend = Math.abs(dividend);
        }

        while (dividend >= divisor) {
            int quo2 = 1;
            int div2 = divisor;
            while (dividend - div2 >= div2) {
                div2 = div2 << 1;
                quo2 = quo2 << 1;
            }
            dividend = dividend - div2;
            quo += quo2;

            if (dividend < divisor) {
                break;
            }
        }

        if (isNeg) {
            return -quo;
        } else {
            return quo;
        }
    }

    /*
    public int divide(int dividend, int divisor) {
        boolean isNeg = false;
        if((dividend < 0 && divisor > 0) || (dividend > 0 && divisor < 0)) {
            isNeg = true;
        }
        
        if(dividend == 0) {
            return 0;
        }
        
        if(divisor == 1) {
            return dividend;
        }
        
        if(dividend == divisor) {
            return 1;
        }
        
        if(divisor == -1) {
            if(dividend == Integer.MIN_VALUE) {
                return Integer.MAX_VALUE;
            } else {
                return - dividend;
            }
        }
        
        boolean isMin = false;
        if(dividend == Integer.MIN_VALUE) {
            if(dividend == -1) {
                return Integer.MAX_VALUE;
            }
            isMin = true;
        }
        
        if(divisor == Integer.MIN_VALUE) {
            return 0;
        }
        
        int quo = 0;
        divisor = Math.abs(divisor);
        
        if(isMin) {
            dividend = Integer.MAX_VALUE - divisor + 1;
            quo++;
        } else {
             dividend = Math.abs(dividend);
        }
        
        while(dividend >= divisor) {
            dividend -= divisor;
            quo++;
            
            if(quo == Integer.MAX_VALUE) {
                break;
            }
        }
        
        if(isNeg) {
            if(quo == Integer.MAX_VALUE && dividend > divisor) {
                return Integer.MIN_VALUE;
            } else {
                return - quo;
            }
        } else {
            return quo;
        }
    }
    */
}
