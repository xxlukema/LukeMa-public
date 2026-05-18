package com.learn.jjwt.test;


import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.context.ContextConfiguration;

import com.learn.boot.auth.jwt.JwtUtils;
import com.learn.boot.config.BootJpaConfig;
import com.learn.boot.config.BootSecurityConfig;
import com.learn.entity.SpringSecurityUserEntity;
import com.learn.exception.AppException;
import com.learn.shein.psql.service.SpringSecurityUserService;

import lombok.extern.log4j.Log4j2;


@Log4j2
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
@ContextConfiguration(classes = { BootJpaConfig.class, BootSecurityConfig.class })
@SpringBootTest
public class JwtUtilsTest {

  private String token = null;

  @Autowired
  private SpringSecurityUserService springSecurityUserService;

  @Disabled
  @Test
  @Order(1)
  public void testGenerateToken()
    throws AppException {
    log.debug(() -> "Start test");

    /*
    Collection<GrantedAuthority> roles = new ArrayList<>();
    roles.add(() -> "ROLE_ADMIN");
    roles.add(() -> "ROLE_USER");
    
    User user = new User("admin", "admin", roles);
    */

    SpringSecurityUserEntity entity = this.springSecurityUserService.findByUsername("admin");

    token = JwtUtils.generateToken(entity);

    log.debug("token: {}", () -> token);

    log.debug(() -> "End test");
  }

  @Disabled
  @Test
  @Order(2)
  public void testGenerateToken2() {
    log.debug(() -> "Start test");

    log.debug("username: {}", () -> JwtUtils.extractUsername(token));
    log.debug("expiration: {}", () -> JwtUtils.extractExpiration(token));
    log.debug("value for 'key 1': {}", () -> JwtUtils.extractValueForKey(token, "key 1", String.class));
    log.debug("is token expired: {}", () -> JwtUtils.isTokenExpired(token));

    log.debug(() -> "End test");

  }

  @Test
  @Order(3)
  public void testGenerateToken3()
    throws AppException {
    log.debug(() -> "Start test");

    /*
    UserDetails userDetails = springSecurityUserDetailsService.loadUserByUsername("admin");
    
    log.info("userDetails: {}", () -> userDetails.getUsername());
    
    userDetails.getAuthorities().forEach(item -> {
      log.debug("Authority: {}", () -> item.getAuthority());
    });
    
    Collection<GrantedAuthority> authorities = new ArrayList<>();
    userDetails.getAuthorities().forEach(e -> {
      log.debug("Role: {}", () -> e);
      authorities.add(() -> e.getAuthority());
    });
    User user = new User(userDetails.getUsername(), userDetails.getPassword(), authorities);
    */

    SpringSecurityUserEntity entity = this.springSecurityUserService.findByUsername("admin");

    String token2 = JwtUtils.generateToken(entity);

    log.debug("token: {}", () -> token2);

    try {
      User user = JwtUtils.parseToken(token2);

      log.debug("user: {}", user);

    } catch (Exception e) {
      log.error("Exception: {}", () -> e.getMessage(), () -> e);
    }

    log.debug(() -> "End test");
  }
}
