package com.learn.aaa;


import org.junit.jupiter.api.Test;

import lombok.extern.log4j.Log4j2;


@Log4j2
public class MyTest {

    int[] a = { 1, 2, 3, 6, 8, 10 };
    int[] b = { 4, 5, 6, 11, 15, 20 };

    @Test
    public void testIntersect() {

        log.debug(() -> "test");
        
        intersects();

    }

    /**
     * O (m * n)
     */
    private void intersects() {

        int counter = 0;
        
        
        for (int i = 0; i < a.length; i++) {

            int value = a[i];

            for (int k = 0; k < b.length; k++) {
                if (value == b[k]) {
                    counter++;
                    log.debug("value: {}", () -> value);
                }
            }

        }
        
        log.debug("counter: {}", counter);

    }

}
