package com.innovature.rentx.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Setter
@Getter
public class OtpForm {

  
    @NotBlank(message = "{otp.should.required}")
    @Size(max = 6,message = "{Incorrect.otp}")
    private  String otp;
    
    @NotBlank(message = "{emailToken.should.required}")
    private String emailToken;



}
