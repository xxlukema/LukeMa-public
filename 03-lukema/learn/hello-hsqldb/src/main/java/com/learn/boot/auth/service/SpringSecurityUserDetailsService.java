package com.learn.boot.auth.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.learn.entity.SpringSecurityUserEntity;
import com.learn.repository.SpringSecurityUserRepository;

import lombok.extern.log4j.Log4j2;


@Log4j2
@Service("springSecurityUserDetailsService")
public class SpringSecurityUserDetailsService
    implements UserDetailsService {

    @Autowired
    private SpringSecurityUserRepository springSecurityUserRepository;

    @Autowired
    private UserCache userCache;

    @Override
    public UserDetails loadUserByUsername(String username)
        throws UsernameNotFoundException {

        UserDetails userDetails = userCache.getUserFromCache(username);

        if (userDetails == null) {

            log.debug(() -> "userDetails not found in digestUserCache");

            SpringSecurityUserEntity user = springSecurityUserRepository.findByUsername(username);

            if (user == null) {
                log.debug(() -> "user not found in database");

                throw new UsernameNotFoundException(username);
            }

            userDetails = new SpringSecurityUserDetails(user);

            userCache.putUserInCache(userDetails);
        } else {
            log.debug(() -> "userDetails found in digestUserCache");
        }

        return userDetails;
    }

}
