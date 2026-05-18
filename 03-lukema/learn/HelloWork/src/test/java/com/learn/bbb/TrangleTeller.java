package com.learn.bbb;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * TrangleTeller reads input from command line for three line lengths of a triangle. 
 * It then tells what kind of triangle it is: scalene, isosceles, or equilateral.
 * 
 * @author lukema
 *
 */
public class TrangleTeller {

    /**
     * Use a collection to make it more scalable. For example, to scale it to hold a shape of different number of sides.
     * This will also allow sort, to make side comparison more effective.
     */
    private final List<Float> sideList = new ArrayList<Float>(3);

    /**
     * The main method for command line invocation.
     * @param args
     */
    public static void main(String[] args) {
        TrangleTeller trangleTeller = new TrangleTeller();

        try {
            trangleTeller.readTrangleSides(args);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(-1);
        }

        trangleTeller.describeTrangle2();
    }

    /**
     * Parse command line inputs. Throws exceptions for invalid inputs and print out hints on how to use this program.  
     * @param sides
     * @throws Exception
     */
    public void readTrangleSides(String[] sides)
        throws Exception {
        if (sides.length != 3) {
            throw new Exception("Usage: java com.learn.TriangleTeller length1 length2 length3");
        } else {
            for (String side : sides) {
                Float length = null;
                try {
                    length = Float.parseFloat(side);
                    if (length <= 0) {
                        throw new Exception(generateInvalidInputMessage(side));
                    }
                } catch (NumberFormatException e) {
                    throw new Exception(generateInvalidInputMessage(side));
                }

                sideList.add(length);
            }
        }
    }

    /**
     * Shared method to generate error message to give user helpful information on how to use the program.
     * @param side
     * @return
     */
    private String generateInvalidInputMessage(String side) {
        StringBuilder sb = new StringBuilder("Error: ");
        sb.append(side).append(" is not a valid length of triangle side. A valid length of trangle side is a number greater than 0 and less than or equal to ")
                .append(Float.MAX_VALUE);

        return sb.toString();
    }

    /**
     * Print out description of the triangle. 
     */
    public void describeTrangle() {

        // Sort the collection first to save some steps in side length comparison. 
        Collections.sort(sideList);

        // Do side comparison.
        if (sideList.get(0).equals(sideList.get(2))) {
            System.out.println("The triangle is equilateral.");
        } else if (sideList.get(1).equals(sideList.get(0)) || sideList.get(1).equals(sideList.get(2))) {
            System.out.println("The triangle is isosceles.");
        } else {
            System.out.println("The triangle is scalene.");
        }
    }

    /**
     * Print out description of the triangle. 
     */
    public void describeTrangle2() {

        Set<Float> set = new HashSet<Float>();

        set.addAll(sideList);

        if (set.size() == 1) {
            System.out.println("The triangle is equilateral.");
        } else if (set.size() == 2) {
            System.out.println("The triangle is isosceles.");
        } else {
            System.out.println("The triangle is scalene.");
        }
    }
}
