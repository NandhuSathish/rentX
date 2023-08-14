package com.innovature.rentx.form;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class SubCategoryUpdateForm {
    @NotBlank(message = "{subcategory.should.required}")
    @Size(min = 3, message = "{subcategory.name.min.length}")
    @Size(max = 100, message = "{subcategory.name.max.length}")
    @Pattern(regexp = "^[a-zA-Z0-9]+(?: [a-zA-Z0-9]+)*$", message = "{subcategory.name.alphanumeric}")
    private String name;
}
