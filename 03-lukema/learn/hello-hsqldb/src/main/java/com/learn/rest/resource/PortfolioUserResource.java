package com.learn.rest.resource;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.learn.boot.config.MyProperties;
import com.learn.entity.PortfolioUser;
import com.learn.exception.AppException;
import com.learn.exception.RestException;
import com.learn.service.PortfolioUserService;

import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;


/**
 * 1. <mvc:annotation-driven /> is required.
 *
 * 2. @RequestBody IS required for POST even with @RestController.
 *
 * 3. @ResponseBody is NOT needed for @RestController
 *
 * @RestController = @Controller + @ResponseBody
 * @ResponseBody is NOT needed with @RestController However, @RequestBody IS
 *               needed for POST with @RestController. If omitted, there will be hidden
 *               mapping errors.
 *
 */
@Log4j2
@RequestMapping("/spring/user")
@RestController
public class PortfolioUserResource {

    @Autowired
    PortfolioUserService userService;

    @Autowired
    MyProperties myProperties;

    @Value("${jasypt.encryptor.algorithm}")
    private String jasyptEncryptAlgorithm;

    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping(value = "/ping", produces = { MediaType.APPLICATION_JSON_VALUE })
    public PortfolioUser ping()
        throws Exception {
        log.debug(() -> "Enter...");

        try {
            PortfolioUser user = new PortfolioUser();
            user.setUsername("ping.user. Yes! What you have seen is correct. This user does not exist.");
            user.setPassword("non-exist");
            user.setLastname("Makeshift lname");
            user.setFirstname("Makeshift fname");
            user.setMiddlename("Makeshift mname");
            user.setEmail("Makeshift email");
            user.setPhone("Makeshift phone");
            return user;
            // throw new RestException("Test RestException");
            // throw new NullPointerException("Test NullPointerException");
        } finally {
            log.info(() -> "Leave.");
        }
    }

    @ResponseStatus(value = HttpStatus.OK)
    @PostMapping(value = "/add", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    public PortfolioUser addUser(@Valid @RequestBody PortfolioUser user) {
        log.debug(() -> "Enter...");

        log.debug("Input: {}", user.toString());
        log.debug("Input username = {}", user.getUsername());

        try {
            if (user.getId() == -1) {
                user.setId(null);
            }
            user = userService.addUser(user);
            return user;
        } catch (AppException e) {
            log.error(() -> "Error Add User", e);
            user.setUsername("lukema");
            return user;
        } finally {
            log.info(() -> "Leave.");
        }
    }

    @PostMapping(value = "/login", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    public PortfolioUser login(@RequestBody PortfolioUser user) {
        log.debug(() -> "Enter...");

        try {
            String password = user.getPassword();
            String username = user.getUsername();

            if (username == null) {
                username = user.getEmail();
            }

            if (username != null) {
                PortfolioUser tmpUser = userService.getUserByUsername(username);

                if (tmpUser == null) {
                    tmpUser = userService.getUserByEmail(username);
                }
                if (tmpUser == null) {
                    tmpUser = userService.getUserByPhone(username);
                }

                if (tmpUser != null) {
                    String uname = tmpUser.getUsername();
                    if (uname == null) {
                        String email = tmpUser.getEmail();
                        uname = email.substring(0, email.indexOf("@"));
                        tmpUser.setUsername(uname);
                    }
                    if (tmpUser.getPassword().equals(password)) {
                        log.info("User login OK. user.username=" + tmpUser.getUsername());
                        return tmpUser;
                    } else {
                        tmpUser.setId(-2L); /* Incorrect password */
                        log.info("User found with incorrect password. user.username=" + tmpUser.getUsername());
                        return tmpUser;
                    }
                }
            }

            log.info(() -> "User not found. user.username=" + user.getUsername());

            return user;
        } catch (AppException e) {
            log.error(() -> "Error Add User", e);
            return new PortfolioUser();
        } finally {
            log.info(() -> "Leave.");
        }
    }

    @PostMapping(value = "/delete/{id}")
    public void deleteUserById(@PathVariable("id") Long id) {
        log.debug(() -> "Enter...");

        log.debug(() -> "Input: " + id);

        try {
            PortfolioUser user = new PortfolioUser();
            user.setId(id);
            userService.deleteUser(user);
        } catch (AppException e) {
            log.error("Error Delete User", e);
        } finally {
            log.info(() -> "Leave.");
        }

    }

    @GetMapping(value = "/get/{username}", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    public PortfolioUser getUserByUsername(@PathVariable("username") String username) {
        log.debug(() -> "Enter...");

        try {
            return userService.getUserByUsername(username);
        } catch (AppException e) {
            log.error("Error Add User", e);
            return new PortfolioUser();
        } finally {
            log.info(() -> "Leave.");
        }
    }

    @ExceptionHandler({ NullPointerException.class })
    public void handleException(NullPointerException ex) {
        log.error("Inside handleException().", ex);
    }

    @RequestMapping(value = "/handleGlobalException")
    public PortfolioUser handleGlobalException()
        throws RestException {
        log.debug(() -> "Enter...");

        try {
            PortfolioUser user = new PortfolioUser();
            user.setUsername("lukema");
            return user;
        } finally {
            log.info(() -> "Leave.");
        }
    }

    @Autowired(required = false)
    public void println() {
        log.info(() -> "######################## UserResource: This line will display on boot start up. ########################");
        log.info(() -> "Name: " + myProperties.getName() + ". Age: " + myProperties.getAge());
        log.info(() -> "JasyptEncrypt Algorithm = " + jasyptEncryptAlgorithm);
    }

}
