package com.innovature.rentx.form;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResendOtpForm {

    @NotBlank(message = "{emailToken.should.required}")
    private String emailToken;

    public boolean isValid() {
        return !emailToken.isEmpty();
    }
    
}
