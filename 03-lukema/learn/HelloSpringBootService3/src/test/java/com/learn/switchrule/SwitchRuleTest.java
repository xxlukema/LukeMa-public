package com.learn.switchrule;


import org.junit.jupiter.api.Test;

import lombok.extern.log4j.Log4j2;


@Log4j2
class SwitchRuleTest {

    @Test
    void testSwitchRule() {
        log.info("testSwitchRule start");
        int i = 1;
        switch (i) {
            case 1, 2 -> log.info("case 1, 2");
            case 4 -> log.info("case 4");
            default -> log.info("default case");
        }
        log.info("testSwitchRule end");
    }

    @Test
    void testSwitchRuleYield() {
        log.info("testSwitchRule2 start");
        int i = 1;
        var res = switch (i) {
            case 1, 2 -> {
                log.info("case 1, 2");
                yield 1;
            }
            case 3 -> {
                log.info("case 3");
                yield 3;
            }
            default -> {
                log.info("default case");
                yield i;
            }
        };

        log.info("testSwitchRule2 end. res: {}", res);
    }

}
