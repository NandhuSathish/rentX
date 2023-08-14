package com.innovature.rentx.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import com.innovature.rentx.entity.SubCategory;
import com.innovature.rentx.view.SubCategoryListView;

public interface SubCategoryRepository extends JpaRepository<SubCategory, Long> {
    Optional<SubCategory> findByName(String name);
    @Modifying
    @Transactional
    void deleteByCategoryId(Integer categoryId);
    Optional<SubCategory> findById(Integer id);
    Page<SubCategory> findByCategoryIdAndNameContainingAndStatus(Integer id, String name, Byte status,Pageable pageable);
    List<SubCategory> findByCategoryIdAndStatus(int catId, byte value);
    Optional<SubCategory> findByNameAndStatus(String name, byte value);
    Optional<SubCategory> findByIdAndStatus(Integer subCategoryId, byte value);
    Optional<SubCategory> findByIdAndCategoryIdAndStatus(Integer subCategoryId,Integer categoryId, byte value);

    Page<SubCategory> findByCategoryIdAndStatusAndNameContainingIgnoreCase(Integer categoryId, byte status, String search, Pageable pageable);
    List<SubCategoryListView> findByNameContainingAndStatus(String search, byte value, Pageable pageable);
    boolean existsByIdAndStatus(Integer id, byte value);
    int countByNameContainingIgnoreCaseAndStatus(String name, byte status);
    int countByNameContainingAndStatus(String search, byte value);
    int countByNameContainingIgnoreCaseAndCategoryIdAndStatus(String search, Integer id, byte value);
   
}

