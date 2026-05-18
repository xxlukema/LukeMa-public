package com.learn.validator;


import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.extern.log4j.Log4j2;


record UserRecord(@NotNull @Size(min = 2, max = 5, message = "username must be between 2 and 5 characters") String username) {
}


@Log4j2
class ValidatorTest {

    private Validator validator;

    /**
     * !Trick: Add the following dependency to the pom.xml file to use the
     * @NotNull and @Size annotations.
     *
     * <dependency>
     *   <groupId>jakarta.validation</groupId>
     *   <artifactId>jakarta.validation-api</artifactId>
     *   <version>3.1.1</version>
     * </dependency>
     * <dependency>
     *   <groupId>org.springframework.boot</groupId>
     *   <artifactId>spring-boot-starter-validation</artifactId>
     * </dependency>
     */
    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testUserValidator() {
        var user = new UserRecord("John");
        var violations = validator.validate(user);
        assertTrue(violations.isEmpty());
    }

    @Test
    void testUserValidatorWithNull() {
        var user = new UserRecord(null);
        var violations = validator.validate(user);
        log.debug("violations: {}", () -> violations);
        assertFalse(violations.isEmpty());
    }

    @Test
    void testUserValidatorWithInvalidSize() {
        var user = new UserRecord("TooLongUsername");
        var violations = validator.validate(user);
        log.debug("violations: {}", () -> violations);
        assertFalse(violations.isEmpty());
    }

}
