package com.learn.validator;


import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import lombok.extern.log4j.Log4j2;


@Log4j2
class MyUserValidatorTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    void testValidator() {
        var user = new MyUser();
        user.setName(null);
        user.setWorking(false);
        user.setAboutMe("test");
        user.setAge(10);
        user.setEmail("test-invalid-email");

        Set<ConstraintViolation<MyUser>> violations = validator.validate(user);
        log.debug("violations: {}", () -> violations);

        assertFalse(violations.isEmpty());
        assertEquals(5, violations.size());
        assertThat(violations)
                .extracting(ConstraintViolation::getMessage)
                .containsExactlyInAnyOrder(
                        "Name cannot be null",
                        "Working must be true",
                        "About Me must be between 10 and 200 characters",
                        "Age should not be less than 18",
                        "Email should be valid");
    }
}
