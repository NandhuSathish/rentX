package com.innovature.rentx.form;
import javax.persistence.Column;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.innovature.rentx.form.validaton.ValidSpecification;
import lombok.*;
import java.util.Map;

@Getter
@Setter
public class ProductForm {

    @NotBlank(message = "{Product.name.should.required}")
    @Size(min = 3, max = 20, message = "{name.must.between.3.and.20}")
    private String name;

    @NotNull(message = "{category.id.required}")
    @Column(name = "categoryId", nullable = false)
    private Integer category;

    @NotNull(message = "{subcategoryid.queryparam.required}")
    @Column(name = "subCategoryId", nullable = false)
    private Integer subCategory;

    @NotNull(message = "{storeId.should.required}")
    @Column(name = "storeId", nullable = false)
    private Integer store;

    @NotBlank(message = "{description.should.required}")
    @Size(min = 3, max = 50, message = "{description.size.must.between}")
    private String description;

    @Valid
    @NotEmpty(message = "{specification.should.required}")
    @Size( max = 10, message = "{specification.key.value.pair.limit.exceeded}")
    @ValidSpecification(message = "{specification.size.must.between}")
    private Map<String, String> specification;

    @NotNull(message = "{stock.should.required}")
    @Max(message = "{stock.limit}", value = 999999)
    @Min(message = "{stock.limit}", value = 1)
    private Integer stock;

    @NotNull(message = "{price.should.required}")
    @Min(message = "{price.limit}", value = 1)
    @Max(message = "{price.limit}", value = 9999999)
    private Integer price;

    @NotBlank(message = "{coverImage.should.required}")
    @Size( max = 255, message = "{image.size.exceeds}")
    private String coverImage;

    @NotBlank(message = "{thumbnail.should.required}")
    @Size( max = 255, message = "{image.size.exceeds}")
    private String thumbnail;
  

}
