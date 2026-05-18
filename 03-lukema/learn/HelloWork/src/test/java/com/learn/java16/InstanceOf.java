package com.learn.java16;


import org.junit.jupiter.api.Test;

import lombok.extern.log4j.Log4j2;


@Log4j2
public class InstanceOf {

    @Test
    public void testInstanceOf() {

        var obj = "Hello World!";

        // String obj = "Hello World!";

        /*
        if (obj instanceof String s) {
        
            // Let pattern matching do the work!
            // ... s.substring(1)
        
            log.debug(() -> s);
        }
        */

        log.debug("Str: {}", () -> obj);

    }

}
