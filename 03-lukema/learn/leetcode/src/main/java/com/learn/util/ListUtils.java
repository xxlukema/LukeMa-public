package com.learn.util;


import java.util.List;
import java.util.stream.Collectors;


public class ListUtils {

    public static String toString(List<Integer> list) {
        if (list == null) {
            return null;
        }

        return list.stream().map(String::valueOf).collect(Collectors.joining());
    }

    /*
    public static String toString(List<String> list) {
        if (list == null) {
            return null;
        }

        return list.stream().collect(Collectors.joining());
    }
    */
}
