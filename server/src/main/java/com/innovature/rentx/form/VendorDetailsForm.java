package com.innovature.rentx.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VendorDetailsForm {

    @NotBlank(message = "{accountNumber.should.required}")
    @Size(min=8,max=18,message= "{accountNumber.must.be.between.8.and.18}")
    @Pattern(regexp = "^[0123456789]+$", message = "{accountNumber.should.be}")
    private String accountNumber;
   
    @NotBlank(message = "{holderName.should.required}")
    @Size(min=3,max=50,message= "{holderName.must.be.between.3.and.50}")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "{holderName.should.be}")
    private String holderName;

    @NotBlank(message = "{ifsc.should.required}")
    @Size(max = 11,min=11,message= "{ifsc.size.must.be}")
    @Pattern(regexp = "^[A-Z0-9]+$", message = "{ifsc.should.be}")
    private String ifsc;

    @NotBlank(message = "{gst.should.required}")
    @Size(max = 15,min=15,message= "{gst.size.must.be}")
    @Pattern(regexp = "^[A-Z0-9]+$", message = "{gst.should.be}")
    private String gst;
   
    @NotBlank(message = "{pan.should.required}")
    @Size(max = 10,min=10,message= "{pan.size.must.be}")
    @Pattern(regexp = "^[A-Z0-9]+$", message = "{pan.should.be}")
    private String pan;


    @NotBlank(message = "{emailToken.should.required}")
    private String emailToken;
    
}
