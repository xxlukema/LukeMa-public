package com.learn.boot.auth.thirdparty;


import org.springframework.security.core.GrantedAuthority;


public class ThirdPartySpringSecurityGrantedAuthority
    implements GrantedAuthority {

    private static final long serialVersionUID = 1L;

    /**
     * Role name in String: ROLE_ADMIN, ROLE_USER
     */
    private String authority;

    public ThirdPartySpringSecurityGrantedAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return authority;
    }

}
