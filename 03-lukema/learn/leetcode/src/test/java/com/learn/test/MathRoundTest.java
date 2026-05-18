package com.learn.test;


import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;


class MathRoundTest {

    @Test
    void testMathRound() {
        assertEquals(3, Math.round(2.5)); // Expected: 3
        assertEquals(2, Math.round(2.4)); // Expected: 2
        assertEquals(-2, Math.round(-2.5)); // Expected: -2
        assertEquals(-3, Math.round(-2.6)); // Expected: -3
    }

}
