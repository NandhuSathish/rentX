package com.innovature.rentx.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.innovature.rentx.entity.Cart;
import com.innovature.rentx.exception.BadRequestException;
import com.innovature.rentx.repository.CartRepository;
import com.innovature.rentx.repository.ProductRepository;
import com.innovature.rentx.security.util.SecurityUtil;

@Component
public class CartUtil {

    @Autowired
    private LanguageUtil languageUtil;

    @Autowired
    private ProductRepository productRepository;



    @Autowired
    private CartRepository cartRepository;

    public Cart validateCartId(Integer cartId) {

        Cart cart = cartRepository
                .findByIdAndStatusAndUserId(cartId, Cart.Status.ACTIVE.value, SecurityUtil.getCurrentUserId())
                .orElseThrow(() -> new BadRequestException(
                        languageUtil.getTranslatedText("invalid.cart.id", null, "en")));

        return cart;
    }

    public void validateItemExists(Integer productId){
        Cart cart=cartRepository.findByProductIdAndStatusAndUserId(productId, Cart.Status.ACTIVE.value,
               SecurityUtil.getCurrentUserId() );

               if (cart != null) {
                throw new BadRequestException(languageUtil.getTranslatedText("cart.product.exist", null, "en"));
    
            }
          
    }
}
