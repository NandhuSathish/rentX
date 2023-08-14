package com.innovature.rentx.service.impl;

import com.innovature.rentx.entity.Cart;
import com.innovature.rentx.entity.Product;
import com.innovature.rentx.entity.User;
import com.innovature.rentx.exception.BadRequestException;
import com.innovature.rentx.form.CartForm;
import com.innovature.rentx.repository.CartRepository;
import com.innovature.rentx.repository.ProductRepository;
import com.innovature.rentx.repository.UserRepository;
import com.innovature.rentx.security.util.SecurityUtil;
import com.innovature.rentx.service.CartService;
import com.innovature.rentx.util.CategoryUtil;
import com.innovature.rentx.util.DateValidation;
import com.innovature.rentx.util.LanguageUtil;
import com.innovature.rentx.util.Pager;
import com.innovature.rentx.view.UserCartListView;
import com.innovature.rentx.view.UserCartProduListView;
import com.innovature.rentx.util.CartUtil;
import com.innovature.rentx.util.ProductUtil;
import com.innovature.rentx.view.UserCartView;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private LanguageUtil languageUtil;

    @Autowired
    private CategoryUtil categoryUtil;

    @Autowired
    private ProductUtil productUtil;

    @Autowired
    private CartUtil cartUtil;

    @Autowired
    private DateValidation dateValidation;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartRepository cartRepository;

    private static final String UNABLE_PERFORM = "unable.to.perform.this.action";
    private static final String pageNumber = "error.page.number.natural";

    private static final String sizeNumber = "error.page.size.natural";
    private static final String sortInvalid = "sort.invalid";
    private static final String directionInvalid = "direction.invalid";

    @Override
    public UserCartView save(CartForm form) {

        dateValidation.dateCheck(form.getStartDate(), form.getEndDate());

        User user = userRepository.findByIdAndStatusAndRole(SecurityUtil.getCurrentUserId(), User.Status.ACTIVE.value,
                User.Role.USER.value);

        Product product = productUtil.validateProduct(form.getProductId());

        cartUtil.validateItemExists(form.getProductId());
        productUtil.validateQuantity(product, form.getQuantity());

        Cart cart = new Cart(form, product, user);

        cartRepository.save(cart);
        int cartCount = cartRepository.countByUserIdAndStatus(SecurityUtil.getCurrentUserId(),
                Cart.Status.ACTIVE.value);

        UserCartView userCartView = new UserCartView(cart, cartCount);
        return userCartView;
    }

    @Override
    public ResponseEntity<String> modifyCart(Integer cartId, Integer quantity, String startDate, String endDate) {

        Cart cart = cartUtil.validateCartId(cartId);

        Product product = productUtil.validateProduct(cart.getProduct().getId());

        if (quantity != null) {
            productUtil.validateQuantity(product, quantity);
            cart.setQuantity(quantity);

        }
        if (startDate != null && endDate != null) {

            Date startDates = dateValidation.stringToStartDateFormat(startDate);

            Date endDates = dateValidation.stringToEndDateFormat(endDate);

            dateValidation.dateCheck(startDates, endDates);
            cart.setStartDate(startDates);
            cart.setEndDate(endDates);

        } else if (startDate != null) {
            Date startDates = dateValidation.stringToStartDateFormat(startDate);

            dateValidation.dateCheck(startDates, cart.getEndDate());
            cart.setStartDate(startDates);

        } else if (endDate != null) {
            Date endDates = dateValidation.stringToEndDateFormat(endDate);

            dateValidation.dateCheck(cart.getStartDate(), endDates);
            cart.setEndDate(endDates);

        }
        cartRepository.save(cart);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    public ResponseEntity<String> removeItem(Integer cartId) {

        Cart cart = cartUtil.validateCartId(cartId);

        cart.setStatus(Cart.Status.DELETED.value);
        cartRepository.save(cart);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public UserCartListView viewCartProduct(Integer page, Integer size, String sort, String order) {
        double totalPrice = 0.0;
        boolean orderD = !order.equalsIgnoreCase("asc");

        categoryUtil.checkListParams(page, size, sort, Sort.Direction.fromString(order.toUpperCase()), pageNumber,
                sizeNumber, sortInvalid, directionInvalid);

        Pageable pageable = PageRequest.of(page - 1, size, (orderD) ? Sort.Direction.DESC : Sort.Direction.ASC, sort);
        List<Cart> getTotalPrice = cartRepository.findByUserIdAndStatus(SecurityUtil.getCurrentUserId(),
                Cart.Status.ACTIVE.value);
        Page<Cart> cartPage = cartRepository.findByUserIdAndStatus(SecurityUtil.getCurrentUserId(),
                Cart.Status.ACTIVE.value, pageable);
        List<Cart> result = cartPage.getContent();
        for (Cart cart : getTotalPrice) {
            double price = cart.getProduct().getPrice();
            int quantity = cart.getQuantity();
            totalPrice += price * quantity;
        }

        int count = cartRepository.countByUserIdAndStatus(SecurityUtil.getCurrentUserId(), Cart.Status.ACTIVE.value);

        List<UserCartProduListView> cartViewList = result.stream()
                .map(UserCartProduListView::new)
                .collect(Collectors.toList());

        Pager<UserCartProduListView> pager = new Pager<>(size, count, page);
        pager.setResult(cartViewList);

        UserCartListView userCartListView = new UserCartListView();
        userCartListView.setCartProducts(pager);
        userCartListView.setTotalPrice(totalPrice);

        return userCartListView;
    }

}
