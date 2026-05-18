package com.learn.java8;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class IntArray2List {

    @Test
    public void testIntArray2List() {

        int[] nums = { 2, 1, 3, 9, 8, 8, 7, 6, 0, 4, 5, 3 };

        List<Integer> list = Arrays.stream(nums).boxed().sorted(Comparator.reverseOrder()).collect(Collectors.toList());

        log.info("list: {}", () -> list);

    }
}
