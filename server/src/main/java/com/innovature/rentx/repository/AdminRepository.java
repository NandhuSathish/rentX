package com.innovature.rentx.repository;

import com.innovature.rentx.entity.Admin;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface AdminRepository extends Repository<Admin,Integer> {

    Admin save(Admin admin);
    Optional<Admin> findByEmailAndRole(String email,byte role);

    @Query(value = "SELECT * FROM admin_table WHERE email=?", nativeQuery = true)
    Admin findByEmailId(String email);

    @Query(value = "SELECT * FROM admin_table WHERE id=?", nativeQuery = true)
    Admin findByUserId(Integer id);

    Optional<Admin> findByIdAndPassword(Integer Id, String password);

}
