package com.innovature.rentx.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailForm {

    @NotBlank(message = "{email.should.required}")
    @Size(max = 255)
    @Email(message="{email.Format.Invalid}")
    private String email;
    
    
}
