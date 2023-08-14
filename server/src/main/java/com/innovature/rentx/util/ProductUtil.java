package com.innovature.rentx.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.innovature.rentx.entity.Product;
import com.innovature.rentx.exception.BadRequestException;
import com.innovature.rentx.repository.ProductRepository;

@Component
public class ProductUtil {

    @Autowired
    private LanguageUtil languageUtil;

    @Autowired
    private ProductRepository productRepository;

    public Product validateProduct(Integer productId) {

        return productRepository.findByIdAndStatus(productId, Product.Status.ACTIVE.value)
                .orElseThrow(() -> new BadRequestException(
                        languageUtil.getTranslatedText("invalid.product.id", null, "en")));
    }

    public void validateQuantity(Product product, Integer quantity) {

        if (quantity > product.getAvailableStock()) {
            throw new BadRequestException(languageUtil.getTranslatedText("cart.product.quantity.range", null, "en"));

        } else if (quantity < 1) {
            throw new BadRequestException(languageUtil.getTranslatedText("invalid.quantity", null, "en"));
        }

    }

}
