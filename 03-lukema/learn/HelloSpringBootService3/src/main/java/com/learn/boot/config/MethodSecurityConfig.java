package com.learn.boot.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;


/**
 * This is for Method level use of @PreAuthorize("hasRole('ADMIN') OR hasRole('USER')"). Without this, only class level
 * use of @PreAuthorize("hasRole('ADMIN') OR hasRole('USER')") is support.
 *
 */
@Configuration
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class MethodSecurityConfig {
}
