package com.learn.repository.test;


import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.learn.boot.config.BootJpaConfig;
import com.learn.boot.config.BootSecurityConfig;
import com.learn.entity.SpringSecurityUserEntity;
import com.learn.repository.SpringSecurityUserRepository;

import lombok.extern.log4j.Log4j2;


@Log4j2
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { BootJpaConfig.class, BootSecurityConfig.class })
@SpringBootTest
public class SpringSecurityUserRepositoryTest {

    @Autowired
    private SpringSecurityUserRepository springSecurityUserRepository;

    @Test
    public void testFindByUsername() {
        log.debug(() -> "Begin Test.");

        SpringSecurityUserEntity springSecurityUserEntity = springSecurityUserRepository.findByUsername("admin");

        log.info("springSecurityUserEntity: {}", () -> springSecurityUserEntity.getUsername());

        log.debug(() -> "End Test.");
    }

}
