package com.learn.test;


import org.junit.jupiter.api.Test;

import lombok.extern.log4j.Log4j2;


/**
 * 1. OR '|'
 *    a = 5 = 0101 (In Binary)
 *    b = 7 = 0111 (In Binary)
 *    Bitwise OR Operation of 5 and 7
 *    0101
 *  | 0111
 * ________
 *    0111  = 7 (In decimal) 
 * 
 * 2. AND '&'
 *    a = 5 = 0101 (In Binary)
 *    b = 7 = 0111 (In Binary)
 *    Bitwise AND Operation of 5 and 7
 *    0101
 *  & 0111
 * ________
 *    0101  = 5 (In decimal) 
 * 
 * 3. XOR '^'
 *    a = 5 = 0101 (In Binary)
 *    b = 7 = 0111 (In Binary)
 *    Bitwise XOR Operation of 5 and 7
 *    0101
 *  ^ 0111
 * ________
 *    0010  = 2 (In decimal) 
 * 
 * 4. Complement '~'
 *    a = 5 = 0101 (In Binary)
 *    Bitwise Complement Operation of 5
 *  ~ 0101
 * ________
 *    1010  = 10 (In decimal) 
 * 
 * 5. Shift left '<<'
 *    1 << n is shifting the bits to left by (n) times.
 *    Every left shit doubles the value.
 *    The result is (2 ^ n)
 */
@Log4j2
public class BitOperatorTest {

    /**
     * a & b = 5 ---- and
     * a | b = 7 ---- or
     * a ^ b = 2 ---- xor
     * ~ a = -6  ---- 2's complement
     * a = 5
     */
    @Test
    public void testOperators() {

        // Initial values
        int a = 5;
        int b = 7;

        // bitwise and
        // 0101 & 0111=0101 = 5
        log.debug("a & b = {}", (a & b));
        log.debug("a: {}, b: {}, (a & b): {}", toBitString(a), toBitString(b), toBitString(a & b));

        // bitwise or
        // 0101 | 0111=0111 = 7
        log.debug("a | b = {}", (a | b));
        log.debug("a: {}, b: {}, (a | b): {}", toBitString(a), toBitString(b), toBitString(a | b));

        // bitwise xor
        // 0101 ^ 0111=0010 = 2
        log.debug("a ^ b = {}", (a ^ b));
        log.debug("a: {}, b: {}, (a ^ b): {}", toBitString(a), toBitString(b), toBitString(a ^ b));

        // bitwise not
        // ~00000000 00000000 00000000 00000101=11111111 11111111 11111111 11111010
        // will give 1's complement (32 bit) of 5 = -6
        log.debug("~ a = {}", ~a);
        log.debug("a: {}, ~a: {}", toBitString(a), toBitString(~a));

        // can also be combined with
        // assignment operator to provide shorthand
        // assignment
        // a=a&b
        a &= b;
        log.debug("a &= {}", a);
        log.debug("a: {}, b: {}, (a & b): {}", toBitString(a), toBitString(b), toBitString(a));

    }

    /**
     * 1 << 3 equals to 1 times 2 three times (2 ^ 3)
     */
    @Test
    public void testShift() {

        int i = 1 << 3;

        log.info("1 << 3: {}", () -> i);

    }

    public static String toBitStringWithoutLeadingZeros(int i) {
        return String.format("%s", Integer.toBinaryString(i));
    }

    public static String toBitString(int i) {
        return String.format("%8s", Integer.toBinaryString(i)).replace(' ', '0');
    }

    @Test
    public void testBitOps() {
        log.debug(() -> "Start Test");

        int i = ~0;

        log.debug("val: {}", () -> toBitString(i >> 2));

        log.debug(() -> "End Test");

    }

}
