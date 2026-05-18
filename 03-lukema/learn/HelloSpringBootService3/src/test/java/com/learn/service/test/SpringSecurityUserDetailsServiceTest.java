package com.learn.service.test;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ContextConfiguration;

import com.learn.boot.auth.service.SpringSecurityUserDetailsService;
import com.learn.boot.config.BootJpaConfig;
import com.learn.boot.config.BootSecurityConfig;

import lombok.extern.log4j.Log4j2;


@Log4j2
@ContextConfiguration(classes = { BootJpaConfig.class, BootSecurityConfig.class })
// @SpringBootTest(classes = HelloSpringBootMainApplication.class)
@SpringBootTest
class SpringSecurityUserDetailsServiceTest {

    @Autowired
    private SpringSecurityUserDetailsService springSecurityUserDetailsService;

    @Test
    void testFindByUsername() {
        log.debug(() -> "Begin Test.");

        UserDetails userDetails = springSecurityUserDetailsService.loadUserByUsername("admin");

        log.info("userDetails: {}", () -> userDetails.getUsername());

        userDetails.getAuthorities().forEach(item -> {
            log.debug("Authority: {}", () -> item.getAuthority());
        });

        log.debug(() -> "End Test.");
    }

}
