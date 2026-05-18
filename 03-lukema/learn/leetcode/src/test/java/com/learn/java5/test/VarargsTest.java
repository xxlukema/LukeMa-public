package com.learn.java5.test;


import org.junit.jupiter.api.Test;

import lombok.extern.log4j.Log4j2;


@Log4j2
public class VarargsTest {

    @Test
    public void testVarargs() {
        display();//zero argument
        display("hello");//one argument
        display("my", "name", "is", "varargs");//four arguments
    }

    void display(String... values) {
        log.debug("display method invoked: {}", values.length);
        for (String s : values) {
            log.debug(s);
        }
    }
}
