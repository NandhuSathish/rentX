package com.innovature.rentx.repository;

import com.innovature.rentx.entity.Store;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store,Integer> {

    Store findByNameAndUserId(String name,Integer id);

    Store findByName(String name);

    Store findByNameAndLattitudeAndLongitude(String name,String lattitude,String longitude);

    Store save(StoreRepository store);

    Page<Store>findByUserIdAndUserRoleAndUserStatusAndNameContainingIgnoreCase(Integer id, byte role, byte status, String name, PageRequest of);

    int countByUserIdAndUserRoleAndUserStatusAndNameContainingIgnoreCase(Integer id, byte role, byte status, String name);

    Optional<Store> findByIdAndStatus(Integer store, byte value);

    Iterable<Store> findAllByStatus(byte value);

    Optional<Store> findByIdAndUserIdAndStatus(Integer store,Integer userId, byte value);


}
