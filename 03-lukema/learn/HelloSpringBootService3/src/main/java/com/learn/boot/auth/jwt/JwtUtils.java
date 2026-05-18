package com.learn.boot.auth.jwt;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import com.learn.boot.auth.thirdparty.ThirdPartySpringSecurityGrantedAuthority;
import com.learn.entity.SpringSecurityUserEntity;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.log4j.Log4j2;


@Log4j2
public class JwtUtils {

    /** cookie name */
    public static final String COOKIE_NAME = "shein-auth-jwt";

    /** expire in one hour */
    public static final int EXPIRE_IN_SESONDS = 3_600 * 4;

    private static final String ROLES_KEY = "roles";

    // @Value("${jwt.secret}")
    // private String secret;

    // byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
    // String decodedString = new String(decodedBytes);
    // private String secret = Base64.getEncoder().encodeToString("My SECRET Key".getBytes());

    private static final String SECRET = """
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
            """;

    /**
     * !!! Important !!!
     * This is not a Random key. All generated keys can be reused if server restarts.
     * Therefore, this is not as secure as a Secure-Random key.
     */
    public static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(SECRET.getBytes());

    /**
     * !!! Important !!!
     * Secure-Random key. The value changes when server restarts.
     * Therefore, all generated keys are voided if server restarts.
     */
    // private static final SecretKey SECRET_KEY = Keys.secretKeyFor(Signature_Algorithm);

    public static Authentication authorizeUser(User user) {

        log.info("JWT Authentication for {}", () -> user.getUsername());

        if (user.getUsername() == null) {
            return null;
        }

        /**
         * Check username/password here.
         *
         * This is where the password decryption happens. Encrypt username/password and compare with values saved in database.
         */
        /*
        if (pasword does not match) {
            return null;
        }
        */

        /**
         * Call REST/LDAP third party service.
         *
         * If user passes authentication, add authorized roles here.
         */
        List<ThirdPartySpringSecurityGrantedAuthority> authorities = new ArrayList<>();

        user.getAuthorities().stream().forEach(e -> {
            authorities.add(new ThirdPartySpringSecurityGrantedAuthority(e.getAuthority()));
        });

        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), authorities);
        /**
         * This is very important! Without this line, it will think user is anonymous user.
         *
         * -- SecurityContextHolder.getContext().setAuthentication(newAuthentication);
         */
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return authentication;
    }

    /**
     * Temp. For reference
     */

    protected static Authentication authorizeUserTest(String username, String password) {

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

            case "admin" -> {
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

            case "user" -> {
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

            default -> {
                return null;
            }
        }
        /**
         * If user passes authentication, add authorized roles here.
         */
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
        Claims payload = Jwts.parser()
                .verifyWith(SECRET_KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        log.info("expire: {}", () -> payload.getExpiration());
        log.info("JWT Token: {}", () -> payload.toString());

        /*
        if ("admin".equals(body.getSubject())) {
        
            Collection<GrantedAuthority> roles = new ArrayList<>();
            roles.add(() -> "ROLE_ADMIN");
            roles.add(() -> "ROLE_USER");
        
            return new User("admin", "admin", roles);
        } else {
            return null;
        }
        */

        // List<GrantedAuthority> list = (List<GrantedAuthority>) extractValueForKey(token, ROLES_KEY, ArrayList.class);
        // or
        // Claims claims = extractAllClaims(token);
        // ArrayList<?> list = claims.get(ROLES_KEY, ArrayList.class);
        // or
        ArrayList<?> list = (ArrayList<?>) extractClaim(token, (claims) -> claims.get(ROLES_KEY));

        log.debug("List of roles: {}", () -> list);

        Collection<GrantedAuthority> authorities = new ArrayList<>();

        list.forEach(e -> {
            log.debug("Role: {} {}", () -> e, () -> e.toString());
            authorities.add(() -> e.toString());
        });

        log.debug("authorities: {}", () -> authorities);

        String username = extractUsername(token);

        return new User(username, "dummy", authorities);
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
    public static String generateToken(SpringSecurityUserEntity user) {
        Map<String, Object> claims = new HashMap<>();
        /**
         * Since jjwt-api-0.12.3:
         *
         * java.lang.UnsupportedOperationException: JWT Claims instance is immutable and may not be modified.
         * Therefore, `Claims claims = Jwts.claims().build();` will be immutable.
         */
        // (Not this since 0.12.3. This will get an immutable map.) Claims claims = Jwts.claims().build();

        List<String> roles = new ArrayList<>();

        user.getAuthorities().stream().forEach(e -> {
            roles.add(e.getAuthority());
        });

        claims.put(ROLES_KEY, roles);

        claims.put("lastname", user.getLastname());
        claims.put("firstname", user.getFirstname());
        claims.put("email", user.getUsername());
        claims.put("businessname", user.getBusinessName());
        claims.put("isBuyOnly", user.getIsBuyOnly());
        claims.put("phone", user.getPhone());
        claims.put("isEnabled", user.getEnabled());
        if (user.getCountry() != null) {
            claims.put("countryCode", user.getCountry().getCode());
        }

        /** JUnit test */
        claims.put("role", "USER");
        claims.put("key 1", "value 1");

        return createToken(claims, user.getUsername());
    }

    private static String createToken(Map<String, Object> claims, String username) {
        if (claims == null) {
            claims = Jwts.claims().build();
        }

        Date now = new Date();
        Date expire = new Date(System.currentTimeMillis() + 1_000 * EXPIRE_IN_SESONDS);

        return Jwts.builder()
                .claims()
                .add(claims)
                .and()
                .subject(username)
                .issuer("learn.com")
                .issuedAt(now)
                .notBefore(now)
                .expiration(expire)
                .signWith(SECRET_KEY)
                .compact();
    }

    /**
     * New functions
     */
    public static String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public static <T> T extractValueForKey(String token, String key, Class<T> clazz) {
        final Claims claims = extractAllClaims(token);
        return claims.get(key, clazz);
    }

    public static Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public static <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public static Claims extractAllClaims(String token) {
        Claims payload = Jwts.parser()
                .verifyWith(SECRET_KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return payload;
    }

    public static Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public static Boolean validateToken(String token, User user) {
        final String username = extractUsername(token);
        return (username.equals(user.getUsername()) && !isTokenExpired(token));
    }

}
