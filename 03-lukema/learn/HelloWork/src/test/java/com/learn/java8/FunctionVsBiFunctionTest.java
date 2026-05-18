package com.learn.java8;


import java.util.function.BiFunction;
import java.util.function.Function;

import org.junit.jupiter.api.Test;

import lombok.extern.log4j.Log4j2;


@Log4j2
public class FunctionVsBiFunctionTest {

    /**
     * Function vs BiFunction:
     *
     * The Function interface is a pre-defined functional interface that can be used as an assignment target for a lambda expression or method reference.
     * It takes a single parameter and returns result by calling the apply() method. While the BiFunction interface is also a pre-defined functional interface
     * that takes two parameters and returns a result. It is similar to the Function interface except it takes two parameters.
     *
     * Syntax
     *
     * @FunctionalInterface
     * public interface Function<T, R>
     *
     * @FunctionalInterface
     * public interface BiFunction<T, U, R>
     */
    @Test
    public void testFunc() {
        Function<Integer, Integer> printNumber = a -> a * 10;
        log.debug("The number is: {}", printNumber.apply(10));

        BiFunction<Integer, Integer, Integer> add = (a, b) -> a + b;
        log.debug("The addition of two numbers are: {}", add.apply(3, 2));
    }
}
