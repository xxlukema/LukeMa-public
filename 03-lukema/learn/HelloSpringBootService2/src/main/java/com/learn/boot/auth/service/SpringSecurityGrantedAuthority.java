package com.learn.boot.auth.service;


import org.springframework.security.core.GrantedAuthority;

import com.learn.entity.SpringSecurityAuthorityEntity;


public class SpringSecurityGrantedAuthority
    implements GrantedAuthority {

    private static final long serialVersionUID = 1L;

    private SpringSecurityAuthorityEntity authority;

    public SpringSecurityGrantedAuthority(SpringSecurityAuthorityEntity authority) {
        this.authority = authority;
    }

    /**
     * Role name in String: ROLE_ADMIN, ROLE_USER
     */
    @Override
    public String getAuthority() {
        return authority.getAuthority();
    }

}
