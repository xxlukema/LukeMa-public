package com.learn.sort;


import java.util.Arrays;

import lombok.extern.log4j.Log4j2;


@Log4j2
public class SortDriver {

    public static void main(String[] args) {

        // int[] numsOrig = { 2, 1, 3, 9, 0, 4, 6, 8, 7, 5, /* dup */ 4 };
        // int[] numsOrig = { 0, 1 };
        int[] numsOrig = { 0, 1, 2 };

        log.debug("{}", () -> "MergeSort");

        int[] numsCopy0 = Arrays.copyOf(numsOrig, numsOrig.length);
        MergeSort.sort(numsCopy0);
        log.debug(" Sorted array: {}", () -> numsCopy0);

        log.debug("{}", () -> "BobbleSort");
        int[] numsCopy1 = Arrays.copyOf(numsOrig, numsOrig.length);
        BobbleSort.sort(numsCopy1);
        log.debug(" Sorted array: {}", () -> numsCopy1);

        log.debug("{}", () -> "QuickSort");
        int[] numsCopy2 = Arrays.copyOf(numsOrig, numsOrig.length);
        QuickSort.quickSort(numsCopy2);
        log.debug(" Sorted array: {}", () -> numsCopy2);

        log.debug("{}", () -> "HeapSort");
        int[] numsCopy3 = Arrays.copyOf(numsOrig, numsOrig.length);
        HeapSort.sort(numsCopy3);
        log.debug(" Sorted array: {}", () -> numsCopy3);

        log.debug("{}", () -> "HashSort2");
        int[] numsCopy4 = Arrays.copyOf(numsOrig, numsOrig.length);
        HashSort2.sort(numsCopy4);
        log.debug(" Sorted array: {}", () -> numsCopy4);

    }
}
