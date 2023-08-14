package com.innovature.rentx.form;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class SubCategoryForm {
    @NotBlank(message = "{subcategory.should.required}")
    @Size(min = 3, message = "{subcategory.name.min.length}")
    @Size(max = 20, message = "{subcategory.name.max.length}")
    @Pattern(regexp = "^[a-zA-Z0-9]+(?: [a-zA-Z0-9]+)*$", message = "{subcategory.name.alphanumeric}")
    private String name;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @NotBlank(message = "{category.id.required}")
    private String categoryId;
}
