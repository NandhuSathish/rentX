package com.innovature.rentx.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
public class StoreForm {
    @NotBlank(message = "{phone.should.required}")
    @Size(min = 10, max = 15, message = "{size.must.be.between.10.and.15}")
    @Pattern(regexp = "^[0123456789]+$", message = "{phone.should.be.numbers}")
    private String mobile;

    @NotBlank(message = "{name.should.required}")
    @Size(min = 3, max = 20, message = "{name.must.be.between}")
    private String name;

    @NotBlank(message = "{pincode.should.required}")
    @Size(max = 8, min = 6, message = "{pincode.size.must.be}")
    @Pattern(regexp = "^[0123456789]+$", message = "{pincode.should.be}")
    private String pincode;

    @NotBlank(message = "{city.should.required}")
    @Size(max = 20, min = 3, message = "{city.size.must.be}")
    private String city;

    @NotBlank(message = "{state.should.required}")
    @Size(min = 3, max = 20, message = "{state.must.be.between}")
    private String state;

    @NotBlank(message = "{lattitude.should.required}")
    private String lattitude;

    @NotBlank(message = "{longitude.should.required}")
    private String longitude;

    @NotBlank(message = "{roadName.should.required}")
    @Size(min = 3, max = 20, message = "{roadName.must.be.between}")
    private String roadName;

    @NotBlank(message = "{buildingName.should.required}")
    @Size(min = 3, max = 20, message = "{buildingName.must.be.between}")
    private String buildingName;

}
