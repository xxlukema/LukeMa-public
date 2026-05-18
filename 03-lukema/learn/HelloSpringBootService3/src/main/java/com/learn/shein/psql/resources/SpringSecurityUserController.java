package com.learn.shein.psql.resources;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import jakarta.ws.rs.core.MediaType;

import org.springframework.boot.web.server.Cookie.SameSite;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.learn.boot.auth.jwt.JwtUtils;
import com.learn.entity.SpringSecurityUserEntity;
import com.learn.exception.AppException;
import com.learn.shein.psql.dto.SheinUserDto;
import com.learn.shein.psql.service.SpringSecurityUserService;

import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;


@Log4j2
@RequiredArgsConstructor
@RequestMapping("/spring/user")
@RestController
public class SpringSecurityUserController {

    /**
    * Implicit constructor injection
    */
    // Auto generated constructor by lombok
    // @Autowired
    private final SpringSecurityUserService springSecurityUserService;

    @ResponseStatus(value = HttpStatus.OK)
    @PostMapping(value = "/signoff", produces = { MediaType.APPLICATION_JSON })
    public ResponseEntity<?> signoff(HttpServletResponse response) {
        log.debug(() -> "Enter...");

        String value = "";

        ResponseCookie cookie = ResponseCookie.from(JwtUtils.COOKIE_NAME, value)
                .httpOnly(false) // cookie.setHttpOnly(true): Javascript cannot access the cookie through document.cookie (to mitigate XSS attacks).
                .secure(false) // (1) Chrome: If SameSite==None, then secure==true. (2) This cookie (false) is visible to both http and https.
                .path("/")
                .maxAge(0)
                /**
                 * `Strict` means the cookie is only sent for requests originating from the same URL as the current one.
                 * `Lax`    means the cookie is not sent on cross-site requests, but will be sent if the user navigates to the origin site from an external site.
                 * `None`   means the cookie will be sent on both `same-site` and `cross-site` requests, but can **ONLY** be used if the `Secure` attribute is also set.
                 */
                .sameSite(SameSite.LAX.toString())
                // .domain("example.com")
                .build();

        // String referer = ((HttpServletRequest) request).getHeader("referer");
        // response.sendRedirect(referer);

        /**
         * JwtAuthFilter6 will remove jwt cookie for this path
         */
        Map<String, String> body = new HashMap<>();
        body.put("message", "Deleted JWT Auth cookie.");

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(body); /** !!! Important !!! Response body cannot be entity. It must be a DTO. */
    }

    @ResponseStatus(value = HttpStatus.OK)
    @PostMapping(value = "/register", consumes = { MediaType.APPLICATION_JSON }, produces = { MediaType.APPLICATION_JSON })
    public ResponseEntity<?> register(@Valid @RequestBody SheinUserDto userDto) {
        log.debug(() -> "Enter...");

        log.debug("Input: {}", () -> userDto.toString());
        log.debug("Input email = {}", () -> userDto.getEmail());

        try {
            springSecurityUserService.save(userDto);

            Map<String, String> body = new HashMap<>();
            body.put("message", "Created");
            return new ResponseEntity<>(body, HttpStatus.CREATED);
        } catch (DataAccessException e1) {
            log.error(() -> "Error register user", e1);
            Map<String, String> body = new HashMap<>();
            if (e1.getMostSpecificCause() != null) {
                String msg = e1.getMostSpecificCause().getLocalizedMessage();
                if (msg.contains("unique_username")) {
                    body.put("message", "User with same email already exists");
                } else if (msg.contains("unique_phone")) {
                    body.put("message", "User with same phone already exists");
                } else {
                    body.put("message", msg);
                }
            } else {
                body.put("message", e1.getLocalizedMessage());
            }
            return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
        } catch (AppException e) {
            log.error(() -> "Error register user", e);
            Map<String, String> body = new HashMap<>();
            if (e.getLocalizedMessage() != null) {
                body.put("message", e.getLocalizedMessage());
            } else {
                body.put("message", e.getMessage());
            }
            return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
        } finally {
            log.info(() -> "Leave.");
        }
    }

