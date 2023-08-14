package com.innovature.rentx.repository;

import com.innovature.rentx.entity.ImageProduct;
import org.springframework.data.repository.Repository;

import java.util.ArrayList;
import java.util.List;

public interface ImageProductRepository extends Repository<ImageProduct, Integer> {

    ImageProduct save(ImageProduct imageProduct);

    ImageProduct findByProductId(Integer productId);

    ImageProduct findByProductIdAndStatusIn(Integer id, byte[] statuses);

    ArrayList<ImageProduct> findByProductIdAndStatus(Integer id, byte statuses);


}
