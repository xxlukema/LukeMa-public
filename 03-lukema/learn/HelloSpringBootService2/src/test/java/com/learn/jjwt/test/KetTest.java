package com.learn.jjwt.test;


import java.security.Key;

import org.junit.jupiter.api.Test;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.log4j.Log4j2;


@Log4j2
public class KetTest {

    @Test
    public void testGenerateKey() {

        log.info(() -> "Begin test.");

        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

        log.info("key: {}", () -> new String(key.getEncoded()));

        log.info(() -> "End test.");

    }

}
