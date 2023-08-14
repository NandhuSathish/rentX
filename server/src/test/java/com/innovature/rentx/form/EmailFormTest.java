package com.innovature.rentx.form;

import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EmailFormTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    public void testEmailFormValidation() {
        // Create a valid EmailForm
        EmailForm form = new EmailForm();
        form.setEmail("test@example.com");

        // Validate the form
        Set<ConstraintViolation<EmailForm>> violations = validator.validate(form);

        // Assertions
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testEmailFormValidationWithInvalidData() {
        // Create an invalid EmailForm
        EmailForm form = new EmailForm();
        form.setEmail("invalidemail");

        // Validate the form
        Set<ConstraintViolation<EmailForm>> violations = validator.validate(form);

        // Assertions
        assertEquals(1, violations.size());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("email")));
    }
}
