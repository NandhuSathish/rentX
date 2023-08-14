package com.innovature.rentx.form;

import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class RefreshTokenForm {

    @NotBlank
    private String refreshToken;
}
