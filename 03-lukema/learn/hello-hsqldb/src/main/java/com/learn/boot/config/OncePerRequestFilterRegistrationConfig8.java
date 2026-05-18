package com.learn.boot.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import com.learn.filter.OncePerRequestMustHaveLowestPrecedenceFilter8;


@Configuration
/**
 * This MUST be a very large number!!! Otherwise, authentication will not work.
 * @Order(Ordered.LOWEST_PRECEDENCE)
 */
@Order(Ordered.LOWEST_PRECEDENCE - 200)
public class OncePerRequestFilterRegistrationConfig8 {

    @Autowired
    private OncePerRequestMustHaveLowestPrecedenceFilter8 oncePerRequestFilter;

    @Bean
    public FilterRegistrationBean<OncePerRequestMustHaveLowestPrecedenceFilter8> configMyOncePerRequestFilter() {
        FilterRegistrationBean<OncePerRequestMustHaveLowestPrecedenceFilter8> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(oncePerRequestFilter);
        registrationBean.addUrlPatterns("/spring/*", "/rest/*");

        return registrationBean;
    }

}
