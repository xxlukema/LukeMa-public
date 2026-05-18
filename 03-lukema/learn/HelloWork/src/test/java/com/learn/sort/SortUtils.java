package com.learn.sort;


import java.util.Arrays;
import java.util.List;


public class SortUtils {

    public static void print(Integer[] array) {
        List<Integer> list = Arrays.asList(array);
        System.out.println(list);
    }

    public static void swap(Integer[] array, int i, int j) {
        if (i >= array.length || j >= array.length) {
            return;
        }

        int tmp = array[i];
        array[i] = array[j];
        array[j] = tmp;
    }

}
