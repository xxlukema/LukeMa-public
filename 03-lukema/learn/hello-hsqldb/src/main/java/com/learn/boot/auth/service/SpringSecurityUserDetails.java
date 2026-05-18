package com.learn.boot.auth.service;


import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.learn.entity.SpringSecurityUserEntity;


public class SpringSecurityUserDetails
    implements UserDetails {

    private static final long serialVersionUID = 1L;

    private final SpringSecurityUserEntity user;

    public SpringSecurityUserDetails(SpringSecurityUserEntity user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return user.getAuthorities().stream().map(item -> new SpringSecurityGrantedAuthority(item)).collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return user.getEnabled();
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.getEnabled();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return user.getEnabled();
    }

    @Override
    public boolean isEnabled() {
        return user.getEnabled();
    }

}
