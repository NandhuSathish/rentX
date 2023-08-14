package com.innovature.rentx.form.validaton;


import java.util.Map;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class SpecificationValidator implements ConstraintValidator<ValidSpecification, Map<String, String>> {

    private static final int MIN_LENGTH = 3;
    private static final int MAX_LENGTH = 20;

    @Override
    public void initialize(ValidSpecification constraintAnnotation) {
       
    }

    @Override
    public boolean isValid(Map<String, String> specification, ConstraintValidatorContext context) {
        if (specification == null) {
            return true; 
        }

        for (Map.Entry<String, String> entry : specification.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            if (key == null || key.length() < MIN_LENGTH || key.length() > MAX_LENGTH ||
                    value == null || value.length() < MIN_LENGTH || value.length() > MAX_LENGTH) {
                return false; 
            }
        }

        return true;
    }
}
