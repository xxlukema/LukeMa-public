package com.learn.java14;


import org.junit.jupiter.api.Test;

import lombok.extern.log4j.Log4j2;


@Log4j2
public class SwitchObject {

    @Test
    public void testSwitchObject() {
        
        var i = Integer.valueOf(1);
        
        // log.debug("Type: {}", () -> checkObjectType(i));
        
        log.debug("str: {}", () -> i);
    }

    /**
     * Not available yet as of 2022-06-25
     */
    /*
    public String checkObjectType(Object obj) {

        return switch (obj) {

            case Integer i -> "An integer";

            case String s -> "A string";

            case Cat c -> "A Cat";

            default -> "I don't know what it is";
        };
    }
    */

}
