package com.innovature.rentx.form.validaton;
import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = ProductValidation.class)
public @interface ProductAnotation {
    String message() default "Invalid product ID";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
