package com.learn.sort;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;


public class BubbleSort {

    private static final Logger LOG = LogManager.getLogger();

    public static final Integer[] ARRAY = { 9, 0, 1, 2, 3, 6, 4, 8, 7, 5 };
    // public static final Integer[] ARRAY = { 8, 2, 9 };

    @Test
    public void testSort() {
        LOG.info("Test begin. " + ARRAY.length);

        SortUtils.print(ARRAY);

        sort(ARRAY);

        SortUtils.print(ARRAY);

        LOG.info("Test complete.");
    }

    public void sort(Integer[] array) {
        sort(array, 0, array.length - 1);
    }

    public void sort(Integer[] array, int lo, int hi) {
        for (int i = 0; i < array.length; i++) {
            for (int k = 0; k < array.length - 1; k++) {
                if (array[k] > array[k + 1]) {
                    SortUtils.swap(array, k, k + 1);
                }
            }
        }
    }

}
