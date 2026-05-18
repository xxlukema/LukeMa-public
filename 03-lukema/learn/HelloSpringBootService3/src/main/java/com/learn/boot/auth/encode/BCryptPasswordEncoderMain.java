package com.learn.boot.auth.encode;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import lombok.extern.log4j.Log4j2;


@Log4j2
public class BCryptPasswordEncoderMain {


    /*
    public static void main(String[] args) {
        encode("admin");
        encode("user");
    }
    */

    public static void encode(String password) {

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(password);

        if (passwordEncoder.matches(password, encodedPassword)) {
            log.info("{} encrypted to: {}", () -> password, () -> encodedPassword);
        } else {
            log.error("{} encrypttion failed.", () -> password);
        }

    }

}
