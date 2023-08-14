package com.innovature.rentx.form;



import com.innovature.rentx.form.ChangePasswordForm;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ChangePasswordFormTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    public void testChangePasswordFormValidation() {
        // Create a valid ChangePasswordForm
        ChangePasswordForm form = new ChangePasswordForm();
        form.setPassword("Negil@1999");
        form.setEmailToken("abc123");

        // Validate the form
        Set<ConstraintViolation<ChangePasswordForm>> violations = validator.validate(form);

        // Assertions
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testChangePasswordFormValidationWithInvalidData() {
        // Create an invalid ChangePasswordForm
        ChangePasswordForm form = new ChangePasswordForm();
        form.setPassword("weak");
        form.setEmailToken("");

        // Validate the form
        Set<ConstraintViolation<ChangePasswordForm>> violations = validator.validate(form);

        // Assertions
        assertEquals(2, violations.size());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("password")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("emailToken")));
    }
}
