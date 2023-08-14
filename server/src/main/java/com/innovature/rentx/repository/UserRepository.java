package com.innovature.rentx.repository;

import com.innovature.rentx.entity.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface UserRepository extends Repository<User, Integer> {

    Optional<User> findByEmail(String email);

    User findByEmailAndStatus(String email, byte value);

    Optional<User> findByIdAndPassword(Integer id, String password);

    Optional<User> findById(Integer id);

    User save(User user);

    public boolean existsByEmail(String email);

    @Query(value = "SELECT * FROM user_table WHERE email=?", nativeQuery = true)
    User findByEmailId(String email);

    @Query(value = "SELECT * FROM user_table WHERE email=?", nativeQuery = true)
    Boolean findByEmailIdUser(String email);

    @Query(value = "SELECT * FROM user_table WHERE id=?", nativeQuery = true)
    User findByUserId(Integer id);

    Optional<User> findByIdAndRoleAndStatusIn(Integer id, byte role, byte[] statuses);

    Page<User> findByRoleAndStatusInAndUsernameContainingIgnoreCase(byte role, byte[] statuses, String username,
            PageRequest of);

    int countByRoleAndStatusInAndUsernameContainingIgnoreCase(byte role, byte[] statuses, String username);

    Page<User> findByRoleAndStatusInAndEmailContainingOrUsernameContainingIgnoreCaseOrPhoneContaining(byte role,
            byte[] statuses, String email, String phone, String username, PageRequest of);

    Page<User> findByRoleAndStatusInAndEmailContainingIgnoreCase(byte role, byte[] statuses, String email,
            PageRequest of);

    int countByRoleAndStatusInAndEmailContainingIgnoreCaseOrPhoneContainingIgnoreCaseOrUsernameContainingIgnoreCase(
            byte role, byte[] statuses, String email, String phone, String username);

    User findByIdAndStatus(Integer currentUserId, byte value);
    User findByIdAndStatusAndRole(Integer currentUserId, byte value,byte role);


    Integer findRoleByEmail(String email);


}
