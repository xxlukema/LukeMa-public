package com.learn.test;


import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import lombok.extern.log4j.Log4j2;


@Log4j2
public class ArrayListTest {

    @Test
    public void testInit() {
        List<Integer> list = new ArrayList<>(100) {
            {
                add(1);
            }
            {
                add(2);
            }
        };

        log.debug("list: {}, list.size(): {}", () -> list, () -> list.size());
    }

}
