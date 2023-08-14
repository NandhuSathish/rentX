package com.innovature.rentx.form;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StoreFormTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    public void testStoreFormValidation() {
        // Create a valid store form
        StoreForm form = new StoreForm();
        form.setMobile("1234567890");
        form.setName("Test Store");
        form.setPincode("123456");
        form.setCity("Test City");
        form.setState("Test State");
        form.setLattitude("12.3456");
        form.setLongitude("98.7654");
        form.setRoadName("Test Road");
        form.setBuildingName("Test Building");

        // Validate the form
        Set<ConstraintViolation<StoreForm>> violations = validator.validate(form);

        // Assertions
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testStoreFormValidationWithInvalidData() {
        // Create an invalid store form
        StoreForm form = new StoreForm();
        form.setMobile("1234567a");
        form.setName("T");
        form.setPincode("12a4b6");
        form.setCity("T");
        form.setState("T");
        form.setLattitude("");
        form.setLongitude("");
        form.setRoadName("T");
        form.setBuildingName("T");

        // Validate the form
        Set<ConstraintViolation<StoreForm>> violations = validator.validate(form);

        // Assertions
        assertEquals(9, violations.size());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("mobile")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("name")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("pincode")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("city")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("state")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("lattitude")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("longitude")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("roadName")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("buildingName")));
    }
}
