package com.learn.boot.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.learn.filter.OncePerRequestMustHaveLowestPrecedenceFilter8;


@Configuration
public class OncePerRequestMustHaveLowestPrecedenceFilter8Factory {

    @Bean
    OncePerRequestMustHaveLowestPrecedenceFilter8 oncePerRequestFilter() {
        return new OncePerRequestMustHaveLowestPrecedenceFilter8();
    }

}
