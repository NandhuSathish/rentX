package com.innovature.rentx.form;

import com.innovature.rentx.form.validaton.Password;
import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class UserForm {
    @NotBlank(message = "{email.should.required}")
    @Size(max = 50,message = "{size.must.be.between.0.and.50}")
    @Email(message = "{email.Format.Invalid}",regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
    private String email;


    @Password
    private String password;
}
