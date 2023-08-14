package com.innovature.rentx.form;

import javax.validation.constraints.*;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImageProductForm {

    @NotNull(message = "{product.id.required}")
    private Integer productId;
    @NotBlank(message = "{Image1.required}")
    @Size( max = 255, message = "{image.size.exceeds}")
    private String image1;
    @Size( max = 255, message = "{image.size.exceeds}")
    private String image2;
    @Size( max = 255, message = "{image.size.exceeds}")
    private String image3;
    @Size( max = 255, message = "{image.size.exceeds}")
    private String image4;

    
}
