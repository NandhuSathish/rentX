package com.innovature.rentx.form;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class ProductFormTest {

    @Test
    public void testProductForm() {
        
        String name = "Product A";
        Integer category = 1;
        Integer subCategory = 2;
        Integer store = 3;
        String description = "Product description";
        Map<String, String> specification = new HashMap<>();
        specification.put("key1", "value1");
        specification.put("key2", "value2");
        Integer stock = 10;
        Integer price = 9;
        String coverImage = "cover-image.jpg";
        String thumbnail = "thumbnail.jpg";

        ProductForm productForm = new ProductForm();
        productForm.setName(name);
        productForm.setCategory(category);
        productForm.setSubCategory(subCategory);
        productForm.setStore(store);
        productForm.setDescription(description);
        productForm.setSpecification(specification);
        productForm.setStock(stock);
        productForm.setPrice(price);
        productForm.setCoverImage(coverImage);
        productForm.setThumbnail(thumbnail);

        Assertions.assertEquals(name, productForm.getName());
        Assertions.assertEquals(category, productForm.getCategory());
        Assertions.assertEquals(subCategory, productForm.getSubCategory());
        Assertions.assertEquals(store, productForm.getStore());
        Assertions.assertEquals(description, productForm.getDescription());
        Assertions.assertEquals(specification, productForm.getSpecification());
        Assertions.assertEquals(stock, productForm.getStock());
        Assertions.assertEquals(price, productForm.getPrice());
        Assertions.assertEquals(coverImage, productForm.getCoverImage());
        Assertions.assertEquals(thumbnail, productForm.getThumbnail());
    }


}
