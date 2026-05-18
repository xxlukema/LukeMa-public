package com.freddiemac.jwt.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.freddiemac.csrf.repository.CsrfMyTokenRepository;


// @EnableWebSecurity
@Configuration
/**
 * This MUST be a very large number!!! Otherwise, authentication will not work.
 * @Order(Ordered.LOWEST_PRECEDENCE)
 * @Order(Ordered.HIGHEST_PRECEDENCE + 400)
 * 
 * !Important: This Configure must be before BootSecurityConfig, so that
 *             oncePerRequestJwtFilter7 will be before SecurityContextPersistenceFilter.class
 */
@Order(Ordered.HIGHEST_PRECEDENCE + 400)
public class CsrfTokenRegistrationConfig6
    extends WebSecurityConfigurerAdapter {

    @Autowired
    CsrfMyTokenRepository csrfMyTokenRepository;

    @Override
    protected void configure(HttpSecurity httpSecurity)
        throws Exception {
        // @formatter:off
        
        httpSecurity.csrf()
                    .csrfTokenRepository(csrfMyTokenRepository)
                    .requireCsrfProtectionMatcher(new AntPathRequestMatcher("/csrf/**"))
                    /**
                     * Bypass CSRF token request for "/csrf/bypass":
                     */
                    .ignoringAntMatchers("/csrf/bypass")
                    /**
                     * Why these default methods are overwritten?
                     * Any GET, HEAD, TRACE, OPTIONS (this is the default)
                     */
                    .ignoringRequestMatchers(new AntPathRequestMatcher("/csrf/**", "GET"),
                                             new AntPathRequestMatcher("/csrf/**", "HEAD"),
                                             new AntPathRequestMatcher("/csrf/**", "TRACE"),
                                             new AntPathRequestMatcher("/csrf/**", "OPTIONS"))
                    .and()
                    /**
                     * This is important! It specifies which antMatchers are intended for this configuration.
                     * It makes this configuration different from others: Different URL coverage.
                     *    .requestMatchers().antMatchers("/csrf/**")
                     */
                    .requestMatchers()
                    .antMatchers("/csrf/**")
                    .and()
                    .authorizeRequests()
                    .antMatchers("/csrf/post").hasAnyRole("ADMIN")
                    .antMatchers("/csrf/get").permitAll()
                    /**
                     * Bypass CSRF token request for "/csrf/bypass", by enforce role access.
                     */
                    // .antMatchers("/csrf/bypass").hasAnyRole("ADMIN")
                    // Or
                    .anyRequest().authenticated()
                    .and()
                    /**
                     * Specify this configuration uses basic user authentication.
                     */
                    .httpBasic();
        
        // @formatter:on
    }

}
