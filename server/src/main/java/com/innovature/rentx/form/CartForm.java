package com.innovature.rentx.form;

import com.innovature.rentx.json.Json;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CartForm {


    @NotNull(message = "{product.id.required}")
    private Integer productId;

    @NotNull(message = "{quantity.should.required}")
    private Integer quantity;


    @Json.DateFormat
    @NotNull(message = "{startDate.should.required}")
    private Date startDate;

    @Json.DateFormat
    @NotNull(message = "{endDate.should.required}")
    private Date endDate;

}
