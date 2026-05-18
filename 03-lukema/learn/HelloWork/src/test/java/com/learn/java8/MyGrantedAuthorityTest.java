package com.learn.java8;


import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import lombok.extern.log4j.Log4j2;


@Log4j2
public class MyGrantedAuthorityTest {

    @Test
    public void testInterface() {
        log.debug(() -> "Begin Test");

        List<MyGrantedAuthority> list = new ArrayList<>();

        list.add(() -> "Admin");
        list.add(() -> "User");
        list.add(() -> "Guest");

        log.debug("list: {}", () -> list);

        list.stream().forEach(item -> log.debug(item.getGrantedAuthority()));

        log.debug(() -> "End Test");
    }
}


@FunctionalInterface
interface MyGrantedAuthority {
    String getGrantedAuthority();
}
