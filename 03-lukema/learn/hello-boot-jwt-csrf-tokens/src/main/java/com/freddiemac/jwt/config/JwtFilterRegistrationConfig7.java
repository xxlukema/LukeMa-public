package com.freddiemac.jwt.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.freddiemac.filter.JwtRequestFilter7;


@EnableWebSecurity
@Configuration
/**
 * This MUST be a very large number!!! Otherwise, authentication will not work.
 * @Order(Ordered.LOWEST_PRECEDENCE)
 * @Order(Ordered.HIGHEST_PRECEDENCE + 500)
 * 
 * !Important: This Configure must be before BootSecurityConfig, so that
 *             oncePerRequestJwtFilter7 will be before SecurityContextPersistenceFilter.class
 */
@Order(Ordered.HIGHEST_PRECEDENCE + 500)
public class JwtFilterRegistrationConfig7
    extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtRequestFilter7 jwtRequestFilter7;

    @Bean
    public JwtRequestFilter7 jwtRequestFilter7() {
        return new JwtRequestFilter7();
    }

    /**
     * This is important part of code, although it is not clear what is this for.
     * Without this block of code, injection of JwtRequestFilter7 will have circular inclusion issue.
     */
    @Bean
    public FilterRegistrationBean<JwtRequestFilter7> filterRegistrationBean() {
        FilterRegistrationBean<JwtRequestFilter7> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(jwtRequestFilter7);
        registrationBean.addUrlPatterns("/jwt/**", "/rest/**");

        return registrationBean;
    }

    @Override
    protected void configure(HttpSecurity httpSecurity)
        throws Exception {
        // @formatter:off
        
        httpSecurity.csrf().disable();
        
        /**
         * Use UsernamePasswordAuthenticationFilter.class as reference. Not SecurityContextPersistenceFilter.class.
         */
        httpSecurity
                    /**
                     * This is important! It specifies which antMatchers are intended for this configuration.
                     * It makes this configuration different from others: Different URL coverage.
                     *     .requestMatchers().antMatchers(...)
                     */
                    .requestMatchers()
                    .antMatchers("/jwt/**")
                    .and()
                    .addFilterBefore(jwtRequestFilter7, UsernamePasswordAuthenticationFilter.class)
                    .authorizeRequests()
                    .antMatchers("/jwtlogin").permitAll()
                    .antMatchers("/jwt/**").hasAnyRole("ADMIN")
                    // .anyRequest().denyAll()
                    .and()
                    // .sessionManagement().disable()
                    .httpBasic();
        
        // @formatter:on
    }

}
