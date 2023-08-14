package com.innovature.rentx.form.validaton;
import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author aswin
 */
// @NotNull(message = "{password.required}")
@NotEmpty(message = "{password.required}")
//@Pattern(message = "{password.is.wrong}", regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])[a-zA-Z0-9 \\!\"#\\$%&'\\(\\)\\*\\+,\\-\\.\\/\\:;\\<\\=\\>\\?@\\[\\\\\\]\\^_`\\{\\|\\}~]+$", groups = Extended.class)
@Target({ANNOTATION_TYPE, FIELD, METHOD, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Constraint(validatedBy = {})
@Documented
public @interface LoginPassword {

    String message() default "{javax.validation.constraints.Pattern.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
