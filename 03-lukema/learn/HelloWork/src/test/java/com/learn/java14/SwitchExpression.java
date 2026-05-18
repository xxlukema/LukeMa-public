package com.learn.java14;


import org.junit.jupiter.api.Test;

import lombok.extern.log4j.Log4j2;


@Log4j2
public class SwitchExpression {

    @Test
    public void testSwitch() {

        var day = Day.MONDAY;

        int numLetters = switch (day) {
            case MONDAY, FRIDAY, SUNDAY -> 6;
            case TUESDAY -> 7;
            default -> {
                String s = day.toString();
                int result = s.length();
                yield result;
            }
        };

        log.debug("switch: {}", () -> numLetters);

    }

}


enum Day {
    MONDAY, FRIDAY, SUNDAY, TUESDAY
}
