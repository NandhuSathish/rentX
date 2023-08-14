package com.innovature.rentx.repository;
import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotNull;

import org.springframework.data.jpa.repository.JpaRepository;

import com.innovature.rentx.entity.Category;
import com.innovature.rentx.entity.Product;
import org.springframework.data.domain.PageRequest;


public interface ProductRepository extends JpaRepository<Product,Long> {

Product save(Product product); 

Product findById(Integer id);

Product findByStoreId(Integer currentUserId);

Product findAllByStoreId(Integer storeId);

boolean existsByNameAndStoreId(String name,  Integer store);

Optional<Product> findByIdAndStatus(Integer id,byte status);

Product findByIdAndStatusIn(Integer id, byte[] statuses);

// Product findByIdAndStatusIn(Integer productId, byte value);



}
