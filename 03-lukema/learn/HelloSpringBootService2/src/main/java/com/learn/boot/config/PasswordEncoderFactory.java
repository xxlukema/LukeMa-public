package com.learn.boot.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.learn.boot.auth.encode.MyPasswordEnconder;


@Configuration
public class PasswordEncoderFactory {

    @Bean
    public PasswordEncoder passwordEncoder() {

        boolean useMyPasswordEnconder = true;

        if (useMyPasswordEnconder) {
            return new MyPasswordEnconder();
        } else {
            return new BCryptPasswordEncoder();
        }
    }

}
