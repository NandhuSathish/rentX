package com.innovature.rentx.form.validaton;

import com.innovature.rentx.entity.Product;
import com.innovature.rentx.exception.BadRequestException;
import com.innovature.rentx.repository.ProductRepository;
import com.innovature.rentx.util.LanguageUtil;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ProductValidation implements ConstraintValidator<ProductAnotation,String> {



    @Autowired
    private LanguageUtil languageUtil;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public void initialize(ProductAnotation constraintAnnotation) {
        // Perform any initialization logic
    }


    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value.isEmpty()){
            throw new BadRequestException(languageUtil.getTranslatedText("category.name.max.length", null, "en"));

        }
        else{

            productRepository.findByIdAndStatus(Integer.valueOf(value),Product.Status.ACTIVE.value).orElseThrow(() -> new BadRequestException(languageUtil.getTranslatedText("invalid.product.id", null, "en")));

        }

        return true;
    }
}
