package com.learn.boot.jdbc.encrypt.test;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


public class PasswordEncoderGeneratorTest {

    private static final Logger LOG = LogManager.getLogger();

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Test
    public void testEncrypt() {

        String[] passwords = { "admin", "user", "xma" };

        for (String passwd : passwords) {
            String hashedPassword = passwordEncoder.encode(passwd);
            boolean matches = passwordEncoder.matches(passwd, hashedPassword);
            LOG.info(hashedPassword + " " + matches);
        }

    }

}
