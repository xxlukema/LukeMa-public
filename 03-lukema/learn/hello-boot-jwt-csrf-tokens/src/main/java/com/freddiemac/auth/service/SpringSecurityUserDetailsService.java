package com.freddiemac.auth.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.freddiemac.auth.entity.SpringSecurityUserEntity;
import com.freddiemac.auth.repository.SpringSecurityUserRepository;

import lombok.extern.log4j.Log4j2;


@Log4j2
@Service
public class SpringSecurityUserDetailsService
    implements UserDetailsService {

    @Autowired
    private SpringSecurityUserRepository springSecurityUserRepository;

    @Autowired
    private UserCache digestUserCache;

    @Override
    public UserDetails loadUserByUsername(String username)
        throws UsernameNotFoundException {

        UserDetails userDetails = digestUserCache.getUserFromCache(username);

        if (userDetails == null) {

            log.debug(() -> "userDetails not found in digestUserCache");

            SpringSecurityUserEntity user = springSecurityUserRepository.findByUsername(username);

            if (user == null) {
                log.debug(() -> "user not found in database");

                throw new UsernameNotFoundException(username);
            }

            userDetails = new SpringSecurityUserDetails(user);

            digestUserCache.putUserInCache(userDetails);
        } else {
            log.debug(() -> "userDetails found in digestUserCache");
        }

        log.info(() -> "userDetailsService authentication success.");

        return userDetails;
    }

}
