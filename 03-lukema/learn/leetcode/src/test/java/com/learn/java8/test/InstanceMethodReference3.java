package com.learn.java8.test;


import java.util.function.BiFunction;

import lombok.extern.log4j.Log4j2;


class Arithmetic {
    public Integer add(Integer a, Integer b) {
        return a + b;
    }
}


@Log4j2
public class InstanceMethodReference3 {
    public static void main(String[] args) {

        Arithmetic arithmetic = new Arithmetic();

        BiFunction<Integer, Integer, Integer> adder = arithmetic::add;

        int result = adder.apply(10, 20);
        log.debug(result);
    }

}
