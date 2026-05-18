package com.learn;


import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@Configuration
public class MySecurityConfig
    extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity httpSecurity)
        throws Exception {

        // @formatter:off
        httpSecurity
           .authorizeRequests()
           .antMatchers("/").permitAll()
           .antMatchers("/ping", "/user").authenticated()
           .and()
           // .httpBasic();
           .oauth2Login();
        // @formatter:on
    }

}
