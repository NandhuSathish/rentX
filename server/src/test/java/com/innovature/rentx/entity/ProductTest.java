package com.innovature.rentx.entity;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class ProductTest {

    @Test
    public void testProductConstructor() {

        String name = "Product A";
        Category category = new Category();
        SubCategory subCategory = new SubCategory();
        Store store = new Store();
        Map<String, String> specification = new HashMap<>();
        specification.put("key1", "value1");
        specification.put("key2", "value2");
        String description = "Product description";
        byte status = Product.Status.INACTIVE.value;
        int stock = 10;
        int availableStock = 10;
        double price = 9.99;
        String coverImage = "cover-image.jpg";
        String thumbnail = "thumbnail.jpg";

        Product product = new Product();
        product.setName(name);
        product.setCategory(category);
        product.setSubCategory(subCategory);
        product.setStore(store);
        product.setSpecification(specification);
        product.setDescription(description);
        product.setStatus(status);
        product.setStock(stock);
        product.setAvailableStock(availableStock);
        product.setPrice(price);
        product.setCoverImage(coverImage);
        product.setThumbnail(thumbnail);

        Assertions.assertEquals(name, product.getName());
        Assertions.assertEquals(category, product.getCategory());
        Assertions.assertEquals(subCategory, product.getSubCategory());
        Assertions.assertEquals(store, product.getStore());
        Assertions.assertEquals(specification, product.getSpecification());
        Assertions.assertEquals(description, product.getDescription());
        Assertions.assertEquals(status, product.getStatus());
        Assertions.assertEquals(stock, product.getStock());
        Assertions.assertEquals(availableStock, product.getAvailableStock());
        Assertions.assertEquals(price, product.getPrice());
        Assertions.assertEquals(coverImage, product.getCoverImage());
        Assertions.assertEquals(thumbnail, product.getThumbnail());
    }

    @Test
    public void testProductAvailability() {
        Product product = new Product();
        int stock = 10;
        int availableStock = 5;

        product.setStock(stock);
        product.setAvailableStock(availableStock);

        Assertions.assertEquals(stock, product.getStock());
        Assertions.assertEquals(availableStock, product.getAvailableStock());
    }


}
