package com.innovature.rentx.repository;

import com.innovature.rentx.entity.Cart;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import com.innovature.rentx.entity.Product;

import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface CartRepository extends JpaRepository<Cart,Integer> {

    Cart save(Cart cart);
    
    Cart findByProductIdAndStatusAndUserId(Integer id, byte status,Integer userId);

//    Page<Cart> findByUserIdAndStatus(Integer userId, byte status, PageRequest of);


    List<Cart>findByUserIdAndStatus(Integer userId, byte status);
//    int findByUserIdAndStatus(Integer userId, byte status);
    Page<Cart> findByUserIdAndStatus(Integer userId, byte status, Pageable pageable);


    int countByUserIdAndStatus(Integer id, byte status);


    Optional<Cart> findByIdAndStatusAndUserId(Integer id, byte status, Integer currentUserId);
}
