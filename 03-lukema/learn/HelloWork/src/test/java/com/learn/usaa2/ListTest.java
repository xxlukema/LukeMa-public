package com.learn.usaa2;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import lombok.extern.log4j.Log4j2;


@Log4j2
public class ListTest {

    private String str = "aaabbAAcccc";
    // private String str = "a";

    @Test
    public void testHuffman() {
        log.debug("start test");

        log.debug("end test: {}", () -> checkHuffman(str));
    }

    private boolean checkHuffman(String intput) {
        String ret = this.toHuffman(str);
        return ret.length() < str.length();
    }

    private String toHuffman(String input) {

        if (input == null) {
            return null;
        }

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);

            int counter = 1;
            for (int k = i + 1; k < input.length(); k++) {
                char nextCh = input.charAt(k);
                if (ch == nextCh) {
                    counter++;
                    i = k;
                } else {
                    break;
                }
            }

            sb.append(ch).append(counter);
        }

        return sb.toString();
    }

    // Overlaps: 2, 3, 4, 5

    Integer[] ints1 = { 1, 2, 3, 3, 3, 4, 5 };
    Integer[] ints2 = { 2, 3, 3, 3, 4, 5, 6 };

    @Test
    public void testOverlaps() {
        log.debug("start test");

        List<Integer> list1 = Arrays.asList(ints1);
        List<Integer> list2 = Arrays.asList(ints2);

        List<Integer> result = this.findOverlaps(list1, list2);

        log.debug("end test: {}", result);
    }

    private List<Integer> findOverlaps(List<Integer> list1, List<Integer> list2) {
        List<Integer> result = new ArrayList<>();
        for (Integer i : list1) {
            if (list2.contains(i)) {
                result.add(i);
            }
        }

        return result.stream().distinct().sorted((a, b) -> b - a).collect(Collectors.toList());
    }

}
