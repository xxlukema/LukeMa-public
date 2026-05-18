package com.learn.repository.test;


import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import com.learn.boot.config.BootJpaConfig;
import com.learn.boot.config.BootSecurityConfig;
import com.learn.entity.SpringSecurityUserEntity;
import com.learn.repository.SpringSecurityUserRepository;

import lombok.extern.log4j.Log4j2;


@Log4j2
@ContextConfiguration(classes = { BootJpaConfig.class, BootSecurityConfig.class })
@SpringBootTest
/**
 * @DataJpaTest - Use an embedded in-memory database
 */
// @DataJpaTest
class SpringSecurityUserRepositoryHeavyTest {

    @Autowired
    private SpringSecurityUserRepository springSecurityUserRepository;

    @Test
    void testFindByUsername() {
        log.debug(() -> "Begin Test.");

        SpringSecurityUserEntity springSecurityUserEntity = springSecurityUserRepository.findByUsername("admin");

        log.info("springSecurityUserEntity: {}", () -> springSecurityUserEntity.getUsername());

        assertEquals("admin", springSecurityUserEntity.getUsername());

        log.debug(() -> "End Test.");
    }

}
