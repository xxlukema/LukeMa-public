package com.learn.sort;


import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import lombok.extern.log4j.Log4j2;


@Log4j2
class SortTest {

    @Test
    void testSortInt() {
        int[] arr = { 5, 2, 9, 1, 5, 6 };
        log.info("Before sorting: {}", arr);
        log.info("After sorting: {}", arr);
        Integer[] arr2 = Arrays.stream(arr).boxed().toArray(Integer[]::new);
        log.info("Before sorting in reverse order: {}", List.of(arr2));
        Arrays.sort(arr2, Collections.reverseOrder());
        log.info("After sorting in reverse order: {}", List.of(arr2));
    }

    @Test
    void testSortString() {
        String[] arr = { "banana", "apple", "orange", "kiwi" };
        log.info("Before sorting: {}", Arrays.toString(arr));
        Arrays.sort(arr, Collections.reverseOrder());
        log.info("After sorting: {}", Arrays.toString(arr));
        List<String> list = Arrays.asList(arr);
        log.info("Before sorting in reverse order: {}", list);
        Collections.sort(list, Collections.reverseOrder());
        log.info("After sorting in reverse order: {}", list);
    }

}
