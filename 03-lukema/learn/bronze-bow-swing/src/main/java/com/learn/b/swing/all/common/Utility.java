package com.learn.b.swing.all.common;


public class Utility {
    public static String int2str(int i) {
        int len = 6;

        String str = Integer.toString(i);
        if (str.length() > 3 && str.charAt(str.length() - 4) != '-') {
            str = str.substring(0, str.length() - 3) + "," + str.substring(str.length() - 3, str.length());
        }

        return padSpace(len - str.length()) + str;
    }

    public static String commaDelim(int i) {
        boolean isNegative = false;
        if (i < 0) {
            isNegative = true;
            i = -i;
        }

        String str = "";

        while (true) {
            int a = i / 1000;
            int z = i % 1000;

            String s = Integer.toString(z);

            if (a == 0) {
                if (str.length() == 0) {
                    str = s;
                } else {
                    str = s + "," + str;
                }

                break;
            } else {
                if (s.length() < 3) {
                    s = padZero(3 - s.length()) + s;
                }

                if (str.length() == 0) {
                    str = s;
                } else {
                    str = s + "," + str;
                }

                i = a;
            }
        }

        if (isNegative) {
            str = "-" + str;
        }

        return str;
    }

    public static String rightAlign(String str, int len) {
        if (str.length() < len) {
            str = Utility.padSpace(len - str.length()) + str;
        }

        return str;
    }

    public static String padSpace(int len) {
        String ret = "";
        for (int i = 0; i < len; i++) {
            ret += " ";
        }

        return ret;
    }

    public static String float2str(double f, int precision) {
        /*
        if(precision > 0)
        {
         double div = Math.pow(10, precision);
         long tmpF = (long) (f * div);
         f = (double) (tmpF / div);
        }
        */

        if (precision > 0) {
            double div = Math.pow(10, precision + 1);
            long tmpF = (long) (f * div);
            long rem = tmpF % 10;
            while (rem > 10) {
                rem %= 10;
            }

            if (rem >= 5) {
                tmpF += 5;
            }

            f = (double) (tmpF / div);
        }

        boolean isNegative = false;

        if (f < 0) {
            isNegative = true;
            f = -f;
        }

        int prec = (int) Math.pow(10, precision);
        f = (double) (int) ((f) * prec) / prec;

        String str = null;
        try {
            str = Double.toString(f);

            int pos = str.indexOf(".");
            if (pos == -1) {
                str += "." + padZero(precision);
            } else {
                int len = str.length() - pos - 1;
                if (len < precision) {
                    str += padZero(precision - len);
                } else if (len > precision) {
                    if (precision == 0) {
                        str = str.substring(0, pos);
                    } else {
                        str = str.substring(0, pos + 1 + precision);
                    }
                }
            }
        } catch (Throwable t) {
        }

        if (str == null) {
            return null;
        }

        int dotPos = str.indexOf(".");
        int endPos = 0;
        if (dotPos > -1) {
            endPos = dotPos;
        } else {
            endPos = str.length();
        }

        if (endPos > 3) {
            str = str.substring(0, endPos - 3) + "," + str.substring(endPos - 3);
        }

        if (endPos > 6) {
            str = str.substring(0, endPos - 6) + "," + str.substring(endPos - 6);
        }

        if (endPos > 9) {
            str = str.substring(0, endPos - 9) + "," + str.substring(endPos - 9);
        }

        if (endPos > 12) {
            str = str.substring(0, endPos - 12) + "," + str.substring(endPos - 9);
        }

        if (isNegative) {
            str = "-" + str;
        }

        return str;
    }

    public static String padZero(int len) {
        String ret = "";
        for (int i = 0; i < len; i++) {
            ret += "0";
        }

        return ret;
    }

    public static String removeComma(String str) {
        return str.replaceAll(",", "");
    }

    public static float parseFloat(String strFloat) {
        float f = 0;
        try {
            f = Float.parseFloat(removeComma(strFloat));
        } catch (Throwable t) {
        }

        return f;
    }

    public static int parseInt(String strInt) {
        int i = 0;
        try {
            i = Integer.parseInt(removeComma(strInt));
        } catch (Throwable t) {
        }

        return i;
    }

    public static String lpadSpace(String str, int len) {
        if (str.length() >= len) {
            return str;
        }

        String newStr = "";

        for (int i = 0; i < len - str.length(); i++) {
            newStr += " ";
        }

        return newStr + str;
    }

    public static String rpadSpace(String str, int len) {
        if (str.length() >= len) {
            return str;
        }

        String newStr = "";

        for (int i = 0; i < len - str.length(); i++) {
            newStr += " ";
        }

        return str + newStr;
    }

    public static float limitValueToMinMax(float value, float min, float max) {
        if (value < min) {
            return min;
        } else if (value > max) {
            return max;
        } else {
            return value;
        }
    }
}
