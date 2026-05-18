package com.learn.test.bit;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import lombok.extern.log4j.Log4j2;


/**
 * Operator     Meaning
 *    &         Bitwise AND operator
 *    |         Bitwise OR operator
 *    ^         Bitwise exclusive OR operator
 *    ~         Binary One's Complement Operator is a unary operator
 *    <<        Left shift operator
 *    >>        Right shift operator
 *
 *
 *    x     y    x & y    x | y    x ^ y
 *    0     0      0        0        0
 *    0     1      0        1        1
 *    1     0      0        1        1
 *    1     1      1        1        0
 *
 */
@Log4j2
@TestMethodOrder(OrderAnnotation.class)
public class BitTest {

    @Order(4)
    @Test
    void testShift() {

        int mask = 1;

        mask <<= 1;
        mask <<= 1;

        log.debug("shift: {}", mask);
    }

    @Order(4)
    @Test
    void testSeenOddEven() {

        int a = 21;

        int odd = 0 ^ a;
        int even = 0 ^ a ^ a;

        log.debug(" odd a: {}, odd: {}", a, odd);
        Assertions.assertEquals(a, odd, "Odd.");

        log.debug("Odd is OK");

        log.debug("even a: {}, even: {}", a, even);
        Assertions.assertEquals(0, even, "Even.");

        log.debug("Even is OK");

    }

    @Order(3)
    @Test
    void testSeenOnceSeenTwice() {

        int a = 21;

        int seenOnce = 0;
        int seenTwice = 0;

        seenOnce = ~seenTwice & (seenOnce ^ a);
        seenTwice = ~seenOnce & (seenTwice ^ a);
        log.debug("1st a: {}, seenOnce: {}, seenTwice: {}", a, seenOnce, seenTwice);
        Assertions.assertEquals(a, seenOnce, "1st time.");
        Assertions.assertEquals(0, seenTwice, "1st time.");

        seenOnce = ~seenTwice & (seenOnce ^ a);
        seenTwice = ~seenOnce & (seenTwice ^ a);
        log.debug("2nd a: {}, seenOnce: {}, seenTwice: {}", a, seenOnce, seenTwice);
        Assertions.assertEquals(0, seenOnce, "2nd time.");
        Assertions.assertEquals(a, seenTwice, "2nd time.");

        seenOnce = ~seenTwice & (seenOnce ^ a);
        seenTwice = ~seenOnce & (seenTwice ^ a);
        log.debug("3rd a: {}, seenOnce: {}, seenTwice: {}", a, seenOnce, seenTwice);
        Assertions.assertEquals(0, seenOnce, "3rd time.");
        Assertions.assertEquals(0, seenTwice, "3rd time.");

        seenOnce = ~seenTwice & (seenOnce ^ a);
        seenTwice = ~seenOnce & (seenTwice ^ a);
        log.debug("4th a: {}, seenOnce: {}, seenTwice: {}", a, seenOnce, seenTwice);
        Assertions.assertEquals(a, seenOnce, "4th time.");
        Assertions.assertEquals(0, seenTwice, "4th time.");

    }

    /**
     * Bitwise NOT (~)
     *
     * The bitwise NOT operator (~) inverts the bits of its operand. Like other bitwise operators, it converts the operand to a 32-bit signed integer
     */
    @Order(1)
    @Test
    void testNot() {

        int a = 21;

        log.debug(" a: {}", () -> ToBitString16(a));
        log.debug("~a: {}", () -> ToBitString16(~a));
        log.debug("~a & a: {}", () -> ToBitString16(~a & a));

        log.debug("~ a: {}, ~~a: {}", ~a, ~~a);

    }

    /**
     * XOR can be used to detect a number "a" appeared "odd" time.
     *
     * 0 ^ a          = a
     * 0 ^ a ^ a      = 0
     *
     * b ^ a          = an intermediate number
     * b ^ a ^ a      = b
     *
     */
    @Order(2)
    @Test
    void testXor() {

        int a = 21;

        log.debug("  a: {}", () -> ToBitString16(a));
        log.debug("  213: {}", () -> ToBitString16(213));
        log.debug(" 213 ^ a: {}", () -> ToBitString16(213 ^ a));
        log.debug(" 213 ^ a ^ a: {}", () -> ToBitString16(213 ^ a ^ a));

        /**
         * Auto converts to 32 bits for negative number:
         */
        log.debug(" ~0: {}", () -> ToBitString16(~0));

        log.debug(" 0 ^ a: {}", () -> ToBitString16(0 ^ a));
        log.debug(" 0 ^ a ^ a: {}", () -> ToBitString16(0 ^ a ^ a));

    }

    public static final String ToBitString32(int n) {
        return ToBitString(n, 32);
    }

    public static final String ToBitString16(int n) {
        return ToBitString(n, 16);
    }

    public static final String ToBitString8(int n) {
        return ToBitString(n, 8);
    }

    public static final String ToBitString(int n) {
        return ToBitString(n, 32);
    }

    public static final String ToBitString(int n, int len) {
        return String.format("%" + len + "s", Integer.toBinaryString(n)).replaceAll(" ", "0");
    }
}