    @ResponseStatus(value = HttpStatus.OK)
    @PostMapping(value = "/signin", consumes = { MediaType.APPLICATION_JSON }, produces = { MediaType.APPLICATION_JSON })
    // public ResponseEntity<?> signin(@Valid @RequestBody SheinUserDto userDto, ServletRequest request, ServletResponse response) {
    public ResponseEntity<?> signin(@Valid @RequestBody SheinUserDto userDto, ServletRequest request, HttpServletResponse response)
        throws IOException {

        log.debug(() -> "Enter...");

        log.debug("Input: {}", () -> userDto.toString());
        log.debug("Input email = {}", () -> userDto.getEmail());

        String username = userDto.getUsername();

        SpringSecurityUserEntity ssuser = null;

        try {
            if (username.contains("@")) {
                ssuser = this.springSecurityUserService.findByUsername(username);
            } else {
                String phone = username;
                StringBuilder sb = new StringBuilder();
                int counter = 0;
                for (char ch : phone.toCharArray()) {
                    if (ch >= '0' && ch <= '9') {
                        if (counter == 3 || counter == 6) {
                            sb.append('-');
                        }
                        sb.append(ch);
                        counter++;
                    }
                }

                phone = sb.toString();

                log.debug("phone: {}", () -> sb.toString());

                if (sb.length() == 12) {
                    ssuser = this.springSecurityUserService.findByPhone(phone);
                } //
            }
        } catch (AppException e) {
            log.error(() -> "Error signin user", e);
            Map<String, String> body = new HashMap<>();
            if (e.getLocalizedMessage() != null) {
                body.put("message", e.getLocalizedMessage());
            } else {
                body.put("message", e.getMessage());
            }
            return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
        } finally {
            log.info(() -> "Leave.");
        }

        if (ssuser == null) {
            // Delete cookie
            Map<String, String> body = new HashMap<>();
            body.put("message", "User not found");
            return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
        } else if (!ssuser.getEnabled()) {
            // Delete cookie
            Map<String, String> body = new HashMap<>();
            body.put("message", "User had been suspended");
            return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
        } else {
            String password = userDto.getPassword();

            if (ssuser.getPassword().equals(password)) {

                userDto.setUsername(ssuser.getUsername());
                userDto.setPassword(null);
                userDto.setFirstname(ssuser.getFirstname());
                userDto.setLastname(ssuser.getLastname());
                userDto.setBusinessname(ssuser.getBusinessName());
                userDto.setPhone(ssuser.getPhone());
                userDto.setEmail(ssuser.getUsername());
                if (ssuser.getCountry() != null) {
                    userDto.setCountryCode(ssuser.getCountry().getCode());
                }
                userDto.setBusinessname(ssuser.getBusinessName());
                userDto.setIsBuyOnly(ssuser.getIsBuyOnly());

                /*
                Collection<GrantedAuthority> authorities = new ArrayList<>();
                
                ssuser.getAuthorities().forEach(e -> {
                    log.debug("Role: {}", () -> e);
                    authorities.add(() -> e.getAuthority());
                });
                
                User user = new User(username, password, authorities);
                */

                String token = JwtUtils.generateToken(ssuser);

                /*
                // Set cookie
                Cookie cookie = new Cookie(JwtUtils.COOKIE_NAME, token);
                // cookie.setDomain("127.0.0.1");
                cookie.setPath("/");
                cookie.setHttpOnly(true); // cookie.setHttpOnly(true): Javascript cannot access the cookie through document.cookie (to mitigate XSS attacks).
                cookie.setSecure(false); // (1) Chrome: If SameSite==None, then secure==true. (2) This cookie (false) is visible to both http and https.
                cookie.setMaxAge(JwtUtils.EXPIRE_IN_SESONDS);
                cookie.setAttribute("SameSite", SameSite.LAX.toString());
                
                response.addCookie(cookie);
                
                HttpHeaders responseHeaders = new HttpHeaders();
                responseHeaders.set("Authorization", "Bearer " + token);
                
                return ResponseEntity.status(HttpStatus.OK).headers(responseHeaders).body(userDto);
                */

                // response.addHeader("Authorization", "Bearer " + token);
                // response.addHeader("shein-auth-jwt", token);
                response.addHeader("Authorization", token);

                /**
                 * Cookie only works for Firefox, not for Chrome.
                 */
                ResponseCookie cookie = ResponseCookie.from(JwtUtils.COOKIE_NAME, token)
                        .httpOnly(false) // cookie.setHttpOnly(true): Javascript cannot access the cookie through document.cookie (to mitigate XSS attacks).
                        .secure(false) // (1) Chrome: If SameSite==None, then secure==true. (2) This cookie (false) is visible to both http and https.
                        .path("/")
                        .maxAge(JwtUtils.EXPIRE_IN_SESONDS)
                        /**
                         * `Strict` means the cookie is only sent for requests originating from the same URL as the current one.
                         * `Lax`    means the cookie is not sent on cross-site requests, but will be sent if the user navigates to the origin site from an external site.
                         * `None`   means the cookie will be sent on both `same-site` and `cross-site` requests, but can **ONLY** be used if the `Secure` attribute is also set.
                         */
                        .sameSite(SameSite.LAX.toString())
                        // .domain("example.com")
                        .build();

                // String referer = ((HttpServletRequest) request).getHeader("referer");
                // response.sendRedirect(referer);

                return ResponseEntity.ok()
                        .header(HttpHeaders.SET_COOKIE, cookie.toString())
                        .body(userDto); /** !!! Important !!! Response body cannot be entity. It must be a DTO. */
            } else {
                // Delete cookie
                Map<String, String> body = new HashMap<>();
                body.put("message", "Password and username mismatch");
                return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
            }
        }
    }

}
