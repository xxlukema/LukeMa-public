package com.learn.test;


import java.math.BigDecimal;
import java.text.DecimalFormat;

import org.junit.jupiter.api.Test;

import lombok.extern.log4j.Log4j2;


@Log4j2
public class FormatNumberTest {

    @Test
    public void testFormatDecimal() {

        double ret = 2.3456789E10;

        log.debug("Fabonacci sum: {}", () -> String.format("%.0f", ret));
        log.debug("Fabonacci sum: {}", () -> BigDecimal.valueOf(ret).toPlainString());

        // DecimalFormat df = new DecimalFormat("#,###");
        log.debug("Fabonacci sum: {}", () -> new DecimalFormat("#,###").format(ret));

        // String s = String.format("%16d", 3);
        String s = String.format("%9d", Integer.MAX_VALUE);
        log.debug("s:{}, len: {}", s, s.length());

        s = Integer.toHexString(Integer.MAX_VALUE);
        log.debug("hex: {}, len: {}", s, s.length());
        s = Integer.toHexString(0);
        log.debug("hex: {}, len: {}", s, s.length());
    }

}
