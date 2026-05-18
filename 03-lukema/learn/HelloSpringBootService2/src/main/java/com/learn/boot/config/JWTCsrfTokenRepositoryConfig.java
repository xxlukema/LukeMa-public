package com.learn.boot.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.csrf.CsrfTokenRepository;

import com.learn.repository.JWTCsrfTokenRepository;


@Configuration
public class JWTCsrfTokenRepositoryConfig {

    @Bean
    // @ConditionalOnMissingBean
    public CsrfTokenRepository jwtCsrfTokenRepository() {
        return new JWTCsrfTokenRepository();
    }
}
