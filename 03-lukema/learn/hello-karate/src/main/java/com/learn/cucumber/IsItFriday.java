package com.learn.cucumber;


import lombok.extern.log4j.Log4j2;


@Log4j2
public class IsItFriday {
    public static String isItFriday(String today) {
        log.info(() -> "Inside isItFriday().");
        return "Friday".equals(today) ? "TGIF" : "Nope";
    }
}
