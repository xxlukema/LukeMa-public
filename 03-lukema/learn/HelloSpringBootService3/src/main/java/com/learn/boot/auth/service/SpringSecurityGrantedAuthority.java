package com.learn.boot.auth.service;


import org.springframework.security.core.GrantedAuthority;

import com.learn.entity.SpringSecurityAuthorityEntity;

import lombok.AllArgsConstructor;


@AllArgsConstructor
public class SpringSecurityGrantedAuthority
    implements GrantedAuthority {

    private static final long serialVersionUID = 1L;

    private final SpringSecurityAuthorityEntity authority;

    /**
     * Role name in String: ROLE_ADMIN, ROLE_USER
     */
    @Override
    public String getAuthority() {
        return authority.getAuthority();
    }

}
