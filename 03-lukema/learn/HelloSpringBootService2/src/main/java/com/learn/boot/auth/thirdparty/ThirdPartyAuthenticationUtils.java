package com.learn.boot.auth.thirdparty;


import java.util.ArrayList;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import lombok.extern.log4j.Log4j2;


@Log4j2
public class ThirdPartyAuthenticationUtils {

    public static Authentication authorizeUser(String username, String password) {

        log.info("Third Party Authentication for {}", () -> username);

        if (username == null) {
            return null;
        }

        /**
         * Check username/password here.
         * 
         * This is where the password decryption happens. Encrypt username/password and compare with values saved in database.
         * 
         * admin/admin
         * user/user
         */
        if (!username.equals(password)) {
            return null;
        }

        /**
        * Call REST/LDAP third party service.
        */
        switch (username) {

            /**
             * If user passes authentication, add authorized roles here.
             */
            case "admin": {
                List<ThirdPartySpringSecurityGrantedAuthority> authorities = new ArrayList<>();
                authorities.add(new ThirdPartySpringSecurityGrantedAuthority("ROLE_ADMIN"));
                authorities.add(new ThirdPartySpringSecurityGrantedAuthority("ROLE_USER"));

                Authentication authentication = new UsernamePasswordAuthenticationToken(username, password, authorities);
                /**
                 * This is very important! Without this line, it will think user is anonymous user.
                 * 
                 * -- SecurityContextHolder.getContext().setAuthentication(newAuthentication);
                 */
                SecurityContextHolder.getContext().setAuthentication(authentication);

                return authentication;
            }

            case "user": {
                List<ThirdPartySpringSecurityGrantedAuthority> roles = new ArrayList<>();
                roles.add(new ThirdPartySpringSecurityGrantedAuthority("ROLE_USER"));

                Authentication authentication = new UsernamePasswordAuthenticationToken(username, password, roles);
                /**
                 * This is very important! Without this line, it will think user is anonymous user.
                 * 
                 * -- SecurityContextHolder.getContext().setAuthentication(newAuthentication);
                 */
                SecurityContextHolder.getContext().setAuthentication(authentication);

                return authentication;
            }

            default:
                return null;
        }
    }
}
