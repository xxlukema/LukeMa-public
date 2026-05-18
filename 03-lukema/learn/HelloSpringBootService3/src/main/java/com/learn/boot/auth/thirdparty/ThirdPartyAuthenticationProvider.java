package com.learn.boot.auth.thirdparty;


import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j2;


/**
 * Useful to authenticate against a third party.
 * 
 * More custom scenarios will still need to access the full Authentication request to be able to perform the
 * authentication process. For example, when authenticating against some external, third party service (such as Crowd) 
 * – both the username and the password from the authentication request will be necessary.
 * 
 */
@Log4j2
@Component
public class ThirdPartyAuthenticationProvider
    implements AuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication)
        throws AuthenticationException {

        if (shouldAuthenticateAgainstThirdPartySystem()) {
            return performThirdPartyAuth(authentication);
        } else {
            return null;
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }

    private boolean shouldAuthenticateAgainstThirdPartySystem() {
        return true;
    }

    /**
     * Call REST/LDAP third party service.
     */
    private Authentication performThirdPartyAuth(Authentication authentication) {

        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        log.info("Doing thrid party authentication using: {}/{} isAuthenticated {}", () -> username, () -> password, () -> authentication.isAuthenticated());

        return ThirdPartyAuthenticationUtils.authorizeUser(username, password);
    }
}
