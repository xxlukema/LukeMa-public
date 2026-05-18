package com.learn.test;

import java.util.StringTokenizer;

import org.junit.jupiter.api.Test;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class StringTokenizerTest {

    @Test
    public void testToken() {
        String str = "abcdefghijklmnoppppqrstuvwxyz";
        // String delimit = ".*d.*k.*p*y";
        // String delimit = "dpy";
        String delimit = "d.*m.*p";

        StringTokenizer st = new StringTokenizer(str, delimit);
        while (st.hasMoreElements()) {
            log.debug("element: {}", () -> st.nextElement());
        }

        String pattern = "d.*m.*p*";
        String arr[] = str.split(pattern);
        log.debug("arr: {} {}", () -> arr, ()->arr.length);
    }
}
