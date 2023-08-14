package com.innovature.rentx.form;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GoogleSignInForm {

    @NotBlank(message = "{must.not.be.blank}")
    @Pattern(regexp = "^(?!.*\\s)(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*()\\-+_={}\\[\\]|\\\\:;\"'<>,.?/])[a-zA-Z0-9 \\!\"#\\$%&'\\(\\)\\*\\+,\\-\\.\\/\\:;\\<\\=\\>\\?@\\[\\\\\\]\\^_`\\{\\|\\}~]+$", message = "{Invalid.id.token}")
    private String token;
        
}
