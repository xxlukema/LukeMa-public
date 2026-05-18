package com.freddiemac.jwt.util.test;


import java.util.ArrayList;
import java.util.Collection;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.freddiemac.jwt.util.JwtUtils;

import lombok.extern.log4j.Log4j2;


@Log4j2
@TestMethodOrder(OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
public class JwtUtilsTest {
    
    private String token = null;

    @Test
    public void testAuthorizeUser() {

        Authentication authentication = JwtUtils.authorizeUser("admin", "admin");

        log.debug("authentication: {}", () -> authentication);

        authentication.getAuthorities().forEach(item -> {
            log.debug("authority: {}", () -> item.getAuthority());
        });

    }
    
    @Test
    @Order(1)
    public void testGenerateToken() {
        Collection<GrantedAuthority> roles = new ArrayList<>();
        roles.add(() -> "ROLE_ADMIN");
        roles.add(() -> "ROLE_USER");

        User user = new User("admin", "admin", roles);
        token = JwtUtils.generateToken(user);
        
        log.debug("token: {}", () -> token);
    }
    
    @Test
    @Order(2)
    public void testDecodeTokenWithoutSignatureKey() {
        String decodedToken = JwtUtils.decodeTokenWithoutSignatureKey(token);
        
        log.debug("decodedToken: {}", () -> decodedToken);
        
    }
    
    @Test
    @Order(3)
    public void testParseToken() throws Exception {
        
        User user = JwtUtils.parseToken(token);
        
        log.debug("user: {}", () -> user);

        log.debug("user: {}", () -> user.getUsername());
        
        user.getAuthorities().forEach(item -> {
            log.debug("authority: {}", () -> item.getAuthority());
        });
    }

}
