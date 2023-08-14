package com.innovature.rentx.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VendorRegStage1Form {

    @NotBlank(message = "{username.should.required}")
    @Size(max = 50,min=3,message= "{size.must.be.between.3.and.50}")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "{username.should.be}")
    private String username;


    @NotBlank(message = "{phone.should.required}")
    @Size(max = 15,min=10,message = "{size.must.be.between.10.and.15}")
    @Pattern(regexp = "^[0123456789]+$", message = "{phone.should.be.numbers}")
    private String phone;

    @NotBlank(message = "{emailToken.should.required}")
    private String emailToken;


}
