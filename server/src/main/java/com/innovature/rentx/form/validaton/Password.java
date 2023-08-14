package com.innovature.rentx.form.validaton;
import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

// @NotNull(message = "{password.required}")
@NotBlank(message = "{password.required}")
@Size(message = "{size.password}", min = 8,max=16)
// @Size(message = "{size.password.max}", max = 16)
// @Pattern(regexp = "^(?=.*[a-z]).+$", message = "{password.should.contain.smallletter}", groups = SmallLetter.class)
// @Pattern(regexp = "^(?=.*[A-Z]).+$", message = "{password.should.contain.capitalletter}", groups = CapitalLetter.class)
// @Pattern(regexp = "^(?=.*[0123456789]).+$", message = "{password.should.contain.number}", groups = Number.class)
// @Pattern(regexp = "^(?=.*[!@#$%^&*()\\-+_={}\\[\\]|\\\\:;\"'<>,.?/]).+$", message = "{password.should.contain.specialcharacter}", groups = SpecialCharacter.class)
// @Pattern(regexp = "^[a-zA-Z0-9!@#$%^&*()+\\-=\\[\\]\\{\\}\\|\\\\;:'\",.<>/?]{8,16}$", message = "{password.should.contain}")
@Pattern(regexp = "^(?!.*\\s)(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*()\\-+_={}\\[\\]|\\\\:;\"'<>,.?/])[a-zA-Z0-9 \\!\"#\\$%&'\\(\\)\\*\\+,\\-\\.\\/\\:;\\<\\=\\>\\?@\\[\\\\\\]\\^_`\\{\\|\\}~]+$", message = "{password.should.contain}")
@Target({ANNOTATION_TYPE, FIELD, METHOD, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Constraint(validatedBy = {})
@Documented
public @interface Password {

    String message() default "{javax.validation.constraints.Pattern.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
