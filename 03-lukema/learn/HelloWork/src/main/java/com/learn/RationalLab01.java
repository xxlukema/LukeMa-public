package com.learn;

// RationalLab01.java
// The Rational Class Program I
// This is the student, starting version of the RationalLab01 assignment.

import javax.swing.JOptionPane;


public class RationalLab01 {
    public static void main(String args[]) {
        String strNbr1 = JOptionPane.showInputDialog("Enter Numerator ");
        String strNbr2 = JOptionPane.showInputDialog("Enter Denominator ");

        int num = Integer.parseInt(strNbr1);
        int den = Integer.parseInt(strNbr2);

        Rational r = new Rational(num, den);
        JOptionPane.showMessageDialog(null, r.getNum() + "/" + r.getDen() + " equals " + r.getDecimal() + "\n and reduces to " + r.getReduced());

        System.exit(0);
    }
}


class Rational {
    int num, den;
    int rednum, redden;

    //  Rational
    public Rational(int a, int b) {
        num = rednum = a;
        den = redden = b;

    }

    //  getNum
    public int getNum() {
        return num;
    }

    //  getDen
    public int getDen() {
        return den;
    }

    //  getDecimal
    public double getDecimal() {
        return (double) num / den;
    }

    //  getOriginal
    public String getOriginal() {
        String og = num + "/" + den;
        return og;
    }

    //  getReduced
    public String getReduced() {
        int gcf = getGCF(num, den);
        rednum = num / gcf;
        redden = den / gcf;
        String red = rednum + "/" + redden;
        return red;
    }

    private int getGCF(int n1, int n2) {
        int rem = 0;
        int gcf = 0;
        do {
            rem = n1 % n2;
            if (rem == 0)
                gcf = n2;
            else {
                n1 = n2;
                n2 = rem;
            }
        } while (rem != 0);
        return gcf;
    }
}
