package com.innovature.rentx.form;

import com.innovature.rentx.form.validaton.Password;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class ChangePasswordForm {


    @Password
    private String password;
    @NotBlank(message = "{emailToken.should.required}")
    private String emailToken;
}
