package com.innovature.rentx.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ImageProductTest {

    @Test
    public void testImageProductConstructor() {
        Integer productId = 1;
        String image1 = "image1.jpg";
        String image2 = "image2.jpg";
        String image3 = "image3.jpg";
        String image4 = "image4.jpg";

        ImageProduct imageProduct = new ImageProduct();
        imageProduct.setProductId(productId);
        imageProduct.setImage1(image1);
        imageProduct.setImage2(image2);
        imageProduct.setImage3(image3);
        imageProduct.setImage4(image4);

        Assertions.assertEquals(productId, imageProduct.getProductId());
        Assertions.assertEquals(image1, imageProduct.getImage1());
        Assertions.assertEquals(image2, imageProduct.getImage2());
        Assertions.assertEquals(image3, imageProduct.getImage3());
        Assertions.assertEquals(image4, imageProduct.getImage4());
    }

    @Test
    public void testImageProductStatus() {
        ImageProduct imageProduct = new ImageProduct();
        byte status = ImageProduct.Status.INACTIVE.value;

        imageProduct.setStatus(status);

        Assertions.assertEquals(status, imageProduct.getStatus());
    }


}
