package com.innovature.rentx.form;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class CategoryForm {
    @NotBlank(message = "{category.should.required}")
    @Size(min = 3, message = "{category.name.min.length}")
    @Size(max = 20, message = "{category.name.max.length}")
    @Pattern(regexp = "^[a-zA-Z0-9]+(?: [a-zA-Z0-9]+)*$", message = "{category.name.alphanumeric}")
    private String name;
    @NotBlank(message = "{coverImage.category.required}")
    @Size( max = 255, message = "{image.size.exceeds}")
    private String coverImage;
}

