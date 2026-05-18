package com.learn.util;


import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class ArrayUtils {

    public static String toString(int[] nums) {
        if (nums == null) {
            return "null";
        }

        return IntStream.of(nums).mapToObj(String::valueOf).collect(Collectors.joining(","));
    }
}
