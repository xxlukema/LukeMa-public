package com.freddiemac.jwt.util;


import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.crypto.SecretKey;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.log4j.Log4j2;


@Log4j2
public class JwtUtils {

    // @Value("${jwt.secret}")
    // private String secret;

    // byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
    // String decodedString = new String(decodedBytes);
    // private String secret = Base64.getEncoder().encodeToString("My Secret Key".getBytes());

    private static final SecretKey Secret_Key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public static Authentication authorizeUser(String username, String password) {

        log.info("JWT Authentication for {}", () -> username);

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
                List<GrantedAuthority> authorities = new ArrayList<>();
                authorities.add(() -> "ROLE_ADMIN");
                authorities.add(() -> "ROLE_USER");

                Authentication authentication = new UsernamePasswordAuthenticationToken(username, password, authorities);
                /**
                 * This is very important! Without this line, it will think user is anonymous user.
                 * 
                 * -- SecurityContextHolder.getContext().setAuthentication(newAuthentication);
                 */
                SecurityContextHolder.getContext().setAuthentication(authentication);

                log.info("User authorized with {}", () -> "admin and user roles");

                return authentication;
            }

            case "user": {
                List<GrantedAuthority> roles = new ArrayList<>();
                roles.add(() -> "ROLE_USER");

                Authentication authentication = new UsernamePasswordAuthenticationToken(username, password, roles);
                /**
                 * This is very important! Without this line, it will think user is anonymous user.
                 * 
                 * -- SecurityContextHolder.getContext().setAuthentication(newAuthentication);
                 */
                SecurityContextHolder.getContext().setAuthentication(authentication);

                log.info("User authorized with {}", () -> "user role");

                return authentication;
            }

            default:
                return null;
        }
    }

    /**
     * Tries to parse specified String as a JWT token. If successful, returns User object with username, id and role prefilled (extracted from token).
     * If unsuccessful (token is invalid or not containing all required user properties), simply returns null.
     * 
     * @param token the JWT token to parse
     * @return the User object extracted from specified token or null if a token is invalid.
     * @throws Exception 
     */
    public static User parseToken(String token)
        throws Exception {
        /**
         * parseClaimsJwt does not support signed claims. But parseClaimsJws does.
         */
        Claims body = Jwts.parserBuilder().setSigningKey(Secret_Key).build().parseClaimsJws(token).getBody();

        log.info("expire: {}", () -> body.getExpiration());
        log.info("JWT Token: {}", () -> body.toString());

        if ("admin".equals(body.getSubject())) {

            Collection<GrantedAuthority> roles = new ArrayList<>();
            roles.add(() -> "ROLE_ADMIN");
            roles.add(() -> "ROLE_USER");

            return new User("admin", "admin", roles);
        } else {
            return null;
        }
    }

    /**
     * Generates a JWT token containing username as subject, and userId and role as additional claims. These properties are taken from the specified
     * User object. Tokens validity is infinite.
     * 
     * @param user the user for which the token will be generated
     * @return the JWT token
     * 
     * Seven predefined claims
     * iss     Issuer
     * sub     Subject
     * aud     Audience
     * exp     Expiration
     * nbf     Not Before
     * iat     Issued At
     * jti     JWT ID
     */
    public static String generateToken(User user) {
        Claims claims = Jwts.claims();

        claims.setSubject(user.getUsername());
        claims.setIssuer("learn.com");
        claims.setAudience("Audience: User");

        Date now = new Date();
        /**
         * Expires 15 minutes later.
         */
        Date expire = new Date(now.toInstant().plus(15, ChronoUnit.MINUTES).toEpochMilli());

        claims.setExpiration(expire);
        claims.setNotBefore(now);
        claims.setIssuedAt(now);
        claims.setId("91234567");

        claims.put("role", "USER");

        return Jwts.builder().setClaims(claims).signWith(Secret_Key).compact();
    }
    
    /**
     * 1. java.util.Base64 does not work:
     * 
     * byte[] bytes = Base64.getDecoder().decode(token);
     * byte[] bytes = Base64.getUrlDecoder().decode(token);
     * 
     * 2. Freddie Mac/Universal: tomcat-embed-core:jar depends on spring-boot-starter-tomcat:jar depends on spring-boot-starter-web
     * 
     * import org.apache.tomcat.util.codec.binary.Base64;
     * byte[] bytes = new Base64(true).decode(token.getBytes());
     * 
     */
    public static String decodeTokenWithoutSignatureKey(String token) {
        /**
         * Freddie Mac/Universal
         */
        byte[] bytes = new Base64(true).decode(token.getBytes());

        String decodedToken = new String(bytes);
        
        return decodedToken;
    }
    
    
    

}
