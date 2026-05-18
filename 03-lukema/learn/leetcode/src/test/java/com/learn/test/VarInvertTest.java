package com.learn.test;

import java.util.Arrays;
import java.util.Set;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import lombok.extern.log4j.Log4j2;

@Log4j2
@TestMethodOrder(OrderAnnotation.class)
public class VarInvertTest {

    @Order(1)
    @Test
    public void testVarInvert() {

        var i = -1;

        log.info("var invert: {}", () -> (~i));
    }

    @Order(2)
    @Test
    public void testShift() {

        log.info("  8 >> 1  : {}", () -> (8 >> 1));
        log.info("  8 >>> 1 : {}", () -> (8 >>> 1));
        log.info(" -8 >> 1  : {}", () -> (-8 >> 1));
        log.info(" -8 >>> 1 : {}", () -> (-8 >>> 1));
        log.info("Integer.toBinaryString(-8)       : {}", () -> Integer.toBinaryString(-8));
        log.info("Integer.toBinaryString(-8 >>> 1) : {}", () -> Integer.toBinaryString(-8 >>> 1));

        log.info(" Integer.MAX_VALUE >> 1  : {}", () -> (Integer.MAX_VALUE >> 1));
        log.info(" Integer.MIN_VALUE >> 1  : {}", () -> (Integer.MIN_VALUE >> 1));
    }

    @Order(3)
    @Test
    public void testArray() {

        String[] mine = { "()()()()", "(()())()", "(()(()))", "()()(())", "(((())))", "(())()()",
                "()((()))", "()(())()", "()(()())", "(()()())", "((()()))", "((()))()", "((())())" };

        Arrays.sort(mine);

        String[] test = { "(((())))", "((()()))", "((())())", "((()))()", "(()(()))", "(()()())",
                "(()())()", "(())(())", "(())()()", "()((()))", "()(()())", "()(())()", "()()(())", "()()()()" };

        Arrays.sort(test);

        log.info("mine: {}", () -> mine);
        log.info("test: {}", () -> test);

        Set<String> set = Set.of(mine);

        for (String str : test) {
            if (!set.contains(str)) {
                log.info("str: {}", () -> str);
            }
        }
    }

    @Order(4)
    @Test
    public void testEmptyString() {

        String str = "Str";
        String empty = "";

        log.info("Contains empty String: {}", () -> str.contains(empty));
        log.info("indexOf empty String: {}", () -> str.indexOf(empty));
    }
}
